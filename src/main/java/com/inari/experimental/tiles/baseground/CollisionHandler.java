package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Orientation;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.physics.collision.CollisionEvent;
import com.inari.firefly.physics.collision.CollisionEventListener;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;

public class CollisionHandler implements CollisionEventListener {
    
    private final FFContext context;
    
    public CollisionHandler( FFContext context ) {
        this.context = context;
    }

    @Override
    public void onCollisionEvent( CollisionEvent event ) {
        System.out.println( event.toString() );
        
        int entityId = event.getMovedEntityId();
        EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
        ETransform transfrom = context.getEntityComponent( entityId, ETransform.TYPE_KEY );
        
        Rectangle collisionIntersectionBounds = event.getCollisionIntersectionBounds();
        float vX = movement.getVelocityX();
        float vY = movement.getVelocityY();
        
        int backStepX = 0;
        int backStepY = 0;
        
        if ( vX > 0 ) {
            movement.setContact( Orientation.EAST );
            backStepX = - collisionIntersectionBounds.width;
        } else if ( vX < 0 ) {
            movement.setContact( Orientation.WEST );
            backStepX = collisionIntersectionBounds.width;
        }
        
        if ( vY > 0 ) {
            movement.setContact( Orientation.SOUTH );
            backStepY = - collisionIntersectionBounds.height;
        } else if ( vY < 0 ) {
            movement.setContact( Orientation.NORTH );
            backStepY = collisionIntersectionBounds.height;
        }
        
        transfrom.move( backStepX, backStepY );
    }

}
