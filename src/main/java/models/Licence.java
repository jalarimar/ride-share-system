package models;

import java.time.LocalDate;

/**
 * Created 26/04/2017.
 */
public class Licence {
    private String type;
    private String number;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    public Licence(String type, String number, LocalDate issueDate, LocalDate expiryDate) {
        this.type = type;
        this.number = number;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
