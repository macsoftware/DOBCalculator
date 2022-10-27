package uk.co.macsoftware.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var textViewSelectedDate: TextView? = null
    private var textViewAgeInMinutes: TextView? = null
    private var textViewAgeInMinutesHeader: TextView? = null

    private fun init() {
        val buttonDatePicker: Button = findViewById(R.id.buttonDatePicker)
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate)
        textViewSelectedDate?.text = ""

        textViewAgeInMinutes = findViewById(R.id.textViewAgeInMinutes)
        textViewAgeInMinutes?.text = ""

        textViewAgeInMinutesHeader = findViewById(R.id.textViewAgeInMinutesHeader)

        buttonDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                textViewSelectedDate?.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = simpleDateFormat.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate =
                        simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        textViewAgeInMinutesHeader?.visibility = View.VISIBLE
                        textViewAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            }, year, month, day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}