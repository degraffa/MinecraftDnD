package com.degraffa.mcdnd.test;

import com.degraffa.mcdnd.roll.Roll;
import com.degraffa.mcdnd.roll.RollOperation;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandRollTest {
    @Test
    public void testSingleRoll() {
        Roll diceRoll = new Roll();
        diceRoll.addRollComponent(1, 6, RollOperation.Add);

        int rollValue = diceRoll.rollDice().get(0).getRollTotal();

        assertTrue(rollValue >= 1 && rollValue <= 6);
    }
}
