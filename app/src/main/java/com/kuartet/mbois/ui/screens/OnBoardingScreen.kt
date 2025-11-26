package com.kuartet.mbois.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kuartet.mbois.R
import com.kuartet.mbois.ui.theme.BrownDark
import com.kuartet.mbois.ui.theme.CreamBackground
import com.kuartet.mbois.ui.theme.OrangePrimary
import com.kuartet.mbois.ui.theme.PoppinsFontFamily
import com.kuartet.mbois.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OnBoardingPage(
    val title: String,
    val subtitle: String,
    val images: List<Any>
)

@Composable
fun OnBoardingScreen(onComplete: () -> Unit) {
    val pages = listOf(
        OnBoardingPage(
            title = "Selamat Datang!",
            subtitle = "Jelajahi Budaya Jawa Timur",
            images = listOf(
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764154962/Suku_Jawa_heycqm.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764154965/Suku_Tengger_r4klz3.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764154961/Suku_Samin_blfocm.png"
            )
        ),
        OnBoardingPage(
            title = "Interaksi 3D & AR",
            subtitle = "Lihat budaya dalam bentuk 3D dan Augmented Reality",
            images = listOf(
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764156440/Peninggalan_Kerajaan_Kahuripan_lqxovm.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764156440/Peninggalan_Kerajaan_Majapahit_skpkpu.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1764156439/Peninggalan_Kerajaan_Singasari_cdcgws.png"
            )
        ),
        OnBoardingPage(
            title = "Tanya AI Budaya",
            subtitle = "Dapatkan informasi mendalam dengan AI Chatbot",
            images = listOf(
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1763856718/c2_qwgqdg.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1763856718/c3_cjbvdv.png",
                "https://res.cloudinary.com/du6ck27qw/image/upload/v1763856717/c4_tcvfrf.png"
            )
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.mbois),
                contentDescription = "Logo MBOIS",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { pageIndex ->
                OnBoardingPageContent(page = pages[pageIndex])
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(pages.size) { iteration ->
                    val width by animateDpAsState(
                        targetValue = if (pagerState.currentPage == iteration) 24.dp else 8.dp,
                        label = "dotWidth"
                    )
                    val color =
                        if (pagerState.currentPage == iteration) OrangePrimary else Color.LightGray

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(8.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onComplete()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 32.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Mulai" else "Lanjut",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = White
                )
            }
        }
    }
}

@Composable
fun OnBoardingPageContent(page: OnBoardingPage) {
    var currentImageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(page) {
        currentImageIndex = 0
        while (true) {
            delay(1500)
            currentImageIndex = (currentImageIndex + 1) % page.images.size
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentImageIndex,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f)) togetherWith
                            fadeOut(animationSpec = tween(500))
                },
                label = "CarouselAnimation"
            ) { index ->
                val imageSource = page.images[index]
                if (imageSource is String) {
                    AsyncImage(
                        model = imageSource,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else if (imageSource is Int) {
                    Image(
                        painter = painterResource(id = imageSource),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = page.title,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BrownDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = page.subtitle,
            fontFamily = PoppinsFontFamily,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}