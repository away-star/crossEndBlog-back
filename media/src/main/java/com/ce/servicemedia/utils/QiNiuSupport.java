package com.ce.servicemedia.utils;

import com.ce.servicemedia.web.config.QiNiuConfigBean;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author xingxing
 * @date 2022/10/12 18:03
 * @desc
 */
@Service
@Slf4j
public class QiNiuSupport {

    @Resource
    public UploadManager uploadManager;

    @Resource
    private BucketManager bucketManager;

    @Resource
    private Auth auth;


    /**
     * 最大尝试次数
     */
    public static final Integer maxReTry = 3;
    /**
     * 七牛云操作成功状态码
     */
    public static final Integer successCode = 200;


    /**
     * 以文件的形式上传 并设置文件上传类型
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    public String uploadFileMimeType(File file, String fileName) throws QiniuException {
        //获取要上传文件的MIME 类型
        String mime = URLConnection.guessContentTypeFromName(file.getName());
        log.info("当前文件MIME类型为：{}", mime);
        Response response = this.uploadManager.put(file, fileName, getUploadToken(), getPutPolicy(),
                mime, false);
        int retry = 0;
        while (response.needRetry() && retry < maxReTry) {
            log.info("当前操作需要进行重试，目前重试第{}次", retry + 1);
            response = this.uploadManager.put(file, fileName, getUploadToken(), getPutPolicy(),
                    mime, false);
            retry++;
        }
        if (response.statusCode == successCode) {
            return new StringBuffer().append(QiNiuConfigBean.getProtocol())
                    .append(QiNiuConfigBean.getCdnProfile()).append("/").append(fileName).toString();
        }
        return "上传失败!";
    }

    /**
     * 以文件的形式上传
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    public String uploadFile(byte[] file, String fileName) throws QiniuException {
        Response response = this.uploadManager.put(file, fileName, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < maxReTry) {
            log.info("当前操作需要进行重试，目前重试第{}次", retry + 1);
            response = this.uploadManager.put(file, fileName, getUploadToken());
            retry++;
        }
        if (response.statusCode == successCode) {
            return new StringBuffer().append(QiNiuConfigBean.getProtocol())
                    .append(QiNiuConfigBean.getCdnProfile()).append("/").append(fileName).toString();
        }
        return "上传失败!";
    }

    /**
     * 以流的形式上传
     *
     * @param inputStream
     * @return
     * @throws QiniuException
     */
    public String uploadFileInputStream(InputStream inputStream, String fileName)
            throws QiniuException {
        Response response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < maxReTry) {
            log.info("当前操作需要进行重试，目前重试第{}次", retry + 1);
            response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
            retry++;
        }
        if (response.statusCode == successCode) {
            return new StringBuffer().append(QiNiuConfigBean.getProtocol())
                    .append(QiNiuConfigBean.getCdnProfile()).append("/").append(fileName).toString();
        }
        return "上传失败!";
    }

    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    public String delete(String key) throws QiniuException {
        Response response = bucketManager.delete(QiNiuConfigBean.getBucket(), key);
        int retry = 0;
        //判断是否需要 重试 删除 且重试次数为3
        while (response.needRetry() && retry++ < maxReTry) {
            log.info("当前操作需要进行重试，目前重试第{}次", retry + 1);
            response = bucketManager.delete(QiNiuConfigBean.getBucket(), key);
        }
        return response.statusCode == successCode ? "删除成功!" : "删除失败!";
    }


    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(QiNiuConfigBean.getBucket());
    }

    /**
     * 定义七牛云上传的相关策略
     */
    public StringMap getPutPolicy() {
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        return stringMap;
    }

    /**
     * 获取公共空间文件
     *
     * @param fileKey 要下载的文件名
     * @return
     */
    public String getPublicFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String url = String.format("%s%s/%s", QiNiuConfigBean.getProtocol(), QiNiuConfigBean.getCdnProfile(), encodedFileName);
        log.info("下载地址：{}", url);
        return url;
    }

    /**
     * 获取私有空间文件
     *
     * @param fileKey 要下载的文件名
     * @return
     */
    public String getPrivateFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", QiNiuConfigBean.getCdnProfile(), encodedFileName);
        //1小时，可以自定义链接过期时间
        long expireInSeconds = 3600;
        String privateUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return privateUrl;
    }


    //此方法会将文件同步到项目根目录，具体原因位置！！

    /**
     * MultipartFile 转file
     *
     * @param file
     * @return
     * @throws Exception
     */
    public File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
