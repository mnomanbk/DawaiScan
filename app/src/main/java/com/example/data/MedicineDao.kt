package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines WHERE id = :code OR batchNumber = :code")
    suspend fun getMedicineByCode(code: String): Medicine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<Medicine>)

    @Query("SELECT * FROM scan_history ORDER BY scanTime DESC")
    fun getScanHistory(): Flow<List<ScanHistory>>

    @Insert
    suspend fun insertScanHistory(scan: ScanHistory)
}
