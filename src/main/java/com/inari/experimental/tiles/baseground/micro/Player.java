package com.inari.experimental.tiles.baseground.micro;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.PositionF;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.ContactConstraint;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PFCollisionResolver;
import com.inari.firefly.platformer.PFContact;
import com.inari.firefly.platformer.PFGravityController;
import com.inari.firefly.platformer.PFMoveController;
import com.inari.firefly.platformer.PFSimpleJumpController;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.utils.Disposable;

public final class Player extends Asset {
    
    public static final String PLAYER_NAME = "PLAYER";
    private int playerId;

    protected Player( int assetIntId ) {
        super( assetIntId );
    }
    
    @Override
    public final int getInstanceId( int index ) {
        return playerId;
    }

    @Override
    public final Disposable load( FFContext context ) {
        context.getComponentBuilder( CollisionResolver.TYPE_KEY, PFCollisionResolver.class )
            .set( CollisionResolver.NAME, PLAYER_NAME )
            .set( CollisionResolver.Y_AXIS_FIRST, true )
        .build(  );
        
        context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.NAME, PLAYER_NAME )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, MicroTileGroundLoad.SPRITE_ATLAS_NAME )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 6 * 8, 0, 8, 8 ) )
        .activate(  );

        playerId = context.getEntityBuilder()
            .add( 
                EEntity.CONTROLLER_IDS, 
                context.getComponentBuilder( Controller.TYPE_KEY, PFGravityController.class )
                    .set( PFGravityController.NAME, "PlatformerGravityController" )
                    .set( PFGravityController.MAX_VELOCITY, 4f )
                .build(  ) 
            )
            .add( 
                EEntity.CONTROLLER_IDS, 
                context.getComponentBuilder( Controller.TYPE_KEY, PFSimpleJumpController.class )
                    .set( PFSimpleJumpController.NAME, "SimplePlatformerJumpController" )
                    .set( PFSimpleJumpController.JUMP_BUTTON_TYPE, ButtonType.FIRE_1 )
                    .set( PFGravityController.MAX_VELOCITY, 6f )
                .build(  ) 
            )
            .add( 
                EEntity.CONTROLLER_IDS, 
                context.getComponentBuilder( Controller.TYPE_KEY, PFMoveController.class )
                    .set( PFMoveController.NAME, "SimplePlatformerMoveController" )
                    .set( PFMoveController.GO_LEFT_BUTTON_TYPE, ButtonType.LEFT )
                    .set( PFMoveController.GO_RIGHT_BUTTON_TYPE, ButtonType.RIGHT )
                    .set( PFMoveController.CLIMB_UP_BUTTON_TYPE, ButtonType.UP )
                    .set( PFMoveController.CLIMB_DOWN_BUTTON_TYPE, ButtonType.DOWN )
                    .set( PFMoveController.CLIMB_VELOCITY, 1 )
                    .set( PFMoveController.EASING_TYPE, Easing.Type.LINEAR )
                    .set( PFMoveController.MAX_VELOCITY, 1f )
                    .set( PFMoveController.TIME_TO_MAX, 200 )
                .build(  ) 
            )
            .set( ETransform.VIEW_NAME, MicroTileGroundLoad.TILE_GROUND_VIEW_NAME )
            .set( ETransform.POSITION, new PositionF( 50, 50 ) )
            .set( ESprite.SPRITE_ASSET_NAME, PLAYER_NAME )
            .set( EMovement.ACTIVE, true )
            //.add( EState.STATE_ASPECTS, PFState.FALLING.aspectId() )
            .set( ECollision.COLLISION_BOUNDS, new Rectangle( 0, 0, 8, 8 ) )
            .add( 
                ECollision.CONTACT_CONSTRAINTS, 
                new ContactConstraint( PFContact.PLATFORMER_SOLID_CONTACT_SCAN, new Rectangle( 0, 0, 8, 13 ) ) 
            )
            .add( 
                ECollision.CONTACT_CONSTRAINTS, 
                new ContactConstraint( PFContact.PLATFORMER_LADDER_CONTACT_SCAN, new Rectangle( 0, 0, 8, 8 ) )
                    .addToMaterialFilter( PFContact.LADDER ) 
            )
            //.set( ECollision.CONTACT_SCAN_BOUNDS, new Rectangle( 0, 0, 8, 13 ) )
            .set( ECollision.COLLISION_RESOLVER_NAME,PLAYER_NAME )
        .activate();
        
        return this;
    }

    @Override
    public final void dispose( FFContext context ) {
        // TODO Auto-generated method stub
        
    }

    

}
