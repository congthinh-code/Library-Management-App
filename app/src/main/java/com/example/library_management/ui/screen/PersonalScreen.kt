package com.example.library_management.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library_management.ui.components.*

@Composable
fun PersonalScreen(
    onHistoryClick: () -> Unit = {}, // Bổ sung callback điều hướng lịch sử mượn sách
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 24.dp)
        ) {
            // 1. Header ứng dụng
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
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF006C45)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "TV",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
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
                                text = "Cá Nhân",
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
                            Text(
                                text = "Đ",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            // 2. Tiêu đề & Subtitle
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Cá nhân",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1C19)
                    )
                    Text(
                        text = "Quản lý thông tin tài khoản của bạn",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // 3. Thẻ thông tin cá nhân
            item {
                ProfileHeaderCard(
                    name = "Nguyễn Văn A",
                    email = "user@gmail.com",
                    memberId = "TV-2026-889",
                    onEditProfileClick = { }
                )
            }

            // 4. Thống kê thư viện
            item {
                LibraryStatsCard(
                    borrowedCount = 12,
                    borrowingCount = 2,
                    readCount = 8
                )
            }

            // 5. Thẻ sách đang đọc
            item {
                CurrentReadingCard(
                    bookTitle = "Tuyển tập Văn học Việt Nam",
                    progressText = "Chương IV (Trang 142/380)",
                    progressPercentage = 0.37f,
                    dueDateNotice = "Hạn còn 5 ngày"
                )
            }

            // 6. Danh sách 5 chức năng
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        SettingMenuItem(
                            icon = Icons.Default.PersonOutline,
                            title = "Thông tin cá nhân",
                            subtitle = "Cập nhật chi tiết tài khoản",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F3F4), thickness = 1.dp)
                        SettingMenuItem(
                            icon = Icons.Default.History,
                            title = "Lịch sử mượn sách",
                            subtitle = "Xem lại sách đã và đang mượn",
                            onClick = onHistoryClick // Kích hoạt callback chuyển tab sang Lịch sử
                        )
                        HorizontalDivider(color = Color(0xFFF1F3F4), thickness = 1.dp)
                        SettingMenuItem(
                            icon = Icons.Default.FavoriteBorder,
                            title = "Sách yêu thích",
                            subtitle = "Danh sách sách đã lưu",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F3F4), thickness = 1.dp)
                        SettingMenuItem(
                            icon = Icons.Default.Settings,
                            title = "Cài đặt",
                            subtitle = "Tùy chỉnh giao diện và thông báo",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F3F4), thickness = 1.dp)
                        SettingMenuItem(
                            icon = Icons.AutoMirrored.Filled.HelpOutline,
                            title = "Trợ giúp và hỗ trợ",
                            subtitle = "Câu hỏi thường gặp và liên hệ",
                            onClick = {}
                        )
                    }
                }
            }

            // 7. Nút Đăng xuất tài khoản
            item {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD32F2F),
                        containerColor = Color(0xFFFFF8F8)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFFFCDD2))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Đăng xuất tài khoản",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // 8. Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Thư Viện Số Quốc Gia",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        text = "Phiên bản ứng dụng 2.4.0",
                        fontSize = 11.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}