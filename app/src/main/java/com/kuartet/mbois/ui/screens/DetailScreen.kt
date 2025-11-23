package com.kuartet.mbois.ui.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.kuartet.mbois.ui.theme.BrownDark
import com.kuartet.mbois.ui.theme.CreamBackground
import com.kuartet.mbois.ui.theme.OrangePrimary
import com.kuartet.mbois.ui.theme.PoppinsFontFamily
import com.kuartet.mbois.ui.theme.White
import com.kuartet.mbois.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    cardId: String,
    viewModel: HomeViewModel,
    onBack: () -> Unit,
    onNavigateToInteractive: (String) -> Unit
) {
    val card = remember(cardId) { viewModel.getCardById(cardId) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var isPlaying by remember { mutableStateOf(false) }
    var isAudioLoading by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer() }

    DisposableEffect(Unit) {
        onDispose {
            try {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                mediaPlayer.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(card?.audioUrl) {
        if (!card?.audioUrl.isNullOrEmpty()) {
            try {
                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                mediaPlayer.setDataSource(card!!.audioUrl)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {

                }
                mediaPlayer.setOnCompletionListener {
                    isPlaying = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleAudio() {
        if (card?.audioUrl.isNullOrEmpty()) {
            Toast.makeText(context, "Audio tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            if (isPlaying) {
                mediaPlayer.pause()
                isPlaying = false
            } else {
                if (isAudioLoading) return
                mediaPlayer.start()
                isPlaying = true
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Gagal memutar audio", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = CreamBackground,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CreamBackground)
                    .statusBarsPadding()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = BrownDark
                    )
                }

                Text(
                    text = card?.name ?: "Detail",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = BrownDark,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (card != null) {
                        onNavigateToInteractive(card.id)
                    }
                },
                containerColor = OrangePrimary,
                contentColor = White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Chat",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { innerPadding ->
        if (card == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data kartu tidak ditemukan", fontFamily = PoppinsFontFamily)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(White)
                ) {
                    SubcomposeAsyncImage(
                        model = card.cardUrl,
                        contentDescription = card.name,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .background(Color.Gray.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = OrangePrimary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.name,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = BrownDark,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { toggleAudio() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = OrangePrimary,
                            contentColor = White
                        ),
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(4.dp, CircleShape)
                    ) {
                        if (isAudioLoading) {
                            CircularProgressIndicator(
                                color = White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play Audio"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFF3E0), RoundedCornerShape(50))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = card.categoryName,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = BrownDark
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Deskripsi Singkat",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = BrownDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = card.desc,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = BrownDark,
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}