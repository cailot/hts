package au.org.htsv.hips.report;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import au.org.htsv.hips.report.service.UserAccountService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HipsReportAuthenticationFailHandler implements AuthenticationFailureHandler{
    private final UserAccountService userAccountService;

    public HipsReportAuthenticationFailHandler(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String username = request.getParameter("username");
        if (username != null) {
                userAccountService.loginFail(username);
            
        }
        response.sendRedirect("/report/login?error"); // fail path
    }

}
