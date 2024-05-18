/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hit.java.exp4.hit1170300910;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author think
 */
public class GUIFileBMI {

    //主函数
    public static void main(String[] args) {
        GUIFileBMI y = new GUIFileBMI();
        y.menu();//调用menu函数
    }

//checkHealth函数：判断胖瘦健康状况
    private String checkHealth(float bmi) {
        if (bmi < 18.5) {
            return "Underweight";//未达到正常体重
        }
        if (bmi >= 18.5 && bmi <= 23) {
            return "Normal Range";//正常体重
        }
        if (bmi > 23 && bmi <= 25) {
            return "Overweight-At Risk";
        }
        if (bmi > 25 && bmi <= 30) {
            return "Overweight-Moderately Obese";
        } else {
            return "Overweight-Severely Obese";

        }
    }

    class Student {

        String id;//学号
        String name;//姓名
        float height;//身高
        float weight;//体重
        float bmi;//BMI指数

        public Student(String id, String name, float height, float weight) {/*构造方法*/
            this.id = id;//将参数的值赋给实例
            this.name = name;
            this.height = height;
            this.weight = weight;
            bmi = Float.parseFloat(String.format("%.2f", weight / (height * height)));//计算BMI
        }

        public Student() {

        }

        /*toString函数：返回一个字符串，该字符串包含学号、姓名、身高、体重、bmi值和胖瘦健康状况*/
        //@Override
        public String toString() {
            return String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%s", id, name, height, weight, bmi, checkHealth(bmi));
        }
    }
    ArrayList<Student> students = new ArrayList<Student>();

    private static String randString(String s, int len) {//随机生成字符串
        StringBuilder ans = new StringBuilder();//初始化ans
        for (int i = 1; i <= len; i++) {//逐位随机产生字符
            char c = s.charAt((int) (Math.random() * s.length()));
            ans.append(c);//连接字符串
        }
        return ans.toString();//返回字符串
    }

    //randlr函数：随机生成浮点数
    private static float randlr(float l, float r) {
        return (float) (Math.random() * (r - l) + l);
    }

    //genSudents函数：随机生成指定数量的名学生对象，并保存到students数组中
    private void genStudents(int num) {
        for (int i = 0; i < num; i++) {//循环产生num个学生信息
            String id = randString("1234567890", 10);
            while (!isExists(id)) {//判断是否与已产生学号重复
                id = randString("1234567890", 10);//若重复，重新产生
            }
            String name = randString("abcdefghijklmnopqrstuvwxyz", 3);/*产生3位学生姓名*/
            float height = Float.parseFloat(String.format("%.2f", randlr(1.5f,
                    2.0f)));//随机生成1.5-2米的身高，保留两位小数存储
            float weight = Float.parseFloat(String.format("%.2f", randlr(40.0f,
                    120.0f)));//随机生成40-120kg的体重，保留两位小数存储
            students.add(new Student(id, name, height, weight));//将随机生成的学生对象保存到ArrayList<Student> students中
        }
    }

    /*isExsists函数:判断该学生是否已经在students数组中，函数返回值为boolean类型，如果已经存在，返回false；否则，返回true*/
    public boolean isExists(String id) {
        for (Student s : students) {
            if (s != null) {
                if (id.equals(s.id)) {
                    return false;
                }
            }
        }
        return true;
    }

