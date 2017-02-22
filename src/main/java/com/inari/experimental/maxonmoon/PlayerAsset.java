package com.inari.experimental.maxonmoon;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.PositionF;
import com.inari.commons.geom.Rectangle;
import com.inari.experimental.maxonmoon.TileLayerMapping.GameLayer;
import com.inari.firefly.animation.timeline.IntTimelineAnimation;
import com.inari.firefly.animation.timeline.IntTimelineData;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.control.Controller;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.entity.EntityController;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.sprite.ESprite;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.physics.animation.Animation;
import com.inari.firefly.physics.animation.AnimationSystem;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.ContactConstraint;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PFGravityController;
import com.inari.firefly.platformer.PFMoveController;
import com.inari.firefly.platformer.PFSimpleJumpController;
import com.inari.firefly.platformer.PFState;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.utils.Disposable;

public final class PlayerAsset extends Asset {
    
    private int entityId;
    private int gravityControllerId;
    private static int animationId;
    
    // 0 : idle sprite right
    // 1 : idle sprite left
    // 2 : right animation 0
    // 3 : right animation 1
    // 4 : left animation 0
    // 5 : left animation 1
    private final static int[] spriteIds = new int[ 6 ];

    protected PlayerAsset( int assetIntId ) {
        super( assetIntId );
        super.setName( Names.PLAYER.NAME );
    }

