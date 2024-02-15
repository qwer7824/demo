package com.today.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

    @Getter
    @EntityListeners(AuditingEntityListener.class)
    @MappedSuperclass
    public class BaseEntity {

        @CreatedDate
        @Column(updatable = false)
        private LocalDate createdAt;

        @LastModifiedDate
        private LocalDate updatedAt;

}
