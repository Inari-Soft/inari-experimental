package com.inari.experimental.maxonmoon;

import com.inari.commons.lang.TypedKey;

public interface Names {
    
    interface PROPERTIES {
        TypedKey<Integer> ZOOM_FACTOR_TYPE_KEY = TypedKey.create( "ZOOM_FACTOR", Integer.class );
        TypedKey<TileLayerMapping> TILE_LAYER_MAPPING_KEY = TypedKey.create( "TILE_LAYER_MAPPING", TileLayerMapping.class );
    }
    
    interface TEXTURES {
        String GAME_TILE_TEXTURE = "GAME_TILE_TEXTURE";
    }
    
    interface TASKS {
        String LOAD_TASK = "LOAD_TASK";
        
    }
    
    interface VIEWS {
        String GAME_MAIN_VIEW = "GAME_MAIN_VIEW";
        String GAME_STATUS_VIEW = "GAME_STATUS_VIEW";
        String GAME_INVENTORY_VIEW = "GAME_INVENTORY_VIEW";
    }
    
    interface CONTROLLER {
        String GAME_CAMERA_CONTROLLER = "GAME_CAMERA_CONTROLLER";
        String GAME_LAYER_VIEW_CONTROLLER = "GAME_LAYER_VIEW_CONTROLLER";
    }
    
    interface PLAYER {
        String SPRITE = "PLAYER_SPRITE";
        String COLLISION_RESOLVER = "PLAYER_COLLISION_RESOLVER";
        String BODY_CONTACT_SCAN = "PLAYER_BODY_CONTACT_SCAN";
        String HEAD_CONTACT_SCAN = "PLAYER_HEAD_CONTACT_SCAN";
        String GROUND_CONTACT_SCAN = "PLAYER_GROUND_CONTACT_SCAN";
        String GROUND_CONTACT_SCAN_SEMI_SOLID = "PLAYER_GROUND_CONTACT_SCAN_SEMI_SOLID";
        String NAME = "PLAYER_NAME";
        String HORIZONTAL_ANIMATION = "PLAYER_HORIZONTAL_ANIMATION";
        String VERTICAL_ANIMATION = "PLAYER_VERTICAL_ANIMATION";
    }

}
