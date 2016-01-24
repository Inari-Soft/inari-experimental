package com.inari.experimental.tiles.baseground;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.Disposable;
import com.inari.firefly.Loadable;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.asset.AssetSystem;
import com.inari.firefly.component.build.ComponentBuilder;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.entity.EntitySystem;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.tile.ETile;
import com.inari.firefly.physics.collision.BitMask;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.FFContextInitiable;
import com.inari.firefly.system.view.View;

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
        
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, tileType.name() )
            .set( SpriteAsset.TEXTURE_ASSET_ID, context.getTextureId( BaseGroundMapLoad.BASE_GROUND_TILE_TEXTURE_NAME ) )
            .set( SpriteAsset.TEXTURE_REGION, new Rectangle( tileType.texturePosition.x * 16, tileType.texturePosition.y * 16, 16, 16 ) )
        .activate( SpriteAsset.class );

        ComponentBuilder entityBuilder = context.getEntityBuilder()
            .set( ETransform.VIEW_ID, context.getSystemComponentId( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME ) )
            .set( ETile.MULTI_POSITION, true )
            .set( ETile.SPRITE_ID, context.getSpriteId( tileType.name() ) );
        
        if ( tileType.collisionBitset != null ) {
            context.getComponentBuilder( BitMask.TYPE_KEY )
                .set( BitMask.NAME, tileType.name() )
                .set( BitMask.WIDTH, 16 )
                .set( BitMask.HEIGHT, 16 )
                .set( BitMask.BITS, tileType.collisionBitset )
            .build();
            
            entityBuilder.set( 
                ECollision.BIT_MASK_ID, 
                context.getSystemComponentId( BitMask.TYPE_KEY, tileType.name() ) 
            );
            
        } else if ( tileType.collisionBounds != null ) {
            
            entityBuilder.set( 
                ECollision.BOUNDING, 
                tileType.collisionBounds 
            );
            
        }

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

}
