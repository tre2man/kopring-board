package com.example.namuboard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Controller("api/post")
class PostController {
    /**
     * 게시글 목록 조회하기
     */
    @GetMapping
    fun getPosts() {
    }

    /**
     * 게시글 생성하기
     */
    @PostMapping
    fun createPost() {
    }

    /**
     * 게시글 수정하기
     */
    @PatchMapping
    fun updatePost() {
    }

    /**
     * 게시글 삭제하기
     */
    @DeleteMapping
    fun deletePost() {
    }
}