package com.example.namuboard.domain.authority.repository

import com.example.namuboard.domain.authority.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String> 
