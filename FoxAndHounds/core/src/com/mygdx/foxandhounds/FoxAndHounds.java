package com.mygdx.foxandhounds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.foxandhounds.screens.ScreenManager;

public class FoxAndHounds extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public SpriteBatch batch;
    public BitmapFont font;
    public ScreenManager screenManager;
    public OrthographicCamera camera;
    public AssetManager assets;

    public void create(){
        assets = new AssetManager();
        batch = new SpriteBatch();
        initFonts();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        screenManager = new ScreenManager(this);
    }
    public void render(){
        super.render();
    }
    public void dispose(){
        super.dispose();
        batch.dispose();
        font.dispose();
        assets.dispose();
        screenManager.dispose();
    }

    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 24;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
    }

}
