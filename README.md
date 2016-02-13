# Ali云的oss 模块util java packet包工具
该项目是参考了QiKeMi 的 [UEditor-for-aliyun-OSS](https://github.com/qikemi/UEditor-for-aliyun-OSS) 的oss的模块,保留QiKeMi的oss模块的部分代码.该项目主要来源于我开发工作过程中的经验积累所成.

## 功能
### 保留QikeMi 的功能
1. 简单文件上传.
2. 获取客户端
3. properties 参数配置模式

### 新增util
1. 异步传输
2. 临时文件上传
3. 大文件批量上传
4. 删除文件
5. 获取文件
6. 已定义文件上传
7. 已定义获取客户端

### 未来新加util
1. 生成权限限制地址
2. 应用实例.

## PACKAGE说明
我重新实现的代码在com.zome.OSSManger 包里,而com.qikemi.package 包,主要修改了**com.qikemi.packages.alibaba.aliyun.oss.properties.OSSPropeties** 和**com.qikemi.packages.alibaba.aliyun.oss.OSSClientFactory**.

## properties 配置
<pre><code>
## ALIYUN OSSClient Configure

# Ueditor use or not story image to ALIYUN OSS, values true/false
# default value false
useStatus=true

# ALIYUN OSS bucket info
bucketName=ali--cdn-shenzhen
key=
secret=

# auto create Bucket to default (HangZhou) zone, values true/false
# default value false
autoCreateBucket=false

## ALIYUN OSS URL
# 客户端授权地址, 比如 http://oss-cn-hangzhou.aliyuncs.com/ ,
ossCliendEndPoint=http://oss-cn-shenzhen.aliyuncs.com/

# ossEndPoint， 默认 加在 上传文件前面作为获取 数据的网址，若使用cdn 则会使用cdn的地址。
ossEndPoint=http://ali--cdn-shenzhen.oss-cn-shenzhen.aliyuncs.com/

## ALIYUN CDN URL
# Ueditor use or not use ALIYUN CDN, values true/false
# default value false, when useCDN=true, the cdnEndPoint will used. 要是使用cdn，则会使用作为 获取文件的网址
useCDN=false
#cdnEndPoint=http(s)://cdn.xiexianbin.cn.w.kunlunar.com/ ，
cdnEndPoint=


# default Ueditor upload base path, from config.json, when
# useLocalStorager=false, this values will work to delete upload
# file, default value is "upload", unuse now.
#uploadBasePath=upload

# Ueditor use or not use asynchronous model to upload image to ALIYUN OSS
# default value false
useAsynUploader=false
# 多文件传输每个文件最大的大小 ,
#该属性为Long类型,以byte 来记载.默认为100Mb
partSize =100*1024*1024L
# 大文件将会使用 多文件传输,保证稳定性,
#超过该属性大小,则会使用多文件传输,
#该属性为Long 类型,以byte来记载,默认为 100Mb
bigFile =100*1024*1024L
# 临时文件 过期时间设置 ,默认为1天,注意这个参数为int类型
tempFileTimeOut=24* 3600 * 1000

</code></pre>

## 附注
by @shaoyi [zhousahoyi@gmail.com](zhousahoyi@gmail.com)


