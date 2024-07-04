package com.example.business.data.entities;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;


@Entity
public class Role {
    //#region constructor
    public Role (){}
    //#endregion

    //#region properties
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String name;
    private String normalizeName;
    private String description;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> listUser;
    //#endregion

    //#region getter setter
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizeName() {
        return this.normalizeName;
    }

    public void setNormalizeName(String normalizeName) {
        this.normalizeName = normalizeName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getListUser() {
        return this.listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
    //#endregion
}
