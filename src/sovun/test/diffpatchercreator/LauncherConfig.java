package sovun.test.diffpatchercreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class LauncherConfig {

	private static final String defaultPropertyName = "patchmaker.properties";
	private Properties prop = new Properties();
	private static LauncherConfig instance;

	public static LauncherConfig getInstance() throws IOException {
		if (instance == null) {
			instance = new LauncherConfig();
		}
		return instance;
	}

	private LauncherConfig(String file) throws IOException {
		File configFile = new File(file);
		if (configFile.exists()) {
			prop.load(new FileInputStream(configFile));
		}
		fixConfig();
	}

	private LauncherConfig() throws IOException {
		this(defaultPropertyName);
	}

	public String get(String key) {
		return prop.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}

	public void set(String key, String value) {
		prop.setProperty(key, value);
		saveConfig();
	}

	public void set(String key, Object value) {
		prop.setProperty(key, value.toString());
		saveConfig();
	}

	private void saveConfig() {
		try {
			prop.store(new FileOutputStream(defaultPropertyName), null);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void fixConfig() {
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("run", "WinMerge\\WinMergeU.exe $fil1 $fil2");


		for (Entry<String, String> configs : configMap.entrySet()) {
			if (get(configs.getKey(), "").isEmpty()) {
				set(configs.getKey(), configs.getValue());
			}
		}
		saveConfig();
	}
}
