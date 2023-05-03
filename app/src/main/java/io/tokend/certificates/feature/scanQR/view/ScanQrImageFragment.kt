package io.tokend.certificates.feature.scanQR.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseFragment
import io.tokend.certificates.databinding.FragmentScanQrImageBinding


class ScanQrImageFragment : BaseFragment() {

    private lateinit var binding: FragmentScanQrImageBinding
    private lateinit var selectedImage: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_scan_qr_image, container, false)
        binding.lifecycleOwner = this
        selectedImage = Uri.parse(arguments?.getString(URI_TAG))

        binding.cropImage.setImageUriAsync(selectedImage)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        clickHelper.addViews(binding.cropImageButton)

        clickHelper.setOnClickListener {
            when (it.id) {
                binding.cropImageButton.id -> {
                    val croppedImage = binding.cropImage.getCroppedImage()

                    val width: Int = croppedImage!!.width
                    val height: Int = croppedImage.height
                    val pixels = IntArray(width * height)
                    croppedImage.getPixels(pixels, 0, width, 0, 0, width, height)
                    croppedImage.recycle()

                    val source = RGBLuminanceSource(width, height, pixels)
                    val bBitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = MultiFormatReader()
                    try {
                        val result = reader.decode(bBitmap)
                        toastManager.long(

                            "The content of the QR image is: " + result.getText()
                        )
                    } catch (e: NotFoundException) {
                        Log.e("TAG", "decode exception", e)
                    }
                }
            }
        }
    }


    companion object {
        private const val URI_TAG = "URI_TAG"
        fun newInstance(uri: String): ScanQrImageFragment {
            return ScanQrImageFragment().also {
                it.arguments = bundleOf(
                    URI_TAG to uri
                )
            }
        }
    }


}