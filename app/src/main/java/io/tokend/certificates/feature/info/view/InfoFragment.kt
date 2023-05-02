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
import io.tokend.certificates.feature.verify.model.CertificateData

class InfoFragment : BaseFragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var certificate: CertificateData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = this

        certificate = arguments?.getSerializable(CERTIFICATE_KEY) as CertificateData ?: throw IllegalArgumentException("No Certificate")
        binding.certificate = certificate

        val date = certificate.message.split(" ")[0]
        binding.dateTextView.text = date

        initButtons()
        return binding.root
    }

    private fun initButtons() {
        clickHelper.addViews(
            binding.backButton
        )

        clickHelper.setOnClickListener {
            when(it.id) {
                binding.backButton.id -> {
                    parentFragmentManager.popBackStack()
                }
            }
        }

    }


    companion object {

        private const val CERTIFICATE_KEY = "CERTIFICATE_KEY"

        fun newInstance(certificate: CertificateData): InfoFragment = InfoFragment().also {
            it.arguments = bundleOf(
                CERTIFICATE_KEY to certificate
            )
        }
    }
}