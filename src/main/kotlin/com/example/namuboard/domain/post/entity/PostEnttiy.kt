package com.example.namuboard.domain.post.entity

import com.example.namuboard.domain.board.entity.BoardEntity
import com.example.namuboard.domain.comment.entity.CommentEntity
import com.example.namuboard.domain.common.CommonEntity
import com.example.namuboard.domain.user.entity.UserEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET deleted_at = current_timestamp WHERE id = ?")
class PostEntity(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    var board: BoardEntity
): CommonEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post", orphanRemoval = true)
    var comments: List<CommentEntity> = mutableListOf();

    companion object {
        fun of(title: String, content: String, user: UserEntity, board: BoardEntity): PostEntity {
            return PostEntity(title, content, user, board)
        }
    }
}