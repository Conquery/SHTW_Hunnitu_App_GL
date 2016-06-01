package com.shtoone.glhnt.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import android.content.Context;
/**
 * Created by Administrator on 2015/11/21.
 */
public class DataPropertiy {
    Properties p;
    private Context context;
    private String PROP_PATH;

    public DataPropertiy(Context p_context) throws IOException {
        context = p_context;
        PROP_PATH = context.getFilesDir().getAbsolutePath();
        File file = new File(PROP_PATH + "/data.properties");
        // file.delete();
        if (!file.exists()) {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write("TENCENT_XG=0".getBytes());
                fos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // ¶Á
    public String get(String key) {
        p = new Properties();
        String keyValueString = "";
        try {
            InputStream in = new FileInputStream(PROP_PATH + "/data.properties");
            p.load(in);
            keyValueString = p.getProperty(key);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyValueString;
    }

    // Ð´
    public void put(String key, String value) {
        p = new Properties();
        try {
            InputStream in = new FileInputStream(PROP_PATH + "/data.properties");
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setProperty(key, value);
        OutputStream fos;
        try {
            fos = new FileOutputStream(PROP_PATH + "/data.properties");
            p.store(fos, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
