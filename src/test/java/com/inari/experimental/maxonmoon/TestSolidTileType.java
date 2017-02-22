package com.inari.experimental.maxonmoon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inari.commons.geom.BitMask;

public class TestSolidTileType {
    
    @Test
    public void test() {
        BitMask mask = TileCollisionType.createSlashedBitMask( 15, -1, 0, 2 );
        assertEquals("", mask.toString() );
    }

}
