package vn.agileviet.quoc2020;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import vn.agileviet.quoc2020.jwt.AuthEntryPointJwt;
import vn.agileviet.quoc2020.jwt.AuthTokenFilter;
import vn.agileviet.quoc2020.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/auth/signin",
						"/api/auth/signin-nebular",
						"/api/auth/signup", "/api/auth/signup-nebular",
						"/api/auth/forget-password").permitAll()
				.antMatchers("/api/auth/**").authenticated()
//				.antMatchers("/**").permitAll()				
				.anyRequest().permitAll()
//				.antMatchers("/api/auth/**").permitAll()
//				.antMatchers("/**.js", "/webjars/**", "/resources/**", "/resources/public/**").permitAll()
//				.antMatchers("/", "/home/**", "/hello/**", "/login/**", "/chat/**", "/chat").permitAll()
//				.antMatchers("/api/test/**").permitAll()
//				.anyRequest().authenticated()
				;

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}


}

//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//		// securedEnabled = true,
//		// jsr250Enabled = true,
//		prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	UserDetailsServiceImpl userDetailsService;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;
//	
//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}
//	
//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
//			.antMatchers("/api/test/**").permitAll()
//			.anyRequest().authenticated();
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
//	
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////
//////		http
//////			.authorizeRequests()
//////				.antMatchers("/", "/home").permitAll()
//////				.anyRequest().authenticated()
//////				.and()
//////			.formLogin()
//////				.loginPage("/login")
//////				.permitAll()
//////				.and()
//////			.logout()
//////				.permitAll();
////	}
//
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("agileviet").password("vietnam").roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
//}