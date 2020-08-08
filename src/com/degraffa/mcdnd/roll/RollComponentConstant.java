package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

public class RollComponentConstant extends RollComponent {
    private int constant;

    public RollComponentConstant(int constant) {
        setConstant(constant);
    }

    public int getConstant() { return constant; }
    public void setConstant(int constant) { this.constant = constant; }

    @Override
    public RollSet roll() {
        int constant = this.constant;
        // negate this if we're subtracting it
        if (this.operation == RollOperation.Subtract) constant *= -1;

        return new RollSet(constant);
    }
}
