package config.filters.wrapper;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import model.Usuario;


public class TokenRequestWrapper extends HttpServletRequestWrapper  {

	private Usuario principal;
	
	public TokenRequestWrapper(HttpServletRequest request, Usuario user) {
	    super(request);
	    this.principal = user;
    }
	
	@Override
	public Principal getUserPrincipal() {
        return this.principal;
    }
	
	@Override
	public String getRemoteUser() {
        return this.principal != null ? this.principal.getName() : null;
    }
     
//    public boolean isUserInRole(final String role) {
//        if (role == null || role.trim().length() == 0) {
//            return false;
//        }
// 
//        if (this.principal == null) {
//            return false;
//        }
//        if (this.principal.getRoles() != null){
//            for (UserRole userRole: this.principal.getRoles()){
//                if (rolesEqual(role, userRole)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//     
//    private boolean rolesEqual(final String given, final UserRole candidate) {
//        return given != null ? given.equalsIgnoreCase(candidate.getName()) : false;
//    }
	
}