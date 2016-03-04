package com.inari.experimental.tiles.baseground;

import java.util.BitSet;

import com.inari.commons.geom.Orientation;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.physics.collision.CollisionResolver;
import com.inari.firefly.physics.collision.Collisions;
import com.inari.firefly.physics.collision.Collisions.CollisionData;

public final class PlayerCollisionResolver extends CollisionResolver {
    
    private int slopeFactor = 4;

    protected PlayerCollisionResolver( int id ) {
        super( id );
    }

    @Override
    public final void resolve( Collisions collisions ) {
        final float velocityY = collisions.entityData.movement.getVelocityY();
        final float velocityX = collisions.entityData.movement.getVelocityX();
        
        boolean skipCheck = false;
        if ( velocityX != 0 ) {
            if ( velocityY != 0 ) {
                collisions.entityData.transform.move( 0f, -velocityY );
                collisions.update();
            }
            
            skipCheck = resolveXAxisCollision( collisions, velocityX );
            collisions.entityData.transform.move( 0f, velocityY );
        }
        
        if ( velocityY != 0 && !skipCheck ) {
            collisions.update();
            resolveYAxisCollision( collisions, velocityY );
        }
    }
    
    private boolean resolveXAxisCollision( Collisions collisions, float velocityX ) {
        
        int xCorrection = 0;
        int yCorrection = 0;
        boolean skip = false;
        
        if ( velocityX < 0 ) {
            for ( CollisionData collision : collisions ) {
                final Rectangle bounds = collision.intersectionBounds;
                final BitSet mask = collision.intersectionMask;

                if ( mask == null ) {
                    if ( bounds.height < slopeFactor && bounds.y + bounds.height == collisions.entityData.worldBounds.height ) {
                        yCorrection = - bounds.height;
                    } else if ( bounds.width > xCorrection ) {
                        xCorrection = bounds.width;
                    }
                } else {
                    if ( bounds.y + bounds.height == collisions.entityData.worldBounds.height ) {
                        // check slope
                        yCorrection = - getYCorrectionWest( bounds, mask );
                        if ( Math.abs( yCorrection ) >= slopeFactor ) {
                            yCorrection = 0;
                            xCorrection = getXCorrectionSouth( bounds, mask );
                        }
                    } else {
                        xCorrection = getXCorrectionSouth( bounds, mask );
                    }
                }
            }
        }
        
        if ( velocityX > 0 ) {
            for ( CollisionData collision : collisions ) {
                final Rectangle bounds = collision.intersectionBounds;
                final BitSet mask = collision.intersectionMask;
                
                if ( mask == null ) {
                    if ( bounds.height < slopeFactor && bounds.y + bounds.height == collisions.entityData.worldBounds.height ) {
                        yCorrection = - bounds.height;
                    } else if ( bounds.width > Math.abs( xCorrection ) ) {
                        xCorrection = - bounds.width;
                    }
                } else {
                    if ( bounds.y + bounds.height == collisions.entityData.worldBounds.height ) {
                        // check slope
                        yCorrection = - getYCorrectionEast( bounds, mask );
                        if ( Math.abs( yCorrection ) >= slopeFactor ) {
                            yCorrection = 0;
                            xCorrection = - getXCorrectionSouth( bounds, mask );
                        }
                    } else {
                        xCorrection = - getXCorrectionSouth( bounds, mask );
                    }
                }
            }
        }

        if ( xCorrection != 0 ) {
            collisions.entityData.transform.moveCeilX( xCorrection );
        }
        if ( yCorrection != 0 && yCorrection < slopeFactor ) {
            collisions.entityData.transform.moveCeilY( yCorrection );
            collisions.entityData.movement.setContact( Orientation.SOUTH );
            skip = true;
        } 
        
        
        //System.out.println( "X " + xCorrection + " " + yCorrection + " " + skip );
        
        return skip;
    }
    
    

    private boolean resolveYAxisCollision( Collisions collisions, float velocityY ) {
        int xCorrection = 0;
        int yCorrection = 0;
        
        if ( velocityY > 0 ) {
            
            for ( CollisionData collision : collisions ) {
                final Rectangle bounds = collision.intersectionBounds;
                final BitSet mask = collision.intersectionMask;
                
                if ( mask == null ) {
                    if ( bounds.height > yCorrection ) {
                        yCorrection = bounds.height;
                    }
                } else {
                    yCorrection = Math.max( getYCorrectionWest( bounds, mask ), getYCorrectionEast( bounds, mask ) );
                    if ( yCorrection > slopeFactor ) {
                        yCorrection = 0;
                    }
                    collisions.entityData.movement.setContact( Orientation.SOUTH );
                }
            }
        }
        
        if ( xCorrection != 0 ) {
            collisions.entityData.transform.moveCeilX( xCorrection );
        }
        if ( yCorrection != 0 ) {
            collisions.entityData.transform.moveCeilY( -yCorrection );
            collisions.entityData.movement.setContact( Orientation.SOUTH );
        }

        //System.out.println( "Y " + xCorrection + " " + yCorrection );
        
        return false;
    }
    
    private int getXCorrectionSouth( Rectangle bounds, BitSet mask ) {
        int corr = 0;
        for ( int x = 0; x < bounds.width; x++ ) {
            if ( mask.get( ( bounds.height - 1 ) * bounds.width + x ) ) {
                corr++;
            }
        }
        return corr;
    }
    
    private int getXCorrectionNorth( Rectangle bounds, BitSet mask ) {
        int corr = 0;
        for ( int x = 0; x < bounds.width; x++ ) {
            if ( mask.get( x ) ) {
                corr++;
            }
        }
        return corr;
    }

    private int getYCorrectionWest( Rectangle bounds, BitSet mask ) {
        int corr = 0;
        for ( int y = bounds.height - 1; y >= 0; y-- ) {
            if ( mask.get( y * bounds.width ) ) {
                corr++;
            }
        }
        return corr;
    }
    
    private int getYCorrectionEast( Rectangle bounds, BitSet mask ) {
        int corr = 0;
        for ( int y = bounds.height - 1; y >= 0; y-- ) {
            if ( mask.get( y * bounds.width + ( bounds.width - 1 ) ) ) {
                corr++;
            }
        }
        return corr;
    }
 
}
