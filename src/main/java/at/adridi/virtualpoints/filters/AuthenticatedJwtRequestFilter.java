package at.adridi.virtualpoints.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import at.adridi.virtualpoints.services.OwnerUserDetailsService;
import at.adridi.virtualpoints.util.JwtUtil;

/**
 * Listens for a valid JWT authentication token in the header of a HTTP request.
 * 
 * @author A.Dridi
 *
 */
@Component
public class AuthenticatedJwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private OwnerUserDetailsService ownerUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (!StringUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwtToken);
		}

		if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails loadedUserDetails = this.ownerUserDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwtToken, loadedUserDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, loadedUserDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
