package com.example.namuboard.domain.authority.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "authority")
class Authority(
    @Id
    @Column(name = "authority_name", length = 50)
    val authorityName: String,
) : Serializable {
    companion object {
        fun of(authorityName: String): Authority = Authority(authorityName)
    }
}
