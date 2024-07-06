package com.example.business.data.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserJWT {
    // #region properties
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String tokenCode;
    private String token;
    private LocalDateTime expiredDate;
    private boolean isLogout;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // #endregion

    // #region getter setter

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getTokenCode() {
        return this.tokenCode;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpired() {
        return this.expiredDate;
    }

    public void setExpired(LocalDateTime expired) {
        this.expiredDate = expired;
    }

    public boolean isIsLogout() {
        return this.isLogout;
    }

    public boolean getIsLogout() {
        return this.isLogout;
    }

    public void setIsLogout(boolean isLogout) {
        this.isLogout = isLogout;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // #endregion
}
