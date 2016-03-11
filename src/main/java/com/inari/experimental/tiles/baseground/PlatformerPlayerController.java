package com.inari.experimental.tiles.baseground;

import java.util.BitSet;

import com.inari.commons.GeomUtils;
import com.inari.commons.StringUtils;
import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.lang.indexed.Indexed;
import com.inari.firefly.animation.Animation;
import com.inari.firefly.animation.AnimationSystem;
import com.inari.firefly.animation.easing.EasingAnimation;
import com.inari.firefly.animation.easing.EasingData;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.entity.EntityController;
import com.inari.firefly.entity.EntitySystem;
import com.inari.firefly.graphics.tile.TileGrid.TileIterator;
import com.inari.firefly.graphics.tile.TileGridSystem;
import com.inari.firefly.physics.collision.BitMask;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.external.FFTimer;

public final class PlatformerPlayerController extends EntityController {
    
    public enum States implements Indexed {
        CONTACT_NORTH,
        CONTACT_EAST,
        CONTACT_SOUTH,
        CONTACT_WEST,
        CONTACT_SLOPE
        ;

        @Override
        public final int index() {
            return ordinal();
        }
    }
    
    private final AnimationSystem animationSystem;
    private final TileGridSystem tileGridSystem;
    private final CollisionSystem collisionSystem;
    private final EntitySystem entitySystem;
    
    private int tileGridId = 0;
    
    private ButtonType goLeft = ButtonType.LEFT;
    private ButtonType goRight = ButtonType.RIGHT;
    private ButtonType jump = ButtonType.FIRE_1;
    
    private float walkVelocity = 1.5f;
    private long walkStartTime = 200;

    private int gravityAnimId;
    private int startWalkAnimId;
    private int jumpAnimId;

//    private float gravity = 0f;
//    private float walk = 0f;
    
    private final Position slopePivot = new Position();
    //private final Position playerFutureWorldPos = new Position();
    private final Rectangle groundScanBounds = new Rectangle( 0, 0, 1, 8 );
    private final BitSet groundScanBits = new BitSet( 8 );

//    private final Position tmpPos = new Position();
//    private final Position slopePivot = new Position();
//    private boolean slopePivotInit = false;

    protected PlatformerPlayerController( int id, FFContext context ) {
        super( id, context );
        animationSystem = context.getSystem( AnimationSystem.SYSTEM_KEY );
        tileGridSystem = context.getSystem( TileGridSystem.SYSTEM_KEY );
        collisionSystem = context.getSystem( CollisionSystem.SYSTEM_KEY );
        entitySystem = context.getSystem( EntitySystem.SYSTEM_KEY );
        
        gravityAnimId = context.getComponentBuilder( Animation.TYPE_KEY )
            .set( EasingAnimation.NAME, "gravitiyAnimation" )
            .set( EasingAnimation.LOOPING, false )
            .set( EasingAnimation.EASING_DATA, new EasingData( Easing.Type.EXPO_OUT, 0f, 5f, 500 ) )
        .build( EasingAnimation.class );
        
        startWalkAnimId = context.getComponentBuilder( Animation.TYPE_KEY )
            .set( EasingAnimation.NAME, "gravitiyAnimation" )
            .set( EasingAnimation.LOOPING, false )
            .set( EasingAnimation.EASING_DATA, new EasingData( Easing.Type.LINEAR, 0f, walkVelocity, walkStartTime ) )
        .build( EasingAnimation.class );
    }

