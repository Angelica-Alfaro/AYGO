package edu.escuelaing.arep.dockerdemo.loadbalancer.controller;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

import edu.escuelaing.arep.dockerdemo.loadbalancer.service.LoadBalancerService;
import spark.Filter;

public class LoadBalancerController {
	
	public LoadBalancerController(LoadBalancerService myLoadBalancerService) {
		after((Filter) (request, response) -> {
    		response.header("Access-Control-Allow-Origin","*");
    		response.header("Access-Control-Allow-Methods","GET");
    	});
	get("hello", (req,res) -> "Hello Docker!");
        get("string", (req,res) -> myLoadBalancerService.read(req,res));
        post("string", (req, res) -> myLoadBalancerService.create(req,res));
	}
}
