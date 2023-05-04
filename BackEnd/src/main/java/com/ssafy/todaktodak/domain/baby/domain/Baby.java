package com.ssafy.todaktodak.domain.baby.domain;


import com.ssafy.todaktodak.domain.baby.dto.BabyUpdateRequestDto;
import com.ssafy.todaktodak.domain.cry.domain.Cry;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.user.domain.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
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
    private Integer babyBirthYear;

    @Column(name = "baby_birth_month")
    private Integer babyBirthMonth;

    @Column(name = "baby_birth_day")
    private Integer babyBirthDay;

    @Column(name = "baby_d_day")
    private Integer babyDDay;

    @Column(name = "baby_zodiak")
    private String babyZodiak;

    @Column(name = "baby_constellation")
    private String babyConstellation;

    @OneToMany(mappedBy = "baby",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Cry> cryList = new ArrayList<>();

    @OneToOne(mappedBy = "baby")
    private Device device;



    @Column(name = "baby_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime babyCreatedDate;


    @Column(name = "baby_updated_date")
    @LastModifiedDate
    private LocalDateTime babyUpdatedDate;



    public static Baby newBabyCreate(User user, String babyImage){
        return Baby.builder()
                .user(user)
                .babyName("이름")
                .babyNickname("별명")
                .babyImageUrl(babyImage)
                .babyGender(Gender.X)
                .babyConstellation("-")
                .babyBirthYear(DateTime.now().getYear())
                .babyBirthMonth(DateTime.now().getMonthOfYear())
                .babyBirthDay(DateTime.now().getDayOfMonth())
                .babyDDay(0)
                .babyZodiak("-")
                .build();
    }

    public void updateBaby(BabyUpdateRequestDto babyUpdateRequestDto,String babyConstellation,String babyZodiak,Integer babyDDay,String imageUrl) {
        this.babyNickname = babyUpdateRequestDto.getBabyNickname();
        this.babyName = babyUpdateRequestDto.getBabyName();
        this.babyImageUrl = imageUrl;
        this.babyGender = Gender.ofString(babyUpdateRequestDto.getBabyGender());
        this.babyBirthYear = babyUpdateRequestDto.getBabyBirthYear();
        this.babyBirthMonth = babyUpdateRequestDto.getBabyBirthMonth();
        this.babyBirthDay = babyUpdateRequestDto.getBabyBirthDay();
        this.babyConstellation = babyConstellation;
        this.babyZodiak = babyZodiak;
        this.babyDDay = babyDDay;




    }

}