    //判断是否继续输入
    private boolean continueInput() {
        System.out.print("Do you want to continue to enter?(y/n)");
        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.next();
            if (s.equals("y")) {//若用户输入y
                return true;
            } else if (s.equals("n")) {//若用户输入n
                return false;
            } else {
                System.out.print("请输入一个有效符号(y/n)：");
            }
        }
    }

    //bmiaverage函数：统计bmi的均值
    private float bmiaverage() {
        float average;//平均值
        float sum = 0;//bmi和
        for (Student s : students) {
            sum += s.bmi;//逐个相加
        }
        average = Float.parseFloat(String.format("%.2f",
                sum / students.size()));//计算平均值，并保留2位小数
        return average;//返回平均值
    }

    //bmimid函数：统计bmi的中值
    private float bmimid() {
        Collections.sort(students, new sortBybmi());
        int x = students.size() / 2;
        float mid;//中值
        if (students.size() % 2 == 0) {//若有偶数个学生
            mid = (students.get(x - 1).bmi + students.get(x).bmi) / 2;/*中值等于两个中位数的平均数*/
            return Float.parseFloat(String.format("%.2f",
                    mid));
        } else {
            return Float.parseFloat(String.format("%.2f",
                    students.get(x).bmi));/*中值等于中位数*/
        }
    }

    //bmimode：统计bmi的众数
    private float[] bmimode() {
        float[] mode = new float[students.size()];//众数数组
        int[] flag = new int[students.size()];//有几个学生的bmi值跟第i个一样
        int max = 1;//flag中最大的值
        for (int i = 0; i < students.size(); i++) {
            flag[i] = 0;//将flag初始化为0
            mode[i] = 0;//将众数初始化为0
        }
        for (int i = 0; i < students.size(); i++) {//外层循环
            for (int j = 0; j < students.size(); j++) {
                if (students.get(i).bmi == students.get(j).bmi) {/*若两者相等，则flag++*/
                    flag[i]++;
                }
            }//循环结束后，flag[i]为bmi值跟第i个一样的学生个数（包括第i个）
        }
        for (int i = 0; i < students.size(); i++) {
            if (max < flag[i]) {//若max的值小于flag[i],则将flag[i]的值赋给max
                max = flag[i];
            }
        }//循环结束后，max为flag中最大的值
        if (max == 1) {//没有众数,返回null
            return null;
        } else {
            int j = 0;//众数的个数
            for (int i = 0; i < students.size(); i++) {
                if (flag[i] == max) {//若第i个BMI为众数
                    if (mode[j] != students.get(i).bmi) {//去掉重复的bmi
                        j++;//赋给下一个mode
                        mode[j] = students.get(i).bmi;//将该bmi值赋给众数
                    }
                }
            }//循环结束后，众数数组赋好值
            return mode;//返回数组
        }
    }

    //bmivariance函数：统计bmi的方差
    private float bmivariance() {
        float variance;//方差
        float average = bmiaverage();//得到平均值
        float sum = 0;//方差和
        for (int i = 0; i < students.size(); i++) {
            sum += Math.pow(students.get(i).bmi - average, 2);//累加方差和
        }
        variance = Float.parseFloat(String.format("%.2f",
                sum / students.size()));;//计算方差，保留2位小数
        return variance;//返回方差
    }

    /*printStatics函数：首先打印所有学生基本信息，然后打印bmi的均值、中值、众数、方差等统计结果信息*/
    private String mode() {
        float[] mode = bmimode();
        String m = "";
        if (mode == null) {//判断是否存在众数
            return "不存在众数！";
        } else {
            for (int i = 0; i < students.size(); i++) {
                if (mode[i] != 0) {//若众数不为0，则输出
                    m = m + mode[i] + "\t";//连接为字符串
                }
            }
            return m;
        }
    }

    //按照学号升序排序
    public class sortByid implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.id.compareTo(s2.id);//若s1小于s2，则返回-1
        }
    }

    /*按照学生姓名的字典顺序
          对学生从小到大进行升序排序*/
    public class sortByname implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.name.compareTo(s2.name);//若s1大于s2，则返回1
        }
    }

    //按照身高升序进行排序
    public class sortByheight implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.height > s2.height ? 1 : -1;//若s1身高大于s2，则返回1
        }
    }

    //按照体重升序进行排序(升序)
    public class sortByweight implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.weight > s2.weight ? 1 : -1;//若s1体重小于等于s2，返回-1
        }
    }

    //comparator子类sortBybmi：按照学生BMI值由小到大排序
    public class sortBybmi implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            return s1.bmi > s2.bmi ? 1 : -1;//若s1小于等于s2，则返回-1
        }
    }

    //printStudents函数：打印排序前和排序后的结果
    private void printStudents() {
        for (Student s : students) {
            System.out.printf(s.id + "\t"
                    + s.name + "\t" + "%.2f" + "\t" + "%.2f" + "\t"
                    + "%.2f" + "\t" + checkHealth(s.bmi) + "\n", s.height,
                    s.weight, s.bmi);//输出学生信息
        }
    }

    /*将所有学生信息以逗号表的形式写入到当前目录下1170300910.txt文件中，每一行写入一个学生*/
    public void saveFile(ArrayList<Student> students, String filename) {
        try {
            BufferedWriter writer
                    = new BufferedWriter(new FileWriter(filename, false));/*将字符流写入文本，缓冲字符，以便有效地写入字符，数组和行;调用BufferedWriter(Writer out) ，创建使用默认大小的输出缓冲区的缓冲字符输出流*/

            for (Student st : students) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f,%s\r\n",
                        st.id, st.name, st.height, st.weight, st.bmi, checkHealth(st.bmi)));//把参数所指的内存写入到参数所指的文件内
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
        }
    }

    //读文件中的数据到学生students中
    public ArrayList<Student> readFile(String fileName) {
        File file = new File(fileName);//通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例
        BufferedReader reader = null;
        ArrayList<Student> v = new ArrayList<Student>();//用来接收文件中的数据
        try {
            reader = new BufferedReader(new FileReader(file));
            /*调用public FileReader(String fileName)构造方法：
            如果指定的文件不存在，则是目录而不是常规文件，
            或者由于某些其他原因无法打开进行读取；
            调用Public BufferedReader(Reader in)：创建一个缓冲字符输入流，
            该流使用具有指定Reader对象的默认大小的输入缓冲区*/
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {//调用readLine()：读取一个文本行
                String[] a = tempString.split(",");//split() 方法用于把一个字符串分割成字符串数组，长度为6
                Student st = new Student(a[0], a[1], Float.parseFloat(a[2]), Float.parseFloat(a[3]));//调用Student参数为4个的构造器
                v.add(st);//添加到v
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    //学生信息主界面mainPanel为默认界面，该界面可从文件中读取并显示所有学生信息，并可以按不同属性对学生排序显示。该界面还可随机生成200名学生，并保存到文件中
    private class mainPanel extends JPanel {

        public mainPanel() {
            this.setLayout(null);//自己决定在画布的什么位置上放添加的标准图形元素，它们的大小是多少
            this.setPreferredSize(new Dimension(1000, 600));//设置JPanel的大小
            JTextArea ta = new JTextArea("", 20, 60);//20行40列
            JScrollPane sp = new JScrollPane(ta);//新建滚动窗格
            sp.setBounds(5, 5, 500, 300);//设置滚动窗格大小与位置
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            this.add(sp);//添加滚动窗格
            JButton read = new JButton("从文件中读取并显示所有学生信息");
            read.setBounds(700, 100, 100, 50);//设置按钮大小与位置
            JButton id = new JButton("按学号排序");
            id.setBounds(700, 160, 100, 50);
            JButton name = new JButton("按姓名排序");
            name.setBounds(700, 220, 100, 50);
            JButton height = new JButton("按身高排序");
            height.setBounds(700, 280, 100, 50);
            JButton weight = new JButton("按体重排序");
            weight.setBounds(700, 340, 100, 50);
            JButton bmi = new JButton("按BMI值排序");
            bmi.setBounds(700, 400, 100, 50);
            JButton generate = new JButton("随机生成200名学生，并保存到文件中");
            generate.setBounds(700, 460, 100, 50);
            this.add(read);
            this.add(generate);
            this.add(id);
            this.add(name);
            this.add(height);
            this.add(weight);
            this.add(bmi);//添加按钮
            //添加事件监听器
            read.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    students = readFile("1170300910.txt");//存入students
                    printStudent(ta);//显示
                }
            });
            id.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    Collections.sort(students, new sortByid());//按学号排序
                    saveFile(students, "1170300910.txt");
                    printStudent(ta);//显示
                }
            });
            name.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    Collections.sort(students, new sortByname());//按姓名排序
                    saveFile(students, "1170300910.txt");
                    printStudent(ta);//显示
                }
            });
            height.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    Collections.sort(students, new sortByheight());//按身高排序
                    saveFile(students, "1170300910.txt");
                    printStudent(ta);//显示
                }
            });
            weight.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    Collections.sort(students, new sortByweight());//按体重排序
                    saveFile(students, "1170300910.txt");
                    printStudent(ta);//显示
                }
            });
            bmi.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    Collections.sort(students, new sortBybmi());//按BMI排序
                    saveFile(students, "1170300910.txt");
                    printStudent(ta);//显示
                }
            });
            generate.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    genStudents(200);//随机生成200名学生添加到students
                    saveFile(students, "1170300910.txt");//保存到文件中
                }
            });

        }
    }

    //在文本框中显示所有学生信息
    public void printStudent(JTextArea ta) {
        ta.setText("");//先清空
        for (Student st : students) {
            ta.append(st.toString() + "\r\n");//逐行打印学生信息
        }
    }

    //学生信息输入界面：每次输入单个学生，保存后，提示用户是否成功保存或提示用户已经存在，请重新输入
    private class inputPanel extends JPanel {

        public inputPanel() {
            this.setLayout(null);//自己决定在画布的什么位置上放添加的标准图形元素，它们的大小是多少
            this.setPreferredSize(new Dimension(1000, 600));//设置JPanel的大小
            JLabel label = new JLabel("请输入学生信息：");//提示信息
            label.setBounds(0, 0, 200, 25);//设置标签的位置和大小
            JLabel id = new JLabel("学号：");
            id.setBounds(0, 25, 200, 25);
            JLabel name = new JLabel("姓名：");
            name.setBounds(0, 50, 200, 25);
            JLabel height = new JLabel("身高（m）：");
            height.setBounds(0, 75, 200, 25);
            JLabel weight = new JLabel("体重（kg）：");
            weight.setBounds(0, 100, 200, 25);
            JTextField i = new JTextField();//用来输入学号的文本框
            i.setBounds(200, 25, 200, 25);
            JTextField n = new JTextField();
            n.setBounds(200, 50, 200, 25);
            JTextField h = new JTextField();
            h.setBounds(200, 75, 200, 25);
            JTextField w = new JTextField();
            w.setBounds(200, 100, 200, 25);
            JButton save = new JButton("保存");
            save.setBounds(200, 125, 200, 25);
            this.add(label);//添加标签
            this.add(id);
            this.add(name);
            this.add(height);
            this.add(weight);
            this.add(i);
            this.add(n);
            this.add(h);
            this.add(w);
            this.add(save);
            save.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    String id = i.getText();//将学号保存至id变量
                    String name = n.getText();
                    float height = Float.parseFloat(h.getText());//将String类型强转为float
                    float weight = Float.parseFloat(w.getText());//将String类型强转为float
                    Student s = new Student(id, name, height, weight);//调用Student构造方法
                    if (!isExists(s.id)) {
                        Dialog fail = new Dialog("用户已经存在，请重新输入！");//如已存在弹出提示
                        fail.setVisible(true);//使控件可以显示出来

                    } else {
                        students.add(s);//将s添加至students
                        saveFile(students, "1170300910.txt");//保存到文件
                        Dialog succeed = new Dialog("成功保存！");//弹出提示
                        succeed.setVisible(true);
                    }
                }
            });
        }
    }

    private class Dialog extends JDialog {//弹出对话框

        private Dialog(String information) {
            super();//继承JDialog的constructor
            Container container = getContentPane();//获取内容窗格
            JLabel label = new JLabel(information);//新建标签，显示information的信息
            container.add(label);//将标签添加到容器中
            setBounds(900, 400, 400, 200);//设定位置与大小
        }
    }

    //学生信息维护界面：输入要维护的学生学号，按学号查询并显示该学生所有信息，可删除或修改学生信息，并提示用户修改/删除成功或失败
    private class maintainPanel extends JPanel {

        public maintainPanel() {
            this.setLayout(null);//自己决定在画布的什么位置上放添加的标准图形元素，它们的大小是多少
            this.setPreferredSize(new Dimension(1000, 600));//设置JPanel的大小
            JLabel label = new JLabel("输入要维护的学生学号:");//提示信息
            label.setBounds(0, 0, 200, 25);//设置标签的位置和大小
            JTextField i = new JTextField();//用来输入学号的文本框
            i.setBounds(200, 0, 200, 25);
            JButton search = new JButton("查询");//点击后ta输出该学生信息
            search.setBounds(400, 0, 200, 25);//设置按钮的位置与大小
            JTextArea ta = new JTextArea("");
            ta.setBounds(0, 25, 1000, 25);
            JButton delete = new JButton("删除该学生");
            delete.setBounds(0, 50, 200, 25);
            JLabel name = new JLabel("新姓名：");
            name.setBounds(0, 75, 200, 25);
            JLabel height = new JLabel("新身高（m）：");
            height.setBounds(0, 100, 200, 25);
            JLabel weight = new JLabel("新体重（kg）：");
            weight.setBounds(0, 125, 200, 25);
            JTextField n = new JTextField();//用来输入姓名的文本框
            n.setBounds(200, 75, 200, 25);
            JTextField h = new JTextField();
            h.setBounds(200, 100, 200, 25);
            JTextField w = new JTextField();
            w.setBounds(200, 125, 200, 25);
            JButton modification = new JButton("修改该学生信息");//从4个text field中录入
            modification.setBounds(0, 150, 200, 25);
            this.add(label);//添加标签
            this.add(i);//添加用来输入学号的文本框
            this.add(search);
            this.add(ta);
            this.add(delete);
            this.add(name);
            this.add(height);
            this.add(weight);
            this.add(n);
            this.add(h);
            this.add(w);
            this.add(modification);
            //search的事件监听器
            search.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    String temp;
                    temp = i.getText();//存储学生学号
                    students = readFile("1170300910.txt");//先读取
                    if (!isExists(temp)) {
                        for (Student i : students) {
                            if (i.id.equals(temp)) {
                                ta.setText(i.toString());//如果存在，显示到文本框
                            }
                        }
                    } else {
                        Dialog error = new Dialog("该学生不存在！");//不存在，弹出提示
                        error.setVisible(true);
                    }
                }
            });
            //delete的事件监听器
            delete.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    String temp;
                    temp = i.getText();//存储学生学号
                    students = readFile("1170300910.txt");//先读取
                    if (!isExists(temp)) {
                        Iterator<Student> iter = students.iterator();//调用迭代器
                        while (iter.hasNext()) {//当有输入数据
                            Student i = iter.next();
                            if (i.id.equals(temp)) {
                                iter.remove();//删除Student i
                            }
                        }
                        saveFile(students, "1170300910.txt");//保存
                        Dialog succeed = new Dialog("删除成功！");
                        succeed.setVisible(true);
                    } else {
                        Dialog d = new Dialog("删除失败：该学生不存在！");//不存在，弹出提示
                        d.setVisible(true);
                    }
                }
            });
            //modification的事件监听器
            modification.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    String id;
                    id = i.getText();//存储学生学号
                    String name = n.getText();//从文本框中读取新的姓名
                    float height = Float.parseFloat(h.getText());//将String类型强转为float
                    float weight = Float.parseFloat(w.getText());//将String类型强转为float
                    students = readFile("1170300910.txt");//先读取
                    if (!isExists(id)) {
                        Iterator<Student> iter = students.iterator();//调用迭代器
                        while (iter.hasNext()) {//当有输入数据
                            Student i = iter.next();
                            if (i.id.equals(id)) {
                                iter.remove();//删除Student i
                            }
                        }
                        Student s = new Student(id, name, height, weight);//调用Student构造方法
                        students.add(s);//将s添加至students
                        saveFile(students, "1170300910.txt");//保存到文件
                        Dialog succeed = new Dialog("修改成功！");//弹出提示
                        succeed.setVisible(true);
                    } else {
                        Dialog d = new Dialog("修改失败：该学生不存在！");
                        d.setVisible(true);
                    }
                }
            });
        }
    }

    //BMI统计界面：显示bmi的相关统计信息（均值、中值、众数、方差），并将bmi值的范围由小到大划分的10个均分区间，统计每个区间的学生人数，并绘制出相应的柱状图
    private class bmiPanel extends JPanel {

        public bmiPanel() {
            this.setLayout(null);//自己决定在画布的什么位置上放添加的标准图形元素，它们的大小是多少
            this.setPreferredSize(new Dimension(1000, 600));
            JButton print = new JButton("显示bmi的相关统计信息");
            print.setBounds(0, 0, 200, 25);
            JTextArea ta = new JTextArea("");//20行40列,用来显示统计信息
            ta.setBounds(0, 25, 1000, 200);
            this.add(ta);//添加文本框
            this.add(print);
            //print的事件监听器
            print.addActionListener(new ActionListener() {//设置事件，读取并打印
                public void actionPerformed(ActionEvent e) {
                    students = readFile("1170300910.txt");
                    ta.setText("");//先清空文本域
                    ta.append("平均数：" + bmiaverage() + "\n" + "中位数："
                            + bmimid() + "\n" + "众数：" + mode() + "\n"
                            + "方差：" + bmivariance());//打印均值、中值、众数、方差
                    if (students.size() > 0) {
                        SetDataSet();
                        CreateFreeChart();
                    }
                }
            });
        }
    }

    //创建统计图
    public void CreateFreeChart() {
        CategoryDataset dataset = SetDataSet();
        JFreeChart chart = ChartFactory.createBarChart(//创建一个chart
                "BMI柱状图",
                "区间",
                "各区间人数",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        processChart(chart);//处理表格中的中文
        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();//以下为设置柱状图格式
        categoryPlot.setForegroundAlpha(0.8f);
        categoryPlot.setBackgroundPaint(new Color(255, 255, 255));

        BarRenderer barRenderer = new BarRenderer();
        barRenderer.setMaximumBarWidth(40);
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setItemMargin(0.2D);
        categoryPlot.setRenderer(barRenderer);
        ChartFrame Frame = new ChartFrame("BMI柱状图", chart);//创建新的frame显示
        Frame.pack();
        Frame.setVisible(true);
    }

    public CategoryDataset SetDataSet()//设置要进行制表的数据集
    {
        students = readFile("1170300910.txt");//先读取
        int[] s = new int[10];//频数数组
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();//创建dataset
        Collections.sort(students, new sortBybmi());//排序
        float min = students.get(0).bmi;
        float max = students.get(students.size() - 1).bmi;
        float interval = (max - min) / 10;//除以10，获得间隔
        for (int i = 0; i < 10; i++) {
            s[i] = 0;
        }
        for (Student e : students)//遍历，根据bmi分类，并生成频数数组
        {

            float data = e.bmi;
            if (data >= min && data < min + interval) {
                s[0]++;
            } else if (data >= min + interval && data < min + 2 * interval) {
                s[1]++;
            } else if (data >= min + 2 * interval && data < min + 3 * interval) {
                s[2]++;
            } else if (data >= min + 3 * interval && data < min + 4 * interval) {
                s[3]++;
            } else if (data >= min + 4 * interval && data < min + 5 * interval) {
                s[4]++;
            } else if (data >= min + 5 * interval && data < min + 6 * interval) {
                s[5]++;
            } else if (data >= min + 6 * interval && data < min + 7 * interval) {
                s[6]++;
            } else if (data >= min + 7 * interval && data < min + 8 * interval) {
                s[7]++;
            } else if (data >= min + 8 * interval && data < min + 9 * interval) {
                s[8]++;
            } else {
                s[9]++;
            }
        }
        //添加数据
        dataset.addValue(s[0], "BMI区间1", "1");
        dataset.addValue(s[1], "BMI区间2", "2");
        dataset.addValue(s[2], "BMI区间3", "3");
        dataset.addValue(s[3], "BMI区间4", "4");
        dataset.addValue(s[4], "BMI区间5", "5");
        dataset.addValue(s[5], "BMI区间6", "6");
        dataset.addValue(s[6], "BMI区间7", "7");
        dataset.addValue(s[7], "BMI区间8", "8");
        dataset.addValue(s[8], "BMI区间9", "9");
        dataset.addValue(s[9], "BMI区间10", "10");

        return dataset;
    }

    public void processChart(JFreeChart chart)//处理表格中的中文
    {
        //读取字体栏
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis rAxis = plot.getRangeAxis();
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        TextTitle textTitle = chart.getTitle();
        //设置字体
        textTitle.setFont(new Font("宋体", Font.PLAIN, 20));
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        rAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
    }

    //menu函数：可通过菜单切换显示4个不同界面
    public void menu() {
        JFrame frame = new JFrame();//创建画架
        frame.setLayout(null);
        frame.setSize(1000, 600);//设置frame属性
        JMenuBar bar = new JMenuBar();//创建菜单条
        JMenu menu = new JMenu("菜单");//创建菜单
        JMenuItem main = new JMenuItem("学生信息主界面");//创建菜单项main
        JMenuItem input = new JMenuItem("学生信息输入界面");
        JMenuItem maintain = new JMenuItem("学生信息维护界面");
        JMenuItem bmi = new JMenuItem("BMI统计界面");//创建菜单项bmi
        menu.add(main);
        menu.add(input);//向菜单中添加组件
        menu.add(maintain);
        menu.add(bmi);
        bar.add(menu);//向菜单条中添加菜单
        frame.setJMenuBar(bar);//将菜单条添加至框架
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点×时结束程序
        //main的事件监听器
        main.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new mainPanel());//调用setContentPane()，用菜单控制替换画架frame上的画布为mainPanel
                frame.setVisible(true);

            }
        });
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new inputPanel());
                frame.setVisible(true);

            }
        });
        maintain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new maintainPanel());
                frame.setVisible(true);

            }
        });
        bmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new bmiPanel());
                frame.setVisible(true);

            }
        });
    }
}
