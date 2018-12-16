package cn.itcast.multithread;

import java.util.*;

/**
 * Created by Admin on 9/3/2016.
 */
public class CollectionModifyTest {
    public static void main(String[] args) {
//        Collection<User> users = new ArrayList<>();
        // 普通的遍历器在遍历集合的时候不能对集合做增加/删除元素的操作，否则会抛异常
        // 具体原因可看相应集合类的Iterator实现源码
        // 解决办法一：使用ListIterator
        // 解决办法二：使用同步集合类
//        List<User> users = new ArrayList<>();
        List<User> users = new ArrayList<>();

        users.add(new User("张三", 20));
        users.add(new User("李四", 22));
        users.add(new User("王五", 25));

//        Iterator<User> iterator = users.iterator();
        ListIterator<User> iterator = users.listIterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if ("张三".equals(user.getName())) {
                users.remove(user);
            } else {
                System.out.println(user);
            }
        }
    }

    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;

            User user = (User) o;

            if (age != user.age) return false;
            return name.equals(user.name);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + age;
            return result;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
