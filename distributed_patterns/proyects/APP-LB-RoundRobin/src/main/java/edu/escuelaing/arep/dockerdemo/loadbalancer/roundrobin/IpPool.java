package edu.escuelaing.arep.dockerdemo.loadbalancer.roundrobin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpPool {
	
    public static Map<String, Integer> ipMap = new ConcurrentHashMap<>();

    static {
        ipMap.put("http://ec2-3-82-36-94.compute-1.amazonaws.com:8080/message", 1);
        ipMap.put("http://ec2-3-82-36-94.compute-1.amazonaws.com:8081/message", 1);
        ipMap.put("http://ec2-3-82-36-94.compute-1.amazonaws.com:8082/message", 1);
    }
}
