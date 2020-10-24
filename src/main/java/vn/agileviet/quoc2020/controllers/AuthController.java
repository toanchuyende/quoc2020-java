package vn.agileviet.quoc2020.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.agileviet.quoc2020.models.ERole;
import vn.agileviet.quoc2020.models.ErrorMessage;
import vn.agileviet.quoc2020.models.Role;
import vn.agileviet.quoc2020.models.User;
import vn.agileviet.quoc2020.payload.request.LoginRequest;
import vn.agileviet.quoc2020.payload.request.NebularLoginRequest;
import vn.agileviet.quoc2020.payload.request.NebularSignupRequest;
import vn.agileviet.quoc2020.payload.request.SignupRequest;
import vn.agileviet.quoc2020.payload.response.JwtResponse;
//import vn.agileviet.quoc2020.payload.request.SignupRequest;
//import vn.agileviet.quoc2020.payload.response.JwtResponse;
import vn.agileviet.quoc2020.payload.response.MessageResponse;
import vn.agileviet.quoc2020.repository.RoleRepository;
import vn.agileviet.quoc2020.repository.UserRepository;
import vn.agileviet.quoc2020.jwt.JwtUtils;
import vn.agileviet.quoc2020.services.UserDetailsImpl;
import org.apache.commons.lang3.*;
// import org.springframework.security.core.userdetails.User as UserOrigin;

/**
 * @author tuan@agileviet.vn
 *
 *         Oct 15, 2020 4:00:11 AM
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	// private Logger logger = Logger.getLogger(AuthController.);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			if (jwt.isEmpty()) {
				return ResponseEntity.of(null);
			}

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

//			UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username(userDetails.getUsername())
//					.password(userDetails.getPassword()).roles(roles.toString())
//			.build();
//
//			Object tempData = new InMemoryUserDetailsManager(userDetails);

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles));
		} catch (Exception e) {
			// TODO: handle exception

//			return ResponseEntity.of(null);
			return ResponseEntity.ok(new ErrorMessage("001", "Login failed!"));
		}
	}

	@PostMapping("/signin-nebular")
	public ResponseEntity<?> authenticateUser_Nebular(@Valid @RequestBody NebularLoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			if (jwt.isEmpty()) {
				return ResponseEntity.of(null);
			}

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

//			UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username(userDetails.getUsername())
//					.password(userDetails.getPassword()).roles(roles.toString())
//			.build();
//
//			Object tempData = new InMemoryUserDetailsManager(userDetails);

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles));
		} catch (Exception e) {
			// TODO: handle exception

			return ResponseEntity.of(null);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setCreated_at(new Date());
		user.setUpdated_at(user.getCreated_at());
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	/**
	 * get logged user info condition: logged POST: JWT token
	 * 
	 * POST /api/auth/get-my-account-info HTTP/1.1 Host: localhost:8080
	 * Authorization: Bearer
	 * eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdW9jcGMiLCJpYXQiOjE2MDI3MDg1MTIsImV4cCI6MTYwMjc5NDkxMn0.NJF7XMo-KycDnd_GF4iesn6Sw-YKz51b3S6_QfHBHX3tfNSjDHw8kUIb4jLj9Fo0jKMlc_IVN5ZSVKFSKkaV_g
	 * 
	 * { "id": "5f866e471bbef63e3e10be9a", "username": "quocpc", "email":
	 * "phancongquoc@gmail.com", "password":
	 * "$2a$10$Hck5NxISCRbgr.W6cftpoOR1.xZMyYHJpAMtzZQCMD.9/KHT6Ddt.", "roles": [ {
	 * "id": "5f866b9c1cb13937041efb37", "name": "ROLE_MODERATOR" }, { "id":
	 * "5f866b9c1cb13937041efb36", "name": "ROLE_USER" } ] }
	 * 
	 * @return
	 */
	@GetMapping("/get-my-account-info")
	public ResponseEntity<?> getMyAccountInfo() {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			Optional<User> userOptional = userRepository.findByUsername(currentPrincipalName);

			if (userOptional == null) {
				return ResponseEntity.of(null);
			}

			User user = userOptional.get();

			return ResponseEntity.ok(user);

		} catch (Exception e) {
			// TODO: handle exception

			return ResponseEntity.of(null);
		}
	}

	/*
	 * get user info condition: logged POST: JWT token MODERATOR or ADMIN
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get-user-info")
	public HttpEntity<?> getUserInfo(@RequestParam(value = "name", defaultValue = "") String name) {

		try {

			if (null == name) {
				return HttpEntity.EMPTY;
			}
			if (StringUtils.isEmpty(name)) {
				return HttpEntity.EMPTY;
			}

//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String currentPrincipalName = authentication.getName();
//			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//					.collect(Collectors.toList());

			Optional<User> userOptional = userRepository.findByUsername(name);

			if (userOptional == null) {
				return HttpEntity.EMPTY;
			}

			User user = userOptional.get();
			// hide password
			user.setPassword(StringUtils.EMPTY);

			return ResponseEntity.ok(user);

		} catch (Exception e) {
			// TODO: handle exception
			return HttpEntity.EMPTY;
//			return ResponseEntity.ok(null);
		}
	}

	@PostMapping("/change-my-account-info")
	public Object changeMyAccountInfo(@Valid @RequestBody User accountInfoRequest) {

		if (null == accountInfoRequest) {
			return HttpEntity.EMPTY;
		}
		if (StringUtils.isEmpty(accountInfoRequest.getUsername())) {
			return HttpEntity.EMPTY;
		}

		// get request info
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		// if name: not equals with accountInfoRequest
		if (!currentPrincipalName.equals(accountInfoRequest.getUsername())) {
			return ResponseEntity.badRequest();
		}

		// match

		Optional<User> optinalUserOnDatabase = userRepository.findByUsername(currentPrincipalName);
		User userOnDatabase = optinalUserOnDatabase.get();

		userOnDatabase.setEmail(accountInfoRequest.getEmail());
		// set other fields
		userOnDatabase.setUpdated_at(new Date());

		userRepository.save(userOnDatabase);

		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}

	@PostMapping("/signup-nebular")
	public ResponseEntity<?> registerUser_Nebular(@Valid @RequestBody NebularSignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = null;
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);

			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);

		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setFullName(signUpRequest.getFullName());
		user.setCreated_at(new Date());
		user.setUpdated_at(user.getCreated_at());
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
