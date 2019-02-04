package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Vaste extends DomainEntity{

    // Attributes -------------------------------------------------------------

    private String ticker;
    private Date publicationMoment;
    private String body;
    private String picture;

    /*When publicationMoment is null -> isDraft*/
    //private boolean isDraft;

    // Getters and Setters ---------------------------------------------------

    @NotBlank
    @Column(unique = true)
    //TODO Control Check
    /*Ticker: yy-\w{4}-mmdd*/
    @Pattern(regexp ="^[0-9]{2}-[a-zA-Z0-9_]{4}-[0-9]{4}$")
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /*publicationMoment == NULL -> isDraft = TRUE */
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getPublicationMoment() {
        return publicationMoment;
    }

    public void setPublicationMoment(Date publicationMoment) {
        this.publicationMoment = publicationMoment;
    }

    @NotBlank
    //TODO Control Check
    @Size(max=100)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @URL
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    /*When publicationMoment is null -> isDraft*/
    /*
    @NotNull
    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }
    */



    // Relationships ---------------------------------------------------
    //TODO Control Check Vaste 0..* -> 1 FixUpTask & Vaste 0 .. * -> 1 Customer

    private Customer customer;
    private FixUpTask fixUpTask;

    //private Application Application;
    //private HandyWorker handyWorker;

    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(optional = false)
    public FixUpTask getFixUpTask() {
        return fixUpTask;
    }

    public void setFixUpTask(FixUpTask fixUpTask) {
        this.fixUpTask = fixUpTask;
    }
}
