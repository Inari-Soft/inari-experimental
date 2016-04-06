package com.inari.experimental.tiles.baseground;

import com.badlogic.gdx.Input;
import com.inari.commons.geom.Position;
import com.inari.commons.geom.Rectangle;
import com.inari.commons.graphics.RGBColor;
import com.inari.firefly.control.task.Task;
import com.inari.firefly.control.task.TaskSystemEvent;
import com.inari.firefly.control.task.TaskSystemEvent.Type;
import com.inari.firefly.graphics.view.View;
import com.inari.firefly.graphics.view.ViewSystem;
import com.inari.firefly.system.external.FFInput;
import com.inari.firefly.system.external.FFInput.ButtonType;

public class LoadTask extends Task {

    protected LoadTask( int id ) {
        super( id );
    }

    @Override
    public void runTask() {
        FFInput input = context.getInput();
        input.mapKeyInput( ButtonType.UP, Input.Keys.W );
        input.mapKeyInput( ButtonType.DOWN, Input.Keys.S );
        input.mapKeyInput( ButtonType.RIGHT, Input.Keys.D );
        input.mapKeyInput( ButtonType.LEFT, Input.Keys.A );
        input.mapKeyInput( ButtonType.FIRE_1, Input.Keys.SPACE );
        input.mapKeyInput( ButtonType.ENTER, Input.Keys.ENTER );
        input.mapKeyInput( ButtonType.QUIT, Input.Keys.ESCAPE );

        context.getSystem( ViewSystem.SYSTEM_KEY )
            .getView( ViewSystem.BASE_VIEW_ID )
            .setClearColor( new RGBColor( .1f, .1f, .1f, 1  ) );
        context.getComponentBuilder( View.TYPE_KEY )
            .set( View.NAME, BaseGroundMapLoad.VIEW_NAME )
            .set( View.BOUNDS, new Rectangle(  4 * 16,  4 * 16, 2 * 21 * 16,  2 * 15 * 16 ) )
            .set( View.WORLD_POSITION, new Position( 0, 0 ) )
            .set( View.CLEAR_COLOR, new RGBColor( .8f, .8f, .8f, 1 ) )
            .set( View.ZOOM, 0.5f )
        .activate();
        
        context.getComponentBuilder( Task.TYPE_KEY )
            .set( BaseGroundMapLoad.NAME, BaseGroundMapLoad.TASK_NAME )
            .set( BaseGroundMapLoad.MAP_WIDTH, 21 )
            .set( BaseGroundMapLoad.MAP_HEIGHT, 15 )
        .buildAndNext( BaseGroundMapLoad.class )
            .set( PlayerHandle.NAME, PlayerHandle.PLAYER_NAME )
        .build( PlayerHandle.class );

        context.addProperty( 
            BaseGroundMapLoad.MAP_DATA,
            "000000000000000000000" +
            "08.................70" +
            "0...................0" +
            "0...................0" +
            "0...................0" +
            "0...................0" +
            "0...................0" +
            "0...................0" +
            "0.......O...........0" +
            "000000000......9....0" +
            "0............OG.....0" +
            "0..........OG.......0" +
            "05..I....OG........60" +
            "00A100000011111111G00" +
            "000000000000000000000"
        );
        
        context.notify( 
            new TaskSystemEvent( 
                Type.RUN_TASK, 
                context.getSystemComponentId( Task.TYPE_KEY, BaseGroundMapLoad.TASK_NAME ) 
            ) 
        );
        
        context.notify( 
            new TaskSystemEvent( 
                Type.RUN_TASK, 
                context.getSystemComponentId( Task.TYPE_KEY, PlayerHandle.PLAYER_NAME ) 
            ) 
        );
    }

}
