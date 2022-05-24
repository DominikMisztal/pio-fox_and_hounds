package com.mygdx.foxandhounds;

public class Pawn {
    private PawnType pawnType;

    public Pawn(PawnType pt){
        pawnType = pt;
    }

    public PawnType getPawnType() {
        return pawnType;
    }
}
