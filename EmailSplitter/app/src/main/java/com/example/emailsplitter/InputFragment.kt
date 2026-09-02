package com.example.emailsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.emailsplitter.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pieces = email.split('@')

            if (pieces.size != 2 || pieces.any(String::isEmpty)) {
                Toast.makeText(requireContext(), "Invalid email!", Toast.LENGTH_SHORT).show()
            } else {
                val username = pieces[0]
                val domain = pieces[1]

                val resultFragment = ResultFragment.newInstance(username, domain)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, resultFragment)
                    .addToBackStack(null)
                    .commit()

                Toast.makeText(requireContext(), "Data passed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}