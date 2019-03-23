package ood.designpatterns;

import ood.designpatterns.builder.User;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        User user = new User.UserBuilder("Miranda", "Kerr")
                .age(35).phone("1234567890").address("some where cali").build();
    }
}
