package com.scsk.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsk.model.rev.PhoneNumberDoc;

/**
 * 電話番号
 * 
 * 携帯電話番号
 *
 * */
public class PhoneNumberUtil {

    final static String SPLITSTR = "-";

    /**
     * ハイフンなしの電話番号からハイフン付き電話番号を復元する。
     * 
     * @param tel
     *            電話番号
     * @param strict
     *            携帯電話等の番号の区切り方が変わります。<br/>
     *            false => 見慣れた慣用的な区切りになります。<br/>
     *            true => 総務省の資料に厳格に従います。<br/>
     * @return ハイフン付き電話番号
     */
    public static String format(String tel, boolean strict) {
        Map<Integer, Map<String, Integer>> groups = getGroups(strict);

        String number = tel.replaceAll("[^\\d]++", "");

        for (Map.Entry<Integer, Map<String, Integer>> entry : groups.entrySet()) {
            int key = entry.getKey().intValue();
            String area = number.substring(0, key);
            if (entry.getValue().containsKey(area)) {
                int splitLen = entry.getValue().get(area).intValue();
                String newTel = area + SPLITSTR + number.substring(key, key + splitLen) + SPLITSTR
                        + number.substring(key + splitLen);
                return newTel.endsWith(SPLITSTR) ? tel : newTel;
            }
        }
        
        String repNumber = number.replaceAll("\\A(00(?:[013-8]|2\\d|91[02-9])\\d)(\\d++)\\z", "$1-$2");
        if(!number.equals(repNumber)) {
            return repNumber;
        }
        
        return tel;
    }

    /**
     * ハイフンなしの電話番号からハイフン付き電話番号を復元する。
     * 
     * @param tel
     *            電話番号
     * @return ハイフン付き電話番号
     */
    public static String format(String tel) {
        return format(tel, false);
    }
    
