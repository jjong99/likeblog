package com.sparta.likeblog.controller;

import com.sparta.likeblog.dto.CommentResponseDto;
import com.sparta.likeblog.dto.PostResponseDto;
import com.sparta.likeblog.security.UserDetailsImpl;
import com.sparta.likeblog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public PostResponseDto addLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return likeService.addLike(userDetails.getUser(), postId);
    }

    @DeleteMapping("/post/{postId}")
    public PostResponseDto cancelLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return likeService.cancelLike(userDetails.getUser(), postId);
    }

    @PostMapping("post/{postId}/comment/{commentId}")
    public CommentResponseDto addCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @PathVariable Long commentId) {
        return likeService.commentAddLike(userDetails.getUser(), postId, commentId);
    }

    @DeleteMapping("post/{postId}/comment/{commentId}")
    public CommentResponseDto cancelCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @PathVariable Long commentId) {
        return likeService.commentCancelLike(userDetails.getUser(), postId, commentId);
    }


}
