package io.tokend.certificates.feature.scanQR.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseFragment
import io.tokend.certificates.databinding.FragmentScanQrImageBinding

class ScanQrImageFragment : BaseFragment() {

    private lateinit var binding: FragmentScanQrImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_scan_qr_camera, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }
}