package com.example.helloandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redButton = view.findViewById<Button>(R.id.redButton)
        val blueButton = view.findViewById<Button>(R.id.blueButton)
        val greenButton = view.findViewById<Button>(R.id.greenButton)
        val purpleButton = view.findViewById<Button>(R.id.purpleButton)
        val orangeButton = view.findViewById<Button>(R.id.orangeButton)

        // Use the same setup method for all five buttons.
        setupColorButton(redButton)
        setupColorButton(blueButton)
        setupColorButton(greenButton)
        setupColorButton(purpleButton)
        setupColorButton(orangeButton)
    }

    /**
     * Navigate to the second fragment if the button is clicked
     */
    private fun setupColorButton(button: Button) {
        button.setOnClickListener {
            navigateToSecondFragment(button.text.toString())
        }
    }

    /**
     * Creates SecondFragment and passes the selected button text as text ie (yellow, green)
     */
    private fun navigateToSecondFragment(selectedColor: String) {

        val secondFragment = SecondFragment()

        val bundle = Bundle()
        bundle.putString("selectedColor", selectedColor)

        secondFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.main, secondFragment)
            .addToBackStack(null)
            .commit()
    }
}