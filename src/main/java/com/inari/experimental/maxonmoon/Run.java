package com.inari.experimental.maxonmoon;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inari.firefly.control.ControllerSystem;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.control.task.TaskSystemEvent;
import com.inari.firefly.control.task.TaskSystemEvent.Type;
import com.inari.firefly.libgdx.GdxFFApplicationAdapter;
import com.inari.firefly.physics.collision.CollisionSystem;
import com.inari.firefly.physics.movement.MovementSystem;
import com.inari.firefly.system.FFContext;
import com.inari.firefly.system.info.FrameRateInfo;

public class Run extends GdxFFApplicationAdapter {
    
    public static final int ZOOM_FACTOR = 4;
    
    
    @Override
    public String getTitle() {
        return "Max On Moon";
    }
    
    
    public static void main (String[] arg) {
        try {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            System.out.println( "display width: " + config.getDesktopDisplayMode().width );
            System.out.println( "display height: " + config.getDesktopDisplayMode().height );
            for ( DisplayMode mode : config.getDisplayModes() ) {
                System.out.println( "display width: " + mode.width + " height: " + mode.height );
            }
            
            config.resizable = true;
            config.width = 20 * 8 * ZOOM_FACTOR;
            config.height = 16 * 8 * ZOOM_FACTOR;
            config.fullscreen = false;
            config.vSyncEnabled = true;
            new LwjglApplication( new Run(), config );
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

    @Override
    protected final void init( FFContext context ) {
        context.loadSystem( MovementSystem.SYSTEM_KEY );
        context.loadSystem( CollisionSystem.SYSTEM_KEY );
        context.loadSystem( ControllerSystem.SYSTEM_KEY, true );
        
        context.getSystemInfoDisplay()
            .addSystemInfo( new FrameRateInfo() )
            .setActive( true );
        
        context.setProperty( Names.PROPERTIES.ZOOM_FACTOR_TYPE_KEY, ZOOM_FACTOR );
        
        context.getComponentBuilder( Task.TYPE_KEY, LoadGame.class )
            .set( Task.NAME, Names.TASKS.LOAD_TASK )
        .build(  );
        
        context.notify( new TaskSystemEvent( Type.RUN_TASK, Names.TASKS.LOAD_TASK ) );
    }

    @Override
    protected void resize( int width, int height, FFContext context ) {

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
