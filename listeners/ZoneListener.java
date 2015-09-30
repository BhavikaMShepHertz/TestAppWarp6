/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warp.Test;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

/**
 *
 * @author DHRUV CHOPRA
 */
public class ZoneListener implements ZoneRequestListener {
    
    private Testauto container;

    ZoneListener(Testauto container){
        this.container = container;
    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent event)
    {
      //container.appendResponseResult("GetMatchedRooms "+event.getRoomsData().toString());
    }
    
    @Override
    public void onCreateRoomDone(RoomEvent event) {
    	System.out.println("onCreateRoom roomname "+event.getData().getName()+" id"+event.getData().getId());
    	if(event.getData()!=null)
			try {
				container.setRoomId(event.getData().getId());
			} 
    	catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       container.result(event.getResult());
    	//container.appendResponseResult("onCreateRoom roomname "+event.getData().getName()+" id"+event.getData().getId());
    }
    @Override
    public void onDeleteRoomDone(RoomEvent event) {
      //container.appendResponseResult("DeleteRoom "+event.getData().toString());
    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent event) {
        for(int i=0; i<event.getRoomIds().length; i++)
        {
        	System.out.println("RoomId "+event.getRoomIds()[i]);
        	container.result(0);
             //container.appendResponseResult("RoomId "+event.getRoomIds()[i]);
        }        
    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent event) {
        
        for(int i=0; i<event.getUserNames().length; i++){
        	System.out.println(event.getUserNames()[i]);
        	container.result(0);
            //container.getClient().getLiveUserInfo(event.getUserNames()[i]);
        }
    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent event) {        
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            String location = "";
            if(event.isLocationLobby()){
                location = "the lobby";
            }
            else{
                location = "room id"+event.getLocationId();
            }
            System.out.println("User "+event.getName()+" is at " +location);
            container.result(0);
            //container.appendResponseResult("User "+event.getName()+" is at " +location);
        }
    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent event) {
        
       //container.appendResponseResult("User " +event.getName()+ "says " +event.getCustomData()); 

    }
}
