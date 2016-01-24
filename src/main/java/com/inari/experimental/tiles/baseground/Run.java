package com.inari.experimental.tiles.baseground;

import java.util.Collection;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inari.firefly.component.attr.AttributeKey;
import com.inari.firefly.libgdx.GdxFFApplicationAdapter;
import com.inari.firefly.libgdx.GdxFirefly;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.movement.MovementSystem;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.task.Task;
import com.inari.firefly.task.TaskSystemEvent;
import com.inari.firefly.task.TaskSystemEvent.Type;

public class Run extends GdxFFApplicationAdapter {
    
    public static final String INIT_TASK_NAME = "RUN_TASK";

    @Override
    public String getTitle() {
        return "BASE-GROUND TILES";
    }
    
    public static void main (String[] arg) {
        try {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.resizable = false;
            config.width = 800;
            config.height = 600;
            config.fullscreen = false;
            new LwjglApplication( new Run(), config );
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

    @Override
    protected Collection<AttributeKey<?>> getDynamicAttributes() {
        Collection<AttributeKey<?>> dynamicAttributes = super.getDynamicAttributes();
        dynamicAttributes.add( GdxFirefly.DynamicAttributes.TEXTURE_COLOR_FILTER_NAME );
        return dynamicAttributes;
    }

    @Override
    protected final void init( FFContext context ) {
        context.loadSystem( MovementSystem.SYSTEM_KEY );
        context.loadSystem( CollisionSystem.SYSTEM_KEY );
        
        context.getComponentBuilder( Task.TYPE_KEY )
            .set( Task.REMOVE_AFTER_RUN, true )
            .set( Task.NAME, INIT_TASK_NAME )
        .build( LoadTask.class );
        context.notify( new TaskSystemEvent( Type.RUN_TASK, INIT_TASK_NAME ) );
    }

    @Override
    protected void resize( int width, int height, FFContext context ) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void pause( FFContext context ) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void resume( FFContext context ) {
        // TODO Auto-generated method stub
    }
}
