package com.gmainer.budgetbook.category.model

import jakarta.persistence.*

@Entity
@Table(name = "category_colors")
data class CategoryColor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_colors_id_seq")
    @SequenceGenerator(name = "category_colors_id_seq", sequenceName = "category_colors_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val code: String,

    val name: String? = null,

    @Column(name = "ui_order")
    val uiOrder: Int? = null
)
