package com.example.cleanugra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cleanugra.uiData.newGray
import com.example.cleanugra.uiData.newWhite

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val items = listOf("categories", "home", "chat")
            var selectedItem = remember { mutableStateOf(items[1]) }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = newGray
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
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
                            onClick = { selectedItem.value = "home" },
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