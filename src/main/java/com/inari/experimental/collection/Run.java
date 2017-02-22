package com.inari.experimental.collection;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inari.firefly.control.ControllerSystem;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.control.task.TaskSystemEvent;
import com.inari.firefly.control.task.TaskSystemEvent.Type;
import com.inari.firefly.libgdx.GdxFFApplicationAdapter;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.movement.MovementSystem;
import com.inari.firefly.prototype.PrototypeSystem;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.info.FrameRateInfo;

public class Run extends GdxFFApplicationAdapter {
    
    @Override
    public String getTitle() {
        return "EXPERIMENTAL COLLECTION";
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
    protected final void init( FFContext context ) {
        context.loadSystem( ControllerSystem.SYSTEM_KEY );
        context.loadSystem( MovementSystem.SYSTEM_KEY );
        context.loadSystem( CollisionSystem.SYSTEM_KEY );
        context.loadSystem( PrototypeSystem.SYSTEM_KEY );
        
//        context.getComponentBuilder( Task.TYPE_KEY )
//            .set( Task.REMOVE_AFTER_RUN, true )
//            .set( Task.NAME,  )
//        .build(  );
//        
//        context.notify( new TaskSystemEvent( Type.RUN_TASK,  ) );
    }

    @Override
    protected void resize( int width, int height, FFContext context ) {

    }

    @Override
    protected void pause( FFContext context ) {

    }

    @Override
    protected void resume( FFContext context ) {

    }

}
