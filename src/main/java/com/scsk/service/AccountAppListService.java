package com.scsk.service;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.scsk.blogic.AbstractBLogic;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.AccountAppDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.request.vo.AccountAppListButtonReqVO;
import com.scsk.response.vo.AccountAppListButtonResVO;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.Utils;
import com.scsk.vo.AccountAppInitVO;

/**
 * 受付進捗管理表出力用一覧検索サービス。<br>
 * <br>
 * 受付進捗管理表出力用一覧検索を実装するロジック。<br>
 */
@Service
public class AccountAppListService extends AbstractBLogic<AccountAppListButtonReqVO, AccountAppListButtonResVO> {
	@Autowired
	private RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;
	/**
	 * 主処理実行する前にパラメータを整理メソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param accountAppListButtonReqVO
	 *            申込一覧情報
	 */
	@Override
	protected void preExecute( AccountAppListButtonReqVO accountAppListButtonReqVO) throws Exception {

	}

	/**
	 * 主処理実行するメソッド（基礎クラスの抽象メソッドを実現）。
	 * 
	 * @param client
	 *            クラウドDBに接続オブジェクト
	 * @param accountAppListButtonReqVO
	 *            申込一覧情報
	 * @return accountAppListButtonResVO 受付進捗管理表出力用情報
	 */
	@Override
	protected AccountAppListButtonResVO doExecute(CloudantClient client,
			AccountAppListButtonReqVO accountAppListButtonReqVO) throws Exception {
		// データベースを取得
		Database db = client.database(Constants.DB_NAME, false);

		AccountAppListButtonResVO accountAppListButtonResVO = new AccountAppListButtonResVO();
		List<AccountAppInitVO> accountAppList = new ArrayList<>();
		String receiptDate = "";
		if (Utils.isNotNullAndEmpty(accountAppListButtonReqVO.getReceiptDate())) {
			receiptDate = accountAppListButtonReqVO.getReceiptDate() + ":00";
		}

		// 受付進捗管理表出力用データを取得
		List<AccountAppDoc> accountAppDocList = new ArrayList<>();

		String[] status = accountAppListButtonReqVO.getStatus();

		for (String tt : status) {
			// 画面の検索条件を入力しない場合、全部受付進捗管理表出力用データを取得
			if (tt.equals("all") && accountAppListButtonReqVO.getReceiptDate().isEmpty()) {
				accountAppDocList = repositoryUtil.getView(db,ApplicationKeys.INSIGHTVIEW_ACCOUNTAPPLIST_ACCOUNTAPPLIST,AccountAppDoc.class);
			} else {
				MapReduce view = new MapReduce();
				// 画面の検索条件、申込受付日付だけ入力場合、
				if (tt.equals("all") && !accountAppListButtonReqVO.getReceiptDate().isEmpty()) {
					view.setMap("function (doc) {if(doc.docType && doc.docType === \"ACCOUNTAPPLICATION\" && doc.delFlg && doc.delFlg===\"0\") {emit(doc.accountAppSeq, 1);}}");
				} else if (!tt.equals("all") && accountAppListButtonReqVO.getReceiptDate().isEmpty()) {
					// 画面の検索条件、ステータスだけ入力場合、
					view.setMap("function (doc) {if(doc.docType && doc.docType === \"ACCOUNTAPPLICATION\" && doc.delFlg && doc.delFlg===\"0\" && doc.status===\""
							+ tt + "\") {emit(doc.accountAppSeq, 1);}}");
				} else if (!tt.equals("all") && !accountAppListButtonReqVO.getReceiptDate().isEmpty()) {
					// 画面の検索条件、ステータスと申込受付日付入力場合、
					view.setMap("function (doc) {if(doc.docType && doc.docType === \"ACCOUNTAPPLICATION\" && doc.delFlg && doc.delFlg===\"0\" && doc.status===\""
							+ tt + "\") {emit(doc.accountAppSeq, 1);}}");
				}
				// 受付進捗管理表出力用データを取得
				accountAppDocList = repositoryUtil.queryByDynamicView(db, view,AccountAppDoc.class);
			}

			if (accountAppDocList != null && accountAppDocList.size() != 0) {
				for (AccountAppDoc doc : accountAppDocList) {
					AccountAppInitVO accountAppInitVO = new AccountAppInitVO();
					if (receiptDate.isEmpty() || receiptDate.compareTo(doc.getReceiptDate()) >= 0) {
						// 一覧出力用データを戻る
						accountAppInitVO.set_id(doc.get_id());
						accountAppInitVO.set_rev(doc.get_rev());
						// 帳票出力回数
						accountAppInitVO.setBillOutputCount(doc.getBillOutputCount());
						// 姓名
						accountAppInitVO.setName(encryptorUtil.decrypt(doc.getLastName())
								+ " "
								+ encryptorUtil.decrypt(doc.getFirstName()));
						// 申込受付日付
						accountAppInitVO.setReceiptDate(doc.getReceiptDate());
						// 受付番号
						accountAppInitVO.setAccountAppSeq(doc.getAccountAppSeq());
						// ステータス
						accountAppInitVO.setStatus(doc.getStatus());
						// 生年月日
						String birthday = encryptorUtil.decrypt(doc.getBirthday());
						if (birthday.length() == 8) {
							accountAppInitVO.setBirthday(birthday.substring(0,4)
									+ "/"
									+ birthday.substring(4, 6)
									+ "/"
									+ birthday.substring(6));
						} else if (Utils.isNotNullAndEmpty(encryptorUtil.decrypt(doc.getBirthday()))) {
							accountAppInitVO.setBirthday(birthday);
						} else {
							accountAppInitVO.setBirthday("");
						}
						// 生年月日(和暦)
						if (Utils.isNotNullAndEmpty(birthday) && birthday.length() > 7) {
							String birthdayJP = japaneseCalendar(birthday);
							accountAppInitVO.setBirthdayJP(birthdayJP);
						} else {
							accountAppInitVO.setBirthdayJP("");
						}

						accountAppList.add(accountAppInitVO);
					}
				}
			}
		}
		accountAppListButtonResVO.setAccountAppList(accountAppList);
		actionLog.saveActionLog(Constants.ACTIONLOG_ACCOUNT_2, db);
		return accountAppListButtonResVO;
	}

	/**
	 * 和暦にフォーマットするメソッド。
	 * 
	 * @param inputdDate
	 *            　処理前日付
	 * @return date 処理後和歴日付
	 * @throws Exception
	 */
	public String japaneseCalendar(String inputDate) {

		String dateOutput = "";
		if (Utils.isNotNullAndEmpty(inputDate) && inputDate.length() > 7) {
			dateOutput = inputDate.substring(0, 4) + "-"
					+ inputDate.substring(4, 6) + "-"
					+ inputDate.substring(6, 8);
		}else{
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.JAPAN);
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(dateOutput, pos);
		Locale locale = new Locale("ja", "JP", "JP");

		// 和暦で表示
		DateFormat japaseseFormat = new SimpleDateFormat("GGGGy年M月d日", locale);
		String dateStr = japaseseFormat.format(date);
		dateStr = dateStr.substring(0, 1)+dateStr.substring(2);
		dateStr = dateStr.substring(0,(dateStr.indexOf("年") + 1 ));
		
		return dateStr;
	}
}
