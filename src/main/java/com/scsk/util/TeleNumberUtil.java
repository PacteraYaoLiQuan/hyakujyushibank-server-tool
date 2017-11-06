package com.scsk.util;

/**
 * 電話番号鑑定
 * 
 * 自宅電話番号鑑定
 * @author ylq
 * 
 * @param input
 *            リクエストデータ
 *            
 * */

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lowagie.text.DocumentException;
import com.scsk.model.rev.TeleNumberDoc;



public class TeleNumberUtil {

    @SuppressWarnings("unchecked")
    public static TeleNumberDoc teleNumber(String tel) throws UnsupportedEncodingException, DocumentException {

        TeleNumberDoc teleNumberDoc = new TeleNumberDoc();
        try {
            String result = SendIdentificationUtil.teleNumberSend(tel);
            
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
                    if (child.getName().equals("access")) {
                        teleNumberDoc.setAccess1(child.getText());
                    }
                    
                    List<Element> elementList = child.elements();
                    for (Element ele : elementList) {
//                        System.out.println(ele.getName() + ": " + ele.getText());
                        if (ele.getName().equals("result")) {
                            teleNumberDoc.setResult1(ele.getText());
                        }if (ele.getName().equals("month")) {
                            teleNumberDoc.setMonth1(ele.getText());
                        }if (ele.getName().equals("movetel")) {
                            teleNumberDoc.setMovetel1(ele.getText());
                        }if (ele.getName().equals("carrier")) {
                            teleNumberDoc.setCarrier1(ele.getText());
                        }if (ele.getName().equals("count")) {
                            teleNumberDoc.setCount1(ele.getText());
                        }if (ele.getName().equals("attention")) {
                            teleNumberDoc.setAttention1(ele.getText());
                        }
                        List<Element> chementList = ele.elements();
                        for (Element ch : chementList) {
//                            System.out.println(ch.getName() + ":" + ch.getText());
                            if (ch.getName().equals("tacsflag")) {
                                teleNumberDoc.setTacsflag1(ch.getText());
                            }if (ch.getName().equals("latestyear")) {
                                teleNumberDoc.setLatestyear1(ch.getText());
                            }if (ch.getName().equals("latestmonth")) {
                                teleNumberDoc.setLatestmonth1(ch.getText());
                            }if (ch.getName().equals("f01")) {
                                teleNumberDoc.setF01_1(ch.getText());
                            }if (ch.getName().equals("f02")) {
                                teleNumberDoc.setF02_1(ch.getText());
                            }if (ch.getName().equals("f03")) {
                                teleNumberDoc.setF03_1(ch.getText());
                            }if (ch.getName().equals("f04")) {
                                teleNumberDoc.setF04_1(ch.getText());
                            }if (ch.getName().equals("f05")) {
                                teleNumberDoc.setF05_1(ch.getText());
                            }if (ch.getName().equals("f06")) {
                                teleNumberDoc.setF06_1(ch.getText());
                            }if (ch.getName().equals("f07")) {
                                teleNumberDoc.setF07_1(ch.getText());
                            }if (ch.getName().equals("f08")) {
                                teleNumberDoc.setF08_1(ch.getText());
                            }if (ch.getName().equals("f09")) {
                                teleNumberDoc.setF09_1(ch.getText());
                            }if (ch.getName().equals("f10")) {
                                teleNumberDoc.setF10_1(ch.getText());
                            }if (ch.getName().equals("f11")) {
                                teleNumberDoc.setF11_1(ch.getText());
                            }if (ch.getName().equals("f12")) {
                                teleNumberDoc.setF12_1(ch.getText());
                            }if (ch.getName().equals("f13")) {
                                teleNumberDoc.setF13_1(ch.getText());
                            }if (ch.getName().equals("f14")) {
                                teleNumberDoc.setF14_1(ch.getText());
                            }if (ch.getName().equals("f15")) {
                                teleNumberDoc.setF15_1(ch.getText());
                            }if (ch.getName().equals("f16")) {
                                teleNumberDoc.setF16_1(ch.getText());
                            }if (ch.getName().equals("f17")) {
                                teleNumberDoc.setF17_1(ch.getText());
                            }if (ch.getName().equals("f18")) {
                                teleNumberDoc.setF18_1(ch.getText());
                            }if (ch.getName().equals("f19")) {
                                teleNumberDoc.setF19_1(ch.getText());
                            }if (ch.getName().equals("f20")) {
                                teleNumberDoc.setF20_1(ch.getText());
                            }if (ch.getName().equals("f21")) {
                                teleNumberDoc.setF21_1(ch.getText());
                            }if (ch.getName().equals("f22")) {
                                teleNumberDoc.setF22_1(ch.getText());
                            }if (ch.getName().equals("f23")) {
                                teleNumberDoc.setF23_1(ch.getText());
                            }if (ch.getName().equals("f24")) {
                                teleNumberDoc.setF24_1(ch.getText());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogInfoUtil.logWarn("", e);
        }
        return teleNumberDoc;
    }
}
