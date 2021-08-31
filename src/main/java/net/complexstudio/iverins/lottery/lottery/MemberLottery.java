package net.complexstudio.iverins.lottery.lottery;

import net.complexstudio.iverins.lottery.Main;
import net.complexstudio.iverins.lottery.ui.VFlowLayout;
import net.complexstudio.iverins.lottery.utils.LotteryTimer;
import net.complexstudio.iverins.lottery.utils.ReaderFile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class MemberLottery {

    private List<String> members;
    private String[] membersArray;

    private final JButton firstName = new JButton("我"); // 姓
    private final JButton secondName = new JButton("是"); // 名字 1
    private final JButton lastName = new JButton("谁"); // 名字 2

    public MemberLottery(ReaderFile reader){
        try {
            this.members = (List<String>) reader.getFileContext();
            this.membersArray = this.members.toArray(new String[0]);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 设置布局
     * @param needAddPanel 需要添加上的布局
     */
    public void setPanel(JPanel needAddPanel , JFrame frame){
        // 设置布局
        JPanel defaultPanel = new JPanel(); // 底层Panel
        defaultPanel.setBackground(Color.WHITE);
        defaultPanel.setLayout(new VFlowLayout());

        JPanel namePanel = new JPanel(); // 名字布局
        JLabel defaultName = new JLabel("还没有名字呢"); // 默认名字
        defaultName.setFont(new Font("微软雅黑" , Font.PLAIN , 32));
        namePanel.add(defaultName);
        namePanel.setBackground(Color.WHITE); // 白色背景

        namePanel.add(defaultName);

        JPanel bottomPanel = new JPanel(); // 底部布局
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new VFlowLayout()); // 布局设置
        JLabel text = new JLabel("每次抽奖将会在 3 秒后出现结果.");
        JButton startLottery = new JButton("开始抽签");
        startLottery.setContentAreaFilled(false);
        startLottery.setSize(80 , 30);
        bottomPanel.add(text);
        bottomPanel.add(startLottery);

        defaultPanel.add(namePanel);
        defaultPanel.add(bottomPanel);

        JPanel lotteryPanel = new JPanel(); // 抽奖界面
        lotteryPanel.setLayout(new FlowLayout(FlowLayout.LEADING , 30 , 30)); // 间隙
        Dimension nameSize = new Dimension(100 , 100);
        Font bigFont = new Font("宋体" , Font.BOLD , 36);
        firstName.setContentAreaFilled(false);
        firstName.setEnabled(false);
        firstName.setPreferredSize(nameSize);
        firstName.setFont(bigFont);
        secondName.setContentAreaFilled(false);
        secondName.setEnabled(false);
        secondName.setPreferredSize(nameSize);
        secondName.setFont(bigFont);
        lastName.setContentAreaFilled(false);
        lastName.setEnabled(false);
        lastName.setPreferredSize(nameSize);
        lastName.setFont(bigFont);
        lotteryPanel.add(firstName);
        lotteryPanel.add(secondName);
        lotteryPanel.add(lastName);
        lotteryPanel.setBackground(Color.WHITE);

        defaultPanel.add(lotteryPanel);

        // 监听
        try {
            startLottery.addActionListener(
                    new LotteryTimer(30 , lottery() , membersArray , frame , defaultName , firstName , secondName , lastName , members , startLottery));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        needAddPanel.add(defaultPanel);
    }

    /**
     * 抽奖本体
     * @return 返回成员结果
     */
    public String lottery() throws UnsupportedEncodingException {
        // 随机数
        Random randomNum = new Random();
        // 转数组
        String[] membersArray = this.members.toArray(new String[0]);
        // 结果
        return Main.unicodeToString(Main.stringToUnicode(membersArray[randomNum.nextInt(this.members.size())]));
    }
}
