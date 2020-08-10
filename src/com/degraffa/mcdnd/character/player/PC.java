package com.degraffa.mcdnd.character.player;

import com.degraffa.mcdnd.character.CreatureType;
import com.degraffa.mcdnd.character.Item;
import com.degraffa.mcdnd.character.NPC;
import com.degraffa.mcdnd.character.StatBlock;

import java.util.ArrayList;

public class PC extends NPC {
    private ArrayList<Item> attunementSlots;
    private int maxAttunements;



    public PC(String name, CreatureType creatureType, StatBlock statBlock) {
        super(name, creatureType, statBlock);
    }


}
