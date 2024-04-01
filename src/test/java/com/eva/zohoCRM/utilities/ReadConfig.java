package com.eva.zohoCRM.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.bouncycastle.crypto.RuntimeCryptoException;

public class ReadConfig {
	Properties pro;

	public ReadConfig() {
		pro = new Properties();

		try {
			FileInputStream file = new FileInputStream("src\\test\\resources\\Config.properties");
			pro.load(file);
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public String getUrl() {
		String value = pro.getProperty("url");
		if (value != null) {
			return value;
		} else {
			throw new RuntimeException("url is not specified in config file");
		}
	}

	public String getEmailOrUsername() {
		String email = pro.getProperty("email");
		if (email != null) {
			return email;
		} else {
			throw new RuntimeException("Email or userName is not specified in config file");
		}
	}

	public String getPassword() {
		String pass = pro.getProperty("password");
		if (pass != null) {
			return pass;
		} else {
			throw new RuntimeException("Password is not specified in config file");
		}
	}
	public String getHomePageHeaderOfACC() {
		String title = pro.getProperty("AccountHeader");
		if (title != null) {
			return title;
		} else {
			throw new RuntimeException("title is not specified in config file");
		}
	}


}
