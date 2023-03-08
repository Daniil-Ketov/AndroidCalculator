package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var numButtons : Array<Button>
    private lateinit var buttonPlus : Button
    private lateinit var buttonMinus : Button
    private lateinit var buttonMultiply : Button
    private lateinit var buttonDivide : Button
    private lateinit var buttonResult : Button
    private lateinit var buttonDot : Button
    private lateinit var buttonClear : Button
    private lateinit var buttonDelete : ImageButton
    private lateinit var tvCurr : EditText
    private lateinit var tvPrev : TextView
    private lateinit var tvOp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numButtons = arrayOf(findViewById(R.id.button0),
                                 findViewById(R.id.button1),
                                 findViewById(R.id.button2),
                                 findViewById(R.id.button3),
                                 findViewById(R.id.button4),
                                 findViewById(R.id.button5),
                                 findViewById(R.id.button6),
                                 findViewById(R.id.button7),
                                 findViewById(R.id.button8),
                                 findViewById(R.id.button9))

        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonResult = findViewById(R.id.buttonResult)
        buttonClear = findViewById(R.id.buttonClear)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDot = findViewById(R.id.buttonDot)

        tvCurr = findViewById(R.id.tvCurr)
        tvOp = findViewById(R.id.tvOperation)
        tvPrev = findViewById(R.id.tvPrev)

        for (btn in numButtons) {
            btn.setOnClickListener {
                appendCurr(btn.text.toString())
            }
        }

        buttonDot.setOnClickListener {
            appendCurr(".")
        }

        buttonPlus.setOnClickListener {
            operation("+")
        }

        buttonMinus.setOnClickListener {
            if (tvCurr.text.toString() == "" && tvPrev.text.toString() == "") {
                appendCurr("-")
            }
            else {
                operation("-")
            }
        }

        buttonMultiply.setOnClickListener {
            operation("×")
        }

        buttonDivide.setOnClickListener {
            operation("÷")
        }

        buttonResult.setOnClickListener {
            calculate()
        }

        buttonDelete.setOnClickListener {
            delete()
        }

        buttonClear.setOnClickListener {
            clear()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun appendCurr(symbol: String) {
        when (symbol) {
            "." -> {
                if ("." in tvCurr.text.toString())
                    return
                when (tvCurr.text.toString()) {
                    "" -> tvCurr.setText("0.")
                    "-" -> tvCurr.setText("-0.")
                    else -> tvCurr.setText(tvCurr.text.toString() + ".")
                }
                return
            }
            "-" -> {
                if (tvCurr.text.toString() == "-") {
                    tvCurr.setText("")
                    return
                }
                if ("-" in tvCurr.text.toString())
                    return
            }
        }
        if (tvCurr.text.toString() == "0") {
            tvCurr.setText(symbol)
            return
        }
        tvCurr.setText(tvCurr.text.toString() + symbol)
    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        when (tvOp.text) {
            "+" -> {
                tvCurr.setText((tvPrev.text.toString().toDouble() + tvCurr.text.toString().toDouble()).toString())
            }
            "-" -> {
                tvCurr.setText((tvPrev.text.toString().toDouble() - tvCurr.text.toString().toDouble()).toString())
            }
            "×" -> {
                tvCurr.setText((tvPrev.text.toString().toDouble() * tvCurr.text.toString().toDouble()).toString())
            }
            "÷" -> {
                if (tvCurr.text.toString().toDouble() == 0.0) {
                    throw java.lang.Exception("Divide by zero exception")
                }
                tvCurr.setText((tvPrev.text.toString().toDouble() / tvCurr.text.toString().toDouble()).toString())
            }
            else -> return
        }
        tvPrev.text = ""
        tvOp.text = ""
    }

    private fun operation(op: String) {
        if (tvPrev.text.toString() == "") {
            if (tvCurr.text.toString() == "")
                return
            tvPrev.text = tvCurr.text
            tvCurr.setText("")
            tvOp.text = (op)
            return
        }
        if (tvCurr.text.toString() == "") {
            tvOp.text = op
            return
        }
        try {
            calculate()
            tvOp.text = op
            tvPrev.text = tvCurr.text
            tvCurr.setText("")
        }
        catch (e: java.lang.Exception) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun delete() {
        if (tvCurr.text.toString() == "") {
            return
        }
        tvCurr.setText(tvCurr.text.toString().slice(0 until tvCurr.text.toString().length - 1))
    }

    private fun clear() {
        tvCurr.setText("")
        tvOp.text = ""
        tvPrev.text = ""
    }
}