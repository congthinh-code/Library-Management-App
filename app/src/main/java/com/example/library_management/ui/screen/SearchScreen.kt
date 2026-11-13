package com.example.library_management.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library_management.data.model.Book
import com.example.library_management.data.model.sampleBooks
import com.example.library_management.ui.components.BookCard
import com.example.library_management.ui.components.BookDetailDialog
import com.example.library_management.ui.components.CategorySection
import com.example.library_management.ui.components.RecentSearchSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    var selectedBook by remember { mutableStateOf<Book?>(null) } // Trạng thái lưu sách xem chi tiết

    val categories = listOf("Tất cả", "Lập trình", "Java", "Kotlin", "Thuật toán", "Văn học", "Khoa học", "Công nghệ")
    val recentSearches = listOf("Vũ trụ", "Trí tuệ nhân tạo", "Sapiens", "Clean Code")

    val filteredBooks = sampleBooks.filter { book ->
        val matchesSearch = searchText.isBlank() ||
                book.title.contains(searchText, ignoreCase = true) ||
                book.author.contains(searchText, ignoreCase = true) ||
                book.category.contains(searchText, ignoreCase = true)

        val matchesCategory = selectedCategory == "Tất cả" ||
                book.category.equals(selectedCategory, ignoreCase = true)

        matchesSearch && matchesCategory
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // 1. Header Tìm kiếm
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Quay lại",
                                tint = Color(0xFF1A1C19)
                            )
                        }
                        Column {
                            Text(
                                text = "Thư Viện Số",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF1A1C19)
                            )
                            Text(
                                text = "Tìm Kiếm",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Thông báo",
                                tint = Color(0xFF1A1C19)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Đ", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        }
                    }
                }
            }

            // 2. SearchBar
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Tìm kiếm sách, tác giả, tài liệu...", fontSize = 14.sp, color = Color.Gray) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF006C45))
                    },
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            if (searchText.isNotEmpty()) {
                                IconButton(onClick = { searchText = "" }, modifier = Modifier.size(24.dp)) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Xóa", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }
                            IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                                Icon(imageVector = Icons.Default.Mic, contentDescription = "Mic", tint = Color.Gray, modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF1F3F4),
                        unfocusedContainerColor = Color(0xFFF1F3F4)
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }

            // 3. Tìm gần đây
            item {
                RecentSearchSection(
                    recentSearches = recentSearches,
                    onTagClicked = { term -> searchText = term }
                )
            }

            // 4. Category Section
            item {
                CategorySection(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            // 5. Thống kê kết quả
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Kết quả tìm kiếm",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1C19)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFE8F5E9))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${filteredBooks.size} cuốn sách",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006C45)
                            )
                        }
                    }

                    Surface(
                        onClick = { },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF1F3F4)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "Phổ biến nhất", fontSize = 12.sp, color = Color(0xFF1A1C19))
                            Icon(imageVector = Icons.Outlined.Tune, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            // 6. Danh sách sách / Trạng thái trống
            if (filteredBooks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Không tìm thấy sách phù hợp",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
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
                                            onClick = { selectedBook = book } // Gán sách được chọn khi click vào Card
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

            // 7. Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Đã hiển thị toàn bộ ${filteredBooks.size} kết quả",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Hiển thị Dialog chi tiết sách khi người dùng bấm vào Card
        BookDetailDialog(book = selectedBook, onDismiss = { selectedBook = null })
    }
}