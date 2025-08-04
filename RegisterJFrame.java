package com.siri.ui;

import com.siri.user.User;
import com.siri.user.UserData;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterJFrame extends UnitJFrame {
    private final String path = "puzzle_game\\image\\register\\";

    private JTextField userInputBox;
    private JPasswordField passwordInputBox;
    private JPasswordField confirmInputBox;

    public RegisterJFrame() {
        super.initialize(488, 430);
    }

    @Override
    protected void initJMenuBar() {
    }//�޲˵�

    @Override
    protected void initData() {
    }

    @Override
    protected void initJLabel() {
        //�û�����
        initUserColumn();
        //������
        initPasswordColumn();
        //�ٴ�����������
        initConfirmColumn();
        //��ť
        initButtonColumn();
        //���ر���ͼƬ
        initBackgroundImg();
    }

    //�û�����
    private void initUserColumn() {
        JLabel userText = new JLabel(new ImageIcon(path + "ע���û���.png"));
        userText.setBounds(84, 135, 79, 17);
        this.add(userText);
        userInputBox = new JTextField();
        userInputBox.setBounds(195, 134, 200, 30);
        this.add(userInputBox);
    }

    //������
    private void initPasswordColumn() {
        JLabel passwordText = new JLabel(new ImageIcon(path + "ע������.png"));
        passwordText.setBounds(98, 195, 64, 16);
        this.add(passwordText);
        passwordInputBox = new JPasswordField();
        passwordInputBox.setBounds(195, 195, 200, 30);
        passwordInputBox.setEchoChar('*');
        this.add(passwordInputBox);
    }

    //�ٴ�����������
    private void initConfirmColumn() {
        JLabel confirmText = new JLabel(new ImageIcon(path + "�ٴ���������.png"));
        confirmText.setBounds(68, 256, 96, 17);
        this.add(confirmText);
        confirmInputBox = new JPasswordField();
        confirmInputBox.setBounds(195, 256, 200, 30);
        confirmInputBox.setEchoChar('*');
        this.add(confirmInputBox);
    }

    //��ť
    private void initButtonColumn() {
        initRegisterBtn();
        initResetBtn();
    }

    //ע�ᰴť
    private void initRegisterBtn() {
        ImageIcon registerIcon = new ImageIcon(path + "ע�ᰴť.png");
        JLabel registerBtn = new JLabel(registerIcon);
        registerBtn.setBounds(123, 310, 128, 47);
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerBtn.setIcon(new ImageIcon(path + "ע�ᰴ��.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerBtn.setIcon(registerIcon);
                register();
            }
        });

        this.add(registerBtn);
    }

    //���ð�ť
    private void initResetBtn() {
        ImageIcon resetIcon = new ImageIcon(path + "���ð�ť.png");
        JLabel resetButton = new JLabel(resetIcon);
        resetButton.setBounds(256, 310, 128, 47);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButton.setIcon(new ImageIcon(path + "���ð���.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetButton.setIcon(resetIcon);
                reset();
            }
        });

        this.add(resetButton);
    }

    //���ر���ͼƬ
    private void initBackgroundImg() {
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));//470,390
        background.setBounds(0, 0, 470, 390);
        this.add(background);
    }

    //ע��
    private void register() {
        String inputUsername = userInputBox.getText();
        String password = new String(passwordInputBox.getPassword());
        String confirm = new String(confirmInputBox.getPassword());
        if (UserData.getUserIndex(inputUsername) != -1) {
            this.showDialog("�û���ע��");
            this.reset();//�������
            return;
        }
        if (!confirm.equals(password)) {
            showDialog("���벻һ��");
            passwordInputBox.setText("");
            confirmInputBox.setText("");
            return;
        }
        User newUser = new User(inputUsername, password);
        UserData.addData(newUser);
        showDialog("ע��ɹ�");
        new LoginJFrame();//��¼
        this.dispose();
    }

    //����
    private void reset() {
        userInputBox.setText("");
        passwordInputBox.setText("");
        confirmInputBox.setText("");
    }
}