    @Override
    public final Disposable load( FFContext context ) {
        context.getComponentBuilder( CollisionResolver.TYPE_KEY, PlayerCollisionResolver.class )
            .set( CollisionResolver.NAME, Names.PLAYER.COLLISION_RESOLVER )
        .build(  );
        
        animationId = context.getComponentBuilder( Animation.TYPE_KEY, IntTimelineAnimation.class)
            .set( IntTimelineAnimation.LOOPING, true )
            .add( IntTimelineAnimation.TIMELINE, new IntTimelineData( 0, 100 ) )
            .add( IntTimelineAnimation.TIMELINE, new IntTimelineData( 1, 100 ) )
        .build(  );
        
        spriteIds[ 0 ] = context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 0, 19 * 8, 8, 8 ) )
        .activate(  );
        spriteIds[ 2 ] = spriteIds[ 0 ];
        spriteIds[ 1 ] = context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 0, 19 * 8, 8, 8 ) )
            .set( SpriteAsset.VERTICAL_FLIP, true )
        .activate(  );
        spriteIds[ 4 ] = spriteIds[ 1 ];
        spriteIds[ 3 ] = context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 8, 19 * 8, 8, 8 ) )
        .activate(  );
        spriteIds[ 5 ] = context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, Names.TEXTURES.GAME_TILE_TEXTURE )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( 8, 19 * 8, 8, 8 ) )
            .set( SpriteAsset.VERTICAL_FLIP, true )
        .activate(  );
        // convert sprite asset ids to sprite ids
        for ( int i = 0; i < spriteIds.length; i++ ) {
            spriteIds[ i ] = context.getAssetInstanceId( spriteIds[ i ] );
        }
        
        gravityControllerId = context.getComponentBuilder( Controller.TYPE_KEY, PFGravityController.class )
            .set( PFGravityController.NAME, "PlatformerGravityController" )
            .set( PFGravityController.MAX_VELOCITY, 3f )
        .build(  );
        
        entityId = context.getEntityBuilder()
            .set( EEntity.ENTITY_NAME, Names.PLAYER.NAME )
            .add( EEntity.CONTROLLER_IDS, gravityControllerId )
            .add( 
                EEntity.CONTROLLER_IDS, 
                context.getComponentBuilder( Controller.TYPE_KEY, PFSimpleJumpController.class )
                    .set( PFSimpleJumpController.NAME, "SimplePlatformerJumpController" )
                    .set( PFSimpleJumpController.JUMP_BUTTON_TYPE, ButtonType.FIRE_1 )
                    .set( PFSimpleJumpController.MAX_VELOCITY, 2f )
                    .set( PFSimpleJumpController.TIME_TO_MAX, 100 )
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
                    .set( PFMoveController.MAX_VELOCITY, 0.5f )
                    .set( PFMoveController.TIME_TO_MAX, 100 )
                .build(  ) 
            )
            .add( 
                EEntity.CONTROLLER_IDS,
                context.getComponentBuilder( PlayerAnimationController.TYPE_KEY, PlayerAnimationController.class )
                    .build(  )
            )
            .set( ETransform.VIEW_NAME, Names.VIEWS.GAME_MAIN_VIEW )
            .set( ETransform.LAYER_NAME, GameLayer.MAIN.name() )
            .set( ETransform.POSITION, new PositionF( 50, 50 ) )
            .set( ESprite.SPRITE_ID, spriteIds[ 0 ] )
            .set( EMovement.ACTIVE, true )
            .set( ECollision.COLLISION_BOUNDS, new Rectangle( 1, 0, 6, 8 ) )
            .add( 
                ECollision.CONTACT_CONSTRAINTS, 
                new ContactConstraint( Names.PLAYER.BODY_CONTACT_SCAN, new Rectangle( 1, 0, 6, 8 ) ) 
                    .addToMaterialFilter( TileMaterialType.SOLID )
            )
            .add( 
                ECollision.CONTACT_CONSTRAINTS, 
                new ContactConstraint( Names.PLAYER.GROUND_CONTACT_SCAN, new Rectangle( 1, 8, 6, 1 ) ) 
                    .addToMaterialFilter( TileMaterialType.SOLID )
            )
            .add( 
                ECollision.CONTACT_CONSTRAINTS,
                new ContactConstraint( Names.PLAYER.GROUND_CONTACT_SCAN_SEMI_SOLID, new Rectangle( 1, 7, 6, 2 ) )
                    .addToMaterialFilter( TileMaterialType.SEMI_SOLID_VERTICAL_DOWN )
             )
            .set( ECollision.COLLISION_RESOLVER_NAME, Names.PLAYER.COLLISION_RESOLVER )
        . build();
        
        return this;
    }
    
    public final void activate( FFContext context ) {
        context.activateEntity( entityId );
        context.activateSystemComponent( Animation.TYPE_KEY, animationId );
    }
    
    public final void deactivate( FFContext context ) {
        context.deactivateEntity( entityId );
        context.deactivateSystemComponent( Animation.TYPE_KEY, animationId );
    }

    @Override
    public final void dispose( FFContext context ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public final int getInstanceId( int index ) {
        return entityId;
    }
    
    public static final class PlayerAnimationController extends EntityController {

        protected PlayerAnimationController( int id ) {
            super( id );
        }

        @Override
        protected final void update( int entityId ) {
            final EEntity entity = context.getEntityComponent( entityId, EEntity.TYPE_KEY );
            final ESprite sprite = context.getEntityComponent( entityId, ESprite.TYPE_KEY );
            final AnimationSystem animationSystem = context.getSystem( AnimationSystem.SYSTEM_KEY );
            
            final int currentSpriteId = sprite.getSpriteId();
            final int walkAnimValue = animationSystem.getValue( animationId, entityId, currentSpriteId );
            
            if ( entity.hasAspect( PFState.WALK_RIGHT ) ) {
                sprite.setSpriteId( spriteIds[ walkAnimValue + 2 ] );
            } else if ( entity.hasAspect( PFState.WALK_LEFT ) ) {
                sprite.setSpriteId( spriteIds[ walkAnimValue + 4 ] );
            } else {
                if ( currentSpriteId == spriteIds[ 2 ] || currentSpriteId == spriteIds[ 3 ] ) {
                    sprite.setSpriteId( spriteIds[ 0 ] );
                } else {
                    sprite.setSpriteId( spriteIds[ 1 ] );
                }
            }
        }
    }

}
