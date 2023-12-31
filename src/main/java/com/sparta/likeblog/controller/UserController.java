package com.sparta.likeblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.likeblog.dto.ApiResponseDto;
import com.sparta.likeblog.dto.AuthRequestDto;
import com.sparta.likeblog.jwt.JwtUtil;
import com.sparta.likeblog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signUp(@Valid @RequestBody AuthRequestDto requestDto) {

        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody AuthRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        //JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(), loginRequestDto.getRole()));

        return ResponseEntity.ok().body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }
}