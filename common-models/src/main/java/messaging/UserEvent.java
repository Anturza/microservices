package messaging;

public class UserEvent {
    private String eventDescription;
    private String userEmail;

    public UserEvent() {

    }

    public UserEvent(String eventDescription, String userEmail) {
        this.eventDescription = eventDescription;
        this.userEmail = userEmail;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
