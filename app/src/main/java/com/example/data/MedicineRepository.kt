package com.example.data

import kotlinx.coroutines.flow.Flow

class MedicineRepository(private val dao: MedicineDao) {
    val scanHistory: Flow<List<ScanHistory>> = dao.getScanHistory()

    suspend fun populateInitialData() {
        // Only insert if empty? Let's just always insert for prototyping.
        val sampleData = listOf(
            Medicine("123456789", "Panadol 500mg", "GSK Pakistan", "B1920", "Jan 2024", "Dec 2026", "Approved", "Genuine"),
            Medicine("987654321", "Augmentin 625mg", "Fake Pharma", "F001X", "Jan 2023", "Dec 2023", "Unregistered", "Fake"),
            Medicine("112233445", "Flagyl 400mg", "Sanofi Aventis", "SL329", "Mar 2024", "Mar 2027", "Approved", "Genuine")
        )
        dao.insertMedicines(sampleData)
    }

    suspend fun verifyMedicine(qrCode: String): Medicine? {
        val medicine = dao.getMedicineByCode(qrCode)
        val statusResult = medicine?.status ?: "Unknown"
        
        dao.insertScanHistory(
            ScanHistory(
                medicineId = medicine?.id,
                scannedCode = qrCode,
                scanTime = System.currentTimeMillis(),
                statusResult = statusResult
            )
        )
        return medicine
    }
}
