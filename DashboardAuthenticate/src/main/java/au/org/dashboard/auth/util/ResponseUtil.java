package au.org.dashboard.auth.util;

import au.org.dashboard.auth.entity.ApiResponse;
import au.org.dashboard.auth.entity.UserData;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(String message, UserData data, Object metadata) {
        return new ApiResponse<>("success", message, data, metadata);
    }

    public static <T> ApiResponse<T> error(String message, UserData data) {
        return new ApiResponse<>("error", message, data, null);
    }
    
    public static UserData getEmptyUser(Integer enabled) {
    	Object[] rawRow = new Object[] {
                "", "", enabled, "", "", ""
            };
    	UserData user = new UserData((rawRow));
    	return user;
    }
}