package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/12/12.
 */
@Table(name = "File")
public class FileEntity implements BaseMode{

    @Column(name = "fil_id",isId = true,autoGen = false)
    private int fil_id;
    @Column(name = "fol_id")
    private int fol_id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private int type;
    @Column(name = "url")
    private String url;
    @Column(name = "use_id")
    private int use_id;
    @Column(name = "cre_date")
    private long cre_date;

    public int getFil_id() {
        return fil_id;
    }

    public void setFil_id(int fil_id) {
        this.fil_id = fil_id;
    }

    public int getFol_id() {
        return fol_id;
    }

    public void setFol_id(int fol_id) {
        this.fol_id = fol_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public long getCre_date() {
        return cre_date;
    }

    public void setCre_date(long cre_date) {
        this.cre_date = cre_date;
    }
}
