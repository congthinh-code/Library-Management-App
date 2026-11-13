package com.example.library_management.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.library_management.data.model.Book

@Composable
fun BookDetailDialog(book: Book?, onDismiss: () -> Unit) {
    if (book != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = book.title)
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Tác giả: ${book.author}")
                    Text(text = "Thể loại: ${book.category}")
                    Text(text = "Số lượng còn: ${book.quantity}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Mô tả:", style = MaterialTheme.typography.titleSmall)
                    Text(text = book.description)
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Đóng")
                }
            }
        )
    }
}