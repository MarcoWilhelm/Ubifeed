package irl.tud.ubifeed;

import irl.tud.ubifeed.presentation.MyServlet;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DependenciesMachine.init("prod.properties");
		MyServlet servlet = (MyServlet) DependenciesMachine.produce(MyServlet.class);
	}

}
