package nestorcicardini.skilltrade.users;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {

	// Get the id of the user that is currently authenticated
	public UUID getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null
				&& authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			return user.getId();
		}
		return null;
	}

	// Get the ProfileId of the user that is currently authenticated
	public UUID getCurrentProfileId() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null
				&& authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			return user.getProfile().getId();
		}
		return null;
	}

	// Method used for debugging. Print on console the roles and authorities of
	// the user invoking the endpoint.
	private void checkRolesAndAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication
				.getAuthorities();
		authorities.forEach(authority -> {
			if (authority.getAuthority().startsWith("ROLE_")) {
				System.out.println("Role: " + authority.getAuthority());
			} else {
				System.out.println("Authority: " + authority.getAuthority());
			}
		});
	}
}
