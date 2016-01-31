package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Orientation;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.physics.collision.CollisionEvent;
import com.inari.firefly.physics.collision.CollisionEvent.CollisionData;
import com.inari.firefly.physics.collision.CollisionEventListener;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;

public class CollisionHandler implements CollisionEventListener {
    
    private final FFContext context;
    
    public CollisionHandler( FFContext context ) {
        this.context = context;
    }

    @Override
    public void onCollisionEvent( CollisionEvent event ) {
        
        int entityId = event.getMovedEntityId();
        EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
        ETransform transfrom = context.getEntityComponent( entityId, ETransform.TYPE_KEY );
        ECollision collision = context.getEntityComponent( entityId, ECollision.TYPE_KEY );
        
        int backStepX = 0;
        int backStepY = 0;
        
        CollisionData maxWidthCollisionData = event.getMaxWidthCollisionData();
        if ( maxWidthCollisionData.intersectionBounds.width > 4 ) {
            if ( maxWidthCollisionData.intersectionBounds.y > 0 && !movement.hasContact( Orientation.SOUTH ) ) {
                movement.setContact( Orientation.SOUTH );
                backStepY = - maxWidthCollisionData.intersectionBounds.height;
            }
        } else {
            movement.removeContact( Orientation.SOUTH );
        }
        
//        
//        if ( collisionIntersectionBounds.area() == 0 ) {
//            return;
//        }
//        
//        float vX = movement.getVelocityX();
//        float vY = movement.getVelocityY();
//        
//        int backStepX = 0;
//        int backStepY = 0;
//        
//        if ( vX > 0 && !movement.hasContact( Orientation.EAST ) ) {
//            movement.setContact( Orientation.EAST );
//            backStepX = - collisionIntersectionBounds.width;
//        } else if ( vX < 0 && !movement.hasContact( Orientation.WEST ) ) {
//            movement.setContact( Orientation.WEST );
//            backStepX = collisionIntersectionBounds.width;
//        }
//        
//        if ( vY > 0 && !movement.hasContact( Orientation.SOUTH ) ) {
//            movement.setContact( Orientation.SOUTH );
//            backStepY = - collisionIntersectionBounds.height;
//        } else if ( vY < 0 && !movement.hasContact( Orientation.NORTH ) ) {
//            movement.setContact( Orientation.NORTH );
//            backStepY = collisionIntersectionBounds.height;
//        }
        if ( backStepX != 0 || backStepY != 0 ) {
            transfrom.move( backStepX, backStepY );
        }
    }

}
