package edu.escuelaing.arep.dockerdemo.loadbalancer.roundrobin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpPool {
	
    public static Map<String, Integer> ipMap = new ConcurrentHashMap<>();

    static {
        //ipMap.put("http://localhost:8081/message", 1);
        ipMap.put("http://web1:8080/message", 1);
        //ipMap.put("http://ec2-3-89-186-237.compute-1.amazonaws.com:33003", 1);
    }
}
