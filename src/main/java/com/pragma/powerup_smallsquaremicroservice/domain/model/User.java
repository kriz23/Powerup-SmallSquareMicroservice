package com.pragma.powerup_smallsquaremicroservice.domain.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String docNumber;
    private String phone;
    private LocalDate birthdate;
    private String mail;
    private String password;
    private Role role;
    
    public User() {
    }
    
    public User(Long id, String name, String surname, String docNumber, String phone, LocalDate birthdate, String mail,
                String password, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.docNumber = docNumber;
        this.phone = phone;
        this.birthdate = birthdate;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getDocNumber() {
        return docNumber;
    }
    
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDate getBirthdate() {
        return birthdate;
    }
    
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    
    public String getMail() {
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
}
