package com.scsk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ImageFileDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
@Service
public class BaseImage {
    @Value("${cloudantLocal.local}")
    private boolean CLOUDANTLOCAL;
    @Autowired
    private RepositoryUtil repositoryUtil;
    @Autowired
    private CloudantDBService cloudantDBService;
    
    public void createImage(){
        Database db = null;
        try {
            cloudantDBService.cloudantOpen();
            CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
            db = cloudantClient.database(Constants.DB_NAME, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File directory = new File("");
        String path = "";
        if (CLOUDANTLOCAL) {
            // ローカルテスト用
            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/src/main/webapp/img";
        } else {
            // BlueMixサーバ実施用
            path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/apps/myapp.war/img";
        }
        List<ImageFileDoc> fileInfoDocList = new ArrayList<>();
        fileInfoDocList = repositoryUtil.getView(db, ApplicationKeys.INSIGHTVIEW_IMAGEFILE_IMAGEFILE,
                ImageFileDoc.class);
        if(fileInfoDocList!=null && fileInfoDocList.size()!=0){
            for (ImageFileDoc imageFileDoc : fileInfoDocList) {
                String base64 = imageFileDoc.getFileStream();
                String fileName = imageFileDoc.getFileNameEN() + "." + imageFileDoc.getPath().split("\\.")[1];
                base64StringToImg(base64,path,fileName);
            }
        }
    }
    private void base64StringToImg(String base64String, String path, String fileName) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            File file = new File(path + "/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fout = new FileOutputStream(file);
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
