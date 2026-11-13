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
import androidx.compose.material.icons.filled.Search
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
import com.example.library_management.ui.screen.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LibraryManagementTheme {
                // Quản lý trạng thái tab hiện tại của BottomNavigation
                var selectedTab by remember { mutableStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Column {
                            // ReadingBar chỉ hiển thị ở màn hình Trang chủ (tab 0) hoặc xuyên suốt nếu phù hợp
                            if (selectedTab == 0) {
                                ReadingBar(
                                    bookTitle = "Clean Code",
                                    progress = "68%",
                                    onClick = { /* Xử lý tiếp tục đọc */ }
                                )
                            }
                            // BottomNavigation điều hướng 4 tab
                            AppBottomNavigation(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    }
                ) { innerPadding ->
                    // Điều hướng nội dung màn hình dựa trên tab đang chọn
                    when (selectedTab) {
                        0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
                        1 -> SearchScreen(modifier = Modifier.padding(innerPadding))
                        2 -> SimplePlaceholderScreen(title = "Lịch sử đọc sách", modifier = Modifier.padding(innerPadding))
                        3 -> SimplePlaceholderScreen(title = "Thông tin cá nhân", modifier = Modifier.padding(innerPadding))
                    }
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

    val filteredBooks = sampleBooks.filter { book ->
        val matchesSearch = book.title.contains(searchText, ignoreCase = true) ||
                book.author.contains(searchText, ignoreCase = true) ||
                book.category.contains(searchText, ignoreCase = true)

        val matchesCategory = selectedCategory == "Tất cả" || book.category.equals(selectedCategory, ignoreCase = true)

        matchesSearch && matchesCategory
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item { HomeHeader() }
        item {
            SearchBarComponent(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )
        }
        item { HomeBanner() }
        item {
            CategorySection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
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
        item {
            val chunkedBooks = filteredBooks.chunked(2)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                        if (rowBooks.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

    BookDetailDialog(book = selectedBook, onDismiss = { selectedBook = null })
}

// Màn hình chờ cho tab Tìm kiếm (Sẽ được phát triển chi tiết ở bước sau)
@Composable
fun SearchPlaceholderScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color(0xFF006C45),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Màn hình Tìm kiếm đang phát triển",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1C19)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Vui lòng chờ triển khai ở các bước tiếp theo.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// Màn hình chờ chung cho các tab Lịch sử và Cá nhân
@Composable
fun SimplePlaceholderScreen(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
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
                    Text(text = "Đang đọc dở", fontSize = 10.sp, color = Color.Gray)
                    Text(text = bookTitle, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF006C45))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = progress, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1C19))
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF006C45)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
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
            title = { Text(text = book.title) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Tác giả: ${book.author}")
                    Text(text = "Thể loại: ${book.category}")
                    Text(text = "Số lượng còn: ${book.quantity}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Mô tả:", style = MaterialTheme.typography.titleSmall)
                    Text(text = book.description)
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) { Text("Đóng") }
            }
        )
    }
}