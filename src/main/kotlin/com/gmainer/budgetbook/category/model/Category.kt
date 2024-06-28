package com.gmainer.budgetbook.category.model

import com.gmainer.budgetbook.category.dto.CategoryResponse
import jakarta.persistence.*


@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_id_seq")
    @SequenceGenerator(name = "categories_id_seq", sequenceName = "categories_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", nullable = false)
    val icon: CategoryIcon,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    val color: CategoryColor,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val categoryType: CategoryType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Category? = null
)

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id,
        name = this.name,
        iconName = this.icon.name,
        colorCode = this.color.code,
        type = this.categoryType,
        parentId = this.parent?.id,
        parentName = this.parent?.name
    )
}
