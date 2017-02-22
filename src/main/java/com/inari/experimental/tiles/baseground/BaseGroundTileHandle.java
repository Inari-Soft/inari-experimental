package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.component.build.ComponentBuilder;
import com.inari.firefly.entity.EntitySystem;
import com.inari.firefly.graphics.ETransform;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.tile.ETile;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.utils.Disposable;
import com.inari.firefly.system.utils.FFContextInitiable;
import com.inari.firefly.system.utils.Loadable;

public class BaseGroundTileHandle implements FFContextInitiable, Loadable, Disposable {
    
    private final BaseGroundTileType tileType;
    
    private FFContext context;
    private int entityId;
    
    private boolean loaded = false;
    
    public BaseGroundTileHandle( BaseGroundTileType tileType ) {
        this.tileType = tileType;
    }

    public final BaseGroundTileType getTileType() {
        return tileType;
    }
    
    @Override
    public void init( FFContext context ) {
        this.context = context;
        
        context.getComponentBuilder( Asset.TYPE_KEY, SpriteAsset.class )
            .set( SpriteAsset.NAME, tileType.name() )
            .set( SpriteAsset.TEXTURE_ASSET_NAME, BaseGroundMapLoad.BASE_GROUND_TILE_TEXTURE_NAME )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( tileType.texturePosition.x * 16, tileType.texturePosition.y * 16, 16, 16 ) )
        .activate(  );

        ComponentBuilder entityBuilder = context.getEntityBuilder()
            .set( ETransform.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME ) )
            .set( ETile.MULTI_POSITION, true )
            .set( ETile.SPRITE_ASSET_NAME, tileType.name() );
        
        if ( tileType.collisionMask != null ) {
            entityBuilder.set( 
                ECollision.COLLISION_MASK, 
                tileType.collisionMask 
            );
            
        } 
            
        entityBuilder.set( 
            ECollision.COLLISION_BOUNDS, 
            tileType.collisionBounds 
        );

        entityId = entityBuilder.build();
    }
    
    public int create( int tileXPos, int tileYPos ) {
        ETile tile = context.getEntityComponent( entityId, ETile.TYPE_KEY );
        tile.getGridPositions().add( new Position( tileXPos, tileYPos ) );
        return entityId;
    }
    
    @Override
    public Disposable load( FFContext context ) {
        if ( loaded ) {
            return this;
        }
        
        context.activateEntity( entityId );
        loaded = true;
        return this;
    }

    @Override
    public void dispose( FFContext context ) {
        if ( !loaded ) {
            return;
        }
        
        EntitySystem entitySystem = context.getSystem( EntitySystem.SYSTEM_KEY );
        
        ETile tile = entitySystem.getComponent( entityId, ETile.TYPE_KEY );
        entitySystem.deactivateEntity( entityId );
        tile.getGridPositions().clear();
        loaded = false;
    }
    
    public void delete() {
        context.deleteEntity( entityId );
        context.deleteSystemComponent( SpriteAsset.TYPE_KEY, tileType.name() );
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

}
