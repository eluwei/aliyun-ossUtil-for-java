package com.qikemi.packages.alibaba.aliyun.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ListObjectsRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

/**
 * Object是OSS中最基本的数据单元，你可以把它简单地理解为文件<br>
 * 
 * 在OSS中，用户操作的基本数据单元是Object。单个Object最大允许存储5TB的数据。<br>
 * Object包含key、meta和data。其中，<br>
 * key是Object的名字；<br>
 * meta是用户对该object的描述，由一系列name-value对组成；<br>
 * data是Object的数据。
 * 
 * @create date : 2014年10月28日 下午2:03:24
 * @Author XieXianbin<a.b@hotmail.com>
 * @Source Repositories Address: <https://github.com/qikemi/UEditor-for-aliyun-OSS>
 */
public class ObjectService {

	/**
	 * 上传Object
	 * 
	 * @param client
	 * @param bucketName
	 * @param key
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static PutObjectResult putObject(OSSClient client,
			String bucketName, String key, String filePath)
			throws FileNotFoundException {

		// 获取指定文件的输入流
		File file = new File(filePath);
		InputStream content = new FileInputStream(file);

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(file.length());
		// 用户自定义文件名称
		meta.addUserMetadata("filename", key);

		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, key, content,
				meta);

		return result;
	}

	/**
	 * 上传Object
	 * 
	 * @param client
	 * @param bucketName
	 * @param key
	 * @param content
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static PutObjectResult putObject(OSSClient client,
			String bucketName, String key, InputStream content)
			throws NumberFormatException, IOException {

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(Integer.parseInt(String.valueOf(content
				.available())));
		// 用户自定义文件名称
		meta.addUserMetadata("filename", key);

		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, key, content,
				meta);

		return result;
	}

	/**
	 * 列出Object<br>
	 * 
	 * @param client
	 * @param bucketName
	 * @param delimiter
	 *            Delimiter 设置为 “/” 时，返回值就只罗列该文件夹下的文件，可以null
	 * @param prefix
	 *            Prefix 设为某个文件夹名，就可以罗列以此 Prefix 开头的文件，可以null
	 * @return
	 */
	public static List<String> listObject(OSSClient client, String bucketName,
			String delimiter, String prefix) {

		// 是否循环的标识
		boolean hasNext = false;
		// 设定结果从Marker之后按字母排序的第一个开始返回
		String marker = "";
		//
		// ObjectListing listing = new ObjectListing();
		List<String> filePathList = new ArrayList<String>();
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(
				bucketName);

		// 是一个用于对Object名字进行分组的字符。所有名字包含指定的前缀且第一次出现Delimiter字符之间的object作为一组元素:
		// CommonPrefixes

		listObjectsRequest.setPrefix(prefix);

		do {
			// 设定结果从Marker之后按字母排序的第一个开始返回
			listObjectsRequest.setMarker(marker);
			// 获取指定bucket下的所有Object信息
			ObjectListing sublisting = client.listObjects(listObjectsRequest);
			// 如果Bucket中的Object数量大于100，则只会返回100个Object， 且返回结果中 IsTruncated
			// 为false
			if (sublisting.isTruncated()) {
				hasNext = true;
				marker = sublisting.getNextMarker();
			} else {
				hasNext = false;
				marker = "";
			}
			// // 遍历所有Object
			for (OSSObjectSummary objectSummary : sublisting.getObjectSummaries()) {
				// System.out.println(objectSummary.getKey());
				filePathList.add(objectSummary.getKey());
			}
		} while (hasNext);

		return filePathList;
	}

    /**
     *  获取 object
     * @param client 客户端
     * @param bucketName    bucket 名
     * @param key 路径
     * @return
     * @throws IOException
     * @since 2.0
     *  @author shaoyi
     */
	public static Object getObject(OSSClient client, String bucketName, String key)
			throws IOException {
        try {
            OSSObject ossObject = client.getObject(bucketName, key);
            InputStream objectContent = null;
            try {
                // 获取Object的输入流
                objectContent = ossObject.getObjectContent();
                Object object = objectContent.read();
                objectContent.close();

                return object;
            } catch ( IOException ioe ) {
                throw ioe;
            } finally {
                //关闭数据流
                objectContent.close();
            }
        } catch ( Exception e ) {
            throw e;
        }
    }

}
