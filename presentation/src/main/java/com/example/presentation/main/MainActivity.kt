package com.example.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.presentation.notes.NotesScreen
import com.example.presentation.notes.NotesViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NotesViewModel> { NotesViewModel.Factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesScreen(
                viewModel = viewModel
            )
        }
    }
}