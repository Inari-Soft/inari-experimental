package com.inari.experimental.tiles.baseground.micro;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.physics.collision.CollisionConstraint;
import com.inari.firefly.physics.collision.CollisionConstraintImpl;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PFCollisionResolver;
import com.inari.firefly.platformer.PFGravityController;
import com.inari.firefly.platformer.PFMoveController;
import com.inari.firefly.platformer.PFPlayerCollisionConstraint;
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
        context.getComponentBuilder( CollisionConstraint.TYPE_KEY )
            .set( CollisionConstraint.NAME, PLAYER_NAME + "_DEFAULT_CONSTRAINT" )
        .buildAndNext( CollisionConstraintImpl.class )
            .set( CollisionConstraint.NAME, PLAYER_NAME )
            .set( PFPlayerCollisionConstraint.DELEGATE_CONSTRAINT_NAME, PLAYER_NAME + "_DEFAULT_CONSTRAINT" )
        .build( PFPlayerCollisionConstraint.class );
        context.getComponentBuilder( CollisionResolver.TYPE_KEY )
            .set( CollisionResolver.NAME, PLAYER_NAME )
            .set( CollisionResolver.Y_AXIS_FIRST, true )
        .build( PFCollisionResolver.class );
        
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, PLAYER_NAME )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, MicroTileGroundLoad.SPRITE_ATLAS_NAME )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 6 * 8, 0, 8, 8 ) )
        .activate( SpriteAsset.class );
        
        int playerMoveControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( PFMoveController.NAME, "SimplePlatformerMoveController" )
            .set( PFMoveController.GO_LEFT_BUTTON_TYPE, ButtonType.LEFT )
            .set( PFMoveController.GO_RIGHT_BUTTON_TYPE, ButtonType.RIGHT )
            .set( PFMoveController.EASING_TYPE, Easing.Type.LINEAR )
            .set( PFMoveController.MAX_VELOCITY, 2f )
            .set( PFMoveController.TIME_TO_MAX, 200 )
        .build( PFMoveController.class );

        int playerGravityControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( PFGravityController.NAME, "PlatformerGravityController" )
            .set( PFGravityController.EASING_TYPE, Easing.Type.EXPO_OUT )
            .set( PFGravityController.MAX_VELOCITY, 5f )
            .set( PFGravityController.TIME_TO_MAX, 500 )
        .build( PFGravityController.class );
        
        int playerJumpControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( PFSimpleJumpController.NAME, "SimplePlatformerJumpController" )
            .set( PFSimpleJumpController.JUMP_BUTTON_TYPE, ButtonType.FIRE_1 )
            .set( PFGravityController.EASING_TYPE, Easing.Type.LINEAR )
            .set( PFGravityController.MAX_VELOCITY, 6f )
            .set( PFGravityController.TIME_TO_MAX, 500 )
        .build( PFSimpleJumpController.class );

        playerId = context.getEntityBuilder()
            .add( EEntity.CONTROLLER_IDS, playerGravityControllerId )
            .add( EEntity.CONTROLLER_IDS, playerJumpControllerId )
            .add( EEntity.CONTROLLER_IDS, playerMoveControllerId )
            .set( ETransform.VIEW_NAME, MicroTileGroundLoad.TILE_GROUND_VIEW_NAME )
            .set( ETransform.XPOSITION, 50 )
            .set( ETransform.YPOSITION, 50 )
            .set( ESprite.SPRITE_ASSET_NAME, PLAYER_NAME )
            .set( EMovement.ACTIVE, true )
            //.add( EState.STATE_ASPECTS, PFState.FALLING.aspectId() )
            .set( ECollision.BOUNDING, new Rectangle( 0, 0, 8, 8 ) )
            .add( ECollision.COLLISION_LAYER_IDS, 0 )
            .set( ECollision.COLLISION_CONSTRAINT_NAME, PLAYER_NAME )
            .set( ECollision.COLLISION_RESOLVER_NAME,PLAYER_NAME )
        .activate();
        
        return this;
    }

    @Override
    public final void dispose( FFContext context ) {
        // TODO Auto-generated method stub
        
    }

    

}
