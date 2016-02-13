package com.qikemi.packages.alibaba.aliyun.oss;

import org.apache.log4j.Logger;

import com.aliyun.openservices.oss.OSSClient;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;

/**
 * OSSClient是OSS服务的Java客户端，它为调用者提供了一系列的方法，用于和OSS服务进行交互<br>
 * 
 * @create date : 2014年10月28日 上午11:20:56
 * @Author XieXianbin<a.b@hotmail.com>
 * @Source Repositories Address: <https://github.com/qikemi/UEditor-for-aliyun-OSS>
 */
public class OSSClientFactory {

	private static Logger logger = Logger.getLogger(OSSClientFactory.class);
	private static OSSClient client = null;
	
	/**
	 * 新建OSSClient 
	 * 
	 * @return
     * @version 2.0  区空格化登陆参数 
	 */
	public static OSSClient createOSSClient(){
		if ( null == client){
			client = new OSSClient(OSSClientProperties.ossCliendEndPoint.trim(), OSSClientProperties.key.trim(), OSSClientProperties.secret.trim());
			logger.info("First CreateOSSClient success.");
		}
		return client;
	}
    public  OSSClient createNewClient(String ossClientEndPoint,String key,String secret){

     return  new OSSClient(ossClientEndPoint.trim(), key.trim(), secret.trim());

    }

public static String getURL(OSSClient ossClient,String bucketName){
    String location = OSSClientProperties.useCDN ? OSSClientProperties.cdnEndPoint : OSSClientProperties.ossEndPoint;
    if(client !=ossClient){
      location =  ossClient.getBucketLocation(bucketName);
    }
    return location;
}

}
