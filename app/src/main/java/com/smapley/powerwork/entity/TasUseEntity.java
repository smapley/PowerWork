package com.smapley.powerwork.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/12/7.
 */
@Table(name = "TasUse")
public class TasUseEntity {

    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "use_id")
    private int use_id;
    @Column(name = "tas_id")
    private int tas_id;
    @Column(name = "rank")
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public int getTas_id() {
        return tas_id;
    }

    public void setTas_id(int tas_id) {
        this.tas_id = tas_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
