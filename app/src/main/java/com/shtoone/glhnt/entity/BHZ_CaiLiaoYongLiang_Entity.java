package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class BHZ_CaiLiaoYongLiang_Entity implements Serializable {
    private static final long serialVersionUID = -7641478522699540451L;

    private List<COM_XY> itemsChengben;
    private List<COM_XY> itemsBaifenbi;
    private List<BHZ_CaiLiaoYongLiang_Item> itemsData;

    public List<COM_XY> getItemsChengben() {
        return itemsChengben;
    }

    public void setItemsChengben(List<COM_XY> itemsChengben) {
        this.itemsChengben = itemsChengben;
    }

    public List<COM_XY> getItemsBaifenbi() {
        return itemsBaifenbi;
    }

    public void setItemsBaifenbi(List<COM_XY> itemsBaifenbi) {
        this.itemsBaifenbi = itemsBaifenbi;
    }

    public List<BHZ_CaiLiaoYongLiang_Item> getItemsData() {
        return itemsData;
    }

    public void setItemsData(List<BHZ_CaiLiaoYongLiang_Item> itemsData) {
        this.itemsData = itemsData;
    }
}
