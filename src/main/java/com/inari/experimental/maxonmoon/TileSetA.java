package com.inari.experimental.maxonmoon;

import com.inari.experimental.maxonmoon.TileLayerMapping.GameLayer;

public enum TileSetA {
/*   0 */ SKY_1( 0, 1, GameLayer.STATIC_BACKGROUND ),
/*   1 */ SKY_1_2( 0, 2, GameLayer.STATIC_BACKGROUND ),
/*   2 */ SKY_2( 1, 1, GameLayer.STATIC_BACKGROUND ),
/*   3 */ SKY_2_3( 1, 2, GameLayer.STATIC_BACKGROUND ),
/*   4 */ SKY_3( 2, 1, GameLayer.STATIC_BACKGROUND ),
/*   5 */ SKY_3_4( 2, 2, GameLayer.STATIC_BACKGROUND ),
/*   6 */ SKY_4( 3, 1, GameLayer.STATIC_BACKGROUND ),
/*   0 */ SKY_4_5( 3, 2, GameLayer.STATIC_BACKGROUND ),
/*   0 */ SKY_5( 4, 1, GameLayer.STATIC_BACKGROUND ),
/*   0 */ SKY_5_6( 4, 2, GameLayer.STATIC_BACKGROUND ),
/*   0 */ SKY_6( 5, 1, GameLayer.STATIC_BACKGROUND ),
    
/*   0 */ MOUNT_FULL( 0, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ICORNER_4000( 0, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_0224( 1, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_2042( 1, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_0103( 2, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_1030( 2, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_0013( 3, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_0031( 3, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_1344( 4, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_3144( 4, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_1434( 5, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_SLOPE_4143( 5, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_0224( 6, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_2042( 6, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_0013( 7, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_0031( 7, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_1344( 8, 3, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
/*   0 */ MOUNT_ISLOPE_3144( 8, 4, GameLayer.BACKGROUND_1, GameLayer.BACKGROUND_2 ),
    
/*   0 */ GREEN_RED_GROUND_4400( 0, 5, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_0044( 0, 5, true, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_0404( 0, 6, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_4040( 0, 6, false, true, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_CORNER_4440( 1, 5, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_CORNER_4404( 1, 5, false, true, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
/*   0 */ GREEN_RED_GROUND_CORNER_4044( 1, 5, true, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_CORNER_0444( 1, 5, true, true, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_ICORNER_4000( 1, 6, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_ICORNER_0400( 1, 6, false, true, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_ICORNER_0040( 1, 6, true, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_ICORNER_0004( 1, 6, true, true, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_0224( 2, 5, false, false, TileCollisionType.SLOPE_0224, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_2042( 2, 5, false, true, TileCollisionType.SLOPE_2042, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_2402( 2, 5, true, false, TileCollisionType.SLOPE_2402, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_4220( 2, 5, true, true, TileCollisionType.SLOPE_4220, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_0013( 2, 6, false, false, TileCollisionType.SLOPE_0013, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_0031( 2, 6, false, true, TileCollisionType.SLOPE_0031, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_1300( 2, 6, true, false, TileCollisionType.SLOPE_1300, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_3100( 2, 6, true, true, TileCollisionType.SLOPE_3100, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_1344( 3, 5, false, false, TileCollisionType.SLOPE_1344, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_3144( 3, 5, false, true, TileCollisionType.SLOPE_3144, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_4413( 3, 5, true, false, TileCollisionType.SLOPE_4413, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_SLOPE_4431( 3, 5, true, true, TileCollisionType.SLOPE_4431, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_DARKEN_1( 4, 5, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_DARKEN_2( 4, 6, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_DARKEN_3( 5, 5, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    GREEN_RED_GROUND_DARKEN_4( 5, 6, false, false, TileCollisionType.FULL, TileMaterialType.SOLID, GameLayer.MAIN ),
    
    FLOWER_A_LEFT( 6, 5, GameLayer.MAIN, GameLayer.FOREGROUND ),
    FLOWER_A_RIGHT( 6, 5, false, true, GameLayer.MAIN, GameLayer.FOREGROUND ),
    FLOWER_A_MIDDLE( 6, 6, GameLayer.MAIN, GameLayer.FOREGROUND ),
    
    TREE_TRUNK( 7, 5, GameLayer.MAIN ),
    TREE_TRUNK_LEAF_LEFT( 7, 6, GameLayer.MAIN ),
    TREE_TRUNK_LEAF_RIGHT( 7, 6, false, true, GameLayer.MAIN ),
    TREE_TRUNK_LEAF_BOTTOM( 8, 5, GameLayer.MAIN ),
    TREE_BRANCH_RIGHT( 10, 5, GameLayer.MAIN ),
    TREE_BRANCH_LEFT( 10, 5, false, true, GameLayer.MAIN ),
    TREE_LEAF_RIGHT_TOP( 9, 6, GameLayer.MAIN ),
    TREE_LEAF_RIGHT_BOTTOM( 9, 6, true, false, GameLayer.MAIN ),
    TREE_LEAF_LEFT_TOP( 9, 6, false, true, GameLayer.MAIN ),
    TREE_LEAF_LEFT_BOTTOM( 9, 6, true, true, GameLayer.MAIN ),
    TREE_LEAF_TOP_1( 8, 6, GameLayer.MAIN ),
    TREE_LEAF_TOP_2( 9, 5, GameLayer.MAIN ),
    TREE_LEAF_BOTTOM( 10, 6, GameLayer.MAIN ),
    TREE_LEAF_LEFT( 11, 5, GameLayer.MAIN ),
    TREE_LEAF_RIGHT( 11, 5, false, true, GameLayer.MAIN ),
    
    MRO_HEAD_LEFT( 11, 6, TileCollisionType.SLOPE_0224, TileMaterialType.SEMI_SOLID_VERTICAL_DOWN, GameLayer.MAIN ),
    MRO_HEAD_RIGHT( 11, 6, false, true, TileCollisionType.SLOPE_2042, TileMaterialType.SEMI_SOLID_VERTICAL_DOWN, GameLayer.MAIN ),
    MRO_HEAD_MIDDLE_LEFT( 12, 5, TileCollisionType.FULL, TileMaterialType.SEMI_SOLID_VERTICAL_DOWN, GameLayer.MAIN ),
    MRO_HEAD_MIDDLE( 12, 6, TileCollisionType.FULL, TileMaterialType.SEMI_SOLID_VERTICAL_DOWN, GameLayer.MAIN ),
    MRO_HEAD_MIDDLE_RIGHT( 12, 5, false, true, TileCollisionType.FULL, TileMaterialType.SEMI_SOLID_VERTICAL_DOWN, GameLayer.MAIN ),
    MRO_HEAD_LEFT_ON_BRANCH( 13, 5, GameLayer.MAIN ),
    MRO_HEAD_RIGHT_ON_BRANCH( 13, 5, false, true, GameLayer.MAIN ),
    MRO_PICOLO_LEFT( 13, 6, GameLayer.MAIN ),
    MRO_PICOLO_RIGHT( 13, 6, false, true, GameLayer.MAIN ),
    MRO_BRANCH_BIG_LEFT( 14, 5, GameLayer.MAIN ),
    MRO_BRANCH_BIG_RIGHT( 14, 5, false, true, GameLayer.MAIN ),
    MRO_SMALL( 14, 6, GameLayer.MAIN ),
    MRO_BRANCH_SMALL_LEFT( 15, 5, GameLayer.MAIN ),
    MRO_BRANCH_SMALL_RIGHT( 15, 5, false, true, GameLayer.MAIN )
    
    ;
    
    //public final String textureName; // TODO
    public final int textureXPos;
    public final int textureYPos;
    public final boolean flipHorizontal;
    public final boolean flipVertical;
    public final TileCollisionType collisionType;
    public final TileMaterialType materialType;
    public final GameLayer[] layers;
    
    private TileSetA( int textureXPos, int textureYPos ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = false;
        this.flipVertical = false;
        this.collisionType = null;
        this.layers = null;
        materialType = null;
    }
    
    private TileSetA( int textureXPos, int textureYPos, GameLayer... layers ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = false;
        this.flipVertical = false;
        this.collisionType = null;
        this.layers = layers;
        materialType = null;
    }
    
    private TileSetA( int textureXPos, int textureYPos, TileCollisionType collisionType, TileMaterialType materialType, GameLayer... layers ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = false;
        this.flipVertical = false;
        this.collisionType = collisionType;
        this.layers = layers;
        this.materialType = materialType;
    }
    
    private TileSetA( int textureXPos, int textureYPos, boolean flipHorizontal, boolean flipVertical, GameLayer... layers ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
        this.collisionType = null;
        this.layers = layers;
        materialType = null;
    }
    
//    private TileSetA( int textureXPos, int textureYPos, boolean flipHorizontal, boolean flipVertical, TileMaterialType materialType, GameLayer... layers ) {
//        this.textureXPos = textureXPos;
//        this.textureYPos = textureYPos;
//        this.flipHorizontal = flipHorizontal;
//        this.flipVertical = flipVertical;
//        this.collisionType = null;
//        this.layers = layers;
//        this.materialType = materialType;
//    }
    
    private TileSetA( int textureXPos, int textureYPos, boolean flipHorizontal, boolean flipVertical, TileCollisionType collisionType, TileMaterialType materialType ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
        this.collisionType = collisionType;
        this.layers = null;
        this.materialType = materialType;
    }
    
    private TileSetA( int textureXPos, int textureYPos, boolean flipHorizontal, boolean flipVertical, TileCollisionType collisionType, TileMaterialType materialType, GameLayer... layers ) {
        this.textureXPos = textureXPos;
        this.textureYPos = textureYPos;
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
        this.collisionType = collisionType;
        this.layers = layers;
        this.materialType = materialType;
    }
}
