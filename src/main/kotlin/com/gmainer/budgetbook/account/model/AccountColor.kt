package com.gmainer.budgetbook.account.model

import jakarta.persistence.*

@Entity
@Table(name = "account_colors")
data class AccountColor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_colors_id_seq")
    @SequenceGenerator(name = "account_colors_id_seq", sequenceName = "account_colors_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val name: String,

    @Column(name = "ui_order", nullable = false)
    val uiOrder: Int
)
