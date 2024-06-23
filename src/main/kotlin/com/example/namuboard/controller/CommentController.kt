package com.example.namuboard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Controller("api/comment")
class CommentController {
    /**
     * 댓글 생성하기
     */
    @PostMapping
    fun createComment() {
    }

    /**
     * 댓글 수정하기
     */
    @PatchMapping
    fun updateComment() {
    }

    /**
     * 댓글 삭제하기
     */
    @DeleteMapping
    fun deleteComment() {
    }
}