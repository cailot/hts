package au.org.htsv.hips.report.controller;

import org.slf4j.Logger;

//import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import au.org.htsv.hips.report.entity.UserData;
import au.org.htsv.hips.report.service.UserAccountService;

/**
 * @author js278
 *
 */
@RestController
public class UserAccountRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserAccountRestController.class);

	@Autowired
	private UserAccountService userAccountService;
	
	// retrieve user based on username
	@RequestMapping(value = "/findUser/{username}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserData> findUser(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<UserData>(userAccountService.getUser(username), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<UserData>(HttpStatus.BAD_REQUEST);
		}
	}		
}
