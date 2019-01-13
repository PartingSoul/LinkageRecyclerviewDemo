package com.parting_soul.linkagerecyclerviewdemo.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author parting_soul
 * @date 2019/1/11
 */
public class RightBean implements MultiItemEntity {
    private String name;
    private String id;
    private int imgRes;
    private int type;
    private String groupName;


    public static final int TYPE_ITEM = 0;
    public static final int TYPE_TITLE = 1;

    public RightBean(String name, String id, int imgRes, String groupName) {
        this.name = name;
        this.id = id;
        this.imgRes = imgRes;
        this.type = TYPE_ITEM;
        this.groupName = groupName;
    }

    public RightBean(String name) {
        this.name = name;
        this.groupName = name;
        this.type = TYPE_TITLE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
