package com.siri.user;

import java.util.ArrayList;

public class UserData {
    private static final ArrayList<User> users = new ArrayList<>(10);

    //加载类时执行导入数据
    static {
        importData();
    }

    //导入数据
    private static void importData() {
        //1.读取文件
        //2.导入
        users.add(new User("siri", "a13808204870"));
        users.add(new User("yang", "111"));
        users.add(new User("Harry", "123"));
    }

    //添加用户
    public static void addData(User user) {
        users.add(user);
        //2.写入文件
    }

    //查找用户
    public static int getUserIndex(String inputUsername) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(inputUsername)) {
                return i;
            }
        }
        return -1;
    }

    //检查密码
    public static boolean verifyPassword(int index, String inputPassword) {
        return users.get(index).getPassword().equals(inputPassword);
    }
}
