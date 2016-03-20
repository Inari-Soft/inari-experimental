package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.tile.TileGrid;
import com.inari.firefly.physics.collision.CollisionConstraint;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.DefaultCollisionConstraint;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PlatformerCollisionResolver;
import com.inari.firefly.platformer.PlatformerGravityController;
import com.inari.firefly.platformer.SimplePlatformerJumpController;
import com.inari.firefly.platformer.SimplePlatformerMoveController;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.view.View;
import com.inari.firefly.task.Task;

public class PlayerHandle extends Task {
    
    public static final String PLAYER_NAME = "PLAYER_NAME";
    
    private int playerId;

    protected PlayerHandle( int id ) {
        super( id );
    }

    @Override
    public void runTask() {
        context.getComponentBuilder( CollisionConstraint.TYPE_KEY )
            .set( CollisionConstraint.NAME, Run.BASE_NAME )
        .build( DefaultCollisionConstraint.class );
        context.getComponentBuilder( CollisionResolver.TYPE_KEY )
            .set( CollisionResolver.NAME, PLAYER_NAME )
        .build( PlatformerCollisionResolver.class );
        
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, PLAYER_NAME )
            .set( SpriteAsset.TEXTURE_ASSET_ID, context.getTextureId( BaseGroundMapLoad.BASE_GROUND_TILE_TEXTURE_NAME ) )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 1 * 16, 3 * 16, 16, 16 ) )
        .activate( SpriteAsset.class );
        
        int playerMoveControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( SimplePlatformerMoveController.NAME, "SimplePlatformerMoveController" )
            .set( SimplePlatformerMoveController.GO_LEFT_BUTTON_TYPE, ButtonType.LEFT )
            .set( SimplePlatformerMoveController.GO_RIGHT_BUTTON_TYPE, ButtonType.RIGHT )
            .set( SimplePlatformerMoveController.EASING_TYPE, Easing.Type.LINEAR )
            .set( SimplePlatformerMoveController.MAX_VELOCITY, 1.5f )
            .set( SimplePlatformerMoveController.TIME_TO_MAX, 200 )
            .set( 
                SimplePlatformerMoveController.TILE_GRID_ID, 
                context.getSystemComponentId( TileGrid.TYPE_KEY, BaseGroundMapLoad.BASE_GROUND_TILE_GRID_NAME ) 
            )
        .build( SimplePlatformerMoveController.class );
        
        int playerGravityControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( PlatformerGravityController.NAME, "PlatformerGravityController" )
            .set( PlatformerGravityController.EASING_TYPE, Easing.Type.EXPO_OUT )
            .set( PlatformerGravityController.MAX_VELOCITY, 5f )
            .set( PlatformerGravityController.TIME_TO_MAX, 500 )
        .build( PlatformerGravityController.class );
        
        int playerJumpControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( SimplePlatformerJumpController.NAME, "SimplePlatformerJumpController" )
            .set( SimplePlatformerJumpController.JUMP_BUTTON_TYPE, ButtonType.FIRE_1 )
            .set( PlatformerGravityController.EASING_TYPE, Easing.Type.LINEAR )
            .set( PlatformerGravityController.MAX_VELOCITY, 8f )
            .set( PlatformerGravityController.TIME_TO_MAX, 500 )
        .build( SimplePlatformerJumpController.class );

        playerId = context.getEntityBuilder()
            .add( EEntity.CONTROLLER_IDS, playerGravityControllerId )
            .add( EEntity.CONTROLLER_IDS, playerJumpControllerId )
            .add( EEntity.CONTROLLER_IDS, playerMoveControllerId )
            .set( ETransform.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME ) )
            .set( ETransform.XPOSITION, 100 )
            .set( ETransform.YPOSITION, 100 )
            .set( ESprite.SPRITE_ID, context.getSpriteId( PLAYER_NAME ) )
            .set( EMovement.ACTIVE, true )
            .set( ECollision.BOUNDING, new Rectangle( 3, 0, 10, 16 ) )
            .set( ECollision.COLLISION_CONSTRAINT_ID, context.getSystemComponentId( CollisionConstraint.TYPE_KEY, Run.BASE_NAME ) )
            .set( ECollision.COLLISION_RESOLVER_ID, context.getSystemComponentId( CollisionResolver.TYPE_KEY, PLAYER_NAME ) )
        .activate();
    }
    
    public int getPlayerId() {
        return playerId;
    }

}
