package io.tokend.certificates.utils

import android.content.Context
import android.content.Intent

object ShareUtil {

    fun shareText(context: Context, textToShare: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}