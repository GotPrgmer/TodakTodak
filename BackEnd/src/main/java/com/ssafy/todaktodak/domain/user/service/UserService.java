package com.ssafy.todaktodak.domain.user.service;

import com.ssafy.todaktodak.domain.user.domain.User;
import com.ssafy.todaktodak.domain.user.dto.*;
import com.ssafy.todaktodak.domain.user.repository.UserRepository;
import com.ssafy.todaktodak.global.error.CustomException;
import com.ssafy.todaktodak.global.error.ErrorCode;
import com.ssafy.todaktodak.global.storage.S3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${s3-user-default-image}")
    private String DEFAULT_USER_PROFILE;

    private final S3Client s3Client;


    @Transactional
    public UserInfoResponseDto userInfo(String userId) {
        User findUser = findUserWithUserId(userId);
        return UserInfoResponseDto.of(findUser);
    }

    @Transactional
    public UserInfoUpdateResponseDto userInfoUpdate(String userId, UserInfoUpdateRequestDto request, MultipartFile file) throws IOException {
        User findUser = findUserWithUserId(userId);
        String imageUrl = null;

        // 사용자 이미지가 있을 때
        if (file != null && !file.isEmpty()) {
            String preImg = findUser.getUserImageUrl();
            if (preImg != null && !preImg.startsWith(DEFAULT_USER_PROFILE)) {
                s3Client.deleteFile(preImg);
            }
            imageUrl = s3Client.uploadFile(file);
            // 사용자 이미지 없을 때
        } else {
            imageUrl = findUser.getUserImageUrl();
        }

        findUser.updateUser(request, imageUrl);

        return UserInfoUpdateResponseDto.of(findUser);

    }

    @Transactional
    public UserFcmUpdateResponseDto userFcmEdit(String userId, UserFcmUpdateRequestDto request) {
        User findUser = findUserWithUserId(userId);
        findUser.updateUserFcm(request);
        return UserFcmUpdateResponseDto.ofFcm(request.getFcmKey());
    }

    public User findUserWithUserId(String userId) {
        Integer userIdToInteger = Integer.parseInt(userId);

        // 사용자 조회
        Optional<User> user = userRepository.findUserByUserId(userIdToInteger);
        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }
}
