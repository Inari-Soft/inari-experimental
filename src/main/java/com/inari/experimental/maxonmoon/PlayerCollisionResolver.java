package com.inari.experimental.maxonmoon;

import com.inari.commons.geom.Position;
import com.inari.commons.lang.aspect.Aspects;
import com.inari.firefly.FFInitException;
import com.inari.firefly.entity.EEntity;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.collision.ContactConstraint;
import com.inari.firefly.physics.collision.ContactScan;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.platformer.PFState;

public class PlayerCollisionResolver extends CollisionResolver {
    
    private final int resolvingIterations = 4;
    private final Aspects GROUND_SLOPE_CONTACT_ASPECTS = CollisionSystem.CONTACT_ASPECT_GROUP.createAspects();
    
    private CollisionSystem collisionSystem;
    
    private final Position[] collisionPoints = new Position[] {
        new Position( 1, 0 ),
        new Position( 4, 0 ),
        new Position( 5, 2 ),
        new Position( 5, 5 ),
        new Position( 4, 7 ),
        new Position( 1, 7 ),
        new Position( 0, 5 ),
        new Position( 0, 2 ),
    };

    protected PlayerCollisionResolver( int id ) {
        super( id );
        GROUND_SLOPE_CONTACT_ASPECTS
            .set( TileCollisionType.SLOPE_0224 )
            .set( TileCollisionType.SLOPE_2042 )
            .set( TileCollisionType.SLOPE_0013 )
            .set( TileCollisionType.SLOPE_1344 )
            .set( TileCollisionType.SLOPE_0031 )
            .set( TileCollisionType.SLOPE_3144 );
    }
    
    

    @Override
    protected void init() throws FFInitException {
        super.init();
        collisionSystem = context.getSystem( CollisionSystem.SYSTEM_KEY );
    }



    @Override
    public void resolve( int entityId ) {
        final ECollision collision = context.getEntityComponent( entityId, ECollision.TYPE_KEY );
        final ContactScan contactScan = collision.getContactScan();
        final EEntity entity = context.getEntityComponent( entityId, EEntity.TYPE_KEY );
        final boolean prevGroundContact = entity.hasAspect( PFState.ON_GROUND );
        
        entity.resetAspect( PFState.ON_GROUND );
        if ( !contactScan.hasAnyContact() ) {
            return;
        }
        
        final ETransform transform = context.getEntityComponent( entityId, ETransform.TYPE_KEY );
        final EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
        final ContactConstraint bodyContacts = contactScan.getContactContstraint( Names.PLAYER.BODY_CONTACT_SCAN );
        final ContactConstraint groundContacts = contactScan.getContactContstraint( Names.PLAYER.GROUND_CONTACT_SCAN );

        final float velocityY = movement.getVelocityY();
        final float velocityX = movement.getVelocityX();
        
        //System.out.println( "prevGroundContact: " +prevGroundContact );
        if ( prevGroundContact ) {
            boolean groundContact = checkGroundContact( 0, groundContacts );
            if ( groundContact ) {
                adjustHorizontal( entityId, transform, bodyContacts, velocityX );
                checkAndAdjustGround( entityId, bodyContacts, transform );
                entity.setAspect( PFState.ON_GROUND );
            } else {
                adjustHorizontal( entityId, transform, bodyContacts, velocityX );
            }
        } else {
            if ( velocityY < 0 ) {
                checkAndAdjustTop( entityId, bodyContacts, transform );
            } else if ( velocityY >= 0 ) {
                // check and adjust ground contact
                if ( checkAndAdjustGround( entityId, bodyContacts, transform ) ) {
                    entity.setAspect( PFState.ON_GROUND );
                } else if ( checkGroundContact( 0, groundContacts ) ) {
                    entity.setAspect( PFState.ON_GROUND );
                    transform.setYpos( (float) Math.ceil( transform.getYpos() ) );
                } 
            } 
            
            adjustHorizontal( entityId, transform, bodyContacts, velocityX );
        }
        
        // adjust slope if needed
        if ( 
            prevGroundContact && 
            !entity.hasAspect( PFState.ON_GROUND ) && 
            groundContacts.hasAnyContacts( GROUND_SLOPE_CONTACT_ASPECTS ) && 
            ( ( velocityX > 0f && groundContacts.hasContact( 0, 0 ) ) || ( velocityX < 0f && groundContacts.hasContact( 5, 0 ) ) ) 
        ) {
            transform.setYpos( (float) Math.ceil( transform.getYpos() + 1 ) );
            entity.setAspect( PFState.ON_GROUND );
        }
        
        // handle semi solid ground contact
        final ContactConstraint groundSemiSolidContacts = contactScan.getContactContstraint( Names.PLAYER.GROUND_CONTACT_SCAN_SEMI_SOLID );
        if ( groundSemiSolidContacts.hasAnyContact() ) {
            if ( velocityY > 0.0f ) {
                float ypos = transform.getYpos();
                final int correction = adjustToSemiSolidGround( entityId, entity, transform, groundSemiSolidContacts );
                if ( correction > 0 && correction < 4 ) {
                    entity.setAspect( PFState.ON_GROUND );
                } else {
                    transform.setYpos( ypos );
                }
            } else if ( velocityY == 0.0f && groundSemiSolidContacts.hasContact( 3, 1 ) && !groundSemiSolidContacts.hasContact( 3, 0 ) ) { 
                entity.setAspect( PFState.ON_GROUND );
            }
        }
        
        //System.out.println( "onGround: " + entity.hasAspect( PFState.ON_GROUND ) );
    }