    @SuppressWarnings("unchecked")
    public static PhoneNumberDoc phoneNumber(String phone) throws UnsupportedEncodingException, DocumentException {

        PhoneNumberDoc phoneNumberDoc = new PhoneNumberDoc();
        try {
            String result = SendIdentificationUtil.phoneNumberSend(phone);
            
//            SAXReader reader = new SAXReader();
            Document document = null;
            
            // XML変換エラーフラグ　false:success  true:faiue　
            boolean parseErrFlag = false;
            try{
                document = DocumentHelper.parseText(result);
            }catch(Exception e2){
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
                        phoneNumberDoc.setAccess2(child.getText());
                    }
                    
                    List<Element> elementList = child.elements();
                    for (Element ele : elementList) {
//                        System.out.println(ele.getName() + ": " + ele.getText());
                        if (ele.getName().equals("result")) {
                            phoneNumberDoc.setResult2(ele.getText());
                        }if (ele.getName().equals("month")) {
                            phoneNumberDoc.setMonth2(ele.getText());
                        }if (ele.getName().equals("movetel")) {
                            phoneNumberDoc.setMovetel2(ele.getText());
                        }if (ele.getName().equals("carrier")) {
                            phoneNumberDoc.setCarrier2(ele.getText());
                        }if (ele.getName().equals("count")) {
                            phoneNumberDoc.setCount2(ele.getText());
                        }if (ele.getName().equals("attention")) {
                            phoneNumberDoc.setAttention2(ele.getText());
                        }
                        List<Element> chementList = ele.elements();
                        for (Element ch : chementList) {
//                            System.out.println(ch.getName() + ":" + ch.getText());
                            if (ch.getName().equals("tacsflag")) {
                                phoneNumberDoc.setTacsflag2(ch.getText());
                            }if (ch.getName().equals("latestyear")) {
                                phoneNumberDoc.setLatestyear2(ch.getText());
                            }if (ch.getName().equals("latestmonth")) {
                                phoneNumberDoc.setLatestmonth2(ch.getText());
                            }if (ch.getName().equals("f01")) {
                                phoneNumberDoc.setF01_2(ch.getText());
                            }if (ch.getName().equals("f02")) {
                                phoneNumberDoc.setF02_2(ch.getText());
                            }if (ch.getName().equals("f03")) {
                                phoneNumberDoc.setF03_2(ch.getText());
                            }if (ch.getName().equals("f04")) {
                                phoneNumberDoc.setF04_2(ch.getText());
                            }if (ch.getName().equals("f05")) {
                                phoneNumberDoc.setF05_2(ch.getText());
                            }if (ch.getName().equals("f06")) {
                                phoneNumberDoc.setF06_2(ch.getText());
                            }if (ch.getName().equals("f07")) {
                                phoneNumberDoc.setF07_2(ch.getText());
                            }if (ch.getName().equals("f08")) {
                                phoneNumberDoc.setF08_2(ch.getText());
                            }if (ch.getName().equals("f09")) {
                                phoneNumberDoc.setF09_2(ch.getText());
                            }if (ch.getName().equals("f10")) {
                                phoneNumberDoc.setF10_2(ch.getText());
                            }if (ch.getName().equals("f11")) {
                                phoneNumberDoc.setF11_2(ch.getText());
                            }if (ch.getName().equals("f12")) {
                                phoneNumberDoc.setF12_2(ch.getText());
                            }if (ch.getName().equals("f13")) {
                                phoneNumberDoc.setF13_2(ch.getText());
                            }if (ch.getName().equals("f14")) {
                                phoneNumberDoc.setF14_2(ch.getText());
                            }if (ch.getName().equals("f15")) {
                                phoneNumberDoc.setF15_2(ch.getText());
                            }if (ch.getName().equals("f16")) {
                                phoneNumberDoc.setF16_2(ch.getText());
                            }if (ch.getName().equals("f17")) {
                                phoneNumberDoc.setF17_2(ch.getText());
                            }if (ch.getName().equals("f18")) {
                                phoneNumberDoc.setF18_2(ch.getText());
                            }if (ch.getName().equals("f19")) {
                                phoneNumberDoc.setF19_2(ch.getText());
                            }if (ch.getName().equals("f20")) {
                                phoneNumberDoc.setF20_2(ch.getText());
                            }if (ch.getName().equals("f21")) {
                                phoneNumberDoc.setF21_2(ch.getText());
                            }if (ch.getName().equals("f22")) {
                                phoneNumberDoc.setF22_2(ch.getText());
                            }if (ch.getName().equals("f23")) {
                                phoneNumberDoc.setF23_2(ch.getText());
                            }if (ch.getName().equals("f24")) {
                                phoneNumberDoc.setF24_2(ch.getText());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogInfoUtil.logWarn("", e);
        }
        return phoneNumberDoc;
    }
    
    /**
     * 
     * @return
     */
    private static Map<Integer, Map<String, Integer>> getGroups(boolean strict) {
        int len = strict ? 3 : 4;

        Map<String, Integer> group_5 = new HashMap<String, Integer>();
        group_5.put("01564", 1);
        group_5.put("01558", 1);
        group_5.put("01586", 1);
        group_5.put("01587", 1);
        group_5.put("01634", 1);
        group_5.put("01632", 1);
        group_5.put("01547", 1);
        group_5.put("05769", 1);
        group_5.put("04992", 1);
        group_5.put("04994", 1);
        group_5.put("01456", 1);
        group_5.put("01457", 1);
        group_5.put("01466", 1);
        group_5.put("01635", 1);
        group_5.put("09496", 1);
        group_5.put("08477", 1);
        group_5.put("08512", 1);
        group_5.put("08396", 1);
        group_5.put("08388", 1);
        group_5.put("08387", 1);
        group_5.put("08514", 1);
        group_5.put("07468", 1);
        group_5.put("01655", 1);
        group_5.put("01648", 1);
        group_5.put("01656", 1);
        group_5.put("01658", 1);
        group_5.put("05979", 1);
        group_5.put("04996", 1);
        group_5.put("01654", 1);
        group_5.put("01372", 1);
        group_5.put("01374", 1);
        group_5.put("09969", 1);
        group_5.put("09802", 1);
        group_5.put("09912", 1);
        group_5.put("09913", 1);
        group_5.put("01398", 1);
        group_5.put("01377", 1);
        group_5.put("01267", 1);
        group_5.put("04998", 1);
        group_5.put("01397", 1);
        group_5.put("01392", 1);
        Map<String, Integer> group_4 = new HashMap<String, Integer>();
        group_4.put("0768", 2);
        group_4.put("0770", 2);
        group_4.put("0772", 2);
        group_4.put("0774", 2);
        group_4.put("0773", 2);
        group_4.put("0767", 2);
        group_4.put("0771", 2);
        group_4.put("0765", 2);
        group_4.put("0748", 2);
        group_4.put("0747", 2);
        group_4.put("0746", 2);
        group_4.put("0826", 2);
        group_4.put("0749", 2);
        group_4.put("0776", 2);
        group_4.put("0763", 2);
        group_4.put("0761", 2);
        group_4.put("0766", 2);
        group_4.put("0778", 2);
        group_4.put("0824", 2);
        group_4.put("0797", 2);
        group_4.put("0796", 2);
        group_4.put("0555", 2);
        group_4.put("0823", 2);
        group_4.put("0798", 2);
        group_4.put("0554", 2);
        group_4.put("0820", 2);
        group_4.put("0795", 2);
        group_4.put("0556", 2);
        group_4.put("0791", 2);
        group_4.put("0790", 2);
        group_4.put("0779", 2);
        group_4.put("0558", 2);
        group_4.put("0745", 2);
        group_4.put("0794", 2);
        group_4.put("0557", 2);
        group_4.put("0799", 2);
        group_4.put("0738", 2);
        group_4.put("0567", 2);
        group_4.put("0568", 2);
        group_4.put("0585", 2);
        group_4.put("0586", 2);
        group_4.put("0566", 2);
        group_4.put("0564", 2);
        group_4.put("0565", 2);
        group_4.put("0587", 2);
        group_4.put("0584", 2);
        group_4.put("0581", 2);
        group_4.put("0572", 2);
        group_4.put("0574", 2);
        group_4.put("0573", 2);
        group_4.put("0575", 2);
        group_4.put("0576", 2);
        group_4.put("0578", 2);
        group_4.put("0577", 2);
        group_4.put("0569", 2);
        group_4.put("0594", 2);
        group_4.put("0827", 2);
        group_4.put("0736", 2);
        group_4.put("0735", 2);
        group_4.put("0725", 2);
        group_4.put("0737", 2);
        group_4.put("0739", 2);
        group_4.put("0743", 2);
        group_4.put("0742", 2);
        group_4.put("0740", 2);
        group_4.put("0721", 2);
        group_4.put("0599", 2);
        group_4.put("0561", 2);
        group_4.put("0562", 2);
        group_4.put("0563", 2);
        group_4.put("0595", 2);
        group_4.put("0596", 2);
        group_4.put("0598", 2);
        group_4.put("0597", 2);
        group_4.put("0744", 2);
        group_4.put("0852", 2);
        group_4.put("0956", 2);
        group_4.put("0955", 2);
        group_4.put("0954", 2);
        group_4.put("0952", 2);
        group_4.put("0957", 2);
        group_4.put("0959", 2);
        group_4.put("0966", 2);
        group_4.put("0965", 2);
        group_4.put("0964", 2);
        group_4.put("0950", 2);
        group_4.put("0949", 2);
        group_4.put("0942", 2);
        group_4.put("0940", 2);
        group_4.put("0930", 2);
        group_4.put("0943", 2);
        group_4.put("0944", 2);
        group_4.put("0948", 2);
        group_4.put("0947", 2);
        group_4.put("0946", 2);
        group_4.put("0967", 2);
        group_4.put("0968", 2);
        group_4.put("0987", 2);
        group_4.put("0986", 2);
        group_4.put("0985", 2);
        group_4.put("0984", 2);
        group_4.put("0993", 2);
        group_4.put("0994", 2);
        group_4.put("0997", 2);
        group_4.put("0996", 2);
        group_4.put("0995", 2);
        group_4.put("0983", 2);
        group_4.put("0982", 2);
        group_4.put("0973", 2);
        group_4.put("0972", 2);
        group_4.put("0969", 2);
        group_4.put("0974", 2);
        group_4.put("0977", 2);
        group_4.put("0980", 2);
        group_4.put("0979", 2);
        group_4.put("0978", 2);
        group_4.put("0920", 2);
        group_4.put("0898", 2);
        group_4.put("0855", 2);
        group_4.put("0854", 2);
        group_4.put("0853", 2);
        group_4.put("0553", 2);
        group_4.put("0856", 2);
        group_4.put("0857", 2);
        group_4.put("0863", 2);
        group_4.put("0859", 2);
        group_4.put("0858", 2);
        group_4.put("0848", 2);
        group_4.put("0847", 2);
        group_4.put("0835", 2);
        group_4.put("0834", 2);
        group_4.put("0833", 2);
        group_4.put("0836", 2);
        group_4.put("0837", 2);
        group_4.put("0846", 2);
        group_4.put("0845", 2);
        group_4.put("0838", 2);
        group_4.put("0865", 2);
        group_4.put("0866", 2);
        group_4.put("0892", 2);
        group_4.put("0889", 2);
        group_4.put("0887", 2);
        group_4.put("0893", 2);
        group_4.put("0894", 2);
        group_4.put("0897", 2);
        group_4.put("0896", 2);
        group_4.put("0895", 2);
        group_4.put("0885", 2);
        group_4.put("0884", 2);
        group_4.put("0869", 2);
        group_4.put("0868", 2);
        group_4.put("0867", 2);
        group_4.put("0875", 2);
        group_4.put("0877", 2);
        group_4.put("0883", 2);
        group_4.put("0880", 2);
        group_4.put("0879", 2);
        group_4.put("0829", 2);
        group_4.put("0550", 2);
        group_4.put("0228", 2);
        group_4.put("0226", 2);
        group_4.put("0225", 2);
        group_4.put("0224", 2);
        group_4.put("0229", 2);
        group_4.put("0233", 2);
        group_4.put("0237", 2);
        group_4.put("0235", 2);
        group_4.put("0234", 2);
        group_4.put("0223", 2);
        group_4.put("0220", 2);
        group_4.put("0192", 2);
        group_4.put("0191", 2);
        group_4.put("0187", 2);
        group_4.put("0193", 2);
        group_4.put("0194", 2);
        group_4.put("0198", 2);
        group_4.put("0197", 2);
        group_4.put("0195", 2);
        group_4.put("0238", 2);
        group_4.put("0240", 2);
        group_4.put("0260", 2);
        group_4.put("0259", 2);
        group_4.put("0258", 2);
        group_4.put("0257", 2);
        group_4.put("0261", 2);
        group_4.put("0263", 2);
        group_4.put("0266", 2);
        group_4.put("0265", 2);
        group_4.put("0264", 2);
        group_4.put("0256", 2);
        group_4.put("0255", 2);
        group_4.put("0243", 2);
        group_4.put("0242", 2);
        group_4.put("0241", 2);
        group_4.put("0244", 2);
        group_4.put("0246", 2);
        group_4.put("0254", 2);
        group_4.put("0248", 2);
        group_4.put("0247", 2);
        group_4.put("0186", 2);
        group_4.put("0185", 2);
        group_4.put("0144", 2);
        group_4.put("0143", 2);
        group_4.put("0142", 2);
        group_4.put("0139", 2);
        group_4.put("0145", 2);
        group_4.put("0146", 2);
        group_4.put("0154", 2);
        group_4.put("0153", 2);
        group_4.put("0152", 2);
        group_4.put("0138", 2);
        group_4.put("0137", 2);
        group_4.put("0125", 2);
        group_4.put("0124", 2);
        group_4.put("0123", 2);
        group_4.put("0126", 2);
        group_4.put("0133", 2);
        group_4.put("0136", 2);
        group_4.put("0135", 2);
        group_4.put("0134", 2);
        group_4.put("0155", 2);
        group_4.put("0156", 2);
        group_4.put("0176", 2);
        group_4.put("0175", 2);
        group_4.put("0174", 2);
        group_4.put("0178", 2);
        group_4.put("0179", 2);
        group_4.put("0184", 2);
        group_4.put("0183", 2);
        group_4.put("0182", 2);
        group_4.put("0173", 2);
        group_4.put("0172", 2);
        group_4.put("0162", 2);
        group_4.put("0158", 2);
        group_4.put("0157", 2);
        group_4.put("0163", 2);
        group_4.put("0164", 2);
        group_4.put("0167", 2);
        group_4.put("0166", 2);
        group_4.put("0165", 2);
        group_4.put("0267", 2);
        group_4.put("0250", 2);
        group_4.put("0533", 2);
        group_4.put("0422", 2);
        group_4.put("0532", 2);
        group_4.put("0531", 2);
        group_4.put("0436", 2);
        group_4.put("0428", 2);
        group_4.put("0536", 2);
        group_4.put("0299", 2);
        group_4.put("0294", 2);
        group_4.put("0293", 2);
        group_4.put("0475", 2);
        group_4.put("0295", 2);
        group_4.put("0297", 2);
        group_4.put("0296", 2);
        group_4.put("0495", 2);
        group_4.put("0438", 2);
        group_4.put("0466", 2);
        group_4.put("0465", 2);
        group_4.put("0467", 2);
        group_4.put("0478", 2);
        group_4.put("0476", 2);
        group_4.put("0470", 2);
        group_4.put("0463", 2);
        group_4.put("0479", 2);
        group_4.put("0493", 2);
        group_4.put("0494", 2);
        group_4.put("0439", 2);
        group_4.put("0268", 2);
        group_4.put("0480", 2);
        group_4.put("0460", 2);
        group_4.put("0538", 2);
        group_4.put("0537", 2);
        group_4.put("0539", 2);
        group_4.put("0279", 2);
        group_4.put("0548", 2);
        group_4.put("0280", 2);
        group_4.put("0282", 2);
        group_4.put("0278", 2);
        group_4.put("0277", 2);
        group_4.put("0269", 2);
        group_4.put("0270", 2);
        group_4.put("0274", 2);
        group_4.put("0276", 2);
        group_4.put("0283", 2);
        group_4.put("0551", 2);
        group_4.put("0289", 2);
        group_4.put("0287", 2);
        group_4.put("0547", 2);
        group_4.put("0288", 2);
        group_4.put("0544", 2);
        group_4.put("0545", 2);
        group_4.put("0284", 2);
        group_4.put("0291", 2);
        group_4.put("0285", 2);
        group_4.put("0120", 3);
        group_4.put("0570", 3);
        group_4.put("0800", 3);
        group_4.put("0990", 3);
        Map<String, Integer> group_3 = new HashMap<String, Integer>();
        group_3.put("099", 3);
        group_3.put("054", 3);
        group_3.put("058", 3);
        group_3.put("098", 3);
        group_3.put("095", 3);
        group_3.put("097", 3);
        group_3.put("052", 3);
        group_3.put("053", 3);
        group_3.put("011", 3);
        group_3.put("096", 3);
        group_3.put("049", 3);
        group_3.put("015", 3);
        group_3.put("048", 3);
        group_3.put("072", 3);
        group_3.put("084", 3);
        group_3.put("028", 3);
        group_3.put("024", 3);
        group_3.put("076", 3);
        group_3.put("023", 3);
        group_3.put("047", 3);
        group_3.put("029", 3);
        group_3.put("075", 3);
        group_3.put("025", 3);
        group_3.put("055", 3);
        group_3.put("026", 3);
        group_3.put("079", 3);
        group_3.put("082", 3);
        group_3.put("027", 3);
        group_3.put("078", 3);
        group_3.put("077", 3);
        group_3.put("083", 3);
        group_3.put("022", 3);
        group_3.put("086", 3);
        group_3.put("089", 3);
        group_3.put("045", 3);
        group_3.put("044", 3);
        group_3.put("092", 3);
        group_3.put("046", 3);
        group_3.put("017", 3);
        group_3.put("093", 3);
        group_3.put("059", 3);
        group_3.put("073", 3);
        group_3.put("019", 3);
        group_3.put("087", 3);
        group_3.put("042", 3);
        group_3.put("018", 3);
        group_3.put("043", 3);
        group_3.put("088", 3);
        group_3.put("050", 4);
        group_3.put("020", len);
        group_3.put("070", len);
        group_3.put("080", len);
        group_3.put("090", len);
        Map<String, Integer> group_2 = new HashMap<String, Integer>();
        group_2.put("04", 4);
        group_2.put("03", 4);
        group_2.put("06", 4);
        Map<Integer, Map<String, Integer>> groups = new LinkedHashMap<Integer, Map<String, Integer>>();
        groups.put(5, group_5);
        groups.put(4, group_4);
        groups.put(3, group_3);
        groups.put(2, group_2);

        return groups;
    }
}
