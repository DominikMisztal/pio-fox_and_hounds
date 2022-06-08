package com.mygdx.foxandhounds.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundController {
        private final Sound click;

        public SoundController(){
                click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        }

        public void playClick(){
                click.play();
        }
}