    private int adjustToSemiSolidGround( int entityId, final EEntity entity, final ETransform transform, final ContactConstraint groundSemiSolidContacts ) {
        int count = 0;
        while ( ( groundSemiSolidContacts.hasContact( 2, 0 ) || groundSemiSolidContacts.hasContact( 4, 0 ) ) && count < resolvingIterations ) {
            transform.setYpos( (float) Math.ceil( transform.getYpos() ) - 1 );
            collisionSystem.updateContacts( entityId );
            count++;
        }
        return count;
    }

    private boolean adjustHorizontal( 
        int entityId, 
        final ETransform transform,
        final ContactConstraint bodyContacts, 
        final float velocityX
   ) {
        if ( velocityX > 0  && checkAndAdjustRight( entityId, bodyContacts, transform ) ) {
            return true;
        } else if ( velocityX < 0 && checkAndAdjustLeft( entityId, bodyContacts, transform ) ) {
            return true;
        }
        return false;
    }
    
     private boolean checkGroundContact( final int y, final ContactConstraint groundContacts ) {
        for ( int x = 1; x < 5; x++ ) {
            if ( groundContacts.hasContact( x, y ) ) {
                return true;
            }
        }
        
        return false;
    }

    private boolean checkAndAdjustRight( final int entityId, final ContactConstraint bodyContacts, final ETransform transform ) {
        int count = 0;
        while ( ( bodyContacts.hasContact( collisionPoints[ 2 ] ) || bodyContacts.hasContact( collisionPoints[ 3 ] ) ) && count < resolvingIterations  ) {
            transform.setXpos( (float) Math.ceil( transform.getXpos() ) - 1f );
            collisionSystem.updateContacts( entityId );
            count++;
        }
        return count > 0;
    }

    private boolean checkAndAdjustLeft( final int entityId, final ContactConstraint bodyContacts, final ETransform transform ) {
        int count = 0;
        while ( ( bodyContacts.hasContact( collisionPoints[ 6 ] ) || bodyContacts.hasContact( collisionPoints[ 7 ] ) ) && count < resolvingIterations  ) {
            transform.setXpos( (float) Math.floor( transform.getXpos() ) + 1f );
            collisionSystem.updateContacts( entityId );
            count++;
        }
        return count > 0;
    }
    
    private boolean checkAndAdjustTop( final int entityId, final ContactConstraint bodyContact, final ETransform transform ) {
        int count = 0;
        while ( ( bodyContact.hasContact( collisionPoints[ 0 ] ) || bodyContact.hasContact( collisionPoints[ 1 ] ) ) && count < resolvingIterations ) {
            transform.setYpos( (float) Math.floor( transform.getYpos() ) + 1 );
            collisionSystem.updateContacts( entityId );
            count++;
        }
        
        return count > 0;
     }

    private boolean checkAndAdjustGround( final int entityId, final ContactConstraint bodyContact, final ETransform transform ) {
        int count = 0;
        while ( ( bodyContact.hasContact( collisionPoints[ 4 ] ) || bodyContact.hasContact( collisionPoints[ 5 ] ) ) && count < resolvingIterations  ) {
            transform.setYpos( (float) Math.ceil( transform.getYpos() ) - 1 );
            collisionSystem.updateContacts( entityId );
            count++;
        }
        
        return count > 0;
     }
}
