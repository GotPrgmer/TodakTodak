package com.ssafy.todaktodak.domain.cry.domain;

import com.ssafy.todaktodak.domain.baby.domain.Baby;
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
@Table(name = "cry")
public class Cry {


    @Id
    @GeneratedValue
    @Column(name = "cry_id")
    private Integer cryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private Baby baby;


    @NotNull
    private Integer cryTime;

    @Column(name = "cry_created_date", updatable = false)
    @CreatedDate
    private LocalDateTime cryCreatedDate;


    @Column(name = "cry_updated_date")
    @LastModifiedDate
    private LocalDateTime cryUpdatedDate;




}
