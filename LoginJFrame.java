package com.siri.ui;

import com.siri.tool.CodeUtil;
import com.siri.user.UserData;//导入数据

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginJFrame extends UnitJFrame {
    private final String path = "puzzle_game\\image\\login\\";
    private String vfCode;//验证码
    //输入框
    JTextField userInputBox;
    JPasswordField passwordInputBox;
    JTextField codeInputBox;
    //验证码
    JLabel rightVfCode;

    final int WIDTH = 488;
    final int HIGH = 430;

    public LoginJFrame() {
        super.initialize(WIDTH, HIGH);
    }

    @Override
    protected void initJMenuBar() {
    }//无菜单

    @Override
    protected void initData() {
        vfCode = CodeUtil.getVfCode();//生成验证码
    }

    @Override
    protected void initJLabel() {
        //先填充的在上层
        //用户名栏
        initUserColumn();
        //密码栏
        initPasswordColumn();
        //验证码栏
        initVfCodeColumn();
        //登录、注册按钮
        initButtonColumn();
        //背景图片
        initBackgroundLabel();
    }

    //用户名栏
    private void initUserColumn() {
        JLabel userText = new JLabel(new ImageIcon(path + "用户名.png"));
        userText.setBounds(116, 135, 47, 17);
        this.add(userText);
        userInputBox = new JTextField();
        userInputBox.setBounds(195, 134, 200, 30);
        this.add(userInputBox);
    }

    //密码栏
    private void initPasswordColumn() {
        JLabel passwordText = new JLabel(new ImageIcon(path + "密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.add(passwordText);
        passwordInputBox = new JPasswordField();
        passwordInputBox.setBounds(195, 195, 200, 30);
        passwordInputBox.setEchoChar('*');
        this.add(passwordInputBox);
        initPasswordBtn();//密码按钮
    }

    //密码显示按钮
    private void initPasswordBtn() {
        ImageIcon img = new ImageIcon(path + "显示密码.png");
        JLabel btn = new JLabel(img);
        btn.setBounds(395, 195, 18, 29);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setIcon(new ImageIcon(path + "显示密码按下.png"));
                passwordInputBox.setEchoChar((char) 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setIcon(img);
                passwordInputBox.setEchoChar('*');
            }
        });
        this.add(btn);
    }

    //验证码栏
    private void initVfCodeColumn() {
        JLabel codeText = new JLabel(new ImageIcon(path + "验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.add(codeText);
        codeInputBox = new JTextField();
        codeInputBox.setBounds(195, 256, 100, 30);
        this.add(codeInputBox);
        rightVfCode = new JLabel(vfCode);
        rightVfCode.setBounds(300, 256, 50, 30);
        //添加点击刷新事件
        rightVfCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshVfCode();
            }
        });
        this.add(rightVfCode);
    }

    //登录、注册按钮
    private void initButtonColumn() {
        initLoginBtn();
        initRegisterBtn();
    }

    //登录按钮
    private void initLoginBtn() {
        ImageIcon loginIcon = new ImageIcon(path + "登录按钮.png");
        JLabel loginBtn = new JLabel(loginIcon);
        loginBtn.setBounds(123, 310, 128, 47);
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loginBtn.setIcon(new ImageIcon(path + "登录按下.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loginBtn.setIcon(loginIcon);
                login();
            }
        });
        this.add(loginBtn);
    }

    //注册按钮
    private void initRegisterBtn() {
        ImageIcon registerIcon = new ImageIcon(path + "注册按钮.png");
        JLabel registerButton = new JLabel(registerIcon);
        registerButton.setBounds(256, 310, 128, 47);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerButton.setIcon(new ImageIcon(path + "注册按下.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerButton.setIcon(registerIcon);
                new RegisterJFrame();//注册
                LoginJFrame.this.dispose();
            }
        });
        this.add(registerButton);
    }

    //背景图片
    private void initBackgroundLabel() {
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.add(background);
    }

    //登录
    private void login() {
        String inputUsername = userInputBox.getText();
        String inputPassword = new String(passwordInputBox.getPassword());
        String inputCode = codeInputBox.getText();
        int index = UserData.getUserIndex(inputUsername);
        if (isEmpty(inputUsername) || isEmpty(inputPassword)) {
            this.showDialog("请输入完整信息");
            clearInputBox();
            return;
        }
        if (index == -1) {
            this.showDialog("用户未注册");
            clearInputBox();
            return;
        }
        if (!verifyVfCode(inputCode)) {
            this.showDialog("验证码错误");
            refreshVfCode();
            return;
        }
        if (!UserData.verifyPassword(index, inputPassword)) {
            this.showDialog("密码错误");
            clearInputBox();
            return;
        }
        //成功登录
        new GameJFrame();//游戏
        this.dispose();
    }

    //清空输入框(密码和验证码)
    private void clearInputBox() {
        refreshVfCode();
        passwordInputBox.setText("");
    }

    //清空验证码框 + 刷新验证码
    private void refreshVfCode() {
        codeInputBox.setText("");
        vfCode = CodeUtil.getVfCode();
        rightVfCode.setText(vfCode);
    }

    //检查是否输入为空
    private boolean isEmpty(String input) {
        return input.equals("");
    }

    //检查验证码
    private boolean verifyVfCode(String inputCode) {
        return vfCode.equalsIgnoreCase(inputCode);
    }
}