    @Override
    protected final void update( final FFTimer timer, int entityId ) {
        FFInput input = context.getInput();
        EMovement movement = entitySystem.getComponent( entityId, EMovement.TYPE_KEY );
        ETransform transform = entitySystem.getComponent( entityId, ETransform.TYPE_KEY );
        ECollision collision = entitySystem.getComponent( entityId, ECollision.TYPE_KEY );

        float xVelocity = movement.getVelocityX();
        float yVelocity = movement.getVelocityY();
        
        // walking right/left
        if ( input.isPressed( goRight ) && xVelocity >= 0f ) {
            if ( xVelocity == 0f && !animationSystem.isActive( startWalkAnimId ) ) {
                animationSystem.activate( startWalkAnimId, timer );
            }
            
           if ( animationSystem.isActive( startWalkAnimId ) ) {
                xVelocity = animationSystem.getValue( startWalkAnimId, entityId, xVelocity );
           } else if ( xVelocity < walkVelocity ) {
               xVelocity = walkVelocity;
           }
        } else if ( input.isPressed( goLeft ) && xVelocity <= 0f ) {
            if ( xVelocity == 0f && !animationSystem.isActive( startWalkAnimId ) ) {
                animationSystem.activate( startWalkAnimId, timer );
            }
            
            if ( animationSystem.isActive( startWalkAnimId ) ) {
                xVelocity = -animationSystem.getValue( startWalkAnimId, entityId, xVelocity );
            } else if ( xVelocity > -walkVelocity ) {
                xVelocity = -walkVelocity;
            }
        } else if ( xVelocity != 0f ) {
            if ( Math.abs( xVelocity ) > 1f ) {
                xVelocity = ( xVelocity > 0f )? xVelocity - 0.3f : xVelocity + 0.3f;
            } else {
                xVelocity = 0f;
                animationSystem.resetAnimation( startWalkAnimId );
            }
        }

        // check falling/context south
        if ( !movement.hasStateFlag( States.CONTACT_SOUTH ) ) {
            if ( !animationSystem.isActive( gravityAnimId ) ) {
                animationSystem.activate( gravityAnimId, timer );
            }
            
            yVelocity = animationSystem.getValue( gravityAnimId, entityId, yVelocity );
        } else {
            if ( animationSystem.isActive( gravityAnimId ) || yVelocity < 0 ) {
                animationSystem.resetAnimation( gravityAnimId );
                yVelocity = 0f;
            }
        }
        
        movement.setVelocity( xVelocity, yVelocity );
        if ( movement.isMoving() ) {
            adjustToGround( transform, collision, movement );
            //System.out.println( StringUtils.bitsetToString( groundScanBits, 1, 8 ) );
        }

    }

    private boolean adjustToGround( final ETransform transform, final ECollision collision, final EMovement movement ) {
         if ( slopePivot.x == 0 && slopePivot.y == 0 ) {
            Rectangle bounding = collision.getBounding();
            slopePivot.x = bounding.x + bounding.width / 2 - 1 ;
            slopePivot.y = bounding.y + bounding.height - 1 - 3;
        }
        groundScanBounds.x = (int) Math.ceil( transform.getXpos() + movement.getVelocityX() ) + slopePivot.x;
        groundScanBounds.y = (int) Math.ceil( transform.getYpos() + movement.getVelocityY() ) + slopePivot.y;
        groundScanBits.clear();
        
        TileIterator groundTileScan = tileGridSystem.getTiles( tileGridId, groundScanBounds );
        if ( !groundTileScan.hasNext() ) {
            return false;
        }
        
        while ( groundTileScan.hasNext() ) {
            addTileToGroundScanBits( groundTileScan );
        }
        
        int correction = 0;
        if ( groundScanBits.isEmpty() ) {
            return false;
        } else if ( groundScanBits.get( 4 ) ) {
            for ( int i = 3; i >= 0; i-- ) {
                if ( groundScanBits.get( i ) ) {
                    correction--;
                }
            }
        } else {
            for ( int i = 4; i < 9; i++ ) {
                if ( !groundScanBits.get( i ) ) {
                    correction++;
                }
            }
        }
        
        if ( correction != 0 ) {
            //System.out.println( "correction " +correction);
            movement.addVelocity( 0f, correction );
        }
        
        return true;
    }

    private void addTileToGroundScanBits( final TileIterator groundTileScan ) {
        final int tileId = groundTileScan.next();
        
        if ( !entitySystem.getAspect( tileId ).contains( ECollision.TYPE_KEY ) ) {
            return;
        }
        
        final int tileWorldXpos = (int) Math.ceil( groundTileScan.getWorldXPos() );
        final int tileWorldYpos = (int) Math.ceil( groundTileScan.getWorldYPos() );
        final int xOffset = groundScanBounds.x - tileWorldXpos;
        final int yOffset = groundScanBounds.y - tileWorldYpos;
        final ECollision tileCollision = entitySystem.getComponent( tileId, ECollision.TYPE_KEY );
        final Rectangle bounding = tileCollision.getBounding();
        final int bitmaskId = tileCollision.getBitmaskId();
        
        if ( bitmaskId >= 0 ) {
            BitMask bitMask = collisionSystem.getBitMask( bitmaskId );
            for ( int i = 0; i < 8; i++ ) {
                if ( GeomUtils.contains( bounding, xOffset, yOffset + i ) ) {
                    groundScanBits.set( i, bitMask.getBit( xOffset, yOffset + i ) );
                }
            }
        } else {
            for ( int i = 0; i < 8; i++ ) {
                if ( GeomUtils.contains( bounding, xOffset, yOffset + i ) ) {
                    groundScanBits.set( i );
                }
            }
        }
    }

}
