package com.shtoone.glhnt.ui;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_zongchanliang_wc_chart extends DemoView  {
    private String TAG = "BHZ_zongchanliang_wc_chart";
    private LineChart chart = new LineChart();
    //分类轴标签集合
    private LinkedList<String> labels = new LinkedList<String>();
    private LinkedList<LineData> chartData = new LinkedList<LineData>();
    Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int colorTitalAxes = Color.rgb(99, 180, 242);
    private int colorPlotGrean = Color.rgb(99, 180, 242);
    private int colorPlotYellow = Color.rgb(255, 153, 0);
    private int colorPlotRed = Color.rgb(255, 106, 106);


    public BHZ_zongchanliang_wc_chart(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public BHZ_zongchanliang_wc_chart(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public BHZ_zongchanliang_wc_chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels(null);
        chartDataSet(null,null,null);
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

            //平移时收缩下
            //float margin = DensityUtil.dip2px(getContext(), 20);
            //chart.setXTickMarksOffsetMargin(margin);

            //显示边框
            //chart.showRoundBorder();

            //数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);

            //坐标系
            //数据轴最大值
            chart.getDataAxis().setAxisMax(100);
            //chart.getDataAxis().setAxisMin(0);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(10);

            //标签轴最大值
//			chart.setCategoryAxisMax(10);
//			//标签轴最小值
//			chart.setCategoryAxisMin(0);

            //设置图的背景色
            //chart.setBackgroupColor(true,Color.BLACK);
            //设置绘图区的背景色
            //chart.getPlotArea().setBackgroupColor(true, Color.WHITE);

            //背景网格
//			PlotGrid plot = chart.getPlotGrid();
//			plot.showHorizontalLines();
//			plot.showVerticalLines();
//			plot.getHorizontalLinePaint().setStrokeWidth(3);
//			plot.getHorizontalLinePaint().setColor(Color.rgb(127, 204, 204));
//			plot.setHorizontalLineStyle(XEnum.LineStyle.DOT);


            //把轴线设成和横向网络线一样和大小和颜色,演示下定制性，这块问得人较多
            //chart.getDataAxis().getAxisPaint().setStrokeWidth(
            //		plot.getHorizontalLinePaint().getStrokeWidth());
            //chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
            //		plot.getHorizontalLinePaint().getStrokeWidth());

            //图例
            chart.getAxisTitle().setLeftTitle("百分比(%)");
            chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);

            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);

            chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            //居中
            chart.getDataAxis().setHorizontalTickAlign(Align.LEFT);
            chart.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);


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


            //定义线上交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //标题
//            chart.setTitle("超标率走势(%)");
//            chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
//            chart.getPlotTitle().getTitlePaint().setTextSize(30);
            //chart.addSubtitle("(XCL-Charts Demo)");

            //激活点击监听
            chart.ActiveListenItemClick();
            //为了让触发更灵敏，可以扩大5px的点击监听范围
            chart.extPointClickRange(2);
            chart.showClikedFocus();

            //标签旋转45度
            chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);


            //显示十字交叉线
            //chart.showDyLine();
            //chart.getDyLine().setDyLineStyle(XEnum.DyLineStyle.Vertical);
            //扩大实际绘制宽度
            //chart.getPlotArea().extWidth(500.f);

            //封闭轴
            //chart.setAxesClosed(true);

            //将线显示为直线，而不是平滑的
            //chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEZIERCURVE);

            //不使用精确计算，忽略Java计算误差,提高性能
            chart.disableHighPrecision();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }
    private void chartDataSet(List<Double> diBaojing,List<Double> zhongBaojing,List<Double> gaoBaojing)
    {
        if(diBaojing == null)
            return;

        chartData.clear();

        LineData dataSeries1 = new LineData("初级",diBaojing,
                colorPlotGrean );
        //把线弄细点
        dataSeries1.getLinePaint().setStrokeWidth(3);
        dataSeries1.getLinePaint().setColor(colorPlotGrean);
        dataSeries1.setLabelVisible(true);
        dataSeries1.setDotStyle(XEnum.DotStyle.DOT);

        //线2的数据集
        LineData dataSeries2 = new LineData("中级",zhongBaojing,
                colorPlotYellow );
        //把线弄细点
        dataSeries2.getLinePaint().setStrokeWidth(3);
        dataSeries2.getLinePaint().setColor(colorPlotYellow);
        dataSeries2.setLabelVisible(true);
        dataSeries2.setDotStyle(XEnum.DotStyle.DOT);

        //线3的数据集
        LineData dataSeries3 = new LineData("高级",gaoBaojing,
                colorPlotRed );

        //把线弄细点
        dataSeries3.getLinePaint().setStrokeWidth(3);
        dataSeries3.getLinePaint().setColor(colorPlotRed);
        dataSeries3.setLabelVisible(true);
        dataSeries3.setDotStyle(XEnum.DotStyle.DOT);

        chartData.add(dataSeries1);
        chartData.add(dataSeries2);
        chartData.add(dataSeries3);
    }

    //设置X坐标
    private void chartLabels(List<String> lbs)
    {
        labels.clear();
        if(lbs == null){
            labels.add("0");
            labels.add("5");
            labels.add("10");
        }else{
            for(int i=0;i<lbs.size();i++){
                labels.add(lbs.get(i));
            }
        }
    }

    //更新
    public void Update(List<BHZ_ZongheTongji_Entity> datalist){
        chartLabels(GetBarDataX(datalist));
        GetLineData(datalist);
        chartDataSet(dataSeriesA,dataSeriesB,dataSeriesC);
        this.invalidate();
    }

    private List<Double> dataSeriesA= null;
    private List<Double> dataSeriesB= null;
    private List<Double> dataSeriesC= null;

    private void GetLineData(List<BHZ_ZongheTongji_Entity> dataList){
        dataSeriesA= new LinkedList<Double>();
        dataSeriesB= new LinkedList<Double>();
        dataSeriesC= new LinkedList<Double>();
        for(int i=0;i<dataList.size();i++)
        {
            dataSeriesA.add(dataList.get(i).getPrimarylv());
            dataSeriesB.add(dataList.get(i).getMiddlelv());
            dataSeriesC.add(dataList.get(i).getHighlv());
        }
    }

    private List<String> GetBarDataX(List<BHZ_ZongheTongji_Entity> dataList){
        List<String> dataSeries= new LinkedList<String>();

        for(int i=0;i<dataList.size();i++)
        {
            dataSeries.add(dataList.get(i).getDate());
        }
        return dataSeries;
    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }
}
