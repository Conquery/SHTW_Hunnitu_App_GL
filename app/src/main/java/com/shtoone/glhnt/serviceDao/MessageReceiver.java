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
    static DataPropertiy dp; // 读写data.properties的类
    private String username = "";

    private void show(Context context, String text) {
// 		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        SharedPreferences saveUserPassword = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        username = saveUserPassword.getString("username", "");
    }

    // 通知展示
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
        // notificationActionType==1为Activity，2为url，3为intent
        notific.setNotificationActionType(notifiShowedRlt
                .getNotificationActionType());
        // Activity,url,intent都可以通过getActivity()获得
        notific.setActivity(notifiShowedRlt.getActivity());
        notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(Calendar.getInstance().getTime()));
        NotificationService.getInstance(context).save(notific);
        context.sendBroadcast(intent);
        show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
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
            //传递用户手机过去
            String rString = query(MSG_ID,dp.get("USER_PHONE"),rcvtime);
            Log.d("回执URL的返回：", rString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 定义发送请求的方法
    private String query(String MsgId, String UserName, String Date) throws Exception {
        // 定义发送请求的URL
//        String url = APIUtil.get("XG_RECIVER").replace("%1", MsgId).replace("%2", UserName).replace("%3", Date);
//        Log.d("回执URL：", url);
//        // 发送请求
//        return HttpUtil.getRequest(url);
        //祝鹏辉 TODO: 推送打开界面
        return "";
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            String m="";
            if(Integer.toHexString(c).length()!=4){
                for(int j=0;j<4-Integer.toHexString(c).length();j++){
                    m=m+"0";
                }
            }
            m=m+Integer.toHexString(c);
            // 转换为unicode
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
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
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
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
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
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        int actionType = (int) message.getActionType();
        if (actionType == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作

            //TODO:处理逻辑，根据message.getMsgId()请求同望API
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
                Log.d("mytag", "请求该通知的服务器信息："+jo.toString());
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


            //根据API的返回值判断是沥青还是水稳还是其它，如果是沥青，启动沥青报警处置详情界面

            //如果是水稳，启动水稳报警处置详情界面；

            //非以上两种情况时，则启动报警列表界面。
            Log.d("消息被点击：", "准备启动");
//			Intent intent2 = new Intent("com.shtoone.shtw_liqing_app.PushMessageListActivity");
//			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intent2);

            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;

        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
        Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
                Toast.LENGTH_SHORT).show();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                Log.d("mytag", "keyValues"+obj.toString());
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
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
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        Log.d(LogTag, text);
        show(context, text);
    }

    // 定义请求推送详情的方法
    private JSONObject query_push(String msg_id) throws Exception {
        // 定义发送请求的URL
//        String url = APIUtil.get("XG_CLICK").replace("%1", msg_id);
//        Log.d("推送点击回执URL:", url);
//        // 发送请求
//        String string = HttpUtil.getRequest(url);
//        if (string == null) {
//            return null;
//        }
//        return new JSONObject(string);
        return new JSONObject("");
    }
}
