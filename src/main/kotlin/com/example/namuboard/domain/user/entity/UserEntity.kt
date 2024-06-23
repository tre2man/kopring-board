package com.example.namuboard.domain.user.entity

import com.example.namuboard.domain.common.CommonEntity
import com.example.namuboard.domain.post.entity.PostEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.springframework.security.crypto.bcrypt.BCrypt

@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted_at = current_timestamp WHERE id = ?")
class UserEntity(
    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    private var password: String // 비밀번호 프로퍼티를 private로 변경
): CommonEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user", orphanRemoval = true)
    var posts: MutableList<PostEntity> = mutableListOf()

    init {
        this.password = encryptPassword(password) // 초기화 블록에서 암호화
    }

    fun updatePassword(newPassword: String) {
        this.password = encryptPassword(newPassword)
    }

    private fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    companion object {
        fun of(email: String, password: String): UserEntity {
            return UserEntity(email, password)
        }
    }
}

//@Entity
//@Table(name = "`user`")
//@SQLDelete(sql = "UPDATE `user` SET deleted_at = current_timestamp WHERE id = ?")
//class UserEntity(
//    @Column(nullable = false)
//    var email: String,
//
//    @Column(nullable = false)
//    var password: String
//): CommonEntity() {
//    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user", orphanRemoval = true)
//    var posts: List<PostEntity> = mutableListOf();
//
//    init {
//        this.password = encryptPassword(password)
//    }
//
//    fun setPassword(newPassword: String) {
//        this.password = encryptPassword(newPassword)
//    }
//
//    private fun encryptPassword(password: String): String {
//        // TODO: Implement password encryption
//        return password;
//    }
//
//    companion object {
//        fun of(email: String, password: String): UserEntity {
//            return UserEntity(email, password)
//        }
//    }
//}
