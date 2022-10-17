package second;

import java.util.*;

public class compareList {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        list.add(new User("ZhangSan", 28, 98));
        list.add(new User("LiSi", 21, 100));
        list.add(new User("KangKang", 27, 89));
        list.add(new User("LiMing", 19, 92));
        list.add(new User("WangGang", 22, 66));
        list.add(new User("ZhaoXin", 24, 85));
        list.add(new User("LiuWei", 20, 78));
        list.add(new User("BaiZhanTang", 16, 99));

        list.sort((User first, User second) -> second.getAge() - first.getAge());
        for (User user : list) {
            System.out.println(user.toString());
        }

        list.sort((User first, User second) -> first.getName().length() - second.getName().length());
        for (User user : list) {
            System.out.println(user.toString());
        }

        list.sort((User first, User second) -> first.getGrade() - second.getGrade());
        for (User user : list) {
            System.out.println(user.toString());
        }
    }
}
