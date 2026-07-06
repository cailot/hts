package au.org.dashboard.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import au.org.dashboard.auth.AuthService;
import au.org.dashboard.auth.entity.ApiResponse;
import au.org.dashboard.auth.entity.UserData;
import au.org.dashboard.auth.util.DashboardAuthenticateUtils;
import au.org.dashboard.auth.util.ResponseUtil;

@Controller
public class DashboardAuthenticateController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<ApiResponse<UserData>> authenticate2(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
		try {
			UserData user = ResponseUtil.getEmptyUser(98);
			if (DashboardAuthenticateUtils.isNull(username) || DashboardAuthenticateUtils.isNull(password)) {
				return ResponseEntity.ok(ResponseUtil.error("Missing parameters", user));
			}
			else {
				user = authService.getUser(username, password);
				if (user.getEnabled().equals(99)) {
					return ResponseEntity.ok(ResponseUtil.error("Invalid username", user));
				}
				else if (user.getEnabled().equals(96)) {
					return ResponseEntity.ok(ResponseUtil.error("Invalid password", user));
				}
				else if (user.getEnabled().equals(1)) {
					return ResponseEntity.ok(ResponseUtil.success("User retrieved successfully", user, null));
				}
				else {
					return ResponseEntity.ok(ResponseUtil.error("Error", user));
				}
			}
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error", e);
		}
				
	}
	
}
