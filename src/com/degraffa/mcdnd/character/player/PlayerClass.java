package com.degraffa.mcdnd.character.player;

import com.degraffa.mcdnd.character.Feat;
import com.degraffa.mcdnd.character.Proficiency;

import java.util.ArrayList;

public abstract class PlayerClass {
    private int level;

    private int hitDice;

    ArrayList<Proficiency> startingProficiencies;

    private boolean isHalfCaster;
    private boolean isFullCaster;

    ArrayList<Feat> classFeats;
}
