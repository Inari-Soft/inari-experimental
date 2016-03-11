package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Easing;
import com.inari.commons.geom.Orientation;
import com.inari.firefly.animation.Animation;
import com.inari.firefly.animation.easing.EasingAnimation;
import com.inari.firefly.animation.easing.EasingData;
import com.inari.firefly.entity.EntityController;
import com.inari.firefly.physics.movement.EMovement;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;
import com.inari.firefly.system.external.FFTimer;

public final class JumpController extends EntityController {
    
    private EasingAnimation animation;
    private float velocity = 0.0f;

    protected JumpController( int id, FFContext context ) {
        super( id, context );
        this.context = context;
        
        int animationId = context.getComponentBuilder( Animation.TYPE_KEY )
            .set( EasingAnimation.NAME, "jumpAnimation" )
            .set( EasingAnimation.LOOPING, false )
            .set( EasingAnimation.EASING_DATA, new EasingData( Easing.Type.LINEAR, -8f, 0f, 500 ) )
        .build( EasingAnimation.class );
        animation = context.getSystemComponent( Animation.TYPE_KEY, animationId, EasingAnimation.class );
    }

    @Override
    protected void update( FFTimer timer, int entityId ) {
//        FFInput input = context.getInput();
//        EMovement movement = context.getEntityComponent( entityId, EMovement.TYPE_KEY );
//        
//        if ( movement.hasContact( Orientation.SOUTH ) && !animation.isActive() && input.isPressed( ButtonType.FIRE_1 ) ) {
//            animation.activate( timer );
//            movement.removeContact( Orientation.SOUTH );
//            return;
//        }
//        
//        if ( animation.isActive() ) {
//            if ( movement.hasContact( Orientation.SOUTH ) ) {
//                System.out.println( "JumpController reset" );
//                reset( movement );
//                return;
//            }
//            
//            float newVelocity = animation.getValue( entityId, velocity );
//            movement.getVelocityVector().dy += ( newVelocity - velocity );
//            velocity = newVelocity;
//            
//            System.out.println( "JumpController newVelocity:"+newVelocity+" velocity:"+velocity+" movement.getVelocityVector().dy:" + movement.getVelocityVector().dy   );
//            
//            
//        } else {
//            reset( movement );
//        }
        
    }
    
    private void reset( EMovement movement ) {
//        animation.reset();
//        movement.getVelocityVector().dy -= velocity;
//        velocity = 0.0f;
    }

}
