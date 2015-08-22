package com.pm.endpoint;
import javax.xml.ws.Endpoint;

import com.pm.ws.JCServerImpl;

public class JCPublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/ws/jc", new JCServerImpl());
		System.out.println("Server is published!");
	}

}
