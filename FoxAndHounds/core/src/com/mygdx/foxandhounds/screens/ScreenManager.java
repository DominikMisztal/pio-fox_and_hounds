package com.mygdx.foxandhounds.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.foxandhounds.FoxAndHounds;

import java.util.HashMap;

public class ScreenManager {
    private final FoxAndHounds game;
    private HashMap<STATE, Screen> gameScreens;

    public enum STATE{
        LOADING,
        ANIMATION,
        MAIN_MENU,
        PLAY,
        ENDGAME
    }

    public ScreenManager(final FoxAndHounds game){
        this.game = game;
        initGameScreens();
        setScreen(STATE.LOADING);
    }

    private void initGameScreens(){
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(STATE.LOADING, new LoadingScreen(game));
        this.gameScreens.put(STATE.ANIMATION, new LogoAnimationScreen(game));
        this.gameScreens.put(STATE.MAIN_MENU, new MainMenuScreen(game));
        this.gameScreens.put(STATE.PLAY, new BoardScreen(game));
        this.gameScreens.put(STATE.ENDGAME, new EndGameScreen(game));
    }

    public void setScreen(STATE nextScreen){
        game.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose(){
        for(Screen screen : gameScreens.values()){
            if(screen != null){
                screen.dispose();
            }
        }
    }

}
