package com.example.everydaytasks.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.R
import com.example.everydaytasks.ui.theme.AlmostWhite
import com.example.everydaytasks.ui.theme.Biruz
import com.example.everydaytasks.ui.theme.LoginButtonColor
import com.example.everydaytasks.ui.theme.TextColor
import com.example.everydaytasks.ui.theme.TextFieldColor
import com.example.everydaytasks.ui.theme.TextFieldLabelColor
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

@Composable
fun LoginPage(
    onNavigationToProgressPage: (ProgressScreenDataObject) -> Unit
) {
    val email = remember{
        mutableStateOf("")
    }
    val password = remember{
        mutableStateOf("")
    }

    val auth = Firebase.auth

    Image(
        painterResource(
            id = R.drawable.loginscreenbg
        ),
        contentDescription = null,
        Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.95f)
                .padding(bottom = 25.dp)
                .offset(y = (-30).dp)
                .height(300.dp)
                .background(
                    color = AlmostWhite,
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = TextFieldColor,
                        focusedContainerColor = TextFieldColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.90f)
                        .height(50.dp),
                    label = {
                        Text(
                            text = "Email",
                            color = TextFieldLabelColor
                        )
                    }
                )
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                TextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = TextFieldColor,
                        focusedContainerColor = TextFieldColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.90f)
                        .height(50.dp),
                    label = {
                        Text(
                            text = "Password",
                            color = TextFieldLabelColor
                        )
                    }
                )
                Spacer(
                    modifier = Modifier
                        .height(19.dp)
                )
                Box(
                    modifier = Modifier
                        .height(54.dp)
                        .width(340.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            1.dp,
                            Biruz,
                            RoundedCornerShape(10.dp)
                        )
                        .background(LoginButtonColor)
                        .clickable {
                            login(
                                auth,
                                email.value,
                                password.value
                            )
                            onNavigationToProgressPage(
                                ProgressScreenDataObject()
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Log in",
                        color = AlmostWhite,
                        fontSize = 26.sp,
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(7.dp)
                )
                Box(
                    modifier = Modifier
                        .height(54.dp)
                        .width(340.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .border(
                            1.dp,
                            Biruz,
                            RoundedCornerShape(10.dp)
                        )
                        .background(LoginButtonColor)
                        .clickable{
                            signUp(
                                auth,
                                email.value,
                                password.value
                            )
                            onNavigationToProgressPage(
                                ProgressScreenDataObject()
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign up",
                        color = AlmostWhite,
                        fontSize = 26.sp
                    )
                }
                Text(
                    text = "Forgot password?",
                    color = TextColor,
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String
) {
    auth.createUserWithEmailAndPassword(
        email,
        password
    )
        .addOnCompleteListener {
            if (it.isSuccessful) {
                null
            } else {
                null
            }
        }
}

private fun login(
    auth: FirebaseAuth,
    email: String,
    password: String
) {
    auth.signInWithEmailAndPassword(
        email,
        password
    )
        .addOnCompleteListener {
            if (it.isSuccessful) {
                null
            } else {
                null
            }
        }
}