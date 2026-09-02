package com.example.emailsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emailsplitter.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_USERNAME = "arg_username"
        private const val ARG_DOMAIN = "arg_domain"

        fun newInstance(username: String, domain: String): ResultFragment {
            val fragment = ResultFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_USERNAME, username)
                putString(ARG_DOMAIN, domain)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.userView.text = arguments?.getString(ARG_USERNAME)
        binding.domainView.text = arguments?.getString(ARG_DOMAIN)
    }
}