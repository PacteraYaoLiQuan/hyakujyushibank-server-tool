package com.scsk.util;

/**
 * ＩＰアドレス鑑定
 * 
 * @author ylq
 * 
 * @param input
 *            IPアドレス
 * @param input
 *            郵便番号
 *            
 * */

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsk.model.rev.GeologicalAppraisalIPDoc;

/**
 * 
 * @author ylq
 *
 */
@Component
public class GeologicalAppraisalIPUtli {
    
    @SuppressWarnings("unchecked")
    public static GeologicalAppraisalIPDoc GeologicalAppraisalIP(String ip, String zip) throws UnsupportedEncodingException, DocumentException {

        boolean flag = false;
        GeologicalAppraisalIPDoc geologicalAppraisalIPDoc = new GeologicalAppraisalIPDoc();
        try {
            String result = SendIdentificationUtil.geologicalAppraisalIPSend(ip, zip);
            
//            SAXReader reader = new SAXReader();
            Document document = null;
            
            // XML変換エラーフラグ　false:success  true:faiue　
            boolean parseErrFlag = false;
            try{
                document = DocumentHelper.parseText(result);
            }catch(Exception e1){
                parseErrFlag = true;
            }
            
            if(parseErrFlag){
                //何もしない
            } else {
                Element root = document.getRootElement();
                
                List<Element> childElements = root.elements();
                for (Element child : childElements) {
//                    System.out.println(child.getName() + ": " + child.getText());
                    if (child.getName().equals("ps-ip")) {
                        geologicalAppraisalIPDoc.setPs_ip(child.getText());
                    }if (child.getName().equals("proxy")) {
                        geologicalAppraisalIPDoc.setProxy(child.getText());
                    }if (child.getName().equals("distance")) {
                        geologicalAppraisalIPDoc.setDistance(child.getText());
                    }if (child.getName().equals("threat")) {
                        if (flag) {
                            geologicalAppraisalIPDoc.setThreat(child.getText());
                            flag = true;
                        }
                    }if (child.getName().equals("search-history")) {
                        geologicalAppraisalIPDoc.setSearchHistory(child.getText());
                    }
                    
                    List<Element> elementList = child.elements();
                    for (Element ele : elementList) {
//                        System.out.println(ele.getName() + ": " + ele.getText());
                        if (ele.getName().equals("code")) {
                            geologicalAppraisalIPDoc.setCountryCode(ele.getText());
                        }if (ele.getName().equals("name")) {
                            if (flag) {
                                geologicalAppraisalIPDoc.setCompanyName(ele.getText());
                            }else {
                                geologicalAppraisalIPDoc.setCountryName(ele.getText());
                                flag = true;
                            }
                        }if (ele.getName().equals("threat")) {
                            if (flag) {
                                geologicalAppraisalIPDoc.setCountryThreat(ele.getText());
                                flag = true;
                            }
                        }if (ele.getName().equals("isMobile")) {
                            geologicalAppraisalIPDoc.setIsMobile(ele.getText());
                        }if (ele.getName().equals("carrier")) {
                            geologicalAppraisalIPDoc.setCarrier(ele.getText());
                        }if (ele.getName().equals("domain")) {
                            geologicalAppraisalIPDoc.setCompanyDomain(ele.getText());
                        }if (ele.getName().equals("city")) {
                            geologicalAppraisalIPDoc.setCompanyCity(ele.getText());
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogInfoUtil.logWarn("", e);
        }
        return geologicalAppraisalIPDoc;
    }
}
