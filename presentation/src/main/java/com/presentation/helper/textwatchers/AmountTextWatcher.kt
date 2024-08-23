package com.presentation.helper.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class AmountTextWatcher (
    private val editText: EditText,
    private val maxAmount: Double? = null,
    private val minAmount: Double? = null,
    private val decimalScale: Int = 2 //  round fraction to digit,
) : TextWatcher {

    //we need to know if the user is erasing or inputting some new character
    private var backspacingFlag = false

    //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
    private var editedFlag = false

    //we need to mark the cursor position and restore it after the edition
    private var cursorComplement = 0

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        //we store the cursor local relative to the end of the string in the EditText before the edition
        cursorComplement = s.length - editText.selectionStart
        //we check if the user ir inputing or erasing a character
        backspacingFlag = count > after
    }

    override fun onTextChanged(
        s: CharSequence, start: Int, before: Int, count: Int
    ) {

        try {

            val string = s.toString()

            if (string.isEmpty()) return
            if (string.startsWith(".")) {
                editText.setText(string.replaceFirst(".", ""))
                return
            }

            var cleanString = string

            //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
            //if the flag is false, this is a original user-typed entry. so we go on and do some magic
            if (!editedFlag) {

                if (!backspacingFlag) {

                    val parseAmount = cleanString.toDoubleOrNull()

                    //  Checks if the amount is greater or less than the max amount ----------------
                    if (parseAmount != null && maxAmount != null && parseAmount > maxAmount) cleanString =
                        maxAmount.toString()
                    //------------------------------------------------------------------------------

                    //  Checks if the amount is greater or less than the max amount ----------------
                    if (parseAmount != null && minAmount != null && parseAmount > minAmount) cleanString =
                        minAmount.toString()
                    //------------------------------------------------------------------------------

                    //  round fraction to digit ----------------------------------------------------
                    if (cleanString.contains(".")) {
                        val fractionSplit: List<String> = cleanString.split(".")

                        if (fractionSplit.isNotEmpty() && fractionSplit[1] != "") {
                            val fraction = fractionSplit[1]

                            if (fraction.length > decimalScale) {
                                cleanString = cleanString.substring(0, cleanString.length - 1)
                            }
                        }

                    }
                    //------------------------------------------------------------------------------

                    //  convert integer value ------------------------------------------------------
                    if (decimalScale == 0) {
                        cleanString = cleanString.replace(".", "")
                    }
                    //------------------------------------------------------------------------------

                    editedFlag = true

                    editText.setText(cleanString)
                    editText.setSelection(editText.text.length - cursorComplement)
                }


            } else editedFlag = false

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun afterTextChanged(p0: Editable?) {
    }

}