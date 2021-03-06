package com.inari.experimental.tiles.baseground;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inari.commons.geom.Rectangle;
import com.inari.firefly.control.ControllerSystem;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.control.task.TaskSystemEvent;
import com.inari.firefly.control.task.TaskSystemEvent.Type;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.libgdx.GdxFFApplicationAdapter;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.movement.MovementSystem;
import com.inari.firefly.system.FFContext;

public class Run extends GdxFFApplicationAdapter {
    
    public static final String BASE_NAME = "BASE_GROUND";
    public static final String INIT_TASK_NAME = "RUN_TASK";


    @Override
    public String getTitle() {
        return "BASE-GROUND TILES";
    }
    
    public static void main (String[] arg) {
        try {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.resizable = true;
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
        
        context.getComponentBuilder( Task.TYPE_KEY, LoadTask.class )
            .set( Task.REMOVE_AFTER_RUN, true )
            .set( Task.NAME, INIT_TASK_NAME )
        .build(  );
        
        context.notify( new TaskSystemEvent( Type.RUN_TASK, INIT_TASK_NAME ) );
    }

    @Override
    protected void resize( int width, int height, FFContext context ) {
        View baseView = context.getSystemComponent( View.TYPE_KEY, 0 );
        if ( baseView != null ) {
            Rectangle baseViewBounds = baseView.getBounds();
//            float normalDiv = (float) baseViewBounds.width / baseViewBounds.height;
//            float screenDiv = (float) context.getScreenWidth() / (float) context.getScreenHeight();
            float widthDiv =  (float) baseViewBounds.width / (float) context.getScreenWidth() ;
            float heightDiv = (float) baseViewBounds.height / (float) context.getScreenHeight();
            float minDiv = Math.min( widthDiv, heightDiv );
            System.out.println("minDiv: " + minDiv);
            
                View view = context.getSystemComponent( View.TYPE_KEY, BaseGroundMapLoad.VIEW_NAME );
            if ( minDiv < 1 ) {
                view.setZoom( 1 / ( 0.5f * minDiv ) );
            }
        }
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
