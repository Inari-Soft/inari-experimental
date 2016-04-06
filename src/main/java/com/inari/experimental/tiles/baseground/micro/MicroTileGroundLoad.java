package com.inari.experimental.tiles.baseground.micro;

import com.badlogic.gdx.Input;
import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.graphics.RGBColor;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.graphics.TextureAsset;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.graphics.view.ViewSystem;
import com.inari.firefly.prototype.Prototype;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;

public class MicroTileGroundLoad extends Task {
    
    public static final String NAME = "MICRO_TILE_GROUND";
    public static final String TASK_NAME = NAME + "_LOAD";
    public static final String TILE_GROUND_VIEW_NAME = NAME + "VIEW";
    public static final String SPRITE_ATLAS_NAME = "_SPRITE_ATLAS";

    protected MicroTileGroundLoad( int id ) {
        super( id );
    }

    @Override
    public void runTask() {
        createInput();
        createViews();
        loadGlobalAssetsAndPrototypes();
        
        String testCave = "00000000000000000000"
                         +"0..................0"
                         +"0..................0"
                         +"0..................0"
                         +"0..................0"
                         +"0..................0"
                         +"0..................0"
                         +"0......40000003....0"
                         +"00L00000000000003..0"
                         +"0.L...........2003.0"
                         +"0.L................0"
                         +"0.L.........000000.0"
                         +"0.L................0"
                         +"0.L................0"
                         +"0.L................0"
                         +"0.L...0000.........0"
                         +"0.L................0"
                         +"0.L..........00....0"
                         +"0.L................0"
                         +"00000000000000000000";
        
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( MicroTileCave.NAME, MicroTileCave.GRID_NAME )
            .set( MicroTileCave.MAP_WIDTH, 20 )
            .set( MicroTileCave.MAP_HEIGHT, 20 )
            .set( MicroTileCave.MAP_DATA, testCave )
        .activateAndNext( MicroTileCave.class )
            .set( Player.NAME, Player.PLAYER_NAME + "Asset" )
        .activate( Player.class );
        
        
    }
    
    private void createInput() {
        FFInput input = context.getInput();
        input.mapKeyInput( ButtonType.UP, Input.Keys.W );
        input.mapKeyInput( ButtonType.DOWN, Input.Keys.S );
        input.mapKeyInput( ButtonType.RIGHT, Input.Keys.D );
        input.mapKeyInput( ButtonType.LEFT, Input.Keys.A );
        input.mapKeyInput( ButtonType.FIRE_1, Input.Keys.SPACE );
        input.mapKeyInput( ButtonType.ENTER, Input.Keys.ENTER );
        input.mapKeyInput( ButtonType.QUIT, Input.Keys.ESCAPE );
    }
    
    private void createViews() {
        context.getSystem( ViewSystem.SYSTEM_KEY )
            .getView( ViewSystem.BASE_VIEW_ID )
            .setClearColor( new RGBColor( .1f, .1f, .1f, 1  ) );
        context.getComponentBuilder( View.TYPE_KEY )
            .set( View.NAME, TILE_GROUND_VIEW_NAME )
            .set( View.BOUNDS, new Rectangle( 100, 100, 600, 400 ) )
            .set( View.WORLD_POSITION, new Position( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( .8f, .8f, .8f, 1 ) )
            .set( View.ZOOM, 0.375f )
        .activate();
    }
    
    private void loadGlobalAssetsAndPrototypes() {
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( TextureAsset.NAME, SPRITE_ATLAS_NAME )
            .set( TextureAsset.RESOURCE_NAME, "assets/tiles/base_ground/microBase.png" )
        .activate( TextureAsset.class );
        
        for ( MicroTileType tileType : MicroTileType.values() ) {
            context.getComponentBuilder( Prototype.TYPE_KEY )
                .set( MicroTilePrototype.NAME, tileType.name() )
                .set( MicroTilePrototype.TEXTURE_ASSET_NAME, SPRITE_ATLAS_NAME )
                .set( MicroTilePrototype.TILE_TYPE, tileType )
            .activate( MicroTilePrototype.class );
        }
    }

}
