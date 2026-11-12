package com.example.library_management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library_management.data.model.Book
import com.example.library_management.data.model.sampleBooks
import com.example.library_management.ui.components.*
import com.example.library_management.ui.theme.LibraryManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LibraryManagementTheme {
                var selectedTab by remember { mutableStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Column {
                            // Thanh ReadingBar hiển thị sách đang đọc phía trên BottomNavigation
                            ReadingBar(
                                bookTitle = "Clean Code",
                                progress = "68%",
                                onClick = { /* Xử lý tiếp tục đọc */ }
                            )
                            // BottomNavigation điều hướng 4 tab
                            AppBottomNavigation(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    }
                ) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    val categories = listOf("Tất cả", "Lập trình", "Java", "Kotlin", "Thuật toán")

    // Lọc sách theo từ khóa tìm kiếm và danh mục được chọn
    val filteredBooks = sampleBooks.filter { book ->
        val matchesSearch = book.title.contains(searchText, ignoreCase = true) ||
                book.author.contains(searchText, ignoreCase = true) ||
                book.category.contains(searchText, ignoreCase = true)

        val matchesCategory = selectedCategory == "Tất cả" || book.category.equals(selectedCategory, ignoreCase = true)

        matchesSearch && matchesCategory
    }

    // Màn hình chính cuộn mượt mà chứa toàn bộ thành phần
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // 1. Header ứng dụng
        item {
            HomeHeader()
        }

        // 2. Thanh tìm kiếm
        item {
            SearchBarComponent(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
        }

        // 3. Banner khuyến khích học tập
        item {
            HomeBanner()
        }

        // 4. Các chip danh mục lọc sách
        item {
            CategorySection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }

        // Tiêu đề sách nổi bật
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sách nổi bật (${filteredBooks.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Xem tất cả >",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF006C45),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 5. Lưới sách 2 cột (BookGrid)
        item {
            val chunkedBooks = filteredBooks.chunked(2)
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                chunkedBooks.forEach { rowBooks ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowBooks.forEach { book ->
                            Box(modifier = Modifier.weight(1f)) {
                                BookCard(
                                    book = book,
                                    onClick = { selectedBook = book }
                                )
                            }
                        }
                        // Cân bằng khoảng trống nếu dòng lẻ phần tử
                        if (rowBooks.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

    // Hiển thị Dialog chi tiết sách khi người dùng bấm vào Card
    BookDetailDialog(book = selectedBook, onDismiss = { selectedBook = null })
}

@Composable
fun ReadingBar(
    bookTitle: String,
    progress: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE8F5E9),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF006C45)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column {
                    Text(
                        text = "Đang đọc dở",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = bookTitle,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006C45)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = progress,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1C19)
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF006C45)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Tiếp tục đọc",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

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