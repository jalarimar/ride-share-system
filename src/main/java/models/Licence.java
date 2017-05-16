package models;

import controllers.SessionManager;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import static controllers.Serializer.saveRss;
import static models.NotificationStatus.NONE;

/**
 * Created 26/04/2017.
 */
public class Licence {
    private String type;
    private String number;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private NotificationStatus lastSeenNotification;

    public Licence(String type, String number, LocalDate issueDate, LocalDate expiryDate) {
        this.type = type;
        this.number = number;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.lastSeenNotification = NONE;
    }

    public String getType() {
        return type;
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public NotificationStatus getLastSeenNotification() {
        return lastSeenNotification;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setExpiryDate(LocalDate newExpiry) {
        this.expiryDate = newExpiry;
        lastSeenNotification = NONE;
    }

    public void setLastSeenNotification(NotificationStatus lastSeenNotification) {
        this.lastSeenNotification = lastSeenNotification;
    }
}
