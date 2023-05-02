package io.tokend.certificates.feature.verify.model

data class Input(
    val sequence: String,
    val witness: String,
    val script: String,
    val index: String,
    val prev_out: PrevOut
)

data class PrevOut(
    val addr: String,
    val n: String,
    val script: String,
    val spending_outpoints: List<SpendingOutpoints>,
    val spent: Boolean,
    val tx_index: String,
    val type: String,
    val value: String
)

data class SpendingOutpoints(
    val n: String,
    val tx_index: String
)

data class Output(
    val type: String,
    val spent: Boolean,
    val value: String,
    val spending_outpoints: List<SpendingOutpoints>,
    val n: String,
    val tx_index: String,
    val script: String,
    val addr: String?
)

data class Transaction(
    val hash: String,
    val ver: String,
    val vin_sz: String,
    val vout_sz: String,
    val size: String,
    val weight: String,
    val fee: String,
    val relayed_by: String,
    val lock_time: String,
    val tx_index: String,
    val double_spend: Boolean,
    val time: String,
    val block_index: String,
    val block_height: String,
    val inputs: List<Input>,
    val out: List<Output>
)

