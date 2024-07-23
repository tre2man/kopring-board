@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.namuboard.domain.user.entity

import com.example.namuboard.domain.authority.entity.Authority
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long? = null,
    @Column(name = "username", length = 50, unique = true)
    var username: String,
    @Column(name = "password", length = 100)
    var password: String,
    @Column(name = "nickname", length = 50)
    var nickname: String,
    @Column(name = "activated")
    var activated: Boolean,
) {
    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")],
    )
    var authorities: Set<Authority> = mutableSetOf()

    companion object {
        fun of(
            username: String,
            password: String,
            nickname: String,
            authority: Authority,
        ): User {
            val user =
                User(
                    username = username,
                    password = password,
                    nickname = nickname,
                    activated = true,
                )
            user.authorities = mutableSetOf(authority)
            return user
        }
    }
}
