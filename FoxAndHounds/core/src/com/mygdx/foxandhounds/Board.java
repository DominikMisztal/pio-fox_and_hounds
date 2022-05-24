package com.mygdx.foxandhounds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class Board extends ApplicationAdapter implements InputProcessor {

    private HashMap<Vector2, Tile> boardTiles;
    private Fox fox;
    private Vector<Hound> hounds;
    OrthographicCamera camera;

    SpriteBatch sb;

    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        boardTiles = new HashMap<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                boardTiles.put(new Vector2(i,j), new Tile(i,j));
            }
        }
        Gdx.input.setInputProcessor(this);
        fox = new Fox(0, 0);
        hounds = new Vector<>();
        for(int i = 0; i < 4; i++){
            hounds.add(new Hound(0+2*i, 7));
        }
        sb = new SpriteBatch();
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(0,0,255,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        for(Map.Entry<Vector2, Tile> entry : boardTiles.entrySet()){
            entry.getValue().render(sb);
        }
        sb.begin();
        fox.render(sb);
        for(Hound hound : hounds){
            hound.render(sb);
        }
        sb.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("X: {"+ screenX+"} Y: {"+ (Gdx.graphics.getHeight() -  screenY)+"}" );
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
