package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.view.View;
import com.inari.firefly.task.Task;

public class PlayerHandle extends Task {
    
    public static final String PLAYER_NAME = "PLAYER_NAME";
    
    private int entityId;

    protected PlayerHandle( int id ) {
        super( id );
    }

    @Override
    public void run( FFContext context ) {
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, PLAYER_NAME )
            .set( SpriteAsset.TEXTURE_ASSET_ID, context.getTextureId( BaseGroundMapLoad.BASE_GROUND_TILE_TEXTURE_NAME ) )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 1 * 16, 3 * 16, 16, 16 ) )
        .activate( SpriteAsset.class );

        entityId = context.getEntityBuilder()
            .set( ETransform.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME ) )
            .set( ETransform.XPOSITION, 100 )
            .set( ETransform.YPOSITION, 100 )
            .set( ESprite.SPRITE_ID, context.getSpriteId( PLAYER_NAME ) )
            .set( EMovement.VELOCITY_Y, 1f )
            .set( EMovement.ACTIVE, true )
            .set( ECollision.BOUNDING, new Rectangle( 0, 0, 16, 16 ) )
        .activate();
    }

}
