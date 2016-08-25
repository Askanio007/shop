package auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import entity.Buyer;
import service.BuyerService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private BuyerService serviceBuyer;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = serviceBuyer.getHashPassword(authentication.getCredentials().toString());
		Buyer buyer = serviceBuyer.getBuyer(name);

		if (buyer == null)
			return null;
		if (!buyer.getPassword().equals(password)) {
			return null;
		}
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("isAuthenticated()"));
		Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
