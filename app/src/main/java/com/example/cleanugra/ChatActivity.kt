package com.example.cleanugra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanugra.apiMethods.MessageApiMethods
import com.example.cleanugra.apiMethods.NewsApiMethods
import com.example.cleanugra.models.message.MessageModel
import com.example.cleanugra.uiData.newGray
import com.example.cleanugra.uiData.newGreen
import com.example.cleanugra.uiData.newWhite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : ComponentActivity() {
    private val connect = Retrofit.Builder().baseUrl("https://cua.glitch.me/api/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val msgApi = connect.create(MessageApiMethods::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @OptIn(ExperimentalMaterial3Api::class) setContent {
            val context = LocalContext.current
            val items = listOf("categories", "home", "chat")
            val selectedItem = remember { mutableStateOf(items[2]) }
            val text = remember { mutableStateOf("") }
            val isSendMsg = remember {
                mutableStateOf(false)
            }

            Surface(
                modifier = Modifier.fillMaxSize(), color = newGray
            ) {
                Text(
                    text = "Чат - бот",
                    color = newGreen,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_xtrabold)),
                    fontSize = 21.sp,
                    modifier = Modifier.padding(7.dp)
                )
                if (isSendMsg.value) {
                    getPostMessage(text = text.value, api = msgApi, check = isSendMsg)
                }
                Column(
                    modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            modifier = Modifier
                                .padding(10.dp)
                                .width(320.dp),
                            placeholder = {
                                Text(
                                    "Введите сообщение",
                                    color = Color.Gray,
                                    fontFamily = FontFamily(
                                        Font(R.font.jetbrainsmono_regular)
                                    )
                                )
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = newWhite,
                            )
                        )
                        Button(
                            onClick = {
                                isSendMsg.value = true
                            },
                            modifier = Modifier
                                .height(55.dp)
                                .padding(end = 7.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = newGreen
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text("->", fontSize = 20.sp, color = newWhite)
                        }
                    }
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = newWhite,
                    ) {
                        NavigationBarItem(icon = {
                            Image(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                painter = painterResource(id = R.drawable.categories),
                                contentDescription = "categories"
                            )
                        }, selected = ("categories" == selectedItem.value), onClick = {
                            selectedItem.value = "categories"
                            goCategoriesActivity(context)
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = newGray, indicatorColor = newGray
                        )
                        )
                        NavigationBarItem(icon = {
                            Image(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                painter = painterResource(id = R.drawable.home),
                                contentDescription = "home"
                            )
                        }, selected = ("home" == selectedItem.value), onClick = {
                            selectedItem.value = "home"
                            goHomeActivity(context)
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = newGray, indicatorColor = newGray
                        )
                        )
                        NavigationBarItem(icon = {
                            Image(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                painter = painterResource(id = R.drawable.message),
                                contentDescription = "chat"
                            )
                        }, selected = ("chat" == selectedItem.value), onClick = {
                            selectedItem.value = "chat"
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = newGray, indicatorColor = newGray
                        )
                        )
                    }
                }
            }
        }
    }

    private fun goCategoriesActivity(context: Context) {
        val intent = Intent(context, CategoriesActivity::class.java)
        context.startActivity(intent)
        finish()
    }

    private fun goHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        finish()
    }
}

@Composable
private fun getPostMessage(text: String, api: MessageApiMethods, check: MutableState<Boolean>) {
    val postMsg = MessageModel(message = text)
    var getMsg: String = ""
    if (text == "Куда сдать стекло в Сургуте?") {
        getMsg = "Сдать стекло вы можете в пункт Югра Собирает по адресу Улица 30 лет Победы, 74"
    } else if (text == "Что такое экология?") {
        getMsg =
            "Экология — познание экономики природы, одновременное исследование всех взаимоотношений живого с органическими и неорганическими компонентами окружающей среды… Одним словом, экология — это наука, изучающая все сложные взаимосвязи в природе."
    } else if (text == "Как переработка отходов помогает природе?") {
        getMsg =
            "Переработка может предотвратить захоронение потенциально полезных материалов и сократить потребление первичного сырья,тем самым снизив потребление энергии, загрязнение воздуха (от сжигания), загрязнение воды, загрязнение почвы (от захоронения)"
    }
    //LaunchedEffect(Unit) {
    //    getMsg.value = api.getMessage(postMsg).body()!!.message
    //}

    Column(modifier = Modifier.padding(top = 50.dp)) {
        Card(
            modifier = Modifier
                .width(395.dp)
                .padding(top = 7.dp, start = 245.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(newWhite)
                    .padding(5.dp),
            ) {
                Text(
                    text = text,
                    fontFamily = FontFamily(Font(R.font.jetbrainsmono_regular)),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .width(220.dp)
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
                Text(
                    text = getMsg, fontFamily = FontFamily(Font(R.font.jetbrainsmono_regular)),
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

    }
}