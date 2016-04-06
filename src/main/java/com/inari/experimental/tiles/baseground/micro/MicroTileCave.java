package com.inari.experimental.tiles.baseground.micro;

import java.util.Arrays;
import java.util.Set;

import com.inari.firefly.asset.Asset;
import com.inari.firefly.component.attr.AttributeKey;
import com.inari.firefly.component.attr.AttributeMap;
import com.inari.firefly.graphics.tile.TileGrid;
import com.inari.firefly.prototype.Prototype;
import com.inari.firefly.system.Disposable;
import com.inari.firefly.system.FFContext;

public final class MicroTileCave extends Asset {
    
    public static final String GRID_NAME = MicroTileGroundLoad.NAME + "_GRID";
    
    public static final AttributeKey<Integer> MAP_WIDTH = new AttributeKey<Integer>( "mapWidth", Integer.class, MicroTileCave.class );
    public static final AttributeKey<Integer> MAP_HEIGHT = new AttributeKey<Integer>( "mapHeight", Integer.class, MicroTileCave.class );
    public static final AttributeKey<String> MAP_DATA = new AttributeKey<String>( "mapData", String.class, MicroTileCave.class );
    private static final AttributeKey<?>[] ATTRIBUTE_KEYS = new AttributeKey[] { 
        MAP_WIDTH,
        MAP_HEIGHT,
        MAP_DATA
    };
    
    private int mapWidth;
    private int mapHeight;
    private String mapData;
    
    private int gridId = -1;

    protected MicroTileCave( int assetIntId ) {
        super( assetIntId );
    }
    
    @Override
    public final int getInstanceId( int index ) {
        return gridId;
    }

    @Override
    public final Disposable load( FFContext context ) {
        gridId = context.getComponentBuilder( TileGrid.TYPE_KEY )
            .set( TileGrid.NAME, GRID_NAME )
            .set( TileGrid.CELL_WIDTH, 8 )
            .set( TileGrid.CELL_HEIGHT, 8 )
            .set( TileGrid.WIDTH, mapWidth )
            .set( TileGrid.HEIGHT, mapHeight )
            .set( TileGrid.VIEW_NAME, MicroTileGroundLoad.TILE_GROUND_VIEW_NAME )
            .set( TileGrid.LAYER_ID, 0 )
            .set( TileGrid.WORLD_XPOS, 0 )
            .set( TileGrid.WORLD_YPOS, 0 )
        .activate();
        
        int index = 0;
        for ( int y = 0; y < mapHeight; y++ ) {
            for ( int x = 0; x < mapWidth; x++ ) {
                if ( index >= mapData.length() ) {
                    continue;
                }
                
                char tileChar = mapData.charAt( index );
                index++;
                if ( tileChar == ' ' || tileChar == '.' ) {
                    continue;
                }
                MicroTileType tileType = MicroTileType.forMapSymbol( tileChar );
                if ( tileType == null ) {
                    continue;
                }
                
                context.getSystemComponent( 
                    Prototype.TYPE_KEY, 
                    tileType.name(), 
                    MicroTilePrototype.class 
                ).createOne( x, y, gridId );
                
                
            }
        }
        
        return this;
    }

    @Override
    public final void dispose( FFContext context ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public final Set<AttributeKey<?>> attributeKeys() {
        Set<AttributeKey<?>> attributeKeys = super.attributeKeys();
        attributeKeys.addAll( Arrays.asList( ATTRIBUTE_KEYS ) );
        return attributeKeys;
    }

    @Override
    public final void fromAttributes( AttributeMap attributes ) {
        super.fromAttributes( attributes );
        
        mapWidth = attributes.getValue( MAP_WIDTH, mapWidth );
        mapHeight = attributes.getValue( MAP_HEIGHT, mapHeight );
        mapData = attributes.getValue( MAP_DATA, mapData );
    }

    @Override
    public final void toAttributes( AttributeMap attributes ) {
        super.toAttributes( attributes );
        
        attributes.put( MAP_WIDTH, mapWidth );
        attributes.put( MAP_HEIGHT, mapHeight );
        attributes.put( MAP_DATA, mapData );
    }

}
