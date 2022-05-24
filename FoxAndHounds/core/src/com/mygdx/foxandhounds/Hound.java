package com.mygdx.foxandhounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hound extends Pawn{
    private Sprite sprite;
    private Vector2 coordinates;

    public Hound(int x, int y){
        super(PawnType.HOUND);
        sprite = new Sprite( new Texture(Gdx.files.internal("textures/dog.png")));
        sprite.setScale((float)0.2);
        coordinates = new Vector2(x, y);
        sprite.setOriginCenter();
        sprite.setPosition(coordinates.x*Tile.TILE_SIZE + 50, coordinates.y*Tile.TILE_SIZE - 50);
        //sprite.setOrigin(coordinates.x*Tile.TILE_SIZE, coordinates.y*Tile.TILE_SIZE + 100);
    }

    public void render(SpriteBatch sb){
        sprite.draw(sb);
    }
}
