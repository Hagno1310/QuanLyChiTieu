package com.example.quanlychitieu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast

class AddExpenseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val spnCategory = findViewById<Spinner>(R.id.spnCategory)
        val dpDate = findViewById<DatePicker>(R.id.dpDate)
        val edtNote = findViewById<EditText>(R.id.edtNote)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = edtName.text.toString().trim()
            val amount = edtAmount.text.toString().toLongOrNull() // null nếu rỗng / quá lớn

            if (name.isEmpty()) {
                edtName.error = "Vui lòng nhập tên khoản chi"
                edtName.requestFocus()
                return@setOnClickListener
            }
            if (amount == null || amount <= 0) {
                edtAmount.error = "Số tiền phải là số hợp lệ và lớn hơn 0"
                edtAmount.requestFocus()
                return@setOnClickListener
            }

            // DatePicker: month bắt đầu từ 0
            val date = "%02d/%02d/%04d".format(dpDate.dayOfMonth, dpDate.month + 1, dpDate.year)
            val expense = Expense(
                name,
                amount,
                spnCategory.selectedItem.toString(),
                date,
                edtNote.text.toString().trim(),
            )

            setResult(RESULT_OK, Intent().putExtra(EXTRA_EXPENSE, expense))
            Toast.makeText(this, "Đã lưu khoản chi", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
