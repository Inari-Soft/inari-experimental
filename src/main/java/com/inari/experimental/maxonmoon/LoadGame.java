package com.inari.experimental.maxonmoon;

import com.badlogic.gdx.Input;
import com.inari.commons.geom.PositionF;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.graphics.RGBColor;
import com.inari.experimental.maxonmoon.TileLayerMapping.GameLayer;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.controller.view.SimpleCameraController;
import com.inari.firefly.graphics.TextureAsset;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.view.Layer;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;

public final class LoadGame extends Task {

    protected LoadGame( int id ) {
        super( id );
    }

    @Override
    public final void runTask() {
        createInput();
        createViews();
        loadAssets();
        
    }

    private void loadAssets() {
        context.getComponentBuilder( Asset.TYPE_KEY, TextureAsset.class )
            .set( TextureAsset.NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
            .set( TextureAsset.RESOURCE_NAME, "assets/maxonmoon/MaxOnMoonTiles.png" )
        .activate();
        
        for ( TileSetA tile : TileSetA.values() ) {
            context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
                .set( SpriteAsset.NAME, tile.name() )
                .set( SpriteAsset.TEXTURE_ASSET_NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
                .set( SpriteAsset.TEXTURE_REGION, new Rectangle( tile.textureXPos * 8, tile.textureYPos * 8, 8, 8 ) )
                .set( SpriteAsset.HORIZONTAL_FLIP, tile.flipHorizontal )
                .set( SpriteAsset.VERTICAL_FLIP, tile.flipVertical )
            .activate();
        }
        
        TileLayerMapping tileLayerMapping = new TileLayerMapping();
        tileLayerMapping.init( context );
        context.setProperty( Names.PROPERTIES.TILE_LAYER_MAPPING_KEY, tileLayerMapping );
        
        // Player Assets
        context.getComponentBuilder( Asset.TYPE_KEY, PlayerAsset.class )
            .activate();
        
        // activate the player: this may happen later when loading the room!?
        PlayerAsset playerAsset = context.getSystemComponent( Asset.TYPE_KEY, Names.PLAYER.NAME, PlayerAsset.class );
        playerAsset.activate( context );
        
        Room room0 = new Room0();
        room0.loadRoom( context );
    }

    private void createViews() {
        Integer _zoomFactor = context.getProperty( Names.PROPERTIES.ZOOM_FACTOR_TYPE_KEY );
        int zoomFactor = _zoomFactor != null? _zoomFactor.intValue() : 4;
        
        float zoom = 1f / zoomFactor;
        int one = 8 * zoomFactor;
        int width = 20 * one;
        int height = 15 * one;
        
        context.getComponentBuilder( Controller.TYPE_KEY, SimpleCameraController.class )
            .set( SimpleCameraController.NAME, Names.CONTROLLER.GAME_CAMERA_CONTROLLER )
        .build();
        
        context.getComponentBuilder( View.TYPE_KEY )
            .set( View.NAME, Names.VIEWS.GAME_STATUS_VIEW )
            .set( View.BOUNDS, new Rectangle( 0, 0, width, one ) )
            .set( View.WORLD_POSITION, new PositionF( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( 0f, 0f, 0f, 1f ) )
            .set( View.ZOOM, zoom )
        .activate();
        
        context.getComponentBuilder( View.TYPE_KEY )
            .set( View.NAME, Names.VIEWS.GAME_MAIN_VIEW )
            .set( View.BOUNDS, new Rectangle( 0, one, width, height ) )
            .set( View.WORLD_POSITION, new PositionF( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( .8f, .8f, .8f, 1 ) )
            .set( View.ZOOM, zoom )
            .set( View.LAYERING_ENABLED, true )
            .set( View.CONTROLLER_NAME, Names.CONTROLLER.GAME_CAMERA_CONTROLLER )
        .activate();
        
        for ( GameLayer layer : GameLayer.values() ) {
            context.getComponentBuilder( Layer.TYPE_KEY )
                .set( Layer.VIEW_NAME, Names.VIEWS.GAME_MAIN_VIEW )
                .set( Layer.NAME, layer.name() )
            .build();
        }
        
        context.getComponentBuilder( View.TYPE_KEY )
            .set( View.NAME, Names.VIEWS.GAME_INVENTORY_VIEW )
            .set( View.BOUNDS, new Rectangle( 0, 16 * one, width, one ) )
            .set( View.WORLD_POSITION, new PositionF( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( 0f, 0f, 0f, 0f ) )
            .set( View.ZOOM, zoom )
        .activate();
        
        
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

}
