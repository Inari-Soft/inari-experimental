package com.inari.experimental.maxonmoon;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.PositionF;
import com.inari.commons.geom.Rectangle;
import com.inari.experimental.maxonmoon.TileLayerMapping.GameLayer;
import com.inari.firefly.component.build.ComponentBuilder;
import com.inari.firefly.control.Controller;
import com.inari.firefly.controller.view.CameraPivot;
import com.inari.firefly.controller.view.SimpleCameraController;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.tile.ETile;
import com.inari.firefly.graphics.tile.TileGrid;
import com.inari.firefly.graphics.view.ViewChangeEvent;
import com.inari.firefly.graphics.view.ViewChangeEvent.Type;
import com.inari.firefly.graphics.view.ViewChangeListener;
import com.inari.firefly.graphics.view.ViewSystem;
import com.inari.firefly.system.FFContext;

public abstract class Room implements ViewChangeListener {
    
    protected final Rectangle roomWorldBounds;
    protected EnumMap<GameLayer, RoomLayer> layerMapping = new EnumMap<GameLayer, RoomLayer>( GameLayer.class );
    
    protected Set<Integer> usedEntities = new HashSet<Integer>();
    
    protected Room( Rectangle roomWorldBounds ) {
        this.roomWorldBounds = roomWorldBounds;
    }

    public final Rectangle getRoomWorldBounds() {
        return roomWorldBounds;
    }

    public final boolean hasLayer( GameLayer layer ) {
        return layerMapping.containsKey( layer );
    }
    
    public void loadRoom( final FFContext context ) {
        final TileLayerMapping tileLayerMapping = context.getProperty( Names.PROPERTIES.TILE_LAYER_MAPPING_KEY );
        final TileSetA[] tiles = TileSetA.values();
        
        for ( GameLayer layer : GameLayer.values() ) {
            RoomLayer roomLayer = layerMapping.get( layer );
            if ( roomLayer != null ) {
                context.getSystem( ViewSystem.SYSTEM_KEY ).activateLayer( layer.name() );

                ComponentBuilder tileMapBuilder = context.getComponentBuilder( TileGrid.TYPE_KEY )
                    .set( TileGrid.NAME, layer.name() )
                    .set( TileGrid.VIEW_NAME, Names.VIEWS.GAME_MAIN_VIEW )
                    .set( TileGrid.LAYER_NAME, layer.name() )
                    .set( TileGrid.CELL_WIDTH, 8 )
                    .set( TileGrid.CELL_HEIGHT, 8 )
                    .set( TileGrid.WIDTH, roomLayer.tileMap[ 0 ].length )
                    .set( TileGrid.HEIGHT, roomLayer.tileMap.length );
                
                if ( roomLayer.position != null ) {
                    tileMapBuilder
                        .set( TileGrid.WORLD_XPOS, ( roomLayer.position.x * 8 ) )
                        .set( TileGrid.WORLD_YPOS, ( roomLayer.position.y * 8 ) );
                }
                
                tileMapBuilder.build();
                
                roomLayer.tileGrid = context.getSystemComponent( TileGrid.TYPE_KEY, layer.name() );
                
                for ( int y = 0; y < roomLayer.tileMap.length; y++ ) {
                    for ( int x = 0; x < roomLayer.tileMap[ y ].length; x++ ) {
                        if ( roomLayer.tileMap[ y ][ x ] < 0 ) {
                            continue;
                        }
                        
                        int tileEntityId = tileLayerMapping.getTileEntityId( tiles[ roomLayer.tileMap[ y ][ x ] ], layer );
                        ETile tile = context.getEntityComponent( tileEntityId, ETile.TYPE_KEY );
                        tile.getGridPositions().add( new Position( x, y ) );
                        usedEntities.add( tileEntityId );
                    }
                }
            }
        }
        
        for( int entityId : usedEntities ) {
            context.activateEntity( entityId );
        }
        
        // add player pivot to camera
        SimpleCameraController simpleCameraController = context.getSystemComponent( 
            Controller.TYPE_KEY, 
            Names.CONTROLLER.GAME_CAMERA_CONTROLLER, 
            SimpleCameraController.class 
        );
        
        simpleCameraController.setSnapToBounds( roomWorldBounds );
        simpleCameraController.setPivot( 
            new CameraPivot() {
                
                private final PositionF playerPos = new PositionF();
                private final ETransform playerTransform = context.getEntityComponent( Names.PLAYER.NAME, ETransform.TYPE_KEY );
                
                @Override
                public void init( FFContext context ) {
                }
                
                @Override
                public PositionF getPivot() {
                    playerPos.x = playerTransform.getXpos();
                    playerPos.y = playerTransform.getYpos();
                    return playerPos;
                }
            }  
        );
        
        context.registerListener( ViewChangeEvent.TYPE_KEY, this );
    }
    
    @Override
    public final void onViewChange( ViewChangeEvent event ) {
        if ( event.getEventType() != Type.ORIENTATION ) {
            return;
        }
        
        final PositionF worldPosition = event.getView().getWorldPosition();
        for ( GameLayer layer : GameLayer.values() ) {
            if ( layer == GameLayer.MAIN ) {
                continue;
            }
            
            RoomLayer roomLayer = layerMapping.get( layer );
            if ( roomLayer == null || roomLayer.scrollFactor < 0 ) {
                continue;
            }
            
            roomLayer.tileGrid.setWorldXPos( ( worldPosition.x / roomLayer.scrollFactor ) + roomLayer.position.x * 8 ); 
            roomLayer.tileGrid.setWorldYPos( ( worldPosition.y / roomLayer.scrollFactor ) + roomLayer.position.y * 8 ); 
            //System.out.println( "layer:  " + layer + " xpos: " + roomLayer.tileGrid.getWorldXPos() + " ypos: " + roomLayer.tileGrid.getWorldYPos());
        }
    }
    
    public void disposeRoom( FFContext context ) {
        for( int entityId : usedEntities ) {
            context.deactivateEntity( entityId );
            ETile tile = context.getEntityComponent( entityId, ETile.TYPE_KEY );
            tile.getGridPositions().clear();
        }
        
        // TODO remove TileGrids and Layers of the room
        
        context.disposeListener( ViewChangeEvent.TYPE_KEY, this );
    }
    
    public final static class RoomLayer {
        
        final Position position;
        final int[][] tileMap;
        final int scrollFactor;
        
        TileGrid tileGrid;
        
        public RoomLayer( int[][] tileMap ) {
            this.position = new Position( 0, 0 );
            this.tileMap = tileMap;
            this.scrollFactor = -1;
        }
        
        public RoomLayer( int[][] tileMap, int scrollFactor ) {
            this.position = new Position( 0, 0 );
            this.tileMap = tileMap;
            this.scrollFactor = scrollFactor;
        }
        
        public RoomLayer( Position position, int[][] tileMap, int scrollFactor ) {
            this.position = position;
            this.tileMap = tileMap;
            this.scrollFactor = scrollFactor;
        }
        
        public RoomLayer( Position position, int[][] tileMap ) {
            this.position = position;
            this.tileMap = tileMap;
            this.scrollFactor = -1;
        }

        public final Position getPosition() {
            return position;
        }
        
        public final int[][] getTileMap() {
            return tileMap;
        }
        
        public final int getScrollFactor() {
            return scrollFactor;
        }
    }
}
