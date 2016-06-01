package com.shtoone.glhnt.serviceDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.shtoone.glhnt.activity.PushMessageListActivity;
import com.shtoone.glhnt.activity.BHZ_ChaobiaoChuZHi_XqActivity;
import com.shtoone.glhnt.entity.XGNotification;
import com.shtoone.glhnt.util.DataPropertiy;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/11/26.
 */
public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";
    static DataPropertiy dp; // ��дdata.properties����
    private String username = "";

    private void show(Context context, String text) {
// 		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        SharedPreferences saveUserPassword = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        username = saveUserPassword.getString("username", "");
    }

    // ֪ͨչʾ
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        XGNotification notific = new XGNotification();
        notific.setMsg_id(notifiShowedRlt.getMsgId());
        notific.setTitle(notifiShowedRlt.getTitle());
        notific.setContent(notifiShowedRlt.getContent());
        // notificationActionType==1ΪActivity��2Ϊurl��3Ϊintent
        notific.setNotificationActionType(notifiShowedRlt
                .getNotificationActionType());
        // Activity,url,intent������ͨ��getActivity()���
        notific.setActivity(notifiShowedRlt.getActivity());
        notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(Calendar.getInstance().getTime()));
        NotificationService.getInstance(context).save(notific);
        context.sendBroadcast(intent);
        show(context, "����1������Ϣ, " + "֪ͨ��չʾ �� " + notifiShowedRlt.toString());
        try {
            dp = new DataPropertiy(context);
            String MSG_ID = String.valueOf(notifiShowedRlt.getMsgId());
            //MSG_ID = MSG_ID.substring(1,MSG_ID.length());
            dp.put("MSG_ID", MSG_ID);

            Date date = new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
            String rcvtime= format.format(date);
            date = format.parse(rcvtime);
            rcvtime = String.valueOf(date.getTime()/1000);
            //�����û��ֻ���ȥ
            String rString = query(MSG_ID,dp.get("USER_PHONE"),rcvtime);
            Log.d("��ִURL�ķ��أ�", rString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ���巢������ķ���
    private String query(String MsgId, String UserName, String Date) throws Exception {
        // ���巢�������URL
//        String url = APIUtil.get("XG_RECIVER").replace("%1", MsgId).replace("%2", UserName).replace("%3", Date);
//        Log.d("��ִURL��", url);
//        // ��������
//        return HttpUtil.getRequest(url);
        //ף���� TODO: ���ʹ򿪽���
        return "";
    }

    /**
     * �ַ���ת��unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // ȡ��ÿһ���ַ�
            char c = string.charAt(i);
            String m="";
            if(Integer.toHexString(c).length()!=4){
                for(int j=0;j<4-Integer.toHexString(c).length();j++){
                    m=m+"0";
                }
            }
            m=m+Integer.toHexString(c);
            // ת��Ϊunicode
            unicode.append("%u" + m);
        }
        return unicode.toString();
    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "��ע��ɹ�";
        } else {
            text = "��ע��ʧ��" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"���óɹ�";
        } else {
            text = "\"" + tagName + "\"����ʧ��,�����룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"ɾ���ɹ�";
        } else {
            text = "\"" + tagName + "\"ɾ��ʧ��,�����룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    // ֪ͨ����ص� actionType=1Ϊ����Ϣ�������actionType=0Ϊ����Ϣ�����
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        int actionType = (int) message.getActionType();
        if (actionType == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // ֪ͨ��֪ͨ�������������������
            // APP�Լ�����������ض���

            //TODO:�����߼�������message.getMsgId()����ͬ��API
            long msg_id = message.getMsgId();
            JSONObject jo = null;
            try {
                String id = resetMsg_id(String.valueOf(msg_id));
                if (id != null) {
                    Log.d("mytag", "success" + id);
                    jo = query_push(id);
                } else {
                    Log.d("mytag", "failing" + id);
                }
                Log.d("mytag", "�����֪ͨ�ķ�������Ϣ��"+jo.toString());
                if (jo != null) {
                    if ("LQ".equals(jo.getString("leixing"))) {
                        Intent i = new Intent(context, BHZ_ChaobiaoChuZHi_XqActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", username);
                        bundle.putString("BidName", jo.getString("biaoduanname"));
                        bundle.putString("xxid", jo.getString("bianhao"));
                        i.putExtras(bundle);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else if ("SW".equals(jo.getString("leixing"))) {
                        Intent i = new Intent(context, BHZ_ChaobiaoChuZHi_XqActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", username);
                        bundle.putString("BidName", jo.getString("biaoduanname"));
                        bundle.putString("xxid", jo.getString("bianhao"));
                        i.putExtras(bundle);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        Intent i = new Intent(context, PushMessageListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            //����API�ķ���ֵ�ж������໹��ˮ�Ȼ�����������������࣬�������౨�������������

            //�����ˮ�ȣ�����ˮ�ȱ�������������棻

            //�������������ʱ�������������б���档
            Log.d("��Ϣ�������", "׼������");
//			Intent intent2 = new Intent("com.shtoone.shtw_liqing_app.PushMessageListActivity");
//			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intent2);

            // �������������activity��onResumeҲ�ܼ������뿴��3���������
            text = "֪ͨ���� :" + message;

        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // ֪ͨ���������������
            // APP�Լ�����֪ͨ����������ض���
            text = "֪ͨ����� :" + message;
        }
        Toast.makeText(context, "�㲥���յ�֪ͨ�����:" + message.toString(),
                Toast.LENGTH_SHORT).show();
        // ��ȡ�Զ���key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                Log.d("mytag", "keyValues"+obj.toString());
                // key1Ϊǰ̨���õ�key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP��������Ĺ��̡�����
        Log.d(LogTag, text);
        show(context, text);
    }

    private String resetMsg_id(String msg_id) {
        int length = msg_id.length();
        if (length > 1) {
            return msg_id.substring(1, length);
        }
        return null;
    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "ע��ɹ�";
            // ��������token
            String token = message.getToken();
        } else {
            text = message + "ע��ʧ�ܣ������룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    // ��Ϣ͸��
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        String text = "�յ���Ϣ:" + message.toString();
        // ��ȡ�Զ���key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1Ϊǰ̨���õ�key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP����������Ϣ�Ĺ���...
        Log.d(LogTag, text);
        show(context, text);
    }

    // ����������������ķ���
    private JSONObject query_push(String msg_id) throws Exception {
        // ���巢�������URL
//        String url = APIUtil.get("XG_CLICK").replace("%1", msg_id);
//        Log.d("���͵����ִURL:", url);
//        // ��������
//        String string = HttpUtil.getRequest(url);
//        if (string == null) {
//            return null;
//        }
//        return new JSONObject(string);
        return new JSONObject("");
    }
}
