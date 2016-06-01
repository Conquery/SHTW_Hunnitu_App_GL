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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SC_shengchanchaxunXqAdapter;
import com.shtoone.glhnt.entity.BHZ_SCshujuchaxun_XqEntity;
import com.shtoone.glhnt.serviceDao.API;
import com.shtoone.glhnt.serviceDao.ImageService;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DateTimePicker;
import com.shtoone.glhnt.util.HttpUtil;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BHZ_ChaobiaoChuZHi_XqActivity extends Activity {

    private TextView tv_time, tv_proName, tv_jbsj, tv_shuliang, tv_gdNum, tv_caozuozhe, tv_didian, tv_jiaozhubuwei, tv_wjjpinzhong,
            tv_shuinipinzhong, tv_shigongpeibibianhao, tv_qiangdudengji, tv_title;
    private ListView listView;
    private ServiceDao service;
    private BHZ_SCshujuchaxun_XqEntity data;
    private ImageView iv_image;
    private EditText et_reason, et_result, et_type, jianli_result, jianli_shenpi, shenpi_confirmday, shenpi_shenpiday, bhgsy_xq_chulishijian, bhgsy_xq_chuliren;
    private Button btn_submit, btn_reset;
    //private Bitmap bitmap;
    private ImageButton btn_back;
    private String xqID;
    private String shebeibianhao;
    private String titleName;
    private String userFullName, role;
    private Boolean chuzhi = false;
    private Handler handler;
    private ProgressDialog mypDialog;
    private String xmmc;
    private LinearLayout jianliSee, chuzhiSee, buttons;
    private RelativeLayout chaobiao_title;
    private Bitmap bitmap = null; // ͼƬ
    private String startTime = "2015-09-01 12:12:12", endTime = "2015-09-30 12:12:12", chuliTime = "2015-09-30 12:12:12";
    //private DataPropertiy dp;
    private boolean isShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_chaobiaochuzhixq);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        try {
            beforeStart();
            setProgressDialog(); //����������Ч
            findView();
            setListener();
            createHandler();
            startGetDataThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �����������߳�
     */
    private void startGetDataThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    data = service.getBhzChaobiaoXqData(xqID, shebeibianhao);
                    Thread.sleep(500);
                    if (data != null) {
                        Log.d("����ͼƬ��ַ��", data.getFilePath());
                        bitmap = ImageService.getImage(data.getFilePath());
                        Thread.sleep(1000);
                    }
                } catch (Exception ignored) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    /**
     * ���ض���
     */
    private void setProgressDialog() {
        mypDialog = new ProgressDialog(BHZ_ChaobiaoChuZHi_XqActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("������,���Ժ�...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    /**
     * �̼߳������
     */
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    mypDialog.dismiss();
                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "���޸������ݣ�", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    /**
     * Ԥ����
     */
    private void beforeStart() throws IOException {
        //***** ���ڳ��ڻ� BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        startTime = endTime;
        chuliTime = endTime;
        //***** ���ڳ��ڻ� END *****
        xmmc = getIntent().getStringExtra("xmmc");
        role = getIntent().getStringExtra("role");
        chuzhi = getIntent().getBooleanExtra("chuzhi", false);
        Log.d("����Ȩ�ޣ�", String.valueOf(chuzhi));
        //dp = new DataPropertiy(BHZ_ChaobiaoChuZHi_XqActivity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > " + getResources().getString(R.string.bhz) + " > ��������");
        service = new ServiceDao();
        xqID = getIntent().getStringExtra("xqID");
        shebeibianhao = getIntent().getStringExtra("shebeibianhao");
        titleName = getIntent().getStringExtra("Title_Name");
        userFullName = getIntent().getStringExtra("userFullName");
    }

    /**
     * �¼�����
     */
    private void setListener() {

        //shenpi_confirmday,shenpi_shenpiday
        shenpi_confirmday.setText(startTime);
        shenpi_shenpiday.setText(endTime);
        shenpi_confirmday.setFocusableInTouchMode(false);
        shenpi_shenpiday.setFocusableInTouchMode(false);
        bhgsy_xq_chulishijian.setFocusableInTouchMode(false);
        shenpi_confirmday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicKDialog = new DateTimePicker(
                        BHZ_ChaobiaoChuZHi_XqActivity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(shenpi_confirmday);
            }
        });
        shenpi_shenpiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicKDialog = new DateTimePicker(
                        BHZ_ChaobiaoChuZHi_XqActivity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(shenpi_shenpiday);
            }
        });

        bhgsy_xq_chulishijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicKDialog = new DateTimePicker(
                        BHZ_ChaobiaoChuZHi_XqActivity.this, chuliTime);
                dateTimePicKDialog.dateTimePicKDialog(shenpi_shenpiday);
            }
        });

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BHZ_ChaobiaoChuZHi_XqActivity.this)
                        .setMessage("��ѡ��").setCancelable(true);
                builder.setPositiveButton("���", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                });
                builder.setNegativeButton("���", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");// ��Ƭ����
                        startActivityForResult(intent, 2);
                    }
                });
                builder.create().show();
            }
        });

        //����ύ
        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BHZ_ChaobiaoChuZHi_XqActivity.this)
                        .setMessage("�Ƿ��ύ?").setCancelable(true);
                builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = null, result = null, type = null, jlReslt = null, jlSp = null, czDay = null, jlConfirmDay = null, jlSpDay = null;
                        try {
                            reason = URLEncoder.encode(et_reason.getText().toString().trim(), "utf-8");
                            result = URLEncoder.encode(et_result.getText().toString().trim(), "utf-8");
                            type = URLEncoder.encode(et_type.getText().toString().trim(), "utf-8");
                            jlReslt = URLEncoder.encode(jianli_result.getText().toString().trim(), "utf-8");
                            jlSp = URLEncoder.encode(jianli_shenpi.getText().toString().trim(), "utf-8");
                            userFullName = URLEncoder.encode(bhgsy_xq_chuliren.getText().toString(), "utf-8");
                            czDay = bhgsy_xq_chulishijian.getText().toString();
                            jlConfirmDay = shenpi_confirmday.getText().toString();
                            jlSpDay = shenpi_shenpiday.getText().toString();
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        //Log.d("mytag", reason + result + type);
                        try {
                            if ("CZ".equals(role)) {
                                if ("".equals(reason)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�����볬��ԭ��", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(result)) { //���ý��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�����봦�ý����", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(type)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�����봦�÷�ʽ��", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(userFullName)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�����봦���ˣ�", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                if ("".equals(czDay)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�����봦��ʱ�䣡", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } else {
                                if ("".equals(jlReslt)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "���������������", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(jlSp)) { //���ý��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "���������������", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(jlConfirmDay)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "������ȷ��ʱ�䣡", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                if ("".equals(jlSpDay)) { //����ԭ��û������ ��ʾ������
                                    Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "������ȷ��ʱ�䣡", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }

                            final String url = API.BHZ_CHAOBIAO_DO_URL.replace("%1", data.getId())
                                    .replace("%2", reason).replace("%3", type).replace("%4", result)
                                    .replace("%5", userFullName).replace("%6", CommFunctions.ChangeTimeToLong(czDay));
                            final String jl_url = API.BHZ_CHAOBIAO_SP.replace("%1", data.getId()).replace("%2", jlReslt)
                                    .replace("%3", jlSp).replace("%4", CommFunctions.ChangeTimeToLong(jlConfirmDay)).replace("%5", userFullName)
                                    .replace("%6", CommFunctions.ChangeTimeToLong(jlSpDay));
                            Log.d("ͼƬ�ϴ�URL��", url);
                            Log.d("��������URL��", jl_url);
                            Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�ϴ��У����Ժ󡣡���", Toast.LENGTH_SHORT).show();
                            final Handler mhandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (msg.what == 1) {
                                        Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�ϴ��ɹ���", Toast.LENGTH_LONG).show();
                                        setResult(1);
                                        finish();
                                    } else {
                                        Toast.makeText(BHZ_ChaobiaoChuZHi_XqActivity.this, "�ϴ�ʧ�ܣ�", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            new Thread(new Runnable() {
                                public void run() {
                                    boolean flag = true;
                                    if ("CZ".equals(role))
                                        //ʩ������
                                        flag = uploadPic(url); //����Ϊ true ��ʾ�ϴ��ɹ� �� false �ϴ�ʧ�� TODO
                                    else {
                                        //��������
                                        Map<String, String> map = new HashMap<String, String>();
                                        try {
                                            String request = HttpUtil.postRequest(jl_url, map);
                                            JSONObject jo = new JSONObject(request);
                                            flag = jo.getBoolean("success");
                                            Log.d("���վ���ϸ����������", request);
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (flag) {
                                        mhandler.sendEmptyMessage(1);  // �ϴ��ɹ� ������Ϣ�� handler �ر�����ҳ����ʾ�ϴ��ɹ�
                                    } else {
                                        mhandler.sendEmptyMessage(2);  // �ϴ�ʧ�� ��ʲô������ ͣ���ڴ�ҳ��
                                    }
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setPositiveButton("ȡ��", null);
                builder.create().show();
            }
        });
        //�������
        btn_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BHZ_ChaobiaoChuZHi_XqActivity.this)
                        .setMessage("�Ƿ��������������?").setCancelable(true);
                builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() { //���ȷ�������������������
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_reason.setText("");
                        et_result.setText("");
                        et_type.setText("");
                        bitmap = null;
                        iv_image.setImageResource(R.drawable.camera);
                    }
                });
                builder.setPositiveButton("ȡ��", null); //���ȡ��ʲô������
                builder.create().show();
            }
        });

    }

    private void showView() {
        tv_title.setText(titleName);
        tv_time.setText(data.getTime());
        tv_proName.setText(data.getProName());
        tv_jbsj.setText(data.getJbsj());
        tv_shuliang.setText(data.getShuliang());
        tv_gdNum.setText(data.getGdh());
        tv_caozuozhe.setText(data.getCaozuozhe());
        tv_didian.setText(data.getDidian());
        tv_jiaozhubuwei.setText(data.getJzbw());
        tv_wjjpinzhong.setText(data.getWjjpz());
        tv_shuinipinzhong.setText(data.getSnpz());
        tv_shigongpeibibianhao.setText(data.getSgpbbj());
        tv_qiangdudengji.setText(data.getQddj());
        et_reason.setText(data.getWentiyuanyin());
        et_result.setText(data.getChulijieguo());
        et_type.setText(data.getChulifangshi());

        //��ʾͼƬ
        if (bitmap != null) {
            iv_image.setImageBitmap(bitmap);
        }

        Log.d("��ɫ������ʲô", role);
        if (!"".equals(data.getChulishijian())) {
            bhgsy_xq_chulishijian.setText(data.getChulishijian());
            bhgsy_xq_chuliren.setText(data.getChuliren());
        }

        //�����������
        jianli_result.setText(data.getJianliresult());
        jianli_shenpi.setText(data.getJianlishenpi());
        if (!data.getConfirmdate().equals(""))
            shenpi_confirmday.setText(data.getConfirmdate());
        if (!data.getShenpidate().equals(""))
            shenpi_shenpiday.setText(data.getShenpidate());

        SC_shengchanchaxunXqAdapter listAdapter = new SC_shengchanchaxunXqAdapter(BHZ_ChaobiaoChuZHi_XqActivity.this, data.getItems());
        listView.setAdapter(listAdapter);
        //ScrollWithListView.setListViewHeightBasedOnChildren(listView);

        int totalHeight = 0;
        int itemHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            itemHeight = listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + 3 * itemHeight;
        listView.setLayoutParams(params);
    }

    private void findView() {
        jianliSee = (LinearLayout) this.findViewById(R.id.jianliSee);
        chuzhiSee = (LinearLayout) this.findViewById(R.id.chuzhiSee);
        buttons = (LinearLayout) this.findViewById(R.id.buttons);
        chaobiao_title = (RelativeLayout) this.findViewById(R.id.chaobiao_title);
        ;

        tv_title = (TextView) this.findViewById(R.id.scshuju_xq_title);
        tv_time = (TextView) this.findViewById(R.id.scshuju_xq_clsj);
        tv_proName = (TextView) this.findViewById(R.id.scshuju_xq_projectName);
        tv_jbsj = (TextView) this.findViewById(R.id.scshuju_xq_jbsj);
        tv_shuliang = (TextView) this.findViewById(R.id.scshuju_xq_shuliang);
        tv_gdNum = (TextView) this.findViewById(R.id.scshuju_xq_gdh);
        tv_caozuozhe = (TextView) this.findViewById(R.id.scshuju_xq_czz);
        tv_didian = (TextView) this.findViewById(R.id.scshuju_xq_ddlc);
        tv_jiaozhubuwei = (TextView) this.findViewById(R.id.scshuju_xq_jzbw);
        tv_wjjpinzhong = (TextView) this.findViewById(R.id.scshuju_xq_wjjpz);
        tv_shuinipinzhong = (TextView) this.findViewById(R.id.scshuju_xq_snpz);
        tv_shigongpeibibianhao = (TextView) this.findViewById(R.id.scshuju_xq_sgpbbh);
        tv_qiangdudengji = (TextView) this.findViewById(R.id.scshuju_xq_qddj);
        listView = (ListView) this.findViewById(R.id.scshuju_xq_listView);
        et_reason = (EditText) this.findViewById(R.id.bhgsy_xq_yuanyin);
        et_result = (EditText) this.findViewById(R.id.bhgsy_xq_chuzhijieguo);
        et_type = (EditText) this.findViewById(R.id.bhgsy_xq_chulifangshi);
        btn_submit = (Button) this.findViewById(R.id.bhgsy_xq_submit);
        btn_reset = (Button) this.findViewById(R.id.bhgsy_xq_unDone);
        iv_image = (ImageView) this.findViewById(R.id.bhgsy_xq_image);
        btn_back = (ImageButton) this.findViewById(R.id.back);
        jianli_result = (EditText) this.findViewById(R.id.jianli_result);
        jianli_shenpi = (EditText) this.findViewById(R.id.jianli_shenpi);
        shenpi_confirmday = (EditText) this.findViewById(R.id.shenpi_confirmday);
        shenpi_shenpiday = (EditText) this.findViewById(R.id.shenpi_shenpiday);
        bhgsy_xq_chulishijian = (EditText) this.findViewById(R.id.bhgsy_xq_chulishijian);
        bhgsy_xq_chuliren = (EditText) this.findViewById(R.id.bhgsy_xq_chuliren);
        bhgsy_xq_chulishijian.setText(startTime);
        bhgsy_xq_chuliren.setText(userFullName);
        shenpi_confirmday.setText(startTime);
        shenpi_shenpiday.setText(endTime);

        if ("SP".equals(role)) {
            jianliSee.setVisibility(View.VISIBLE);
            et_reason.setEnabled(chuzhi);
            et_result.setEnabled(chuzhi);
            et_type.setEnabled(chuzhi);
            bhgsy_xq_chulishijian.setEnabled(chuzhi);
            bhgsy_xq_chuliren.setEnabled(chuzhi);
        } else if ("CZ".equals(role)) {
            jianliSee.setVisibility(View.GONE);
        } else {
            chuzhiSee.setVisibility(View.GONE);
            jianliSee.setVisibility(View.GONE);
            buttons.setVisibility(View.GONE);
            chaobiao_title.setVisibility(View.GONE);
        }
    }

    /**
     * �ϴ�ͼƬ�ķ���
     *
     * @param path
     * @return
     */
    private boolean uploadPic(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // ����ÿ�δ��������С��������Ч��ֹ�ֻ���Ϊ�ڴ治�����
            // �˷���������Ԥ�Ȳ�֪�����ݳ���ʱ����û�н����ڲ������ HTTP �������ĵ�����
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // �������������
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "text/html");

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            // ��Ҫ�ϴ�������д������
            //			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            }
            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

            byte[] buffer = new byte[8192]; // 8k
            int length = 0;
            // ��ȡ�� ��д�뵽 �ϴ�����
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
            if (jo.getBoolean("success")) { //����������json success Ϊ true ��ʾ�ϴ��ɹ�
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
     * �����ջ�ѡ��ͼƬ֮�� �Ĵ�����
     */
    @SuppressWarnings("static-access")
    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 1) { // ��ʾ�Ǵ����շ��ص������������
            // �ж��ڴ濨�Ƿ����
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.i("mytag", "SD card is not avaiable/writeable right now");
                return;
            }

            //�� ���ص� bitmap ���ж�Ӧ�ı������
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
                    e2.printStackTrace();
                }
            }
        } else if (requestCode == 2) { // ��ʾ�Ǵ�ѡ��ͼƬ����
            Uri uri = data.getData(); //�õ�ͼƬ uri
            ContentResolver resolver = getContentResolver(); //������
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri); //  ����Ӧ uri ͨ��������ת��Ϊ bitmap
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            iv_image.setImageBitmap(bitmap);
        }
    }
}
