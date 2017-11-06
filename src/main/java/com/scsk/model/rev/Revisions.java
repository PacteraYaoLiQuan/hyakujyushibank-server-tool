package com.scsk.model.rev;

/**
 * 
 * @author ws
 *
 */
public class Revisions {
    // 最新版本
    private int start;
    // 版本号数组
    private String ids[];

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
