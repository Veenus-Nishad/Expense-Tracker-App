package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseSummary
import com.github.mikephil.charting.data.Entry


//Jitne bhi Screns Hoti hai utne ViewModel banao

//is viewModel ke andar hume dao ka Object chiye that isiliye (dao:ExpenseDao)
class StatsViewModel(dao:ExpenseDao) :ViewModel(){
    //we only have to show data here
    val entries=dao.getAllExpensesByDate()
    val topEntries=dao.getTopExpenses()

    fun getEntriesForChart(entries:List<ExpenseSummary>):List<Entry>{
        val list = mutableListOf<Entry>()
        for(entry in entries){
            val formattedDate= Utils.getMillisFromDate(entry.date)
            list.add(Entry(formattedDate.toFloat(),entry.total_amount.toFloat()))
        }
    return list // returning entries as list
    }

}

class StatsViewModelFactory(private val context:Context): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(StatsViewModel::class.java)){
            val dao= ExpenseDatabase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}