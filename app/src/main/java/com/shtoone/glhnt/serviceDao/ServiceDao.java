package com.shtoone.glhnt.serviceDao;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shtoone.glhnt.entity.BHZ_CaiLiaoYongLiang_Entity;
import com.shtoone.glhnt.entity.BHZ_CaiLiaoYongLiang_Item;
import com.shtoone.glhnt.entity.BHZ_Cailiao;
import com.shtoone.glhnt.entity.BHZ_ChaobiaoTongji;
import com.shtoone.glhnt.entity.BHZ_ChaobiaoXq;
import com.shtoone.glhnt.entity.BHZ_Lingdao;
import com.shtoone.glhnt.entity.BHZ_SCshujuchaxun_Entity;
import com.shtoone.glhnt.entity.BHZ_SCshujuchaxun_XqEntity;
import com.shtoone.glhnt.entity.BHZ_Status;
import com.shtoone.glhnt.entity.BHZ_ZongheTj;
import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;
import com.shtoone.glhnt.entity.COMM_GangJinTestType;
import com.shtoone.glhnt.entity.COMM_Shebei;
import com.shtoone.glhnt.entity.COM_XY;
import com.shtoone.glhnt.entity.COM_ZuzhiJiegou;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.entity.Menu_BHZ;
import com.shtoone.glhnt.entity.Menu_SYS;
import com.shtoone.glhnt.entity.SC_Detail;
import com.shtoone.glhnt.entity.SC_chaxunItem_xq_data;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_Entity;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_XqEntity;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_xqList;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Entity;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Xq_Entity;
import com.shtoone.glhnt.entity.SYS_HunNiTu_Entity;
import com.shtoone.glhnt.entity.SYS_HunNiTu_Xq_Entity;
import com.shtoone.glhnt.entity.SYS_Item;
import com.shtoone.glhnt.entity.SYS_Lingdao;
import com.shtoone.glhnt.entity.SYS_Lingdao_ly;
import com.shtoone.glhnt.entity.SYS_TongJiFengXi_Entity;
import com.shtoone.glhnt.entity.SYS_TongJiFengXi_Item_Entity;
import com.shtoone.glhnt.entity.SYS_Tongjifenxi;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.HttpUtil;
import com.shtoone.glhnt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    private static final String TAG = "ServiceDao";
    public COMM_Shebei shebeidata;

    /**
     * ��¼��֤
     *
     * @param username �û���
     * @param password ����
     * @return ʧ�ܷ���null �ɹ������û���Ϣ
     * @throws Exception
     */
    public UserInfo loginCheck(String username, String password) throws Exception {
        String url = API.Login_URL.replace("%1", username).replace("%2", password);
        Log.d(TAG, "��¼��֤ :" + url);
        String request = HttpUtil.getRequest(url);
        if (request != null) {
            UserInfo userInfo = new Gson().fromJson(request, UserInfo.class);
            if (userInfo.isSuccess()) {
                return userInfo;
            }
        }
        return null;
    }

    /**
     * ��ȡ����������
     *
     * @param departId ��֯�ṹID
     * @return ʧ�ܷ���null �ɹ�����MainInfo
     * @throws Exception
     */
    public MainInfo getData(String departId) throws Exception {
        MainInfo mainInfo = null;
        String url = API.Main_URL.replace("%1", departId);
        Log.d(TAG, "���������� :" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return mainInfo;
        }
        JSONObject jsonObject = new JSONObject(request);
        if (jsonObject.getBoolean("success")) {
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                mainInfo = new MainInfo();
                mainInfo.setCzpanshu(object.getString("czpanshu"));
                mainInfo.setDepartName(object.getString("departName"));
                mainInfo.setHcblv(object.getString("hcblv"));
                mainInfo.setCbpanshu(object.getString("cbpanshu"));
                mainInfo.setTotalPanshu(object.getString("totalPanshu"));
                mainInfo.setBhjCount(object.getString("bhjCount"));
                mainInfo.setHcbpanshu(object.getString("hcbpanshu"));
                mainInfo.setId(object.getInt("id"));
                mainInfo.setMcblv(object.getString("mcblv"));
                mainInfo.setCzlv(object.getString("czlv"));
                mainInfo.setMcbpanshu(object.getString("mcbpanshu"));
                mainInfo.setCblv(object.getString("cblv"));
                mainInfo.setDepartId(object.getString("departId"));
                mainInfo.setTotalFangliang(object.getString("totalFangliang"));
            }
        }
        return mainInfo;
    }

    /**
     * ��ȡ�豸�б�
     *
     * @param userGroupId
     * @param type
     * @return
     * @throws Exception
     */
    public COMM_Shebei getCOMM_Shebei(String userGroupId, String type, String qufen) throws Exception {
        COMM_Shebei data = null;
        String url = "";
        if (qufen.equals("BHZ")) {
            url = API.COMM_DEVICE.replace("%1", userGroupId).replace("%2", type);
        }else {
            url = API.SYS_SHEBEI_LIST.replace("%1", userGroupId);
        }
        Log.e(TAG, "�豸�б�+++++++++++++++++++++++++++++++++++++ :" + url);
        String request = HttpUtil.getRequest(url);
        Log.e(TAG, "�豸�б�+++++++++++++++++++++++++++++++++++++ :" + request);
        if (request == null) {
            return data;
        } else {
            data = new Gson().fromJson(request, COMM_Shebei.class);
        }
        return data;
    }

