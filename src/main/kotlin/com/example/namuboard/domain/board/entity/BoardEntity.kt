package com.example.namuboard.domain.board.entity

import com.example.namuboard.domain.post.entity.PostEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "board")
@SQLDelete(sql = "UPDATE board SET deleted_at = current_timestamp WHERE id = ?")
class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val title: String? = null

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    val posts: List<PostEntity> = mutableListOf()
}
