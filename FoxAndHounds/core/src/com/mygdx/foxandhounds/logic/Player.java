package com.mygdx.foxandhounds.logic;

import com.mygdx.foxandhounds.logic.PawnType;

public class Player {
    private PawnType type;

    public Player(PawnType type){
        this.type = type;
    }

    public PawnType getType() {
        return type;
    }    
    
}
