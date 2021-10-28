package id.alamsyah.muhammad.pinjolapp

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import id.alamsyah.muhammad.pinjolapp.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    // Binding object instance with access to the views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout XML file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view of the Activity to be the root view of the layout
        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculatePinjol() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.jumlahPinjamanEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }


    private fun calculatePinjol() {

        // Get the decimal value from the cost of service EditText field
        val stringInTextField = binding.jumlahPinjamanEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        val stringInTextField1 = binding.jatuhTempoEditText.text.toString()
        val tempo = stringInTextField1.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            displayCair(0.0)
            return
        }
        if (tempo == null || tempo == 0.0) {
            displayTotal(0.0)
            return
        }

        var Biaya_layanan = cost * 5/100
        var bunga = 3.75/100 * tempo


        // Calculate Pinjol
        var cair = cost - Biaya_layanan
        displayCair(cair)

        var total = cost + bunga
        displayTotal(total)





    }

    /**
     * Format the tip amount according to the local currency and display it onscreen.
     * Example would be "Tip Amount: $10.00".
     */
    private fun displayCair(cair: Double) {
        val localeID = Locale("in", "ID")
        val formattedCair = NumberFormat.getCurrencyInstance(localeID).format(cair)
        binding.pinjamBersih.text = getString(R.string.pinjaman_bersih, formattedCair)
    }

    private fun displayTotal(total: Double) {
        val localeID = Locale("in", "ID")
        val formattedTotal = NumberFormat.getCurrencyInstance(localeID).format(total)
        binding.totalTagihan.text = getString(R.string.total_tagihan, formattedTotal)
    }



    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}