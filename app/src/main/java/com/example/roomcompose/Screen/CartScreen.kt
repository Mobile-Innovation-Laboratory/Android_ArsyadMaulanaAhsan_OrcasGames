package com.example.roomcompose.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomcompose.Model.AuthViewModel
import com.example.roomcompose.Model.PurchasedGamesViewModel
import com.example.roomcompose.Object.PurchasedGame
import com.example.roomcompose.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    authView: AuthViewModel,
    viewModel: PurchasedGamesViewModel
) {
    val cartItems by viewModel.purchasedGames.observeAsState(emptyList())
    val selectedTab = remember { mutableStateOf(0) }

    val isDarkTheme = remember { mutableStateOf(true) }
    Scaffold(
        containerColor = if (isDarkTheme.value) Color(0xFF121212) else colorResource(
            id = R.color.white
        ),
        // 🔹 TOP BAR
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isDarkTheme.value) Color.Black else Color.White,
                    titleContentColor = if (isDarkTheme.value) Color.White else Color.Black,
                ),
                title = { Text("Games", fontWeight = FontWeight.Bold) },
                actions = {

                    IconButton(onClick = { isDarkTheme.value = !isDarkTheme.value }) {
                        Icon(
                            painter = if (isDarkTheme.value) painterResource(id = R.drawable.moon) else painterResource(
                                id = R.drawable.sun
                            ),
                            contentDescription = "Toggle Theme",
                            tint = if (isDarkTheme.value) Color.White else Color.Black
                        )
                    }

                }
            )
        },

        // 🔹 BOTTOM BAR
        bottomBar = {
            BottomAppBar(
                containerColor = if (isDarkTheme.value) Color.Black else Color.White,
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val icons = listOf(
                            Pair(R.drawable.home, "home"),
                            Pair(R.drawable.cart, "cart"),
                            Pair(R.drawable.games, "gamelist"),
                            Pair(R.drawable.trophies, "achievement"),
                            Pair(R.drawable.settings_1, "settings")
                        )

                        icons.forEachIndexed { index, (icon, route) ->
                            IconButton(
                                onClick = {
                                    selectedTab.value = index + 1
                                    navController.navigate(route)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = route,
                                    tint = if (selectedTab.value == index + 1) colorResource(id = R.color.greenpm) else Color.Gray
                                )
                            }
                        }
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(
                    if (isDarkTheme.value) Color(0xFF121212) else colorResource(
                        id = R.color.white
                    )
                )
        ) {
            Text(
                text = "Your Cart",
                color = if (isDarkTheme.value)Color.White else colorResource(id = R.color.black),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { item ->
                    CartItemCard(item) { removedItem ->
                        viewModel.removeGame(removedItem)
                    }
                }
            }

            Button(
                onClick = { /* Implement Checkout */ },
                modifier = Modifier.fillMaxWidth()
                , colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.yellowpm)
                )
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 18.sp,
                    color = if (isDarkTheme.value)Color.White else colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CartItemCard(item: PurchasedGame, onRemove: (PurchasedGame) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF232222)),
        shape = RoundedCornerShape(18.dp),
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = "Game Image",
                modifier = Modifier.size(80.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${item.price}",
                    color = Color(0xFFE2F163),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = {
                    onRemove(item)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "✅ ${item.title} Removed from the cart!",
                            actionLabel = "Delete",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Remove")
            }
        }
    }
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .padding(bottom = 40.dp),
    ) { data ->
        Snackbar(
            snackbarData = data,
            shape = RoundedCornerShape(12.dp),
            containerColor = Color.Black,
            contentColor = Color.White,
            actionColor = Color.Green
        )
    }
}
