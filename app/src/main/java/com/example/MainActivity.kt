package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.MedicineRepository
import com.example.ui.DawaiViewModel
import com.example.ui.DawaiViewModelFactory
import com.example.ui.HomeScreen
import com.example.ui.ReportScreen
import com.example.ui.ResultScreen
import com.example.ui.ScanScreen
import com.example.ui.theme.DawaiScanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "dawai-database"
        ).fallbackToDestructiveMigration().build()
        val repository = MedicineRepository(db.medicineDao())
        
        enableEdgeToEdge()
        setContent {
            DawaiScanTheme {
                val viewModel: DawaiViewModel = viewModel(factory = DawaiViewModelFactory(repository))
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onNavigateToScan = { navController.navigate("scan") },
                            onNavigateToReport = { navController.navigate("report") }
                        )
                    }
                    composable("scan") {
                        ScanScreen(
                            viewModel = viewModel,
                            onResultNavigate = {
                                navController.popBackStack()
                                navController.navigate("result") 
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("result") {
                        ResultScreen(
                            viewModel = viewModel,
                            onBackToHome = {
                                viewModel.clearScanResult()
                                navController.popBackStack("home", inclusive = false)
                            },
                            onReportFake = {
                                navController.navigate("report")
                            }
                        )
                    }
                    composable("report") {
                        ReportScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
