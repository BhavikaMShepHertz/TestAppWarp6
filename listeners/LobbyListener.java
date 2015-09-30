/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warp.Test;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.LobbyRequestListener;

public class LobbyListener implements LobbyRequestListener {

    private Testauto container;
    LobbyListener(Testauto container){
        this.container = container;
    }
    
    @Override
    public void onJoinLobbyDone(LobbyEvent le) 
    {
    	container.result(le.getResult());
    	System.out.println("Lobby Name---"+le.getInfo().getName());
    	System.out.println("Lobby ID---"+le.getInfo().getId());
    	System.out.println("Owner---"+le.getInfo().getRoomOwner());
           //container.appendResponseResult("JoinLobby Name "+le.getInfo().getName()+"Id "+le.getInfo().getId()+"RoomOwner "+le.getInfo().getRoomOwner());
    }

    @Override
    public void onLeaveLobbyDone(LobbyEvent le) {
    	container.result(le.getResult());
    	System.out.println("Lobby Name---"+le.getInfo().getName());
    	System.out.println("Lobby ID---"+le.getInfo().getId());
    	System.out.println("Room Owner---"+le.getInfo().getRoomOwner());
         //container.appendResponseResult("LeaveLobby Name "+le.getInfo().getName()+"Id "+le.getInfo().getId()+"RoomOwner "+le.getInfo().getRoomOwner());
    }

    @Override
    public void onSubscribeLobbyDone(LobbyEvent le) {
         //container.appendResponseResult("SubscribeLobby Name "+le.getInfo().getName()+"Id "+le.getInfo().getId()+"RoomOwner "+le.getInfo().getRoomOwner());
    	container.result(le.getResult());
    	System.out.println("Lobby Name---"+le.getInfo().getName());
    	System.out.println("Lobby ID---"+le.getInfo().getId());
    	System.out.println("Room Owner---"+le.getInfo().getRoomOwner());
    }

    @Override
    public void onUnSubscribeLobbyDone(LobbyEvent le) {
         //container.appendResponseResult("UnSubscribeLobby Name "+le.getInfo().getName()+"Id "+le.getInfo().getId()+"RoomOwner "+le.getInfo().getRoomOwner());
    }

    @Override
    public void onGetLiveLobbyInfoDone(LiveRoomInfoEvent event) {
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            String[] users = event.getJoinedUsers();
            String result = "";
            //container.appendResponseResult("Users are");
            //System.out.println(users.length);
           for(int i=0; i<users.length; i++)
           {
                result += " "+users[i];     
                
            }
           System.out.println("user at lobby----"+result);
            container.result(event.getResult());
            
            //container.appendResponseResult(result);
        }
    }
    
}
