package com.balai.user.model.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@MappedSuperclass
@RequiredArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
