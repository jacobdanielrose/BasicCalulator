package com.example.basiccalulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear() {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint() {
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual() {
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        tvInput.text = (one.toDouble() * two.toDouble()).toString()
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length-2)
        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }
    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains ("-")
        }
    }
}