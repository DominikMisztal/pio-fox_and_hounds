package com.mygdx.foxandhounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Tile {
    public final static int TILE_SIZE = 100;
    private Vector2 coordinates;
    private Pawn pawn;
    private Sprite sprite;
    private ShapeRenderer shapeRenderer;

    public Tile(int x, int y){
        // if((x+y)%2 == 0){
        //     sprite = new Sprite(new Texture(Gdx.files.internal("assets/textures/white.png")));
        //     //new Texture(Gdx.files.internal("textures/knight.png"));
        // }
        // else{
        //     sprite = new Sprite(new Texture(Gdx.files.internal("assets/textures/black.png")));
        // }

        // sprite.setPosition(x*64, y*64);
        coordinates = new Vector2(x,y);
        shapeRenderer = new ShapeRenderer();
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
