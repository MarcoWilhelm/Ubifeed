package irl.tud.ubifeed;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.presentation.MyServlet;

import irl.tud.ubifeed.venue.VenueDto;
import irl.tud.ubifeed.venue.VenueImpl;;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DependenciesMachine.init("prod.properties");
		
		Server server = new Server(8080); 
		// server on port 8080
		
		WebAppContext context = new WebAppContext(); 
		// configuration object
		
		HttpServlet ms= (MyServlet) DependenciesMachine.produce(MyServlet.class);
		//MyServlet answer to a request
		
		context.addServlet(new ServletHolder(ms), "/ubifeed/*");
		// ms will process the requests on the path /ubifeed/
	    
		context.setResourceBase("www"); 
		// repertory of the html files ...
		// relative => at the project root
		
		HttpServlet ds = new DefaultServlet();
		// servlet provided by jetty
		// will answer to the requests to get the html files...
		
		context.addServlet(new ServletHolder(ds), "/");
		// on the path /ubifeed/ : ms has the priority
		
		server.setHandler(context); 
		// give the configuration to the server
		

		try {
			server.start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
