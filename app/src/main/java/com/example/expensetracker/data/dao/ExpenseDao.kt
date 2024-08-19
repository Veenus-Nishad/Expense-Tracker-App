package com.example.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow

//Helps in communication With the Database

@Dao
interface ExpenseDao {


    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    //For Graph top Expenses
    @Query("SELECT * FROM expense_table WHERE type = 'Expense' ORDER BY amount DESC LIMIT 5")
    fun getTopExpenses(): Flow<List<ExpenseEntity>>


    @Query("SELECT type, date, SUM(amount) AS total_amount FROM expense_table where type = :type GROUP BY type, date ORDER BY date")
    fun getAllExpensesByDate(type:String="Expense"): Flow<List<ExpenseSummary>>


    @Insert //data Insert karne ke liye
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)
}