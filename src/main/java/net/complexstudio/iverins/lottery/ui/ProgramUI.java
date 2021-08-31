package net.complexstudio.iverins.lottery.ui;

import net.complexstudio.iverins.lottery.Main;
import net.complexstudio.iverins.lottery.lottery.MemberLottery;
import net.complexstudio.iverins.lottery.utils.ReaderFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProgramUI extends JFrame {

    private final JPanel helpPanel = new JPanel();
    private final JPanel settingPanel = new JPanel();
    private final JPanel groupPanel = new JPanel();
    private final JPanel memberPanel = new JPanel();

    private static final Font contextFont = new Font("微软雅黑" , Font.PLAIN , 24);

    public ProgramUI(){
        // 设置标题
        this.setTitle("LotteryPlus");
        // 固定窗口大小
        this.setResizable(false);
        // 设置默认窗格颜色
        this.getContentPane().setBackground(Color.WHITE);
        // 关闭
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 存在配置文件
        if (Main.getReader().existConfig()) {
            this.setSize(Main.getReader().getUiConfig().getWeight(), Main.getReader().getUiConfig().getHeight());
        } else {
            // 设置默认长宽
            this.setSize(800, 500);
        }
        this.loadPanel();
        this.setLayout(new BorderLayout()); // 左右布局
    }

    /**
     * 加载布局
     */
    public void loadPanel(){
        // 帮助界面的开始
        helpPanel.setBackground(Color.WHITE);
        helpPanel.setBorder(BorderFactory.createTitledBorder("帮助中心"));
        helpPanel.setLayout(new VFlowLayout());

        JPanel howToAddMemberFile = new JPanel(); // 如何创建一个添加成员的文件
        JLabel htaTitle = new JLabel("如何加载成员文件");
        JTextArea htaArea = new JTextArea("1. 创建一个 .txt 文件 \n" +
                "2. 往刚刚新创建的文件里面添加名字（注意，一行一个名字） \n" +
                "3. 保存，然后在选择成员文件中选择刚刚创建的文件 \n" +
                "切记：文件名尽量为英文名" , 4 , 30);
        JScrollPane htaScroll = new JScrollPane(htaArea);
        htaArea.setEditable(false);
        htaArea.setBackground(Color.WHITE);
        howToAddMemberFile.setLayout(new VFlowLayout());
        howToAddMemberFile.add(htaTitle);
        howToAddMemberFile.add(htaScroll);
        howToAddMemberFile.setBackground(Color.WHITE);

        JPanel howToAddGroupFile = new JPanel();
        JLabel htaTitleGroup = new JLabel("如何加载小组文件");
        JTextArea htaAreaGroup = new JTextArea("1. 创建一个 .txt 文件 \n" +
                "2. 往刚刚新创建的文件里面添加组名（注意，一行一个组名） \n" +
                "3. 保存，然后选择该文件 \n" +
                "切记：文件名尽量为英文名" , 4 , 30);
        JScrollPane htaScrollGroup = new JScrollPane(htaAreaGroup);
        htaAreaGroup.setEditable(false);
        htaAreaGroup.setBackground(Color.WHITE);
        howToAddGroupFile.setLayout(new VFlowLayout());
        howToAddGroupFile.add(htaTitleGroup);
        howToAddGroupFile.add(htaScrollGroup);
        howToAddGroupFile.setBackground(Color.WHITE);

        JPanel tipPanel = new JPanel();
        JLabel tipTitle = new JLabel("注意");
        tipTitle.setFont(contextFont);
        JLabel tipContext = new JLabel("每次选择完文件后需要重启程序(下个版本或许会更新实时更新吧?)");
        JLabel helpGBK = new JLabel("若出现乱码的情况，请转码 GBK （网上有教程）");
        helpGBK.setForeground(Color.RED);
        tipPanel.setBackground(Color.WHITE);
        tipPanel.setLayout(new VFlowLayout());
        tipPanel.add(tipTitle);
        tipPanel.add(tipContext);
        tipPanel.add(helpGBK);

        helpPanel.add(howToAddMemberFile);
        helpPanel.add(howToAddGroupFile);
        helpPanel.add(tipPanel);
        // 帮助界面的结束

        // 设置配置开始
        settingPanel.setBackground(Color.WHITE);
        settingPanel.setLayout(new VFlowLayout());
        settingPanel.setBorder(BorderFactory.createTitledBorder("设置配置"));
        JPanel weightLabel = new JPanel();
        JPanel heightLabel = new JPanel();
        JPanel groupLabel = new JPanel();
        JPanel memberLabel = new JPanel();

        JLabel weightContext = new JLabel("窗口宽度");
        JTextField weightText = new JTextField(100);
        weightText.setEditable(false);
        weightText.setText(String.valueOf(Main.getReader().getUiConfig().getWeight()));
        weightContext.setFont(contextFont);
        weightLabel.add(weightContext);
        weightLabel.add(weightText);

        JLabel heightContext = new JLabel("窗口高度");
        JTextField heightText = new JTextField(100);
        heightText.setEditable(false);
        heightText.setText(String.valueOf(Main.getReader().getUiConfig().getHeight()));
        heightContext.setFont(contextFont);
        heightLabel.add(heightContext);
        heightLabel.add(heightText);

        JPanel selectFilePanel = new JPanel();
        JPanel selectMemberFilePanel = new JPanel();
        JButton selectFile = new JButton("选择文件");
        selectFile.setEnabled(false); // 下一版本更新小组抽签
        JButton selectMemberFile = new JButton("选择文件");
        selectFilePanel.setBackground(Color.WHITE);
        selectMemberFilePanel.setBackground(Color.WHITE);
        selectFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT , 10 , 10));
        selectMemberFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT , 10 , 10));
        selectFile.setFont(new Font("微软雅黑" , Font.PLAIN , 8));
        selectMemberFile.setFont(new Font("微软雅黑" , Font.PLAIN , 8));
        selectFile.setContentAreaFilled(false);
        selectMemberFile.setContentAreaFilled(false);

        JLabel groupContext = new JLabel("小组文件");
        JTextField groupText = new JTextField(30);
        if ("None".equalsIgnoreCase(Main.getReader().getUiConfig().getGroup())){
            // 没有文件
            selectFilePanel.add(groupText);
            groupText.setEditable(false);
            groupText.setText("敬请期待，下一版本更新...");
            selectFilePanel.add(selectFile);
        } else {
            selectFilePanel.add(groupText);
            groupText.setEditable(false);
            groupText.setText(Main.getReader().getUiConfig().getGroup());
            selectFilePanel.add(selectFile);
        }
        groupContext.setFont(contextFont);
        groupLabel.add(groupContext);
        groupLabel.add(selectFilePanel);

        JLabel memberContext = new JLabel("成员文件");
        JTextField memberText = new JTextField(30);
        if ("NONE".equalsIgnoreCase(Main.getReader().getUiConfig().getMember())){
            selectMemberFilePanel.add(memberText);
            memberText.setEditable(false);
            memberText.setText("暂无文件，请点击选择文件来选择文件");
            selectMemberFilePanel.add(selectMemberFile);
        } else {
            selectMemberFilePanel.add(memberText);
            memberText.setEditable(false);
            memberText.setText(Main.getReader().getUiConfig().getMember());
            selectMemberFilePanel.add(selectMemberFile);
        }
        memberContext.setFont(contextFont);
        memberLabel.add(memberContext);
        memberLabel.add(selectMemberFilePanel);

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("请选择TXT文件" , "txt");

        selectFile.addActionListener(e -> {
            JFileChooser chooseGroup = new JFileChooser();
            chooseGroup.setBackground(Color.WHITE);
            chooseGroup.setAcceptAllFileFilterUsed(false);
            chooseGroup.setFileFilter(txtFilter);
            int result = chooseGroup.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                String groupPath = chooseGroup.getSelectedFile().getAbsolutePath(); // 文件路径
                Main.getReader().getUiConfig().setGroup(groupPath);
                try {
                    Main.getReader().save(); // 保存配置文件
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                groupText.setText(groupPath);
                setVisible(true);
            }
        });

        selectMemberFile.addActionListener(e -> {
            JFileChooser chooserMember = new JFileChooser();
            chooserMember.setBackground(Color.WHITE);
            chooserMember.setAcceptAllFileFilterUsed(false);
            chooserMember.setFileFilter(txtFilter);
            int result = chooserMember.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                String groupPath = chooserMember.getSelectedFile().getAbsolutePath();
                Main.getReader().getUiConfig().setMember(groupPath);
                try {
                    Main.getReader().save();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                memberText.setText(groupPath);
                setVisible(true);
            }
        });

        Collection<JPanel> settingPanels = new ArrayList<>();
        settingPanels.add(weightLabel);
        settingPanels.add(heightLabel);
        settingPanels.add(groupLabel);
        settingPanels.add(memberLabel);
        for (JPanel panel : settingPanels){
            panel.setLayout(new VFlowLayout());
            panel.setVisible(true);
            panel.setBackground(Color.WHITE);
            settingPanel.add(panel);
        }
        // 设置界面结束

        // 小组界面开始
        groupPanel.setBackground(Color.WHITE);
        groupPanel.setBorder(BorderFactory.createTitledBorder("小组抽签"));
        // 小组界面结束

        // 成员界面开始
        memberPanel.setBackground(Color.WHITE);
        memberPanel.setBorder(BorderFactory.createTitledBorder("普通抽签"));
        if (!Main.getReader().getUiConfig().getMember().equalsIgnoreCase("NONE")) {
            MemberLottery memberLottery = new MemberLottery(new ReaderFile(new File(Main.getReader().getUiConfig().getMember())));
            memberLottery.setPanel(memberPanel , this);
        }
        // 成员界面结束
    }

    /**
     * 设置右边的布局
     */
    public void setRightPanel(){
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        JButton setting = new JButton("设置配置");
        JButton group = new JButton("小组抽签");
        JButton member = new JButton("普通抽签");
        JButton help = new JButton("帮助中心");
        List<JButton> control = new ArrayList<>();
        control.add(setting);
        control.add(group);
        control.add(member);
        control.add(help);
        rightPanel.setLayout(new VFlowLayout());
        if (Main.getReader().getUiConfig().getMember().equalsIgnoreCase("none")){
            member.setEnabled(false);
        }
        for (JButton needChange : control){
            needChange.setPreferredSize(new Dimension(200 , 80));
            needChange.setContentAreaFilled(false);
            needChange.setFont(contextFont);
            rightPanel.add(needChange);
        }
        group.setEnabled(false);
        setting.addActionListener(e -> setLeftPanel("SETTING"));
        group.addActionListener(e -> setLeftPanel("GROUP"));
        member.addActionListener(e -> setLeftPanel("MEMBER"));
        help.addActionListener(e -> setLeftPanel("HELP"));
        rightPanel.setBorder(BorderFactory.createTitledBorder("控制中心"));
        rightPanel.setVisible(true);
        this.add(rightPanel , BorderLayout.EAST);
    }

    /**
     * 设置左边的布局
     * @param value 传入的参数
     */
    public void setLeftPanel(String value){
        switch (value) {
            case "HELP":
                this.remove(settingPanel);
                this.remove(groupPanel);
                this.remove(memberPanel);

                this.add(helpPanel , BorderLayout.CENTER);

                this.revalidate();
                this.repaint();
                break;
            case "SETTING":
                this.remove(helpPanel);
                this.remove(groupPanel);
                this.remove(memberPanel);

                this.add(settingPanel , BorderLayout.CENTER);

                this.revalidate();
                this.repaint();
                break;
            case "GROUP":
                this.remove(settingPanel);
                this.remove(helpPanel);
                this.remove(memberPanel);

                this.add(groupPanel , BorderLayout.CENTER);

                this.revalidate();
                this.repaint();
                break;
            case "MEMBER":
                this.remove(settingPanel);
                this.remove(groupPanel);
                this.remove(helpPanel);

                this.add(memberPanel , BorderLayout.CENTER);

                this.revalidate();
                this.repaint();
                break;
            default:
                this.setVisible(true);
                break;
        }
        this.setVisible(true);
    }

    /**
     * 返回窗口
     */
    public void init(){
        this.setRightPanel();
        this.setVisible(true);
    }
}
