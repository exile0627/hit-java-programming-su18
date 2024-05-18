/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hit.java.exp3.hit1170300910;

import java.util.Scanner;
import java.math.BigDecimal;//保留两位小数的函数（老师给出）
import java.util.*;

/**
 *
 * @author think
 */
public class CollectionBMI {

    public static void main(String[] args) {//主函数
        CollectionBMI y = new CollectionBMI();//实例化CollectionBMI
        while (true) {//重复显示菜单
            y.menu();//调用menu函数
        }
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
            String str = id + "\t" + name + "\t" + height + "\t"
                    + weight + "\t" + bmi + "\t" + checkHealth(bmi);
            return str;//返回str
        }
    }
    ArrayList<Student> students= new ArrayList<Student>();

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

    //inputStudent函数:输入多个学生的相关信息
    private void inputStudent() {
        while (true) {
            if (continueInput()) {//若返回值为true
                Scanner in = new Scanner(System.in);
                String id, name;
                float height, weight;
                System.out.print("id:");
                id = in.nextLine();
                System.out.print("name:");
                name = in.nextLine();
                System.out.print("height(m):");
                height = in.nextFloat();
                System.out.print("weight(kg):");
                weight = in.nextFloat();
                if (!this.isExists(id)) {//若返回值为false
                    System.out.println("此学号已经存在，请重新输入：");
                    id = in.nextLine();
                } else {//若isExists返回值为true
                    students.add(new Student(id, name, height, weight));/*将输入的学生对象保存到ArrayList<Student> students中*/
                }

            } else {
                break;
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
    private void printStatics() {
        for (Student s : students) {
            System.out.printf(s.id + "\t"
                    + s.name + "\t" + "%.2f" + "\t" + "%.2f" + "\t"
                    + "%.2f" + "\t" + checkHealth(s.bmi) + "\n", s.height,
                    s.weight, s.bmi);//逐个打印学生信息
        }
        System.out.printf("BMI指数的均值为%.2f" + "\n", bmiaverage());/*打印bmi的均值*/
        System.out.printf("BMI指数的中值为%.2f" + "\n", bmimid());//打印bmi的均值
        System.out.printf("BMI指数的众数为");
        float[] mode = bmimode();
        if (mode == null) {//判断是否存在众数
            System.out.printf("不存在众数！" + "\n");
        } else {
            for (int i = 0; i < students.size(); i++) {
                if (mode[i] != 0) {//若众数不为0，则输出
                    System.out.printf("%.2f" + "\t", mode[i]);
                }
            }
            System.out.printf("\n");//换行
        }
        System.out.printf("BMI指数的方差为%.2f" + "\n", bmivariance());/*打印bmi的方差*/
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

    /*menu函数：提供随机生成学生、打印学生、5种排序、打印统计信息、退出执行等9个菜单功能，用户输入指定选项后，运行相应函数功能*/
    private void menu() {
        int a;//用于接收用户指令
        System.out.println("1.输入学生");
        System.out.println("2.随机生成学生");
        System.out.println("3.打印学生");
        System.out.println("4.按学号排序");
        System.out.println("5.按姓名排序");
        System.out.println("6.按身高排序");
        System.out.println("7.按体重排序");
        System.out.println("8.按bmi指数排序");
        System.out.println("9.打印统计信息");
        System.out.println("10.退出执行");
        System.out.print("请输入指令：");
        Scanner scan = new Scanner(System.in);
        a = scan.nextInt();
        switch (a) {
            case 1:
                inputStudent();//调用inputStudent函数
                break;
            case 2:
                int num;
                System.out.print("输入学生人数：");
                num = scan.nextInt();//存储到num
                genStudents(num);/*调用genStudents*/
                break;
            case 3:
                printStudents();//打印学生
                break;
            case 4:
                Collections.sort(students, new sortByid());
                break;
            case 5:
                Collections.sort(students, new sortByname());
                break;
            case 6:
                Collections.sort(students, new sortByheight());
                break;
            case 7:
                Collections.sort(students, new sortByweight());
                break;
            case 8:
                Collections.sort(students, new sortBybmi());
                break;
            case 9:
                printStatics();//打印统计信息
                break;
            case 10:
                System.exit(0);//程序退出
                break;
            default://处理非法输入
                System.out.println("指令错误！");
                break;
        }
    }

}

