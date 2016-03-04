package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Orientation;
import com.inari.firefly.animation.Animation;
import com.inari.firefly.animation.easing.EasingAnimation;
import com.inari.firefly.animation.easing.EasingData;
import com.inari.firefly.entity.EntityController;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFTimer;

public final class GravityController extends EntityController {

    private EasingAnimation animation;
    private float velocity = 0.0f;

    protected GravityController( int id, FFContext context ) {
        super( id, context );
        this.context = context;
        
        int animationId = context.getComponentBuilder( Animation.TYPE_KEY )
            .set( EasingAnimation.NAME, "gravitiyAnimation" )
            .set( EasingAnimation.LOOPING, false )
            .set( EasingAnimation.EASING_DATA, new EasingData( Easing.Type.EXPO_OUT, 0.0f, 15.0f, 500 ) )
        .build( EasingAnimation.class );
        animation = context.getSystemComponent( Animation.TYPE_KEY, animationId, EasingAnimation.class );
    }

    @Override
    protected final void update( FFTimer timer, int entityId ) {
        EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
        
        if ( !movement.hasContact( Orientation.SOUTH ) ) {
            if ( !animation.isFinished() && !animation.isActive() ) {
                animation.activate( timer );
            }
            
            
            float newVelocity = animation.getValue( entityId, velocity );
            movement.getVelocityVector().dy += ( newVelocity - velocity );
            System.out.println( movement.getVelocityVector().dy );
            velocity = newVelocity;
        } else {
            reset();
        }
    }
    
    private void reset() {
        animation.reset();
        velocity = 0.0f;
    }

}
