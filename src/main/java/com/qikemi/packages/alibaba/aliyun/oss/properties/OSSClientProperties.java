package com.qikemi.packages.alibaba.aliyun.oss.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.qikemi.packages.utils.SystemUtil;

public class OSSClientProperties {

	private static Logger logger = Logger.getLogger(OSSClientProperties.class);
	
	private static Properties OSSKeyProperties = new Properties();
	public static String bucketName = "";
	public static String key = "";
	public static String secret = "";
	public static boolean autoCreateBucket = false;
	
	public static String ossCliendEndPoint = "";
	public static String ossEndPoint = "";
	public static boolean useCDN = false;
	public static String cdnEndPoint = "";
	
	public static boolean useLocalStorager = false;
	public static String uploadBasePath = "upload";
	public static boolean useAsynUploader = false;
    public static Long partSize = 100*1024*1024L;
    public static Long bigFile = 100*1024*1024L;
    public static int tempFileTimeOut = 24* 3600 * 1000;

	static {
		String OSSKeyPath = SystemUtil.getProjectClassesPath()
				+ "OSSKey.properties";
		// 生成文件输入流
		FileInputStream inpf = null;
		try {
			inpf = new FileInputStream(new File(OSSKeyPath));
			OSSKeyProperties.load(inpf);

			bucketName = (String) OSSKeyProperties.get("bucketName");
			key = (String) OSSKeyProperties.get("key");
			secret = (String) OSSKeyProperties.get("secret");
			autoCreateBucket = "true".equalsIgnoreCase((String) OSSKeyProperties.get("autoCreateBucket")) ? true : false;
			
			ossCliendEndPoint = (String) OSSKeyProperties.get("ossCliendEndPoint");
			ossEndPoint = (String) OSSKeyProperties.get("ossEndPoint");
			useCDN = "true".equalsIgnoreCase((String) OSSKeyProperties.get("useCDN")) ? true : false;
			cdnEndPoint = (String) OSSKeyProperties.get("cdnEndPoint");
			
			useLocalStorager = "true".equalsIgnoreCase((String) OSSKeyProperties.get("useLocalStorager")) ? true : false;
			uploadBasePath = (String) OSSKeyProperties.get("uploadBasePath");
            partSize =Long.parseLong(String.valueOf(OSSKeyProperties.get("partSize")).trim());
            bigFile =Long.parseLong(String.valueOf(OSSKeyProperties.get("bigFile")).trim());
            tempFileTimeOut =  Integer.parseInt(String.valueOf(OSSKeyProperties.get("tempFileTimeOut")).trim());
			useAsynUploader = "true".equalsIgnoreCase((String) OSSKeyProperties.get("useAsynUploader")) ? true : false;

		} catch (Exception e) {
			logger.warn("系统未找到指定文件：OSSKey.properties --> 系统按照OSSUtil默认配置执行。");
		}
	}


}
