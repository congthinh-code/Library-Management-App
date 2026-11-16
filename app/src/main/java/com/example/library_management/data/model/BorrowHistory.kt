package com.example.library_management.data.model

enum class BorrowStatus {
    BORROWING, // Đang mượn
    RETURNED,  // Đã trả
    OVERDUE    // Quá hạn
}

data class BorrowHistory(
    val id: Int,
    val bookId: Int, // Liên kết với id của Book
    val borrowDate: String,
    val dueDate: String,
    val returnDate: String?, // Có thể null nếu chưa trả
    val status: BorrowStatus
)

// Dữ liệu mẫu lịch sử mượn sách tham chiếu đúng các id trong BookData.kt (1 đến 4)
val sampleBorrowHistories = listOf(
    BorrowHistory(
        id = 1,
        bookId = 1, // Clean Code
        borrowDate = "01/11/2026",
        dueDate = "15/11/2026",
        returnDate = null,
        status = BorrowStatus.BORROWING
    ),
    BorrowHistory(
        id = 2,
        bookId = 3, // Head First Kotlin
        borrowDate = "20/10/2026",
        dueDate = "05/11/2026",
        returnDate = "04/11/2026",
        status = BorrowStatus.RETURNED
    ),
    BorrowHistory(
        id = 3,
        bookId = 4, // Introduction to Algorithms
        borrowDate = "10/09/2026",
        dueDate = "25/09/2026",
        returnDate = null,
        status = BorrowStatus.OVERDUE
    ),
    BorrowHistory(
        id = 4,
        bookId = 2, // Effective Java
        borrowDate = "05/10/2026",
        dueDate = "20/10/2026",
        returnDate = "18/10/2026",
        status = BorrowStatus.RETURNED
    )
)