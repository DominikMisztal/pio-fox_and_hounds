package com.mygdx.foxandhounds.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor{
    public final static int TILE_SIZE = 100;
    private Vector2 coordinates;
    private Pawn pawn;
    private ShapeRenderer shapeRenderer;
    private Sprite tileBorder;

    public Tile(int x, int y){
        coordinates = new Vector2(x,y);
        shapeRenderer = new ShapeRenderer();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void render(){
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.identity();
        if( (int)(coordinates.x + coordinates.y) %2 == 1){
            shapeRenderer.setColor(Color.WHITE);
        }
        else{
            shapeRenderer.setColor(Color.BLACK);
        }
        shapeRenderer.rect(coordinates.x * TILE_SIZE, coordinates.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();
    }

    public void setBorder(int type){
        if(type == 0){
            tileBorder = null;
        }
        if(type == 1){
            tileBorder = new Sprite(new Texture(Gdx.files.internal("textures/GreenFrame.png")));
            tileBorder.setPosition(TILE_SIZE*coordinates.x, TILE_SIZE*coordinates.y);
        }
    }

    public void renderBorder(SpriteBatch sb){
        tileBorder.draw(sb);
    }

    public Vector2 getCoordinates(){
        return coordinates;
    }



    
}
