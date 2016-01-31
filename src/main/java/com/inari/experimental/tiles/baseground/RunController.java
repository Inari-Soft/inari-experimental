package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Orientation;
import com.inari.firefly.entity.EntityController;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.external.FFTimer;

public class RunController extends EntityController {

    protected RunController( int id, FFContext context ) {
        super( id, context );
    }

    @Override
    protected void update( FFTimer timer, int entityId ) {
        FFInput input = context.getInput();
        EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
        
        if ( movement.hasContact( Orientation.SOUTH ) ) {
            if ( input.isPressed( ButtonType.RIGHT ) ) {
                movement.setVelocityX( 1f );
                return;
            } else if ( input.isPressed( ButtonType.LEFT ) ) {
                movement.setVelocityX( -1f );
                return;
            }
        } 
        movement.setVelocityX( 0f );
    }

}
