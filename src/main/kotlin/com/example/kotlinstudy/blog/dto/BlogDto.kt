package com.example.kotlinstudy.blog.dto

data class BlogDto(  // <- data 붙여주면 예쁘게 나옴
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
)