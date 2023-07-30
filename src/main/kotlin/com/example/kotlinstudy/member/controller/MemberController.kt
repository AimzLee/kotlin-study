package com.example.kotlinstudy.member.controller

import com.example.kotlinstudy.common.authority.TokenInfo
import com.example.kotlinstudy.common.dto.BaseResponse
import com.example.kotlinstudy.member.dto.LoginDto
import com.example.kotlinstudy.member.dto.MemberDtoRequest
import com.example.kotlinstudy.member.dto.MemberDtoResponse
import com.example.kotlinstudy.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    @GetMapping("/info/{id}")
    fun searchMyInfo(@PathVariable id: Long): BaseResponse<MemberDtoResponse> {
        val response = memberService.searchMyInfo(id)
        return BaseResponse(data = response)
    }

}