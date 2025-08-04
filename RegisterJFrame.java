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
    }//无菜单

    @Override
    protected void initData() {
    }

    @Override
    protected void initJLabel() {
        //用户名栏
        initUserColumn();
        //密码栏
        initPasswordColumn();
        //再次输入密码栏
        initConfirmColumn();
        //按钮
        initButtonColumn();
        //加载背景图片
        initBackgroundImg();
    }

    //用户名栏
    private void initUserColumn() {
        JLabel userText = new JLabel(new ImageIcon(path + "注册用户名.png"));
        userText.setBounds(84, 135, 79, 17);
        this.add(userText);
        userInputBox = new JTextField();
        userInputBox.setBounds(195, 134, 200, 30);
        this.add(userInputBox);
    }

    //密码栏
    private void initPasswordColumn() {
        JLabel passwordText = new JLabel(new ImageIcon(path + "注册密码.png"));
        passwordText.setBounds(98, 195, 64, 16);
        this.add(passwordText);
        passwordInputBox = new JPasswordField();
        passwordInputBox.setBounds(195, 195, 200, 30);
        passwordInputBox.setEchoChar('*');
        this.add(passwordInputBox);
    }

    //再次输入密码栏
    private void initConfirmColumn() {
        JLabel confirmText = new JLabel(new ImageIcon(path + "再次输入密码.png"));
        confirmText.setBounds(68, 256, 96, 17);
        this.add(confirmText);
        confirmInputBox = new JPasswordField();
        confirmInputBox.setBounds(195, 256, 200, 30);
        confirmInputBox.setEchoChar('*');
        this.add(confirmInputBox);
    }

    //按钮
    private void initButtonColumn() {
        initRegisterBtn();
        initResetBtn();
    }

    //注册按钮
    private void initRegisterBtn() {
        ImageIcon registerIcon = new ImageIcon(path + "注册按钮.png");
        JLabel registerBtn = new JLabel(registerIcon);
        registerBtn.setBounds(123, 310, 128, 47);
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerBtn.setIcon(new ImageIcon(path + "注册按下.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerBtn.setIcon(registerIcon);
                register();
            }
        });

        this.add(registerBtn);
    }

    //重置按钮
    private void initResetBtn() {
        ImageIcon resetIcon = new ImageIcon(path + "重置按钮.png");
        JLabel resetButton = new JLabel(resetIcon);
        resetButton.setBounds(256, 310, 128, 47);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButton.setIcon(new ImageIcon(path + "重置按下.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetButton.setIcon(resetIcon);
                reset();
            }
        });

        this.add(resetButton);
    }

    //加载背景图片
    private void initBackgroundImg() {
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));//470,390
        background.setBounds(0, 0, 470, 390);
        this.add(background);
    }

    //注册
    private void register() {
        String inputUsername = userInputBox.getText();
        String password = new String(passwordInputBox.getPassword());
        String confirm = new String(confirmInputBox.getPassword());
        if (UserData.getUserIndex(inputUsername) != -1) {
            this.showDialog("用户已注册");
            this.reset();//清空重置
            return;
        }
        if (!confirm.equals(password)) {
            showDialog("密码不一致");
            passwordInputBox.setText("");
            confirmInputBox.setText("");
            return;
        }
        User newUser = new User(inputUsername, password);
        UserData.addData(newUser);
        showDialog("注册成功");
        new LoginJFrame();//登录
        this.dispose();
    }

    //重置
    private void reset() {
        userInputBox.setText("");
        passwordInputBox.setText("");
        confirmInputBox.setText("");
    }
}
