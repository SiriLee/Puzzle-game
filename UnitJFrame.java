package com.siri.ui;

import javax.swing.*;

public abstract class UnitJFrame extends JFrame {
    //�ܽ᣺���๹�췽���в�Ҫ���ÿ��ܱ���д�ķ�����ԭ��ִ�и��๹�췽��ʱ����Ĭ�ϳ�ʼ���ĳ�Ա����δ��ʼ�������ܵ���Խ����ʣ�
    public UnitJFrame() {
    }

    //��ʼ�������ڹ��췽����ʼ��ʱ�����ܵ���������ʼ��������
    protected void initialize(int width, int high) {
        //��ʼ��������Ϣ
        this.initJFrame(width, high);
        //��ʼ���˵���Ϣ
        this.initJMenuBar();
        //��ʼ�����
        this.initPanel();
        //��ʼ������
        this.initData();
        //��ʼ��ͼƬ��������Ϣ
        this.initJLabel();
        //��ʾ����-���
        this.setVisible(true);
    }

    //��ʼ��������Ϣ(�ṩͳһ����)
    protected void initJFrame(int w, int h) {
        //���ô�С
        this.setSize(w, h);
        //���ñ���
        this.setTitle("ƴͼ������ v1.0");
        //���ý����ö�
        this.setAlwaysOnTop(true);
        //���þ�����������Ļ
        this.setLocationRelativeTo(null);
        //����Ĭ�Ϲر�ģʽ
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //��ʼ���˵���Ϣ
    protected abstract void initJMenuBar();

    //��ʼ�����(�ṩĬ�Ϸ���)
    protected void initPanel() {
        this.getContentPane().setLayout(null);//ȡ�����Ĭ�Ͼ��з�ʽ
    }

    //��ʼ������
    protected abstract void initData();

    //��ʼ��ͼƬ��������Ϣ
    protected abstract void initJLabel();

    //��������
    protected void showDialog(String text) {
        JDialog jDialog = new JDialog();
        JLabel prompt = new JLabel(text, SwingConstants.CENTER);
        prompt.setSize(200, 100);
        jDialog.getContentPane().add(prompt);

        jDialog.setTitle("��ʾ");
        jDialog.setSize(200, 100);
        jDialog.setLocationRelativeTo(null);
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);

        jDialog.setVisible(true);
    }
}
