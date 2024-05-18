/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hit.java.exp2.hit1170300910;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author think
 */
public class OOBMI {

    public static void main(String[] args) {//主函数
        int[] sortedIndex = new int[1000];//排序数组
        for (int i = 0; i < 1000; i++) {
            sortedIndex[i] = i;//让排序数组值按自然数排列
        }
        OOBMI oobmi = new OOBMI();
        while (true) {//重复显示菜单
            oobmi.menu(sortedIndex);//调用menu函数
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

        String id = "0";//学号
        String name = null;//姓名
        float height = 0;//身高
        float weight = 0;//体重
        float bmi = 0;//BMI指数

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
    static Student[] students;//增加成员属性

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
            students[i] = new Student();
            String id = randString("1234567890", 10);
            while (isExists(id)) {//判断是否与已产生学号重复
                id = randString("1234567890", 10);//若重复，重新产生
            }
            String name = randString("abcdefghijklmnopqrstuvwxyz", 3);/*产生3位学生姓名*/
            float height = Float.parseFloat(String.format("%.2f", randlr(1.5f,
                    2.0f)));//随机生成1.5-2米的身高，保留两位小数存储
            float weight = Float.parseFloat(String.format("%.2f", randlr(40.0f,
                    120.0f)));//随机生成40-120kg的体重，保留两位小数存储
            students[i] = new Student(id, name, height, weight);/*初始化students[i]*/
        }
    }

    /*isExsists函数:判断该学生是否已经在students数组中，函数返回值为boolean类型，如果已经存在，返回false；否则，返回true*/
    public static boolean isExists(String id) {
        int flag = 0;//标志变量
        int i = 0;
        for (i = 0; students[i].id.compareTo("0") != 0; i++) {
            if (students[i].id.compareTo("0") == 0) {
                return false;
            } else {
                if (id.compareTo(students[i].id) == 0) {
                    flag = 1;//若重复，标志变量改为1
                     return true;
                }
            }
        }
        return false;
    }

    //bmiaverage函数：统计bmi的均值
    private float bmiaverage() {
        float average;//平均值
        float sum = 0;//bmi和
        for (int i = 0; i < students.length; i++) {
            sum += students[i].bmi;//逐个相加
        }
        average = Float.parseFloat(String.format("%.2f",
                sum / students.length));//计算平均值，并保留2位小数
        return average;//返回平均值
    }

