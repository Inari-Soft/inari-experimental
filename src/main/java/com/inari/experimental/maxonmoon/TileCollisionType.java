package com.inari.experimental.maxonmoon;

import com.inari.commons.geom.BitMask;
import com.inari.commons.lang.aspect.Aspect;
import com.inari.commons.lang.aspect.AspectGroup;
import com.inari.firefly.physics.collision.CollisionSystem;

public enum TileCollisionType implements Aspect {
    FULL( null ),
    
    SLOPE_0224( createSlashedBitMask( -7, 1, 0, -1 ) ),
    SLOPE_2042( createSlashedBitMask( 0, -1, 0, -1 ) ),
    SLOPE_2402( createSlashedBitMask( 0, 1, 0, 1 ) ),
    SLOPE_4220( createSlashedBitMask( 7, -1, 0, 1 ) ),
    
    SLOPE_0013( createSlashedBitMask( -14, 1, 0, -2 ) ),
    SLOPE_0031( createSlashedBitMask( -7, -1, 0, -2 ) ),
    SLOPE_1300( createSlashedBitMask( 0, 1, 0, 2 ) ),
    SLOPE_3100( createSlashedBitMask( 7, -1, 0, 2 ) ),
    
    SLOPE_1344( createSlashedBitMask( -6, 1, 0, -2 ) ),
    SLOPE_3144( createSlashedBitMask( 1, -1, 0, -2 ) ),
    SLOPE_4413( createSlashedBitMask( 8, 1, 0, 2 ) ),
    SLOPE_4431( createSlashedBitMask( 15, -1, 0, 2 ) )
    
    ;
    
    
    private final Aspect aspect;
    public final BitMask collisionMask;
    
    private TileCollisionType( BitMask collisionMask ) {
        aspect = CollisionSystem.CONTACT_ASPECT_GROUP.createAspect( name() );
        this.collisionMask = collisionMask;
    }
    
    @Override
    public AspectGroup aspectGroup() {
        return aspect.aspectGroup();
    }
    
    @Override
    public int index() {
        return aspect.index();
    }
    
    static final BitMask createSlashedBitMask( final int xoffset, final int xfactor, final int yoffset, final int yfactor ) {
        BitMask result = new BitMask( 8, 8 );
        for ( int y = 0; y < 8; y++ ) {
            for ( int x = 0; x < 8; x++ ) {
                if ( x * xfactor + xoffset >= y * yfactor + yoffset ) {
                    result.setBit( x, y );
                }
            }
        }
        
        return result;
    }
}
