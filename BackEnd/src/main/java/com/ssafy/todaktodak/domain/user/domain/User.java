package com.ssafy.todaktodak.domain.user.domain;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.sun.istack.NotNull;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_imageUrl")
    private String userImageUrl;

    @Column(name = "user_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime userCreatedDate;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Baby> babys = new ArrayList<Baby>();


    @Column(name = "user_updated_date")
    @LastModifiedDate
    private LocalDateTime userUpdatedDate;
}
