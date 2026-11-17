package com.example.library_management.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LibraryStatsCard(
    borrowedCount: Int,
    borrowingCount: Int,
    readCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SingleStatCard(
            icon = Icons.Outlined.MenuBook,
            count = borrowedCount,
            label = "Đã mượn",
            pastelBgColor = Color(0xFFE8F5E9),
            iconTintColor = Color(0xFF006C45),
            modifier = Modifier.weight(1f)
        )
        SingleStatCard(
            icon = Icons.Outlined.AutoStories,
            count = borrowingCount,
            label = "Đang mượn",
            pastelBgColor = Color(0xFFE3F2FD),
            iconTintColor = Color(0xFF1565C0),
            modifier = Modifier.weight(1f)
        )
        SingleStatCard(
            icon = Icons.Outlined.CheckCircle,
            count = readCount,
            label = "Đã đọc",
            pastelBgColor = Color(0xFFFFF3E0),
            iconTintColor = Color(0xFFEF6C00),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SingleStatCard(
    icon: ImageVector,
    count: Int,
    label: String,
    pastelBgColor: Color,
    iconTintColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(pastelBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTintColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = count.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1A1C19),
                maxLines = 1
            )

            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}