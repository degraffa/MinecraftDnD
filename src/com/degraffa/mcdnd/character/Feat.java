package com.degraffa.mcdnd.character;

import java.util.ArrayList;

public class Feat {
    private String name;
    private String description;

    private RechargeType rechargeType;

    private int maxUses;
    private int currentUses;

    private ArrayList<Effect> effects;

    public Feat(String name, String description) {
        setupFeat(name, description, RechargeType.Unlimited, 0, new ArrayList<>());
    }

    public Feat(String name, String description, RechargeType rechargeType, int maxUses) {
        setupFeat(name, description, rechargeType, maxUses, new ArrayList<>());
    }

    private void setupFeat(String name, String description, RechargeType rechargeType, int maxUses, ArrayList<Effect> effects) {
        this.name = name;
        this.description = description;
        this.rechargeType = rechargeType;
        this.maxUses = maxUses;
        this.currentUses = maxUses;
        this.effects = effects;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }

    public ArrayList<Effect> getEffects() { return this.effects; }
    public void addEffect(Effect effect) { this.effects.add(effect); }
}
