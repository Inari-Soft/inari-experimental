package com.inari.experimental.tiles.baseground.micro;

import java.util.BitSet;

import com.inari.commons.geom.Rectangle;
import com.inari.firefly.physics.collision.BitMask;

public enum MicroTileType {
    FULL( 
        '0', true,
        new Rectangle( 0, 0, 8, 8 ) 
    ),
    SLOPE_NORTH_WEST( 
        '1', true,
        new Rectangle( 8, 0, 8, 8 ),
        BitMask.createSlashedBitset( 8, 7, -1, 0, 1 )
    ),
    SLOPE_NORTH_EAST(
        '2', true,
        new Rectangle( 16, 0, 8, 8 ),
        BitMask.createSlashedBitset( 8, 0, 1, 0, 1 )
    ),
    SLOPE_SOUTH_WEST( 
        '3', true,
        new Rectangle( 24, 0, 8, 8 ),
        BitMask.createSlashedBitset( 8, 0, -1, 0, -1 )
    ),
    SLOPE_SOUTH_EAST( 
        '4', true,
        new Rectangle( 32, 0, 8, 8 ),
        BitMask.createSlashedBitset( 8, -7, 1, 0, -1 )
    ),
    SOLIDE(
        '5', true,
        new Rectangle( 40, 0, 8, 8 ) 
    ),
    LADDER(
        'L', false,
        new Rectangle( 56, 0, 8, 8 ) 
    ),
    SPIKE(
        'S', false,
        new Rectangle( 64, 0, 8, 8 ) 
    )
    ;
    
    public final char mapSymbol;
    public final boolean solid;
    public final Rectangle bounds;
    public final BitSet collisionBitset;
    
    private MicroTileType( char mapSymbol, boolean solid, Rectangle bounds ) {
        this.mapSymbol = mapSymbol;
        this.solid = solid;
        this.bounds = bounds;
        this.collisionBitset = null;
    }
    
    private MicroTileType( char mapSymbol, boolean solid, Rectangle bounds, BitSet collisionBitset ) {
        this.mapSymbol = mapSymbol;
        this.solid = solid;
        this.bounds = bounds;
        this.collisionBitset = collisionBitset;
    }

    public static MicroTileType forMapSymbol( char mapSymbol ) {
        for ( MicroTileType type : MicroTileType.values() ) {
            if ( type.mapSymbol == mapSymbol ) {
                return type;
            }
        }
        return null;
    }
    

}