package com.qikemi.packages.baidu.ueditor.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.zome.OSSManager.ObjectServiceMy;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.qikemi.packages.alibaba.aliyun.oss.ObjectService;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import com.qikemi.packages.utils.SystemUtil;

/**
 * 同步上传文件到阿里云OSS<br>
 * 
 * @create date : 2014年10月28日 22:11:00
 * @Author XieXianbin<a.b@hotmail.com> ,shaoyi_zhou(zhoushaoyi@gmail.com)
 * @Source Repositories Address:
 *         <https://github.com/qikemi/UEditor-for-aliyun-OSS>
 */
public class SynUploader extends Thread {

	private static Logger logger = Logger.getLogger(SynUploader.class);

	public boolean upload(JSONObject stateJson, OSSClient client,
			HttpServletRequest request) {
//		Bucket bucket = BucketService.create(client,
//				OSSClientProperties.bucketName);
		// get the key, which the upload file path
		String key = stateJson.getString("url").replaceFirst("/", "");
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					SystemUtil.getProjectRootPath() + key));
			PutObjectResult result = ObjectService.putObject(client,
					OSSClientProperties.bucketName, key, fileInputStream);
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
     * @author shaoyi_zhou zhou
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
