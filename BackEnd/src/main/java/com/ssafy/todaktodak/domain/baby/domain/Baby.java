package com.ssafy.todaktodak.domain.baby.domain;


import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.user.domain.User;
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

    @NotNull
    @Column(name = "baby_name")
    private String babyName;

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

    public static Baby newBabyCreate(User user, String babyImage){
        return Baby.builder()
                .user(user)
                .babyName("이름")
                .babyNickname("별명")
                .babyImageUrl(babyImage)
                .babyGender(Gender.X)
                .babyConstellation("-")
                .babyBirthYear("-")
                .babyBirthMonth("-")
                .babyBirthDay("-")
                .babyDDay(0)
                .babyJodiak("-")
                .build();
    }

    public void updateBaby(BabyUpdateRequestDto babyUpdateRequestDto,String imageUrl) {
        this.babyNickname = babyUpdateRequestDto.getBabyNickname();
        this.babyName = babyUpdateRequestDto.getBabyName();
        this.babyImageUrl = imageUrl;
        this.babyGender = Gender.ofString(babyUpdateRequestDto.getBabyGender());
        this.babyBirthYear = babyUpdateRequestDto.getBabyBirthYear();
        this.babyBirthMonth = babyUpdateRequestDto.getBabyBirthMonth();
        this.babyBirthDay = babyUpdateRequestDto.getBabyBirthDay();




    }

}

