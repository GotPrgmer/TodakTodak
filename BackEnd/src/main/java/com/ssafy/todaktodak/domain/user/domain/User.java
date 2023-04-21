package com.ssafy.todaktodak.domain.user.domain;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
import com.ssafy.todaktodak.global.auth.oauth.dto.SocialUserResponseDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "user_nickname")
    private String userNickname;

    @NotNull
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role userRole; // USER, MANAGER, ADMIN

    @Column(name = "user_imageUrl")
    private String userImageUrl;


    @Column(name = "user_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime userCreatedDate;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Baby> babys = new ArrayList<Baby>();


    @Column(name = "user_updated_date")
    @LastModifiedDate
    private LocalDateTime userUpdatedDate;

    public static User principalUser(Integer userId, Role role) {
        return User.builder()
                .userId(userId)
                .userRole(role)
                .build();
    }

    public static User kakaoSignupMember( SocialUserResponseDto socialUserResponseDto) {
        return User.builder()
                .userEmail(socialUserResponseDto.getEmail())
                .userNickname(socialUserResponseDto.getName())
                .userImageUrl(socialUserResponseDto.getImageUrl())
                .userRole(Role.USER)
                .build();
    }
}
