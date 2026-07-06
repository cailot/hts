package au.org.hts.dashboard.security;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        
        String errorParam = ""; // default
        if ("unauthorised".equalsIgnoreCase(exception.getMessage())) {
            errorParam = "unauthorised";
        }else if ("Invalid_password".equalsIgnoreCase(exception.getMessage())) {
            errorParam = "bad_credentials";
        }else if ("Invalid username".equalsIgnoreCase(exception.getMessage())) {
            errorParam = "no_user";
        }
        getRedirectStrategy().sendRedirect(request, response, "/login?error=" + errorParam);
    }
}
