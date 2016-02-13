package com.zome.OSSManager;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.qikemi.packages.alibaba.aliyun.oss.OSSClientFactory;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import com.zome.OSSManager.upload.AsynUploaderThreader_oss;
import com.zome.OSSManager.upload.MultipartUpload;
import com.zome.OSSManager.upload.SynUploader_oss;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Robin on 2015-12-19.
 *  OSS 文件管理 util .
 */
public class OssManager {

static Logger logger = Logger.getLogger(OssManager.class);

    private static ExecutorService executorOSSService = Executors.newSingleThreadExecutor();
    /**
     * 临时文件上传
     * 这边有两个实现方式一个是在bucket 中设置 lifeCycle ,另外一种是设置 Expire.下面是设置Expire 的方式的例子
     * @param file 文件
     * @param filePath 路径
     * @param fileName 文件名
     * @return  访问对象的网站 地址
     * @throws FileNotFoundException
     * @throws ClientException
     */
    public static String SimpleFileTempUp(File file, String filePath, String fileName) throws FileNotFoundException,ClientException {


        // 初始化OSSClient
        OSSClient client =OSSClientFactory.createOSSClient();

        String bucket = filePath.split("/")[0];
        String path = filePath.substring(bucket.length()+1,filePath.length()) +fileName;
        String webPath="";
        ObjectMetadata objectMetadata= new ObjectMetadata();
        Date expire = new Date(new Date().getTime() +  OSSClientProperties.tempFileTimeOut);
        objectMetadata.setExpirationTime(expire);
        //构造函数
        StringBuffer buffer =new StringBuffer();

        try {

            buffer.append(OSSClientFactory.getURL(client, bucket));

                buffer.append(copyFileToOssClient(client, bucket, path, file,objectMetadata));


            } catch ( Exception e ) {
                logger.error(e);
                throw new ClientException(e);
            }

        return buffer.toString();

    }

    /**
     *
     //   自定义上传 文件
     * @param bucket 路径
     * @param fileName 文件名
     * @param file 文件
     * @return 当前生成的URL
     */
    public static String copyFileToOssClient(OSSClient client, String bucket, String fileName, File file, ObjectMetadata objectMetadata) throws IOException,ClientException {
        try {
            // upload type
            FileInputStream conten = new FileInputStream(file);
            if(file.length() > OSSClientProperties.partSize){
                MultipartUpload multipartUpload = new MultipartUpload(client,bucket,fileName,file.getAbsolutePath(),objectMetadata);
                multipartUpload.UploadMultipartFile();
            }else{
            if ( OSSClientProperties.useAsynUploader) {
                executorOSSService.execute(new AsynUploaderThreader_oss(bucket,client,file,fileName,objectMetadata));
            } else {
                SynUploader_oss synUploader_oss =new SynUploader_oss();
                synUploader_oss.upload(bucket,client,file,fileName,objectMetadata);
            }
            conten.close();

            }

        } catch ( Exception e ) {
            logger.error(e);
            throw new ClientException(e);
        }

        return fileName;
    }

    /**
     *  上传 到
     *
     * @param file 上传文件
     * @param filePath 上传的相对地址
     * @param fileName 文件名
     * @throws FileNotFoundException
     */
    public static String SimplefileUp(File file, String filePath, String fileName) throws FileNotFoundException,ClientException {
        String extName = ""; // 扩展名格式：
            if (file.getName().lastIndexOf(".") >= 0){
                extName = file.getName().substring(file.getName().lastIndexOf("."));
            }
        // 初始化OSSClient
        OSSClient client =OSSClientFactory.createOSSClient();
      String bucket = filePath.split("/")[0];
      String path = filePath.substring(bucket.length()+1,filePath.length()) +"/"+fileName+extName;
        StringBuffer buffer =new StringBuffer();

        buffer.append(OSSClientFactory.getURL(client,bucket));
        try {
            buffer.append(copyFileToOssClient(client,bucket, path, file));

        }catch (Exception e){
            logger.error(e);
            throw new ClientException(e);
        }
        return buffer.toString();

    }


    /**
     *
     // s 上传文件到 oss 的 路径上
     * @param bucket 路径
     * @param fileName 文件名
     * @param file 复制文件
     * @return 当前生成的URL
     */
    public static String copyFileToOssClient(OSSClient client,String bucket, String fileName, File file) throws IOException,ClientException {

        try {
        // upload type
        FileInputStream conten = new FileInputStream(file);
            if(file.length() > OSSClientProperties.partSize){
                MultipartUpload multipartUpload = new MultipartUpload(client,bucket,fileName,file.getAbsolutePath());
                multipartUpload.UploadMultipartFile();
            }else {
                if ( OSSClientProperties.useAsynUploader ) {
                    executorOSSService.execute(new AsynUploaderThreader_oss(bucket, client, file, fileName));
                } else {
                    SynUploader_oss synUploader_oss = new SynUploader_oss();
                    synUploader_oss.upload(bucket, client, file, fileName);
                }
            }
        conten.close();


        } catch ( Exception e ) {
            logger.error(e);
            throw new ClientException(e);
        }

        return fileName;
    }

    /**
     * 删除 文件
     * @param pathList  文件路径集合
     * @return 结果
     */
    public static void DeleteiFile(com.aliyun.oss.OSSClient client, String bucketName, String pathList) throws ClientException {
        try{
         client.deleteObject(bucketName,pathList);
        } catch (ClientException e) {
            throw e;
        }
        catch ( Exception e ){
            throw new ClientException(e);
        }
    }
    /**
     * 删除 文件
     * @param pathList  文件路径集合
     * @return 结果
     */
    public static List<String> DeleteMultiFile(com.aliyun.oss.OSSClient client, String bucketName, List<String> pathList) throws ClientException {
        try{
           DeleteObjectsRequest deleteObjectsRequest= new DeleteObjectsRequest(bucketName);
            deleteObjectsRequest.setKeys(pathList);
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(deleteObjectsRequest);
           return deleteObjectsResult.getDeletedObjects();
         } catch (NumberFormatException e) {
        logger.error("upload file to aliyun OSS object server occur NumberFormatException.");
            throw new ClientException(e);
        }
        catch ( Exception e ){
            throw new ClientException(e);
        }
    }
    /**
     * 列出Object<br>
     *
     * @param delimiter
     *            Delimiter 设置为 “/” 时，返回值就只罗列该文件夹下的文件，可以null
     * @param prefix
     *            Prefix 设为某个文件夹名，就可以罗列以此 Prefix 开头的文件，可以null
     * @return 文件列表
     */
    public static List<String> listObject(String delimiter, String prefix) {
        OSSClient client =  OSSClientFactory.createOSSClient();
        String bucketName = OSSClientProperties.bucketName;
      return  ObjectServiceMy.listObject(client,bucketName,delimiter,prefix);
    }

    /**
     * 获取path
     * @param client 客户端
     * @param bucketName bucket 名
     * @param path 文件路径
     * @return  对象
     */
    public static Object getObject(OSSClient client, String bucketName,String path){
        try {
            return ObjectServiceMy.getObject(client,bucketName,path);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        catch(Exception e){
            logger.error("没有读取该文件的权限");
            e.printStackTrace();
        }
        return null;

    }
}
