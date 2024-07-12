package propertyfile;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	public String getPropData(String key) {
		try {
			FileInputStream fis = new FileInputStream(
				"E:\\Testng\\RestAssuredAutomationFramework-master\\src\\test\\resources\\org\\propertiesFiles\\Config.properties");
			Properties prop = new Properties();
			prop.load(fis);
//			String value = prop.getProperty(key);
//			return value;
			return prop.getProperty(key);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
