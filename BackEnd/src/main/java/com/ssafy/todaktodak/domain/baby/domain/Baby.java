package com.ssafy.todaktodak.domain.baby.domain;



import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.user.domain.Role;
import com.ssafy.todaktodak.domain.user.domain.User;
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

@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "baby")
public class Baby {


    @Id
    @GeneratedValue
    @Column(name = "baby_id")
    private Integer babyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull
    @Column(name = "baby_nickname")
    private String babyNickname;

    @Column(name = "baby_image_url")
    private String babyImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "baby_gender")
    private Gender babyGender;

    @Column(name = "baby_birth_year")
    private String babyBirthYear;

    @Column(name = "baby_birth_month")
    private String babyBirthMonth;

    @Column(name = "baby_birth_day")
    private String babyBirthDay;

    @Column(name = "baby_d_day")
    private Integer babyDDay;

    @Column(name = "baby_jodiak")
    private String babyJodiak;

    @Column(name = "baby_constellation")
    private String babyConstellation;


    @Column(name = "baby_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime babyCreatedDate;


    @Column(name = "baby_updated_date")
    @LastModifiedDate
    private LocalDateTime babyUpdatedDate;


    @OneToOne(mappedBy = "baby")
    private Device device;

    public static Baby newBabyCreate(User user){
        return Baby.builder()
                .user(user)
                .babyNickname("별명")
                .babyImageUrl("")
                .babyGender(Gender.X)
                .babyConstellation("-")
                .babyBirthYear("-")
                .babyBirthMonth("-")
                .babyBirthDay("-")
                .babyDDay(0)
                .babyJodiak("-")
                .build();
    }

}

