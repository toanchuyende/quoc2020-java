package vn.agileviet.quoc2020.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import vn.agileviet.quoc2020.services.UserDetailsImpl;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${agileviet.app.jwtSecret}")
	private String jwtSecret;

	@Value("${agileviet.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

			String jwtValue = Jwts.builder()
					.setSubject((userPrincipal.getUsername()))
//					.set((userPrincipal.getUsername()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
					.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
			return jwtValue;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
