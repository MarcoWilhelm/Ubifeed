package irl.tud.ubifeed;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DependenciesMachine {

	private static Properties matchings = null;
	private static Map<String, Object> instances = new HashMap<String, Object>();

	/**
	 * load the properties file for the injection machine
	 * @param path the relative path to the properties file asked
	 * @throws FileNotFoundException if the properties file doesn't exists
	 */
	public static void init(String path) throws FileNotFoundException {

		//initializing matchings
		matchings = new Properties();
		InputStream input = null;

		//load properties
		try {
			input = new FileInputStream(path);
			matchings.load(input);
		}catch(IOException ioExcept) {
			ioExcept.printStackTrace();
			matchings = null;
		}finally {
			// always close the stream 
			if (input != null) {
				try {
					input.close();
				} catch (IOException ioexc) {
					ioexc.printStackTrace();
				}
			}
		}

		if(matchings == null) {
			throw new FileNotFoundException("Properties couldn't be loaded");
		}
	}

	public static Object produce(Class<?> classToProduce) {

		//init object
		Object object = getImpl(classToProduce);

		// inject dependencies
		injectDependencies(object);
		
		System.out.println(instances);
		//clear instances
		instances = new HashMap<String, Object>();

		return object;
	}

	private static Object getImpl(Class<?> anInterface) throws IllegalStateException {
		// if not initialized raises exception
		if (matchings == null) {
			throw new IllegalStateException(
					"The ServiceFactory has not been initialized and yet calling getImpl()");
		}
		String implName = matchings.getProperty(anInterface.getName());
		// if none found
		
		System.out.println("Impl for " + anInterface.getName() + " = " + implName);
		if (implName == null) {
			return null;
		}

		// loading the implementation
		Object impl = null;
		try {
			impl = Class.forName(implName).newInstance();
		} catch (Exception except) {
			except.printStackTrace();
			impl = null;
		}
		return impl;
	}

	private static void injectDependencies(Object object) {
		System.out.println("OBJECT : " + object);
		Field[] fields = object.getClass().getFields();
		for(int i = 0; i< fields.length; i++) {
			if(!fields[i].isAnnotationPresent(Inject.class)) {
				continue;
			}
			if (!instances.containsKey(matchings.getProperty(fields[i].getType().getName()))) {
				Object instance = getImpl(fields[i].getType());
				instances.put(matchings.getProperty(fields[i].getType().getName()), instance);
				System.out.println("PUT : " + fields[i].getType().getName());
				injectDependencies(instance);
			}
			try {
				System.out.println(fields[i].getType().getName() + "; " + instances.get(matchings.getProperty(fields[i].getType().getName())));
				fields[i].set(object, instances.get(matchings.getProperty(fields[i].getType().getName())));
			} catch (IllegalArgumentException illegalArgumentException) {
				illegalArgumentException.printStackTrace();
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		System.out.println("OUT : " + object);
	}
}
