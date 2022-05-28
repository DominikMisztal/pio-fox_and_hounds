package com.mygdx.foxandhounds.logic;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.foxandhounds.screens.BoardScreen;

public class TileListener extends ClickListener {
    private Tile tile;
    private BoardScreen boardScreen;

    public TileListener(Tile tile, BoardScreen bs){
        boardScreen = bs;
        this.tile = tile;
    }

    @Override
    public void clicked(InputEvent event, float x, float y){
        Tile temp = boardScreen.getCurrentTile();
        boardScreen.setCurrentTile(tile);
        if(boardScreen.checkValidMove(tile)){
            boardScreen.movePawn(temp, tile);
            boardScreen.setCurrentPawn(null);
            return;
        }
        boardScreen.clearMoves();
        boardScreen.setCurrentPawn(null);
        if(tile.getPawn() != null){
            if(tile.getPawn().getPawnType() == boardScreen.getCurrentPlayer()){
                boardScreen.setCurrentPawn(tile.getPawn()); 
                if(tile.getPawn().getPawnType() == PawnType.FOX){
                    boardScreen.findMovesFox();
                }
                else{
                    boardScreen.findMovesHound();
                }
            }
        }
    }
    
}
