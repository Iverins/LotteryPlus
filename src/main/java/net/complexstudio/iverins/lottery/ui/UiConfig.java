package net.complexstudio.iverins.lottery.ui;

import com.alibaba.fastjson.annotation.JSONField;

public class UiConfig {

    @JSONField(name = "WEIGHT" , ordinal = 1)
    private int weight;

    @JSONField(name = "HEIGHT" , ordinal = 2)
    private int height;

    @JSONField(name = "GROUP" , ordinal = 3)
    private String group;

    @JSONField(name = "MEMBER" , ordinal = 4)
    private String member;

    public UiConfig(int height , int weight , String group , String member){
        this.height = height;
        this.weight = weight;
        this.group = group;
        this.member = member;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getGroup() {
        return group;
    }

    public String getMember() {
        return member;
    }
}
