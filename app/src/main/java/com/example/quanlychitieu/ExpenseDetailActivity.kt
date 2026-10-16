package com.example.quanlychitieu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class ExpenseDetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        // Nhận dữ liệu qua Intent
        val expense = intent.getExpense()
        val position = intent.getIntExtra(EXTRA_POSITION, -1)

        findViewById<TextView>(R.id.tvName).text = expense.name
        findViewById<TextView>(R.id.tvAmount).text = formatMoney(expense.amount)
        findViewById<TextView>(R.id.tvCategory).text = expense.category
        findViewById<TextView>(R.id.tvDate).text = expense.date
        findViewById<TextView>(R.id.tvNote).text = expense.note.ifEmpty { "Không có ghi chú" }

        findViewById<ImageButton>(R.id.btnUp).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }

        // Xóa: trả vị trí về MainActivity để xóa khỏi ArrayList và cập nhật tổng tiền
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra(EXTRA_POSITION, position))
            Toast.makeText(this, "Đã xóa \"${expense.name}\"", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
