package com.kuartet.mbois.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.kuartet.mbois.ui.theme.BrownDark
import com.kuartet.mbois.ui.theme.CreamBackground
import com.kuartet.mbois.ui.theme.OrangePrimary
import com.kuartet.mbois.ui.theme.PoppinsFontFamily
import com.kuartet.mbois.ui.theme.White
import com.kuartet.mbois.viewmodel.HomeViewModel
import java.util.concurrent.Executors
import androidx.core.net.toUri

@OptIn(ExperimentalGetImage::class)
class QrCodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
    private val scanner = BarcodeScanning.getClient(options)

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val rawValue = barcode.rawValue
                    if (rawValue != null) {
                        onQrCodeScanned(rawValue)
                        break
                    }
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}

@Composable
fun ScanScreen(
    onBack: () -> Unit,
    onCardDetected: (String) -> Unit,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember { mutableStateOf(false) }
    var showUnrecognizedDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var pendingCardId by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            hasCameraPermission = true
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val onQrScanned: (String) -> Unit = onQrScanned@{ value ->
        if (showUnrecognizedDialog || showConfirmDialog) return@onQrScanned
        val uri = try {
            value.toUri()
        } catch (_: Exception) {
            null
        }
        if (uri == null || uri.scheme != "https" || uri.host != "m-bois.web.app") {
            showUnrecognizedDialog = true
        } else {
            val segments = uri.pathSegments
            if (segments.size == 1 && segments[0].isNotBlank()) {
                pendingCardId = segments[0]
                showConfirmDialog = true
            } else {
                showUnrecognizedDialog = true
            }
        }
    }

    BackHandler {
        onBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(White, CircleShape)
                        .clickable { onBack() }
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = BrownDark
                    )
                }
                Text(
                    text = "Scan QR Code",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = BrownDark,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (hasCameraPermission) {
                    Box(
                        modifier = Modifier
                            .size(260.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .fillMaxSize(),
                            factory = { ctx ->
                                val previewView = PreviewView(ctx)
                                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                                cameraProviderFuture.addListener({
                                    val cameraProvider = cameraProviderFuture.get()
                                    val preview = Preview.Builder().build().also {
                                        it.surfaceProvider = previewView.surfaceProvider
                                    }
                                    val analysis = ImageAnalysis.Builder()
                                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                        .build()
                                        .also {
                                            it.setAnalyzer(
                                                Executors.newSingleThreadExecutor(),
                                                QrCodeAnalyzer { value ->
                                                    previewView.post {
                                                        onQrScanned(value)
                                                    }
                                                }
                                            )
                                        }
                                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                                    try {
                                        cameraProvider.unbindAll()
                                        cameraProvider.bindToLifecycle(
                                            lifecycleOwner,
                                            cameraSelector,
                                            preview,
                                            analysis
                                        )
                                    } catch (_: Exception) {
                                    }
                                }, ContextCompat.getMainExecutor(ctx))
                                previewView
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 4.dp,
                                    color = OrangePrimary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(0.88f)
                                .height(4.dp)
                                .background(OrangePrimary)
                        )
                    }
                } else {
                    Text(
                        text = "Izin kamera diperlukan untuk melakukan scan QR.",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 14.sp,
                        color = BrownDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(
                        color = OrangePrimary,
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Arahkan kamera ke QR Code",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = White
                    )
                    Text(
                        text = "di belakang kartu fisik M-BOIS",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = White
                    )
                }
            }
        }

        if (showUnrecognizedDialog) {
            AlertDialog(
                onDismissRequest = { showUnrecognizedDialog = false },
                title = {
                    Text(
                        text = "Objek tidak dikenali",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = BrownDark
                    )
                },
                text = {
                    Text(
                        text = "Objek tidak dikenali, silahkan scan bagian belakang M-BOIS Kuartet.",
                        fontFamily = PoppinsFontFamily,
                        color = BrownDark
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showUnrecognizedDialog = false }) {
                        Text(text = "OK", color = OrangePrimary)
                    }
                },
                containerColor = White,
                textContentColor = BrownDark,
                titleContentColor = BrownDark
            )
        }

        if (showConfirmDialog && pendingCardId != null) {
            val card = viewModel.getCardById(pendingCardId!!)
            val cardName = card?.name ?: "Kartu M-BOIS"
            val categoryName = card?.categoryName?: "Kategori Kartu"

            AlertDialog(
                onDismissRequest = {
                    showConfirmDialog = false
                    pendingCardId = null
                },
                title = {
                    Text(
                        text = "$categoryName: $cardName",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = BrownDark
                    )
                },
                text = {
                    Text(
                        text = "Kartu Ditemukan, lanjutkan ke halaman Pustaka Budaya $cardName",
                        fontFamily = PoppinsFontFamily,
                        color = BrownDark
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val id = pendingCardId
                            if (id != null) {
                                showConfirmDialog = false
                                pendingCardId = null
                                onCardDetected(id)
                            }
                        }
                    ) {
                        Text(text = "Lihat Detail", color = OrangePrimary)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showConfirmDialog = false
                            pendingCardId = null
                        }
                    ) {
                        Text(text = "Batal", color = BrownDark)
                    }
                },
                containerColor = White
            )
        }
    }
}