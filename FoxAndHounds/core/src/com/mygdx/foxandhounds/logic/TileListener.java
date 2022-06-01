package com.mygdx.foxandhounds.logic;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TileListener extends ClickListener {
    private final Tile tile;
    private final GameLogicHandler logicHandler;

    public TileListener(Tile tile, GameLogicHandler logicHandler){
        this.tile = tile;
        this.logicHandler = logicHandler;
    }

    @Override
    public void clicked(InputEvent event, float x, float y){
        Tile temp = logicHandler.getCurrentTile();
        logicHandler.setCurrentTile(tile);
        if(logicHandler.checkValidMove(tile)){
            logicHandler.movePawn(temp, tile);
            logicHandler.setCurrentPawn(null);
            return;
        }
        logicHandler.clearMoves();
        logicHandler.setCurrentPawn(null);
        if(tile.getPawn() != null){
            if(tile.getPawn().getPawnType() == logicHandler.getCurrentPlayer()){
                logicHandler.setCurrentPawn(tile.getPawn()); 
                if(tile.getPawn().getPawnType() == PawnType.FOX){
                    logicHandler.findMovesFox();
                }
                else{
                    logicHandler.findMovesHound();
                }
                logicHandler.setBorders();
            }
        }
    }
    
}
