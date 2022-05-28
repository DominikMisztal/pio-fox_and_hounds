package com.mygdx.foxandhounds.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private Vector<Player> players;
    private PawnType currentPlayer;

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
        players = new Vector<>();
        players.add(new Player(PawnType.FOX));
        players.add(new Player(PawnType.HOUND));
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
        // Vector2 temp = lastClickTile;
    
        // Vector3 position = camera.unproject(new Vector3(screenX, screenY, 0));
        
        // //System.out.println("Current player: " + players.get(currentPlayer).getType());
        // lastClickTile = convertCoordinates(position);
        // System.out.println("click: " + lastClickTile);
        // Tile tile = board.getTile(lastClickTile);
        // for(Tile tileToClear : tilesToMoveTo){
        //     tileToClear.setBorder(0);
        // }
        // if(tilesToMoveTo.contains(tile)){
        //     System.out.println("moving unit");
        //     tilesToMoveTo.clear();
        //     currentlySelectedPawn.changePosition((int)lastClickTile.x, (int)lastClickTile.y);
        //     board.getTile(temp).setPawn(null);
        //     board.getTile(lastClickTile).setPawn(currentlySelectedPawn);
        // }
        // else{
        //     tilesToMoveTo.clear();
        //     currentlySelectedPawn = tile.getPawn();
            
        //     if(currentlySelectedPawn != null && currentlySelectedPawn.getPawnType() == players.get(currentPlayer).getType()){
        //         System.out.println("Current pawn: " + currentlySelectedPawn.getPawnType() );
        //         if(currentlySelectedPawn.getPawnType() == PawnType.FOX){
        //             findMovesFox(currentlySelectedPawn);
        //         }
        //         else{
        //             findMovesHound(currentlySelectedPawn);
        //         }
        //     }
        // }
        // return true;
        return false;
    }

    public void findMovesFox(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y+1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
    }

    public void findMovesHound(){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = currentlySelectedPawn.getCoordinates().x-1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = currentlySelectedPawn.getCoordinates().x+1; temp.y = currentlySelectedPawn.getCoordinates().y-1;
        tile = board.getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
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

    private Vector2 convertCoordinates(Vector3 position){
        return new Vector2( (float)Math.floor(position.x / Tile.TILE_SIZE),  (float)Math.floor(position.y / Tile.TILE_SIZE));
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }
}
