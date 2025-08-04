package com.siri.user;

import java.util.ArrayList;

public class UserData {
    private static final ArrayList<User> users = new ArrayList<>(10);

    //������ʱִ�е�������
    static {
        importData();
    }

    //��������
    private static void importData() {
        //1.��ȡ�ļ�
        //2.����
        users.add(new User("siri", "a13808204870"));
        users.add(new User("yang", "111"));
        users.add(new User("Harry", "123"));
    }

    //����û�
    public static void addData(User user) {
        users.add(user);
        //2.д���ļ�
    }

    //�����û�
    public static int getUserIndex(String inputUsername) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(inputUsername)) {
                return i;
            }
        }
        return -1;
    }

    //�������
    public static boolean verifyPassword(int index, String inputPassword) {
        return users.get(index).getPassword().equals(inputPassword);
    }
}
