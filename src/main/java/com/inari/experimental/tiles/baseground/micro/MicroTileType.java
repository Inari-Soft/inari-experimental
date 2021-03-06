package com.inari.experimental.tiles.baseground.micro;

import com.inari.commons.geom.BitMask;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.lang.aspect.Aspect;
import com.inari.firefly.platformer.PFContact;

public enum MicroTileType {
    FULL( 
        '0', true, null,
        new Rectangle( 0, 0, 8, 8 ) 
    ),
    SLOPE_NORTH_WEST( 
        '1', true, null,
        new Rectangle( 8, 0, 8, 8 ),
        createSlashedBitMask( 8, 7, -1, 0, 1 )
    ),
    SLOPE_NORTH_EAST(
        '2', true, null,
        new Rectangle( 16, 0, 8, 8 ),
        createSlashedBitMask( 8, 0, 1, 0, 1 )
    ),
    SLOPE_SOUTH_WEST( 
        '3', true, null,
        new Rectangle( 24, 0, 8, 8 ),
        createSlashedBitMask( 8, 0, -1, 0, -1 )
    ),
    SLOPE_SOUTH_EAST( 
        '4', true, null,
        new Rectangle( 32, 0, 8, 8 ),
        createSlashedBitMask( 8, -7, 1, 0, -1 )
    ),
    SOLIDE(
        '5', true, null,
        new Rectangle( 40, 0, 8, 8 ) 
    ),
    LADDER(
        'L', false, PFContact.LADDER,
        new Rectangle( 56, 0, 8, 8 ) 
    ),
    SPIKE(
        'S', false, PFContact.SPIKE,
        new Rectangle( 64, 0, 8, 8 ) 
    )
    ;
    
    public final char mapSymbol;
    public final boolean solid;
    public final Aspect contactType;
    public final Rectangle bounds;
    public final BitMask collisionMask;
    
    private MicroTileType( char mapSymbol, boolean solid, Aspect contactType, Rectangle bounds ) {
        this.mapSymbol = mapSymbol;
        this.solid = solid;
        this.contactType = contactType;
        this.bounds = bounds;
        this.collisionMask = null;
    }
    
    private MicroTileType( char mapSymbol, boolean solid, Aspect contactType, Rectangle bounds, BitMask collisionMask ) {
        this.mapSymbol = mapSymbol;
        this.solid = solid;
        this.contactType = contactType;
        this.bounds = bounds;
        this.collisionMask = collisionMask;
    }

    public static MicroTileType forMapSymbol( char mapSymbol ) {
        for ( MicroTileType type : MicroTileType.values() ) {
            if ( type.mapSymbol == mapSymbol ) {
                return type;
            }
        }
        return null;
    }
    
    public static final BitMask createSlashedBitMask( final int squareWidth, final int xoffset, final int xfactor, final int yoffset, final int yfactor ) {
        BitMask result = new BitMask( squareWidth, squareWidth );
        for ( int y = 0; y < squareWidth; y++ ) {
            for ( int x = 0; x < squareWidth; x++ ) {
                if ( x * xfactor + xoffset >= y * yfactor + yoffset ) {
                    result.setBit( x, y );
                }
            }
        }
        
        return result;
    }
    

}
