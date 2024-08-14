package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*Ye RoomData base ki class automatically cheeze generate karek degi
 jaise expense Dao ki class*/
@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDataBase:RoomDatabase() {
    // to tell the system is class se kya expected hai here Dao
    abstract fun expenseDao(): ExpenseDao

    companion object{
        const val DATABASE_NAME="expense_database"

        @JvmStatic// create's and return's database
        fun getDatabase(context: Context):ExpenseDataBase{
            return Room.databaseBuilder(
                context,
                ExpenseDataBase::class.java,
                DATABASE_NAME
            ).addCallback(object: Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    InitBasicData(context)
                }
                fun InitBasicData(context:Context){
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = getDatabase(context).expenseDao()
                        dao.insertExpense(ExpenseEntity(1,"Salary",5000.0,System.currentTimeMillis().toString(),"Salary","Income"))
                        dao.insertExpense(ExpenseEntity(2,"paypal",50020.0,System.currentTimeMillis().toString(),"paypal","Income"))
                        dao.insertExpense(ExpenseEntity(3,"Netflix",50100.0,System.currentTimeMillis().toString(),"Netflix","Expense"))
                        dao.insertExpense(ExpenseEntity(4,"StarBucks",53000.0,System.currentTimeMillis().toString(),"Starbucks","Expense"))
                    }
                }
            }).build()
        }
    }
}

//Now ready for functionality Implementation