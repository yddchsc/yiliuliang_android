package util;

import java.util.Properties;
public class Config {
//用来解析装载系统配置文件
	 private Properties cfg = new Properties();
	  public Config(){
		  
	  }
	  public Config(String file){
	    try {
//	      cfg.load(new FileInputStream(file));
	    	cfg.load(Config.class.getResourceAsStream(file));
	    } catch (Exception e) {
	      e.printStackTrace();
	      throw new RuntimeException(e);
	    }
	  }
	  public String getString(String key){
		    return cfg.getProperty(key);
		  }
		  
		  public int getInt(String key){
		    return Integer.parseInt(cfg.getProperty(key));
		  }
		  
		  public double getDouble(String key){
		    return Double.parseDouble(getString(key));
		  }
		  public static void main(String[] args) {
		    Config config = 
		      new Config("ServerConfig.properties");
		
		    System.out.println(config.getString("username"));
		    
		  }
}
