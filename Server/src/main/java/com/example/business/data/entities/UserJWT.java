package com.example.business.data.entities;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserJWT {
    //#region properties
    @Id
    private String token;
    private LocalDateTime expiredDate;
    private boolean isLogout;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //#endregion

    //#region getter setter
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
    //#endregion
}
