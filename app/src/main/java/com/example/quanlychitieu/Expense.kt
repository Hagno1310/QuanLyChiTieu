package com.example.quanlychitieu

import android.content.Intent
import android.os.Build
import java.io.Serializable
import java.text.NumberFormat
import java.util.Locale

data class Expense(
    val name: String,
    val amount: Long,
    val category: String,
    val date: String, // dd/MM/yyyy
    val note: String,
) : Serializable

const val EXTRA_EXPENSE = "expense"
const val EXTRA_POSITION = "position"

/** 120000 -> "120.000 đ" */
fun formatMoney(amount: Long): String =
    NumberFormat.getInstance(Locale.forLanguageTag("vi-VN")).format(amount) + " đ"

fun Intent.getExpense(): Expense =
    if (Build.VERSION.SDK_INT >= 33) getSerializableExtra(EXTRA_EXPENSE, Expense::class.java)!!
    else @Suppress("DEPRECATION") (getSerializableExtra(EXTRA_EXPENSE) as Expense)
