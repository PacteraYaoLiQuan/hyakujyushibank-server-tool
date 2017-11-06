package com.scsk.repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.Search;
import com.cloudant.client.api.model.DesignDocument;
import com.cloudant.client.api.model.DesignDocument.MapReduce;
import com.cloudant.client.api.model.Params;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.scsk.constants.Constants;
import com.scsk.constants.MessageKeys;
import com.scsk.exception.BusinessException;
import com.scsk.model.UtilDoc;
import com.scsk.model.rev.RevUtilDoc;
import com.scsk.util.LogInfoUtil;
import com.scsk.util.ResultMessages;
import com.scsk.util.SessionUser;
import com.scsk.util.Utils;

/**
 * DBのCRUD処理共通クラス。<br>
 * <br>
 * CRUDをパッケージするロジック。<br>
 * 
 * @param <T>
 * @param <T>
 * @param bean
 *            Bean情報
 */
@Repository
public class RepositoryUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Index検索処理。
     * 
     * @param db
     *            DB 対象
     * @param indexName
     *            cloudDBのIndex名
     * @param queryKey
     *            検索条件のkey
     * @param sort
     *            ソート順
     * @param limit
     *            取得件数
     * @param cls
     *            DOCのタイプ
     */
    public <T> List<T> getIndex(Database db, String indexName, String queryKey, String sort[], Integer limit,
            Class<T> cls) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.INDEX);
        List<T> resultList = new ArrayList<T>();
        Search search = db.search(indexName).includeDocs(true);
        if (sort != null && sort.length > 0) {
            String sortStr = null;
            try {
                sortStr = objectMapper.writeValueAsString(sort);
            } catch (IOException e) {
                LogInfoUtil.LogError("sort formater error!", e);
            }
            search.sort(sortStr);
        }
        if (limit != null && limit > 0) {
            search.limit(limit);
        }
        resultList = search.query(queryKey, cls);
        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.INDEX);
        return resultList;
    }

    /**
     * Index検索処理。
     * 
     * @param db
     *            DB 対象
     * @param indexName
     *            cloudDBのIndex名
     * @param queryKey
     *            検索条件のkey
     * @param cls
     *            DOCのタイプ
     */
    public <T> List<T> getIndex(Database db, String indexName, String queryKey, Class<T> cls) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.INDEX);
        List<T> resultList = new ArrayList<T>();
        resultList = db.search(indexName).includeDocs(true).query(queryKey, cls);

        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.INDEX);
        return resultList;
    }

    /**
     * 単一検索処理。
     * 
     * @param db
     *            DB 対象
     * @param id
     *            docの_id
     * @param cls
     *            DOCのタイプ
     */
    public <T> T find(Database db, String id, Class<T> cls) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.FIND);
        T doc;
        try {
            doc = db.find(cls, id);
        } catch (NoDocumentException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.FIND);
        return doc;
    }

    /**
     * 単一検索処理。
     * 
     * @param db
     *            DB 対象
     * @param id
     *            docの_id
     * @param rev
     *            docのリリース
     * @param cls
     *            DOCのタイプ
     */
    public <T> T find(Database db, String id, String rev, Class<T> cls) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.FIND);
        T doc;
        try {
            doc = db.find(cls, id, rev);
        } catch (NoDocumentException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.FIND);
        return doc;
    }

    /**
     * 単一検索処理。
     * 
     * @param db
     *            DB 対象
     * @param id
     *            docの_id
     * @param params
     */
    public RevUtilDoc find(Database db, String id) {
        LogInfoUtil.LogInfoStrat(RevUtilDoc.class.getSimpleName() + Constants.FIND);
        RevUtilDoc doc;
        try {
            doc = db.find(RevUtilDoc.class, id, new Params().revisions().revsInfo());
        } catch (NoDocumentException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(RevUtilDoc.class.getSimpleName() + Constants.FIND);
        return doc;
    }

    /**
     * View検索処理。
     * 
     * @param <T>
     * @param db
     *            DB 対象
     * @param viewName
     *            cloudDBのView名
     * @param descending
     *            ソート順
     * @param cls
     *            DOCのタイプ
     */
    public <T> List<T> getView(Database db, String viewName, boolean descending, Class<T> cls) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.VIEW);
        List<T> resultList = new ArrayList<T>();
        try {
            resultList = db.getViewRequestBuilder(Constants.DB_VIEW, viewName).newRequest(Key.Type.STRING, Object.class)
                    .reduce(false).descending(descending)
                    // .startKey("start-key")
                    // .endKey("end-key")
                    // .limit(10)
                    .includeDocs(true).build().getResponse().getDocsAs(cls);
        } catch (IOException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }

        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.VIEW);
        return resultList;
    }
    
    /**
     * 
     * @param db
     *            DB 対象
     * @param viewName
     *            cloudDBのView名
     * @param cls
     *            DOCのタイプ
     * @param keys
     *            one or more keys of the type emitted by the view
     * @return
     */
    public <T> List<T> getView(Database db, String viewName, Class<T> cls, String... keys) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.VIEW);
        List<T> resultList = new ArrayList<T>();
        try {
            resultList = db.getViewRequestBuilder(Constants.DB_VIEW, viewName)
                    .newRequest(Key.Type.STRING, Object.class).reduce(false).includeDocs(true).keys(keys).build()
                    .getResponse().getDocsAs(cls);
        } catch (IOException e) {
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }

        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.VIEW);
        return resultList;
    }

    /**
     * View検索処理。
     * 
     * @param <T>
     * @param db
     *            DB 対象
     * @param viewName
     *            cloudDBのView名
     * @param descending
     *            ソート順
     * @param cls
     *            DOCのタイプ
     * @param limit
     *           取得件数
     */
    public <T> List<T> getView(Database db, String viewName, boolean descending, Class<T> cls, int limit) {
        LogInfoUtil.LogInfoStrat(cls.getSimpleName() + Constants.VIEW);
        List<T> resultList = new ArrayList<T>();
        try {
            resultList = db.getViewRequestBuilder(Constants.DB_VIEW, viewName).newRequest(Key.Type.STRING, Object.class)
                    .reduce(false).descending(descending).limit(limit).includeDocs(true).build().getResponse()
                    .getDocsAs(cls);
        } catch (IOException e) {
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        }

        LogInfoUtil.LogInfoEnd(cls.getSimpleName() + Constants.VIEW);
        return resultList;
    }

    /**
     * View検索処理。
     * 
     * @param <T>
     * @param db
     *            DB 対象
     * @param viewName
     *            cloudDBのView名
     * @param cls
     *            DOCのタイプ
     */
    public <T> List<T> getView(Database db, String viewName, Class<T> cls) {
        return getView(db, viewName, false, cls);
    }

    /**
     * 動的に生成されたビューと検索処理。
     * 
     * @param db
     *            DB 対象
     * @param view
     *            ビューコンテンツ
     * @param descending
     *            ソート順
     * @param classType
     *            DOCのタイプ
     * @return
     */
    public <T> List<T> queryByDynamicView(Database db, MapReduce view, boolean descending, Class<T> classType) {
        LogInfoUtil.LogInfoStrat(classType.getSimpleName() + Constants.DYNAMICVIEW);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String time = Utils.currentTime(Constants.DATE_FORMAT_ASS);
        String sessionId = sra.getSessionId();
        String designDoc = "_design/dynamicView_" + time + "_" + sessionId;
        String viewName = "dynamicView";
        List<T> resultList = new ArrayList<T>();
        DesignDocument ddoc = new DesignDocument();

        // Call setters on ddoc to populate document
        ddoc.setId(designDoc);
        ddoc.setLanguage("javascript");
        // Save to create the design document
        Response saveRes = db.save(ddoc);
        LogInfoUtil.LogDebug("save code => " + saveRes.getStatusCode());

        // Update
        ddoc = db.find(DesignDocument.class, designDoc);
        Map<String, MapReduce> updates = new HashMap<String, MapReduce>();
        updates.put(viewName, view);
        ddoc.setViews(updates);
        saveRes = db.update(ddoc);
        LogInfoUtil.LogDebug("update code => " + saveRes.getStatusCode());
        try {
            // query
            resultList = db.getViewRequestBuilder("dynamicView_" + time + "_" + sessionId, viewName)
                    .newRequest(Key.Type.STRING, Object.class).reduce(false).descending(descending).includeDocs(true)
                    .build().getResponse().getDocsAs(classType);
        } catch (IOException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1004);
            throw new BusinessException(messages);
        } finally {
            Response removeRes = db.getDesignDocumentManager().remove(designDoc);
            LogInfoUtil.LogDebug("remove code => " + removeRes.getStatusCode());
            if (removeRes.getStatusCode() == 0) {
                LogInfoUtil.logWarn(Constants.LOGMESSAGE001, designDoc);
            }
        }

        LogInfoUtil.LogInfoEnd(classType.getSimpleName() + Constants.DYNAMICVIEW);
        return resultList;
    }

    /**
     * 動的に生成されたビューと検索処理。
     * 
     * @param db
     *            DB 対象
     * @param view
     *            ビューコンテンツ
     * @param classType
     *            DOCのタイプ
     * @return
     */
    public <T> List<T> queryByDynamicView(Database db, MapReduce view, Class<T> classType) {
        return queryByDynamicView(db, view, false, classType);
    }

    /**
     * 登録処理。
     * 
     * @param db
     *            DB 対象
     * @param doc
     *            Doc情報
     */
    
    public void save(Database db, UtilDoc doc,String delFlg) {

        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        if (SessionUser.userId() != "") {
            // 作成者設定
            doc.setCreatedBy(SessionUser.userId());
            // 更新者設定
            doc.setUpdatedBy(SessionUser.userId());
        } 
        // 作成日時設定
        doc.setCreatedDate(date);

        // 更新日時設定
        doc.setUpdatedDate(date);
        // 削除フラグ
        doc.setDelFlg(delFlg);
        try {
            // 登録処理
            db.save(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
    
    }
    public void save(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        if (SessionUser.userId() != "") {
            // 作成者設定
            doc.setCreatedBy(SessionUser.userId());
            // 更新者設定
            doc.setUpdatedBy(SessionUser.userId());
        } 
        // 作成日時設定
        doc.setCreatedDate(date);

        // 更新日時設定
        doc.setUpdatedDate(date);
        
        // 削除フラグ
        doc.setDelFlg("0");
        try {
            // 登録処理
            db.save(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
    }
    
    public void bulk(Database db, List<UtilDoc> doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        try {
            // 登録処理
        	db.bulk(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
    }
    
    
    public void savePushrecord(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        if (SessionUser.userId() != "") {
            // 作成者設定
            doc.setCreatedBy(SessionUser.userId());
            // 更新者設定
            doc.setUpdatedBy(SessionUser.userId());
        } 
        // 削除フラグ
        doc.setDelFlg("0");
        try {
            // 登録処理
            db.save(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
    }
    
    public void saveActionLog(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 作成日時設定
        doc.setCreatedDate(date);

        // 更新日時設定
        doc.setUpdatedDate(date);
        // 削除フラグ
        doc.setDelFlg("0");
        try {
            // 登録処理
            db.save(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
    }
    /**
     * 登録処理。
     * 
     * @param db
     *            DB 対象
     * @param doc
     *            Doc情報
     * @return response
     */
    public Response saveToResultRes(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 作成者設定
        doc.setCreatedBy(SessionUser.userId());
        // 作成日時設定
        doc.setCreatedDate(date);
        // 更新者設定
        doc.setUpdatedBy(SessionUser.userId());
        // 更新日時設定
        doc.setUpdatedDate(date);
        // 削除フラグ
        doc.setDelFlg("0");
        Response response;
        try {
            // 登録処理
            response = db.save(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
        return response;
    }

    /**
     * _idを返すために保存
     * 
     * @param db
     *            DB 対象
     * @param docId
     *            DOC _id
     */
    public String saveToResultId(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.SAVE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 作成者設定
        doc.setCreatedBy(SessionUser.userId());
        // 作成日時設定
        doc.setCreatedDate(date);
        // 更新者設定
        doc.setUpdatedBy(SessionUser.userId());
        // 更新日時設定
        doc.setUpdatedDate(date);
        // 削除フラグ
        doc.setDelFlg("0");

        String id;
        try {
            // 登録処理
            Response response = db.save(doc);
            id = response.getId();

        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1005);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.SAVE);
        return id;
    }
    
    /**
     * 更新処理。
     * 
     * @param db
     *            DB 対象
     * @param doc
     *            Doc情報
     */
    public void update(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.UPDATE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 更新者設定
        doc.setUpdatedBy(SessionUser.userId());
        // 更新日時設定
        doc.setUpdatedDate(date);
        try {
            // 更新処理
            db.update(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1006);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.UPDATE);
    }

    /**
     * 更新処理。
     * 
     * @param db
     *            DB 対象
     * @param doc
     *            Doc情報
     * @return response
     */
    public Response updateToResultRes(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.UPDATE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 更新者設定
        doc.setUpdatedBy(SessionUser.userId());
        // 更新日時設定
        doc.setUpdatedDate(date);

        Response response = null;
        try {
            // 更新処理
            response = db.update(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1006);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.UPDATE);
        return response;
    }

    /**
     * 削除処理。(論理)
     * 
     * @param db
     *            DB 対象
     * @param doc
     *            Doc情報
     */
    public void delete(Database db, UtilDoc doc) {
        LogInfoUtil.LogInfoStrat(doc.getClass().getSimpleName() + Constants.DELETE);
        // システム日時を取得
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
        String date = sdf.format(new Date());
        // 更新者設定
        doc.setUpdatedBy(SessionUser.userId());
        // 更新日時設定
        doc.setUpdatedDate(date);
        // 削除フラグ
        doc.setDelFlg("1");
        try {
            // 更新(削除)処理
            db.update(doc);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1007);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(doc.getClass().getSimpleName() + Constants.DELETE);
    }

    /**
     * 削除処理。(物理)
     * 
     * @param db
     *            DB 対象
     * @param docId
     *            DOC _id
     */
    public void removeByDocId(Database db, String docId) {
        LogInfoUtil.LogInfoStrat(docId.getClass().getSimpleName() + Constants.REMOVE);
        Object obj = new Object();
        try {
            obj = db.find(Object.class, docId);
        } catch (NoDocumentException e) {
            return;
        }
        try {
            db.remove(obj);
        } catch (DocumentConflictException e) {
            LogInfoUtil.LogDebug(e.getMessage(), e);
            ResultMessages messages = ResultMessages.error().add(MessageKeys.E_ACCOUNT001_1007);
            throw new BusinessException(messages);
        }
        LogInfoUtil.LogInfoEnd(docId.getClass().getSimpleName() + Constants.REMOVE);
    }

}