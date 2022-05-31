package com.mygdx.foxandhounds.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pawn {
    private final PawnType pawnType;
    private final Sprite sprite;
    private final Vector2 coordinates;

    public Pawn(PawnType pt, int x, int y){
        pawnType = pt;
        if(pawnType == PawnType.HOUND){
            sprite = new Sprite( new Texture(Gdx.files.internal("textures/hound.png")));
        }
        else{
            sprite = new Sprite( new Texture(Gdx.files.internal("textures/fox.png")));
        }
        coordinates = new Vector2(x, y);
        //sprite.setOrigin(coordinates.x,coordinates.y);
        sprite.setOrigin(0,0);
        sprite.setPosition(x* Tile.TILE_SIZE, y*Tile.TILE_SIZE);
        
    }

    public PawnType getPawnType() {
        return pawnType;
    }

    public void render(SpriteBatch sb){
        sprite.draw(sb);
    }

    public void changePosition(int x, int y){
        sprite.setPosition(x*Tile.TILE_SIZE, y*Tile.TILE_SIZE);
        coordinates.x = x; coordinates.y = y;
    }

    public Vector2 getCoordinates(){
        return coordinates;
    }
}
