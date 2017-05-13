package cn.zmhappy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;

public class ErlangB {

	static final int WIDTH=600;
	static final int HEIGHT=300;
	
	public static void main(String[] args) {
		
		
		JFrame jf = new JFrame("ErlangB Calculator");	//新建框架，设置参数
		jf.setSize(WIDTH, HEIGHT);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		JPanel contentPane = new JPanel();	//新建内容放置区域
		jf.setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0,3,5,5));	//设置布局，每行3列，行列之间间距为5单位
		
		JLabel name_a = new JLabel("BHT(Erl.)(a)");	//新建标签
		JLabel name_b = new JLabel("Blocking(b)");
		JLabel name_s = new JLabel("Lines(s)");
		contentPane.add(name_a);
		contentPane.add(name_b);
		contentPane.add(name_s);
		
		final JRadioButton jr_a = new JRadioButton("unknown");	//新建单选按钮
		final JRadioButton jr_b = new JRadioButton("unknown");
		final JRadioButton jr_s = new JRadioButton("unknown");
		jr_b.setSelected(true);		//设置为默认选择
		ButtonGroup bg = new ButtonGroup();		//添加到按钮组中，保证同一时刻只有一个按钮被选上
		bg.add(jr_a);
		bg.add(jr_b);
		bg.add(jr_s);
		contentPane.add(jr_a);
		contentPane.add(jr_b);
		contentPane.add(jr_s);
		
		final JTextField input_a = new JTextField(15);	//新建文本框
		final JTextField input_b = new JTextField(15);
		final JTextField input_s = new JTextField(15);
		contentPane.add(input_a);
		contentPane.add(input_b);
		contentPane.add(input_s);
		
		JButton calculate = new JButton("计算");		//新建计算button
		contentPane.add(calculate);
		JButton history = new JButton("过往计算结果");
		contentPane.add(history);
		
		calculate.addActionListener(new ActionListener(){	//事件监听
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jr_a.isSelected()){
					float b = Float.parseFloat(input_b.getText());
					int s = Integer.parseInt(input_s.getText());
					input_a.setText(erlangb_a(b, s)+"");
				}else if(jr_b.isSelected()){
					float a = Float.parseFloat(input_a.getText());
					int s = Integer.parseInt(input_s.getText());
					input_b.setText(erlangb_b(a, s)+"");
				}else if(jr_s.isSelected()){
					float a = Float.parseFloat(input_a.getText());
					float b = Float.parseFloat(input_b.getText());
					input_s.setText(erlangb_s(a, b)+"");
				}
			}
		});
		
		jf.pack();
		
		
		
		/*
		 * 折线图的实现
		 */
		/*
		// 绘图数据集  
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();  
//        dataSet.addValue(1, "2", "3");
//        dataSet.addValue(2, "2", "4");
//        dataSet.addValue(3, "2", "5");
        
        
        for(int i = 0; i < 20; i++){
        	dataSet.addValue(erlangb_s(i, 0.25), "0.25", i+"");
        	dataSet.addValue(erlangb_s(i, 0.5), "0.5", i+"");
        	dataSet.addValue(erlangb_s(i, 0.75), "0.75", i+"");
        }
            
        JFreeChart chart = ChartFactory.createLineChart("B给定情况下，s随a的变化曲线", "BHT(Erl.)(a)", "Lines(s)", dataSet,  
                PlotOrientation.VERTICAL, // 绘制方向  
                true, // 显示图例  
                true, // 采用标准生成器  
                false // 是否生成超链接  
                );  
        
        //获取绘图区对象  
        CategoryPlot plot = chart.getCategoryPlot();  
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // 设置绘图区背景色  
        plot.setRangeGridlinePaint(Color.WHITE); // 设置水平方向背景线颜色  
        plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true  
        plot.setDomainGridlinePaint(Color.WHITE); // 设置垂直方向背景线颜色  
        plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false  
        
        CategoryAxis domainAxis = plot.getDomainAxis();     
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));		//设置标题字体 
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离  
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。  
        domainAxis.setMaximumCategoryLabelLines(2);  
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题  
        domainAxis.setTickLabelFont(new Font("黑体",Font.BOLD,14));  //垂直标题  
        ValueAxis rangeAxis=plot.getRangeAxis();		//获取垂直
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数  
        rangeAxis.setAutoRangeMinimumSize(1);   //最小跨度  
        rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。     
        rangeAxis.setLowerBound(0);   //最小值显示0  
        rangeAxis.setAutoRange(false);   //不自动分配Y轴数据  
        rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));     // 设置坐标标记大小  
        rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色  
		
        ChartPanel frame1 = new ChartPanel(chart,true);
        JFrame frame = new JFrame("Java数据统计图"); 
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(frame1);
        frame.pack();
        
        */
        
//		System.out.println(erlangb_b(12, 20));
		
	}
	
	
	//x!
	public static long factorial(int x){
		long sum = 1;
		for(int i = 1; i <= x; i++){
			sum *= i;
		}
		return sum;
	}
	
	//a,s-->b
		public static double erlangb_b(double a, int s){
			double b = 0, temp = 0; 
			for(int r = 0; r <= s; r++){
				temp = temp + Math.pow(a, r) / factorial(r);
			}
			b = Math.pow(a, s) / factorial(s);
			System.out.println("temp_b = "+b);
			b = b / temp;
			System.out.println("erlangb_b("+a+", "+s+") = "+b);
			return b;
		}
		
		//a,b-->s
		public static int erlangb_s(double a, double b){
			int s = 0;
			while(erlangb_b(a,s) > b){
				s++;
			}
			return s-1;
		}
		
		//b,s-->a
		public static double erlangb_a(double b, int s){
			double a = 0, a_about = s/(1-b);
			double step = 1.0, b_get;
			b_get = erlangb_b(a_about, s);
			
			while(step>0.000001 || b_get<b){
				if(b_get>b){
					a_about -= step;
				}else{
					step = step / 2;
					a_about += step;
				}
				b_get = erlangb_b(a_about, s);
//				System.out.println(b_get+" = erlangb_b("+a_about+", "+s+")");
			}
			
			return a_about;
		}

	
	

}
