package com.ctong.entrypass.ood.designpatterns.builder;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
public class UserLombok {

    @Builder.Default
    String title = "Lombok User";

    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;

    private int age;
    private String phone;
    private String address;

    @Singular
    List<String> children;
}
