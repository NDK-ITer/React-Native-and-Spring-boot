package com.example.business.data.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserInformationPast {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String lastPhoneNumber;
    private String lastEmail;

    @CreatedDate
    private LocalDateTime dateChange;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //#region getter setter
    public String getLastPhoneNumber() {
        return this.lastPhoneNumber;
    }

    public void setLastPhoneNumber(String lastPhoneNumber) {
        this.lastPhoneNumber = lastPhoneNumber;
    }

    public String getLastEmail() {
        return this.lastEmail;
    }

    public void setLastEmail(String lastEmail) {
        this.lastEmail = lastEmail;
    }
    //#endregion
}
