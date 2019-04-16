package com.dullon.demoboot.other;



import java.io.*;
//io流简单示例，根据文件路径读取文件，并通过 输出流写入新路径，并修改文件名后缀。
public class IoDemo {
    public static void main(String [] args) throws Exception {
        File strFile = new File("E:\\个人\\repo\\demo-boot\\src\\main\\java\\com\\dullon\\demoboot\\designpattern1");
        if (!(strFile.exists()&&strFile.isDirectory())) {
            throw new Exception("目录不存在");
        }
        File [] files = strFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });

        System.out.println(files.length);
        File destDir = new File("D:\\word\\jad");
        if (!(destDir.exists()&&destDir.isDirectory())){
            destDir.mkdir();
        }
        for (File f:files) {
            FileInputStream fin = new FileInputStream(f);
            String destFileName = f.getName().replaceAll("\\.java$",".jad");
            FileOutputStream fout = new FileOutputStream(new File(destDir,destFileName));
            copy(fin,fout);
            fin.close();
            fout.close();
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        int len = 0;
        byte [] buf = new byte[1024];
        while ((len = in.read(buf)) != -1){
            out.write(buf,0,len);
        }

    }
}
