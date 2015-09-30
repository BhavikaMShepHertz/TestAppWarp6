/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warp.Test;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.Oneway;

public class RoomListen implements RoomRequestListener{

    private Testauto container;
    RoomListen(Testauto container)
    {
    this.container = container;
    }
    
    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent roominfo)
    {
    	System.out.println("UpdatePropertyDone "+roominfo.getProperties().size());
    	container.result(0);
      //container.appendResponseResult("UpdatePropertyDone "+roominfo.getProperties().size());
    }
     
    @Override
    public void onLockPropertiesDone(byte bt)
    {
    	System.out.println("LockPropertiesDone "+bt);
      //container.appendResponseResult("LockPropertiesDone "+bt);
    }
    
    @Override
    public void onUnlockPropertiesDone(byte bt)
    {
    	System.out.println("UnlockPropertiesDone "+bt);
      //container.appendResponseResult("UnlockPropertiesDone "+bt);
    	
    }
    
    @Override
    public void onSubscribeRoomDone(RoomEvent event) {
        //container.appendResponseResult("SubscribeRoom "+event.getData().getName());
    	//System.out.println("SubscribeRoom "+event.getData().getName());
    	container.result(event.getResult());
    	
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent event) {
          //container.appendResponseResult("UnSubscribeRoom "+event.getData().getName());
    	
    	System.out.println(event.getData().getName());
    	container.result(event.getResult());
    }

    @Override
    public void onJoinRoomDone(RoomEvent event) {
         //container.appendResponseResult("onJoinRoom "+event.getResult());
    	//System.out.println("Room Joined--"+event.getData().getName());
    	System.out.println("Room Joined--");
    	container.result(event.getResult());
    }

    @Override
    public void onLeaveRoomDone(RoomEvent event) {
    	 System.out.println(event.getData().getName());
         System.out.println(event.getData().getId());
    	container.result(event.getResult());
          //container.appendResponseResult("Leave Room "+event.getData().getName());
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent event) {
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            String[] users = event.getJoinedUsers();
            String result = "";
            System.out.println("Room Info:");
            System.out.println("Room Name.."+event.getData().getName());
            System.out.println("Room ID.."+event.getData().getId());
            System.out.println("No. of users.."+users.length);
//          System.out.println(event.getData().getName()+"(Room Id= " +event.getData().getId()+") Total users "+users.length);
            container.result(event.getResult());
            //for(int i=0; i<users.length; i++){
            // result += " "+users[i];                
            //}
          //container.appendResponseResult(result);
        }
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent event) {
       //container.appendResponseResult("CustomRoomData "+event.getData().getName()+" "+event.getCustomData().toString());
    }
    
   
    
    
}
