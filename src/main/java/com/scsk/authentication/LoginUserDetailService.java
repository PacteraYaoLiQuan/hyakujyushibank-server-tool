package com.scsk.authentication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudant.client.api.Database;
import com.scsk.constants.ApplicationKeys;
import com.scsk.constants.Constants;
import com.scsk.model.UserDoc;
import com.scsk.repository.RepositoryUtil;
import com.scsk.service.CloudantDBService;
import com.scsk.util.ActionLog;
import com.scsk.util.EncryptorUtil;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.Utils;

/**
 * ログインチェックサービス。<br>
 * <br>
 * 該当システムのログインビジネスロジック。<br>
 */
public class LoginUserDetailService implements UserDetailsService {
    @Autowired
    private CloudantDBService cloudantDBService;
    @Autowired
    public RepositoryUtil repositoryUtil;
	@Autowired
	private EncryptorUtil encryptorUtil;
	@Autowired
	private ActionLog actionLog;

    /**
     * 入力するemailでパスワードを検索して一致性をチェックする。
     * 
     * @param userBean
     *            ユーザ情報
     */
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpSession session = request.getSession();
       
        
        UserDetails userDetail = null;
//        if ("Admin".equalsIgnoreCase(userID)) {
//         // ユーザIDとパスワードをチェックする
//            try {
//                session.setAttribute(Constants.LONGINID, "Login ID: " + userID);
//                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                userDetail = (UserDetails) new LoginUser("ADMIN", passwordEncoder.encode("SCSK@2016"),
//                        !false, true, true, true, getAuthorities(1),
//                        1,0);
//            } catch (Exception e) {
//                LogInfoUtil.LogError(e.getMessage(), e);
//            }
//        } else {
            // データベースを取得
            // emailでパスワードを検索する
          
            try {
                cloudantDBService.cloudantOpen();
                Database db = cloudantDBService.getCloudantClient().database(Constants.DB_NAME, false);
                // 検索キーを整理する
             // ユーザー一覧初期データを取得
                List<UserDoc> userInfoDocList = new ArrayList<>();
                userInfoDocList = repositoryUtil.getView(db,
                        ApplicationKeys.INSIGHTVIEW_USERLIST_USERLIST, UserDoc.class);
                List<UserDoc> userList = new ArrayList<UserDoc>();
                for (UserDoc userDoc :userInfoDocList) {
                    if (userID.equalsIgnoreCase(encryptorUtil.decrypt(userDoc.getUserID()))) {
                        userList.add(userDoc);
                        break;
                    }
                }
              
//                String queryKey = "userID:\"" + encryptorUtil.encrypt(userID) + "\"";
//                List<UserDoc> userList = repositoryUtil.getIndex(db, ApplicationKeys.INSIGHTINDEX_LOGIN_LOGIN_GETUSERINFO,
//                        queryKey, UserDoc.class);
                if (userList != null && userList.size() > 0) {
                    session.setAttribute(Constants.LONGINNAME, encryptorUtil.decrypt(userList.get(0).getUserName()));
                    session.setAttribute(Constants.LOCKSTATUS, userList.get(0).getLoginStatus());
                    session.setAttribute(Constants.LONGINID, "Login ID: " + encryptorUtil.decrypt(userList.get(0).getUserID()));
                    // 登録したユーザの権限を判断する
                    Integer accessID = 0;
                    if (Constants.ROLE_ADMIN.equals(userList.get(0).getAuthority())) {
                        accessID = 1;
                    }
             
                    int change90DayFlg = 0;
                    if (Utils.isNotNullAndEmpty(userList.get(0).getEndPasswordChangeDateTime())) {
                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_APP_DATE2);
                        Calendar nowdate = Calendar.getInstance();
                        nowdate.add(Calendar.DAY_OF_WEEK, -90);
                        Date date;
                        date = format.parse(userList.get(0).getEndPasswordChangeDateTime().substring(0, 10).replace("/", "-"));
                        if (date.compareTo(nowdate.getTime()) < 0) {
                            change90DayFlg = 1;
                            actionLog.saveActionLog(Constants.ACTIONLOG_PASSWORD_3, db);
                        }
                    }
                   
                    // ユーザIDとパスワードをチェックする
                    userDetail = (UserDetails) new LoginUser(encryptorUtil.decrypt(userList.get(0).getUserID()), userList.get(0).getPassword(),
                            !userList.get(0).isLockStatus(), true, true, true, getAuthorities(accessID),
                            userList.get(0).getChangePasswordFlg(),change90DayFlg);
                } else {
                    session.setAttribute(Constants.LONGINID, "Login ID: " + userID);
                }
            } catch (Exception e) {
                LogInfoUtil.LogError(e.getMessage(), e);
            } finally {
                cloudantDBService.cloudantClose();
            }
//        }
      

        return userDetail;
    }

    /**
     * 登録したユーザの権限を集計する。
     * 
     * @param accessID
     *            権限ID
     */
    public Collection<GrantedAuthority> getAuthorities(Integer accessID) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        // 全てユーザに対して、デフォルトUSER権限を付与する
        authList.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
        // admin権限があるユーザがadmin権限を付与する
        if (accessID.compareTo(1) == 0) {
            authList.add(new SimpleGrantedAuthority(Constants.ROLE_ADMIN));
        }
        // 集計した権限リストを戻る
        return authList;
    }

}
