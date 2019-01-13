package com.parting_soul.linkagerecyclerviewdemo.bean;

/**
 * @author parting_soul
 * @date 2019/1/11
 */
public class LeftSortBean {
    private String typeName;
    private boolean isSelected;

    public LeftSortBean(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
