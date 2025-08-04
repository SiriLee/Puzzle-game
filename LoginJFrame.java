package com.siri.ui;

import com.siri.tool.CodeUtil;
import com.siri.user.UserData;//��������

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginJFrame extends UnitJFrame {
    private final String path = "puzzle_game\\image\\login\\";
    private String vfCode;//��֤��
    //�����
    JTextField userInputBox;
    JPasswordField passwordInputBox;
    JTextField codeInputBox;
    //��֤��
    JLabel rightVfCode;

    final int WIDTH = 488;
    final int HIGH = 430;

    public LoginJFrame() {
        super.initialize(WIDTH, HIGH);
    }

    @Override
    protected void initJMenuBar() {
    }//�޲˵�

    @Override
    protected void initData() {
        vfCode = CodeUtil.getVfCode();//������֤��
    }

    @Override
    protected void initJLabel() {
        //���������ϲ�
        //�û�����
        initUserColumn();
        //������
        initPasswordColumn();
        //��֤����
        initVfCodeColumn();
        //��¼��ע�ᰴť
        initButtonColumn();
        //����ͼƬ
        initBackgroundLabel();
    }

    //�û�����
    private void initUserColumn() {
        JLabel userText = new JLabel(new ImageIcon(path + "�û���.png"));
        userText.setBounds(116, 135, 47, 17);
        this.add(userText);
        userInputBox = new JTextField();
        userInputBox.setBounds(195, 134, 200, 30);
        this.add(userInputBox);
    }

    //������
    private void initPasswordColumn() {
        JLabel passwordText = new JLabel(new ImageIcon(path + "����.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.add(passwordText);
        passwordInputBox = new JPasswordField();
        passwordInputBox.setBounds(195, 195, 200, 30);
        passwordInputBox.setEchoChar('*');
        this.add(passwordInputBox);
        initPasswordBtn();//���밴ť
    }

    //������ʾ��ť
    private void initPasswordBtn() {
        ImageIcon img = new ImageIcon(path + "��ʾ����.png");
        JLabel btn = new JLabel(img);
        btn.setBounds(395, 195, 18, 29);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setIcon(new ImageIcon(path + "��ʾ���밴��.png"));
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

    //��֤����
    private void initVfCodeColumn() {
        JLabel codeText = new JLabel(new ImageIcon(path + "��֤��.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.add(codeText);
        codeInputBox = new JTextField();
        codeInputBox.setBounds(195, 256, 100, 30);
        this.add(codeInputBox);
        rightVfCode = new JLabel(vfCode);
        rightVfCode.setBounds(300, 256, 50, 30);
        //��ӵ��ˢ���¼�
        rightVfCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshVfCode();
            }
        });
        this.add(rightVfCode);
    }

    //��¼��ע�ᰴť
    private void initButtonColumn() {
        initLoginBtn();
        initRegisterBtn();
    }

    //��¼��ť
    private void initLoginBtn() {
        ImageIcon loginIcon = new ImageIcon(path + "��¼��ť.png");
        JLabel loginBtn = new JLabel(loginIcon);
        loginBtn.setBounds(123, 310, 128, 47);
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loginBtn.setIcon(new ImageIcon(path + "��¼����.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loginBtn.setIcon(loginIcon);
                login();
            }
        });
        this.add(loginBtn);
    }

    //ע�ᰴť
    private void initRegisterBtn() {
        ImageIcon registerIcon = new ImageIcon(path + "ע�ᰴť.png");
        JLabel registerButton = new JLabel(registerIcon);
        registerButton.setBounds(256, 310, 128, 47);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerButton.setIcon(new ImageIcon(path + "ע�ᰴ��.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerButton.setIcon(registerIcon);
                new RegisterJFrame();//ע��
                LoginJFrame.this.dispose();
            }
        });
        this.add(registerButton);
    }

    //����ͼƬ
    private void initBackgroundLabel() {
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.add(background);
    }

    //��¼
    private void login() {
        String inputUsername = userInputBox.getText();
        String inputPassword = new String(passwordInputBox.getPassword());
        String inputCode = codeInputBox.getText();
        int index = UserData.getUserIndex(inputUsername);
        if (isEmpty(inputUsername) || isEmpty(inputPassword)) {
            this.showDialog("������������Ϣ");
            clearInputBox();
            return;
        }
        if (index == -1) {
            this.showDialog("�û�δע��");
            clearInputBox();
            return;
        }
        if (!verifyVfCode(inputCode)) {
            this.showDialog("��֤�����");
            refreshVfCode();
            return;
        }
        if (!UserData.verifyPassword(index, inputPassword)) {
            this.showDialog("�������");
            clearInputBox();
            return;
        }
        //�ɹ���¼
        new GameJFrame();//��Ϸ
        this.dispose();
    }

    //��������(�������֤��)
    private void clearInputBox() {
        refreshVfCode();
        passwordInputBox.setText("");
    }

    //�����֤��� + ˢ����֤��
    private void refreshVfCode() {
        codeInputBox.setText("");
        vfCode = CodeUtil.getVfCode();
        rightVfCode.setText(vfCode);
    }

    //����Ƿ�����Ϊ��
    private boolean isEmpty(String input) {
        return input.equals("");
    }

    //�����֤��
    private boolean verifyVfCode(String inputCode) {
        return vfCode.equalsIgnoreCase(inputCode);
    }
}
