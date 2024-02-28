package com.pragma.powerup_smallsquaremicroservice.domain.utils;

public enum OrderStateEnum {
    PENDING("PENDING", 1), PREPARING("PREPARING", 2), READY("READY", 3), DELIVERED("DELIVERED", 4), CANCELLED(
            "CANCELLED", 5);
    
    private final String state;
    private final int stateNumber;
    
    OrderStateEnum(String state, int stateNumber) {
        this.state = state;
        this.stateNumber = stateNumber;
    }
    
    public String getState() {
        return state;
    }
    
    public int getStateNumber() {
        return stateNumber;
    }
    
    public OrderStateEnum nextState() {
        if (this.stateNumber == 4) {
            return OrderStateEnum.DELIVERED;
        }
        int nextState = this.stateNumber + 1;
        for (OrderStateEnum orderState : OrderStateEnum.values()) {
            if (orderState.getStateNumber() == nextState) {
                return orderState;
            }
        }
        return null;
    }
    
    public boolean isBefore(OrderStateEnum orderState) {
        return orderState.getStateNumber() - this.stateNumber == 1;
    }
    
    public boolean isAfer(OrderStateEnum orderState) {
        return this.stateNumber - orderState.getStateNumber() == -1;
    }
    
    public boolean isCancellable() {
        return this.stateNumber == 1;
    }
    
}
