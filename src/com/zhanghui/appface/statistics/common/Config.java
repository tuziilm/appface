package com.zhanghui.appface.statistics.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

public final class Config {
	public final static String FILE_NAME_LOG="statistic";
	public final static String CONFIG_FILE=".appface/config.properties";
	public final static String SEP="\u0001";
	public final static boolean isWindows=System.getProperty("os.name").toLowerCase().indexOf("win")!=-1;

	protected final static Properties config=loadConfig();
	public final static String DIR_LOG=(isWindows?"D:":"")+config.getProperty("logs.root.dir");

	/**
	 * 加载配置文件
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	private static Properties loadConfig() {
		Properties config=new Properties();
		try{
			config.load(new FileInputStream(System.getProperty("user.home")+"/"+CONFIG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("loaded config:"+System.getProperty("user.home")+"/"+CONFIG_FILE);
		return config;
	}

    public final static String getProperty(String key){
        return config.getProperty(key);
    }

	/**
	 * 获取前N天的日期字串
	 * @return
	 */
	public static String[] getPreNDaysStrings(int n, Date now) {
		String[] dayStrings=new String[n];
		for(int i=0;i<n;i++){
			dayStrings[i]= DateFormatUtils.format(
				DateUtils.addDays(now, i-n), "yyyy-MM-dd");
		}
		return dayStrings;
	}

	/**
	 * 获取前N天的日志文件
	 * @return
	 */
	public static String[] getPreNDaysLogFiles(int n, Date now) {
		String[] dayStrings=new String[n];
		StringBuilder file=new StringBuilder();
		for(int i=0;i<n; i++){
			file.append(DIR_LOG)
					.append("/")
					.append(FILE_NAME_LOG)
					.append(".")
					.append(getPreNDaysStrings(n, now)[i])
					.append(".log");
			dayStrings[i]=file.toString();
			file.setLength(0);
		}
		return dayStrings;
	}
}