/*//    **********************xUtils������******************************

    *//**
     * ��ȡ�豸�б�
     *
     * @param userGroupId
     * @param type
     * @return
     * @throws Exception
     *//*
    public COMM_Shebei getCOMM_Shebei_new(String userGroupId, String type, String qufen, RequestQueue mRequestQueue) throws Exception {
        String url = "";
        if (qufen.equals("BHZ")){
            url = API.COMM_DEVICE.replace("%1", userGroupId).replace("%2", type);
        }else{
            url = API.SYS_SHEBEI_LIST.replace("%1", userGroupId);
        }
        Log.e(TAG, "�豸�б�+++++++++++++++++++++++++++++++++++++ :" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "�豸�б�+++++++++++++++++++++++++++++++++++++ :" + response);
                shebeidata = new Gson().fromJson(response, COMM_Shebei.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(stringRequest);
        Log.e(TAG, "�豸�б�+++++++++++++++++++++++++++++++++++++ :" + shebeidata.toString());
        return shebeidata;
    }
//    ****************************************************/


    /**
     * ��ȡ���������б�
     */
    public COMM_GangJinTestType getCOMM_GangJinTestType(String testType) throws Exception {
        COMM_GangJinTestType data = null;
        String url= API.SYS_SHEBEI_TEST_LIST.replace("%1", testType);
        Log.d(TAG, "�豸�б� :" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return data;
        } else {
            data = new Gson().fromJson(request, COMM_GangJinTestType.class);
        }
        return data;
    }

    /**
     * ��ȡ��֯�ṹ
     *
     * @return
     * @throws Exception
     */
    public COM_ZuzhiJiegou getZuzhijiegou(String updTime, String type, String userGroupID, String userRole) throws Exception {
        updTime = CommFunctions.ChangeTimeToLong(updTime);
        COM_ZuzhiJiegou data = null;
        String url = API.CommZuzhiJiegou.replace("%1", updTime).replace("%2", type).replace("%3", userGroupID).replace("%4", userRole);
        Log.d(TAG, "��ȡ��֯�ṹ :" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return data;
        } else {
            data = new Gson().fromJson(request, COM_ZuzhiJiegou.class);
        }
        return data;
    }

    /**
     * ��ȡ�����Ҳ˵��������ϰ�ť�еĸ�������
     *
     * @param userGroup �û���������֯�ṹID
     * @return
     * @throws Exception
     */
    public SYS_Item getSysItemsData(String userGroup, String startTime, String endTime) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime); //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        SYS_Item data = null;
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.SYS_Items.replace("%1", userGroup).replace("%2", startTime).replace("%3", endTime);
            Log.d(TAG, "���������������� :" + url);
            String request = HttpUtil.getRequest(url);
            if (request == null) {
                return data;
            } else {
                data = new Gson().fromJson(request, SYS_Item.class);
            }
        }
        return data;
    }

    /**
     * ��ȡ���վ�˵��������ϰ�ť�еĸ�������
     *
     * @param userGroup �û���������֯�ṹID
     * @return
     * @throws Exception
     */
    public Menu_SYS getSysMenuData(String userGroup) throws Exception {
        Menu_SYS data = null;
        String url = API.Menu_SYS.replace("%1", userGroup);
        Log.d(TAG, "���վ���������� :" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return data;
        } else {
            data = new Gson().fromJson(request, Menu_SYS.class);
        }
        return data;
    }

    /**
     * ��ȡ���վ�˵��������ϰ�ť�еĸ�������
     *
     * @param userGroup �û���������֯�ṹID
     * @return
     * @throws Exception
     */
    public Menu_BHZ getBhzMenuData(String userGroup) throws Exception {
        Menu_BHZ data = null;
        String url = API.Menu_BHZ.replace("%1", userGroup);
        Log.d(TAG, "���վ���������� :" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return data;
        } else {
            data = new Gson().fromJson(request, Menu_BHZ.class);
        }
        return data;
    }


    /**
     * ���վ����֯�ṹΪ��λ��ͳ������
     *
     * @param userGroup
     * @return
     * @throws Exception
     */
    public BHZ_ChaobiaoTongji getBhzTongjiData(String userGroup, String startTime, String endTime) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);   //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        BHZ_ChaobiaoTongji data = null;
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            //��������
            BHZ_ChaobiaoTongji server_data = new BHZ_ChaobiaoTongji();
            Log.d("userGroup", userGroup);
            String url = API.BHZ_Lingdao.replace("%1", userGroup).replace("%2", startTime).replace("%3", endTime);
            Log.d("���վ����֯�ṹΪ��λ��ͳ������:", url);
            String request = HttpUtil.getRequest(url);
            if (request == null) {
                return data;
            } else {
                data = new Gson().fromJson(request, BHZ_ChaobiaoTongji.class);
            }
        }

        return data;
    }

    /**
     * ���վ�쵼ר�õ���
     *
     * @return
     */
    public List<BHZ_Lingdao> getBhzLingdaoData(String userGroup, String startTime, String endTime) throws Exception {
        Log.d("���վ�쵼ר�õ���:", "��ʼ");
        BHZ_ChaobiaoTongji server_data = this.getBhzTongjiData(userGroup, startTime, endTime);
        List<BHZ_Lingdao> items = null;
        //Log.d("���վ�쵼ר�õ���:",String.valueOf(server_data == null));
        if (server_data.getData().size() > 0) {
            items = new ArrayList<BHZ_Lingdao>();
            for (int i = 0; i < server_data.getData().size(); i++) {
                BHZ_ChaobiaoTongji.DataEntity tmp = server_data.getData().get(i);
                BHZ_Lingdao item = new BHZ_Lingdao();
                item.setDepartId(tmp.getDepartId());
                item.setBiaoduan(tmp.getDepartName());
                item.setBanhezhanCount(tmp.getBhzCount());
                item.setBanhejiCount(tmp.getBhjCount());
                item.setZongpanshu(tmp.getTotalPanshu());
                item.setZongfangliang(tmp.getTotalFangliang());
                item.setChuji1(tmp.getCbpanshu());
                item.setChuji2(tmp.getCblv());
                item.setChuji3(tmp.getCczpanshu());
                item.setChuji4(tmp.getCzlv());
                item.setZhongji1(tmp.getMcbpanshu());
                item.setZhongji2(tmp.getMcblv());
                item.setZhongji3(tmp.getMczpanshu());
                item.setZhongji4(tmp.getMczlv());
                item.setGaoji1(tmp.getHcbpanshu());
                item.setGaoji2(tmp.getHcblv());
                item.setGaoji3(tmp.getHczpanshu());
                item.setGaoji4(tmp.getHczlv());
                items.add(item);
            }
        }
        return items;
    }

    /**
     * �������쵼
     *
     * @return
     */
    public List<SYS_Lingdao> getSysLingdaoData(String userGroupId, String startTime, String endTime) throws Exception {
        SYS_Item sys_tj_list = this.getSysItemsData(userGroupId, startTime, endTime);
        List<SYS_Lingdao> items = null;
        if (sys_tj_list.getData().size() > 0) {
            items = new ArrayList<SYS_Lingdao>();
            for (int i = 0; i < sys_tj_list.getData().size(); i++) {
                boolean notFound = true;
                SYS_Item.DataEntity tmp = sys_tj_list.getData().get(i);
                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j).getDepartId().equals(tmp.getUserGroupId())) {
                        notFound = false;
                        items.get(j).setSysCount(tmp.getSysCount());
                        items.get(j).setSyjCount(tmp.getSyjCount());
                        if (tmp.getTesttype().equals("100014")) {
                            items.get(j).setTongkangya1(tmp.getTestCount());
                            items.get(j).setTongkangya2(tmp.getNotQualifiedCount());
                            items.get(j).setTongkangya3(tmp.getRealCount());
                            items.get(j).setTongkangya4(tmp.getRealPer());
                        } else if (tmp.getTesttype().equals("100047")) {
                            items.get(j).setGangjinlali1(tmp.getTestCount());
                            items.get(j).setGangjinlali2(tmp.getNotQualifiedCount());
                            items.get(j).setGangjinlali3(tmp.getRealCount());
                            items.get(j).setGangjinlali4(tmp.getRealPer());
                        } else if (tmp.getTesttype().equals("100048")) {
                            items.get(j).setGangjinhjjt1(tmp.getTestCount());
                            items.get(j).setGangjinhjjt2(tmp.getNotQualifiedCount());
                            items.get(j).setGangjinhjjt3(tmp.getRealCount());
                            items.get(j).setGangjinhjjt4(tmp.getRealPer());
                        } else {
                            items.get(j).setGangjinjxljjt1(tmp.getTestCount());
                            items.get(j).setGangjinjxljjt2(tmp.getNotQualifiedCount());
                            items.get(j).setGangjinjxljjt3(tmp.getRealCount());
                            items.get(j).setGangjinjxljjt4(tmp.getRealPer());
                        }
                        break;
                    }
                }
                if (notFound) {
                    SYS_Lingdao item = new SYS_Lingdao();
                    item.setDepartId(tmp.getUserGroupId());
                    item.setUserGroup(tmp.getDepartName());
                    item.setSysCount(tmp.getSysCount());
                    item.setSyjCount(tmp.getSyjCount());
                    if (tmp.getTesttype().equals("100014")) {
                        item.setTongkangya1(tmp.getTestCount());
                        item.setTongkangya2(tmp.getNotQualifiedCount());
                        item.setTongkangya3(tmp.getRealCount());
                        item.setTongkangya4(tmp.getRealPer());
                    } else if (tmp.getTesttype().equals("100047")) {
                        item.setGangjinlali1(tmp.getTestCount());
                        item.setGangjinlali2(tmp.getNotQualifiedCount());
                        item.setGangjinlali3(tmp.getRealCount());
                        item.setGangjinlali4(tmp.getRealPer());
                    } else if (tmp.getTesttype().equals("100048")) {
                        item.setGangjinhjjt1(tmp.getTestCount());
                        item.setGangjinhjjt2(tmp.getNotQualifiedCount());
                        item.setGangjinhjjt3(tmp.getRealCount());
                        item.setGangjinhjjt4(tmp.getRealPer());
                    } else {
                        item.setGangjinjxljjt1(tmp.getTestCount());
                        item.setGangjinjxljjt2(tmp.getNotQualifiedCount());
                        item.setGangjinjxljjt3(tmp.getRealCount());
                        item.setGangjinjxljjt4(tmp.getRealPer());
                    }
                    items.add(item);
                }
            }
        }
        return items;
    }


    /**
     * �������쵼
     *
     * @return
     */
    public SYS_Lingdao_ly getSysLingdaoData_ly(String userGroupId, String startTime, String endTime) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);     //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        SYS_Lingdao_ly data = null;
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.SYS_Items.replace("%1", userGroupId).replace("%2", startTime).replace("%3", endTime);
            Log.d(TAG, "���������������� :" + url);
            String request = HttpUtil.getRequest(url);
            Log.e("request", "request:" + request);

            if (request==null) {
                return data;
            } else {
                data = new Gson().fromJson(request, SYS_Lingdao_ly.class);
            }
        }

        return data;
    }


    /**
     * �õ�������ǿ���б�����
     *
     * @param userGroupID    ��֯�ṹID
     * @param isQualified    �Ƿ�ϸ�
     * @param startTime      ��ʼʱ��
     * @param endTime        ����ʱ��
     * @param current_PageNo ��ǰҳ��
     * @return �����б�����
     * @throws Exception
     */
    public SYS_HunNiTu_Entity getHNTQDDataList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal,String testType) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.HNT_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            Log.d(TAG, "������ǿ���б� :" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<SYS_HunNiTu_Entity>() {
                    }.getType();
                    SYS_HunNiTu_Entity item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * �õ�������ǿ����������
     *
     * @param xqID ����ID
     * @return
     * @throws Exception
     */
    public SYS_HunNiTu_Xq_Entity getHNTQDXqData(String xqID) throws Exception {
        Log.e("xqID", "xqID:" + xqID);
        String url = API.HNTXQ_URL.replace("%1", xqID);
        Log.e("url", url);

        Log.d(TAG, "������ǿ������ :" + url);
        String request = HttpUtil.getRequest(url);
        Log.e("request", "request:" + request);


        if (request != null) {
            Type type = new TypeToken<SYS_HunNiTu_Xq_Entity>() {
            }.getType();
            JSONObject jsonObject = new JSONObject(request);
            if (jsonObject.getBoolean("success")) {
                SYS_HunNiTu_Xq_Entity item = new Gson().fromJson(request, type);
                if (item.isSuccess()) {
                    return item;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * �õ��ֽ������б�����
     *
     * @param userGroupID    ��֯�ṹID
     * @param isQualified    �Ƿ�ϸ�
     * @param startTime      ��ʼʱ��
     * @param endTime        ����ʱ��
     * @param current_PageNo ҳ��
     * @return ���������б�����
     */
    public SYS_GangJingLaLi_Entity getGJLLDataList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.GJLL_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal);
            Log.d(TAG, "�ֽ������б� :" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<SYS_GangJingLaLi_Entity>() {
                    }.getType();
                    SYS_GangJingLaLi_Entity item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
//        JSONObject jsonObject = new JSONObject(request);
//        if (jsonObject.getBoolean("success")) { // TODO json.boolean
//            items = new ArrayList<>();
//            JSONArray array = jsonObject.getJSONArray("data");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = array.getJSONObject(i);
//                SYS_GangJingLaLi_Entity item = new SYS_GangJingLaLi_Entity();
//                item.setId(object.getString("SYJID"));
//                item.setTime(object.getString("SYRQ"));
//                item.setSheBeiName(object.getString("banhezhanminchen"));
//                item.setWeituoNum(object.getString("WTID"));
//                item.setHege(object.getString("PDJG"));
//                item.setShijianNum("null TODO");
//                item.setQufuli("null TODO");
//                item.setKanglaqiangdu("null TODO");
//                item.setPingzhong("null TODO");
//                item.setProjectName(object.getString("WTBH"));
//                item.setPartName(object.getString("CJMC"));
//                items.add(item);
//            }
//        }
//        return items;
    }

    /**
     * ��ȡ�ֽ�������������
     *
     * @param xqID ����ID
     * @return
     */
    public SYS_GangJingLaLi_Xq_Entity getGangjinLaliDetail(String xqID) throws Exception {
        SYS_GangJingLaLi_Xq_Entity item = null;
        String url = API.GJLLXQ_URL.replace("%1", xqID);
        Log.d(TAG, "GJLLXQ   " + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return item;
        }
        JSONObject jsonObject = new JSONObject(request);
        if (jsonObject.getBoolean("success")) {
            Type type = new TypeToken<SYS_GangJingLaLi_Xq_Entity>() {
            }.getType();
            item = new Gson().fromJson(request, type);
            if (item.isSuccess()) {
                return item;
            }
        }

        return item;
    }

    /**
     * �õ��ֽ�ӽ�ͷ�б�����
     *
     * @param userGroupID    ��֯�ṹID
     * @param isQualified    �Ƿ�ϸ�
     * @param startTime      ��ʼʱ��
     * @param endTime        ����ʱ��
     * @param current_PageNo ҳ��
     * @return ���غ��ӽ�ͷ�б�����
     */
    public SYS_GangJingLaLi_Entity getGJHJJTDataList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal,String testType) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.GJHJJT_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            Log.d(TAG, "�ֽ�ӽ�ͷ�б� :" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<SYS_GangJingLaLi_Entity>() {
                    }.getType();
                    SYS_GangJingLaLi_Entity item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                }
            }
        }
        return null;
//        JSONObject jsonObject = new JSONObject(request);
//        if (jsonObject.getBoolean("success")) { // TODO json.boolean
//            items = new ArrayList<>();
//            JSONArray array = jsonObject.getJSONArray("data");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = array.getJSONObject(i);
//                SYS_GangJingLaLi_Entity item = new SYS_GangJingLaLi_Entity();
//                item.setId(object.getString("SYJID"));
//                item.setTime(object.getString("SYRQ"));
//                item.setSheBeiName(object.getString("banhezhanminchen"));
//                item.setWeituoNum(object.getString("WTID"));
//                item.setHege(object.getString("PDJG"));
//                item.setShijianNum("null TODO");
//                item.setQufuli("null TODO");
//                item.setKanglaqiangdu("null TODO");
//                item.setPingzhong("null TODO");
//                item.setProjectName(object.getString("WTBH"));
//                item.setPartName(object.getString("CJMC"));
//                items.add(item);
//            }
//        }
//        return items;
    }

    /**
     * ��ȡ�ֽ�ӽ�ͷ��������
     * TODO
     *
     * @param xqID ����ID
     * @return
     */
    public SYS_GangJingLaLi_Xq_Entity getGangJinHanjietouDetail(String xqID) throws Exception {
        SYS_GangJingLaLi_Xq_Entity item = null;
        String url = API.GJHJJTXQ_URL.replace("%1", xqID);
        Log.d(TAG, "�ֽ��ͷ���飺" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return item;
        }
        JSONObject jsonObject = new JSONObject(request);
        if (jsonObject.getBoolean("success")) {
            Type type = new TypeToken<SYS_GangJingLaLi_Xq_Entity>() {
            }.getType();
            item = new Gson().fromJson(request, type);
            if (item.isSuccess()) {
                return item;
            }
        }
//        JSONObject jsonObject = new JSONObject(request);
//        if (jsonObject.getBoolean("success")) {
//        if (false) {
//            JSONObject object = jsonObject.getJSONObject("data");
//            item = new SYS_GangJingLaLi_Xq_Entity();
//            item.setTime(object.getString("SYRQ") == null ? "" : object.getString("SYRQ"));     //��������
//            item.setProName(object.getString("WTBH") == null ? "" : object.getString("WTBH"));  //��������
//            item.setProPart(object.getString("CJMC") == null ? "" : object.getString("CJMC"));  //���̲�λ
//            item.setHege("�ϸ�");                                                               //�Ƿ�ϸ�  TODO
//            item.setWeituoNum("null");                                                          //ί�б��  TODO
//            item.setShijianNum(object.getString("SJBH") == null ? "" : object.getString("SJBH"));//�Լ����
//            item.setQufuli(object.getString("QFLZ") == null ? "" : object.getString("QFLZ"));   //������
//            item.setZhijin(object.getString("GCZJ") == null ? "" : object.getString("GCZJ"));   //ֱ��
//            item.setQufuqiangdu(object.getString("QFQD") == null ? "" : object.getString("QFQD"));//����ǿ��
//            item.setPinzhong("null");                                                           //Ʒ��    TODO
//            item.setKanglali(object.getString("LZ") == null ? "" : object.getString("LZ"));     //������
//            item.setKanglaqiangdu(object.getString("LZQD") == null ? "" : object.getString("LZQD"));//����ǿ��
//            item.setShenchanglv(object.getString("SCL") == null ? "" : object.getString("SCL")); //�쳤��
//        }
//        List<COM_XY> litexys = new ArrayList<COM_XY>();
//        for (int j = 0; j < 20; j++) {
//            COM_XY xy = new COM_XY();
//            xy.setName1("2015-09-1" + j + " " + "12:12:12");
//            xy.setName2(j * j * 1.0);
//            litexys.add(xy);
//        }
//        item.setLists(litexys);
        return null;
    }

    /**
     * �õ��ֽ��е���ӽ�ͷ�б�����
     * TODO
     *
     * @param userGroupID    ��֯�ṹID
     * @param isQualified    �Ƿ�ϸ�
     * @param startTime      ��ʼʱ��
     * @param endTime        ����ʱ��
     * @param current_PageNo ҳ��
     * @return ���غ��ӽ�ͷ�б�����
     */
    public SYS_GangJingLaLi_Entity getGJJXLJJTDataList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal,String testType) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.GJJXLJJT_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            Log.d(TAG, "��е��ͷ�б�" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<SYS_GangJingLaLi_Entity>() {
                    }.getType();
                    SYS_GangJingLaLi_Entity item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                }
            }
        }
        return null;

