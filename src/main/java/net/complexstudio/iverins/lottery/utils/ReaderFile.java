package net.complexstudio.iverins.lottery.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReaderFile {

    private final File needReadFile;
    private Collection<String> fileContext;

    public ReaderFile(File needReadFile){
        this.needReadFile = needReadFile;
    }

    public void loadFile() throws IOException {
        // 读取文件
        FileInputStream reader = new FileInputStream(this.needReadFile);
        byte[] contextBytes = new byte[(int) this.needReadFile.length()];
        // 读取
        reader.read(contextBytes);
        String contextStr = new String(contextBytes);
        String[] contexts = contextStr.split("\n");
        this.fileContext = Arrays.stream(contexts).map(String::trim).collect(Collectors.toList());
    }

    public Collection<String> getFileContext() throws IOException {
        this.loadFile();
        return this.fileContext;
    }

}
