package io.tokend.certificates.feature.info.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import io.tokend.certificates.R
import io.tokend.certificates.base.view.BaseFragment
import io.tokend.certificates.databinding.FragmentInfoBinding
import io.tokend.certificates.feature.verify.model.CertificateQrData

class InfoFragment : BaseFragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var certificate: CertificateQrData
    private var isConfirmed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = this

        certificate = arguments?.getSerializable(CERTIFICATE_KEY) as CertificateQrData
            ?: throw IllegalArgumentException("No Certificate")
        isConfirmed = arguments?.getBoolean(IS_CONFIRMED_KEY)
            ?: throw IllegalAccessException("No Confirm data")
        binding.certificate = certificate

        val date = certificate.message.split(" ")[0]
        binding.dateTextView.text = date
        binding.isConfirmed = isConfirmed
        initButtons()
        binding.linkTextView.movementMethod
        return binding.root
    }

    private fun initButtons() {
        clickHelper.addViews(
            binding.backButton
        )

        clickHelper.setOnClickListener {
            when (it.id) {
                binding.backButton.id -> {
                    parentFragmentManager.popBackStack()
                }
            }
        }

    }
    companion object {

        private const val CERTIFICATE_KEY = "CERTIFICATE_KEY"
        private const val IS_CONFIRMED_KEY = "IS_CONFIRMED_KEY"

        fun newInstance(certificate: CertificateQrData, isConfirmed: Boolean): InfoFragment =
            InfoFragment().also {
                it.arguments = bundleOf(
                    CERTIFICATE_KEY to certificate,
                    IS_CONFIRMED_KEY to isConfirmed
                )
            }
    }
}