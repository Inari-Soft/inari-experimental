package com.inari.experimental.maxonmoon;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.PositionF;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.graphics.RGBColor;
import com.inari.experimental.maxonmoon.TileLayerMapping.GameLayer;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.shape.EShape;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.external.ShapeData.Type;

public class Room0 extends Room {

    public Room0() {
        super( new Rectangle( 0, 0, 20 * 8 * 4, 15 * 8 ) );
        super.layerMapping.put( 
            GameLayer.STATIC_BACKGROUND, 
            new RoomLayer(
                new int[][] {
                    { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                    { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                    { 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 },
                    { 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2 },
                    { 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2 },
                    { 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2 },
                    { 3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
                    { 4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4 },
                    { 4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4 },
                    { 5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5 },
                    { 6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6 },
                    { 7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7 },
                    { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 },
                    { 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10 },
                    { 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10 }
                 },
                1
            )
         );
        
        super.layerMapping.put( 
            GameLayer.BACKGROUND_1, 
            new RoomLayer(
                new int[][] {
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,18,17,19,12 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,28,26,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,14,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,11,11,11,24 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,12,11,23,20,18,-1,-1,-1,-1,17,19,12,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,25,27,12,11,11,20,18,17,19,12,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,16,-1,-1,13,12,11,11,11,11,11,11,11,11,11,24,11,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,22,-1,15,12,11,11,11,11,11,11,11,11,11,11,11,24,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,11,11,14,21,11,11,11,11,11,11,11,11,11,11,11,11,11,28,26,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,11,11,25,27,24,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,20,18,-1,-1,-1,-1,17,19,28,26,11,11,23,24,11,11,28,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,21,11,11,20,18,17,19,12,11,11,11,25,27,12,11,24,11,11,11,28,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,12,11,11,11,11,24,11,11,11,25,27,12,11,11,11,11,28,26,11,11,11,11,11,11,11,11,11,11,11,23,28,26,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,13,12,11,11,11,11,11,11,11,24,11,23,12,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,23,11,11,11,28,26,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,17,19,12,11,11,11,11,11,11,11,11,11,23,12,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,25,27,11,11,11,11,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,17,19,12,11,11,11,11,11,11,11,11,11,11,23,12,11,11,11,11,25,27,24,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11 }
                },
                2
            )
        );
        
        super.layerMapping.put( 
            GameLayer.BACKGROUND_2, 
            new RoomLayer(
                new Position( 0, 9 ),
                new int[][] {
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,20,18,-1,-1 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,-1,-1,-1,13,12,14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,21,11,11,20,18 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,21,16,-1,-1,-1,-1,-1,-1,15,12,11,11,14,-1,-1,-1,-1,-1,-1,-1,21,16,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,12,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,22,-1,-1,-1,-1,-1,-1,21,11,11,11,11,20,18,-1,-1,-1,-1,13,12,22,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,12,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,12,11,11,14,-1,-1,-1,17,19,28,26,11,11,11,11,11,20,18,17,19,12,25,27,20,18,-1,-1,-1,-1,-1,17,19,12,11,11,11,11,11,11,11 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,19,12,11,11,11,11,11,14,-1,13,12,11,25,27,24,11,11,11,11,25,27,12,11,11,11,11,11,11,20,18,-1,17,19,12,11,11,11,11,11,11,11,11,11 }
                },
                3
            )
        );
        
        super.layerMapping.put( 
            GameLayer.MAIN, 
            new RoomLayer(
                new Position( 0, 8 ),
                new int[][] {
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,75,77,78,79,76,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,45,49,29 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,60,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,41,37,53,54 },
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,82,60,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,41,37,53,54,55 },
                    { 29,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,29,42,-1,-1,-1,-1,-1,60,86,75,76,-1,-1,29,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,57,58,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,57,59,59,58,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,45,49,37,53,54,55,56 },
                    { 29,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,29,-1,-1,-1,-1,-1,60,87,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,45,49,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,37,53,53,54,55,56,56 },
                    { 29,-1,-1,45,49,29,29,29,29,29,29,29,29,42,-1,-1,-1,-1,29,-1,-1,45,63,-1,75,78,76,-1,29,-1,-1,-1,-1,-1,45,49,29,29,29,29,29,37,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,54,54,55,56,56,56 },
                    { 29,29,29,37,53,53,53,53,53,53,53,53,53,38,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,29,37,53,53,53,53,53,53,53,54,54,54,54,53,54,54,54,54,54,53,53,54,54,54,53,54,54,54,54,53,54,54,54,54,54,53,54,54,54,54,54,55,55,56,56,56,56 },
                }
            )
        );
        
        super.layerMapping.put( 
            GameLayer.FOREGROUND, 
            new RoomLayer(
                new Position( 0, 11 ),
                new int[][] {
                    { -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,57,58,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 }
                }
            )
        );
    }

    @Override
    public void loadRoom( FFContext context ) {
        super.loadRoom( context );
        
        context.getEntityBuilder()
            .set( ETransform.LAYER_NAME, GameLayer.STATIC_BACKGROUND.name() )
            .set( ETransform.VIEW_NAME, Names.VIEWS.GAME_MAIN_VIEW )
            .set( ETransform.POSITION, new PositionF( 100, 0 ) )
            .set( EShape.FILL, true )
            .set( EShape.SHAPE_TYPE, Type.RECTANGLE )
            .set( EShape.VERTICES, new float[] { 30,20,1,1} )
            .add( EShape.COLORS, RGBColor.create( 255, 255, 255, 255 ) )
        .activate();
    }
    
    

}
