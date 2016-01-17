package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.graphics.RGBColor;
import com.inari.firefly.asset.AssetSystem;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.entity.EntitySystem;
import com.inari.firefly.graphics.TextureAsset;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.view.View;
import com.inari.firefly.system.view.ViewSystem;
import com.inari.firefly.task.Task;

public class LoadTask extends Task {
    
    public static final String VIEW_NAME = "BASE_GROUND_VIEW";

    protected LoadTask( int id ) {
        super( id );
    }

    @Override
    public void run( FFContext context ) {
        ViewSystem viewSystem = context.getSystem( ViewSystem.SYSTEM_KEY );
        AssetSystem assetSystem = context.getSystem( AssetSystem.SYSTEM_KEY );
        EntitySystem entitySystem = context.getSystem( EntitySystem.SYSTEM_KEY );
        
        viewSystem
            .getView( ViewSystem.BASE_VIEW_ID )
            .setClearColor( new RGBColor( .1f, .1f, .1f, 1  ) );
        viewSystem.getViewBuilder()
            .set( View.NAME, VIEW_NAME )
            .set( View.BOUNDS, new Rectangle(  4 * 16,  4 * 16, 2 * 21 * 16,  2 * 15 * 16 ) )
            .set( View.WORLD_POSITION, new Position( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( .8f, .8f, .8f, 1 ) )
            .set( View.ZOOM, 0.5f )
        .activate();
        
        assetSystem.getAssetBuilder()
            .set( TextureAsset.NAME, "baseGroundTexture" )
            .set( TextureAsset.RESOURCE_NAME, "assets/tiles/base_ground/testCave.png" )
        .activateAndNext( TextureAsset.class )
            .set( SpriteAsset.NAME, "baseGround1" )
            .set( SpriteAsset.TEXTURE_ASSET_ID, assetSystem.getAssetId( "baseGroundTexture" ) )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 0, 0, 16, 16 ) )
        .activate( SpriteAsset.class );
        
        entitySystem.getEntityBuilder()
            .set( ETransform.VIEW_ID, viewSystem.getViewId( VIEW_NAME ) )
            .set( ETransform.XPOSITION, 0 )
            .set( ETransform.YPOSITION, 0 )
            .set( ESprite.SPRITE_ID, assetSystem.getAsset( "baseGround1" ).getInstanceId() )
        .activate();
        
    }

}
