package com.example.roomcompose.utils
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomcompose.Model.PurchasedGamesViewModel
import com.example.roomcompose.Object.PurchasedGame
import com.example.roomcompose.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipeableGameCards(purchasedModel: PurchasedGamesViewModel) {
    val pagerState = rememberPagerState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    HorizontalPager(count = gamesList.size, state = pagerState) { page ->
        val game = gamesList[page]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = game.imageRes),
                    contentDescription = game.title,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.description,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        val purchasedGame = PurchasedGame(
                            id = game.id,
                            title = game.title,
                            price = game.price,
                            imageRes = game.imageRes,
                            releaseDate = "December",
                            releaseYear = 2023
                        )
                        purchasedModel.buyGame(purchasedGame)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "✅ ${game.title} added to cart!",
                                actionLabel = "View",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2F163))
                ){
                    Text(
                        text = "Buy Now - $${game.price}",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .padding(bottom = 40.dp)
            ,
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
}
