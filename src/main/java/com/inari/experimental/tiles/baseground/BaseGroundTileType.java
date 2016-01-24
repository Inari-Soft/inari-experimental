package com.inari.experimental.tiles.baseground;

import java.util.BitSet;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.physics.collision.BitMask;

public enum BaseGroundTileType {
    FULL( 
        '0',
        new Position( 0, 0 ), 
        new Rectangle( 0, 0, 16, 16 ) 
    ),
    HALF_SOUTH(
        '1',
        new Position( 1, 0 ), 
        new Rectangle( 0, 8, 16, 8 )
    ),
    HALF_WEST( 
        '2',
        new Position( 2, 0 ), 
        new Rectangle( 0, 0, 8, 16 )
    ),
    HALF_NORTH( 
        '3',
        new Position( 3, 0 ), 
        new Rectangle( 0, 0, 16, 8 )
    ),
    HALF_EAST( 
        '4',
        new Position( 4, 0 ), 
        new Rectangle( 8, 0, 8, 16 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL(
        '5',
        new Position( 5, 0 ),
        BitMask.createSlashedBitset( 16, 0, -1, 0, -1 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL(
        '6',
        new Position( 6, 0 ),
        BitMask.createSlashedBitset( 16, -15, 1, 0, -1 )
    ),
    SLASH_NORTH_FULL_EAST_FULL(
        '7',
        new Position( 7, 0 ),
        BitMask.createSlashedBitset( 16, 0, 1, 0, 1 )
    ),
    SLASH_NORTH_FULL_WEST_FULL(
        '8',
        new Position( 0, 1 ),
        BitMask.createSlashedBitset( 16, 15, -1, 0, 1 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL_NORTH_HALF(
        '9',
        new Position( 1, 1 ),
        BitMask.createSlashedBitset( 16, -14, 2, 0, -1 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL_EAST_HALF(
        'A',
        new Position( 2, 1 ),
        BitMask.createSlashedBitset( 16, 1, -1, 0, -2 )
    ),
    SLASH_NORTH_FULL_WEST_FULL_SOUTH_HALF(
        'B',
        new Position( 3, 1 ),
        BitMask.createSlashedBitset( 16, 31, -2, 0, 1 )
    ),
    SLASH_NORTH_FULL_EAST_FULL_WEST_HALF(
        'C',
        new Position( 4, 1 ),
        BitMask.createSlashedBitset( 16, 16, 1, 0, 2 )
    ),
    SLASH_SOUTH_FULL_WEST_FULL_NORTH_HALF(
        'D',
        new Position( 5, 1 ),
        BitMask.createSlashedBitset( 16, 16, -2, 0, -1 )
    ),
    SLASH_NORTH_FULL_WEST_FULL_EAST_HALF(
        'E',
        new Position( 6, 1 ),
        BitMask.createSlashedBitset( 16, 31, -1, 0, 2 )
    ),
    SLASH_NORTH_FULL_EAST_FULL_SOUTH_HALF(
        'F',
        new Position( 7, 1 ),
        BitMask.createSlashedBitset( 16, 16, 1, 0, 2 )
    ),
    SLASH_SOUTH_FULL_EAST_FULL_WEST_HALF(
        'G',
        new Position( 0, 2 ),
        BitMask.createSlashedBitset( 16, -14, 1, 0, -2 )
    ),
    SLASH_SOUTH_HALF_EAST_FULL(
        'H',
        new Position( 1, 2 ),
        BitMask.createSlashedBitset( 16, -30, 2, 0, -1 )
    ),
    SLASH_SOUTH_FULL_WEST_HALF(
        'I',
        new Position( 2, 2 ),
        BitMask.createSlashedBitset( 16, -15, -1, 0, -2 )
    ),
    SLASH_NORTH_HELF_WEST_FULL(
        'J',
        new Position( 3, 2 ),
        BitMask.createSlashedBitset( 16, 15, -2, 0, 1 )
    ),
    SLASH_NORTH_FULL_EAST_HALF(
        'K',
        new Position( 4, 2 ),
        BitMask.createSlashedBitset( 16, 0, 1, 0, 2 )
    ),
    SLASH_SOUTH_HALF_WEST_FULL(
        'L',
        new Position( 5, 2 ),
        BitMask.createSlashedBitset( 16, 0, -2, 0, -1 )
    ),
    SLASH_NORTH_FULL_WEST_HALF(
        'M',
        new Position( 6, 2 ),
        BitMask.createSlashedBitset( 16, 15, -1, 0, 2 )
    ),
    SLASH_NORTH_HALF_EAST_FULL(
        'N',
        new Position( 7, 2 ),
        BitMask.createSlashedBitset( 16, -15, 2, 0, 1 )
    ),
    SLASH_SOUTH_FULL_EAST_HALF(
        'O',
        new Position( 0, 3 ),
        BitMask.createSlashedBitset( 16, 31, -1, 0, 2 )
    )
    
    ;
    
    public final char mapSymbol;
    public final Position texturePosition;
    public final Rectangle collisionBounds;
    public final BitSet collisionBitset;
    
    private BaseGroundTileType( char mapSymbol, Position texturePosition, Rectangle collisionBounds ) {
        this.mapSymbol = mapSymbol;
        this.texturePosition = texturePosition;
        this.collisionBounds = collisionBounds;
        this.collisionBitset = null;
    }
    
    private BaseGroundTileType( char mapSymbol, Position texturePosition, BitSet collisionBitset ) {
        this.mapSymbol = mapSymbol;
        this.texturePosition = texturePosition;
        this.collisionBounds = null;
        this.collisionBitset = collisionBitset;
    }

    public static BaseGroundTileType forMapSymbol( char mapSymbol ) {
        for ( BaseGroundTileType type : BaseGroundTileType.values() ) {
            if ( type.mapSymbol == mapSymbol ) {
                return type;
            }
        }
        return null;
    }

}
