package com.siri.ui;

import javax.swing.*;

public abstract class UnitJFrame extends JFrame {
    //总结：父类构造方法中不要调用可能被重写的方法，原因：执行父类构造方法时子类默认初始化的成员变量未初始化，可能导致越界访问！
    public UnitJFrame() {
    }

    //初始化（用于构造方法初始化时，可能调用其他初始化方法）
    protected void initialize(int width, int high) {
        //初始化界面信息
        this.initJFrame(width, high);
        //初始化菜单信息
        this.initJMenuBar();
        //初始化面板
        this.initPanel();
        //初始化数据
        this.initData();
        //初始化图片、文字信息
        this.initJLabel();
        //显示界面-最后
        this.setVisible(true);
    }

    //初始化界面信息(提供统一方法)
    protected void initJFrame(int w, int h) {
        //设置大小
        this.setSize(w, h);
        //设置标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置居中于整体屏幕
        this.setLocationRelativeTo(null);
        //设置默认关闭模式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //初始化菜单信息
    protected abstract void initJMenuBar();

    //初始化面板(提供默认方法)
    protected void initPanel() {
        this.getContentPane().setLayout(null);//取消组件默认居中方式
    }

    //初始化数据
    protected abstract void initData();

    //初始化图片、文字信息
    protected abstract void initJLabel();

    //弹窗方法
    protected void showDialog(String text) {
        JDialog jDialog = new JDialog();
        JLabel prompt = new JLabel(text, SwingConstants.CENTER);
        prompt.setSize(200, 100);
        jDialog.getContentPane().add(prompt);

        jDialog.setTitle("提示");
        jDialog.setSize(200, 100);
        jDialog.setLocationRelativeTo(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);

        jDialog.setVisible(true);
    }
}
