package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

// Ye view Model jab Add expense Dabae tous data ko database mein bheje isiliye

class AddExpenseViewModel(val dao:ExpenseDao):ViewModel() {
    suspend fun addExpense(expenseEntity: ExpenseEntity):Boolean{
        return try {
            dao.insertExpense(expenseEntity)
            true
        } catch(ex:Throwable){
            false
        }
    }
}

class AddExpenseiewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(AddExpenseViewModel::class.java)){
            val dao= ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}