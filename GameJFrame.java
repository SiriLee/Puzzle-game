package com.siri.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import java.util.Vector;

public class GameJFrame extends UnitJFrame {
    //ԭ��¼����
    //private LoginJFrame loginJFrame;
    //����������
    int stepCounter;
    //�ļ�
    private final String path = "puzzle_game\\image\\";
    private String imgFile = path + "animal\\animal3\\";//Ĭ�ϳ�ʼ·����������initialize()֮ǰ��ֵ
    //ͼƬ����Ķ�ά����
    private final int[][] winCodeArr = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    private int[][] imgCodeArr;//�ֶ���ֵ
    //ͼƬ����(���������һһ��Ӧ)
    private final JLabel[] jLabelArr = new JLabel[16];
    //��Ա��ǩ
    private JLabel completeImg;
    private JLabel winImg;
    private JLabel counterLabel;

    //���ڳߴ�
    private final int WINDOW_WIDTH = 603;
    private final int WINDOW_HIGH = 680;
    //ͼƬ����ԭ�㣬ͼƬ�ߴ�
    private final int ORIGIN_X = 83;
    private final int ORIGIN_Y = 134;
    private final int SIZE = 105;

    private Point dragStartOffset;//������ͼƬ�ĳ�ʼƫ��
    private JLayeredPane layeredPane;//������

    //ͼƬ��������/ͼƬ����(j, i)
    private Vector<Integer> selfImgInfo;//�ƶ�ͼƬ����
    private Vector<Integer> targetImgInfo;//Ŀ��ͼƬ����

    public GameJFrame() {
        super.initialize(WINDOW_WIDTH, WINDOW_HIGH);//�����ʼ������
    }

    //��ʼ���˵���ʵ��
    @Override
    protected void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        //����
        JMenu functionJMenu = new JMenu("����");
        jMenuBar.add(functionJMenu);
        //����-����ͼƬ
        JMenu changeImgJMenu = new JMenu("����ͼƬ");
        functionJMenu.add(changeImgJMenu);
        //����-����ͼƬ-����
        JMenuItem humanItem = new JMenuItem("����");
        humanItem.addActionListener(e -> initImgFile("girl"));
        changeImgJMenu.add(humanItem);
        //����-����ͼƬ-����
        JMenuItem animalItem = new JMenuItem("����");
        animalItem.addActionListener(e -> initImgFile("animal"));
        changeImgJMenu.add(animalItem);
        //����-����ͼƬ-�˶�
        JMenuItem sportItem = new JMenuItem("�˶�");
        sportItem.addActionListener(e -> initImgFile("sport"));
        changeImgJMenu.add(sportItem);
        //����-������Ϸ
        JMenuItem replayItem = new JMenuItem("������Ϸ");
        replayItem.addActionListener(e -> replay());
        functionJMenu.add(replayItem);
        //����-���µ�¼
        JMenuItem reLoginItem = new JMenuItem("���µ�¼");
        reLoginItem.addActionListener(e -> reLogin());
        functionJMenu.add(reLoginItem);
        //����-�ر���Ϸ
        JMenuItem closeItem = new JMenuItem("�ر���Ϸ");
        closeItem.addActionListener(e -> closeGame());
        functionJMenu.add(closeItem);

        //��������
        JMenu aboutJMenu = new JMenu("��������");
        jMenuBar.add(aboutJMenu);
        //��������-���ں�
        JMenuItem accountItem = new JMenuItem("���ں�");
        accountItem.addActionListener(e -> accountDialog());
        aboutJMenu.add(accountItem);

