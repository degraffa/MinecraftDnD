package com.degraffa.mcdnd.roll;

// A condition for dice rolls!
public class RollCondition {
    // The type of condition
    RollConditionType type;
    // A relative value for the condition
    int conditionValue;

    public RollCondition(RollConditionType type, int conditionValue) {
        this.type = type;
        this.conditionValue = conditionValue;
    }
}
