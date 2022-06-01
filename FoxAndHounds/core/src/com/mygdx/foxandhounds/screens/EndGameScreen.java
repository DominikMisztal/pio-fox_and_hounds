package com.mygdx.foxandhounds.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.foxandhounds.FoxAndHounds;
import com.mygdx.foxandhounds.logic.PawnType;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class EndGameScreen implements Screen, InputProcessor {

    final FoxAndHounds game;
    private Skin skin;
    private final Stage stage;
    private final ShapeRenderer shapeRenderer;
    String endText;

    TextButton playButton;
    TextButton exitButton;

    public EndGameScreen(final FoxAndHounds game) {
        this.game = game;
        this.stage = new Stage(new FillViewport(FoxAndHounds.WIDTH, FoxAndHounds.HEIGHT, game.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        if(game.winner == PawnType.FOX){
            endText = "Fox is the winner!";
        }
        else{
            endText = "Hound is the winner!";
        }
        this.skin = new Skin();
        this.skin.addRegions(game.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", game.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
    }

    private void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, endText, FoxAndHounds.WIDTH / 2f - 75, Gdx.graphics.getHeight() * .75f);
        game.batch.end();

        update(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
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

    private void initButtons(){
        playButton = new TextButton("Restart game", skin, "default");
        playButton.setSize(300,100);
        playButton.setPosition(FoxAndHounds.WIDTH / 2f - 150f,FoxAndHounds.HEIGHT/ 2f + 60);
        playButton.addAction(sequence(alpha(0), parallel(fadeIn(.5f),
                moveBy(0,-20,.5f, Interpolation.pow5Out))));
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.resetBoard = true;
                game.screenManager.setScreen(ScreenManager.STATE.PLAY);
            }
        });

        exitButton = new TextButton("Exit game", skin, "default");
        exitButton.setSize(300,100);
        exitButton.setPosition(FoxAndHounds.WIDTH / 2f - 150f,FoxAndHounds.HEIGHT/ 2f - 60);
        exitButton.addAction(sequence(alpha(0), parallel(fadeIn(.5f),
                moveBy(0,-20,.5f, Interpolation.pow5Out))));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(playButton);
        stage.addActor(exitButton);
    }


    //...Rest of class omitted for succinctness.

}