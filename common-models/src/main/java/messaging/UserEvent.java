package messaging;

import jakarta.validation.constraints.Email;

public class UserEvent {

    private UserEventDescription eventDescription;

    @Email
    private String userEmail;

    public UserEvent() {

    }

    public UserEvent(UserEventDescription eventDescription, String userEmail) {
        this.eventDescription = eventDescription;
        this.userEmail = userEmail;
    }

    public UserEventDescription getEventDescription() {
        return eventDescription;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setEventDescription(UserEventDescription eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public enum UserEventDescription {
        CREATED,
        DELETED
    }
}
