import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.nio.charset.Charset;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class Main {

    public static void main(String[] args) throws Exception {
        //系统的默认编码是GBK
        System.out.println("Default Charset=" + Charset.defaultCharset());
        String t = "hfjkds中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国hfsdkj<img src='sasa' /> fjldsajflkdsjaflkdsjalf <img src='sada' ait=''/>sfdsfadas";
        //思路：先转为Unicode，然后转为GBK
        String utf8 = new String(t.getBytes( "UTF-8"));
        //等同于:
//        String utf8 = new String(t.getBytes( "UTF-8"),Charset.defaultCharset());

        System.out.println(utf8);
        String unicode = new String(utf8.getBytes(),"UTF-8");
        //等同于:
//        String unicode = new String(utf8.getBytes(Charset.defaultCharset()),"UTF-8");
        System.out.println(unicode);
        String gbk = new String(unicode.getBytes("GBK"));
        //等同于:
//        String gbk = new String(unicode.getBytes("GBK"),Charset.defaultCharset());

        System.out.println(gbk);
    }
}