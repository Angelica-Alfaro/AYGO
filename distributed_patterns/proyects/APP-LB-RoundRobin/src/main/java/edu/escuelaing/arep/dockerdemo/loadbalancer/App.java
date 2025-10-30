package edu.escuelaing.arep.dockerdemo.loadbalancer;
import edu.escuelaing.arep.dockerdemo.loadbalancer.controller.LoadBalancerController;
import edu.escuelaing.arep.dockerdemo.loadbalancer.service.LoadBalancerService;
import static spark.Spark.*;

/**
 * Communication  using java network management libraries
 * @author Angélica
 *
 */
public class App {
	
	/**
	 * Start the web server
	 * @param args - server connection
	 */
	public static void main(String[] args) {
		port(getPort());
		staticFiles.location("/public");
		new LoadBalancerController(new LoadBalancerService()); 
	}

	/**
	 * This method reads the default port as specified by the PORT variable in the
	 * environment.
	 *
	 * Provides the port automatically so you need this to run the project.
	 */
	static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 4567; 
	}
}