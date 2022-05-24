package com.mygdx.foxandhounds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Tile {
    public final static int TILE_SIZE = 100;
    private Vector2 coordinates;
    private Pawn pawn;
    private ShapeRenderer shapeRenderer;

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

    public void render(SpriteBatch sb){
        //sprite.draw(sb);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.identity();
        if( (int)(coordinates.x + coordinates.y) %2 == 1){
            shapeRenderer.setColor(Color.BLACK);
        }
        else{
            shapeRenderer.setColor(Color.WHITE);
        }
        //shapeRenderer.translate(coordinates.x * TILE_SIZE, coordinates.y * TILE_SIZE, 0);
        shapeRenderer.rect(coordinates.x * TILE_SIZE, coordinates.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();
    }

    
}