        this.setJMenuBar(jMenuBar);
    }

    //��ʼ�����(�ֲ����)
    @Override
    protected void initPanel() {
        //������
        layeredPane = new JLayeredPane();
        this.setContentPane(layeredPane);
        //��Ӽ��̼���
        setFrameListeners(this);
    }

    //��ʼ������
    @Override
    protected void initData() {
        stepCounter = 0;
        //������ȷ��������
        imgCodeArr = new int[4][4];
        initImgCodeArr();//��ʼ����������
        //���Ҷ�ά����
        Random rd = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int tmpI = rd.nextInt(4);
                int tmpJ = rd.nextInt(4);
                int tmp = imgCodeArr[i][j];
                imgCodeArr[i][j] = imgCodeArr[tmpI][tmpJ];
                imgCodeArr[tmpI][tmpJ] = tmp;
            }
        }
    }

    //��ʼ����������(����2��)
    private void initImgCodeArr() {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(winCodeArr[i], 0, imgCodeArr[i], 0, 4);
        }
    }

    //��ʼ��ͼƬ������
    @Override
    protected void initJLabel() {
        //�ȼ��ص����Ϸ�������ص����·���
        //��ʼ������ͼƬ
        initCompleteLabel();
        //��ʼ��ʤ��ͼƬ
        initWinLabel();
        //��ʼ��ƴͼͼƬ
        initPuzzleLabel();
        //��ʼ����������ǩ
        initCounterLabel();
        //��ʼ������ͼƬ
        initBackgroundLabel();
    }

    //��ʼ������ͼƬ
    private void initCompleteLabel() {
        completeImg = new JLabel(new ImageIcon(imgFile + "all.jpg"));//����ͼƬ
        completeImg.setBounds(ORIGIN_X, ORIGIN_Y, SIZE * 4, SIZE * 4);
        layeredPane.setLayer(completeImg, JLayeredPane.DRAG_LAYER + 10);//�ö���ʾ
        completeImg.setVisible(false);//Ĭ�ϲ��ɼ�
        this.add(completeImg);
    }

    //��ʼ��ʤ��ͼƬ
    private void initWinLabel() {
        winImg = new JLabel(new ImageIcon(path + "win.png"));//����ͼƬ
        winImg.setBounds(203, 283, 197, 73);
        layeredPane.setLayer(winImg, JLayeredPane.DRAG_LAYER + 5);//�ϲ���ʾ
        winImg.setVisible(false);//Ĭ�ϲ��ɼ�
        this.add(winImg);
    }

    //��ʼ��ƴͼͼƬ
    private void initPuzzleLabel() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int index = imgCodeArr[i][j] - 1;//���� = ���� - 1
                //����ͼƬ(����;���ͼƬһһ��Ӧ)�����ñ߿���¼�
                jLabelArr[index] = initImage(imgFile + imgCodeArr[i][j] + ".jpg");
                //����ͼƬλ��
                setImgLocation(i, j);
                //���ͼƬ
                this.add(jLabelArr[index]);
            }
        }
    }

    //��ʼ����������ǩ
    private void initCounterLabel() {
        counterLabel = new JLabel("������" + stepCounter);
        counterLabel.setBounds(50, 30, 100, 20);
        layeredPane.setLayer(counterLabel, JLayeredPane.DEFAULT_LAYER);
        this.add(counterLabel);
    }

    //��ʼ������ͼƬ
    private void initBackgroundLabel() {
        //����ͼƬ�����ٱ����ã�ֻ���ں����ڲ����������贴��Ϊ��Ա����
        String backGroundDest = path + "background.png";//����ͼƬ
        JLabel backJLabel = new JLabel(new ImageIcon(backGroundDest));
        backJLabel.setBounds(40, 40, 508, 560);
        //��������Ϊ��ײ�
        layeredPane.setLayer(backJLabel, JLayeredPane.DEFAULT_LAYER - 10);
        this.add(backJLabel);
    }

    //����ƴͼͼƬ��ʼ��
    private JLabel initImage(String fileName) {
        JLabel img = new JLabel(new ImageIcon(fileName));//����ͼƬ
        img.setBorder(new BevelBorder(BevelBorder.LOWERED));//�߿�
        this.setLabelListeners(img);//�¼�����
        return img;
    }

    //����ƴͼͼƬ(��Ҫ)
    private void setImgLocation(int i, int j) {
        JLabel img = jLabelArr[imgCodeArr[i][j] - 1];//��ȡͼƬ
        img.setBounds(SIZE * j + ORIGIN_X, SIZE * i + ORIGIN_Y, SIZE, SIZE);//�����෴
        layeredPane.setLayer(img, JLayeredPane.DEFAULT_LAYER);
        isWInOption();//ÿ���·���ͼƬλ��ʱ�������Ϸʤ��
    }

    //������ü��̼���
    private void setFrameListeners(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    completeImg.setVisible(true);//��ʾ����ͼƬ
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    completeImg.setVisible(false);//��������ͼƬ
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    if (isWin()) return;//ʤ�����ټ���
                    cheat();
                }
            }
        });
    }

    //ͼƬ�����¼�����
    private void setLabelListeners(JLabel img) {
        //��ѹ���ɿ��¼�
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isWin()) return;//ʤ�����ټ���
                selfImgInfo = getImageInfo(img.getLocation());//����ƶ�ͼƬ����
                dragStartOffset = e.getPoint();//������ͼƬ��ƫ��
                layeredPane.setLayer(img, JLayeredPane.DRAG_LAYER);//ͼƬ�ö�(��ק��)
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isWin()) return;//ʤ�����ټ���
                targetImgInfo = getImageInfo(img.getLocation());//��ȡĿ��ͼƬ����
                exchangeImage();//���������ͼƬ(ͼƬ����ʱ���Զ��ع�Ĭ�ϲ�)
            }
        });
        //��ק�¼�
        img.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isWin()) return;//ʤ�����ټ���
                Point mousePosition = e.getLocationOnScreen();//���λ��
                Point panelPosition = GameJFrame.this.getContentPane().getLocationOnScreen();//���λ��
                int relativeX = mousePosition.x - panelPosition.x;
                int relativeY = mousePosition.y - panelPosition.y;//����������λ��
                img.setLocation(relativeX - dragStartOffset.x, relativeY - dragStartOffset.y);//ͼƬλ��
            }
        });
    }

    //���Ŀ��ͼƬ��������(ͼƬ����)
    private Vector<Integer> getImageInfo(Point location) {
        Vector<Integer> vector = new Vector<>(2);
        //��ȡͼƬ��������
        int x = location.x + SIZE / 2;
        int y = location.y + SIZE / 2;
        //������λ��
        int relativeX = x - ORIGIN_X;
        int relativeY = y - ORIGIN_Y;
        //��ȡ��������
        int xIndex = getLegalIndex(relativeX / SIZE);
        int yIndex = getLegalIndex(relativeY / SIZE);
        vector.add(xIndex);
        vector.add(yIndex);
        return vector;
    }

    //��úϷ�����(0-3)
    private int getLegalIndex(int index) {
        index = Math.max(0, index);
        return Math.min(3, index);
    }

    /*GUI�е�������������������������෴������*/
    //���������ͼƬ
    private void exchangeImage() {
        //��ȡ����
        int j1 = selfImgInfo.get(0);
        int i1 = selfImgInfo.get(1);
        int j2 = targetImgInfo.get(0);
        int i2 = targetImgInfo.get(1);
        //�ж��Ƿ�Ϊԭλ
        if (i1 != i2 || j1 != j2) {
            stepCounter++;//������
            counterLabel.setText("������" + stepCounter);
        }
        //��������
        int tmp = imgCodeArr[i1][j1];
        imgCodeArr[i1][j1] = imgCodeArr[i2][j2];
        imgCodeArr[i2][j2] = tmp;
        //����ͼƬ
        setImgLocation(i1, j1);
        setImgLocation(i2, j2);
    }

    //�ж�ʤ������
    private void isWInOption() {
        if (!isWin()) return;
        //��ʾʤ��ͼƬ
        winImg.setVisible(true);
    }

    //�ж�ʤ��
    private boolean isWin() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (imgCodeArr[i][j] != winCodeArr[i][j]) return false;
            }
        }
        return true;
    }

    //���
    private void cheat() {
        initImgCodeArr();
        //����ͼƬ
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                setImgLocation(i, j);
            }
        }
    }

    //������Ϸ
    private void replay() {
        this.getJMenuBar().removeAll();
        this.getContentPane().removeAll();
        super.initialize(WINDOW_WIDTH, WINDOW_HIGH);
    }

    //���µ�¼
    private void reLogin() {
        new LoginJFrame();
        this.dispose();
    }

    //�ر���Ϸ
    private void closeGame() {
        System.exit(0);
    }

    //����
    private void accountDialog() {
        JDialog jDialog = new JDialog();
        JLabel img = new JLabel(new ImageIcon(path + "about.png"));
        img.setSize(258, 258);

        jDialog.getContentPane().add(img);//Ĭ��������У�ֱ�����
        jDialog.setSize(344, 344);
        jDialog.setLocationRelativeTo(null);//��Ļ����
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);//�����ƴͼ���򴰿ڵĲ���

        jDialog.setVisible(true);
    }

    //��ʼ��(����)ͼƬ�ļ�
    private void initImgFile(String imgType) {
        String imgTypePath = imgType + "\\" + imgType;
        Random rd = new Random();
        File directory;
        String origin = imgFile;
        do {
            int imgFileNum = 1 + rd.nextInt(10);//1-10
            imgFile = path + imgTypePath + imgFileNum + "\\";
            directory = new File(imgFile);
        } while (!directory.exists() || imgFile.equals(origin));//��֤�ļ�������������ͼƬ��
        replay();
    }
}
