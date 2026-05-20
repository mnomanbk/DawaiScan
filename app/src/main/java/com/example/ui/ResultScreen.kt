package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    viewModel: DawaiViewModel,
    onBackToHome: () -> Unit,
    onReportFake: () -> Unit
) {
    val result by viewModel.currentScanResult.collectAsStateWithLifecycle()
    val loading by viewModel.scanLoading.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verification Result") },
                navigationIcon = {
                    IconButton(onClick = onBackToHome) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Verifying with DRAP Database...")
                }
            } else {
                if (result != null) {
                    val statusColor = if (result!!.status == "Genuine") StatusGenuine else StatusFake
                    val icon = if (result!!.status == "Genuine") Icons.Filled.CheckCircle else Icons.Filled.Warning
                    val title = if (result!!.status == "Genuine") "Genuine Medicine" else "Suspicious / Fake Medicine"
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(28.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
                        ) {
                            Column(modifier = Modifier.padding(4.dp)) {
                                val topAreaColor = if (result!!.status == "Genuine") MaterialTheme.colorScheme.primaryContainer else ErrorBackground
                                val topAreaTextColor = if (result!!.status == "Genuine") MaterialTheme.colorScheme.onPrimaryContainer else OnErrorBackground
                                val subtitleColor = if (result!!.status == "Genuine") MaterialTheme.colorScheme.primary else ErrorTextColor
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(topAreaColor, RoundedCornerShape(24.dp))
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier.size(64.dp).background(statusColor, androidx.compose.foundation.shape.CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(icon, contentDescription = result!!.status, tint = Color.White, modifier = Modifier.size(40.dp))
                                        }
                                        Spacer(Modifier.height(12.dp))
                                        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = topAreaTextColor)
                                        Text(if (result!!.status == "Genuine") "Verified by DRAP Records" else "Failed verification", style = MaterialTheme.typography.labelMedium, color = subtitleColor)
                                    }
                                }
                                
                                Column(modifier = Modifier.padding(20.dp)) {
                                    DetailRow("Medicine Name", result!!.name, result!!.company)
                                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text("BATCH NUMBER", color = SubtitleText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                            Text(result!!.batchNumber, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("EXPIRY DATE", color = SubtitleText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                            Text(result!!.expDate, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = ErrorTextColor)
                                        }
                                    }
                                    
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(InfoBackground, RoundedCornerShape(16.dp)).border(1.dp, InfoBorderColor, RoundedCornerShape(16.dp)).padding(12.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(modifier = Modifier.background(InfoBlue, RoundedCornerShape(8.dp)).padding(8.dp)) {
                                                Icon(Icons.Filled.VerifiedUser, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                            }
                                            Spacer(Modifier.width(12.dp))
                                            Column {
                                                Text("DRAP REGISTRATION", style = MaterialTheme.typography.labelSmall, color = InfoBlue, fontWeight = FontWeight.Bold)
                                                Text("Status: ${result!!.drapStatus}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        if (result!!.status != "Genuine") {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = ErrorBackground),
                                shape = RoundedCornerShape(24.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorBorderColor)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(40.dp).background(Color.White, androidx.compose.foundation.shape.CircleShape), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Filled.Report, contentDescription = "Report", tint = ErrorTextColor)
                                        }
                                        Spacer(Modifier.width(12.dp))
                                        Text("Report suspicious stock?", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = OnErrorBackground)
                                    }
                                    Button(
                                        onClick = onReportFake,
                                        colors = ButtonDefaults.buttonColors(containerColor = ErrorTextColor),
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    ) {
                                        Text("REPORT", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                        }
                        
                        Button(
                            onClick = onBackToHome,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onSurface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
                        ) {
                            Text("Back to Dashboard")
                        }
                    }
                } else {
                    // Unknown code
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                         Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "Unknown",
                            tint = Color.Gray,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Unregistered Product",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "This QR code does not match any registered medicine in the DRAP database. Report this product immediately.",
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Button(
                            onClick = onReportFake,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Report Suspicious Product")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, description: String = "") {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(text = label.uppercase(), color = SubtitleText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(text = value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            if (description.isNotEmpty()) {
                Text(text = description, color = SubtitleText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
