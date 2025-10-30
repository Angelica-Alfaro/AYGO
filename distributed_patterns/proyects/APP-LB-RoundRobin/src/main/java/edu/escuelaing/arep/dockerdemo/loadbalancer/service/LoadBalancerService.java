package edu.escuelaing.arep.dockerdemo.loadbalancer.service;

import java.io.IOException;
import edu.escuelaing.arep.dockerdemo.loadbalancer.connection.LogServiceConnection;
import edu.escuelaing.arep.dockerdemo.loadbalancer.roundrobin.LoadBalancer;
import edu.escuelaing.arep.dockerdemo.loadbalancer.roundrobin.RoundRobin;
import spark.Request;
import spark.Response;

public class LoadBalancerService {
	
	public static LogServiceConnection logServiceConnection;
	public static LoadBalancer roundRobin;
	
	public LoadBalancerService(){
		logServiceConnection = LogServiceConnection.getInstance();
		roundRobin = new RoundRobin();
	}
	
	public String read (Request req, Response res) {
		res.type("application/json");
		String url = roundRobin.getServer();
		String result = "";
		try {
			result = logServiceConnection.sendGET(url);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	 }

	public String create(Request req, Response res) {
		res.type("application/json");
		String url = roundRobin.getServer();
		String result = "";
		try {
			System.out.println(req.body());
			result = logServiceConnection.sendPOST(url, req.body());	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
