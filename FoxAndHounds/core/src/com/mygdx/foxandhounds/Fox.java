package com.mygdx.foxandhounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Fox extends Pawn {
    private Sprite sprite;
    private Vector2 coordinates;

    public Fox(int x, int y){
        super(PawnType.FOX);
        sprite = new Sprite( new Texture(Gdx.files.internal("textures/fox.jpg")));
        sprite.setScale((float)0.1);
        coordinates = new Vector2(x, y);
        //sprite.setPosition(x*Tile.TILE_SIZE, y*Tile.TILE_SIZE);
        sprite.setOrigin(coordinates.x,coordinates.y);
    }

    public void render(SpriteBatch sb){
        sprite.draw(sb);
    }
}
