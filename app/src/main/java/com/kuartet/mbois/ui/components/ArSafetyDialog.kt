package com.kuartet.mbois.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuartet.mbois.ui.theme.BrownDark
import com.kuartet.mbois.ui.theme.OrangePrimary
import com.kuartet.mbois.ui.theme.PoppinsFontFamily
import com.kuartet.mbois.ui.theme.White

@Composable
fun ArSafetyDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dismissLabel: String = "Kembali"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = null,
                tint = OrangePrimary
            )
        },
        title = {
            Text(
                text = "Peringatan Keselamatan AR",
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = BrownDark
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SafetyPoint(
                    icon = Icons.Default.FamilyRestroom,
                    text = "Anak-anak wajib didampingi orang tua atau orang dewasa saat menggunakan fitur AR.",
                    translation = "Children must use AR under parental supervision."
                )
                SafetyPoint(
                    icon = Icons.Default.Visibility,
                    text = "Perhatikan lingkungan sekitar. Pastikan area aman dan bebas rintangan.",
                    translation = "Stay aware of your surroundings."
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Saya Mengerti", color = OrangePrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissLabel, color = BrownDark)
            }
        },
        containerColor = White
    )
}

@Composable
private fun SafetyPoint(icon: ImageVector, text: String, translation: String) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OrangePrimary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = text,
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                color = BrownDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = translation,
                fontFamily = PoppinsFontFamily,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray
            )
        }
    }
}
