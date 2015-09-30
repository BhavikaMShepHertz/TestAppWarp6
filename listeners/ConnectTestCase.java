package com.warp.Test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class ConnectTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("initialization started");
		 WarpClient.initialize("646dd8ea3ce1be580c07248a8aaf53fdefb8851a399c740a86b6fba6ab3b8c2c", "7aac4570645d21d7fd3527544009688e00a5a71af5e6e5d67bf5c3dced252a32","127.0.0.1");
	
		 WarpClient.setRecoveryAllowance(60);
		 String username;
	
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() throws Exception {
//		WarpClient theClient = WarpClient.getInstance();
//		theClient.addConnectionRequestListener(new ConnectionListener());
//		//theClient.connectWithUserName(null);
//		theClient.connectWithUserName("mdsdsdayank");
//		 System.out.println("initialization finished");
	}
	
	
	
		 
	}


