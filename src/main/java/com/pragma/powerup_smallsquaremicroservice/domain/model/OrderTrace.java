package com.pragma.powerup_smallsquaremicroservice.domain.model;

import java.time.LocalDateTime;

public class OrderTrace {
    private String id;
    private Long idOrder;
    private Long idClient;
    private String clientMail;
    private String lastState;
    private String currentState;
    private Long idEmployee;
    private String employeeMail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public OrderTrace() {
    }
    
    public OrderTrace(String id, Long idOrder, Long idClient, String clientMail, String lastState, String currentState,
                      Long idEmployee, String employeeMail, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.clientMail = clientMail;
        this.lastState = lastState;
        this.currentState = currentState;
        this.idEmployee = idEmployee;
        this.employeeMail = employeeMail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Long getIdOrder() {
        return idOrder;
    }
    
    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public String getClientMail() {
        return clientMail;
    }
    
    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }
    
    public String getLastState() {
        return lastState;
    }
    
    public void setLastState(String lastState) {
        this.lastState = lastState;
    }
    
    public String getCurrentState() {
        return currentState;
    }
    
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    
    public Long getIdEmployee() {
        return idEmployee;
    }
    
    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
    
    public String getEmployeeMail() {
        return employeeMail;
    }
    
    public void setEmployeeMail(String employeeMail) {
        this.employeeMail = employeeMail;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
