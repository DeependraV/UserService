package com.lcwd.user.service.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "micro_user")
public class User {
    @Id
    @Column(name = "ID")
    private String userId;
    @Column(name = "NAME", length = 33)
    private String name;
    @Column(name = "EMAIL")
    private  String email;
    @Column(name = "ABOUT")
    private  String about;

    //This annotation is used to not store data is Database
    @Transient
    private List<Rating> ratings=new ArrayList<>();
}
