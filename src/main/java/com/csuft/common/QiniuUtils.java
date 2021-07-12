package com.csuft.common;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class QiniuUtils {
    //生成上传凭证，然后准备上传
    public  static String qiniu_img_url_pre = "http://qw3yf2srn.hn-bkt.clouddn.com/";
    public  static String accessKey = "QruPNGOiDKwNpJV8CypOAGLw2YV8JnKk4eM3IXDt";
    public  static String secretKey = "-w9XHIxxt5LCaD2j1zXOcEDN3TgVy9Y38Scle9YH";
    public  static String bucket = "csuft-tk-tc";

    /**
     * 文件上传
     * @param uploadBytes 文件内容,字节数组
     * @param uploadFileName 保存到服务器端的文件名
     */
    public static void upload2QiNiu(byte[] uploadBytes, String uploadFileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = uploadFileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            System.out.println(response.bodyString());
            //访问路径
            System.out.println(qiniu_img_url_pre + uploadFileName);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                ex2.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     * @param filename 服务端文件名
     */
    public static void deleteFile2QiNiu(String filename){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        String key = filename;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    //测试上传与删除
    public static void main(String[] args) throws IOException {

        //测试上传
        String localFilePath = "D:/logo1.jpg";
        FileInputStream fileInputStream = new FileInputStream(localFilePath);
        byte[] data = new byte[1024 * 1024];
        fileInputStream.read(data);
        String saveFileName = UUID.randomUUID().toString().replace("-", "");
        QiniuUtils.upload2QiNiu(data, saveFileName);


        //测试删除
        //QiniuUtils.deleteFile2QiNiu("f0a0b3e977d24fcd9f50daa917faadf6");
    }
}
