package com.example.namuboard.domain.common

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
@SQLRestriction("deleted_at is NULL")
open class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    @CreatedDate
    var createdAt: LocalDateTime? = null;

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null;

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null;
}