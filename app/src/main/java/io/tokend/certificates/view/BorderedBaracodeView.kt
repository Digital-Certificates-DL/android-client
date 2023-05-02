package io.tokend.certificates.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.Size
import kotlin.math.roundToInt


class BorderedBaracodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BarcodeView(context, attrs, defStyleAttr) {


    override fun getFramingRectSize(): Size {
        return Size(dpToPx(186), dpToPx(186))
    }

    override fun calculateFramingRect(
        container: Rect?,
        surface: Rect?
    ): Rect {

        // intersection is the part of the container that is used for the preview
        val intersection = Rect(container)

        if (framingRectSize != null) {
            // Specific size is specified. Make sure it's not larger than the container or surface.
            val horizontalMargin = Math.max(0, (intersection.width() - framingRectSize.width) / 2)
            val verticalMargin = Math.max(0, (intersection.height() - framingRectSize.height) / 2)
            intersection.inset(horizontalMargin, verticalMargin)
            return intersection
        }
        // margin as 10% (default) of the smaller of width, height
        // margin as 10% (default) of the smaller of width, height
        val margin =
            Math.min(intersection.width() * marginFraction, intersection.height() * marginFraction)
                .toInt()
        intersection.inset(margin, margin)
        if (intersection.height() > intersection.width()) {
            // We don't want a frame that is taller than wide.
            intersection.inset(0, (intersection.height() - intersection.width()) / 2)
        }
        return intersection
    }

    private fun dpToPx(dp: Int): Int {
        return ((dp * Resources.getSystem().displayMetrics.density).roundToInt())
    }
}