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
    //�������ǩ����
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
        //ͼ��ռ��Χ��С
        chart.setChartRange(w,h);
    }


    private void chartRender()
    {
        try {

            //���û�ͼ��Ĭ������pxֵ,���ÿռ���ʾAxis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //ƽ��ʱ������
            //float margin = DensityUtil.dip2px(getContext(), 20);
            //chart.setXTickMarksOffsetMargin(margin);

            //��ʾ�߿�
            //chart.showRoundBorder();

            //����Դ
            chart.setCategories(labels);
            chart.setDataSource(chartData);

            //����ϵ
            //���������ֵ
            chart.getDataAxis().setAxisMax(100);
            //chart.getDataAxis().setAxisMin(0);
            //������̶ȼ��
            chart.getDataAxis().setAxisSteps(10);

            //��ǩ�����ֵ
//			chart.setCategoryAxisMax(10);
//			//��ǩ����Сֵ
//			chart.setCategoryAxisMin(0);

            //����ͼ�ı���ɫ
            //chart.setBackgroupColor(true,Color.BLACK);
            //���û�ͼ���ı���ɫ
            //chart.getPlotArea().setBackgroupColor(true, Color.WHITE);

            //��������
//			PlotGrid plot = chart.getPlotGrid();
//			plot.showHorizontalLines();
//			plot.showVerticalLines();
//			plot.getHorizontalLinePaint().setStrokeWidth(3);
//			plot.getHorizontalLinePaint().setColor(Color.rgb(127, 204, 204));
//			plot.setHorizontalLineStyle(XEnum.LineStyle.DOT);


            //��������ɺͺ���������һ���ʹ�С����ɫ,��ʾ�¶����ԣ�����ʵ��˽϶�
            //chart.getDataAxis().getAxisPaint().setStrokeWidth(
            //		plot.getHorizontalLinePaint().getStrokeWidth());
            //chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
            //		plot.getHorizontalLinePaint().getStrokeWidth());

            //ͼ��
            chart.getAxisTitle().setLeftTitle("�ٷֱ�(%)");
            chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);

            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);

            chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            //����
            chart.getDataAxis().setHorizontalTickAlign(Align.LEFT);
            chart.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);


            //�����������ǩ��ʾ��ʽ
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


            //�������Ͻ�����ǩ��ʾ��ʽ
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //����
//            chart.setTitle("����������(%)");
//            chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
//            chart.getPlotTitle().getTitlePaint().setTextSize(30);
            //chart.addSubtitle("(XCL-Charts Demo)");

            //����������
            chart.ActiveListenItemClick();
            //Ϊ���ô�������������������5px�ĵ��������Χ
            chart.extPointClickRange(2);
            chart.showClikedFocus();

            //��ǩ��ת45��
            chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);


            //��ʾʮ�ֽ�����
            //chart.showDyLine();
            //chart.getDyLine().setDyLineStyle(XEnum.DyLineStyle.Vertical);
            //����ʵ�ʻ��ƿ��
            //chart.getPlotArea().extWidth(500.f);

            //�����
            //chart.setAxesClosed(true);

            //������ʾΪֱ�ߣ�������ƽ����
            //chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEZIERCURVE);

            //��ʹ�þ�ȷ���㣬����Java�������,�������
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

        LineData dataSeries1 = new LineData("����",diBaojing,
                colorPlotGrean );
        //����Ūϸ��
        dataSeries1.getLinePaint().setStrokeWidth(3);
        dataSeries1.getLinePaint().setColor(colorPlotGrean);
        dataSeries1.setLabelVisible(true);
        dataSeries1.setDotStyle(XEnum.DotStyle.DOT);

        //��2�����ݼ�
        LineData dataSeries2 = new LineData("�м�",zhongBaojing,
                colorPlotYellow );
        //����Ūϸ��
        dataSeries2.getLinePaint().setStrokeWidth(3);
        dataSeries2.getLinePaint().setColor(colorPlotYellow);
        dataSeries2.setLabelVisible(true);
        dataSeries2.setDotStyle(XEnum.DotStyle.DOT);

        //��3�����ݼ�
        LineData dataSeries3 = new LineData("�߼�",gaoBaojing,
                colorPlotRed );

        //����Ūϸ��
        dataSeries3.getLinePaint().setStrokeWidth(3);
        dataSeries3.getLinePaint().setColor(colorPlotRed);
        dataSeries3.setLabelVisible(true);
        dataSeries3.setDotStyle(XEnum.DotStyle.DOT);

        chartData.add(dataSeries1);
        chartData.add(dataSeries2);
        chartData.add(dataSeries3);
    }

    //����X����
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

    //����
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
