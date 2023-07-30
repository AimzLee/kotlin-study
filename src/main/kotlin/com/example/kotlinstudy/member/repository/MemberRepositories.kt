package com.example.kotlinstudy.member.repository

import com.example.kotlinstudy.member.entity.Member
import com.example.kotlinstudy.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    // ID 중복 검사를 위해 필요
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>