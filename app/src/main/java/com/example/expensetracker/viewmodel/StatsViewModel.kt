package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

//Jitne bhi Screns Hoti hai utne ViewModel banao

//is viewModel ke andar hume dao ka Object chiye that isiliye (dao:ExpenseDao)
class StatsViewModel(dao:ExpenseDao) :ViewModel(){
    //we only have to show data here
    val expense=dao.getAllExpenses()

}

class StatsViewModelFactory(private val context:Context): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(StatsViewModel::class.java)){
            val dao= ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}