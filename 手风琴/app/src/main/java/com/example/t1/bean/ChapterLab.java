package com.example.t1.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ChapterLab {
    @NonNull
    public static List<Chapter> generateMockDatas()
    {
        List<Chapter> datas = new ArrayList<>();

        Chapter root1 = new Chapter(1,"第一章");
        root1.addChild(1,"第一节");
        root1.addChild(2,"第二节");
        root1.addChild(3,"第三节");

        Chapter root2 = new Chapter(2,"第二章");
        root2.addChild(1,"第一节");
        root2.addChild(2,"第二节");
        root2.addChild(3,"第三节");

        Chapter root3 = new Chapter(3,"第三章");
        root3.addChild(1,"第一节");
        root3.addChild(2,"第二节");
        root3.addChild(3,"第三节");

        Chapter root4 = new Chapter(4,"第四章");
        root4.addChild(1,"第一节");
        root4.addChild(2,"第二节");
        root4.addChild(3,"第三节");

        Chapter root5 = new Chapter(5,"第五章");
        root5.addChild(1,"第一节");
        root5.addChild(2,"第二节");
        root5.addChild(3,"第三节");

        datas.add(root1);
        datas.add(root2);
        datas.add(root3);
        datas.add(root4);
        datas.add(root5);

        return  datas;
    }
}
