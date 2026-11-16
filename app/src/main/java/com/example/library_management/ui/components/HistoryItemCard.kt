package com.example.library_management.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library_management.data.model.BorrowHistory
import com.example.library_management.data.model.BorrowStatus
import com.example.library_management.data.model.sampleBooks

@Composable
fun HistoryItemCard(
    history: BorrowHistory,
    modifier: Modifier = Modifier
) {
    val book = sampleBooks.find { it.id == history.bookId }
    val bookTitle = book?.title ?: "Sách không xác định"
    val bookAuthor = book?.author ?: "Tác giả không rõ"

    val (statusText, statusBgColor, statusTextColor, statusIcon) = when (history.status) {
        BorrowStatus.BORROWING -> Quad(
            "Đang mượn",
            Color(0xFFE8F5E9),
            Color(0xFF006C45),
            Icons.Default.Schedule
        )
        BorrowStatus.RETURNED -> Quad(
            "Đã trả",
            Color(0xFFE8F5E9),
            Color(0xFF006C45),
            Icons.Default.CheckCircle
        )
        BorrowStatus.OVERDUE -> Quad(
            "Quá hạn",
            Color(0xFFFFEBEE),
            Color(0xFFD32F2F),
            Icons.Default.Error
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tiêu đề sách và Badge trạng thái
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bookTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1C19),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = statusBgColor
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = statusIcon,
                            contentDescription = null,
                            tint = statusTextColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = statusText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusTextColor
                        )
                    }
                }
            }

            // Tác giả kèm icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = bookAuthor,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            HorizontalDivider(color = Color(0xFFF1F3F4), thickness = 1.dp)

            // Khung chứa thông tin ngày tháng
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8F9FA))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Ngày mượn
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = Color(0xFF006C45),
                            modifier = Modifier.size(14.dp)
                        )
                        Column {
                            Text(
                                text = if (history.status == BorrowStatus.RETURNED) "Mượn" else "Ngày mượn",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = history.borrowDate,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1C19)
                            )
                        }
                    }

                    // Hạn trả
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = if (history.status == BorrowStatus.OVERDUE) Color(0xFFD32F2F) else Color(0xFF006C45),
                            modifier = Modifier.size(14.dp)
                        )
                        Column {
                            Text(
                                text = if (history.status == BorrowStatus.OVERDUE) "Hạn trả (Trễ)" else if (history.status == BorrowStatus.RETURNED) "Hạn" else "Hạn trả",
                                fontSize = 10.sp,
                                color = if (history.status == BorrowStatus.OVERDUE) Color(0xFFD32F2F) else Color.Gray
                            )
                            Text(
                                text = history.dueDate,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (history.status == BorrowStatus.OVERDUE) Color(0xFFD32F2F) else Color(0xFF1A1C19)
                            )
                        }
                    }

                    // Ngày trả thực tế (nếu có)
                    if (!history.returnDate.isNullOrEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF006C45),
                                modifier = Modifier.size(14.dp)
                            )
                            Column {
                                Text(
                                    text = "Đã trả",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = history.returnDate,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006C45)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)