package com.mygdx.foxandhounds.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.foxandhounds.screens.BoardScreen;

public class Board extends Stage {
    private final HashMap<Vector2, Tile> boardTiles;

    public Board(BoardScreen bs){
        boardTiles = new HashMap<>();
        Tile tile;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tile = new Tile(j,i);
                boardTiles.put(new Vector2(j,i), tile);
                tile.setBounds(Tile.TILE_SIZE*j, Tile.TILE_SIZE *  i, Tile.TILE_SIZE, Tile.TILE_SIZE);
                addActor(tile);
                EventListener eventListener = new TileListener(tile, bs);
                tile.addListener(eventListener);
            }
        }
    }

    public void render(){
        for(Map.Entry<Vector2, Tile> entry : boardTiles.entrySet()){
            entry.getValue().render();
        }
    }

    public Tile getTile(Vector2 coordinates){
        return boardTiles.get(coordinates);
    }
    
}
