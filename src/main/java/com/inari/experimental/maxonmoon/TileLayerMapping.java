package com.inari.experimental.maxonmoon;

import com.inari.commons.geom.Rectangle;
import com.inari.firefly.component.build.ComponentBuilder;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.tile.ETile;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.system.FFContext;

public final class TileLayerMapping {
    
    public enum GameLayer {
        STATIC_BACKGROUND,
        BACKGROUND_1,
        BACKGROUND_2,
        MAIN,
        FOREGROUND
    }
    
    TileLayerMapping() {}
    
    private final int[][] layerTileMapping = new int[ 500 ][ GameLayer.values().length ];
    
    final void init( FFContext context ) {
        for ( TileSetA tile : TileSetA.values() ) {
            GameLayer[] layers = tile.layers != null? tile.layers : GameLayer.values();
            
            for ( GameLayer layer : layers ) {
                ComponentBuilder builder = context.getEntityBuilder()
                    .set( ETransform.VIEW_NAME, Names.VIEWS.GAME_MAIN_VIEW )
                    .set( ETransform.LAYER_NAME, layer.name() )
                    .set( ETile.SPRITE_ASSET_NAME, tile.name() );
                
//                if ( tile == TileSetA.TREE_TRUNK || tile == TileSetA.MRO_HEAD_MIDDLE ) {
//                    builder.set( ETile.TINT_COLOR, new RGBColor( .5f, .5f, .5f, 1f ) );
//                }
                
                if ( tile.collisionType != null ) {
                    builder
                        .set( ECollision.CONTACT_TYPE, tile.collisionType )
                        .set( ECollision.COLLISION_BOUNDS, new Rectangle( 0, 0, 8, 8 ) );
                    
                    if ( tile.collisionType.collisionMask != null ) {
                        builder.set( ECollision.COLLISION_MASK, tile.collisionType.collisionMask );
                    } 

                }
                if ( tile.materialType != null ) {
                    builder.set( ECollision.MATERIAL_TYPE, tile.materialType );
                }
                    
                int tileEntityId = builder.build();
            
                layerTileMapping[ tile.ordinal() ][ layer.ordinal() ] = tileEntityId;
            }
        }
    }
    
    public final int getTileEntityId( TileSetA tile, GameLayer layer ) {
        return layerTileMapping[ tile.ordinal() ][ layer.ordinal() ];
    }
    


}
