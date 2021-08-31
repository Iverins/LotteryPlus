package net.complexstudio.iverins.lottery.utils;

import net.complexstudio.iverins.lottery.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Random;

public class LotteryTimer implements ActionListener {

    private final int time;
    private int count;
    private final Timer timer;
    private String name;
    private final String[] membersArray;
    private final JFrame frame;
    private final JLabel label;
    private final JButton firstName;
    private final JButton secondName;
    private final JButton lastName;
    private final Collection<String> members;
    private final JButton thisButton;

    /**
     * 构造方法
     * @param time 时间 （10ms）
     * @param name 名字
     * @param membersArray 成员数组
     * @param frame GUI
     * @param label 名字显示
     * @param firstName 姓
     * @param secondName 第二个字
     * @param lastName 第三个字
     * @param members 成员集合
     * @param thisButton 执行的按钮
     */
    public LotteryTimer(int time , String name , String[] membersArray , JFrame frame , JLabel label , JButton firstName , JButton secondName , JButton lastName , Collection<String> members , JButton thisButton){
        this.time = time;
        this.name = name;
        this.membersArray = membersArray;
        this.frame = frame;
        this.label = label;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.members = members;
        this.timer = new Timer(100 , this);
        this.count = 0;
        this.thisButton = thisButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (count == 0) {
            Main.player.play();
            this.label.setText("正在抽签中");
            this.thisButton.setEnabled(false);
            this.firstName.setEnabled(false);
            this.secondName.setEnabled(false);
            this.lastName.setEnabled(false);
            timer.start(); // 开始任务
        }
        String[] nameSpilt = name.split(""); // 拆分名字
        count++; // 时间流逝
        if (Main.getWordCount(name) == 4) {
            this.secondName.setText(""); // 第二名字设置为空
            // 两个字的名
            String randomName = this.membersArray[new Random().nextInt(this.members.size())]; // 获取随机名字
            this.firstName.setText(randomName.split("")[0]); // 第一个字符
            if (Main.getWordCount(randomName) == 4) {
                // 随机名字是两个字
                this.secondName.setText(" ");
                this.lastName.setText(randomName.split("")[1]);
            }
            if (Main.getWordCount(randomName) == 6) {
                this.secondName.setText(randomName.split("")[1]);
                this.lastName.setText(randomName.split("")[2]);
            }
            if (this.time == count) {
                this.firstName.setText(nameSpilt[0]);
                this.secondName.setText(" ");
                this.lastName.setText(nameSpilt[1]);
                this.firstName.setEnabled(true);
                this.secondName.setEnabled(true);
                this.lastName.setEnabled(true);
            }
            this.frame.setVisible(true);
        }
        if (Main.getWordCount(name) == 6) {
            // 三个字的名
            String randomName = this.membersArray[new Random().nextInt(this.members.size())];
            this.firstName.setText(randomName.split("")[0]); // 第一个字符
            if (Main.getWordCount(randomName) == 4) {
                // 随机名字是两个字
                this.secondName.setText(" ");
                this.lastName.setText(randomName.split("")[1]);
            }
            if (Main.getWordCount(randomName) == 6) {
                this.secondName.setText(randomName.split("")[1]);
                this.lastName.setText(randomName.split("")[2]);
            }
            if (this.time == count) {
                this.firstName.setText(nameSpilt[0]);
                this.secondName.setText(nameSpilt[1]);
                this.lastName.setText(nameSpilt[2]);
                this.firstName.setEnabled(true);
                this.secondName.setEnabled(true);
                this.lastName.setEnabled(true);
            }
            this.frame.setVisible(true);
        }
        if (this.time == count) {
            label.setText(name);
            count = 0; // 重置
            this.name = this.membersArray[new Random().nextInt(this.members.size())]; // 换名字
            this.thisButton.setEnabled(true);
            Main.player.stop();
            timer.stop(); // 停止任务
        }
    }
}
