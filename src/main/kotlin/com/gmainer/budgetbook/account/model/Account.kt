package com.gmainer.budgetbook.account.model

import com.gmainer.budgetbook.account.dto.AccountResponse
import jakarta.persistence.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_id_seq")
    @SequenceGenerator(name = "accounts_id_seq", sequenceName = "accounts_id_seq", allocationSize = 1)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", nullable = false)
    val icon: AccountIcon,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    val color: AccountColor
)

fun Account.toResponse(): AccountResponse {
    return AccountResponse(
        id = this.id,
        name = this.name,
        iconName = this.icon.name,
        iconOrder = this.icon.uiOrder,
        colorCode = this.color.code,
        colorOrder = this.color.uiOrder
    )
}
