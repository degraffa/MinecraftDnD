package com.degraffa.mcdnd.test;

import com.degraffa.mcdnd.roll.DiceRoll;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandRollTest {
    @Test
    public void testSingleRoll() {
        DiceRoll diceRoll = new DiceRoll();
        diceRoll.addDiceSet(1, 6);

        int rollValue = diceRoll.rollDice().get(0).getRollTotal();

        assertTrue(rollValue >= 1 && rollValue <= 6);
    }
}
