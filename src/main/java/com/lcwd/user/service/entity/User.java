package com.lcwd.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Transient
    private List<Rating> ratings=new ArrayList<>();
}
