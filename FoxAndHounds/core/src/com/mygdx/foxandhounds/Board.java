package com.mygdx.foxandhounds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class Board extends ApplicationAdapter implements InputProcessor {

    private HashMap<Vector2, Tile> boardTiles;
    private Fox fox;
    private Vector<Hound> hounds;
    private OrthographicCamera camera;
    private Vector2 lastClickTile;
    private Vector<Tile> tilesToMoveTo;
    private Pawn currentlySelectedPawn;
    private Vector<Player> players;
    private int currentPlayer;

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
        sb = new SpriteBatch();
        tilesToMoveTo = new Vector<>();
        players = new Vector<>();
        players.add(new Player(PawnType.FOX));
        players.add(new Player(PawnType.HOUND));
        currentPlayer = 0;
        sb.setProjectionMatrix(camera.combined);
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
        for(Tile tile : tilesToMoveTo){
            tile.renderBorder(sb);
        }
        sb.end();

    }

    @Override
    public boolean keyDown(int keycode) {
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
}
