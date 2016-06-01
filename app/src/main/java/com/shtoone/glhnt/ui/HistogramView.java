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

import com.shtoone.glhnt.entity.COM_XY;

/**
 * Created by Administrator on 2015/11/22.
 */
public class HistogramView extends DemoView {
    private int colorTitalAxes = Color.rgb(99, 180, 242);
    private int colorPlotArea = Color.rgb(144, 227, 254);
    private int colorPlotGrean = Color.rgb(99, 180, 242);

    public void setMinY(int minY) {
        this.minY = minY;
    }

    private int minY = 0;

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    private int maxY = 100;

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    private int stepY = 5;
    private String title = "";

    public void setColorPlotArea(int color) {
        colorPlotArea = color;
    }

    public void setTitle(String title) {
        this.title = title;
        chart.addSubtitle(title);
    }

    private BarChart chart = new BarChart();
    //轴数据源
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();
    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    public HistogramView(Context context) {
        super(context);
        initView();
    }

    public HistogramView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyle) {
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
            chart.setTitle("");
            chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
            chart.getPlotTitle().getTitlePaint().setTextSize(20);
            //chart.addSubtitle("(XCL-Charts Demo)");
            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);
            chart.setCustomLines(mCustomLineDataset);

            //图例
//			chart.getAxisTitle().setLeftTitle("cishu(t)");
            //chart.getAxisTitle().setLowerTitle("统计区间");
            chart.getAxisTitle().getLeftTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLeftTitlePaint().setTextSize(14);
            chart.getAxisTitle().getLowerTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLowerTitlePaint().setTextSize(14);

            //数据轴
            chart.getDataAxis().setAxisMax(maxY);
            chart.getDataAxis().setAxisMin(minY);
            chart.getDataAxis().setAxisSteps(stepY);
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
//			chart.getPlotGrid().showHorizontalLines();

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
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
            e.printStackTrace();
        }
    }

    //更新
    public void Update(List<COM_XY> dataSeriesA){
        chartLabels(GetBarDataX(dataSeriesA));
        chartDataSet(GetBarData(dataSeriesA));
        this.invalidate();
    }

    private void chartDataSet(List<Double> dataSeriesA)
    {
        if (dataSeriesA.size() > 0) {
            chartData.clear();
            Double maxDouble = MaxAndMinList(dataSeriesA)[1];
            //Integer step = GetStep((String.valueOf(maxDouble).split(".")[0]).length());
            int step = (int) Math.round(maxDouble);
            chart.getDataAxis().setAxisMax(maxDouble);
            chart.getDataAxis().setAxisSteps(step/10);
            //标签对应的柱形数据集
            //List<Double> dataSeriesA= new LinkedList<Double>();
            //依数据值确定对应的柱形颜色.
            List<Integer> dataColorA= new LinkedList<Integer>();

            //此地的颜色为Key值颜色及柱形的默认颜色 TODO
            BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
                    colorPlotArea);

            chartData.add(BarDataA);
        }
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

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        }
    }

    @Override
    public List<XChart> bindChart() {
        List<XChart> lst = new ArrayList<XChart>();
        lst.add(chart);
        return lst;
    }

    private List<Double> GetBarData(List<COM_XY> dataList){
        List<Double> dataSeriesA= new LinkedList<Double>();

        for(int i=0;i<dataList.size();i++)
        {
            dataSeriesA.add(dataList.get(i).getName2());
        }
        return dataSeriesA;
    }

    private List<String> GetBarDataX(List<COM_XY> dataList){
        List<String> dataSeriesA= new LinkedList<String>();

        for(int i=0;i<dataList.size();i++)
        {
            dataSeriesA.add(dataList.get(i).getName1());
        }
        return dataSeriesA;
    }

    public Double[] MaxAndMinList(List<Double> dataSeriesA){
        if(dataSeriesA.size() == 0)
            return new Double[]{0.00,100.00};

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
