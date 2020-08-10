package com.degraffa.mcdnd.character;

import java.util.ArrayList;

// Super class for later classes
// Holds data common between all DnD characters
public class NPC {
    private String name;

    private CreatureType creatureType;

    private StatBlock statBlock;

    ArrayList<Feat> feats;
    ArrayList<Item> items;

    public NPC(String name, CreatureType creatureType, StatBlock statBlock) {
        this.name = name;

        this.creatureType = creatureType;
        this.statBlock = statBlock;

        feats = new ArrayList<>();
        items = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CreatureType getCreatureType() { return creatureType; }
    public void setCreatureType(CreatureType creatureType) { this.creatureType = creatureType; }

    public StatBlock getStatBlock() { return this.statBlock; }
    public void setStatBlock(StatBlock statBlock) { this.statBlock = statBlock; }

    public ArrayList<Feat> getFeats() { return this.feats; }
    public void addFeat(Feat feat) { this.feats.add(feat); }

    public ArrayList<Item> getItems() { return this.items; }
    public void additem(Item item) { this.items.add(item); }
}
