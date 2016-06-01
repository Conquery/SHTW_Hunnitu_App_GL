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
    //������Դ
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
        //ͼ��ռ��Χ��С
        chart.setChartRange(w,h);
    }


    private void chartRender()
    {
        try {

            //���û�ͼ��Ĭ������pxֵ,���ÿռ���ʾAxis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //��ʾ�߿�
            //chart.showRoundBorder();

            //����
//            chart.setTitle("������ͳ��(t)");
//            chart.getPlotTitle().getTitlePaint().setColor(colorTitalAxes);
//            chart.getPlotTitle().getTitlePaint().setTextSize(30);
            //chart.addSubtitle("(XCL-Charts Demo)");
            //����Դ
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);
            chart.setCustomLines(mCustomLineDataset);

            //ͼ��
            chart.getAxisTitle().setLeftTitle("����(t)");
            //chart.getAxisTitle().setLowerTitle("ͳ������");
            chart.getAxisTitle().getLeftTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLeftTitlePaint().setTextSize(20);
            chart.getAxisTitle().getLowerTitlePaint().setColor(colorTitalAxes);
            chart.getAxisTitle().getLowerTitlePaint().setTextSize(20);

            //������
            chart.getDataAxis().setAxisMax(99999);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(10000);
            chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getAxisPaint().setTextSize(9);

            //ָ�����ٸ���̶�(��ϸ�̶�)��Ϊ���̶�
            chart.getDataAxis().setDetailModeSteps(2);
            chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);

            //X����
            chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);
            //chart.getCategoryAxis().getAxisPaint().setTextSize(9);

            //��������
            chart.getPlotGrid().showHorizontalLines();

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

            //��ǩ��ת45��
            chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);

            //�����ζ�����ʾֵ
            chart.getBar().setItemLabelVisible(true);
            chart.getBar().getItemLabelPaint().setColor(colorPlotGrean);

            //�趨��ʽ
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //����Key
            chart.getPlotLegend().hide();

            //�����Ӽ�û�հ�
            chart.getBar().setBarInnerMargin(0.1f); //�ɳ���0.1��0.5����ɶЧ����


            //����ƽ��ģʽ
            chart.disablePanMode();
            //�������
            chart.disableHighPrecision();

            //���κͱ�ǩ���з�ʽ
            chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);

            chart.getDataAxis().setAxisLineStyle(XEnum.AxisLineStyle.FILLCAP);
            chart.getCategoryAxis().setAxisLineStyle(XEnum.AxisLineStyle.FILLCAP);
            // chart.showRoundBorder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //����
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
        //��ǩ��Ӧ���������ݼ�
        //List<Double> dataSeriesA= new LinkedList<Double>();
        //������ֵȷ����Ӧ��������ɫ.
        List<Integer> dataColorA= new LinkedList<Integer>();

        //�˵ص���ɫΪKeyֵ��ɫ�����ε�Ĭ����ɫ
        BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
                Color.rgb(0,191,255));

        chartData.add(BarDataA);
    }

    //X������
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
     * ������/�ֽ���
     */
    private void chartDesireLines()
    {
        mCustomLineDataset.add(new CustomLineData("���",18.5d,Color.rgb(77, 184, 73),3));
        mCustomLineDataset.add(new CustomLineData("��",24d,Color.rgb(252, 210, 9),4));
        mCustomLineDataset.add(new CustomLineData("��",27.9d,Color.rgb(171, 42, 96),5));
        mCustomLineDataset.add(new CustomLineData("��",30d,Color.RED,6));

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
