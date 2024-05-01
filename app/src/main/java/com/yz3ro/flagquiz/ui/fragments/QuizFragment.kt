package com.yz3ro.flagquiz.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yz3ro.flagquiz.R
import com.yz3ro.flagquiz.databinding.FragmentQuizBinding
import com.yz3ro.flagquiz.ui.viewModels.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    val viewModel: QuizViewModel by viewModels()
    private val randomOptions = mutableListOf<String>()
    var selectedCountry: String? = null
    var score = 0
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getRandomCountry()
        binding.apply {
            binding.btnOption1.setOnClickListener {
                checkAnswer(
                    binding.btnOption1.text.toString(),
                    selectedCountry.toString(),
                    binding.btnOption1
                )
            }

            btnOption2.setOnClickListener {
                checkAnswer(
                    binding.btnOption2.text.toString(),
                    selectedCountry.toString(),
                    binding.btnOption2
                )
            }

            btnOption3.setOnClickListener {
                checkAnswer(
                    binding.btnOption3.text.toString(),
                    selectedCountry.toString(),
                    binding.btnOption3
                )
            }

            btnOption4.setOnClickListener {
                checkAnswer(
                    binding.btnOption4.text.toString(),
                    selectedCountry.toString(),
                    binding.btnOption4
                )
            }
        }
    }

    private fun observeData() {
        viewModel.randomCountryList.observe(viewLifecycleOwner) { countries ->
            if (countries.isNotEmpty()) {
                selectedCountry = countries.first().country_name
                val flagUrl = countries.first().flag_url
                countries.first().region
                Glide.with(this)
                    .load(flagUrl)
                    .into(binding.imgFlag)
                viewModel.getRandomCountryName(selectedCountry.toString())
            }
        }
        viewModel.randomCountryNameList.observe(viewLifecycleOwner) { countryNames ->
            if (countryNames.size == 3) {
                val correctCountryName = selectedCountry
                setRandomButtonTexts(countryNames, correctCountryName.toString())
            } else {
                val correctCountryName = selectedCountry
                setRandomButtonTexts(countryNames, correctCountryName.toString())
            }
        }
    }

    private fun setRandomButtonTexts(countryNames: List<String>, correctCountryName: String) {
        randomOptions.clear()
        randomOptions.add(correctCountryName)
        randomOptions.addAll(countryNames.filter { it != correctCountryName })
        randomOptions.shuffle()
        binding.apply {
            btnOption1.text = randomOptions.getOrNull(0) ?: ""
            btnOption2.text = randomOptions.getOrNull(1) ?: ""
            btnOption3.text = randomOptions.getOrNull(2) ?: ""
            btnOption4.text = randomOptions.getOrNull(3) ?: ""
        }
    }

    fun checkAnswer(selectedAnswer: String, correctAnswer: String, button: Button) {
        if (selectedAnswer == correctAnswer) {
            button.isEnabled = false
            score += 10
            handler.postDelayed({ loadNewQuestion() }, 800)
            binding.txtScore.text = score.toString()
            button.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.true_option))
            handler.postDelayed({
                button.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.option))
            }, 500)
        } else {
            score -= 10
            if (score <= 0) {
                showGameOverPopup()
            }
            binding.txtScore.text = score.toString()
            button.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.false_option))
            handler.postDelayed({
                button.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.option))
            }, 500)

        }
    }

    private fun loadNewQuestion() {
        viewModel.getRandomCountry()
        viewModel.getRandomCountryName(selectedCountry.toString())
        binding.apply {
            btnOption1.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.option))
            btnOption2.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.option))
            btnOption3.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.option))
            btnOption4.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.option))
            btnOption1.isEnabled = true
            btnOption2.isEnabled = true
            btnOption3.isEnabled = true
            btnOption4.isEnabled = true
        }
    }

    private fun showGameOverPopup() {
        val inflater = requireActivity().layoutInflater
        val popupView = inflater.inflate(R.layout.popup_lose, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val btnOk = popupView.findViewById<Button>(R.id.button2)
        btnOk.setOnClickListener {
            popupWindow.dismiss()
            resetGame()
        }
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(requireActivity().window.decorView, Gravity.CENTER, 0, 0)
    }

    private fun resetGame() {
        score = 0
        viewModel.getRandomCountry()
        viewModel.getRandomCountryName(selectedCountry.toString())
        binding.txtScore.text = score.toString()
    }

}

