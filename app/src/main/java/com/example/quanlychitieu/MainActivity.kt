package com.example.quanlychitieu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {

    companion object {
        private const val REQUEST_ADD = 1
        private const val REQUEST_DETAIL = 2

        // Dữ liệu mẫu ban đầu (ngày = hôm nay). Để static nên không mất khi xoay màn hình.
        private val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val expenses = arrayListOf(
            Expense("Ăn sáng", 30000, "Ăn uống", today, ""),
            Expense("Đổ xăng", 50000, "Đi lại", today, ""),
            Expense("Mua sách", 120000, "Học tập", today, "Sách Lập trình Android"),
        )
    }

    private lateinit var adapter: ArrayAdapter<Expense>
    private lateinit var tvTotal: TextView
    private lateinit var lvExpenses: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTotal = findViewById(R.id.tvTotal)
        lvExpenses = findViewById(R.id.lvExpenses)

        adapter = object : ArrayAdapter<Expense>(this, R.layout.item_expense, expenses) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.item_expense, parent, false)
                val e = getItem(position)!!
                view.findViewById<TextView>(R.id.tvName).text = e.name
                view.findViewById<TextView>(R.id.tvAmount).text = formatMoney(e.amount)
                view.findViewById<TextView>(R.id.tvCategory).text = e.category
                view.findViewById<TextView>(R.id.tvDate).text = e.date
                return view
            }
        }
        lvExpenses.adapter = adapter
        lvExpenses.emptyView = findViewById(R.id.tvEmpty)

        // Nhấn vào một dòng -> mở màn hình chi tiết
        lvExpenses.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ExpenseDetailActivity::class.java)
            intent.putExtra(EXTRA_EXPENSE, expenses[position])
            intent.putExtra(EXTRA_POSITION, position)
            startActivityForResult(intent, REQUEST_DETAIL)
        }

        // Nút Thêm khoản chi
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivityForResult(Intent(this, AddExpenseActivity::class.java), REQUEST_ADD)
        }

        updateTotal()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) return

        when (requestCode) {
            REQUEST_ADD -> {
                expenses.add(data.getExpense())
                adapter.notifyDataSetChanged()
                lvExpenses.smoothScrollToPosition(expenses.size - 1)
            }
            REQUEST_DETAIL -> {
                val position = data.getIntExtra(EXTRA_POSITION, -1)
                if (position in expenses.indices) {
                    expenses.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        updateTotal()
    }

    // Tổng số tiền = cộng dồn số tiền tất cả khoản chi
    private fun updateTotal() {
        tvTotal.text = formatMoney(expenses.sumOf { it.amount })
    }
}
