package irl.tud.ubifeed;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * The Config class for all the hard-coded texts used in the application. It uses a .properties file
 * to load all the properties between the entered informations and its message.
 * 
 * @author Yann Pollet
 * @version 1.0
 */
public final class Config {

  private static Properties props;


  // loads the properties of the file config.properties
  static {
    String configPath = "./config.properties";
    props = new Properties();
    Path path = FileSystems.getDefault().getPath(configPath);

    try (InputStream inputStream = Files.newInputStream(path)) {
      props.load(inputStream);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
  


  /**
   * get a property in the file config.properties.
   * 
   * @param name Name of the researched information
   * @return property found with the parameter in the file config.properties.
   */
  public static String getConfigFor(String name) {
    return props.getProperty(name);
  }
}
