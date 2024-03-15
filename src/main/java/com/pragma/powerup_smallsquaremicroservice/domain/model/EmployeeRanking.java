package com.pragma.powerup_smallsquaremicroservice.domain.model;

public class EmployeeRanking {
    private Long idEmployee;
    private String averageOrdersPerformance;
    
    public EmployeeRanking() {
    }
    
    public EmployeeRanking(Long idEmployee, String averageOrdersPerformance) {
        this.idEmployee = idEmployee;
        this.averageOrdersPerformance = averageOrdersPerformance;
    }
    
    public Long getIdEmployee() {
        return idEmployee;
    }
    
    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
    
    public String getAverageOrdersPerformance() {
        return averageOrdersPerformance;
    }
    
    public void setAverageOrdersPerformance(String averageOrdersPerformance) {
        this.averageOrdersPerformance = averageOrdersPerformance;
    }
}
