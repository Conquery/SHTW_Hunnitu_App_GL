package com.shtoone.glhnt.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_zongchanliang_cl_chart extends DemoView implements Runnable  {
    private int colorTitalAxes = Color.rgb(99, 180, 242);
    private int colorPlotGrean = Color.rgb(99, 180, 242);

    private String TAG = "BHZ_zongchanliang_cl_chart";
    private BarChart chart = new BarChart();
    //轴数据源
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();
    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    public BHZ_zongchanliang_cl_chart(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public BHZ_zongchanliang_cl_chart(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public BHZ_zongchanliang_cl_chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels(null);
        //chartDataSet();
        //chartDesireLines();
        chartRender();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w,h);
    }


    private void chartRender()
    {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //显示边框
            //chart.showRoundBorder();

            //标题
//            chart.setTitle("生产量统计(t)");
//            chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
//            chart.getPlotTitle().getTitlePaint().setTextSize(30);
            //chart.addSubtitle("(XCL-Charts Demo)");
            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);
            chart.setCustomLines(mCustomLineDataset);

            //图例
            chart.getAxisTitle().setLeftTitle("产量(t)");
            //chart.getAxisTitle().setLowerTitle("统计区间");
            chart.getAxisTitle().getLeftTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLeftTitlePaint().setTextSize(20);
            chart.getAxisTitle().getLowerTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLowerTitlePaint().setTextSize(20);

            //数据轴
            chart.getDataAxis().setAxisMax(99999);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(10000);
            chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getAxisPaint().setTextSize(9);

            //指隔多少个轴刻度(即细刻度)后为主刻度
            chart.getDataAxis().setDetailModeSteps(2);
            chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            //X方向
            chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);
            //chart.getCategoryAxis().getAxisPaint().setTextSize(9);

            //背景网格
            chart.getPlotGrid().showHorizontalLines();

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });

            //标签旋转45度
            chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);

            //在柱形顶部显示值
            chart.getBar().setItemLabelVisible(true);
            chart.getBar().getItemLabelPaint().setColor(colorPlotGrean);

            //设定格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //隐藏Key
            chart.getPlotLegend().hide();

            //让柱子间没空白
            chart.getBar().setBarInnerMargin(0.1f); //可尝试0.1或0.5各有啥效果噢


            //禁用平移模式
            chart.disablePanMode();
            //提高性能
            chart.disableHighPrecision();

            //柱形和标签居中方式
            chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);

            chart.getDataAxis().setAxisLineStyle(XEnum.AxisLineStyle.FILLCAP);
            chart.getCategoryAxis().setAxisLineStyle(XEnum.AxisLineStyle.FILLCAP);
            // chart.showRoundBorder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //更新
    public void Update(List<BHZ_ZongheTongji_Entity> dataSeriesA){
        chartLabels(GetBarDataX(dataSeriesA));
        chartDataSet(GetBarData(dataSeriesA));
        this.invalidate();
    }

    private void chartDataSet(List<Double> dataSeriesA)
    {
        chartData.clear();
        Double maxDouble = MaxAndMinList(dataSeriesA)[1];
        //Integer step = GetStep((String.valueOf(maxDouble).split(".")[0]).length());
        chart.getDataAxis().setAxisMax(maxDouble);
        //chart.getDataAxis().setAxisSteps(step);
        //标签对应的柱形数据集
        //List<Double> dataSeriesA= new LinkedList<Double>();
        //依数据值确定对应的柱形颜色.
        List<Integer> dataColorA= new LinkedList<Integer>();

        //此地的颜色为Key值颜色及柱形的默认颜色
        BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
                Color.rgb(0,191,255));

        chartData.add(BarDataA);
    }

    //X轴设置
    private void chartLabels(List<String> data)
    {
        chartLabels.clear();
        if(data== null){
            for(int i=1;i<31;i++)
            {
                if(1 == i || i%5 == 0)
                {
                    chartLabels.add(String.valueOf(i));
                }else{
                    chartLabels.add("");
                }
            }
        }else{
            for(int i=0;i<data.size();i++)
            {
                chartLabels.add(data.get(i));
            }
        }
    }

    /**
     * 期望线/分界线
     */
    private void chartDesireLines()
    {
        mCustomLineDataset.add(new CustomLineData("最高",18.5d,Color.rgb(77, 184, 73),3));
        mCustomLineDataset.add(new CustomLineData("高",24d,Color.rgb(252, 210, 9),4));
        mCustomLineDataset.add(new CustomLineData("中",27.9d,Color.rgb(171, 42, 96),5));
        mCustomLineDataset.add(new CustomLineData("低",30d,Color.RED,6));

    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public List<XChart> bindChart() {
        // TODO Auto-generated method stub
        List<XChart> lst = new ArrayList<XChart>();
        lst.add(chart);
        return lst;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    private List<Double> GetBarData(List<BHZ_ZongheTongji_Entity> dataList){
        List<Double> dataSeriesA= new LinkedList<Double>();

        for(int i=0;i<dataList.size();i++)
        {
            dataSeriesA.add(dataList.get(i).getChangliang());
        }
        return dataSeriesA;
    }

    private List<String> GetBarDataX(List<BHZ_ZongheTongji_Entity> dataList){
        List<String> dataSeriesA= new LinkedList<String>();

        for(int i=0;i<dataList.size();i++)
        {
            dataSeriesA.add(dataList.get(i).getDate());
        }
        return dataSeriesA;
    }

    public Double[] MaxAndMinList(List<Double> dataSeriesA){
        Double max = dataSeriesA.get(0);
        Double min = dataSeriesA.get(0);
        for (int i = 0; i < dataSeriesA.size(); i++) {
            if (min > dataSeriesA.get(i)) min = dataSeriesA.get(i);
            if (max < dataSeriesA.get(i)) max = dataSeriesA.get(i);
        }
        return new Double[]{min,max};
    }

    public Integer GetStep(Integer a){
        String data0 = "1";
        if(a > 1){
            for(int i=0; i<a; i++){
                data0 = data0 + "0";
            }
        }
        return Integer.valueOf(data0);
    }
}
