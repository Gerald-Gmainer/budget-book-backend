package com.gmainer.budgetbook.category.model

import jakarta.persistence.*

@Entity
@Table(name = "category_icons")
data class CategoryIcon(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_icons_id_seq")
    @SequenceGenerator(name = "category_icons_id_seq", sequenceName = "category_icons_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(name = "ui_order")
    val uiOrder: Int?
)
