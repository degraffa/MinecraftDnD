package com.degraffa.mcdnd.roll;

public abstract class RollComponent {
    protected RollOperation operation;

    public abstract RollSet roll();

    public RollOperation getRollOperation() { return operation; }
    public void setRollOperation(RollOperation operation) { this.operation = operation; }
}
