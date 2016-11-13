package auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
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
import utils.EncryptionString;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private BuyerService serviceBuyer;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
		String password = EncryptionString.toMD5(authentication.getCredentials().toString());
		if (!serviceBuyer.isCorrectData(login, password)) return null;
		Authentication auth = getAuthentication(login, password);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public static Authentication getAuthentication (String login, String password){
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("isAuthenticated()"));
		return new UsernamePasswordAuthenticationToken(login, password, grantedAuths);
	}

}