    //bmimid函数：统计bmi的中值
    private float bmimid() {
        float mid;//中值
        int tmp;//用来交换脚标
        int sortedIndex[] = new int[students.length];//初始化 the sortedIndex[]
        for (int i = 0; i < students.length; i++) {
            sortedIndex[i] = i;
        }
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[i].bmi > students[j].bmi) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;
                }
            }
        }//从小到大排序
        if (students.length % 2 == 0) {//若学生个数位偶数
            float m;
            m = (students[sortedIndex[students.length / 2]].bmi
                    + students[sortedIndex[students.length / 2 + 1]].bmi) / 2;
            mid = Float.parseFloat(String.format("%.2f", m));/*中值等于两个中位数的平均数*/
        } else {
            mid = Float.parseFloat(String.format("%.2f",
                    students[sortedIndex[students.length / 2]].bmi));/*中值等于中位数*/
        }
        return mid;//返回中值
    }

    //bmimode：统计bmi的众数
    private float[] bmimode() {
        float[] mode = new float[students.length];//众数数组
        int[] flag = new int[students.length];//有几个学生的bmi值跟第i个一样
        int max = 1;//flag中最大的值
        for (int i = 0; i < students.length; i++) {
            flag[i] = 0;//将flag初始化为0
            mode[i] = 0;//将众数初始化为0
        }
        for (int i = 0; i < students.length; i++) {//外层循环
            for (int j = 0; j < students.length; j++) {
                if (students[i].bmi == students[j].bmi) {/*若两者相等，则flag++*/
                    flag[i]++;
                }
            }//循环结束后，flag[i]为bmi值跟第i个一样的学生个数（包括第i个）
        }
        for (int i = 0; i < students.length; i++) {
            if (max < flag[i]) {//若max的值小于flag[i],则将flag[i]的值赋给max
                max = flag[i];
            }
        }//循环结束后，max为flag中最大的值
        if (max == 1) {//没有众数,返回null
            return null;
        } else {
            int j = 0;//众数的个数
            for (int i = 0; i < students.length; i++) {
                if (flag[i] == max) {//若第i个BMI为众数
                    mode[j] = students[i].bmi;//将该bmi值赋给众数
                    if (mode[j] != students[i].bmi) {//去掉重复的bmi
                        j++;//累计众数的个数
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
        for (int i = 0; i < students.length; i++) {
            sum += Math.pow(students[i].bmi - average, 2);
        }
        variance = Float.parseFloat(String.format("%.2f",
                sum / students.length));;//计算方差，保留2位小数
        return variance;//返回方差
    }

    /*printStatics函数：首先打印所有学生基本信息，然后打印bmi的均值、中值、众数、方差等统计结果信息*/
    private void printStatics() {
        for (int i = 0; i < students.length; i++) {
            System.out.printf(students[i].id + "\t"
                    + students[i].name + "\t" + "%.2f" + "\t" + "%.2f" + "\t"
                    + "%.2f" + "\t" + checkHealth(students[i].bmi) + "\n", students[i].height,
                    students[i].weight, students[i].bmi);//逐个打印学生信息
        }
        System.out.printf("BMI指数的均值为%.2f" + "\n", bmiaverage());/*打印bmi的均值*/
        System.out.printf("BMI指数的中值为%.2f" + "\n", bmimid());//打印bmi的均值
        System.out.printf("BMI指数的众数为");
        float[] mode = bmimode();
        if (mode == null) {//判断是否存在众数
            System.out.printf("不存在众数！" + "\n");
        } else {
            for (int i = 0; i < students.length; i++) {
                if (mode[i] != 0) {//若众数不为0，则输出
                    System.out.printf("%.2f" + "\t", mode[i]);
                }
            }
        }
        System.out.printf("BMI指数的方差为%.2f" + "\n", bmivariance());/*打印bmi的方差*/
    }

    //sortByid函数：按照学生学号由小到大排序
    private void sortByid(int[] sortedIndex) {
        int tmp;//用来交换排序数组的值
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[sortedIndex[i]].id.compareTo(students[sortedIndex[j]].id) > 0) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;//交换一次
                }
            }
        }//交换完成
    }

    //sortByname函数：按照学生姓名由小到大排序
    private void sortByname(int[] sortedIndex) {
        int tmp;//用来交换排序数组的值
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[sortedIndex[i]].name.compareTo(students[sortedIndex[j]].name) > 0) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;//交换一次
                }
            }
        }//交换完成
    }

    //sortByheight函数：按照学生身高由小到大排序
    private void sortByheight(int[] sortedIndex) {
        int tmp;//用来交换排序数组的值
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[sortedIndex[i]].height > students[sortedIndex[j]].height) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;//交换一次
                }
            }
        }//交换完成
    }

    //sortByweight函数：按照学生体重由小到大排序
    private void sortByweight(int[] sortedIndex) {
        int tmp;//用来交换排序数组的值
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[sortedIndex[i]].weight > students[sortedIndex[j]].weight) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;//交换一次
                }
            }
        }//交换完成
    }

    //sortBybmi函数：按照学生BMI值由小到大排序
    private void sortBybmi(int[] sortedIndex) {
        int tmp;//用来交换排序数组的值
        for (int i = 0; i < students.length - 1; i++) {//外层循环
            for (int j = i + 1; j < students.length; j++) {
                if (students[sortedIndex[i]].bmi > students[sortedIndex[j]].bmi) {/*若前者比后者大，则交换两者的脚标*/
                    tmp = sortedIndex[i];
                    sortedIndex[i] = sortedIndex[j];
                    sortedIndex[j] = tmp;//交换一次
                }
            }
        }//交换完成
    }

    //printStudents函数：打印排序前和排序后的结果
    private void printStudents(int sortedIndex[]) {
        for (int i = 0; i < students.length; i++) {
            System.out.printf(students[sortedIndex[i]].id + "\t"
                    + students[sortedIndex[i]].name + "\t" + "%.2f" + "\t" + "%.2f" + "\t"
                    + "%.2f" + "\t" + checkHealth(students[sortedIndex[i]].bmi) + "\n", students[sortedIndex[i]].height,
                    students[sortedIndex[i]].weight, students[sortedIndex[i]].bmi);/*打印学生信息,显示两位小数,换行*/
        }
    }

    /*menu函数：提供随机生成学生、打印学生、5种排序、打印统计信息、退出执行等9个菜单功能，用户输入指定选项后，运行相应函数功能*/
    private void menu(int[] sortedIndex) {
        int a;//用于接收用户指令
        System.out.println("1.随机生成学生");
        System.out.println("2.打印学生");
        System.out.println("3.按学号排序");
        System.out.println("4.按姓名排序");
        System.out.println("5.按身高排序");
        System.out.println("6.按体重排序");
        System.out.println("7.按bmi指数排序");
        System.out.println("8.打印统计信息");
        System.out.println("9.退出执行");
        Scanner scan = new Scanner(System.in);
        a = scan.nextInt();
        switch (a) {
            case 1:
                int num;
                System.out.print("输入学生人数：");
                num = scan.nextInt();//存储到num
                students = new Student[num];
                genStudents(num);/*调用genStudents*/
                break;
            case 2:
                printStudents(sortedIndex);//打印学生
                break;
            case 3:
                sortByid(sortedIndex);
                break;
            case 4:
                sortByname(sortedIndex);
                break;
            case 5:
                sortByheight(sortedIndex);
                break;
            case 6:
                sortByweight(sortedIndex);
                break;
            case 7:
                sortBybmi(sortedIndex);
                break;
            case 8:
                printStatics();//打印统计信息
                break;
            case 9:
                System.exit(0);//程序退出
                break;
            default://处理非法输入
                System.out.println("指令错误！");
                break;
        }
    }

}
