package com.ultivic.customkeyboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ultivic.customkeyboard.databinding.ActivityMainBinding

/**
 * MainActivity controls the functionality related to enabling and selecting the custom keyboard.
 */
class MainActivity : AppCompatActivity() {

    // Binding the views using view binding
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /**
     * Called when the activity is first created. Sets up the UI and handles button clicks.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge display
        enableEdgeToEdge()

        // Set the layout for the activity
        setContentView(binding.root)

        /**
         * Handle window insets to account for system bars (status bar, navigation bar).
         * Adjusts padding based on system bar insets.
         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Set the button click listener. Checks if the custom keyboard is enabled.
         * If not enabled, shows a dialog to guide the user. If enabled, opens the keyboard picker.
         */
        binding.btnStart.setOnClickListener {
            if (!isCustomKeyboardEnabled()) {
                showKeyBoardEnableDialog()  // Show dialog if the keyboard is not enabled
            } else {
                showInputMethodPicker()  // Open input method picker if enabled
            }
        }
    }

    /**
     * Shows an AlertDialog prompting the user to enable the custom keyboard via system settings.
     */
    private fun showKeyBoardEnableDialog() {
        // Options that will be displayed in the dialog (currently only one option)
        val options = arrayOf("1. Enable KeyBoard")

        /**
         * Create an AlertDialog with options. This dialog guides the user to enable the keyboard.
         */
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enable Rapid Lab Scan KeyBoard")
            .setCancelable(false)  // Disable ability to cancel dialog without making a selection
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        enableCustomKeyboard()  // Opens settings to enable the custom keyboard
                        dialog.dismiss()  // Close the dialog after the selection is made
                    }
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Opens the system's input method picker, allowing the user to switch keyboards.
     */
    private fun showInputMethodPicker() {
        val imeManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imeManager.showInputMethodPicker()  // Opens the system's keyboard picker
    }

    /**
     * Opens the system settings where the user can enable the custom keyboard.
     */
    private fun enableCustomKeyboard() {
        // Create an intent to open the input method settings page
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)  // Start the settings activity
    }

    /**
     * Checks whether the custom keyboard is currently enabled.
     *
     * @return Returns true if the custom keyboard is selected, false otherwise.
     */
    private fun isCustomKeyboardEnabled(): Boolean {
        // Get the current default input method ID from system settings
        val currentInputMethod = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)

        // Check if the current input method's ID starts with the app's package name
        return currentInputMethod?.startsWith(packageName) ?: false
    }
}
