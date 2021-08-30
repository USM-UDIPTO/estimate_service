package com.dxc.eproc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

// TODO: Auto-generated Javadoc
/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

	/** The Constant ANONYMOUS. */
	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	/**
	 * Instantiates a new security utils.
	 */
	private SecurityUtils() {
	}

	/**
	 * Get the login of the current user.
	 *
	 * @return the login of the current user.
	 */
	public static Optional<String> getCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
	}

	/**
	 * Extract principal.
	 *
	 * @param authentication the authentication
	 * @return the string
	 */
	private static String extractPrincipal(Authentication authentication) {
		if (authentication == null) {
			return null;
		} else if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
			return springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			return (String) authentication.getPrincipal();
		}
		return null;
	}

	/**
	 * Get the JWT of the current user.
	 *
	 * @return the JWT of the current user.
	 */
	public static Optional<String> getCurrentUserJWT() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(securityContext.getAuthentication())
				.filter(authentication -> authentication.getCredentials() instanceof String)
				.map(authentication -> (String) authentication.getCredentials());
	}

	/**
	 * Check if a user is authenticated.
	 *
	 * @return true if the user is authenticated, false otherwise.
	 */
	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && getAuthorities(authentication).noneMatch(ANONYMOUS::equals);
	}

	/**
	 * If the current user has a specific authority (security role).
	 * <p>
	 * The name of this method comes from the {@code isUserInRole()} method in the
	 * Servlet API.
	 *
	 * @param authority the authority to check.
	 * @return true if the current user has the authority, false otherwise.
	 */
	public static boolean isCurrentUserInRole(String authority) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && getAuthorities(authentication).anyMatch(authority::equals);
	}

	/**
	 * Checks if is current user in roles.
	 *
	 * @param authorities the authorities
	 * @return true, if is current user in roles
	 */
	public static boolean isCurrentUserInRoles(List<String> authorities) {
		boolean isExist = false;
		for (String authority : authorities) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			isExist = authentication != null && getAuthorities(authentication).anyMatch(authority::equals);
			if (isExist) {
				break;
			}
		}
		return isExist;
	}

	/**
	 * Gets the authorities.
	 *
	 * @param authentication the authentication
	 * @return the authorities
	 */
	public static Stream<String> getAuthorities(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority);
	}

	/**
	 * Extract authority from claims.
	 *
	 * @param claims the claims
	 * @return the list
	 */
	public static List<GrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
		return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
	}

	/**
	 * Gets the roles from claims.
	 *
	 * @param claims the claims
	 * @return the roles from claims
	 */
	@SuppressWarnings("unchecked")
	private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
		return (Collection<String>) claims.getOrDefault("groups", claims.getOrDefault("roles", new ArrayList<>()));
	}

	/**
	 * Map roles to granted authorities.
	 *
	 * @param roles the roles
	 * @return the list
	 */
	private static List<GrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
		return roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	/**
	 * Map custom roles to granted authorities.
	 *
	 * @param roles the roles
	 * @return the list
	 */
	public static List<GrantedAuthority> mapCustomRolesToGrantedAuthorities(Collection<String> roles) {
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	/**
	 * Checks if the current user has a specific authority.
	 *
	 * @param authority the authority to check.
	 * @return true if the current user has the authority, false otherwise.
	 */
	public static boolean hasCurrentUserThisAuthority(String authority) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && getAuthorities(authentication).anyMatch(authority::equals);
	}
}
