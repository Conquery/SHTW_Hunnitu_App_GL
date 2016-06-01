package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SYS_BuhegechuzhiXQAdapter;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_XqEntity;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.ScrollWithListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;

public class SYS_BuHeGeSYChuZhi_XqActivity extends Activity {

    private TextView tv_title, tv_sysName, tv_syName, tv_proName, tv_partName, tv_weituoNum, tv_shijianNum, tv_syTime, tv_shijianChicun,
            tv_sjQiangdu, tv_lingqi, tv_qiangduDaibiao, tv_pingzhongNum, tv_guigezhonglei, tv_gcZhiJing, tv_czry, tv_result;
    private ImageView iv_result, iv_image;
    private ListView listView;
    private EditText et_reason, et_result, et_type;
    private Button  btn_submit, btn_reset;
    private Handler handler;
    private ProgressDialog mypDialog;
    private ServiceDao service;
    private SYS_BuHeGeChuZhi_XqEntity data;
    private Bitmap bitmap;
    private String xqID, titleName;
    private ImageButton btn_back;
    //private DataPropertiy dp;
    private String xmmc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_buhegechuzhixq);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        try {
            beforeStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setProgressDialog(); //创建加载特效
        findView();
        setListener();
        createHandler();
        startGetDataThread();
    }

    private void beforeStart() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        //dp = new DataPropertiy(SYS_BuHeGeSYChuZhi_XqActivity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > 试验室管理 > 不合格试验处置");
        service = new ServiceDao();
        xqID = getIntent().getStringExtra("xqID");
        titleName = getIntent().getStringExtra("Title_Name");
    }

    private void setListener() {

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_BuHeGeSYChuZhi_XqActivity.this)
                        .setMessage("请选择").setCancelable(true);
                builder.setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                });
                builder.setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");// 相片类型
                        startActivityForResult(intent, 2);
                    }
                });
                builder.create().show();
            }
        });

        //点击提交
        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_BuHeGeSYChuZhi_XqActivity.this)
                        .setMessage("是否提交?").setCancelable(true);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = null, result = null, type = null;
                        try {
                            reason = URLEncoder.encode(et_reason.getText().toString().trim(), "utf-8");
                            result = URLEncoder.encode(et_result.getText().toString().trim(), "utf-8");
                            type = URLEncoder.encode(et_type.getText().toString().trim(), "utf-8");
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        Log.d("mytag", reason + result + type);
                        try {
                            if ("".equals(reason)) { //超标原因没有内容 提示并返回
                                //DialogUtil.showDialog(SC_chaobiaochaxunXqActivity.this, "请输入超标原因！",false);
                                Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "请输入不合格原因！", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if ("".equals(result)) { //处置结果没有内容 提示并返回
                                Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "请输入处置结果！", Toast.LENGTH_LONG).show();
                                //DialogUtil.showDialog(SC_chaobiaochaxunXqActivity.this, "请输入处置结果！",false);
                                return;
                            }
                            if ("".equals(type)) { //处置原因没有内容 提示并返回
                                Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "请输入处置方式！", Toast.LENGTH_LONG).show();
                                //DialogUtil.showDialog(SC_chaobiaochaxunXqActivity.this, "请输入处置方式！",false);
                                return;
                            }
//							if (bitmap == null) { // 没有图片 提示并返回
//								DialogUtil.showDialog(SC_chaobiaochaxunXqActivity.this, "请拍照或选择图片！",false);
//								return;
//							}
//							final String url = APIUtil.get("SC_DAICHUZHIBAOJING_POST").replace("%1", xxid + "")
//									.replace("%2", reason).replace("%3", type).replace("%4", result);
//							Log.d("mytag", url);
                            Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "上传中，请稍后。。。", Toast.LENGTH_SHORT).show();
                            final Handler mhandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (msg.what == 1) {
                                        Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
                                        setResult(1);
                                        finish();
                                    } else {
                                        Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "上传失败！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            new Thread(new Runnable() {
                                public void run() {
//									boolean flag = uploadPic(url); //返回为 true 表示上传成功 ； false 上传失败 TODO
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (true) {
                                        mhandler.sendEmptyMessage(1);  // 上传成功 发送消息到 handler 关闭详情页并提示上传成功
                                    } else {
                                        mhandler.sendEmptyMessage(2);  // 上传失败 则什么都不做 停留在此页面
                                    }
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setPositiveButton("取消", null);
                builder.create().show();
            }
        });
        //点击重置
        btn_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_BuHeGeSYChuZhi_XqActivity.this)
                        .setMessage("是否重置输入的内容?").setCancelable(true);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() { //点击确定清空三个输入框的内容
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_reason.setText("");
                        et_result.setText("");
                        et_type.setText("");
                        bitmap = null;
                        iv_image.setImageResource(R.drawable.camera);
                    }
                });
                builder.setPositiveButton("取消", null); //点击取消什么都不做
                builder.create().show();
            }
        });

    }

    private void startGetDataThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    data = service.getXqData(xqID);
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    mypDialog.dismiss();
                    Toast.makeText(SYS_BuHeGeSYChuZhi_XqActivity.this, "已无更多数据！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    protected void showView() {
        tv_title.setText(titleName);
        tv_sysName.setText(data.getSysName());
        tv_syName.setText(data.getSyName());
        tv_proName.setText(data.getProName());
        tv_partName.setText(data.getPartName());
        tv_weituoNum.setText(data.getWeituoNum());
        tv_shijianNum.setText(data.getShijianNum());
        tv_syTime.setText(data.getShiyanTime());
        tv_shijianChicun.setText(data.getShijianChicun());
        tv_sjQiangdu.setText(data.getSjQiangdu());
        tv_lingqi.setText(data.getLingqi());
        tv_qiangduDaibiao.setText(data.getQdDaibiao());
        tv_pingzhongNum.setText(data.getPingzhongNum());
        tv_guigezhonglei.setText(data.getGuigeZhonglei());
        tv_gcZhiJing.setText(data.getGongchengZhijing());
        tv_czry.setText(data.getCaozuorenyuan());
        tv_result.setText(data.getPanduanjieguou());
//		if ("合格".equals(data.getPanduanjieguou())) {
//			iv_result.setImageResource(R.drawable.ok);
//		} else {
//			iv_result.setImageResource(R.drawable.ng);
//		}
        listView.setAdapter(new SYS_BuhegechuzhiXQAdapter(SYS_BuHeGeSYChuZhi_XqActivity.this, data.getItems()));
        ScrollWithListView.setListViewHeightBasedOnChildren(listView);
    }

    private void findView() {
        btn_back = (ImageButton) this.findViewById(R.id.back);
        tv_title = (TextView) this.findViewById(R.id.bhgcz_xq_title);
        tv_sysName = (TextView) this.findViewById(R.id.bhgsy_xq_sysmc);
        tv_syName = (TextView) this.findViewById(R.id.bhgsy_xq_symc);
        tv_proName = (TextView) this.findViewById(R.id.bhgsy_xq_projectName);
        tv_partName = (TextView) this.findViewById(R.id.bhgsy_xq_partName);
        tv_weituoNum = (TextView) this.findViewById(R.id.bhgsy_xq_weituoNum);
        tv_shijianNum = (TextView) this.findViewById(R.id.bhgsy_xq_shijianNum);
        tv_syTime = (TextView) this.findViewById(R.id.bhgsy_xq_syriqi);
        tv_shijianChicun = (TextView) this.findViewById(R.id.bhgsy_xq_shijianchicun);
        tv_sjQiangdu = (TextView) this.findViewById(R.id.bhgsy_xq_sjQiangdu);
        tv_lingqi = (TextView) this.findViewById(R.id.bhgsy_xq_lingqi);
        tv_qiangduDaibiao = (TextView) this.findViewById(R.id.bhgsy_xq_qiangduDaibiao);
        tv_pingzhongNum = (TextView) this.findViewById(R.id.bhgsy_xq_pzbm);
        tv_guigezhonglei = (TextView) this.findViewById(R.id.bhgsy_xq_ggzl);
        tv_gcZhiJing = (TextView) this.findViewById(R.id.bhgsy_xq_gczj);
        tv_czry = (TextView) this.findViewById(R.id.bhgsy_xq_czry);
        tv_result = (TextView) this.findViewById(R.id.bhgsy_xq_pdjg);
//		iv_result = (ImageView) this.findViewById(R.id.bhgsy_xq_iv_pdjg);
        iv_image = (ImageView) this.findViewById(R.id.bhgsy_xq_image);
        listView = (ListView) this.findViewById(R.id.bhgsy_xq_listView);
        et_reason = (EditText) this.findViewById(R.id.bhgsy_xq_yuanyin);
        et_result = (EditText) this.findViewById(R.id.bhgsy_xq_chuzhijieguo);
        et_type = (EditText) this.findViewById(R.id.bhgsy_xq_chulifangshi);
//        btn_take = (Button) this.findViewById(R.id.bhgsy_xq_photograph);
//        btn_select = (Button) this.findViewById(R.id.bhgsy_xq_photos);
        btn_submit = (Button) this.findViewById(R.id.bhgsy_xq_submit);
        btn_reset = (Button) this.findViewById(R.id.bhgsy_xq_unDone);
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(SYS_BuHeGeSYChuZhi_XqActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    /**
     * 上传图片的方法
     *
     * @param path
     * @return
     */
    private boolean uploadPic(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "text/html");

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            // 将要上传的内容写入流中
            //			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            }
            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

            byte[] buffer = new byte[8192]; // 8k
            int length = 0;
            // 读取流 并写入到 上传流中
            while ((length = inputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            inputStream.close();
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String sl;
            String result = "";
            while ((sl = br.readLine()) != null)
                result = result + sl;
            JSONObject jo = new JSONObject(result);
            Log.d("mytag", jo.toString());
            br.close();
            is.close();
            if (jo.getBoolean("success")) { //服务器返回json success 为 true 表示上传成功
                return true;
            } else {
                return false;
            }
            // dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在拍照或选择图片之后 的处理方法
     */
    @SuppressWarnings("static-access")
    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 1) { // 表示是从拍照返回到超标详情界面
            // 判断内存卡是否可用
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.i("mytag", "SD card is not avaiable/writeable right now");
                return;
            }

            //对 返回的 bitmap 进行对应的保存操作
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");

            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();
            String fileName = "/sdcard/myImage/" + name;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (Exception e2) {
                }
            }
        } else if (requestCode == 2) { // 表示是从选择图片返回
            Uri uri = data.getData(); //得到图片 uri
            ContentResolver resolver = getContentResolver(); //处理器
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri); //  将对应 uri 通过处理器转化为 bitmap
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            iv_image.setImageBitmap(bitmap);
        }
    }

}