package com.example.expensetracker

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.feature.home.HomeScreen
import com.example.expensetracker.feature.add_expense.AddExpense
import com.example.expensetracker.feature.stats.StatsScreen
import com.example.expensetracker.ui.theme.Zinc

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    // As bottom bar will be visible on our add expense screen due to Scaffold we will
    // use a variable to maintain its visibility
    var bottomBarVisibility by remember {
        mutableStateOf(true)
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = bottomBarVisibility) {
                NavigationBottomBar(
                    navController = navController, items = listOf(
                        NavItem(route = "/home", icon = R.drawable.ic_home),
                        NavItem(route = "/stats", icon = R.drawable.ic_stats),
                    )
                )
            }
        }
    ) {innerPadding->
        NavHost(navController = navController, startDestination = "/home", modifier = Modifier.padding(innerPadding)) {
            composable(route = "/home") {
                bottomBarVisibility=true
                HomeScreen(navController)
            }
            composable(route = "/add") {
                bottomBarVisibility=false
                AddExpense(navController)
            }
            composable(route = "/stats") {
                bottomBarVisibility=true
                StatsScreen(navController)
            }
        }
    }

}

/* In sab ke upar bhi nav baar chiye toh
    1. A data class to store navigation bar items
    2. Your bottom bar function with list of data class as parameter
    3. A State for current item/Screen in bottom Bar here "currentBackStackEntryAsState()"
    4. Use a Scaffold to encapsulate routes inside bottom bar
    5. call the visibility variable inside desired composable
*/

data class NavItem(
    val route: String,
    val icon: Int
)

@Composable
fun NavigationBottomBar(
    navController: NavController,
    items: List<NavItem> //List of our data class
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar {
        items.forEach { item -> //Iterator name
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                }, alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Zinc,
                    selectedTextColor = Zinc,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}