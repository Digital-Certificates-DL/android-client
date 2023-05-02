package io.tokend.certificates.feature.verify.logic

import org.bitcoinj.core.Address
import org.bitcoinj.core.Base58
import org.bitcoinj.core.ECKey.signedMessageToKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.script.Script

object BitcoinVerifier {

    private val mainAddress = "1BooKnbm48Eabw3FdPgTSudt9u4YTWKBvf"

    fun verifyBitcoinMessage(message: String, address: String, signature: String): Boolean {
        if (mainAddress != address) {
            return false
        }
        val ecKey = signedMessageToKey(message, signature)
        val params = NetworkParameters.fromID(NetworkParameters.ID_MAINNET)
        val addressV =
            Base58.encodeChecked(0, Address.fromKey(params, ecKey!!, Script.ScriptType.P2PKH).hash)
        if (address == addressV) return true
        return false
    }
}