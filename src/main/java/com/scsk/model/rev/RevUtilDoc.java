package com.scsk.model.rev;

/**
 * 
 * @author ws
 *
 */
public class RevUtilDoc {
    //
    private Revisions _revisions;
    //
    private RevsInfo _revs_info[];

    public Revisions get_revisions() {
        return _revisions;
    }

    public void set_revisions(Revisions _revisions) {
        this._revisions = _revisions;
    }

    public RevsInfo[] get_revs_info() {
        return _revs_info;
    }

    public void set_revs_info(RevsInfo[] _revs_info) {
        this._revs_info = _revs_info;
    }
}
