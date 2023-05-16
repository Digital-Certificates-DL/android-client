package io.tokend.certificates.extensions

fun String.decodeHex(): String {
    require(length % 2 == 0) {"Must have an even length"}
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
        .toString(Charsets.ISO_8859_1)  // Or whichever encoding your input uses
}