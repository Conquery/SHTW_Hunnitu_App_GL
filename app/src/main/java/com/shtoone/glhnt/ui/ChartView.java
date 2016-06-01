package com.shtoone.glhnt.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.COM_XY;

import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class ChartView extends DemoView {

    private LineChart chart = new LineChart();
    private LinkedList<String> labels = new LinkedList<String>();
    private LinkedList<LineData> chartData = new LinkedList<LineData>();
    Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int colorTitalAxes = Color.rgb(255, 255, 255);
    private int colorPlotGrean = Color.rgb(127, 255, 0);

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
        chart.getDataAxis().setAxisSteps(step);
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    private String chartTitle = "¡¶÷µ«˙œﬂ";

    private int step = 5;

    public ChartView(Context context) {
        super(context);
        initView(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        colorPlotGrean = colorTitalAxes = context.getResources().getColor(R.color.blueText);
        chartLabels(null);
        chartDataSet(null);
        chartRender();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {

            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            // float margin = DensityUtil.dip2px(getContext(), 20);
            // chart.setXTickMarksOffsetMargin(margin);

            // chart.showRoundBorder();

            chart.setCategories(labels);
            chart.setDataSource(chartData);

            chart.getDataAxis().setAxisMax(100);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(step);
            chart.getCategoryAxis().hideTickMarks();
            chart.getDataAxis().hideTickMarks();

            chart.getDataAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getAxisPaint().setTextSize(9);
            chart.getDataAxis().getTickLabelPaint().setTextSize(12);
            chart.getDataAxis().setTickLabelMargin(40);
            chart.getDataAxis().getTickMarksPaint().setColor(colorTitalAxes);
            chart.getDataAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getDataAxis().setHorizontalTickAlign(Align.LEFT);
            chart.getDataAxis().getTickLabelPaint().setTextAlign(Align.LEFT);

            chart.getCategoryAxis().getAxisPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().setTickLabelMargin(20);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(14);
            chart.getCategoryAxis().getTickLabelPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().getTickMarksPaint().setColor(colorTitalAxes);
            chart.getCategoryAxis().setTickLabelRotateAngle(45f);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);

            chart.addSubtitle(chartTitle);
            chart.getPlotTitle().getSubtitlePaint().setColor(colorTitalAxes);
            chart.getPlotTitle().getSubtitlePaint().setTextSize(16);

            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {
                @Override
                public String textFormatter(String value) {
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });

            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }
            });

            chart.ActiveListenItemClick();
            chart.extPointClickRange(2);
            chart.showClikedFocus();

            chart.disableHighPrecision();
            //chart.showRoundBorder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chartDataSet(List<Double> wendu) {
        if (wendu == null)
            return;

        chartData.clear();

        Double maxDouble = MaxAndMinList(dataSeriesA)[1];
        //Integer step = GetStep((String.valueOf(maxDouble).split(".")[0]).length());
        int step = (int) Math.round(maxDouble);
        chart.getDataAxis().setAxisMax(maxDouble);
        chart.getDataAxis().setAxisSteps(step);

        LineData dataSeries1 = new LineData("∫…‘ÿ(KN)", wendu, colorPlotGrean);
        dataSeries1.getLinePaint().setStrokeWidth(1);
        dataSeries1.getLinePaint().setColor(colorPlotGrean);
        dataSeries1.setDotStyle(XEnum.DotStyle.CROSS);
        dataSeries1.setDotRadius(3);
        chartData.add(dataSeries1);
    }

    private void chartLabels(List<String> lbs) {
        labels.clear();
        if (lbs == null) {
            labels.add("0");
            labels.add("5");
            labels.add("10");
        } else {
            for (int i = 0; i < lbs.size(); i++) {
                if (i % step == 0) {
                    //String[] arrStrings = (lbs.get(i).split(" ")[1]).split(":");
                    //labels.add(arrStrings[0] + ":" + arrStrings[1] + ":" + arrStrings[2]);
                    labels.add(lbs.get(i));
                } else {
                    labels.add("");
                }
            }
        }
    }

    public void Update(List<COM_XY> datalist) {
        chart.setTitle("");
        chartLabels(GetBarDataX(datalist));
        GetLineData(datalist);
        chartDataSet(dataSeriesA);
        this.invalidate();
    }

    private List<Double> dataSeriesA = null;

    private void GetLineData(List<COM_XY> dataList) {
        dataSeriesA = new LinkedList<Double>();

        for (int i = 0; i < dataList.size(); i++) {
            dataSeriesA.add(dataList.get(i).getName2());
        }
    }

    private List<String> GetBarDataX(List<COM_XY> dataList) {
        List<String> dataSeries = new LinkedList<String>();

        for (int i = 0; i < dataList.size(); i++) {
            dataSeries.add(dataList.get(i).getName1());
        }
        return dataSeries;
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
        }
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
}
