package com.presentation.helper.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.presentation.databinding.ViewAmountEditTextBinding
import com.presentation.extension.formatWithoutRedundantDecimal
import com.presentation.extension.requestFocusAndOpenKeyboard
import com.presentation.helper.textwatchers.AmountTextWatcher

class AmountEditTextView (
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(context, attrs)
{

    private val initialAmountValue = 0.0

    private var binding: ViewAmountEditTextBinding
    private var amountTextWatcher: AmountTextWatcher
    private var amount: Double = initialAmountValue


    init {

        binding = ViewAmountEditTextBinding.inflate(LayoutInflater.from(context), this, true)

        binding.apply {

            amountTextWatcher = AmountTextWatcher(binding.tietAmount, decimalScale = 2)
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        binding.tietAmount.addTextChangedListener(amountTextWatcher)
        binding.tietAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {

                amount = returnFormattedInput()

                isInputCorrectFormat.invoke(isInputCorrectFormat())
                afterAmountChanged.invoke(amount)
            }
        })
    }

    private fun returnFormattedInput(): Double {
        return if (binding.tietAmount.text.toString().isEmpty()) initialAmountValue
        else binding.tietAmount.text.toString().toDoubleOrNull() ?: initialAmountValue
    }

    private fun isInputCorrectFormat() = returnFormattedInput() != initialAmountValue


    //  ---------- SHARED FUNCTION START -----------------------------------------------------------

    var isInputCorrectFormat: (Boolean) -> Unit = {}
    var afterAmountChanged: (Double) -> Unit = {}



    fun getAmount(): Double {
        return try {
            binding.tietAmount.text.toString().toDouble()
        } catch (e: Exception) {
            initialAmountValue
        }
    }

    fun getTextInputEditText() = binding.tietAmount

    fun setInputType(type: Int) {
        binding.tietAmount.inputType = type
    }

    fun setAmount(value: Double) {
        binding.apply {
            tietAmount.setText(value.formatWithoutRedundantDecimal())
            tietAmount.setSelection(tietAmount.text?.length ?: 0)
        }
    }

    fun focusInput() {
        binding.tietAmount.requestFocusAndOpenKeyboard()
        binding.tietAmount.setSelection(binding.tietAmount.text?.length ?: 0)
    }


    //  ---------- SHARED FUNCTION END   -----------------------------------------------------------

}