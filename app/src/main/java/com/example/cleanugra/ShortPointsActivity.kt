package com.example.cleanugra

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.cleanugra.apiMethods.ReceptionsApiMethods
import com.example.cleanugra.models.categories.CategoryModel
import com.example.cleanugra.models.receptions.ReceptionShortModel
import com.example.cleanugra.uiData.newGray
import com.example.cleanugra.uiData.newGreen
import com.example.cleanugra.uiData.newWhite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64


class ShortPointsActivity : ComponentActivity() {
    private val connect = Retrofit.Builder().baseUrl("https://cua.glitch.me/api/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val receptionsApi = connect.create(ReceptionsApiMethods::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val items = listOf("categories", "home", "chat")
            val categoryTitle: String = intent.getStringExtra("title")!!.toString()
            val selectedItem = remember {
                mutableStateOf(items[0])
            }

            val pointsList = remember {
                mutableStateOf(emptyList<ReceptionShortModel>())
            }
            LaunchedEffect(Unit) {
                pointsList.value =
                    receptionsApi.getPointsByTitleCategory(categoryTitle).body()!!
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = newGray
            ) {
                Text(
                    text = "Пункты приема сырья",
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
                        text = "Пункты приема сырья",
                        color = newGreen,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_xtrabold)),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    if (pointsList.value.isEmpty() == false) {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 90.dp, top = 40.dp)
                        ) {
                            itemsIndexed(
                                pointsList.value
                            ) { index, item ->
                                pointsListItem(item, context)
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
                if (pointsList.value.isEmpty()) {
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


    private fun goChatActivity(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)
        finish()
    }

    private fun goCategoriesActivity(context: Context) {
        val intent = Intent(context, CategoriesActivity::class.java)
        context.startActivity(intent)
        finish()
    }
}


@Composable
private fun pointsListItem(point: ReceptionShortModel, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        //.clickable() {
        //    val intent = Intent(context, ShortPointsActivity::class.java)
        //    intent.putExtra("title", categy.title)
        //    context.startActivity(intent)
        //},
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(newWhite)
                .padding(5.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        end = 5.dp,
                        start = 8.dp
                    )
                ) {
                    Text(
                        text = point.title,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_bold)),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = point.address,
                        fontFamily = FontFamily(Font(R.font.jetbrainsmono_regular)),
                    )
                }
            }
        }
    }
}