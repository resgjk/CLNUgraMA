package com.example.cleanugra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanugra.uiData.newGray
import com.example.cleanugra.uiData.newGreen
import com.example.cleanugra.uiData.newWhite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailPointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val items = listOf("categories", "home", "chat")
            val selectedItem = remember {
                mutableStateOf(items[0])
            }
            val uriHandler = LocalUriHandler.current
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 7.dp, end = 7.dp, bottom = 250.dp, top = 10.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(newWhite)
                            .padding(15.dp),
                    ) {
                        Column {
                            Row {
                                Text(
                                    intent.getStringExtra("title")!!,
                                    fontSize = 21.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_xtrabold)
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                            Row {
                                Text(
                                    "Адрес: ",
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_bold)
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp, top = 5.dp)
                                )
                                Text(
                                    intent.getStringExtra("address")!!,
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_regular)
                                    ),
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Row {
                                Text(
                                    "Время работы: ", fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_bold)
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp, top = 5.dp)
                                )
                                Text(
                                    intent.getStringExtra("time")!!, fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_regular)
                                    ), modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Row {
                                Text(
                                    "Описание: ", fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_bold)
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp, top = 5.dp)
                                )
                                Text(
                                    intent.getStringExtra("description")!!, fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_regular)
                                    ), modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Row {
                                Text(
                                    "Номер телефона: ", fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_bold)
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 4.dp, top = 5.dp)
                                )
                                Text(
                                    intent.getStringExtra("phone_number")!!, fontSize = 17.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_regular)
                                    ),
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Row {
                                if (intent.getStringExtra("vk_ref")!! != "-") {
                                    Button(
                                        onClick = { uriHandler.openUri(intent.getStringExtra("vk_ref")!!) },
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(100.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = newWhite
                                        )
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.vk),
                                            contentDescription = "vk",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                                if (intent.getStringExtra("tg_ref")!! != "-") {
                                    Button(
                                        onClick = { uriHandler.openUri(intent.getStringExtra("tg_ref")!!) },
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(100.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = newWhite
                                        )
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.tg),
                                            contentDescription = "tg",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
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
