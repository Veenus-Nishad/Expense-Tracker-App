package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

//Jitne bhi Screns Hoti hai utne ViewModel banao

//is viewModel ke andar hume dao ka Object chiye that isiliye (dao:ExpenseDao)
class HomeViewModel(dao:ExpenseDao) :ViewModel(){
    //we only have to show data here
    val expense=dao.getAllExpenses()

    fun getBalance(list:List<ExpenseEntity>):String{
        var total=0.0
        list.forEach{
            if(it.type=="Income"){
                total+=it.amount
            }else{
                total-=it.amount
            }
        }
        return "$ ${total}"
    }

    fun getTotalExpense(list:List<ExpenseEntity>):String{
        var total=0.0
        list.forEach{
            if(it.type=="Expense"){
                total+=it.amount
            }
        }
        return "$ ${total}"
    }
    // FOR APPLYING Generic Icons
    fun getItemIcon(item:ExpenseEntity):Int{
        if (item.category == "Paypal"){
            return R.drawable.ic_paypal
        }else if (item.category == "Netflix"){
            return R.drawable.ic_netflix
        }
        return R.drawable.ic_upwork
    }

    fun getTotalIncome(list:List<ExpenseEntity>):String{
        var total=0.0
        list.forEach{
            if(it.type=="Income"){
                total+=it.amount
            }
        }
        return "$ ${total}"
    }
}

class HomeViewModelFactory(private val context:Context): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val dao= ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}