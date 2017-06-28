package cn.devshare.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.entity
 * Class describe:扩展的用户类
 * Author: cheng
 * Create time: 2017/6/28 11:00
 */
public class User extends BmobUser {
    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
