package com.scsk.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scsk.constants.ApplicationKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.SampleReportDoc;
import com.scsk.report.ReportBLogic;
import com.scsk.util.ResultMessages;


/**
 * 帳票出力コントロール。<br>
 * <br>
 * 帳票出力を実装すること。<br>
 */
@Controller
@RequestMapping("/protected")  
public class SampleReportController {

	@Autowired
	private ReportBLogic reportBLogic;
	
    /**
     *  帳票出力メソッド。
     * @param req request
     * @param res response
     */
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String report(HttpServletRequest req, HttpServletResponse res, Model model) {
		
		// 帳票出力用の datasourceを準備
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(SampleReportDoc.getList());
		// 帳票出力用の パラメータを準備
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("para1", "あいうえお");
		parameters.put("para2", "bbb");

		this.getClass().getClassLoader();
		//プロジェクトのパスを取得
		String jasperName = ApplicationKeys.ACCOUNT001_REPORT;
		
		try{
//			reportBLogic.reportCreate(jasperName,parameters,jrDataSource,req,res);			
		}catch(BusinessException be){
			//帳票出力失敗
      	  ResultMessages messages = be.getResultMessages();
      	  model.addAttribute(messages);
      	  return "welcomePage";
		}
		return "";
	}
	

}
