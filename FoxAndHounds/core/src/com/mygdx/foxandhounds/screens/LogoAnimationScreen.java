package com.mygdx.foxandhounds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.foxandhounds.FoxAndHounds;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class LogoAnimationScreen implements Screen {
    private FoxAndHounds game;
    private Stage stage;
    private Image image;

    public LogoAnimationScreen(final FoxAndHounds game){
        this.game = game;
        this.stage = new Stage(new FitViewport(FoxAndHounds.WIDTH, FoxAndHounds.HEIGHT, game.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable setMenuScreen = new Runnable() {
            @Override
            public void run() {
                game.screenManager.setScreen(ScreenManager.STATE.MAIN_MENU);
            }
        };

        Texture texture = new Texture(Gdx.files.internal("textures/fox.png"));
        image = new Image(texture);
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        image.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() - 50);
        image.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f,1f,2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - 50, stage.getHeight() / 2 - 50, 2f, Interpolation.swing)),
                delay(0.25f), fadeOut(0.25f), run(setMenuScreen)));

        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f,.25f,.25f,0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta){
        if(stage.getActors().size == 0){
            game.screenManager.setScreen(ScreenManager.STATE.MAIN_MENU);
        }
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
    }
}
