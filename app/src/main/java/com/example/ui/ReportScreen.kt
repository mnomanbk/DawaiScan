package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(onBack: () -> Unit) {
    var medicineName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Fake Medicine") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Submit a report to DRAP",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = medicineName,
                onValueChange = { medicineName = it },
                label = { Text("Medicine Name / Batch (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Pharmacy Name or Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = comments,
                onValueChange = { comments = it },
                label = { Text("Additional Details") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Simulated Camera App Launch */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Photo")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Attach Evidence Photo")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { showSuccessDialog = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Submit Report")
            }
        }
        
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Report Submitted") },
                text = { Text("Thank you for reporting. DRAP authorities have been notified of this suspicious activity.") },
                confirmButton = {
                    TextButton(onClick = { 
                        showSuccessDialog = false
                        onBack() 
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
