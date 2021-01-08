package com.blockchain.nabu.models.data

import com.blockchain.nabu.datamanagers.custodialwalletimpl.PaymentMethodType
import com.blockchain.nabu.models.responses.banktransfer.LinkBankAttrsResponse
import java.io.Serializable

data class LinkBankTransfer(val id: String, val partner: BankPartner, val attributes: LinkBankAttributes) : Serializable

enum class BankPartner {
    YODLEE;

    fun attributes(attrsResponse: LinkBankAttrsResponse): LinkBankAttributes =
        when (this) {
            YODLEE -> {
                require(attrsResponse.fastlinkUrl != null)
                require(attrsResponse.token != null)
                require(attrsResponse.fastlinkParams != null)
                YodleeAttributes(
                    attrsResponse.fastlinkUrl, attrsResponse.token,
                    attrsResponse.fastlinkParams.configName
                )
            }
        }
}

interface LinkBankAttributes

class YodleeAttributes(val fastlinkUrl: String, val token: String, val configName: String) : LinkBankAttributes,
    Serializable

data class LinkedBank(
    override val id: String,
    override val currency: String,
    val partner: BankPartner,
    val name: String,
    val accountNumber: String,
    val state: LinkedBankState,
    val errorStatus: LinkedBankErrorState
) : Bank {
    override val account: String
        get() = accountNumber
    override val title: String
        get() = name
    override val paymentMethod: PaymentMethodType
        get() = PaymentMethodType.BANK_TRANSFER
}

enum class LinkedBankErrorState {
    ACCOUNT_ALREADY_LINKED,
    NAMES_MISS_MATCHED,
    ACCOUNT_TYPE_UNSUPPORTED,
    UNKNOWN,
    NONE
}

enum class LinkedBankState {
    PENDING,
    BLOCKED,
    ACTIVE,
    UNKNOWN
}

interface Bank : Serializable {
    val currency: String
    val account: String
    val title: String
    val id: String
    val paymentMethod: PaymentMethodType
}