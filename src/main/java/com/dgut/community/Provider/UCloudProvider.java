package com.dgut.community.Provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucketName}")
    private String bucketName;

    @Value("${ucloud.ufile.regoin}")
    private String regoin;

    @Value("${ucloud.ufile.suffix}")
    private String suffix;

    @Value("${ucloud.ufile.expires}")
    private Integer expires;



    public String upload(InputStream inputStream, String mimeType,String fileName){
        String confileName="";
        String[] split = fileName.split("\\.");
        if (split.length>1){
            confileName= UUID.randomUUID().toString()+"."+split[split.length-1];
        }else {
            return null;
        }

        try {
            ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig config = new ObjectConfig(regoin, suffix);
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(inputStream, mimeType)
                    .nameAs(confileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();

                    if (response != null && response.getRetCode() == 0){
                        String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                                .getDownloadUrlFromPrivateBucket(confileName, bucketName, expires)
                                .createUrl();
                        return url;
                    }else {
                        return null;
                    }

        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
