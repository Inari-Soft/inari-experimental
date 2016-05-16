package com.inari.experimental.tiles.baseground.micro;

import java.util.Arrays;
import java.util.Set;

import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.lang.list.DynArray;
import com.inari.firefly.asset.Asset;
import com.inari.firefly.component.ComponentId;
import com.inari.firefly.component.attr.Attribute;
import com.inari.firefly.component.attr.AttributeKey;
import com.inari.firefly.component.attr.AttributeMap;
import com.inari.firefly.component.attr.ComponentAttributeMap;
import com.inari.firefly.entity.ETransform;
import com.inari.firefly.entity.EntitySystem.Entity;
import com.inari.firefly.graphics.sprite.SpriteAsset;
import com.inari.firefly.graphics.tile.ETile;
import com.inari.firefly.graphics.tile.TileSystemEvent;
import com.inari.firefly.graphics.tile.TileSystemEvent.Type;
import com.inari.firefly.physics.collision.BitMask;
import com.inari.firefly.physics.collision.ECollision;
import com.inari.firefly.prototype.Prototype;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.utils.Disposable;

public final class MicroTilePrototype extends Prototype {
    
    public static final AttributeKey<Integer> CREATE_ATTR_TILE_XPOS = new AttributeKey<Integer>( "tileXPos", Integer.class, MicroTilePrototype.class );
    public static final AttributeKey<Integer> CREATE_ATTR_TILE_YPOS = new AttributeKey<Integer>( "tileYPos", Integer.class, MicroTilePrototype.class );
    public static final AttributeKey<Integer> CREATE_ATTR_TILE_GRID_ID = new AttributeKey<Integer>( "tileGridId", Integer.class, MicroTilePrototype.class );
    private static final AttributeMap ATTRIBUTES = new ComponentAttributeMap( null );
    private static final Attribute GRID_X_ATTR = new Attribute( CREATE_ATTR_TILE_XPOS, -1 );
    private static final Attribute GRID_Y_ATTR = new Attribute( CREATE_ATTR_TILE_YPOS, -1 );
    private static final Attribute GRID_ID = new Attribute( CREATE_ATTR_TILE_GRID_ID, -1 );
    private static final DynArray<ComponentId> CREATE_RESULT = new DynArray<ComponentId>( 1, 0 );
    
    public static final AttributeKey<MicroTileType> TILE_TYPE = new AttributeKey<MicroTileType>( "tileType", MicroTileType.class, MicroTilePrototype.class );
    public static final AttributeKey<String> TEXTURE_ASSET_NAME = new AttributeKey<String>( "textureAssetName", String.class, MicroTilePrototype.class );
    public static final AttributeKey<Integer> TEXTURE_ASSET_ID = new AttributeKey<Integer>( "textureAssetId", Integer.class, MicroTilePrototype.class );
    private static final AttributeKey<?>[] ATTRIBUTE_KEYS = new AttributeKey[] {
        TILE_TYPE
    };
    
    private MicroTileType tileType;
    private int textureAssetId;
    
    private int tileEntityId = -1;

    protected MicroTilePrototype( int id ) {
        super( id );
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
        
        tileType = attributes.getValue( TILE_TYPE, tileType );
        textureAssetId = attributes.getIdForName( TEXTURE_ASSET_NAME, TEXTURE_ASSET_ID, Asset.TYPE_KEY, textureAssetId );
    }

    @Override
    public final void toAttributes( AttributeMap attributes ) {
        super.toAttributes( attributes );
        
        attributes.put( TILE_TYPE, tileType );
        attributes.put( TEXTURE_ASSET_ID, textureAssetId );
    }

    @Override
    public final Disposable load( FFContext context ) {
        context.getComponentBuilder( Asset.TYPE_KEY )
            .set( SpriteAsset.NAME, tileType.name() )
            .set( SpriteAsset.TEXTURE_ASSET_ID, textureAssetId )
            .set( SpriteAsset.TEXTURE_REGION, tileType.bounds )
        .activate( SpriteAsset.class );
        
        int colllisionMaskId = -1;
        if ( tileType.collisionBitset != null ) {
            colllisionMaskId = context.getComponentBuilder( BitMask.TYPE_KEY )
                .set( BitMask.NAME, tileType.name() )
                .set( BitMask.WIDTH, 8 )
                .set( BitMask.HEIGHT, 8 )
                .set( BitMask.BITS, tileType.collisionBitset )
            .build();
        } 
        
        tileEntityId = context.getEntityBuilder()
            .set( ETransform.VIEW_NAME, MicroTileGroundLoad.TILE_GROUND_VIEW_NAME)
            .set( ETile.MULTI_POSITION, true )
            .set( ETile.SPRITE_ASSET_NAME, tileType.name() )
            .set( ECollision.SOLID, tileType.solid )
            .set( ECollision.CONTACT_TYPE, tileType.contactType )
            .set( ECollision.BOUNDING, new Rectangle( 0, 0, tileType.bounds.width, tileType.bounds.height ) )
            .set( ECollision.BIT_MASK_ID, colllisionMaskId )
        .activate();

        return this;
    }

    @Override
    public final void dispose( FFContext context ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public final DynArray<ComponentId> createOne( AttributeMap attributes ) {
        CREATE_RESULT.set( 
            0, 
            new ComponentId( Entity.ENTITY_TYPE_KEY, createOne( 
                attributes.getValue( CREATE_ATTR_TILE_XPOS, -1 ),
                attributes.getValue( CREATE_ATTR_TILE_YPOS, -1 ),
                attributes.getValue( CREATE_ATTR_TILE_GRID_ID, -1 )
            ) )
        );
        return CREATE_RESULT;
    }
    
    public final int createOne( int tileXPos, int tileYPos, int tileGridId ) {
        context.notify( 
            new TileSystemEvent( 
                Type.MULTIPOSITION_ADD, 
                tileGridId,
                tileEntityId,
                new Position( tileXPos, tileYPos )
            )
        );
        return tileEntityId;
    }

}
