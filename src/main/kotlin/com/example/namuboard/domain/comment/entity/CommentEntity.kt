package com.example.namuboard.domain.comment.entity

import com.example.namuboard.domain.common.CommonEntity
import com.example.namuboard.domain.post.entity.PostEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET deleted_at = current_timestamp WHERE id = ?")
class CommentEntity(
    @Column(nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val post: PostEntity,
): CommonEntity() {
    companion object {
        fun of(content: String, post: PostEntity): CommentEntity {
            return CommentEntity(content, post)
        }
    }
}