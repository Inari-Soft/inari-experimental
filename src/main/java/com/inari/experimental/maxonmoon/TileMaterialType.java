package com.inari.experimental.maxonmoon;

import com.inari.commons.lang.aspect.Aspect;
import com.inari.commons.lang.aspect.AspectGroup;
import com.inari.firefly.physics.collision.CollisionSystem;

public enum TileMaterialType implements Aspect {
    SOLID,
    SEMI_SOLID_VERTICAL_DOWN
    //...
    ;
    
    private final Aspect aspect;
    
    private TileMaterialType() {
        aspect = CollisionSystem.MATERIAL_ASPECT_GROUP.createAspect( name() );
    }
    
    @Override
    public int index() {
        return aspect.index();
    }

    @Override
    public AspectGroup aspectGroup() {
        return aspect.aspectGroup();
    }
}
