package com.zome.OSSManager.upload;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * 异步上传文件到阿里云OSS
 * 
 * @create date : 2015年12日25
 * @Author shaoyi_zhou(zhoushaoyi@gmail.com)
 *
 * @Refreing 参考 Repositories Address:
 * @version 2.0
 * @since 2.0
 *         <https://github.com/qikemi/UEditor-for-aliyun-OSS>
 */
public class AsynUploaderThreader_oss implements Runnable{

	private static Logger logger = Logger.getLogger(AsynUploaderThreader_oss.class);
	private File file = null;
	private OSSClient client = null;
	private String pathName= null;
    private String bucket=null;
    private ObjectMetadata objectMetadata =null;

    public AsynUploaderThreader_oss(String bucketName, OSSClient client, File file,
                                    String pathName) {
        this.file = file;
        this.client = client;
        this.pathName = pathName;
        this.bucket = bucketName;
    }

    public AsynUploaderThreader_oss(String bucketName, OSSClient client, File file,
                                    String pathName, ObjectMetadata objectMetadata) {
        this.file = file;
        this.client = client;
        this.pathName = pathName;
        this.bucket = bucket;
        this.objectMetadata = objectMetadata;
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		SynUploader_oss synUploader = new SynUploader_oss();
		synUploader.upload(bucket,client,file, pathName,objectMetadata);
		logger.debug("asynchronous upload image to aliyun oss success.");
	}

}
