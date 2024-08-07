package com.example.expensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.expensetracker.ui.theme.Zinc
import java.time.temporal.TemporalAmount

@Composable
fun HomeScreen(modifier: Modifier) {
    Surface(modifier=Modifier.fillMaxSize()) {
        ConstraintLayout(modifier=Modifier.fillMaxSize()) {
            /*
            By using id in XML we can create references for views. We do that in the compose
             via createRefs() or createRefFor(). By these DSL functions, we can create a
             reference for each composable on the screen. Keep in mind that createRefs()
             can take 16 components.We can specify constraints for a reference (composable)
                via constraintAs() modifier function.
            */
            val( nameRow,list,card,topBar)=createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription =null,
                modifier=Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Box(modifier= Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ){
                Column {
                    Text(text = "Good AfterNoon,", fontSize = 16.sp,color= Color.White)
                    Text(text = "Dragon", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                        color=Color.White)
                }
                Image(painter = painterResource(id = R.drawable.ic_notification), contentDescription =null,
                    modifier=Modifier.align(Alignment.CenterEnd))
            }
            CardItem(modifier=Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })
        }
    }
}

@Composable
fun CardItem(modifier: Modifier){
    Column( modifier= modifier
        .padding(16.dp)
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Zinc)
        .padding(16.dp)
        ) {
        Box(modifier= Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column(modifier=Modifier.align(Alignment.CenterStart)) {
                Text(text = "Total Balance",fontSize=16.sp,color=Color.White)
                Text(text = "$ 5000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Image(painter = painterResource(id = R.drawable.dots_menu), contentDescription =null,
                modifier=Modifier.align(Alignment.CenterEnd))
        }
        Box(modifier= Modifier
            .fillMaxWidth()
            .weight(1f)){
           CardRowItem(modifier = Modifier.align(Alignment.CenterStart), title = "Income", image =R.drawable.ic_income , amount = " $34")
           CardRowItem(modifier = Modifier.align(Alignment.CenterEnd), title = "Expenses", image =R.drawable.ic_expense , amount = " $35")
        }
    }
}

@Composable
fun CardRowItem(modifier:Modifier,title:String,image:Int,amount: String){
    Column(modifier=modifier) {
        Row {
            Image(painter = painterResource(id = image), contentDescription = null )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text =title, fontSize = 16.sp,color=Color.White)
        }
        Text(text = amount,fontSize = 20.sp, fontWeight = FontWeight.Bold,color=Color.White)
    }
}

@Composable
fun TransactionList(modifier: Modifier){
    Column(modifier=modifier.padding(horizontal = 16.dp)) {
        Box(modifier=Modifier.fillMaxWidth()){
            Text(text = "Recent Transactions", fontSize = 20.sp)
            Text(text = "See All", fontSize = 16.sp,modifier=Modifier.align(Alignment.CenterEnd))
        }
        TransactionItem(title = "Netflix", icon =R.drawable.ic_netflix , amount ="- $ 200.00" , date ="Today" , color =Color.Red )
        TransactionItem(title = "UpWork", icon =R.drawable.ic_upwork , amount ="- $ 200.00" , date ="Today" , color =Color.Red )
        TransactionItem(title = "PayPal", icon =R.drawable.ic_paypal , amount ="- $ 200.00" , date ="Today" , color =Color.Red )
        TransactionItem(title = "StarBucks", icon =R.drawable.ic_starbucks , amount ="- $ 200.00" , date ="Today" , color =Color.Red )
    }
}

@Composable
fun TransactionItem(title:String,icon:Int,amount: String,date: String,color:Color){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)){
        Row{
            Image(
                painter= painterResource(id = icon), contentDescription = null,
                modifier=Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp)
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = date, fontSize = 12.sp)
            }
        }
        Text(text = amount, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterEnd),color=color)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(modifier=Modifier)
}