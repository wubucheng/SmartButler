package cn.devshare.smartbutler.entity;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/3 10:06
 */
public class ChatData {
    //type
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private int type;
    //文本
    private String text;
}
