package com.gmainer.budgetbook.account.model

import jakarta.persistence.*

@Entity
@Table(name = "account_icons")
data class AccountIcon(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_icons_id_seq")
    @SequenceGenerator(name = "account_icons_id_seq", sequenceName = "account_icons_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(name = "ui_order", nullable = false)
    val uiOrder: Int
)
