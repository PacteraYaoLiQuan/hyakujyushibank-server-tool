package com.scsk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;

@Service
public class BaseContents {
	@Value("${cloudantLocal.local}")
	private boolean CLOUDANTLOCAL;
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private CloudantDBService cloudantDBService;

	public void createContents() {
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
			path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/src/main/webapp/images/contents";
		} else {
			// BlueMixサーバ実施用
			path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/apps/myapp.war/images/contents";
		}
		List<ContentsDoc> contentsList = repositoryUtil.getView(db,
				ApplicationKeys.INSIGHTVIEW_CONTENTSLIST_CONTENTSLIST, ContentsDoc.class);
		if (contentsList != null && contentsList.size() != 0) {
			for (ContentsDoc contentsDoc : contentsList) {
				if (!contentsDoc.getContentsFileName1().equals("") && !contentsDoc.getContentsFile1().equals("")) {
					base64StringToContents(contentsDoc.getContentsFile1(), path, contentsDoc.getContentsFileName1());
					contentsDoc.setCreateFlag1(1);
				}
				if (!contentsDoc.getContentsFileName2().equals("") && !contentsDoc.getContentsFile2().equals("")) {
					base64StringToContents(contentsDoc.getContentsFile2(), path, contentsDoc.getContentsFileName2());
					contentsDoc.setCreateFlag2(1);
				}
				if (!contentsDoc.getContentsFileName3().equals("") && !contentsDoc.getContentsFile3().equals("")) {
					base64StringToContents(contentsDoc.getContentsFile3(), path, contentsDoc.getContentsFileName3());
					contentsDoc.setCreateFlag3(1);
				}
				if (!contentsDoc.getContentsFileName4().equals("") && !contentsDoc.getContentsFile4().equals("")) {
					base64StringToContents(contentsDoc.getContentsFile4(), path, contentsDoc.getContentsFileName4());
					contentsDoc.setCreateFlag4(1);
				}
				if (!contentsDoc.getContentsFileName5().equals("") && !contentsDoc.getContentsFile5().equals("")) {
					base64StringToContents(contentsDoc.getContentsFile5(), path, contentsDoc.getContentsFileName5());
					contentsDoc.setCreateFlag5(1);
				}
				repositoryUtil.update(db, contentsDoc);
			}
		}
	}

	private void base64StringToContents(String base64String, String path, String fileName) {
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
