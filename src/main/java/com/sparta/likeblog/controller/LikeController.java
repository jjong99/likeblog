package com.sparta.likeblog.controller;

import com.sparta.likeblog.dto.PostResponseDto;
import com.sparta.likeblog.security.UserDetailsImpl;
import com.sparta.likeblog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public PostResponseDto addLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return likeService.addLike(userDetails.getUser(), postId);
    }

    @DeleteMapping("/{postId}")
    public PostResponseDto cancelLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return likeService.cancelLike(userDetails.getUser(), postId);
    }


}
