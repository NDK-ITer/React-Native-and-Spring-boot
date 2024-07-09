package com.example.business.data.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    // #region constructor
    public User() {
        setIsLock(false);
        setIsVerified(false);
    }
    // #endregion

    // #region properties
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String routeCode;
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String tokenAccess;
    private String tokenVerifyEmail;
    private String avatar;
    private String facebook;
    private String tikTok;
    private String instagram;
    private String twitter;
    private boolean isVerified;
    private boolean isLock;
    private LocalDateTime verifiedDate;
    private LocalDateTime tokenVerifyEmailExpired;
    private LocalDateTime dob;
    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserJWT> listJWT;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserInformationPast> listOldInformation;
    // #endregion

    // #region getter setter

    public String getFacebook() {
        return this.facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTikTok() {
        return this.tikTok;
    }

    public void setTikTok(String tikTok) {
        this.tikTok = tikTok;
    }

    public String getInstagram() {
        return this.instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public List<UserJWT> getListJWT() {
        return this.listJWT;
    }

    public void setListJWT(List<UserJWT> listJWT) {
        this.listJWT = listJWT;
    }

    public List<UserInformationPast> getListOldInformation() {
        return this.listOldInformation;
    }

    public void setListOldInformation(List<UserInformationPast> listOldInformation) {
        this.listOldInformation = listOldInformation;
    }

    public String getId() {
        return this.id;
    }

    public String getRouteCode() {
        return this.routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTokenAccess() {
        return this.tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getTokenVerifyEmail() {
        return this.tokenVerifyEmail;
    }

    public void setTokenVerifyEmail(String tokenVerifyEmail) {
        this.tokenVerifyEmail = tokenVerifyEmail;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isIsVerified() {
        return this.isVerified;
    }

    public boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isIsLock() {
        return this.isLock;
    }

    public boolean getIsLock() {
        return this.isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public LocalDateTime getVerifiedDate() {
        return this.verifiedDate;
    }

    public LocalDateTime getTokenVerifyEmailExpired() {
        return this.tokenVerifyEmailExpired;
    }

    public void setTokenVerifyEmailExpired(LocalDateTime tokenVerifyEmailExpired) {
        this.tokenVerifyEmailExpired = tokenVerifyEmailExpired;
    }

    public void setVerifiedDate(LocalDateTime verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public LocalDateTime getDOB() {
        return this.dob;
    }

    public void setDOB(LocalDateTime dob) {
        this.dob = dob;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    // #endregion
}
