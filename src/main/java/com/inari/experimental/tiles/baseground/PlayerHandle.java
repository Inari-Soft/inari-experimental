package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PFCollisionResolver;
import com.inari.firefly.platformer.PFGravityController;
import com.inari.firefly.platformer.PFMoveController;
import com.inari.firefly.platformer.PFSimpleJumpController;
import com.inari.firefly.system.external.FFInput.ButtonType;

public class PlayerHandle extends Task {
    
    public static final String PLAYER_NAME = "PLAYER_NAME";
    private int playerId;

    protected PlayerHandle( int id ) {
        super( id );
    }

    @Override
    public void runTask() {
        context.getComponentBuilder( CollisionResolver.TYPE_KEY )
            .set( CollisionResolver.NAME, PLAYER_NAME )
        .build( PFCollisionResolver.class );
        
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, PLAYER_NAME )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, BaseGroundMapLoad.BASE_GROUND_TILE_TEXTURE_NAME )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 1 * 16, 3 * 16, 16, 16 ) )
        .activate( SpriteAsset.class );
        
        int playerMoveControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
            .set( PFMoveController.NAME, "SimplePlatformerMoveController" )
            .set( PFMoveController.GO_LEFT_BUTTON_TYPE, ButtonType.LEFT )
            .set( PFMoveController.GO_RIGHT_BUTTON_TYPE, ButtonType.RIGHT )
            .set( PFMoveController.EASING_TYPE, Easing.Type.LINEAR )
            .set( PFMoveController.MAX_VELOCITY, 1.5f )
            .set( PFMoveController.TIME_TO_MAX, 200 )
        .build( PFMoveController.class );
        
//        int playerControllerId = context.getComponentBuilder( Controller.TYPE_KEY )
//            .set( PFPlayerController.TILE_GRID_NAME, BaseGroundMapLoad.BASE_GROUND_TILE_GRID_NAME )
//        .build( PFPlayerController.class );
        
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
            .set( PFGravityController.MAX_VELOCITY, 8f )
            .set( PFGravityController.TIME_TO_MAX, 500 )
        .build( PFSimpleJumpController.class );

        playerId = context.getEntityBuilder()
            .add( EEntity.CONTROLLER_IDS, playerGravityControllerId )
            .add( EEntity.CONTROLLER_IDS, playerJumpControllerId )
            .add( EEntity.CONTROLLER_IDS, playerMoveControllerId )
//            .add( EEntity.CONTROLLER_IDS, playerControllerId )
            .set( ETransform.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME ) )
            .set( ETransform.XPOSITION, 100 )
            .set( ETransform.YPOSITION, 100 )
            .set( ESprite.SPRITE_ASSET_NAME, PLAYER_NAME )
            .set( EMovement.ACTIVE, true )
            .set( ECollision.COLLISION_BOUNDS, new Rectangle( 3, 0, 10, 16 ) )
            .set( ECollision.COLLISION_RESOLVER_ID, context.getSystemComponentId( CollisionResolver.TYPE_KEY, PLAYER_NAME ) )
        .activate();
    }
    
    public int getPlayerId() {
        return playerId;
    }

}
