package com.scsk.batch.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.ContentsDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.LogInfoUtil_Batch;

public class BatCreateContentsController implements Job {
	private RepositoryUtil repositoryUtil = new RepositoryUtil();
	private int deleteSuccess = 0, deleteFail = 0, listSize = 0;
	private String num = "", insertNum = "", fileName = "", errorMessage = "";
	private int updateSuccess = 0, updateFail = 0, updateListSize = 0;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LogInfoUtil_Batch.LogInfo("==========  コンテンツ作成バッチ実施開始  ==========");
		CloudantDBService cloudantDBService = (CloudantDBService) context.getJobDetail().getJobDataMap()
				.get("cloudantDBService");
		boolean CLOUDANTLOCAL = (boolean) context.getJobDetail().getJobDataMap().get("CLOUDANTLOCAL");
		try {
			cloudantDBService.cloudantOpen();
			CloudantClient cloudantClient = cloudantDBService.getCloudantClient();
			Database db = cloudantClient.database(Constants.DB_NAME, false);
			File directory = new File("");
			String path = "";
			String base64String = "";
			if (CLOUDANTLOCAL) {
				path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
						+ "/src/main/webapp/images/contents";
			} else {
				path = directory.getAbsolutePath().toString().replaceAll("\\\\", "/")
						+ "/apps/myapp.war/images/contents";
			}
			List<ContentsDoc> contentsList = repositoryUtil.getView(db,
					ApplicationKeys.INSIGHTVIEW_CONTENTSLIST_CONTENTSLIST, ContentsDoc.class);
			if (contentsList != null && contentsList.size() != 0) {
				for (ContentsDoc contentsDoc : contentsList) {
					if (contentsDoc.getCreateFlag1() == 0 && !contentsDoc.getContentsFileName1().equals("")
							&& !contentsDoc.getContentsFile1().equals("")) {
						base64StringToContents(contentsDoc.getContentsFile1(), path,
								contentsDoc.getContentsFileName1());
						contentsDoc.setCreateFlag1(1);
					}
					if (contentsDoc.getCreateFlag2() == 0 && !contentsDoc.getContentsFileName2().equals("")
							&& !contentsDoc.getContentsFile2().equals("")) {
						base64StringToContents(contentsDoc.getContentsFile2(), path,
								contentsDoc.getContentsFileName2());
						contentsDoc.setCreateFlag2(1);
					}
					if (contentsDoc.getCreateFlag3() == 0 && !contentsDoc.getContentsFileName3().equals("")
							&& !contentsDoc.getContentsFile3().equals("")) {
						base64StringToContents(contentsDoc.getContentsFile3(), path,
								contentsDoc.getContentsFileName3());
						contentsDoc.setCreateFlag3(1);
					}
					if (contentsDoc.getCreateFlag4() == 0 && !contentsDoc.getContentsFileName4().equals("")
							&& !contentsDoc.getContentsFile4().equals("")) {
						base64StringToContents(contentsDoc.getContentsFile4(), path,
								contentsDoc.getContentsFileName4());
						contentsDoc.setCreateFlag4(1);
					}
					if (contentsDoc.getCreateFlag5() == 0 && !contentsDoc.getContentsFileName5().equals("")
							&& !contentsDoc.getContentsFile5().equals("")) {
						base64StringToContents(contentsDoc.getContentsFile5(), path,
								contentsDoc.getContentsFileName5());
						contentsDoc.setCreateFlag5(1);
					}
					if(contentsDoc.getCreateFlag1()==2){
						File file=new File(path+"/"+contentsDoc.getContentsFileName1());
						file.delete();
						contentsDoc.setCreateFlag1(0);
						contentsDoc.setContentsFileName1("");
					}
					if(contentsDoc.getCreateFlag2()==2){
						File file=new File(path+"/"+contentsDoc.getContentsFileName2());
						file.delete();
						contentsDoc.setCreateFlag2(0);
						contentsDoc.setContentsFileName2("");
					}
					if(contentsDoc.getCreateFlag3()==2){
						File file=new File(path+"/"+contentsDoc.getContentsFileName3());
						file.delete();
						contentsDoc.setCreateFlag3(0);
						contentsDoc.setContentsFileName3("");
					}
					if(contentsDoc.getCreateFlag4()==2){
						File file=new File(path+"/"+contentsDoc.getContentsFileName4());
						file.delete();
						contentsDoc.setCreateFlag4(0);
						contentsDoc.setContentsFileName4("");
					}
					if(contentsDoc.getCreateFlag5()==2){
						File file=new File(path+"/"+contentsDoc.getContentsFileName5());
						file.delete();
						contentsDoc.setCreateFlag5(0);
						contentsDoc.setContentsFileName5("");
					}
					repositoryUtil.update(db, contentsDoc);
				}
			}
		} catch (Exception e) {
			LogInfoUtil_Batch.logWarn(e.getMessage(), e);
		} finally {
			LogInfoUtil_Batch.LogInfo("==========  コンテンツ作成バッチ実施完了  ==========");
			cloudantDBService.cloudantClose();
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
			updateFail += 1;
			errorMessage = e.getMessage();
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
