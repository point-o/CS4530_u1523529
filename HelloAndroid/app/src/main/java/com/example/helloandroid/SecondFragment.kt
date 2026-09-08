package com.example.helloandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedColorTextView =
            view.findViewById<TextView>(R.id.selectedColorTextView)

        val backButton =
            view.findViewById<Button>(R.id.backButton)

        // retrieve the text that from first fragment
        val selectedColor =
            arguments?.getString("selectedColor") ?: "No color selected"

        selectedColorTextView.text = selectedColor

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}