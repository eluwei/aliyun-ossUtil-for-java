package com.zome.OSSManager.upload;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.zome.OSSManager.ObjectServiceMy;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 同步上传文件到阿里云OSS<br>
 * 
 * @create date : 2016年02月02日
 * @Author XieXianbin<a.b@hotmail.com> shaoyi(zhoushaoyi@gmail.com)
 * @Source Repositories Address:
 *         <https://github.com/qikemi/UEditor-for-aliyun-OSS> 1.0
 *
 */
public class SynUploader_oss extends Thread {

	private static Logger logger = Logger.getLogger(SynUploader_oss.class);

    /***
     * @since 1.0
     * @param bucketName  buckName
     * @param client    服务器客户端
     * @param file  文件
     * @param pathName 上传地址
     * @return 上传是否成功
     */
    public boolean upload(String bucketName, OSSClient client, File file,
                          String pathName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            PutObjectResult result = ObjectServiceMy.putObject(client,
                    bucketName, pathName, fileInputStream);
            logger.debug("upload file to aliyun OSS object server success. ETag: "
                    + result.getETag());
            return true;
        } catch (FileNotFoundException e) {
            logger.error("upload file to aliyun OSS object server occur FileNotFoundException.");
        } catch (NumberFormatException e) {
            logger.error("upload file to aliyun OSS object server occur NumberFormatException.");
        } catch (IOException e) {
            logger.error("upload file to aliyun OSS object server occur IOException.");
        }
        return false;
    }

    /**
     * @since 2.0
     * @param bucketName
     * @param client 服务器客户端
     * @param file 文件
     * @param pathName 上传地址
     * @param objectMetadata  文件 信息
     * @return 上传是否成功
     */
	public boolean upload(String bucketName, OSSClient client, File file,
                          String pathName, ObjectMetadata objectMetadata) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);

			PutObjectResult result = ObjectServiceMy.putObject(client,
                    bucketName, pathName, fileInputStream,objectMetadata);
			logger.debug("upload file to aliyun OSS object server success. ETag: "
					+ result.getETag());
			return true;
		} catch (FileNotFoundException e) {
			logger.error("upload file to aliyun OSS object server occur FileNotFoundException.");
		} catch (NumberFormatException e) {
			logger.error("upload file to aliyun OSS object server occur NumberFormatException.");
		} catch (IOException e) {
			logger.error("upload file to aliyun OSS object server occur IOException.");
		}
		return false;
	}

}
