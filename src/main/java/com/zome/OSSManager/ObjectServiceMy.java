package com.zome.OSSManager;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.*;
import com.aliyun.oss.ClientException;
import com.qikemi.packages.alibaba.aliyun.oss.ObjectService;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Robin on 2016-01-08.
 */
public class ObjectServiceMy extends ObjectService {

    static Logger logger = Logger.getLogger(ObjectServiceMy.class);


    public ObjectServiceMy() {
        super();
    }

    /**
     * 上传Object
     *
     * @param client
     * @param bucketName
     * @param key
     * @param filePath
     * @param objectMetadata
     *
     * @return
     *
     * @throws FileNotFoundException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, String filePath, ObjectMetadata objectMetadata) throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream content = new FileInputStream(file);
        ObjectMetadata meta = objectMetadata == null ? new ObjectMetadata() : objectMetadata;
        meta.setContentLength(file.length());
        meta.addUserMetadata("filename", key);
        PutObjectResult result = client.putObject(bucketName, key, content, meta);
        return result;
    }

    /**
     * 上传Object
     *
     * @param client         客户端
     * @param bucketName     bucket名
     * @param key            key
     * @param content        内容
     * @param objectMetadata
     *
     * @return
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, InputStream content, ObjectMetadata objectMetadata) throws NumberFormatException, IOException,OSSException,ClientException {
        ObjectMetadata meta = objectMetadata == null ? new ObjectMetadata() : objectMetadata;
        meta.setContentLength((long) Integer.parseInt(String.valueOf(content.available())));
        meta.addUserMetadata("filename", key);
        PutObjectResult result = client.putObject(bucketName, key, content, meta);
        return result;
    }

    /**
     * 上传Object
     *
     * @param client         客户端
     * @param bucketName     bucket名
     * @param key            key
     * @param content        内容
     * @param objectMetadata
     *
     * @return
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    public static PutObjectResult putMultiObject(OSSClient client, String bucketName, String key, InputStream content, ObjectMetadata objectMetadata) throws NumberFormatException, IOException,OSSException,ClientException {
        ObjectMetadata meta = objectMetadata == null ? new ObjectMetadata() : objectMetadata;
        meta.setContentLength((long) Integer.parseInt(String.valueOf(content.available())));
        meta.addUserMetadata("filename", key);
        PutObjectResult result = client.putObject(bucketName, key, content, meta);
        return result;
    }

    /**
     * 上传Object
     * @param client 客户端
     * @param bucketName bucket名
     * @param key 路径
     * @param content 内容
     * @param objectMetadata 对象属性
     * @return 结果
     * @throws NumberFormatException
     * @throws IOException
     */
    public static String putMulitiObject(OSSClient client, String bucketName, String key , InputStream content,ObjectMetadata objectMetadata,Long partSize) throws NumberFormatException, IOException {
        ObjectMetadata meta =objectMetadata ==null ? new ObjectMetadata() :objectMetadata;
        InitiateMultipartUploadRequest request =new InitiateMultipartUploadRequest(bucketName,key);
        request.setObjectMetadata(meta);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        String uploadId = result.getUploadId();
        UploadPartRequest uploadPartRequest =new UploadPartRequest();
        uploadPartRequest.setBucketName(bucketName);
        uploadPartRequest.setKey(key);
        uploadPartRequest.setUploadId(uploadId);
        uploadPartRequest.setInputStream(content);
        uploadPartRequest.setPartSize(partSize);
        return uploadId;
    }
    }
