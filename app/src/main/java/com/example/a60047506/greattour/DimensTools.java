package com.example.a60047506.greattour;

/**
 * Created by 60047506 on 2017-11-06.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class DimensTools {
    static String oldFilePath = "./res/values-nodpi/dimens.xml";
    static String filePath720 = "./res/values-1280x720/dimens.xml";
    static String filePath672 = "./res/values-1280x672/dimens.xml";
    static String filePath1080 = "./res/values-1920x1080/dimens.xml";
    static float changes = 1.5f;

    public static void main(String[] args) {
        String allPx = getAllPx();
        DeleteFolder(oldFilePath);
        writeFile(oldFilePath, allPx);
        String st = convertStreamToString(oldFilePath, changes);
        DeleteFolder(filePath720);
        writeFile(filePath720, st);
        DeleteFolder(filePath672);
        writeFile(filePath672, st);
        String st1 = convertStreamToString(oldFilePath, 1f);
        DeleteFolder(filePath1080);
        writeFile(filePath1080, st1);
    }

    public static String convertStreamToString(String filepath, float f) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(filepath));
            String line = null;
            System.out.println("q1");
            String endmark = "px</dimen>";
            String startmark = ">";
            while ((line = bf.readLine()) != null) {
                if (line.contains(endmark)) {
                    int end = line.lastIndexOf(endmark);
                    int start = line.indexOf(startmark);
                    String stpx = line.substring(start + 1, end);
                    int px = Integer.parseInt(stpx);
                    int newpx = (int) ((float) px / f);
                    String newline = line.replace(px + "px", newpx + "px");
                    sb.append(newline + "\r\n");
                } else {
                    sb.append(line + "\r\n");
                }
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean DeleteFolder(String sPath) {
        File file = new File(sPath);
        if (!file.exists()) {
            return true;
        } else {
            if (file.isFile()) {
                return deleteFile(sPath);
            } else {
                // return deleteDirectory(sPath);
            }
        }
        return false;
    }

    public static void writeFile(String filepath, String st) {
        try {
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(st);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAllPx() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<resources>" + "\r\n");
            sb.append("<dimen name=\"screen_width\">1920px</dimen>" + "\r\n");
            sb.append("<dimen name=\"screen_height\">1080px</dimen>" + "\r\n");
            for (int i = 1; i <= 1920; i++) {
                System.out.println("i=" + i);
                sb.append("<dimen name=\"px" + i + "\">" + i + "px</dimen>"
                        + "\r\n");
            }
            sb.append("</resources>" + "\r\n");
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
