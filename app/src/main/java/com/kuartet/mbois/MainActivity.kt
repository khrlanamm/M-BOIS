package com.kuartet.mbois

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.kuartet.mbois.ui.theme.BrownDark
import com.kuartet.mbois.ui.theme.CreamBackground
import com.kuartet.mbois.ui.theme.MBOISTheme
import com.kuartet.mbois.ui.theme.OrangePrimary
import com.kuartet.mbois.ui.theme.PoppinsFontFamily
import com.kuartet.mbois.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            MBOISTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    var lastBackPressTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime < 2000) {
            (context as? Activity)?.finish()
        } else {
            lastBackPressTime = currentTime
            Toast.makeText(context, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = CreamBackground,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .statusBarsPadding()
                    .height(56.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mbois),
                    contentDescription = "Logo MBOIS",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterStart),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Galeri Budaya",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = BrownDark,
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Color.Transparent)
                        .clickable {
                            context.startActivity(Intent(context, ProfileActivity::class.java))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (user?.photoUrl != null) {
                        AsyncImage(
                            model = user.photoUrl,
                            contentDescription = "Foto Profil",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(1.dp, BrownDark, CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil Default",
                            tint = BrownDark,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = OrangePrimary,
                contentColor = White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "Scan QR",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}