package com.shtoone.glhnt.serviceDao;

/**
 * 用于存放API
 */
public class API {

    /**
     * 更新地址
     */
    public static final String UpdateURL = "http://sp.shtoone.com:8083/nxsy/appVersion/version.xml";

    /**
     * 基础地址
     */
    public static final String BaseURL = "http://sp.shtoone.com:8083/nxsy/";

    /**
     * 登录地址
     */
    public static final String Login_URL = BaseURL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";

    /**
     * 组织结构面板
     */
    public static final String CommZuzhiJiegou = BaseURL + "app.do?AppDepartTree&updateDepartTime=%1&funtype=%2&userGroupId=%3&type=%4";

    /**
     * 主界面地址 TODO
     */
    public static final String Main_URL = BaseURL + "app.do?AppHntMain&departId=%1";

    /**
     * 请求设备列表 1:水泥拌和站 2:沥青拌和站 3:万能机 4:压力机
     */
    public static final String COMM_DEVICE = BaseURL + "app.do?getShebeiList&userGroupId=%1&shebeiType=%2";

    /**
     * 按试验种类获取条目
     */
    public static final String SYS_Items = BaseURL + "sysController.do?sysHome&userGroupId=%1&startTime=%2&endTime=%3";

    /**
     * 拌合站菜单界面
     */
    public static final String Menu_SYS = BaseURL + "sysController.do?sysMainLogo&userGroupId=%1";

    /**
     * 拌合站菜单界面
     */
    public static final String Menu_BHZ = BaseURL + "app.do?hntMainLogo&userGroupId=%1";

    /**
     * 拌合站统计信息
     */
    public static final String BHZ_Lingdao = BaseURL + "app.do?AppHntMain&departId=%1&startTime=%2&endTime=%3";

    /**
     * 混泥土强度列表地址
     */
    public static final String HNT_URL = BaseURL + "sysController.do?hntkangya&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15&testId=%8";

    /**
     * 混泥土强度详情地址
     */
    public static final String HNTXQ_URL = BaseURL + "sysController.do?hntkangyaDetail&SYJID=%1";

    /**
     * 钢筋拉力列表地址
     */
    public static final String GJLL_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 钢筋拉力详情地址
     */
    public static final String GJLLXQ_URL = BaseURL + "sysController.do?gangjinDetail&SYJID=%1";

    /**
     * 钢筋焊接接头列表地址
     */
    public static final String GJHJJT_URL = BaseURL + "sysController.do?gangjinhanjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15&testId=%8";

    /**
     * 钢筋焊接接头详情地址
     */
    public static final String GJHJJTXQ_URL = BaseURL + "sysController.do?gangjinhanjiejietouDetail&SYJID=%1";

    /**
     * 钢筋机械连接接头列表地址
     */
    public static final String GJJXLJJT_URL = BaseURL + "sysController.do?gangjinlianjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15&testId=%8";

    /**
     * 拌合站生产数据查询
     */
    public static final String BHZ_SCDATA_URL = BaseURL + "app.do?AppHntXiangxiList&departId=%1&startTime=%2&endTime=%3&pageNo=%4&shebeibianhao=%5&maxPageItems=15";

    /**
     * 拌合站生产数据详情查询
     */
    public static final String BHZ_SCDATA_DETAIL_URL = BaseURL + "app.do?AppHntXiangxiDetail&bianhao=%1";

    /**
     * 拌合站待处置超标列表查询
     */
    public static final String BHZ_CHAOBIAO_LIST_URL = BaseURL + "app.do?AppHntChaobiaoList&departId=%1&startTime=%2&endTime=%3&dengji=%4&chuzhileixing=%5&pageNo=%6&shebeibianhao=%7&maxPageItems=15";

    /**
     * 拌合站待处置超标详情
     */
    public static final String BHZ_CHAOBIAO_DETAIL_URL = BaseURL + "app.do?AppHntChaobiaoDetail&bianhao=%1&shebeibianhao=%2";

    /**
     * 混凝土超标处置
     */
    public static final String BHZ_CHAOBIAO_DO_URL = BaseURL + "app.do?AppHntChaobiaoChuzhi&jieguobianhao=%1&chaobiaoyuanyin=%2&chuzhifangshi=%3&chuzhijieguo=%4&chuzhiren=%5&chuzhishijian=%6";

    /**
     * 混凝土综合统计分析
     */
    public static final String BHZ_ZONGHT_TJ_URL = BaseURL + "app.do?hntCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&cllx=%5";

    /**
     * 混凝土材料用量
     */
    public static final String BHZ_CAILIAO_URL = BaseURL + "app.do?AppHntMaterial&departId=%1&startTime=%2&endTime=%3&shebeibianhao=%4";

    /**
     * 试验室超标处置
     */
    public static final String SYS_CHAOBIAO_DO_URL = BaseURL + "sysController.do?hntkangyaPost";

    /**
     * 拌合站状态
     */
    public static final String COMM_BHZ_STS = BaseURL + "app.do?AppHntBanhejiState&departId=%1&pageNo=%2&maxPageItems=15";

    /**
     * 拌合站超标审批
     */
    public static final String BHZ_CHAOBIAO_SP = BaseURL + "app.do?AppHntChaobiaoShenpi&jieguobianhao=%1&jianliresult=%2&jianlishenpi=%3&confirmdate=%4&shenpiren=%5&shenpidate=%6";

    /**
     * 试验室设备列表
     */
    public static final String SYS_SHEBEI_LIST = BaseURL + "sysController.do?getSysShebeiList&userGroupId=%1";
    /**
     * 试验室试验类型列表
     */
    public static final String SYS_SHEBEI_TEST_LIST = BaseURL + "sysController.do?getSyLx&testType=%1";

    /**
     * 试验室综合统计分析
     */
    public static final String SYS_TONGJI_FENXI = BaseURL + "sysController.do?sysCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3";

    /**
     * 钢筋列表地址
     */
    public static final String GJ_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15&testId=%8";

    /**
     *压力机试验类型
     */
    public static final String YalijiTestType = "1";

    /**
     *万能机试验类型
     */
    public static final String WangnjTestType = "2";

}
