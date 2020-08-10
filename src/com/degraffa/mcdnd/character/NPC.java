package com.degraffa.mcdnd.character;

import com.degraffa.mcdnd.character.feat.Feat;

import java.util.ArrayList;

// Super class for later classes
// Holds data common between all DnD characters
public class NPC {
    private String name;

    private CreatureType creatureType;

    ArrayList<Feat> feats;
    ArrayList<Attack> attacks;

    public NPC(String name, int maxHP, int str, int dex, int con, int wis, int cha) {

        feats = new ArrayList<>();
        attacks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreatureType getCreatureType() {
        return creatureType;
    }

    public void setCreatureType(CreatureType creatureType) {
        this.creatureType = creatureType;
    }
}
