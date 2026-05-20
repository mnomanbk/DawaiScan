package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey val id: String,
    val name: String,
    val company: String,
    val batchNumber: String,
    val mfgDate: String,
    val expDate: String,
    val drapStatus: String,
    val status: String // "Genuine", "Suspicious", "Fake"
)

@Entity(tableName = "scan_history")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medicineId: String?, // Nullable if the QR format was corrupted or unknown
    val scannedCode: String,
    val scanTime: Long,
    val statusResult: String // "Genuine", "Suspicious", "Fake", "Unknown"
)
