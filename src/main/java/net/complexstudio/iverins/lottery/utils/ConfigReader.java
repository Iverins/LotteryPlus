package net.complexstudio.iverins.lottery.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.complexstudio.iverins.lottery.Main;
import net.complexstudio.iverins.lottery.ui.UiConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigReader {

    private final String fileName;
    private final File file;

    private UiConfig uiConfig;

    public ConfigReader(String fileName){
        this.fileName = fileName;
        File pluginFile = new File(Main.getDataFolder() , "lotteryConfig");
        if (!pluginFile.exists()){
            // 不存在
            pluginFile.mkdirs(); // 创建文件夹
        }
        this.file = new File(pluginFile , this.fileName);
    }

    /**
     * 加载默认配置
     * @throws IOException 创建配置时出错
     */
    public void loadDefaultConfig() throws IOException {
        // 加载默认配置
        if (!this.file.exists()){
            // 不存在?
            // 创建新文件
            this.file.createNewFile();
        }
        InputStream reader = Main.class.getClassLoader().getResourceAsStream(this.fileName);
        assert reader != null;
        if (this.file.length() == 0){
            // 内无内容
            // 读取内容
            // 存储空间
            byte[] data = new byte[2048];
            int length = reader.read(data);
            FileOutputStream writer = new FileOutputStream(this.file);
            // 写入数据
            writer.write(data , 0 , length);
            reader.close();
            writer.close();
        }
        // 读取数据
        byte[] data = new byte[2048];
        FileInputStream fileReader = new FileInputStream(this.file);
        fileReader.read(data);
        String dataContext = new String(data);
        // 反序列化
        uiConfig = JSON.parseObject(dataContext , UiConfig.class);
    }

    public void save() throws IOException {
        FileOutputStream writer = new FileOutputStream(this.file);
        byte[] data = JSONObject.toJSONString(this.uiConfig , true).getBytes(StandardCharsets.UTF_8);
        writer.write(data); // 写入数据
        writer.close();
    }

    /**
     * 返回配置文件
     * @return 配置文件
     */
    public UiConfig getUiConfig() {
        return this.uiConfig;
    }

    /**
     * 是否存在配置文件
     * @return 存在配置文件？
     */
    public boolean existConfig(){
        return this.file.exists();
    }
}
