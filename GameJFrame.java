package com.siri.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import java.util.Vector;

public class GameJFrame extends UnitJFrame {
    //原登录窗口
    //private LoginJFrame loginJFrame;
    //步数计数器
    int stepCounter;
    //文件
    private final String path = "puzzle_game\\image\\";
    private String imgFile = path + "animal\\animal3\\";//默认初始路径，必须在initialize()之前赋值
    //图片编码的二维数组
    private final int[][] winCodeArr = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    private int[][] imgCodeArr;//手动赋值
    //图片数组(编码和索引一一对应)
    private final JLabel[] jLabelArr = new JLabel[16];
    //成员标签
    private JLabel completeImg;
    private JLabel winImg;
    private JLabel counterLabel;

    //窗口尺寸
    private final int WINDOW_WIDTH = 603;
    private final int WINDOW_HIGH = 680;
    //图片区域原点，图片尺寸
    private final int ORIGIN_X = 83;
    private final int ORIGIN_Y = 134;
    private final int SIZE = 105;

    private Point dragStartOffset;//鼠标相对图片的初始偏移
    private JLayeredPane layeredPane;//多层面板

    //图片编码坐标/图片向量(j, i)
    private Vector<Integer> selfImgInfo;//移动图片向量
    private Vector<Integer> targetImgInfo;//目标图片向量

    public GameJFrame() {
        super.initialize(WINDOW_WIDTH, WINDOW_HIGH);//父类初始化函数
    }

    //初始化菜单的实现
    @Override
    protected void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        //功能
        JMenu functionJMenu = new JMenu("功能");
        jMenuBar.add(functionJMenu);
        //功能-更换图片
        JMenu changeImgJMenu = new JMenu("更换图片");
        functionJMenu.add(changeImgJMenu);
        //功能-更换图片-人类
        JMenuItem humanItem = new JMenuItem("人类");
        humanItem.addActionListener(e -> initImgFile("girl"));
        changeImgJMenu.add(humanItem);
        //功能-更换图片-动物
        JMenuItem animalItem = new JMenuItem("动物");
        animalItem.addActionListener(e -> initImgFile("animal"));
        changeImgJMenu.add(animalItem);
        //功能-更换图片-运动
        JMenuItem sportItem = new JMenuItem("运动");
        sportItem.addActionListener(e -> initImgFile("sport"));
        changeImgJMenu.add(sportItem);
        //功能-重新游戏
        JMenuItem replayItem = new JMenuItem("重新游戏");
        replayItem.addActionListener(e -> replay());
        functionJMenu.add(replayItem);
        //功能-重新登录
        JMenuItem reLoginItem = new JMenuItem("重新登录");
        reLoginItem.addActionListener(e -> reLogin());
        functionJMenu.add(reLoginItem);
        //功能-关闭游戏
        JMenuItem closeItem = new JMenuItem("关闭游戏");
        closeItem.addActionListener(e -> closeGame());
        functionJMenu.add(closeItem);

        //关于我们
        JMenu aboutJMenu = new JMenu("关于我们");
        jMenuBar.add(aboutJMenu);
        //关于我们-公众号
        JMenuItem accountItem = new JMenuItem("公众号");
        accountItem.addActionListener(e -> accountDialog());
        aboutJMenu.add(accountItem);

