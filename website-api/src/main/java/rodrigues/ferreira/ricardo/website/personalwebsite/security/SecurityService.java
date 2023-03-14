package rodrigues.ferreira.ricardo.website.personalwebsite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.User;

@Service
public class SecurityService {

	@Autowired
	private UserReactiveClient userReactiveClient;
	public Long getUserId() {
		SecurityContext context = SecurityContextHolder.getContext();
		if(context == null) {
			return null;
		}

		Authentication authentication = context.getAuthentication();
		if(authentication == null) {
			return null;
		}

		if (authentication.getPrincipal() instanceof Jwt) {
			Jwt jwt = (Jwt) authentication.getPrincipal();
			if(jwt == null) {
				return null;
			}

			String userId = jwt.getClaims().get("user_id").toString();
			if(userId == null) {
				return null;
			}

			return Long.parseLong(userId);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public UserResponse getCurrentUser() {
		Jwt principal = (Jwt) SecurityContextHolder.
				getContext().getAuthentication().getPrincipal();
		return userReactiveClient.findById(Long.valueOf(principal.getClaim("user_id"))).blockOptional()
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
	}

}