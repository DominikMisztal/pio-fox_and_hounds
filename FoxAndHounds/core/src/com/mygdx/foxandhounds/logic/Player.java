package com.mygdx.foxandhounds.logic;

public class Player {
    private final PawnType type;

    public Player(PawnType type){
        this.type = type;
    }

    public PawnType getType() {
        return type;
    }    
    
}
