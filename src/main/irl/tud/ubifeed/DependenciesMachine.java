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

	/**
	 * Return an object after initializing all the dependencies.
	 * 
	 * @param classToProduce the class that you want the object
	 * @return object initialized with all the dependencies
	 */
	public static Object produce(Class<?> classToProduce) {

		//init object
		Object object = getImpl(classToProduce);

		// inject dependencies
		injectDependencies(object);

		//clear instances
		instances = new HashMap<String, Object>();

		return object;
	}

	/**
	 * This method gives the matching implementation of a given interface.
	 * 
	 * @param anInterface The interface for which the implementation is required.
	 * @return An instance of the corresponding implementation, and null if no matching is found or if
	 *         an error during creation is raised.
	 * @throws IllegalStateException If the Factory has not been initialized yet.
	 */
	private static Object getImpl(Class<?> anInterface) throws IllegalStateException {
		// if not initialized raises exception
		if (matchings == null) {
			throw new IllegalStateException(
					"The ServiceFactory has not been initialized and yet calling getImpl()");
		}
		String implName = matchings.getProperty(anInterface.getName());
		// if none found

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

	/**
	 * Inject all the dependencies in the Service objects.
	 * 
	 * @param object the object you want to inject its dependencies
	 */
	private static void injectDependencies(Object object) {
		Field[] fields = object.getClass().getFields();
		// for each attribute
		for(int i = 0; i< fields.length; i++) {
			if(!fields[i].isAnnotationPresent(Inject.class)) {
				//attribute has not the annotation
				continue;
			}
			// check if the object has already been instanciated or not
			if (!instances.containsKey(matchings.getProperty(fields[i].getType().getName()))) {
				//object not instanciated
				Object instance = getImpl(fields[i].getType());
				instances.put(matchings.getProperty(fields[i].getType().getName()), instance);
				//inject depend of the object
				injectDependencies(instance);
			}
			try {
				// Instanciate the attribute
				fields[i].set(object, instances.get(matchings.getProperty(fields[i].getType().getName())));
			} catch (IllegalArgumentException illegalArgumentException) {
				illegalArgumentException.printStackTrace();
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}
}
