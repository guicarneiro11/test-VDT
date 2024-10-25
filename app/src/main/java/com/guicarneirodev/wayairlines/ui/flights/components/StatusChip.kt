package com.guicarneirodev.wayairlines.ui.flights.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guicarneirodev.wayairlines.ui.theme.ChipTextBlack
import com.guicarneirodev.wayairlines.ui.theme.ChipTextWhite
import com.guicarneirodev.wayairlines.ui.theme.StatusBlue
import com.guicarneirodev.wayairlines.ui.theme.StatusGreen
import com.guicarneirodev.wayairlines.ui.theme.StatusOrange
import com.guicarneirodev.wayairlines.ui.theme.StatusRed
import com.guicarneirodev.wayairlines.ui.theme.StatusYellow
import java.util.Locale

@Composable
fun StatusChip(status: String, onClick: () -> Unit) {
    val (backgroundColor, textColor) = when (status.lowercase()) {
        "concluído", "no_horario" -> StatusGreen to ChipTextWhite
        "cancelado" -> StatusRed to ChipTextWhite
        "agendado" -> StatusBlue to ChipTextWhite
        "atrasou" -> StatusOrange to ChipTextBlack
        else -> StatusYellow to ChipTextBlack
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = when (status.lowercase()) {
                "no_horario" -> "No Horário"
                "atrasou" -> "Atrasou"
                else -> status.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            },
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}