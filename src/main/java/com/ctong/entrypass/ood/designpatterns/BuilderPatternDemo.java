package com.ctong.entrypass.ood.designpatterns;

import com.ctong.entrypass.ood.designpatterns.builder.User;

/**
 * Builder Pattern
 * 生成器模式
 * 将复杂对象的建造过程抽象出来, 能够分步骤创建复杂对象
 */
public class BuilderPatternDemo {

    public static void main(String[] args) {
        User user = new User.UserBuilder("Miranda", "Kerr")
                            .age(35)
                            .phone("1234567890")
                            .address("some where cali")
                            .build();

        System.out.println(user.getFirstName() + " " + user.getLastName()
                            + ", live in " + user.getAddress());
    }
}
