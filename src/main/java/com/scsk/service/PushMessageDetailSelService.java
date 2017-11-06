package com.scsk.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.YamagataMsgDetailDoc;
import com.scsk.model.YamagataMsgTitleDoc;
import com.scsk.request.vo.PushMessageDetailUpdateReqVO;
import com.scsk.response.vo.PushMessageDetailUpdateResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.PasswordUtils;
import com.scsk.util.ResultMessages;

@Service
public class PushMessageDetailSelService
        extends AbstractBLogic<PushMessageDetailUpdateReqVO, PushMessageDetailUpdateResVO> {

    @Autowired
    private ActionLog actionLog;
    @Value("${bank_cd}")
    private String bank_cd;

    /**
     * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param reqVo
     *            入力情報
     */
    @Override
    protected void preExecute(PushMessageDetailUpdateReqVO detailReqVO) throws Exception {

    }

    /**
     * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
     * 
     * @param client
     *            クラウドDBに接続オブジェクト
     * @param reqVo
     *            入力情報
     * @return resVo 詳細情報
     * @throws Exception
     */
    @Override
    protected PushMessageDetailUpdateResVO doExecute(CloudantClient client, PushMessageDetailUpdateReqVO detailReqVO)
            throws Exception {

        String pushMessageDetailLog = "（件名：";
        PushMessageDetailUpdateResVO pushMessageDetailUpdateResVO = new PushMessageDetailUpdateResVO();
        // データベースを取得
        Database db = client.database(Constants.DB_NAME, false);
        // ファイル詳細情報取得
        YamagataMsgTitleDoc yamagataMsgTitleDOC = new YamagataMsgTitleDoc();
        YamagataMsgDetailDoc yamagataMsgDetailDOC = new YamagataMsgDetailDoc();

        if ("2".equals(detailReqVO.getModeType())) {
            try {
                yamagataMsgTitleDOC = (YamagataMsgTitleDoc) repositoryUtil.find(db, detailReqVO.get_id(),
                        YamagataMsgTitleDoc.class);
                pushMessageDetailLog = pushMessageDetailLog + yamagataMsgTitleDOC.getPushTitle();
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
                throw new BusinessException(messages);
            }

            // 件名
            detailReqVO.setPushTitle(yamagataMsgTitleDOC.getPushTitle());

            try {
                yamagataMsgDetailDOC = (YamagataMsgDetailDoc) repositoryUtil.find(db,
                        yamagataMsgTitleDOC.getPushDetailOid(), YamagataMsgDetailDoc.class);
            } catch (BusinessException e) {
                // e.printStackTrace();
                LogInfoUtil.LogDebug(e.getMessage());
                // エラーメッセージを出力、処理終了。
                ResultMessages messages = ResultMessages.warning().add(MessageKeys.E_PUSHMESSAGE_1004);
                throw new BusinessException(messages);
            }
            // メッセージ
            detailReqVO.setPushMessage(yamagataMsgDetailDOC.getPushMessage());
        }

        // 当日日付を取得する（日付フォーマット）
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ASS);
        String createDate = sdf.format(new Date());
        String text = detailReqVO.getPushTitle();
        // String text2 = detailReqVO.getPushMessage().replace("\n",
        // "<BR>").replace(" ", "&nbsp;");
        String text2 = detailReqVO.getPushMessage().replace("\n", "<BR>");
        String imgPath = detailReqVO.getOutPath() + ApplicationKeys.REPORT_HTML_PATH;
        String htmlFileName = createDate + ".html";
        String htmlPath = imgPath + "/" + htmlFileName;
        File file2 = new File(htmlPath);
        StringBuilder sb = new StringBuilder();

        if ("0122".equals(bank_cd)) {
            try {
                file2.createNewFile();// 创建文件

                sb.append(Constants.YAMAGATA_ALLPUSH_MESSAGE_HTML_START);
                // sb.append("<div class=\"box\">");
                sb.append("<p align='center' style='font-weight:bolder'>");
                sb.append(text);
                sb.append("</p>");
                sb.append("<hr color='#ff0000'/>");
                sb.append("<p>");
                sb.append(text2);
                sb.append("</p>");
                // sb.append("</div>");
                sb.append(
                        "<img src=\"https://yamagatabank-api.scsk-api.minefocus.jp/images/contents/YAMAGATA_AM0001003_A.png\">");
                sb.append("</body></html>");

                PrintStream printStream = new PrintStream(new FileOutputStream(file2));

                printStream.println(sb.toString());// 将字符串写入文件

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ("0174".equals(bank_cd) || "0173".equals(bank_cd)) {
            try {
                file2.createNewFile();// 创建文件

                sb.append(Constants.YAMAGATA_ALLPUSH_MESSAGE_HTML_START);
                sb.append("<script>");
                sb.append("function setParentImgUrl(){");
                sb.append(" opener.aaa();");
                sb.append(" window.close();}");
                sb.append("</script>");
                sb.append("<p align='center' style='font-weight:bolder'>");
                sb.append(text);
                sb.append("</p>");
                sb.append("<hr color='#ff0000'/>");
                sb.append("<p>");
                sb.append(text2);
                sb.append("</p>");
                sb.append("</div>");
                if (!"2".equals(detailReqVO.getModeType())) {
                    sb.append("<div style='height:30%;width: 100%;box-sizing:border-box;padding-bottom:30px; left: 0; text-align:center;'>");
                    sb.append(
                            "<button style=\"background-color: #2196f3 !important; color:#FFF; border:0; width:86%; bottom:10px;padding:14px; margin:0 auto;cursor:pointer;\" onclick=\"setParentImgUrl()\">配信する</button>");
                    sb.append("</div>");
                }
                sb.append("</body></html>");

                PrintStream printStream = new PrintStream(new FileOutputStream(file2));

                printStream.println(sb.toString());// 将字符串写入文件

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pushMessageDetailUpdateResVO.setOutPath(htmlFileName);
        pushMessageDetailUpdateResVO.setPushTitle(yamagataMsgTitleDOC.getPushTitle());
        pushMessageDetailUpdateResVO.setPushMessage(yamagataMsgDetailDOC.getPushMessage());
        actionLog.saveActionLog(Constants.ACTIONLOG_PUSH_MESSAGE_3 + pushMessageDetailLog + ")", db);
        return pushMessageDetailUpdateResVO;
    }
}
