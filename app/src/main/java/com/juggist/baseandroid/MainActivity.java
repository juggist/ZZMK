package com.juggist.baseandroid;

import android.content.Intent;
import android.os.Build;

import com.juggist.baseandroid.ui.HomeActivity;
import com.juggist.jcore.base.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import androidx.annotation.RequiresApi;


public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        startActivity(new Intent(this, HomeActivity.class));
    }


    private static class ChooseSubject{
        private String name;
        private String student_id;

        public ChooseSubject(String name, String student_id) {
            this.name = name;
            this.student_id = student_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChooseSubject that = (ChooseSubject) o;
            return Objects.equals(student_id, that.student_id);
        }


        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        @Override
        public String toString() {
            return "ChooseSubject{" +
                    "name='" + name + '\'' +
                    ", student_id='" + student_id + '\'' +
                    '}';
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args){
        ChooseSubject cs_01 = new ChooseSubject("语文","1");
        ChooseSubject cs_02 = new ChooseSubject("数学","1");
        ChooseSubject cs_03 = new ChooseSubject("英语","3");
        ChooseSubject cs_04 = new ChooseSubject("地理","4");
        ArrayList<ChooseSubject> items = new ArrayList<>();
        items.add(cs_03);
        items.add(cs_02);

        items.add(cs_04);
        items.add(cs_01);
        System.out.print("items 01 = " + items.toString());
        Iterator<ChooseSubject> iterator = items.iterator();
        while(iterator.hasNext()){
            ChooseSubject item = iterator.next();
            if(item.equals( cs_01)){
                iterator.remove();
            }
        }
        System.out.print("items 02 = " + items.toString());

    }

}
