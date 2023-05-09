package io.tokend.certificates.feature.scanQR.view

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Camera
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseActivity
import io.tokend.certificates.databinding.ActivityScanQrCameraBinding
import io.tokend.certificates.utils.PermissionManager

class ScanQrCameraActivity() : BaseActivity() {

    private lateinit var binding: ActivityScanQrCameraBinding

    private val cameraPermission =
        PermissionManager(Manifest.permission.CAMERA, PERMISSION_REQUEST_CODE)


    override fun onCreateAllowed(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_qr_camera)
        binding.lifecycleOwner = this
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initScanner()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding.zxingBarcodeScanner.setStatusText("")



        startScannerIfHavePermission()
    }

    private fun initScanner() {

        binding.zxingBarcodeScanner.initializeFromIntent(
            IntentIntegrator(this)
                .setBeepEnabled(false)
                // for mixed qr codes (light and dark)
                .addExtra(Intents.Scan.SCAN_TYPE, 2)
                .setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                .setDesiredBarcodeFormats(listOf(BarcodeFormat.QR_CODE.name))
                .createScanIntent()
        )



        binding.zxingBarcodeScanner.decodeContinuous(scanViewCallback)
    }

    override fun onStop() {
        super.onStop()
        binding.zxingBarcodeScanner.pause()
    }

    override fun onStart() {
        super.onStart()
        if (cameraPermission.check(this)) {
            binding.zxingBarcodeScanner.resume()
        }
    }

    private fun startScannerIfHavePermission() {
        cameraPermission.check(
            activity = this,
            action = {
                binding.zxingBarcodeScanner.resume()
            },
            deniedAction = {
                binding.zxingBarcodeScanner.pause()
                finishWithCancel()
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraPermission.handlePermissionResult(requestCode, permissions, grantResults)
    }

    private fun finishWithCancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun finishWithAccess(result: String) {
        setResult(RESULT_OK, Intent().also {
            it.putExtra(RESULT_CODE, result)
        })
        finish()
    }

    private val scanViewCallback = object : BarcodeCallback {


        override fun barcodeResult(result: BarcodeResult) {
            finishWithAccess(result.text)
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>) {}
    }


    companion object {
        private const val PERMISSION_REQUEST_CODE = 404
        const val RESULT_CODE = "RESULT_CODE"
    }
}