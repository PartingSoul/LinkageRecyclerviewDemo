package com.parting_soul.linkagerecyclerviewdemo.bean;

import java.util.List;

/**
 * @author parting_soul
 * @date 2019/1/10
 */
public class DataBean {

    /**
     * name : 热门分类
     * list : [{"imageid":"257352113","name":"早餐","icon":"262591853","id":"7136465","isrec":"1"},{"imageid":"260673546","name":"宝宝辅食","icon":"262303906","id":"257353790","isrec":"1"},{"imageid":"260673552","name":"烘焙","icon":"262179312","id":"257354226","isrec":"1"},{"imageid":"260673617","name":"食疗养生","icon":"262712824","id":"257354383","isrec":"1"}]
     */

    private String name;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * imageid : 257352113
         * name : 早餐
         * icon : 262591853
         * id : 7136465
         * isrec : 1
         */

        private String imageid;
        private String name;
        private String icon;
        private String id;
        private String isrec;

        public String getImageid() {
            return imageid;
        }

        public void setImageid(String imageid) {
            this.imageid = imageid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsrec() {
            return isrec;
        }

        public void setIsrec(String isrec) {
            this.isrec = isrec;
        }
    }
}
