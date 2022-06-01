package com.mygdx.foxandhounds.logic;

import java.util.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.foxandhounds.FoxAndHounds;
import com.mygdx.foxandhounds.screens.ScreenManager.STATE;

public class GameLogicHandler {
    
    private Board board;
    private FoxAndHounds game;
    private Fox fox;
    private Vector<Hound> hounds;
    private Tile currentTile;
    public final Vector<Tile> tilesToMoveTo;
    private Pawn currentlySelectedPawn;
    private PawnType currentPlayer;
    private int highestHoundPos;

    public GameLogicHandler(){
        tilesToMoveTo = new Vector<>();
        currentPlayer = PawnType.FOX;
        highestHoundPos = 7;
    }

    public void setVariables(Board board, FoxAndHounds game, Fox fox, Vector<Hound> hounds){
        this.board = board;
        this.game = game;
        this.fox = fox;
        this.hounds = hounds;
    }


    public void findMovesFox(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);          
        }
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);           
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);           
        }
    }

    public void findMovesHound(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);    
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);     
        }
    }

    public void setBorders(){
        for(Tile tile : tilesToMoveTo){
            tile.setBorder(1);
        }
    }

    public void clearMoves(){
        tilesToMoveTo.clear();
    }

    public void movePawn(Tile prevTile, Tile newTile){
        clearMoves();
        currentlySelectedPawn.changePosition((int)newTile.getCoordinates().x, (int)newTile.getCoordinates().y);
        newTile.setPawn(currentlySelectedPawn);
        prevTile.setPawn(null);
        if(currentPlayer == PawnType.HOUND){
            findHighestHoundPos();
        }
        changePlayers();

        if(checkWinCondition()){
            finishGame();
        }
        clearMoves();
    }

    private void findHighestHoundPos(){
        int temp = -1;
        for(Hound hound : hounds) {
            if((int)hound.getCoordinates().y > temp){
                temp = (int)hound.getCoordinates().y;
            }
        }
        highestHoundPos = temp;
    }
    
    public void resetBoard(){
        Vector2 temp = new Vector2(0,0);
        board.getTile(fox.getCoordinates()).setPawn(null);
        fox.changePosition(0, 0);
        board.getTile(temp).setPawn(fox);
        for(int i = 0; i < 4; i++){
            temp.x = 1+2*i; temp.y = 7;
            board.getTile(hounds.get(i).getCoordinates()).setPawn(null);
            hounds.get(i).changePosition((int)temp.x, (int)temp.y);  
            board.getTile(temp).setPawn(hounds.get(i));
        }
        currentPlayer = PawnType.FOX;
        game.resetBoard = false;
        highestHoundPos = 7;
    }

    private boolean checkWinCondition(){
        Pawn tempPawn = currentlySelectedPawn;
        currentlySelectedPawn = fox;
        findMovesFox();
        if    (fox.getCoordinates().equals(new Vector2(1,7)) 
            || fox.getCoordinates().equals(new Vector2(3,7))
            || fox.getCoordinates().equals(new Vector2(5,7)) 
            || fox.getCoordinates().equals(new Vector2(7,7))
            || (int)fox.getCoordinates().y > highestHoundPos
            || tilesToMoveTo.isEmpty()){
            return true;
        }
        currentlySelectedPawn = tempPawn;
        return false;
    }

    private void finishGame(){
        if    (fox.getCoordinates().equals(new Vector2(1,7)) 
            || fox.getCoordinates().equals(new Vector2(3,7))
            || fox.getCoordinates().equals(new Vector2(5,7)) 
            || fox.getCoordinates().equals(new Vector2(7,7))
            || (int)fox.getCoordinates().y > highestHoundPos){
            game.winner = PawnType.FOX;
        }
        else{
            game.winner = PawnType.HOUND;
        }
        game.screenManager.setScreen(STATE.ENDGAME);
    }

    public void changePlayers(){
        if(currentPlayer == PawnType.FOX){
            currentPlayer = PawnType.HOUND;
        }
        else{
            currentPlayer = PawnType.FOX;
        }
    }

    public boolean checkValidMove(Tile tile){
        if(tilesToMoveTo.contains(tile)){
            return true;
        }
        return false;
    }
    
    public Tile getCurrentTile(){
        return currentTile;
    }

    public void setCurrentTile(Tile tile){
        currentTile = tile;
    }

    public PawnType getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPawn(Pawn pawn){
        currentlySelectedPawn = pawn;
    }
}
