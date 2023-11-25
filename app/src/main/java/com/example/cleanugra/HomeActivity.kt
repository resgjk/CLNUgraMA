package com.example.cleanugra

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanugra.apiMethods.CategoriesApiMethods
import com.example.cleanugra.apiMethods.NewsApiMethods
import com.example.cleanugra.models.categories.CategoryModel
import com.example.cleanugra.models.news.NewsModel
import com.example.cleanugra.uiData.newGray
import com.example.cleanugra.uiData.newGreen
import com.example.cleanugra.uiData.newWhite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

class HomeActivity : ComponentActivity() {
    private val connect = Retrofit.Builder().baseUrl("https://cua.glitch.me/api/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val newsApi = connect.create(NewsApiMethods::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val items = listOf("categories", "home", "chat")
            val selectedItem = remember {
                mutableStateOf(items[1])
            }

            val newsList = remember {
                mutableStateOf(emptyList<NewsModel>())
            }
            LaunchedEffect(Unit) {
                newsList.value = newsApi.getNews().body()!!.news
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = newGray
            ) {
                Text(
                    text = "Последние новости",
                    color = newGreen,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_xtrabold)),
                    fontSize = 21.sp,
                    modifier = Modifier.padding(7.dp)
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = "Последние новости",
                        color = newGreen,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_xtrabold)),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    if (newsList.value.isEmpty() == false) {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 90.dp, top = 40.dp)
                        ) {
                            itemsIndexed(
                                newsList.value
                            ) { index, item ->
                                newsListItem(news = item, context = context)
                            }

                        }
                    }
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = newWhite,
                    ) {
                        NavigationBarItem(
                            icon = {
                                Image(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    painter = painterResource(id = R.drawable.categories),
                                    contentDescription = "categories"
                                )
                            },
                            selected = ("categories" == selectedItem.value),
                            onClick = {
                                selectedItem.value = "categories"
                                goCategoriesActivity(context)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = newGray,
                                indicatorColor = newGray
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Image(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    painter = painterResource(id = R.drawable.home),
                                    contentDescription = "home"
                                )
                            },
                            selected = ("home" == selectedItem.value),
                            onClick = {
                                selectedItem.value = "home"
                                goHomeActivity(context)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = newGray,
                                indicatorColor = newGray
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Image(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    painter = painterResource(id = R.drawable.message),
                                    contentDescription = "chat"
                                )
                            },
                            selected = ("chat" == selectedItem.value),
                            onClick = {
                                selectedItem.value = "chat"
                                goChatActivity(context)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = newGray,
                                indicatorColor = newGray
                            )
                        )
                    }
                }
                if (newsList.value.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = newGreen
                        )
                    }
                }
            }
        }
    }

    private fun goHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        finish()
    }

    private fun goCategoriesActivity(context: Context) {
        val intent = Intent(context, CategoriesActivity::class.java)
        context.startActivity(intent)
        finish()
    }

    private fun goChatActivity(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)
        finish()
    }
}

@Composable
private fun newsListItem(news: NewsModel, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(newWhite)
                .padding(5.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = news.title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_bold)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = news.date,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_regular)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = news.decsription,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_regular)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                val decodedBytes = Base64.getDecoder().decode(news.img)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                Image(
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = "img",
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .padding(5.dp)
                )
            }
        }
    }
}
