package com.ultivic.customkeyboard.keyboard

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.inputmethodservice.InputMethodService
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ultivic.customkeyboard.databinding.KeyboardLayoutBinding

class MyImageKeyboard : InputMethodService() {
    private val binding by lazy { KeyboardLayoutBinding.inflate(layoutInflater) }

    /**
     * Indicates whether the keyboard should display uppercase letters.
     */
    private var isUpperCase = true

    /**
     * Determines if the numeric keyboard is currently visible.
     */
    private var isNumericVisible = true

    /**
     * Handler to manage delayed actions or UI updates.
     */
    private val handler = Handler()

    /**
     * Holds the current text that needs to be converted based on user input.
     */
    private var currentTextToConvert: String = ""

    /**
     * BroadcastReceiver to handle incoming broadcast intents for updating the keyboard.
     */
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                /**
                 * Action to update the keyboard with a new barcode value.
                 */
                "UPDATE_KEYBOARD_BARCODE" -> {
                    val newBarcodeValue = intent.getStringExtra("newBarcodeValue")
                    if (newBarcodeValue != null) {
                        // Update the current text to convert with the new barcode value.
                        // updateBarcode(newBarcodeValue)
                        currentTextToConvert = newBarcodeValue
                    }
                }

                /**
                 * Action to transfer text to the keyboard.
                 */
                "TRANSFER_TEXT" -> {
                    val transferText = intent.getStringExtra("transferText")
                    if (transferText != null) {
                        // Update the current text to convert with the transferred text.
                        // updateBarcodeTransfer(transferText)
                        currentTextToConvert = transferText
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        /**
         * Register the broadcast receiver to listen for specific actions.
         */
        val filter = IntentFilter().apply {
            addAction("UPDATE_KEYBOARD_BARCODE")
            addAction("TRANSFER_TEXT")
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        /**
         * Unregister the broadcast receiver to avoid memory leaks.
         */
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    /**
     * Handles the input view creation for a custom keyboard.
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateInputView(): View {

        binding.apply {
            singleImageView.setOnClickListener {
                currentInputConnection.commitText(currentTextToConvert, 1)
            }

            button1.setOnClickListener { currentInputConnection.commitText("1", 1) }
            button2.setOnClickListener { currentInputConnection.commitText("2", 1) }
            button3.setOnClickListener { currentInputConnection.commitText("3", 1) }
            button4.setOnClickListener { currentInputConnection.commitText("4", 1) }
            button5.setOnClickListener { currentInputConnection.commitText("5", 1) }
            button6.setOnClickListener { currentInputConnection.commitText("6", 1) }
            button7.setOnClickListener { currentInputConnection.commitText("7", 1) }
            button8.setOnClickListener { currentInputConnection.commitText("8", 1) }
            button9.setOnClickListener { currentInputConnection.commitText("9", 1) }
            button0.setOnClickListener { currentInputConnection.commitText("0", 1) }

            buttonA.setOnClickListener { currentInputConnection.commitText(buttonA.text, 1) }
            buttonB.setOnClickListener { currentInputConnection.commitText(buttonB.text, 1) }
            buttonC.setOnClickListener { currentInputConnection.commitText(buttonC.text, 1) }
            buttonD.setOnClickListener { currentInputConnection.commitText(buttonD.text, 1) }
            buttonE.setOnClickListener { currentInputConnection.commitText(buttonE.text, 1) }
            buttonF.setOnClickListener { currentInputConnection.commitText(buttonF.text, 1) }
            buttonG.setOnClickListener { currentInputConnection.commitText(buttonG.text, 1) }
            buttonH.setOnClickListener { currentInputConnection.commitText(buttonH.text, 1) }
            buttonI.setOnClickListener { currentInputConnection.commitText(buttonI.text, 1) }
            buttonJ.setOnClickListener { currentInputConnection.commitText(buttonJ.text, 1) }
            buttonK.setOnClickListener { currentInputConnection.commitText(buttonK.text, 1) }
            buttonL.setOnClickListener { currentInputConnection.commitText(buttonL.text, 1) }
            buttonM.setOnClickListener { currentInputConnection.commitText(buttonM.text, 1) }
            buttonN.setOnClickListener { currentInputConnection.commitText(buttonN.text, 1) }
            buttonO.setOnClickListener { currentInputConnection.commitText(buttonO.text, 1) }
            buttonP.setOnClickListener { currentInputConnection.commitText(buttonP.text, 1) }
            buttonQ.setOnClickListener { currentInputConnection.commitText(buttonQ.text, 1) }
            buttonR.setOnClickListener { currentInputConnection.commitText(buttonR.text, 1) }
            buttonS.setOnClickListener { currentInputConnection.commitText(buttonS.text, 1) }
            buttonT.setOnClickListener { currentInputConnection.commitText(buttonT.text, 1) }
            buttonU.setOnClickListener { currentInputConnection.commitText(buttonU.text, 1) }
            buttonV.setOnClickListener { currentInputConnection.commitText(buttonV.text, 1) }
            buttonW.setOnClickListener { currentInputConnection.commitText(buttonW.text, 1) }
            buttonX.setOnClickListener { currentInputConnection.commitText(buttonX.text, 1) }
            buttonY.setOnClickListener { currentInputConnection.commitText(buttonY.text, 1) }
            buttonZ.setOnClickListener { currentInputConnection.commitText(buttonZ.text, 1) }


            alphabetUpperLowerCase.setOnClickListener {
                grdNumeric.visibility = View.GONE
                grdAlphabets.visibility = View.VISIBLE
                grdAlphabets2.visibility = View.VISIBLE
                grdAlphabets3.visibility = View.VISIBLE
                alphabetUpperLowerCase.visibility = View.VISIBLE
                ivBackspace.visibility = View.VISIBLE
                isUpperCase = !isUpperCase

                if (isUpperCase) {
                    setButtonTextToUpper()
                } else {
                    setButtonTextToLower()
                }
            }

            button123.setOnClickListener {
                if (isNumericVisible) {
                    // Show numeric layout, hide alphabet layouts
                    grdNumeric.visibility = View.VISIBLE
                    grdNumeric2ndRow.visibility = View.VISIBLE
                    grdNumeric3rdRow.visibility = View.VISIBLE
                    ivBackspaceNumeric.visibility = View.VISIBLE
                    buttonSpecialCharacters.visibility = View.VISIBLE
                    grdAlphabets.visibility = View.GONE
                    grdAlphabets2.visibility = View.GONE
                    grdAlphabets3.visibility = View.GONE
                    alphabetUpperLowerCase.visibility = View.GONE
                    ivBackspace.visibility = View.GONE

                    // Change button text to "Abc"
                    button123.text = "Abc"
                } else {
                    // Hide numeric layout, show alphabet layout
                    grdNumeric.visibility = View.GONE
                    grdNumeric2ndRow.visibility = View.GONE
                    grdNumeric3rdRow.visibility = View.GONE
                    ivBackspaceNumeric.visibility = View.GONE
                    buttonSpecialCharacters.visibility = View.GONE
                    grdSpecialCharacters.visibility = View.GONE
                    grdSpecialCharacters2ndRow.visibility = View.GONE
                    grdSpecialCharacters3rdRow.visibility = View.GONE
                    button12345.visibility = View.GONE

                    grdAlphabets.visibility = View.VISIBLE
                    grdAlphabets2.visibility = View.VISIBLE
                    grdAlphabets3.visibility = View.VISIBLE
                    alphabetUpperLowerCase.visibility = View.VISIBLE
                    ivBackspace.visibility = View.VISIBLE

                    buttoncomma.text = ","
                    buttonFullStop.text = "."

                    // Change button text to "123"
                    button123.text = "123"
                }

                // Toggle the flag for next click
                isNumericVisible = !isNumericVisible
            }


            buttoncomma.setOnClickListener {

                if (button12345.visibility == View.GONE || button12345.visibility == View.INVISIBLE) {
                    buttoncomma.text = ","
                } else {
                    buttoncomma.text = "<"
                }
                currentInputConnection.commitText(buttoncomma.text, 1)
            }

            buttonFullStop.setOnClickListener {

                if (button12345.visibility == View.GONE || button12345.visibility == View.INVISIBLE) {
                    buttonFullStop.text = "."
                } else {
                    buttonFullStop.text = ">"
                }
                currentInputConnection.commitText(buttonFullStop.text, 1)
            }

            search.setOnClickListener {
                currentInputConnection.performEditorAction(EditorInfo.IME_ACTION_SEARCH)
            }
            buttonSpace.setOnClickListener {
                currentInputConnection.commitText(" ", 1)
            }


            // Handle Backspace
            ivBackspace.setOnClickListener {
                currentInputConnection.deleteSurroundingText(
                    1,
                    0
                )  // Deletes one character before the cursor
            }

            ivBackspace.setOnLongClickListener {
                handler.post(deleteRunnable)  // Start the delete action on long press
                true  // Return true to indicate the long press event is handled
            }

//             Set a touch listener to stop continuous deletion when the backspace button is released
            ivBackspace.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                    handler.removeCallbacks(deleteRunnable)  // Stop the delete action
                }
                false  // Return false to allow other touch events to be processed
            }

            // numeric row second  of special characters clicks
            buttonAtTheRate.setOnClickListener {
                currentInputConnection.commitText(
                    buttonAtTheRate.text,
                    1
                )
            }
            buttonHash.setOnClickListener { currentInputConnection.commitText(buttonHash.text, 1) }
            buttonRupee.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRupee.text,
                    1
                )
            }
            buttonUnderScore.setOnClickListener {
                currentInputConnection.commitText(
                    buttonUnderScore.text,
                    1
                )
            }
            buttonAnd.setOnClickListener { currentInputConnection.commitText(buttonAnd.text, 1) }
            buttonMinus.setOnClickListener {
                currentInputConnection.commitText(
                    buttonMinus.text,
                    1
                )
            }
            buttonPlus.setOnClickListener { currentInputConnection.commitText(buttonPlus.text, 1) }
            buttonRightRoundBracket.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRightRoundBracket.text,
                    1
                )
            }
            buttonLeftRoundBrace.setOnClickListener {
                currentInputConnection.commitText(
                    buttonLeftRoundBrace.text,
                    1
                )
            }
            buttonRightSlash.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRightSlash.text,
                    1
                )
            }

            //numeric row third of special characters
            buttonStar.setOnClickListener { currentInputConnection.commitText(buttonStar.text, 1) }
            buttonDoubleUpperCotes.setOnClickListener {
                currentInputConnection.commitText(
                    buttonDoubleUpperCotes.text,
                    1
                )
            }
            buttonSingleUpperCotes.setOnClickListener {
                currentInputConnection.commitText(
                    buttonSingleUpperCotes.text,
                    1
                )
            }
            buttonColon.setOnClickListener {
                currentInputConnection.commitText(
                    buttonColon.text,
                    1
                )
            }
            buttonSemiColon.setOnClickListener {
                currentInputConnection.commitText(
                    buttonSemiColon.text,
                    1
                )
            }
            buttonExclamation.setOnClickListener {
                currentInputConnection.commitText(
                    buttonExclamation.text,
                    1
                )
            }
            buttonQuestionMark.setOnClickListener {
                currentInputConnection.commitText(
                    buttonQuestionMark.text,
                    1
                )
            }

            ivBackspaceNumeric.setOnClickListener {
                currentInputConnection.deleteSurroundingText(
                    1,
                    0
                )  // Deletes one character before the cursor
            }

            ivBackspaceNumeric.setOnLongClickListener {
                handler.post(deleteRunnable)  // Start the delete action on long press
                true  // Return true to indicate the long press event is handled
            }

            //Set a touch listener to stop continuous deletion when the backspace button is released
            ivBackspaceNumeric.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                    handler.removeCallbacks(deleteRunnable)  // Stop the delete action
                }
                false
            }

            buttonSpecialCharacters.setOnClickListener {
                grdSpecialCharacters.visibility = View.VISIBLE
                grdSpecialCharacters2ndRow.visibility = View.VISIBLE
                grdSpecialCharacters3rdRow.visibility = View.VISIBLE
                button12345.visibility = View.VISIBLE

                grdNumeric.visibility = View.GONE
                grdNumeric2ndRow.visibility = View.GONE
                grdNumeric3rdRow.visibility = View.GONE
                buttonSpecialCharacters.visibility = View.GONE

                buttoncomma.text = "<"
                buttonFullStop.text = ">"
            }

            //special characters row 1 click listeners
            buttonTild.setOnClickListener { currentInputConnection.commitText(buttonTild.text, 1) }
            buttonUpperSingleCote.setOnClickListener {
                currentInputConnection.commitText(
                    buttonUpperSingleCote.text,
                    1
                )
            }
            buttonBar.setOnClickListener { currentInputConnection.commitText(buttonBar.text, 1) }
            buttonBullet.setOnClickListener {
                currentInputConnection.commitText(
                    buttonBullet.text,
                    1
                )
            }
            buttonSquareRoot.setOnClickListener {
                currentInputConnection.commitText(
                    buttonSquareRoot.text,
                    1
                )
            }
            buttonPie.setOnClickListener { currentInputConnection.commitText(buttonPie.text, 1) }
            buttonDivide.setOnClickListener {
                currentInputConnection.commitText(
                    buttonDivide.text,
                    1
                )
            }
            buttonMultiply.setOnClickListener {
                currentInputConnection.commitText(
                    buttonMultiply.text,
                    1
                )
            }
            buttonSilcrow.setOnClickListener {
                currentInputConnection.commitText(
                    buttonSilcrow.text,
                    1
                )
            }
            buttonUpwardPointingTriangle.setOnClickListener {
                currentInputConnection.commitText(
                    buttonUpwardPointingTriangle.text,
                    1
                )
            }

            //special characters row 2nd click listeners
            buttonEuro.setOnClickListener { currentInputConnection.commitText(buttonEuro.text, 1) }
            buttonYen.setOnClickListener { currentInputConnection.commitText(buttonYen.text, 1) }
            buttonDollar.setOnClickListener {
                currentInputConnection.commitText(
                    buttonDollar.text,
                    1
                )
            }
            buttonCediSign.setOnClickListener {
                currentInputConnection.commitText(
                    buttonCediSign.text,
                    1
                )
            }
            buttonCircumFlex.setOnClickListener {
                currentInputConnection.commitText(
                    buttonCircumFlex.text,
                    1
                )
            }
            buttonDegree.setOnClickListener {
                currentInputConnection.commitText(
                    buttonDegree.text,
                    1
                )
            }
            buttonEqualTo.setOnClickListener {
                currentInputConnection.commitText(
                    buttonEqualTo.text,
                    1
                )
            }
            buttonLeftCurlyBrace.setOnClickListener {
                currentInputConnection.commitText(
                    buttonLeftCurlyBrace.text,
                    1
                )
            }
            buttonRightCurlyBrace.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRightCurlyBrace.text,
                    1
                )
            }
            buttonLeftSlash.setOnClickListener {
                currentInputConnection.commitText(
                    buttonLeftSlash.text,
                    1
                )
            }

            //special characters row 3rd click listeners
            buttonPercentage.setOnClickListener {
                currentInputConnection.commitText(
                    buttonPercentage.text,
                    1
                )
            }
            buttonCopyRight.setOnClickListener {
                currentInputConnection.commitText(
                    buttonCopyRight.text,
                    1
                )
            }
            buttonRegisteredTrademark.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRegisteredTrademark.text,
                    1
                )
            }
            buttonTrademarkSymbol.setOnClickListener {
                currentInputConnection.commitText(
                    buttonTrademarkSymbol.text,
                    1
                )
            }

            buttonTick.setOnClickListener { currentInputConnection.commitText(buttonTick.text, 1) }
            buttonLeftSquareBracket.setOnClickListener {
                currentInputConnection.commitText(
                    buttonLeftSquareBracket.text,
                    1
                )
            }
            buttonRightSquareBracket.setOnClickListener {
                currentInputConnection.commitText(
                    buttonRightSquareBracket.text,
                    1
                )
            }

            button12345.setOnClickListener {
                grdSpecialCharacters.visibility = View.GONE
                grdSpecialCharacters2ndRow.visibility = View.GONE
                grdSpecialCharacters3rdRow.visibility = View.GONE
                button12345.visibility = View.GONE

                grdNumeric.visibility = View.VISIBLE
                grdNumeric2ndRow.visibility = View.VISIBLE
                grdNumeric3rdRow.visibility = View.VISIBLE
                buttonSpecialCharacters.visibility = View.VISIBLE

                buttoncomma.text = ","
                buttonFullStop.text = "."

            }
        }
        return binding.root
    }


    // Set initial button text
    private fun setButtonTextToUpper() {
        binding.apply {
            buttonA.text = "A"
            buttonB.text = "B"
            buttonC.text = "C"
            buttonD.text = "D"
            buttonE.text = "E"
            buttonF.text = "F"
            buttonG.text = "G"
            buttonH.text = "H"
            buttonI.text = "I"
            buttonJ.text = "J"
            buttonK.text = "K"
            buttonL.text = "L"
            buttonM.text = "M"
            buttonN.text = "N"
            buttonO.text = "O"
            buttonP.text = "P"
            buttonQ.text = "Q"
            buttonR.text = "R"
            buttonS.text = "S"
            buttonT.text = "T"
            buttonU.text = "U"
            buttonV.text = "V"
            buttonW.text = "W"
            buttonX.text = "X"
            buttonY.text = "Y"
            buttonZ.text = "Z"
        }
    }

    //set buttons to lower case
    private fun setButtonTextToLower() {
        binding.apply {
            buttonA.text = "a"
            buttonB.text = "b"
            buttonC.text = "c"
            buttonD.text = "d"
            buttonE.text = "e"
            buttonF.text = "f"
            buttonG.text = "g"
            buttonH.text = "h"
            buttonI.text = "i"
            buttonJ.text = "j"
            buttonK.text = "k"
            buttonL.text = "l"
            buttonM.text = "m"
            buttonN.text = "n"
            buttonO.text = "o"
            buttonP.text = "p"
            buttonQ.text = "q"
            buttonR.text = "r"
            buttonS.text = "s"
            buttonT.text = "t"
            buttonU.text = "u"
            buttonV.text = "v"
            buttonW.text = "w"
            buttonX.text = "x"
            buttonY.text = "y"
            buttonZ.text = "z"
        }

    }

    // Runnable that deletes characters
    private val deleteRunnable = object : Runnable {
        override fun run() {
            currentInputConnection.deleteSurroundingText(1, 0)
            handler.postDelayed(this, 100)  // Repeat every 100 milliseconds (adjust as needed)
        }
    }
}

