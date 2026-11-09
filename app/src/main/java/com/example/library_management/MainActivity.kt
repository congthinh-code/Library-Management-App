package com.example.library_management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.library_management.ui.theme.LibraryManagementTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

import com.example.library_management.data.model.Book
import com.example.library_management.data.model.sampleBooks

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LibraryManagementTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    BookListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BookListScreen(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }

    // Trạng thái lưu cuốn sách đang được chọn để xem chi tiết (null nếu không chọn cuốn nào)
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    val filteredBooks = sampleBooks.filter { book ->
        book.title.contains(searchText, ignoreCase = true) ||
                book.author.contains(searchText, ignoreCase = true) ||
                book.category.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Quản lý thư viện",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Tìm kiếm sách") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Danh sách sách (${filteredBooks.size})",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredBooks) { book ->
                BookItem(
                    book = book,
                    onClick = { selectedBook = book } // Khi bấm vào sách, gán giá trị để hiện chi tiết
                )
            }
        }
    }

    // Hiển thị Dialog chi tiết sách khi `selectedBook` khác null
    selectedBookoyama(book = selectedBook, onDismiss = { selectedBook = null })
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Thêm sự kiện click cho Card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Tác giả: ${book.author}")
            Text(text = "Thể loại: ${book.category}")
            Text(text = "Số lượng: ${book.quantity}")
        }
    }
}

@Composable
fun selectedBookoyama(book: Book?, onDismiss: () -> Unit) {
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
                    Text(text = "Số lượng: ${book.quantity}")
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

@Preview(showBackground = true)
@Composable
fun BookListPreview() {
    LibraryManagementTheme {
        BookListScreen()
    }
}