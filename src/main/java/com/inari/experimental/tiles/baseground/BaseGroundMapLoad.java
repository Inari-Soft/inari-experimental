package com.inari.experimental.tiles.baseground;

import java.util.Arrays;
import java.util.Set;

import com.inari.commons.lang.TypedKey;
import com.inari.commons.lang.list.DynArray;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.component.attr.AttributeKey;
import com.inari.firefly.component.attr.AttributeMap;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.graphics.TextureAsset;
import com.inari.firefly.graphics.tile.TileGrid;
import com.inari.firefly.graphics.view.View;

public class BaseGroundMapLoad extends Task {

    public static final TypedKey<String> MAP_DATA = TypedKey.create( "MAP_DATA", String.class );
    
    public static final String TASK_NAME = "BASE_GROUND";
    public static final String VIEW_NAME = TASK_NAME + "_VIEW";
    public static final String BASE_GROUND_TILE_TEXTURE_NAME = TASK_NAME + "_TILE_TEXTURE";
    public static final String BASE_GROUND_TILE_GRID_NAME = TASK_NAME + "_TILE_GRID";
    
    public static final AttributeKey<Integer> MAP_WIDTH = new AttributeKey<Integer>( "mapWidth", Integer.class, BaseGroundMapLoad.class );
    public static final AttributeKey<Integer> MAP_HEIGHT = new AttributeKey<Integer>( "mapHeight", Integer.class, BaseGroundMapLoad.class );
    private static final AttributeKey<?>[] ATTRIBUTE_KEYS = new AttributeKey[] { 
        MAP_WIDTH,
        MAP_HEIGHT
    };
    
    private DynArray<BaseGroundTileHandle> tileHandles = DynArray.create( BaseGroundTileHandle.class );

    private int mapWidth;
    private int mapHeight;
    
    protected BaseGroundMapLoad( int id ) {
        super( id );
    }

    @Override
    public void init() {
        super.init();
        
        context.getComponentBuilder( Asset.TYPE_KEY, TextureAsset.class )
            .set( TextureAsset.NAME, BASE_GROUND_TILE_TEXTURE_NAME )
            .set( TextureAsset.RESOURCE_NAME, "assets/tiles/base_ground/testCave.png" )
        .activate(  );
        
        context.getComponentBuilder( TileGrid.TYPE_KEY )
            .set( TileGrid.NAME, BASE_GROUND_TILE_GRID_NAME )
            .set( TileGrid.CELL_WIDTH, 16 )
            .set( TileGrid.CELL_HEIGHT, 16 )
            .set( TileGrid.WIDTH, mapWidth )
            .set( TileGrid.HEIGHT, mapHeight )
            .set( TileGrid.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, VIEW_NAME ) )
            .set( TileGrid.LAYER_ID, 0 )
            .set( TileGrid.WORLD_XPOS, 0 )
            .set( TileGrid.WORLD_YPOS, 0 )
        .build();
        
        for ( BaseGroundTileType tileType : BaseGroundTileType.values() ) {
            BaseGroundTileHandle tileHandle = new BaseGroundTileHandle( tileType );
            tileHandles.set( tileType.ordinal(), tileHandle );
            tileHandle.init( context );
        }
    }
    
    @Override
    public void runTask() {
        String data = context.getProperty( MAP_DATA );
        
        for ( BaseGroundTileHandle tileHandle : tileHandles ) {
            tileHandle.dispose( context );
        }
        
        int index = 0;
        for ( int y = 0; y < mapHeight; y++ ) {
            for ( int x = 0; x < mapWidth; x++ ) {
                if ( index >= data.length() ) {
                    continue;
                }
                
                BaseGroundTileType tileType = BaseGroundTileType.forMapSymbol( data.charAt( index ) );
                index++;
                if ( tileType == null ) {
                    continue;
                }
                
                tileHandles.get( tileType.ordinal() ).create( x, y );
                
            }
        }
        
        for ( BaseGroundTileHandle tileHandle : tileHandles ) {
            tileHandle.load( context );
        }
    }

    @Override
    public void dispose() {
        for ( BaseGroundTileHandle tileHandle : tileHandles ) {
            tileHandle.delete();
        }
        
        context.deleteSystemComponent( TileGrid.TYPE_KEY, BASE_GROUND_TILE_GRID_NAME );
        context.deleteSystemComponent( Asset.TYPE_KEY, BASE_GROUND_TILE_TEXTURE_NAME );
        
        super.dispose();
    }
    
    @Override
    public Set<AttributeKey<?>> attributeKeys() {
        Set<AttributeKey<?>> attributeKeys = super.attributeKeys();
        attributeKeys.addAll( Arrays.asList( ATTRIBUTE_KEYS ) );
        return attributeKeys;
    }

    @Override
    public void fromAttributes( AttributeMap attributes ) {
        super.fromAttributes( attributes );
        
        mapWidth = attributes.getValue( MAP_WIDTH, mapWidth );
        mapHeight = attributes.getValue( MAP_HEIGHT, mapHeight );
    }

    @Override
    public void toAttributes( AttributeMap attributes ) {
        super.toAttributes( attributes );
        
        attributes.put( MAP_WIDTH, mapWidth );
        attributes.put( MAP_HEIGHT, mapHeight );
    }


}
