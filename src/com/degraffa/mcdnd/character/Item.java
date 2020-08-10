package com.degraffa.mcdnd.character;

import java.util.ArrayList;

// It turns out that Items are pretty much what feats are

public class Item extends Feat {
    public Item(String name, String description) {
        super(name, description);
    }

    public Item(String name, String description, RechargeType rechargeType, int maxUses) {
        super(name, description, rechargeType, maxUses);
    }
}
