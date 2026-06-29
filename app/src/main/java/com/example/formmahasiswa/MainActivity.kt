package com.example.formmahasiswa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formmahasiswa.ui.theme.FormmahasiswaTheme

// Data Class untuk Mahasiswa
data class Mahasiswa(
    val id: Int,
    val nim: String,
    val nama: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormmahasiswaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        @OptIn(ExperimentalMaterial3Api::class)
                        TopAppBar(
                            title = { Text("UPH 24SI3", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF212121)
                            )
                        )
                    }
                ) { innerPadding ->
                    StudentCrudScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun StudentCrudScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    // State Management
    var nim by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var cari by remember { mutableStateOf("") }
    
    // List Mahasiswa dengan data awal Florencia Audry
    val students = remember { 
        mutableStateListOf(Mahasiswa(1, "03081240032", "Florencia Audry")) 
    }
    var selectedStudent by remember { mutableStateOf<Mahasiswa?>(null) }
    var nextId by remember { mutableStateOf(2) }

    var isAscending by remember { mutableStateOf(true) }
    
    // Logic Search & Filter & Sort
    val filteredStudents = students.filter { 
        it.nama.contains(cari, ignoreCase = true) || it.nim.contains(cari)
    }.let { 
        if (isAscending) it.sortedBy { s -> s.nama } else it.sortedByDescending { s -> s.nama }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- FORM INPUT BOX ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3E5F5), shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFD1C4E9), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(
                if (selectedStudent == null) "Form Input Mahasiswa (Tambah Baru)" else "Form Edit Mahasiswa",
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            
            TextField(
                value = nim,
                onValueChange = { nim = it },
                placeholder = { Text("Masukkan NIM (contoh: 0308122001)", fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF00796B),
                    unfocusedIndicatorColor = Color.LightGray
                ),
                singleLine = true
            )
            
            TextField(
                value = nama,
                onValueChange = { nama = it },
                placeholder = { Text("Masukkan Nama Lengkap", fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF00796B),
                    unfocusedIndicatorColor = Color.LightGray
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val buttonModifier = Modifier.weight(1f).height(36.dp)
                val buttonShape = RoundedCornerShape(4.dp)
                
                // Button TAMBAH
                Button(
                    onClick = {
                        if (nim.isNotBlank() && nama.isNotBlank()) {
                            students.add(Mahasiswa(nextId++, nim, nama))
                            nim = ""; nama = ""
                            Toast.makeText(context, "Berhasil Menambah!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Isi NIM dan Nama!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = buttonModifier,
                    enabled = selectedStudent == null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                    shape = buttonShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("TAMBAH", color = if (selectedStudent == null) Color.Black else Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                
                // Button UPDATE
                Button(
                    onClick = {
                        val index = students.indexOfFirst { it.id == selectedStudent?.id }
                        if (index != -1) {
                            students[index] = Mahasiswa(selectedStudent!!.id, nim, nama)
                            selectedStudent = null
                            nim = ""; nama = ""
                            Toast.makeText(context, "Berhasil Update!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = buttonModifier,
                    enabled = selectedStudent != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        disabledContainerColor = Color(0xFFEEEEEE)
                    ),
                    shape = buttonShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("UPDATE", color = if (selectedStudent != null) Color.Black else Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                
                // Button HAPUS
                Button(
                    onClick = {
                        if (selectedStudent != null) {
                            students.removeAll { it.id == selectedStudent!!.id }
                            selectedStudent = null
                            nim = ""; nama = ""
                            Toast.makeText(context, "Berhasil Hapus!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = buttonModifier,
                    enabled = selectedStudent != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        disabledContainerColor = Color(0xFFEEEEEE)
                    ),
                    shape = buttonShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("HAPUS", color = if (selectedStudent != null) Color.Black else Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- SEARCH SECTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                value = cari,
                onValueChange = { cari = it },
                placeholder = { Text("Cari nama atau NIM...", fontSize = 14.sp) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { isAscending = !isAscending },
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                shape = RectangleShape,
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text(if (isAscending) "SORT: ASC" else "SORT: DESC", color = Color.Black, fontSize = 11.sp)
            }
        }

        Text(
            "Daftar Mahasiswa (Realm)", 
            fontWeight = FontWeight.Bold, 
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        // --- LIST MAHASISWA (DYNAMIC) ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            filteredStudents.forEach { student ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (selectedStudent?.id == student.id) Color(0xFFBBDEFB) else Color(0xFFE3F2FD), 
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(1.dp, Color(0xFFBBDEFB), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            if (selectedStudent?.id == student.id) {
                                selectedStudent = null
                                nim = ""; nama = ""
                            } else {
                                selectedStudent = student
                                nim = student.nim
                                nama = student.nama
                            }
                        }
                        .padding(16.dp)
                ) {
                    Text(
                        student.nama, 
                        color = Color(0xFF0D47A1), 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 17.sp
                    )
                    Text(
                        "NIM: ${student.nim} | ID: ${student.id}", 
                        color = Color.Gray, 
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- BOTTOM BUTTON ---
        Button(
            onClick = { /* Back to menu logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
            shape = RectangleShape
        ) {
            Text("KEMBALI KE MENU UTAMA", color = Color.Black, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FormmahasiswaTheme {
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("UPH 24SI3", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF212121))
                )
            }
        ) {
            StudentCrudScreen(Modifier.padding(it))
        }
    }
}
