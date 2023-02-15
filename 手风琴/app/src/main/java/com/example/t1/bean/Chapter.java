package com.example.t1.bean;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

    private  int id;
    private  String name;

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";


    public Chapter(){

    }

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private List<ChapterItem> children = new ArrayList<>();

    public void addChild(ChapterItem chapterItem){
        chapterItem.setId(getId());
        children.add(chapterItem);
    }

    public void addChild(int cid,String cname){
        ChapterItem chapterItem = new ChapterItem(cid,cname);
        chapterItem.setId(getId());
        children.add(chapterItem);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterItem> children) {
        this.children = children;
    }



}
