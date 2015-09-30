package com.warp.Test;



import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.junit.Before;
import org.junit.Test;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class Testauto {

	 static WarpClient theClient;
	//private String localUsername;
	private final int Idle = 0;
	private final int Processing = 2;
	private static int currentState = 0;
	private String roomID, username, roomName, RoomId;
	private int resultCode, userlength;
	private String userinfo;
	private String result;
   private static boolean intialize=false;
	static {
		
	}
	/**
	 * Creates new form WarpSampleUI
	 */
	public Testauto() {

		
		if(intialize)
			return;
		WarpClient
				.initialize(
						"112af4dc8deae4602107b53dc00320ffbe6f4601c181097332210247042c0b2e",
						"acedbf2c30df3cff12a6592e09441986ffa136e1caedbfd6b4c72ea57dddc3f6");
		WarpClient.setRecoveryAllowance(60);
		try {
			System.out.println("Setup");
			theClient = WarpClient.getInstance();
			theClient
					.addConnectionRequestListener(new ConnectionListener(this));
			theClient.addZoneRequestListener(new ZoneListener(this));
			theClient.addRoomRequestListener(new RoomListen(this));
			theClient.addNotificationListener(new NotificationListener(this));
			theClient.addLobbyRequestListener(new LobbyListener(this));
			intialize=true;
		} catch (Exception ex) {
			Logger.getLogger(Testauto.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}
	@Before
	public void setUp() throws Exception {
	}
	private  void waitFor() {
		int count=0;
		// System.out.println("Before");
		int state=currentState;
		while (currentState == Processing&&count<15) {
			count++;
			try {
				System.out.println("currentStatte"+currentState);
				Thread.sleep(1000);
				if(count==20)
					currentState = Idle;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// System.out.println("Test case completed...");
	}

    // Test cases of Connect/Disconnect
	@Test
	public void case1testWithNull() throws Exception // Connect without username
	{
		System.out.println("test with null");
		int expected = 4;
		currentState = Processing;
		System.out.println("Test Case-1: Connect without any username");
		theClient.connectWithUserName("");
		waitFor();
		assertEquals(expected, resultCode);
		theClient.disconnect();
		System.out.println("Completed");
	}

	@Test
	public void case2testwithvaliduname() throws Exception // Connect with valid username
	{
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-2: Connect with valid username");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		assertEquals(expected,resultCode);
		System.out.println("Connect successfully");
		theClient.disconnect();
	}

	@Test
	public void case3testwithuname() throws Exception // disconnect and again connect
													// with same username or
													// different
	{
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-3: Connect with same/different username");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		System.out.println("Connect successfully....");
		theClient.disconnect();
		System.out.println("Disconnect...");
		theClient.connectWithUserName(user);
		System.out.println("Connect again..");
		theClient.disconnect();

	}
	
	@Test
	public void case4testwithuname1() throws Exception // connect with same user name
													// twice
	{
		int expected = 0;
		//int expected1 = 4;
		currentState = Processing;
		System.out.println("Test Case-4: Connect with same username twice");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
	    waitFor();
	    int flag=1;
	    assertEquals(expected, resultCode);
		currentState = Processing;
		try
		{
		theClient.connectWithUserName(user);
		}
		catch(Exception e){
			
			flag=0;
			 assertEquals(flag, 0);
			currentState = Idle;
			return;
		}
	}

	@Test
	public void case5testwithuname2() throws Exception // connect with different user
													// name twice
	{
		int expected = 0;
		//int expected1 = 4;
		currentState = Processing;
		System.out.println("Test Case-5: Connect with differ username twice");
		theClient.disconnect();
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		int flag=1;
		waitFor();
		 assertEquals(expected, resultCode);
		// Connected successfully....Now connect again..
	
		currentState = Processing;
		try
		{
		theClient.createRoom("Room1", "Bhavika", 0, null);
		}
		catch(Exception e)
		{
			
			flag=0;
			assertEquals(flag, 0);
			currentState = Idle;
			return;
		}

	}

	@Test
	public void case6getroomsinfo() throws Exception // get all rooms info
	{
		
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-6: Get all room info");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.getAllRooms();
		waitFor();
		theClient.disconnect();
	}

	// Test case of Create Room

	@Test
	public void case7createroom1() throws Exception // Create Room with valid room
												// name and no of users not
												// given
	{
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-7:Create Room with valid room name and no of users not given");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
	
		waitFor();
		int flag=1;
		 assertEquals(expected, resultCode);
		// Connected successfully....Now create room..
		System.out.println("Conneted........");
		currentState = Processing;
		try{
		theClient.createRoom("Room1", "Bhavika", 0, null);
		}
		catch(Exception e){
			
			flag=0;
			 assertEquals(flag, 0);
			currentState = Idle;
			return;
		}
		
		waitFor();
		 assertEquals(flag, 0);
		if (expected == resultCode) {
			System.out.println("Room has been created..");
		} else {
			System.out.println("Room not created");
		}
		currentState = Processing;
		theClient.getLiveUserInfo(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case8createroom2() throws Exception // Create Room without room name
												// and no of users given only
	{
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-8:Create Room without room name and no of users given only");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		int flag=1;
		 assertEquals(expected, resultCode);
		// Connected successfully....Now create room..
		currentState = Processing;
		try
		{
		theClient.createRoom("", "Bhavika", 1, null);
		}
		catch(Exception e)
		{flag=0;
		 assertEquals(flag, 0);
		currentState = Idle;
		return;
		}
		 assertEquals(flag, 0);
			
		
		
		if (expected == resultCode) {
			System.out.println("Room has been created..");
		} else {
			System.out.println("Room has not been created");
		}
		currentState = Processing;
		theClient.getLiveUserInfo(roomID);
		waitFor();
		theClient.disconnect();
	}

	@Test
	public void case9createroom() throws Exception // Create Room with valid room
												// name and no of users
	{
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-9:Create Room with valid room name and no of users");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connected successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		waitFor();
		if (expected == resultCode) 
		{
			System.out.println("Room has been created successfully..");
		} 
		else 
		{
			System.out.println("Room not created");
		}
		
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	// Test case of User info
	@Test
	public void case10userinfo() throws Exception // Get user info
	{

		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-10:Get user info");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		waitFor();
		assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
	    assertEquals(expected, resultCode);

		
		 
		currentState = Processing;

		theClient.getLiveUserInfo(user);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	/*@Test
	public void case11joinroom1() throws Exception // Join room with no room id
	{
		theClient.disconnect();
		int flag=1;
		int expected = 0;
		//int expected1 = 4; // In case of room id null, bad request appear.
		currentState = Processing;
		System.out.println("Test Case-11:Join room with no room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		currentState = Processing;
		try
		{
			theClient.joinRoom("");
		}
		catch(Exception e)
		{
			
			System.out.println("Test case failed");
			flag=0;
			 assertEquals(flag, 0);
			currentState = Idle;
			return;
		}
		
	
	theClient.disconnect();
	
	}

	@Test
	public void case12joinroom2() throws Exception // Join room with incorrect room id
	{

		theClient.disconnect();
		int flag=0;
		int expected = 0;
		//int expected1 = 2; // In case of invalid room id , Resource not found
							// error appear.
		currentState = Processing;
		System.out.println("Test Case-12: Join room with incorrect room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		currentState = Processing;
		try
		{
			theClient.joinRoom("11");
		}
		catch(Exception e)
		{
			System.out.println("Test case failed");
			flag=0;
			 assertEquals(flag, 0);
			currentState = Idle;
			return;
		}
	
	theClient.disconnect();
	}*/
	
	@Test
	public void case13joinroom() throws Exception // Join room with room id
	{
		
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-13:Join room with room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		System.out.println("Create room");
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case14roominfo() throws Exception // Get room info
	{

		theClient.disconnect();
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-14:Get room info");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		System.out.println("Room has been created");
		// Room has been created ...Now join room
		waitFor();
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		if (expected == resultCode) {
			System.out.println("User joined room---" + roomID);
		} else {
			System.out.println("User unable to join room");
		}
		
		//Get room info
		currentState = Processing;
		theClient.getLiveRoomInfo(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case15subscriberoom() throws Exception // Subscribe room with room id
	{
	
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-15:Subscribe room with room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now subscribe room
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode)
		{
			System.out.println("User subsrcibe room---" + roomID);
		} 
		else
		{
			System.out.println("User unable to subscribe room");
		}
		theClient.disconnect();
	}
/*	@Test
	public void case16subscriberoom1() throws Exception // Subscribe room with no room id
	{
		theClient.disconnect();
		int flag=1;
		int expected = 0;
		int expected1 = 2;
		currentState = Processing;
		System.out.println("Test Case-16:Subscribe room with no room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		System.out.println("Joined....");
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now subscribe room
		currentState = Processing;
		
		try
		{
			theClient.subscribeRoom("");
		}
		catch(Exception e)
		{
		
			System.out.println("Test case failed");
			flag=0;
			 assertEquals(flag, 0);
			currentState = Idle;
			return;
		}
		
		
		
		theClient.disconnect();
	}*/
	

	/*@Test
	public void case17leaveroom() throws Exception // Leave room with valid room id
	{
		
		int expected = 0;

		currentState = Processing;
		System.out.println("Test Case-17:Leave room with valid room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room20", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now leave room
		currentState = Processing;
		theClient.leaveRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) 
		{
			System.out.println("User left room---" + roomID);
		
		}
		else {
			System.out.println("User unable to left room");
		}
		theClient.disconnect();
	}*/

	@Test
	public void case18unsubscriberoom() throws Exception // Unsubscribe room with
													// valid room id
	{
		theClient.disconnect();
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-18:Unsubscribe room with room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been joined ...Now subscribe
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		//Room is going to unsubscribe
		currentState = Processing;
		theClient.unsubscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User unsubsrcibe room---" + roomID);
		} else {
			System.out.println("User unable to unsubscribe room");
		}
		theClient.disconnect();
	}

	/*@Test
	public void case19joinlobby() throws Exception // Join lobby with valid room id
	{
		
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-19:Join lobby with valid room id");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		
		// Room has been created ...Now join lobby
		currentState = Processing;
		theClient.joinLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case20getlobbyinfo() throws Exception // get lobby info
	{
		
		int expected = 0;
		currentState = Processing;
		System.out.println("Test Case-20:Get lobby info");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully..Now join lobby
		currentState = Processing;
		theClient.joinLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.getLiveLobbyInfo();
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case21subscribelobby() throws Exception // subscribe lobby
	{
		theClient.disconnect();
		int expected = 0;

		currentState = Processing;
		System.out.println("Test Case-21:Subscribe lobby");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		// Room has been created ...Now join room
		waitFor();
		 assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.subscribeLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
		}

	@Test
	public void case22leavelobby() throws Exception // leave lobby
	{
		theClient.disconnect();
		int expected = 0;

		currentState = Processing;
		System.out.println("Test Case-22:Leave lobby");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		// Room has been created ...Now join lobby
		currentState = Processing;
		theClient.joinLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.leaveLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case23sendchat() throws Exception // send chat
	{
		theClient.disconnect();
		int expected = 0;
		System.out.println("Test case 23:Send chat");

		currentState = Processing;
		
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now subscribe...
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User subsrcibe room---" + roomID);
		} else {
			System.out.println("User unable to subscribe room");
		}
		// Now send chat message...
		currentState = Processing;
		theClient.sendChat("Hi");
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();

	}

	@Test
	public void case24sendupdate() throws Exception // send update
	{
		
		int expected = 0;

		currentState = Processing;
		System.out.println("Test case 24:Send update");
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now subscribe...
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User subsrcibe room---" + roomID);
		} else {
			System.out.println("User unable to subscribe room");
		}
		// Now send update...
		currentState = Processing;
		String message = "Hi";
		theClient.sendUpdatePeers(message.getBytes());
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case25sendprichat() throws Exception // send private chat
	{
		System.out.println("Test case 25:Send private chat");
		int expected = 0;
		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now subscribe...
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User subsrcibe room---" + roomID);
		} 
		else {
			System.out.println("User unable to subscribe room");
		}
		// Now send chat message...
		currentState = Processing;
		theClient.sendPrivateChat(user, "Hi");
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();

	}

	@Test
	public void case26sendpriupdate() throws Exception // send private update
	{
		System.out.println("Test case 26:Send pivate update");
		int expected = 0;

		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now subscribe...
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User subscribe room---" + roomID);
		} else {
			System.out.println("User unable to subscribe room");
		}
		// Now send private update
		currentState = Processing;
		String message = "Hi";
		theClient.sendPrivateUpdate(user,message.getBytes());

		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}

	@Test
	public void case27getonlineusers() throws Exception // online users info
	{
		System.out.println("Test case 27:Online users info");
		int expected = 0;
		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		currentState = Processing;
		theClient.getOnlineUsers();
		waitFor();
		theClient.disconnect();
	}

	@Test
	public void case28updateprop() throws Exception // update property
												
	{
		System.out.println("Test case 28:Update property");
		int expected = 0;

		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		 assertEquals(expected, resultCode);
		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now update property...
		currentState = Processing;
		HashMap<String, Object> tableProperties = new HashMap<String, Object>();
		tableProperties.put("h1", "h1");
		theClient.updateRoomProperties(roomID, tableProperties, null);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();

	}
	
	@Test
	public void case29Lockprop() throws Exception // Lock property
												
	{
		System.out.println("Test case 29:Lock property");
		int expected = 0;
		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();

		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now lock property...
		currentState = Processing;
		HashMap<String, Object> tableProperties = new HashMap<String, Object>();
		tableProperties.put("h1", "h1");
		theClient.lockProperties(tableProperties);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}
	
	@Test
	public void case30unLockprop() throws Exception // Unlock property
												
	{
		System.out.println("Test case 30:Unblock property");
		int expected = 0;
		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room3", "Bhavika", 5, null);
		waitFor();
		assertEquals(expected, resultCode);
		
		// Room has been created ...Now join room...
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);

		// Room has been joined. Now unlock property...
		currentState = Processing;
		HashMap<String, Object> tableProperties = new HashMap<String, Object>();
		tableProperties.put("h1", "h1");
		theClient.unlockProperties(null);
		waitFor();
		 assertEquals(expected, resultCode);
		theClient.disconnect();
	}*/
	
	/*@Test
	public void maintest() throws Exception 
	{
		
		int expected = 0;

		currentState = Processing;
		String user = "user_"+(Math.floor(Math.random() * 1000));
		theClient.connectWithUserName(user);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Connect Successfully....");
		} else {
			System.out
					.println("Unable to connect...Please try after sometime..");
		}

		// Connect successfully....Now create room..
		currentState = Processing;
		theClient.createRoom("Room1", "Bhavika", 5, null);
		waitFor();
		if (expected == resultCode) {
			System.out.println("Room has been created...");
		} else {
			System.out.println("Room has not been created..");
		}

		// Room has been created ...Now join room
		currentState = Processing;
		theClient.joinRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Room has been joined...");
		} else {
			System.out.println("Unable to join room...");
		}

		// Room has been joined. Now subscribe...
		currentState = Processing;
		theClient.subscribeRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User subsrcibe room---" + roomID);
		} else {
			System.out.println("User unable to subscribe room");
		}

		// Now get room info
		currentState = Processing;
		theClient.getLiveRoomInfo(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Above is the room ifo..");
		} else {
			System.out.println("No room info available...");
		}
		// Now send chat message...
		currentState = Processing;
		theClient.sendChat("Hi");
		waitFor();
		 assertEquals(expected, resultCode);
		// Now send update..
		currentState = Processing;
		String message = "Hi";
		theClient.sendUpdatePeers(message.getBytes());
		waitFor();
		 assertEquals(expected, resultCode);
		
		//Send private chat
		currentState = Processing;
		theClient.sendPrivateChat(user, "Hi");
		waitFor();
		 assertEquals(expected, resultCode);

		// Now send private update
		currentState = Processing;
		String message1 = "Hi";
		theClient.sendPrivateUpdate(user,message1.getBytes());
		waitFor();
		 assertEquals(expected, resultCode);
		
		// Get online users info
		currentState = Processing;
		System.out.println("Online users info..");
		theClient.getOnlineUsers();
		waitFor();
		// Leave room
		currentState = Processing;
		theClient.leaveRoom(roomID);
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User left room---" + roomID);
		} else {
			System.out.println("User unable to left room");
		}
		// Join lobby
		currentState = Processing;
		theClient.joinLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Lobby has been joined...");
		} else {
			System.out.println("User unable to join lobby..");
		}
		// Get lobby info
		currentState = Processing;
		theClient.getLiveLobbyInfo();
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Above is the lobby info...");
		} else {
			System.out.println("No lobby info exists...");
		}
		// Now subscribe lobby
		currentState = Processing;
		theClient.subscribeLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("Lobby has been subscribed...");
		} else {
			System.out.println("Unable to subscribe lobby...");
		}

		// Leave lobby
		currentState = Processing;
		theClient.leaveLobby();
		waitFor();
		 assertEquals(expected, resultCode);
		if (expected == resultCode) {
			System.out.println("User left the lobby...");
		} else {
			System.out.println("Unable to left lobby...");
		}
		System.out.println("Disconnect....");
		theClient.disconnect();
		

	}*/

	public synchronized void result(int resultCode) 
	{
		//System.out.println("RESULY"+currentState);
		currentState = Idle;
		//System.out.println("RESULY"+currentState);
		this.resultCode = resultCode;
	
	
	}
	public synchronized void setRoomId(String roomID) throws Exception 
	{
		currentState = Idle;
		this.roomID = roomID;
	}

}
