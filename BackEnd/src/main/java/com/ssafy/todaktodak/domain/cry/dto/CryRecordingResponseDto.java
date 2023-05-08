package com.ssafy.todaktodak.domain.cry.dto;

import com.ssafy.todaktodak.domain.cry.domain.Cry;
import com.ssafy.todaktodak.domain.device.domain.Device;
import com.ssafy.todaktodak.domain.device.dto.DeviceInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryRecordingResponseDto {

    private Integer cryId;

    public static CryRecordingResponseDto ofCry(Cry cry) {

        return CryRecordingResponseDto.builder()
                .cryId(cry.getCryId())
                .build();

    }
}
