package au.org.htsv.hips.report;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import au.org.htsv.hips.report.service.UserAccountService;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HipsReportAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    private final UserAccountService userAccountService;

    public HipsReportAuthenticationSuccessHandler(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName();
        //UserData user = userAccountService.getUser(username);
        userAccountService.loginSuccess(username);
        response.sendRedirect("/report"); // go to main page
    }

}
