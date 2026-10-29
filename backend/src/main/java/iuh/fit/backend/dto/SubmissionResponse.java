package iuh.fit.backend.dto;

public class SubmissionResponse {
    private String message;
    private int received;

    public SubmissionResponse() {}

    public SubmissionResponse(String message, int received) {
        this.message = message;
        this.received = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }
}

