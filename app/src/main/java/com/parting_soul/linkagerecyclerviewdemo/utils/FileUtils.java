package com.parting_soul.linkagerecyclerviewdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author parting_soul
 * @date 2018/1/27
 * IO操作工具类
 */
public class FileUtils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeable = null;
            }
        }
    }

    /**
     * 保存内容到文件
     *
     * @param path
     * @param content
     */
    public static boolean saveContentToFile(String path, String content) {
        return saveContentToFile(path, content, false);
    }

    /**
     * 保存内容到文件
     *
     * @param path
     * @param content
     * @param isAppend
     * @return
     */
    public static boolean saveContentToFile(String path, String content, boolean isAppend) {
        boolean isSuccess = true;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path), isAppend);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            closeQuietly(fos);
        }
        return isSuccess;
    }

    public static void deleteFiles(File root, String conditionPrefix) {
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                if (file.getName().startsWith(conditionPrefix)) {
                    file.delete();
                }
            }
        }
    }


    /**
     * 外部存储器是否可用
     *
     * @return
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 从Asset中获取数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readFromAsset(Context context, String fileName) {
        String result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(baos);
        }
        return result;
    }

}
