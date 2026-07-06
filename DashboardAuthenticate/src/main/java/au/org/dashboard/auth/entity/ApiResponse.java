package au.org.dashboard.auth.entity;

public class ApiResponse<T> {
    private String status;
    private String message;
    private UserData data;
    private Object metadata;

    public ApiResponse(String status, String message, UserData data, Object metadata) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
        this.setMetadata(metadata);
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserData getData() {
		return data;
	}

	public void setData(UserData data) {
		this.data = data;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

}