//        JSONObject jsonObject = new JSONObject(request);
//        if (jsonObject.getBoolean("success")) {
//            items = new ArrayList<>();
//            JSONArray array = jsonObject.getJSONArray("data");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = array.getJSONObject(i);
//                SYS_GangJingLaLi_Entity item = new SYS_GangJingLaLi_Entity();
//                item.setId(object.getString("SYJID"));
//                item.setTime(object.getString("SYRQ"));
//                item.setSheBeiName(object.getString("banhezhanminchen"));
//                item.setWeituoNum(object.getString("WTID"));
//                item.setHege(object.getString("PDJG"));
//                item.setShijianNum("null TODO");
//                item.setQufuli("null TODO");
//                item.setKanglaqiangdu("null TODO");
//                item.setPingzhong("null TODO");
//                item.setProjectName(object.getString("WTBH"));
//                item.setPartName(object.getString("CJMC"));
//                items.add(item);
//            }
//        }
    }

    /**
     * ��ȡ�ֽ��е���ӽ�ͷ��������
     * TODO
     *
     * @param xqID ����ID
     * @return
     */
    public SYS_GangJingLaLi_Xq_Entity getGangjinJixietouDetail(String xqID) throws Exception {
        SYS_GangJingLaLi_Xq_Entity item = null;
        String url = API.GJHJJTXQ_URL.replace("%1", xqID);
        Log.d(TAG, "�ֽ��е���ӽ�ͷ�������飺" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return item;
        }
        JSONObject jsonObject = new JSONObject(request);
        if (jsonObject.getBoolean("success")) {
            Type type = new TypeToken<SYS_GangJingLaLi_Xq_Entity>() {
            }.getType();
            item = new Gson().fromJson(request, type);
            if (item.isSuccess()) {
                return item;
            }
        }
//        JSONObject jsonObject = new JSONObject(request);
////        if (jsonObject.getBoolean("success")) {
//        if (false) {
//            JSONObject object = jsonObject.getJSONObject("data");
//            item = new SYS_GangJingLaLi_Xq_Entity();
//            item.setTime(object.getString("SYRQ") == null ? "" : object.getString("SYRQ"));     //��������
//            item.setProName(object.getString("WTBH") == null ? "" : object.getString("WTBH"));  //��������
//            item.setProPart(object.getString("CJMC") == null ? "" : object.getString("CJMC"));  //���̲�λ
//            item.setHege("�ϸ�");                                                               //�Ƿ�ϸ�  TODO
//            item.setWeituoNum("null");                                                          //ί�б��  TODO
//            item.setShijianNum(object.getString("SJBH") == null ? "" : object.getString("SJBH"));//�Լ����
//            item.setQufuli(object.getString("QFLZ") == null ? "" : object.getString("QFLZ"));   //������
//            item.setZhijin(object.getString("GCZJ") == null ? "" : object.getString("GCZJ"));   //ֱ��
//            item.setQufuqiangdu(object.getString("QFQD") == null ? "" : object.getString("QFQD"));//����ǿ��
//            item.setPinzhong("null");                                                           //Ʒ��    TODO
//            item.setKanglali(object.getString("LZ") == null ? "" : object.getString("LZ"));     //������
//            item.setKanglaqiangdu(object.getString("LZQD") == null ? "" : object.getString("LZQD"));//����ǿ��
//            item.setShenchanglv(object.getString("SCL") == null ? "" : object.getString("SCL")); //�쳤��
//        }
////        List<COM_XY> litexys = new ArrayList<COM_XY>();
////        for (int j = 0; j < 20; j++) {
////            COM_XY xy = new COM_XY();
////            xy.setName1("2015-09-1" + j + " " + "12:12:12");
////            xy.setName2(j * j * 1.0);
////            litexys.add(xy);
////        }
////        item.setLists(litexys);
        return null;
    }


    /**
     * �õ��ֽ������б�����
     *
     * @param userGroupID    ��֯�ṹID
     * @param isQualified    �Ƿ�ϸ�
     * @param startTime      ��ʼʱ��
     * @param endTime        ����ʱ��
     * @param current_PageNo ҳ��
     * @param testType       ҳ��
     * @return ���������б�����
     */
    public SYS_GangJingLaLi_Entity getGJDataList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal, String testType) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);   //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.GJ_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            Log.d(TAG, "�ֽ������б� :" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<SYS_GangJingLaLi_Entity>() {
                    }.getType();
                    SYS_GangJingLaLi_Entity item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }


    /**
     * ��ȡʵ���Ҳ��ϸ����б�
     *
     * @return
     */
    public List<SYS_BuHeGeChuZhi_Entity> getSysBuhegeData(String userGroupId, String startTime, String endTime, String current_PageNo, String isReal, String device,String testType) throws Exception {
        List<SYS_BuHeGeChuZhi_Entity> items = null;
        items = new ArrayList<SYS_BuHeGeChuZhi_Entity>();
        //1.ȡȡ��������ѹǿ�Ȳ��ϸ�����
        SYS_HunNiTu_Entity sys_hntky = this.getHNTQDDataList(userGroupId, "0", startTime, endTime, current_PageNo, device, isReal,testType);
        if (sys_hntky != null) {
            //Log.d("��������ѹǿ�Ȳ��ϸ�",String.valueOf(sys_hntky.getData().size()));
            for (int i = 0; i < sys_hntky.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_HunNiTu_Entity.DataEntity data = sys_hntky.getData().get(i);
                item.setId(data.getSYJID());
                item.setType("��������ѹǿ������");
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }

        //2.ȡ�ֽ��������ϸ�����
        SYS_GangJingLaLi_Entity sys_gjll = this.getGJLLDataList(userGroupId, "0", startTime, endTime, current_PageNo, device, isReal);
        if (sys_gjll != null) {
            //Log.d("�ֽ��������ϸ�",String.valueOf(sys_gjll.getData().size()));
            for (int i = 0; i < sys_gjll.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_GangJingLaLi_Entity.DataEntity data = sys_gjll.getData().get(i);
                item.setId(data.getSYJID());
                item.setType("�ֽ���������");
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }

        //3.ȡ�ֽ�ӽ�ͷ���ϸ�����
        SYS_GangJingLaLi_Entity sys_gjhjjt = this.getGJHJJTDataList(userGroupId, "0", startTime, endTime, current_PageNo, device, isReal,testType);
        if (sys_gjhjjt != null) {
            //Log.d("�ֽ�ӽ�ͷ���ϸ�", String.valueOf(sys_gjhjjt.getData().size()));
            for (int i = 0; i < sys_gjhjjt.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_GangJingLaLi_Entity.DataEntity data = sys_gjhjjt.getData().get(i);
                item.setId(data.getSYJID());
                item.setType("�ֽ�ӽ�ͷ����");
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }

        //4.�ֽ��е���ӽ�ͷ
        SYS_GangJingLaLi_Entity sys_jxljjt = this.getGJJXLJJTDataList(userGroupId, "0", startTime, endTime, current_PageNo, device, isReal,testType);
        if (sys_jxljjt != null) {
            //Log.d("�ֽ��е���ӽ�ͷ���ϸ�", String.valueOf(sys_jxljjt.getData().size()));
            for (int i = 0; i < sys_jxljjt.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_GangJingLaLi_Entity.DataEntity data = sys_jxljjt.getData().get(i);
                item.setId(data.getSYJID());
                item.setType("�ֽ��е���ӽ�ͷ����");
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }

        return items;
    }

    /**
     * ��ȡʵ����ѹ�������ϸ����б�
     *
     * @return
     */
    public List<SYS_BuHeGeChuZhi_Entity> getYalijiSysBuhegeData(String userGroupId, String startTime, String endTime, String current_PageNo, String isReal, String device,String testType) throws Exception {
        List<SYS_BuHeGeChuZhi_Entity> items = new ArrayList<SYS_BuHeGeChuZhi_Entity>();
        //1.ȡȡ��������ѹǿ�Ȳ��ϸ�����
        SYS_HunNiTu_Entity sys_hntky = this.getHNTQDDataList(userGroupId, "3", startTime, endTime, current_PageNo, device, isReal,testType);
        if (sys_hntky != null) {
            //Log.d("��������ѹǿ�Ȳ��ϸ�",String.valueOf(sys_hntky.getData().size()));
            for (int i = 0; i < sys_hntky.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_HunNiTu_Entity.DataEntity data = sys_hntky.getData().get(i);
                item.setId(data.getSYJID());
                item.setType("��������ѹǿ��");
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }

        return items;
    }


    /**
     * ��ȡʵ�������ܻ����ϸ����б�
     *
     * @return
     */
    public List<SYS_BuHeGeChuZhi_Entity> getSysWannengjiBuhegeData(String userGroupId, String startTime, String endTime, String current_PageNo, String isReal, String device,String testType) throws Exception {
        List<SYS_BuHeGeChuZhi_Entity> items = null;
        items = new ArrayList<SYS_BuHeGeChuZhi_Entity>();

        //2.ȡ�ֽ��������ϸ�����
        SYS_GangJingLaLi_Entity sys_gjll = this.getGJDataList(userGroupId, "0", startTime, endTime, current_PageNo, device, isReal, testType);
        if (sys_gjll != null) {
            //Log.d("�ֽ��������ϸ�",String.valueOf(sys_gjll.getData().size()));
            for (int i = 0; i < sys_gjll.getData().size(); i++) {
                SYS_BuHeGeChuZhi_Entity item = new SYS_BuHeGeChuZhi_Entity();
                SYS_GangJingLaLi_Entity.DataEntity data = sys_gjll.getData().get(i);
                item.setId(data.getSYJID());
                item.setType(data.getTestName());
                item.setShijianTime(data.getSYRQ());
                item.setProjectName(data.getGCMC());
                item.setShebeiName(data.getShebeiname());
                item.setShigongPart(data.getSGBW());
                item.setShijianNum(data.getSJBH());
                items.add(item);
            }
        }
        return items;
    }


    /**
     * �õ����ϸ��������б�
     *
     * @param xqID ����ID
     * @return
     */
    public SYS_BuHeGeChuZhi_XqEntity getXqData(String xqID) {
        SYS_BuHeGeChuZhi_XqEntity item = new SYS_BuHeGeChuZhi_XqEntity();
        item.setSysName("WT-XXXXXXXXXXXXXXXXX");
        item.setSyName("NNNNNNNNNNN");
        item.setProName("NNNNNNNNNNN");
        item.setPartName("NNNNNNNNNNN");
        item.setWeituoNum("WT-0000000000000000000000000000");
        item.setShijianNum("SJ-00000000000000000000000");
        item.setShiyanTime("2015-09-21");
        item.setShijianChicun("100*100*100");
        item.setSjQiangdu("C30");
        item.setLingqi("25");
        item.setQdDaibiao("32.2");
        item.setPingzhongNum("NNNNNNNNNNN");
        item.setGuigeZhonglei("NNNNNNNNNNN");
        item.setGongchengZhijing("NNNNNNNNNNN");
        item.setCaozuorenyuan("��ΰ");
        item.setPanduanjieguou("���ϸ�");
        List<SYS_BuHeGeChuZhi_xqList> items = new ArrayList<SYS_BuHeGeChuZhi_xqList>();
        for (int i = 0; i < 3; i++) {
            SYS_BuHeGeChuZhi_xqList x = new SYS_BuHeGeChuZhi_xqList();
            x.setId((i + 1) + "");
            x.setLizhi("22.3");
            x.setQufulizhi("445.4");
            x.setQiangdu("C29");
            items.add(x);
        }
        item.setItems(items);
        return item;
    }

    /**
     * ������ͳ�Ʒ���
     *
     * @return
     */
    public SYS_TongJiFengXi_Entity getSysTongjifenxi() {
        SYS_TongJiFengXi_Entity item = new SYS_TongJiFengXi_Entity();
        List<COM_XY> cishuList = new ArrayList<COM_XY>();
        List<SYS_TongJiFengXi_Item_Entity> leiijcishulist = new ArrayList<SYS_TongJiFengXi_Item_Entity>();
        for (int i = 0; i < 10; i++) {
            COM_XY c = new COM_XY();
            SYS_TongJiFengXi_Item_Entity s = new SYS_TongJiFengXi_Item_Entity();
            c.setName1((i + 1) + "��");
            c.setName2((i + 1) * 1.0);
            cishuList.add(c);
            s.setBiaoduan((i + 1) + "��");
            s.setCishu((i + 1) + "");
            s.setHege((i + 1) + "");
            s.setBuhege((i + 1) + "");
            s.setYouxiao((i + 1) + "");
            s.setHegelv("0");
            leiijcishulist.add(s);
        }
        item.setLeiijcishulist(leiijcishulist);
        item.setLeijicishuList(cishuList);
        return item;
    }

    /**
     * �ӷ������˲�ѯ���վ������������
     *
     * @return
     * @throws Exception
     */
    public SC_Detail getBHZDetailItem(int id) throws Exception {
        String url = API.BHZ_SCDATA_DETAIL_URL.replace("%1", String.valueOf(id));
        Log.d(TAG, "���վ������������:" + url);
        String request = HttpUtil.getRequest(url);
        if (request != null) {
            JSONObject jsonObject = new JSONObject(request);
            if (jsonObject.getBoolean("success")) {
                Gson gson = new GsonBuilder().create();
                SC_Detail item = new Gson().fromJson(request, SC_Detail.class);     //gson.fromJson(request, BHZ_Item.class);
                if (item.isSuccess()) {
                    return item;
                }
            }

        }
        return null;
    }

    /**
     * ��ȡ���վ�������ݲ�ѯ�б�
     *
     * @return
     */
    public List<BHZ_SCshujuchaxun_Entity> getBhzScData(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String shebeiNo) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);   //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        List<BHZ_SCshujuchaxun_Entity> items = null;
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.BHZ_SCDATA_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", current_PageNo).replace("%5", shebeiNo);
            Log.d(TAG, "���վ�������ݲ�ѯ:" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                items = new ArrayList<BHZ_SCshujuchaxun_Entity>();
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    JSONArray datas = jsonObject.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject tmp = datas.getJSONObject(i);
                        BHZ_SCshujuchaxun_Entity item = new BHZ_SCshujuchaxun_Entity();
                        item.setId(tmp.getInt("id"));
                        item.setDeviceName(tmp.getString("banhezhanminchen"));
                        item.setTime(tmp.getString("chuliaoshijian"));
                        item.setmName(tmp.getString("banhezhanminchen"));
                        item.setProName(tmp.getString("gongchengmingcheng"));
                        item.setJiaozhubuwei(tmp.getString("jiaozuobuwei"));
                        item.setDidianlicheng(tmp.getString("sigongdidian"));
                        item.setQiangdudengji(tmp.getString("qiangdudengji"));
                        item.setShuliang(tmp.getString("shuliang"));
                        items.add(item);
                    }
                }
            }
        }

//        BHZ_Item server_data = this.getBHZ_Server_Item(userGroupID,isQualified,startTime,endTime,current_PageNo);
//        List<BHZ_SCshujuchaxun_Entity> items = null;
//        Log.d("���վ��������������",String.valueOf(server_data.getData().size()));
//        if(server_data.getData().size() > 0){
//            items = new ArrayList<>();
//            for (int i = 0; i < server_data.getData().size(); i++) {
//                BHZ_Item.DataEntity tmp = server_data.getData().get(i);
//                BHZ_SCshujuchaxun_Entity item = new BHZ_SCshujuchaxun_Entity();
//                item.setId(tmp.getId());
//                item.setDeviceName(tmp.getShebeiname());
//                item.setTime(tmp.getChuliaoshijian());
//                item.setmName(tmp.getShebeiname());
//                item.setProName(tmp.getGongchengmingcheng());
//                item.setJiaozhubuwei(tmp.getJiaozuobuwei());
//                item.setDidianlicheng(tmp.getSigongdidian());
//                item.setQiangdudengji(tmp.getQiangdudengji());
//                item.setShuliang(tmp.getShuliang());
//                items.add(item);
//            }
//        }
        return items;
    }

    /**
     * �ӷ������˲�ѯ���վ������������
     *
     * @return
     * @throws Exception
     */
    public BHZ_ChaobiaoXq getServerBHZChaobiaoXq(String bianhao, String shebeibianhao) throws Exception {
        String url = API.BHZ_CHAOBIAO_DETAIL_URL.replace("%1", bianhao).replace("%2", shebeibianhao);
        Log.d(TAG, "���վ��������:" + url);
        String request = HttpUtil.getRequest(url);
        if (request != null) {
            JSONObject jsonObject = new JSONObject(request);
            if (jsonObject.getBoolean("success")) {
                Gson gson = new GsonBuilder().create();
                BHZ_ChaobiaoXq item = new Gson().fromJson(request, BHZ_ChaobiaoXq.class);
                if (item.isSuccess()) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * ��ȡ���վ��������
     *
     * @return
     */
    public BHZ_SCshujuchaxun_XqEntity getBhzChaobiaoXqData(String bianhao, String shebeibianhao) throws Exception {
        BHZ_ChaobiaoXq server_data = this.getServerBHZChaobiaoXq(bianhao, shebeibianhao);
        BHZ_SCshujuchaxun_XqEntity item = null;
        if (server_data != null) {
            item = new BHZ_SCshujuchaxun_XqEntity();
            item.setId(server_data.getData().getJieguobianhao());
            item.setShenpidate(server_data.getData().getShenpidate());          //������
            item.setConfirmdate(server_data.getData().getConfirmdate());      //ȷ������
            item.setJianlishenpi(server_data.getData().getJianlishenpi());    //��������
            item.setJianliresult(server_data.getData().getJianliresult());     //������
            item.setFilePath(API.BaseURL + server_data.getData().getFilepath());
            item.setTime(server_data.getData().getChuliaoshijian());
            item.setProName(server_data.getData().getGongchengmingcheng());
            item.setJbsj(server_data.getData().getJiaobanshijian());
            item.setShuliang(server_data.getData().getGujifangshu());
            item.setGdh(server_data.getData().getPeifanghao());
            item.setCaozuozhe(server_data.getData().getChaozuozhe());
            item.setDidian(server_data.getData().getSigongdidian());
            item.setJzbw(server_data.getData().getJiaozuobuwei());
            item.setWjjpz(server_data.getData().getWaijiajipingzhong());
            item.setSnpz(server_data.getData().getShuinipingzhong());
            item.setSgpbbj(server_data.getData().getPeifanghao());
            item.setQddj(server_data.getData().getQiangdudengji());
            item.setChulifangshi(server_data.getData().getChulifangshi());
            item.setChulijieguo(server_data.getData().getChulijieguo());
            item.setWentiyuanyin(server_data.getData().getWentiyuanyin());
            item.setChulishijian(server_data.getData().getChulishijian());
            item.setChuliren(server_data.getData().getChuliren());

            List<SC_chaxunItem_xq_data> items = new ArrayList<SC_chaxunItem_xq_data>();
            SC_chaxunItem_xq_data x = new SC_chaxunItem_xq_data();
            //shuini1
            x.setName(server_data.getHbfield().getShuini1_shijizhi());
            x.setShiji(server_data.getData().getShuini1_shijizhi());
            x.setPeibi(server_data.getData().getShuini1_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw1()));
            x.setCb(String.valueOf(server_data.getData().getFlper1()));
            items.add(x);

            //shuini2
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getShuini2_shijizhi());
            x.setShiji(server_data.getData().getShuini2_shijizhi());
            x.setPeibi(server_data.getData().getShuini2_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw2()));
            x.setCb(String.valueOf(server_data.getData().getFlper2()));
            items.add(x);

            //sha1
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getSha1_shijizhi());
            x.setShiji(server_data.getData().getSha1_shijizhi());
            x.setPeibi(server_data.getData().getSha1_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getGlw1()));
            x.setCb(String.valueOf(server_data.getData().getGlper1()));
            items.add(x);

            //sha2
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getSha2_shijizhi());
            x.setShiji(server_data.getData().getSha2_shijizhi());
            x.setPeibi(server_data.getData().getSha2_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getGlw2()));
            x.setCb(String.valueOf(server_data.getData().getGlper2()));
            items.add(x);

            //shi1
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getShi1_shijizhi());
            x.setShiji(server_data.getData().getShi1_shijizhi());
            x.setPeibi(server_data.getData().getShi1_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getGlw3()));
            x.setCb(String.valueOf(server_data.getData().getGlper3()));
            items.add(x);

            //shi2
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getShi2_shijizhi());
            x.setShiji(server_data.getData().getShi2_shijizhi());
            x.setPeibi(server_data.getData().getShi2_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getGlw4()));
            x.setCb(String.valueOf(server_data.getData().getGlper4()));
            items.add(x);

            //guliao5
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getGuliao5_shijizhi());
            x.setShiji(server_data.getData().getGuliao5_shijizhi());
            x.setPeibi(server_data.getData().getGuliao5_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getGlw5()));
            x.setCb(String.valueOf(server_data.getData().getGlper5()));
            items.add(x);

            //kuangfen3
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getKuangfen3_shijizhi());
            x.setShiji(server_data.getData().getKuangfen3_shijizhi());
            x.setPeibi(server_data.getData().getKuangfen3_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw3()));
            x.setCb(String.valueOf(server_data.getData().getFlper3()));
            items.add(x);

            //Feimeihui4
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getFeimeihui4_shijizhi());
            x.setShiji(server_data.getData().getFeimeihui4_shijizhi());
            x.setPeibi(server_data.getData().getFeimeihui4_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw4()));
            x.setCb(String.valueOf(server_data.getData().getFlper4()));
            items.add(x);

            //Fenliao5
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getFenliao5_shijizhi());
            x.setShiji(server_data.getData().getFenliao5_shijizhi());
            x.setPeibi(server_data.getData().getFenliao5_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw5()));
            x.setCb(String.valueOf(server_data.getData().getFlper5()));
            items.add(x);

            //Fenliao6
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getFenliao6_shijizhi());
            x.setShiji(server_data.getData().getFenliao6_shijizhi());
            x.setPeibi(server_data.getData().getFenliao6_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getFlw6()));
            x.setCb(String.valueOf(server_data.getData().getFlper6()));
            items.add(x);

            //Waijiaji1
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getWaijiaji1_shijizhi());
            x.setShiji(server_data.getData().getWaijiaji1_shijizhi());
            x.setPeibi(server_data.getData().getWaijiaji1_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getWjw1()));
            x.setCb(String.valueOf(server_data.getData().getWjper1()));
            items.add(x);

            //Waijiaji2
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getWaijiaji2_shijizhi());
            x.setShiji(server_data.getData().getWaijiaji2_shijizhi());
            x.setPeibi(server_data.getData().getWaijiaji2_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getWjw2()));
            x.setCb(String.valueOf(server_data.getData().getWjper2()));
            items.add(x);

            //waijiaji3
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getWaijiaji3_shijizhi());
            x.setShiji(server_data.getData().getWaijiaji3_shijizhi());
            x.setPeibi(server_data.getData().getWaijiaji3_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getWjw3()));
            x.setCb(String.valueOf(server_data.getData().getWjper3()));
            items.add(x);

            //waijiaji4
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getWaijiaji4_shijizhi());
            x.setShiji(server_data.getData().getWaijiaji4_shijizhi());
            x.setPeibi(server_data.getData().getWaijiaji4_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getWjw4()));
            x.setCb(String.valueOf(server_data.getData().getWjper4()));
            items.add(x);

            //Shui1
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getShui1_shijizhi());
            x.setShiji(server_data.getData().getShui1_shijizhi());
            x.setPeibi(server_data.getData().getShui1_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getShw1()));
            x.setCb(String.valueOf(server_data.getData().getShper1()));
            items.add(x);

            //shui2
            x = new SC_chaxunItem_xq_data();
            x.setName(server_data.getHbfield().getShui2_shijizhi());
            x.setShiji(server_data.getData().getShui2_shijizhi());
            x.setPeibi(server_data.getData().getShui2_lilunzhi());
            x.setWucha(String.valueOf(server_data.getData().getShw2()));
            x.setCb(String.valueOf(server_data.getData().getShper2()));
            items.add(x);
            item.setItems(items);
        }
        return item;
    }

    /**
     * ��ȡ���վ�������ݲ�ѯ�б�
     *
     * @return
     */
    public List<BHZ_SCshujuchaxun_Entity> getBhzChaobiaoList(String userGroupID, String startTime, String endTime, String dengji, String chuzhileixing, String current_PageNo, String device) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        List<BHZ_SCshujuchaxun_Entity> items = null;
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.BHZ_CHAOBIAO_LIST_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", dengji).replace("%5", chuzhileixing).replace("%6", current_PageNo).replace("%7", device);
            Log.d(TAG, "�������ݲ�ѯ:" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                items = new ArrayList<BHZ_SCshujuchaxun_Entity>();
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    JSONArray datas = jsonObject.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject tmp = datas.getJSONObject(i);
                        BHZ_SCshujuchaxun_Entity item = new BHZ_SCshujuchaxun_Entity();
                        item.setId(tmp.getInt("id"));
                        item.setBianhao(tmp.getString("xinxibianhao"));
                        item.setShebeibianhao(tmp.getString("shebeibianhao"));
                        item.setDeviceName(tmp.getString("banhezhanminchen"));
                        item.setTime(tmp.getString("chuliaoshijian"));
                        item.setmName(tmp.getString("banhezhanminchen"));
                        item.setProName(tmp.getString("gongchengmingcheng"));
                        item.setJiaozhubuwei(tmp.getString("jiaozuobuwei"));
                        item.setDidianlicheng(tmp.getString("sigongdidian"));
                        item.setQiangdudengji(tmp.getString("qiangdudengji"));
                        item.setShuliang(tmp.getString("leiji"));
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    /**
     * ȡ�ð��վ�����������
     *
     * @param userGroupID
     * @param startTime
     * @param endTime
     * @param deviceNo
     * @return
     * @throws Exception
     */
    public BHZ_Cailiao getBhzCailiaoYongliang(String userGroupID, String startTime, String endTime, String deviceNo) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);    //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.BHZ_CAILIAO_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", deviceNo);
            Log.d(TAG, "��������ͳ�ƣ�" + url);
            String request = HttpUtil.getRequest(url);
            if (request != null) {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    Type type = new TypeToken<BHZ_Cailiao>() {
                    }.getType();
                    BHZ_Cailiao item = new Gson().fromJson(request, type);
                    if (item.isSuccess()) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * ���վ��������ͳ��
     *
     * @return
     */
    public BHZ_CaiLiaoYongLiang_Entity getBhzCailiaoyongliang(String userGroupID, String startTime, String endTime, String deviceNo) throws Exception {
        BHZ_CaiLiaoYongLiang_Entity item = new BHZ_CaiLiaoYongLiang_Entity();
        List<COM_XY> itemsChengben = new ArrayList<COM_XY>();
        List<COM_XY> itemsBaifenbi = new ArrayList<COM_XY>();
        List<BHZ_CaiLiaoYongLiang_Item> itemsData = new ArrayList<BHZ_CaiLiaoYongLiang_Item>();
        BHZ_Cailiao server_data = this.getBhzCailiaoYongliang(userGroupID, startTime, endTime, deviceNo);
        if (server_data != null) {
            //BHZ_Cailiao.DataEntity data = server_data.getData();

            /*****************************************************
             * ͨ�����ݹ������������Ա��б�
             ****************************************************/
            BHZ_CaiLiaoYongLiang_Item x = new BHZ_CaiLiaoYongLiang_Item();
            COM_XY x1 = null;
            COM_XY x2 = null;
            BHZ_CaiLiaoYongLiang_Item x3 = null;
            String name = "";
            double wc = 0;
            double wcp = 0;
            double ll = 0;
            double sj = 0;
            String isShow = "0";

            //shuini1
            isShow = server_data.getHntbhzisShow().getShuini1_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShuini1_shijizhi();
                sj = ("".equals(server_data.getData().getShuini1_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShuini1_shijizhi());
                ll = ("".equals(server_data.getData().getShuini1_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShuini1_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //shuini2
            isShow = server_data.getHntbhzisShow().getShuini2_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShuini2_shijizhi();
                sj = ("".equals(server_data.getData().getShuini2_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShuini2_shijizhi());
                ll = ("".equals(server_data.getData().getShuini2_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShuini2_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Sha1
            isShow = server_data.getHntbhzisShow().getSha1_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getSha1_shijizhi();
                sj = ("".equals(server_data.getData().getSha1_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getSha1_shijizhi());
                ll = ("".equals(server_data.getData().getSha1_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getSha1_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Sha2
            isShow = server_data.getHntbhzisShow().getSha2_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getSha2_shijizhi();
                sj = ("".equals(server_data.getData().getSha2_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getSha2_shijizhi());
                ll = ("".equals(server_data.getData().getSha2_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getSha2_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Shi1
            isShow = server_data.getHntbhzisShow().getShi1_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShi1_shijizhi();
                sj = ("".equals(server_data.getData().getShi1_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShi1_shijizhi());
                ll = ("".equals(server_data.getData().getShi1_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShi1_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Shi2
            isShow = server_data.getHntbhzisShow().getShi2_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShi2_shijizhi();
                sj = ("".equals(server_data.getData().getShi2_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShi2_shijizhi());
                ll = ("".equals(server_data.getData().getShi2_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShi2_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Guliao5
            isShow = server_data.getHntbhzisShow().getGuliao5_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getGuliao5_shijizhi();
                sj = ("".equals(server_data.getData().getGuliao5_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getGuliao5_shijizhi());
                ll = ("".equals(server_data.getData().getGuliao5_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getGuliao5_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Kuangfen3
            isShow = server_data.getHntbhzisShow().getKuangfen3_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getKuangfen3_shijizhi();
                sj = ("".equals(server_data.getData().getKuangfen3_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getKuangfen3_shijizhi());
                ll = ("".equals(server_data.getData().getKuangfen3_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getKuangfen3_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Feimeihui4
            isShow = server_data.getHntbhzisShow().getFeimeihui4_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getFeimeihui4_shijizhi();
                sj = ("".equals(server_data.getData().getFeimeihui4_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getFeimeihui4_shijizhi());
                ll = ("".equals(server_data.getData().getFeimeihui4_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getFeimeihui4_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Fenliao5
            isShow = server_data.getHntbhzisShow().getFenliao5_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getFenliao5_shijizhi();
                sj = ("".equals(server_data.getData().getFenliao5_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getFenliao5_shijizhi());
                ll = ("".equals(server_data.getData().getFenliao5_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getFenliao5_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Fenliao6
            isShow = server_data.getHntbhzisShow().getFenliao6_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getFenliao6_shijizhi();
                sj = ("".equals(server_data.getData().getFenliao6_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getFenliao6_shijizhi());
                ll = ("".equals(server_data.getData().getFenliao6_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getFenliao6_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Waijiaji1
            isShow = server_data.getHntbhzisShow().getWaijiaji1_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getWaijiaji1_shijizhi();
                sj = ("".equals(server_data.getData().getWaijiaji1_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji1_shijizhi());
                ll = ("".equals(server_data.getData().getWaijiaji1_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji1_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Waijiaji2
            isShow = server_data.getHntbhzisShow().getWaijiaji2_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getWaijiaji2_shijizhi();
                sj = ("".equals(server_data.getData().getWaijiaji2_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji2_shijizhi());
                ll = ("".equals(server_data.getData().getWaijiaji2_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji2_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Waijiaji3
            isShow = server_data.getHntbhzisShow().getWaijiaji3_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getWaijiaji3_shijizhi();
                sj = ("".equals(server_data.getData().getWaijiaji3_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji3_shijizhi());
                ll = ("".equals(server_data.getData().getWaijiaji3_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji3_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Waijiaji4
            isShow = server_data.getHntbhzisShow().getWaijiaji4_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getWaijiaji4_shijizhi();
                sj = ("".equals(server_data.getData().getWaijiaji4_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji4_shijizhi());
                ll = ("".equals(server_data.getData().getWaijiaji4_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getWaijiaji4_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //Shui1
            isShow = server_data.getHntbhzisShow().getShui1_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShui1_shijizhi();
                sj = ("".equals(server_data.getData().getShui1_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShui1_shijizhi());
                ll = ("".equals(server_data.getData().getShui1_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShui1_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }

            //shui2
            isShow = server_data.getHntbhzisShow().getShui2_shijizhi();
            if ("1".equals(isShow)) {
                x1 = new COM_XY();
                x2 = new COM_XY();
                x3 = new BHZ_CaiLiaoYongLiang_Item();
                name = server_data.getHbfield().getShui2_shijizhi();
                sj = ("".equals(server_data.getData().getShui2_shijizhi())) ? 0 : Double.valueOf(server_data.getData().getShui2_shijizhi());
                ll = ("".equals(server_data.getData().getShui2_lilunzhi())) ? 0 : Double.valueOf(server_data.getData().getShui2_lilunzhi());
                wc = Math.round(sj - ll);
                wcp = Math.round((ll == 0 ? 0 : (sj - ll) / ll * 100));
                wcp = wcp > 100 ? 100 : wcp;
                x1.setName1(name);
                x1.setName2(sj);
                x2.setName1(name);
                x2.setName2(wc);
                x3.setName(name);
                x3.setShiji(String.valueOf(sj));
                x3.setPeibi(String.valueOf(ll));
                x3.setWuchazhi(String.valueOf(wc));
                itemsChengben.add(x1);
                itemsBaifenbi.add(x2);
                itemsData.add(x3);
            }
        }
        item.setItemsBaifenbi(itemsBaifenbi);
        item.setItemsChengben(itemsChengben);
        item.setItemsData(itemsData);
        return item;
    }

    /**
     * �������ۺ�ͳ�Ʒ�����������
     *
     * @param userGroupId
     * @param startTime
     * @param endTime
     * @param shebeibianhao
     * @param cycle
     * @return
     * @throws Exception
     */
    public BHZ_ZongheTj getBhzZongheTjjiServerData(String userGroupId, String startTime, String endTime, String shebeibianhao, String cycle) throws Exception {
        startTime = CommFunctions.ChangeTimeToLong(startTime);   //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            BHZ_ZongheTj data = null;
            String url = API.BHZ_ZONGHT_TJ_URL.replace("%1", userGroupId).replace("%2", startTime).replace("%3", endTime).replace("%4", shebeibianhao).replace("%5", cycle);
            Log.d(TAG, "���վ�ۺ�ͳ�֣�" + url);
            String request = HttpUtil.getRequest(url);
            if (request == null) {
                return data;
            } else {
                JSONObject jsonObject = new JSONObject(request);
                if (jsonObject.getBoolean("success")) {
                    data = new Gson().fromJson(request, BHZ_ZongheTj.class);
                }
            }
            return data;
        }
        return null;
    }

    /**
     * �������ۺ�ͳ�Ʒ���APP��
     *
     * @param userGroupId
     * @param startTime
     * @param endTime
     * @param shebeibianhao
     * @param cycle
     * @return
     * @throws Exception
     */
    public List<BHZ_ZongheTongji_Entity> getBhzZongheTjjiClientData(String userGroupId, String startTime, String endTime, String shebeibianhao, String cycle) throws Exception {
        BHZ_ZongheTj server_data = this.getBhzZongheTjjiServerData(userGroupId, startTime, endTime, shebeibianhao, cycle);
        List<BHZ_ZongheTongji_Entity> data = null;
        if (server_data != null) {
            if (server_data.getData().size() > 0) {
                data = new ArrayList<BHZ_ZongheTongji_Entity>();
                for (int i = 0; i < server_data.getData().size(); i++) {
                    BHZ_ZongheTongji_Entity item = new BHZ_ZongheTongji_Entity();
                    BHZ_ZongheTj.DataEntity tmp = server_data.getData().get(i);
                    item.setDate(tmp.getXa() + "-" + tmp.getXb());
                    item.setPanshu(Double.valueOf(tmp.getPangshu()));
                    item.setChangliang(Double.valueOf(tmp.getGujifangshu()));
                    item.setPrimaryps(Double.valueOf(tmp.getLowcbps()));
                    item.setPrimarylv(Double.valueOf(tmp.getLowcbper()));
                    item.setMiddleps(Double.valueOf(tmp.getMidcbps()));
                    item.setMiddlelv(Double.valueOf(tmp.getMidcbper()));
                    item.setHighps(Double.valueOf(tmp.getHighcbps()));
                    item.setHighlv(Double.valueOf(tmp.getHighcbper()));
                    data.add(item);
                }
            }
            return data;
        }
        return null;
    }

    /**
     * ��ȡ���վͨѶ״̬
     *
     * @param userGroupId
     * @param current_PageNo
     * @return
     * @throws Exception
     */
    public BHZ_Status getBhzStatus(String userGroupId, String current_PageNo) throws Exception {
        BHZ_Status data = null;
        String url = API.COMM_BHZ_STS.replace("%1", userGroupId).replace("%2", current_PageNo);
        Log.d(TAG, "���վ״̬��" + url);
        String request = HttpUtil.getRequest(url);
        if (request == null) {
            return data;
        } else {
            JSONObject jsonObject = new JSONObject(request);
            data = new Gson().fromJson(request, BHZ_Status.class);
            if (!data.isSuccess())
                return null;
        }
        return data;
    }

    /**
     * ������ͳ�Ʒ���
     *
     * @param userGroupId
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public SYS_Tongjifenxi getSYS_Tongjifenxi(String userGroupId, String startTime, String endTime) throws Exception {
        SYS_Tongjifenxi data = null;
        startTime = CommFunctions.ChangeTimeToLong(startTime);     //�����ʼʱ����ڽ���ʱ�䣬����null
        endTime = CommFunctions.ChangeTimeToLong(endTime);
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = API.SYS_TONGJI_FENXI.replace("%1", userGroupId).replace("%2", startTime).replace("%3", endTime);
            Log.d(TAG, "ʵ����ͳ�Ʒ�����" + url);
            String request = HttpUtil.getRequest(url);
            if (request == null) {
                return data;
            } else {
                data = new Gson().fromJson(request, SYS_Tongjifenxi.class);
                if (!data.isSuccess())
                    return null;
            }
        }
        return data;
    }

    private String IsNull(String value) {
        if ("".equals(value) || null == value) {
            return "0";
        }else {
            return value;
        }
    }
}
