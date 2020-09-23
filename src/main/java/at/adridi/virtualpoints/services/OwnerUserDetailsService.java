package at.adridi.virtualpoints.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.adridi.virtualpoints.models.Owner;

@Service
public class OwnerUserDetailsService implements UserDetailsService {

	@Autowired
	private OwnerService ownerService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Owner loadedOwner = this.ownerService.getOwnerByEmail(username);
		if(loadedOwner!=null) {
			return new User(loadedOwner.getEmail(), loadedOwner.getPassword(), new ArrayList<>());
		} else {
			return null;
		}
	}

}
