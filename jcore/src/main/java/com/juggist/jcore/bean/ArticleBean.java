package com.juggist.jcore.bean;

import com.juggist.jcore.base.BaseBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 9:54 AM
 */
public class ArticleBean extends BaseBean {

    /**
     * id : 33
     * auto_id : 33
     * article_id : null
     * artCatogry_id : 24
     * user_id : 1
     * article_name : JM专场介绍
     * article_content : 艰苦奋斗发简历，健康减肥的。，家乐福扩散到解放放假快乐大家分 金坷垃， 框架费的设计费 昆仑决法拉盛发；多少来副驾驶垃圾分类精神分裂聚少离多房间里的设计费
     * pic_url : ["http://47.106.64.20/store/2018/09/22/6727301294e6ae23cd0ebdbdea4e4.jpg","http://47.106.64.20/store/2018/09/22/743955228a925d91ae71f150d4f30.jpg","http://47.106.64.20/store/2018/09/22/835558170ebf29caeb4a8a6c98504.jpg","http://47.106.64.20/store/2018/09/22/665835284fdec246ee8d22de1ce1.jpg"]
     * lookCount : 3
     * article_time : 1537597403
     * user_name : 官方推荐
     * user_headimg :
     */

    private String id;
    private String       auto_id;
    private Object       article_id;
    private String       artCatogry_id;
    private String       user_id;
    private String       article_name;
    private String       article_content;
    private String       lookCount;
    private String       article_time;
    private String       user_name;
    private String       user_headimg;
    private ArrayList<String> pic_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(String auto_id) {
        this.auto_id = auto_id;
    }

    public Object getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Object article_id) {
        this.article_id = article_id;
    }

    public String getArtCatogry_id() {
        return artCatogry_id;
    }

    public void setArtCatogry_id(String artCatogry_id) {
        this.artCatogry_id = artCatogry_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public String getLookCount() {
        return lookCount;
    }

    public void setLookCount(String lookCount) {
        this.lookCount = lookCount;
    }

    public String getArticle_time() {
        return article_time;
    }

    public void setArticle_time(String article_time) {
        this.article_time = article_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public void setUser_headimg(String user_headimg) {
        this.user_headimg = user_headimg;
    }

    public ArrayList<String> getPic_url() {
        return pic_url;
    }

    public void setPic_url(ArrayList<String> pic_url) {
        this.pic_url = pic_url;
    }
}

