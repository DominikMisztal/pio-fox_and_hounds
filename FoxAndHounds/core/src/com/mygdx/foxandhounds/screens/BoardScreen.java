package com.mygdx.foxandhounds.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.foxandhounds.FoxAndHounds;
import com.mygdx.foxandhounds.logic.*;

import java.util.Vector;



public class BoardScreen extends ApplicationAdapter implements InputProcessor, Screen {

    private Board board;
    private final FoxAndHounds game;
    private Skin skin;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Fox fox;
    private Vector<Hound> hounds;
    private Tile currentTile;
    private Vector<Tile> tilesToMoveTo;
    private Pawn currentlySelectedPawn;
    private PawnType currentPlayer;

    

    public BoardScreen(FoxAndHounds game){
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        board = new Board(this);
        board.getViewport().setCamera(camera);
        fox = new Fox(0, 0);
        Vector2 temp = new Vector2();
        temp.x = 0; temp.y = 0;
        board.getTile(temp).setPawn(fox);
        Hound hound;
        hounds = new Vector<>();
        for(int i = 0; i < 4; i++){
            hound = new Hound(1+2*i, 7);
            temp.x = 1+2*i; temp.y = 7;
            board.getTile(temp).setPawn(hound);
            hounds.add(hound);
        }
        tilesToMoveTo = new Vector<>();
        currentPlayer = PawnType.FOX;
        game.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(board);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.skin = new Skin();
        this.skin.addRegions(game.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", game.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));
    }

    @Override
    public void create () {
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,255,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        board.render();
        game.batch.begin();
        
        fox.render(game.batch);
        for(Hound hound : hounds){
            hound.render(game.batch);
        }
        for(Tile tile : tilesToMoveTo){
            tile.renderBorder(game.batch);
        }
        game.batch.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.T){
            game.screenManager.setScreen(ScreenManager.STATE.ENDGAME);
        }
        if(keycode == Input.Keys.NUM_1){
            currentPlayer = PawnType.FOX;
        }
        if(keycode == Input.Keys.NUM_2){
            currentPlayer = PawnType.HOUND;
        }
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

    public void findMovesFox(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);          
        }
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);           
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);           
        }
    }

    public void findMovesHound(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);    
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);     
        }
    }

    public void setBorders(){
        for(Tile tile : tilesToMoveTo){
            tile.setBorder(1);
        }
    }

    @Override
    public void hide() {

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

    public void clearMoves(){
        tilesToMoveTo.clear();
    }

    public void movePawn(Tile prevTile, Tile newTile){
        clearMoves();
        System.out.println("moving from: " + prevTile.getCoordinates());
        System.out.println("moving to: " + newTile.getCoordinates());
        currentlySelectedPawn.changePosition((int)newTile.getCoordinates().x, (int)newTile.getCoordinates().y);
        newTile.setPawn(currentlySelectedPawn);
        prevTile.setPawn(null);
        changePlayers();
        checkWinCondition();
        clearMoves();
        
    }
    
    public void resetBoard(){
        Vector2 temp = new Vector2(0,0);
        board.getTile(fox.getCoordinates()).setPawn(null);
        fox.changePosition(0, 0);
        board.getTile(temp).setPawn(fox);;
        for(int i = 0; i < 4; i++){
            temp.x = 1+2*i; temp.y = 7;
            board.getTile(hounds.get(i).getCoordinates()).setPawn(null);
            hounds.get(i).changePosition((int)temp.x, (int)temp.y);  
            board.getTile(temp).setPawn(hounds.get(i));
        }
        currentPlayer = PawnType.FOX;
    }

    public void checkWinCondition(){
        Vector2 temp = fox.getCoordinates();
        findMovesFox();
        if(temp.equals(new Vector2(1,7)) || temp.equals(new Vector2(3,7))
            || temp.equals(new Vector2(5,7)) || temp.equals(new Vector2(7,7))){
            game.winner = PawnType.FOX;
            game.screenManager.setScreen(ScreenManager.STATE.ENDGAME);
            resetBoard();
        }
        else if(tilesToMoveTo.isEmpty()){
            game.winner  = PawnType.HOUND;
            game.screenManager.setScreen(ScreenManager.STATE.ENDGAME);
            resetBoard();
        }
    }

    public void changePlayers(){
        if(currentPlayer == PawnType.FOX){
            currentPlayer = PawnType.HOUND;
        }
        else{
            currentPlayer = PawnType.FOX;
        }
    }

    public boolean checkValidMove(Tile tile){
        if(tilesToMoveTo.contains(tile)){
            return true;
        }
        return false;
    }
    public Tile getCurrentTile(){
        return currentTile;
    }

    public void setCurrentTile(Tile tile){
        currentTile = tile;
    }

    public PawnType getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPawn(Pawn pawn){
        currentlySelectedPawn = pawn;
    }

    public Pawn getCurrentPawn(){
        return currentlySelectedPawn;
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }
}
