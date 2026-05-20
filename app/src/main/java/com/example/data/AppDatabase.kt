package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class, ScanHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}
