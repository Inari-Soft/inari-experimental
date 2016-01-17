package com.inari.experimental.tiles.baseground;

import java.util.BitSet;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.physics.collision.BitMask;

public enum TileData {
    FULL( 
        new Position( 0, 0 ), 
        new Rectangle( 0, 0, 16, 16 ) 
    ),
    HALF_SOUTH( 
        new Position( 1, 0 ), 
        new Rectangle( 0, 8, 16, 8 )
    ),
    HALF_WEST( 
        new Position( 2, 0 ), 
        new Rectangle( 0, 0, 8, 16 )
    ),
    HALF_NORTH( 
        new Position( 3, 0 ), 
        new Rectangle( 0, 0, 16, 8 )
    ),
    HALF_EAST( 
        new Position( 4, 0 ), 
        new Rectangle( 8, 0, 8, 16 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL(
        new Position( 5, 0 ),
        BitMask.createSlashedBitset( 16, 0, -1, 0, -1 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL(
        new Position( 6, 0 ),
        BitMask.createSlashedBitset( 16, -15, 1, 0, -1 )
    ),
    SLASH_NORTH_FULL_EAST_FULL(
        new Position( 7, 0 ),
        BitMask.createSlashedBitset( 16, 0, 1, 0, 1 )
    ),
    SLASH_NORTH_FULL_WEST_FULL(
        new Position( 0, 1 ),
        BitMask.createSlashedBitset( 16, 15, -1, 0, 1 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL_NORTH_HALF(
        new Position( 1, 1 ),
        BitMask.createSlashedBitset( 16, -14, 2, 0, -1 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL_EAST_HALF(
        new Position( 2, 1 ),
        BitMask.createSlashedBitset( 16, 1, -1, 0, -2 )
    ),
    
    SLASH_NORTH_FULL_WEST_FULL_SOUTH_HALF(
        new Position( 3, 1 ),
        BitMask.createSlashedBitset( 16, 31, -2, 0, 1 )
    ),
    SLASH_NORTH_FULL_EAST_FULL_WEST_HALF(
        new Position( 4, 1 ),
        BitMask.createSlashedBitset( 16, 16, 1, 0, 2 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL_NORTH_HALF(
        new Position( 5, 1 ),
        BitMask.createSlashedBitset( 16, 16, -2, 0, -1 )
    ),
    SLASH_NORTH_FULL_WEST_FULL_EAST_HALF(
        new Position( 6, 1 ),
        BitMask.createSlashedBitset( 16, 31, -1, 0, 2 )
    ),
    SLASH_NORTH_FULL_EAST_FULL_SOUTH_HALF(
        new Position( 7, 1 ),
        BitMask.createSlashedBitset( 16, 16, 1, 0, 2 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL_WEST_HALF(
        new Position( 0, 2 ),
        BitMask.createSlashedBitset( 16, -14, 1, 0, -2 )
    ),
    SLASH_SOUTH_HALF_EAST_FULL(
        new Position( 1, 2 ),
        BitMask.createSlashedBitset( 16, -30, 2, 0, -1 )
    ),
    SLASH_SOUTH_FULL_WEST_HALF(
        new Position( 2, 2 ),
        BitMask.createSlashedBitset( 16, -15, -1, 0, -2 )
    ),
    SLASH_NORTH_HELF_WEST_FULL(
        new Position( 3, 2 ),
        BitMask.createSlashedBitset( 16, 15, -2, 0, 1 )
    ),
    SLASH_NORTH_FULL_EAST_HALF(
        new Position( 4, 2 ),
        BitMask.createSlashedBitset( 16, 0, 1, 0, 2 )
    ),
    SLASH_SOUTH_HALF_WEST_FULL(
        new Position( 5, 2 ),
        BitMask.createSlashedBitset( 16, 0, -2, 0, -1 )
    ),
    SLASH_NORTH_FULL_WEST_HALF(
        new Position( 6, 2 ),
        BitMask.createSlashedBitset( 16, 15, -1, 0, 2 )
    ),
    SLASH_NORTH_HALF_EAST_FULL(
        new Position( 7, 2 ),
        BitMask.createSlashedBitset( 16, -15, 2, 0, 1 )
    ),
    SLASH_SOUTH_FULL_EAST_HALF(
        new Position( 0, 3 ),
        BitMask.createSlashedBitset( 16, 31, -1, 0, 2 )
    )
    
    ;
    
    public final Position texturePosition;
    public final Rectangle collisionBounds;
    public final BitSet collisionBitset;
    
    private TileData( Position texturePosition, Rectangle collisionBounds ) {
        this.texturePosition = texturePosition;
        this.collisionBounds = collisionBounds;
        this.collisionBitset = null;
    }
    
    private TileData( Position texturePosition, BitSet collisionBitset ) {
        this.texturePosition = texturePosition;
        this.collisionBounds = null;
        this.collisionBitset = collisionBitset;
    }

}
