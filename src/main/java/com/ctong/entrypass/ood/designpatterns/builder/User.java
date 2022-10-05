package com.ctong.entrypass.ood.designpatterns.builder;

/**
 * This class is demonstrating builder pattern
 */
public class User {

    // names are required and immutable
    private final String firstName;
    private final String lastName;

    private int age;
    private String phone;
    private String address;

    public User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public static class UserBuilder {

        private final String firstName;
        private final String lastName;

        private int age = 0; // default value is 0
        private String phone = ""; // default value is an empty string
        private String address; // default value is null

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        // all the following methods are used to set values for optional fields
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
