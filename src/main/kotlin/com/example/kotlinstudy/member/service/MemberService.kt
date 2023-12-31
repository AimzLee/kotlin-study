package com.example.kotlinstudy.member.service

import com.example.kotlinstudy.common.authority.JwtTokenProvider
import com.example.kotlinstudy.common.authority.TokenInfo
import com.example.kotlinstudy.common.exception.InvalidInputException
import com.example.kotlinstudy.common.status.ROLE
import com.example.kotlinstudy.member.dto.LoginDto
import com.example.kotlinstudy.member.dto.MemberDtoRequest
import com.example.kotlinstudy.member.dto.MemberDtoResponse
import com.example.kotlinstudy.member.entity.Member
import com.example.kotlinstudy.member.entity.MemberRole
import com.example.kotlinstudy.member.repository.MemberRepository
import com.example.kotlinstudy.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복 검사
        var member: Member? = memberRepository
            .findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료 되었습니다."
    }

    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtTokenProvider.createToken(authentication)
    }

    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }
}