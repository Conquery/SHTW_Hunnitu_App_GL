package com.shtoone.glhnt.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.serviceDao.API;
import com.shtoone.glhnt.serviceDao.ParseXmlService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class UpdateManager {
    /* 下载状�? */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数�?*/
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private URL url = null;

    private Context mContext;
    /* 更新进度�?*/
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位�?
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安裑文件
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 软件更新
     */
    @SuppressLint("HandlerLeak")
    public void checkUpdate() {

        final Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    showNoticeDialog();
                }
                if (msg.what == 2) {
                    Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
                }
            }
        };

        // 获取当前软件版本
        final int versionCode = getVersionCode(mContext);

        new Thread(new Runnable() {
            public void run() {
                try {
                    // 创建version.xml的连接地 把version.xml放到网络上，然后获取文件信息
                    // // 服务�?路径
                    String uriPath = API.UpdateURL;
                    url = new URL(uriPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inStream = connection.getInputStream(); // 从输入流获取数据
                    // 解析XML文件由于XML文件比较小，因此使用DOM方式进行解析
                    ParseXmlService service = new ParseXmlService();
                    mHashMap = service.parseXml(inStream);

                    if (null != mHashMap) {
                        int serviceCode = Integer.valueOf(mHashMap.get("version"));
                        // 版本判断
                        if (serviceCode > versionCode) {
                            mhandler.sendEmptyMessage(3); // 设置�?
                        } else {
                            mhandler.sendEmptyMessage(2);
                        }
                    } else {
                        mhandler.sendEmptyMessage(2);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取软件版本
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            Log.i("code", "code : " + versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话
     */
    private void showNoticeDialog() {
        // 构键对话�?
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话�?
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话�?
     */
    private void showDownloadDialog() {
        // 构键软件下载对话�?
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.activity_soft_update, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress); // 取消按钮
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状�?
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软�?
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入�?
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);

                    Log.i("filePath:  ", mSavePath + "");
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位�?
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate); // 点击取消就停正G���?
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显�?
            mDownloadDialog.dismiss();
        }
    };

    /**
     * 安裑APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安裑APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}