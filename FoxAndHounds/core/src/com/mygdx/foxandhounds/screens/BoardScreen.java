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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class BoardScreen extends ApplicationAdapter implements InputProcessor, Screen {

    private final FoxAndHounds game;
    private Skin skin;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private HashMap<Vector2, Tile> boardTiles;
    private Fox fox;
    private Vector<Hound> hounds;
    private Vector2 lastClickTile;
    private Vector<Tile> tilesToMoveTo;
    private Pawn currentlySelectedPawn;
    private Vector<Player> players;
    private int currentPlayer;

    public BoardScreen(FoxAndHounds game){
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();

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
        fox = new Fox(0, 0);
        Vector2 temp = new Vector2();
        temp.x = 0; temp.y = 0;
        getTile(temp).setPawn(fox);
        Hound hound;
        hounds = new Vector<>();
        for(int i = 0; i < 4; i++){
            hound = new Hound(1+2*i, 7);
            temp.x = 1+2*i; temp.y = 7;
            getTile(temp).setPawn(hound);
            hounds.add(hound);
        }
        tilesToMoveTo = new Vector<>();
        players = new Vector<>();
        players.add(new Player(PawnType.FOX));
        players.add(new Player(PawnType.HOUND));
        currentPlayer = 0;
        game.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

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
        
        for(Map.Entry<Vector2, Tile> entry : boardTiles.entrySet()){
            entry.getValue().render(game.batch);
        }
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
            currentPlayer = 0;
        }
        if(keycode == Input.Keys.NUM_2){
            currentPlayer = 1;
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
        Vector2 temp = lastClickTile;
    
        Vector3 position = camera.unproject(new Vector3(screenX, screenY, 0));
        
        //System.out.println("Current player: " + players.get(currentPlayer).getType());
        lastClickTile = convertCoordinates(position);
        System.out.println("click: " + lastClickTile);
        Tile tile = getTile(lastClickTile);
        for(Tile tileToClear : tilesToMoveTo){
            tileToClear.setBorder(0);
        }
        if(tilesToMoveTo.contains(tile)){
            System.out.println("moving unit");
            tilesToMoveTo.clear();
            currentlySelectedPawn.changePosition((int)lastClickTile.x, (int)lastClickTile.y);
            getTile(temp).setPawn(null);
            getTile(lastClickTile).setPawn(currentlySelectedPawn);
        }
        else{
            tilesToMoveTo.clear();
            currentlySelectedPawn = tile.getPawn();
            
            if(currentlySelectedPawn != null && currentlySelectedPawn.getPawnType() == players.get(currentPlayer).getType()){
                System.out.println("Current pawn: " + currentlySelectedPawn.getPawnType() );
                if(currentlySelectedPawn.getPawnType() == PawnType.FOX){
                    findMovesFox(currentlySelectedPawn);
                }
                else{
                    findMovesHound(currentlySelectedPawn);
                }
            }
        }
        return true;
    }

    private void findMovesFox(Pawn pawn){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = pawn.getCoordinates().x-1; temp.y = pawn.getCoordinates().y-1;
        tile = getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = pawn.getCoordinates().x+1; temp.y = pawn.getCoordinates().y-1;
        tile = getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = pawn.getCoordinates().x-1; temp.y = pawn.getCoordinates().y+1;
        tile = getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = pawn.getCoordinates().x+1; temp.y = pawn.getCoordinates().y+1;
        tile = getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
    }

    private void findMovesHound(Pawn pawn){
        Vector2 temp = new Vector2();
        Tile tile;
        temp.x = pawn.getCoordinates().x-1; temp.y = pawn.getCoordinates().y-1;
        tile = getTile(temp);
        if(tile != null && tile.getPawn() == null){
            tilesToMoveTo.add(tile);
            tile.setBorder(1);
        }
        temp.x = pawn.getCoordinates().x+1; temp.y = pawn.getCoordinates().y-1;
        tile = getTile(temp);
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

    private Tile getTile(Vector2 coordinates){
        return boardTiles.get(coordinates);
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }
}