        this.setJMenuBar(jMenuBar);
    }

    //初始化面板(分层面板)
    @Override
    protected void initPanel() {
        //多层面板
        layeredPane = new JLayeredPane();
        this.setContentPane(layeredPane);
        //添加键盘监听
        setFrameListeners(this);
    }

    //初始化数据
    @Override
    protected void initData() {
        stepCounter = 0;
        //拷贝正确编码数组
        imgCodeArr = new int[4][4];
        initImgCodeArr();//初始化编码数组
        //打乱二维数组
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

    //初始化编码数组(调用2次)
    private void initImgCodeArr() {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(winCodeArr[i], 0, imgCodeArr[i], 0, 4);
        }
    }

    //初始化图片和文字
    @Override
    protected void initJLabel() {
        //先加载的在上方，后加载的在下方！
        //初始化完整图片
        initCompleteLabel();
        //初始化胜利图片
        initWinLabel();
        //初始化拼图图片
        initPuzzleLabel();
        //初始化计数器标签
        initCounterLabel();
        //初始化背景图片
        initBackgroundLabel();
    }

    //初始化完整图片
    private void initCompleteLabel() {
        completeImg = new JLabel(new ImageIcon(imgFile + "all.jpg"));//导入图片
        completeImg.setBounds(ORIGIN_X, ORIGIN_Y, SIZE * 4, SIZE * 4);
        layeredPane.setLayer(completeImg, JLayeredPane.DRAG_LAYER + 10);//置顶显示
        completeImg.setVisible(false);//默认不可见
        this.add(completeImg);
    }

    //初始化胜利图片
    private void initWinLabel() {
        winImg = new JLabel(new ImageIcon(path + "win.png"));//导入图片
        winImg.setBounds(203, 283, 197, 73);
        layeredPane.setLayer(winImg, JLayeredPane.DRAG_LAYER + 5);//上层显示
        winImg.setVisible(false);//默认不可见
        this.add(winImg);
    }

    //初始化拼图图片
    private void initPuzzleLabel() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int index = imgCodeArr[i][j] - 1;//索引 = 编码 - 1
                //导入图片(编码和具体图片一一对应)，设置边框和事件
                jLabelArr[index] = initImage(imgFile + imgCodeArr[i][j] + ".jpg");
                //设置图片位置
                setImgLocation(i, j);
                //添加图片
                this.add(jLabelArr[index]);
            }
        }
    }

    //初始化计数器标签
    private void initCounterLabel() {
        counterLabel = new JLabel("步数：" + stepCounter);
        counterLabel.setBounds(50, 30, 100, 20);
        layeredPane.setLayer(counterLabel, JLayeredPane.DEFAULT_LAYER);
        this.add(counterLabel);
    }

    //初始化背景图片
    private void initBackgroundLabel() {
        //背景图片不会再被调用，只需在函数内部创建，无需创建为成员变量
        String backGroundDest = path + "background.png";//背景图片
        JLabel backJLabel = new JLabel(new ImageIcon(backGroundDest));
        backJLabel.setBounds(40, 40, 508, 560);
        //背景设置为最底层
        layeredPane.setLayer(backJLabel, JLayeredPane.DEFAULT_LAYER - 10);
        this.add(backJLabel);
    }

    //单张拼图图片初始化
    private JLabel initImage(String fileName) {
        JLabel img = new JLabel(new ImageIcon(fileName));//导入图片
        img.setBorder(new BevelBorder(BevelBorder.LOWERED));//边框
        this.setLabelListeners(img);//事件监听
        return img;
    }

    //放置拼图图片(重要)
    private void setImgLocation(int i, int j) {
        JLabel img = jLabelArr[imgCodeArr[i][j] - 1];//获取图片
        img.setBounds(SIZE * j + ORIGIN_X, SIZE * i + ORIGIN_Y, SIZE, SIZE);//坐标相反
        layeredPane.setLayer(img, JLayeredPane.DEFAULT_LAYER);
        isWInOption();//每次新放置图片位置时，检测游戏胜利
    }

    //框架设置键盘监听
    private void setFrameListeners(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    completeImg.setVisible(true);//显示完整图片
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    completeImg.setVisible(false);//隐藏完整图片
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    if (isWin()) return;//胜利后不再监听
                    cheat();
                }
            }
        });
    }

    //图片设置事件监听
    private void setLabelListeners(JLabel img) {
        //按压和松开事件
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isWin()) return;//胜利后不再监听
                selfImgInfo = getImageInfo(img.getLocation());//获得移动图片向量
                dragStartOffset = e.getPoint();//鼠标相对图片的偏移
                layeredPane.setLayer(img, JLayeredPane.DRAG_LAYER);//图片置顶(拖拽层)
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isWin()) return;//胜利后不再监听
                targetImgInfo = getImageInfo(img.getLocation());//获取目标图片向量
                exchangeImage();//交换编码和图片(图片放置时，自动回归默认层)
            }
        });
        //拖拽事件
        img.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isWin()) return;//胜利后不再监听
                Point mousePosition = e.getLocationOnScreen();//鼠标位置
                Point panelPosition = GameJFrame.this.getContentPane().getLocationOnScreen();//面板位置
                int relativeX = mousePosition.x - panelPosition.x;
                int relativeY = mousePosition.y - panelPosition.y;//鼠标相对面板的位置
                img.setLocation(relativeX - dragStartOffset.x, relativeY - dragStartOffset.y);//图片位置
            }
        });
    }

    //获得目标图片编码坐标(图片向量)
    private Vector<Integer> getImageInfo(Point location) {
        Vector<Integer> vector = new Vector<>(2);
        //获取图片中心坐标
        int x = location.x + SIZE / 2;
        int y = location.y + SIZE / 2;
        //相对面板位置
        int relativeX = x - ORIGIN_X;
        int relativeY = y - ORIGIN_Y;
        //获取坐标索引
        int xIndex = getLegalIndex(relativeX / SIZE);
        int yIndex = getLegalIndex(relativeY / SIZE);
        vector.add(xIndex);
        vector.add(yIndex);
        return vector;
    }

    //获得合法索引(0-3)
    private int getLegalIndex(int index) {
        index = Math.max(0, index);
        return Math.min(3, index);
    }

    /*GUI中的坐标与数组的坐标总是正好相反！！！*/
    //交换编码和图片
    private void exchangeImage() {
        //获取数据
        int j1 = selfImgInfo.get(0);
        int i1 = selfImgInfo.get(1);
        int j2 = targetImgInfo.get(0);
        int i2 = targetImgInfo.get(1);
        //判断是否为原位
        if (i1 != i2 || j1 != j2) {
            stepCounter++;//计数器
            counterLabel.setText("步数：" + stepCounter);
        }
        //交换编码
        int tmp = imgCodeArr[i1][j1];
        imgCodeArr[i1][j1] = imgCodeArr[i2][j2];
        imgCodeArr[i2][j2] = tmp;
        //放置图片
        setImgLocation(i1, j1);
        setImgLocation(i2, j2);
    }

    //判断胜利操作
    private void isWInOption() {
        if (!isWin()) return;
        //显示胜利图片
        winImg.setVisible(true);
    }

    //判断胜利
    private boolean isWin() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (imgCodeArr[i][j] != winCodeArr[i][j]) return false;
            }
        }
        return true;
    }

    //外挂
    private void cheat() {
        initImgCodeArr();
        //放置图片
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                setImgLocation(i, j);
            }
        }
    }

    //重新游戏
    private void replay() {
        this.getJMenuBar().removeAll();
        this.getContentPane().removeAll();
        super.initialize(WINDOW_WIDTH, WINDOW_HIGH);
    }

    //重新登录
    private void reLogin() {
        new LoginJFrame();
        this.dispose();
    }

    //关闭游戏
    private void closeGame() {
        System.exit(0);
    }

    //弹窗
    private void accountDialog() {
        JDialog jDialog = new JDialog();
        JLabel img = new JLabel(new ImageIcon(path + "about.png"));
        img.setSize(258, 258);

        jDialog.getContentPane().add(img);//默认组件居中，直接添加
        jDialog.setSize(344, 344);
        jDialog.setLocationRelativeTo(null);//屏幕居中
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);//避免对拼图程序窗口的操作

        jDialog.setVisible(true);
    }

    //初始化(更改)图片文件
    private void initImgFile(String imgType) {
        String imgTypePath = imgType + "\\" + imgType;
        Random rd = new Random();
        File directory;
        String origin = imgFile;
        do {
            int imgFileNum = 1 + rd.nextInt(10);//1-10
            imgFile = path + imgTypePath + imgFileNum + "\\";
            directory = new File(imgFile);
        } while (!directory.exists() || imgFile.equals(origin));//保证文件内至少有两张图片！
        replay();
    }
}
