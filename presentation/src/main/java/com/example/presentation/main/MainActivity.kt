package com.example.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.notedetails.NoteDetails
import com.example.presentation.notedetails.NoteDetailsViewModel
import com.example.presentation.notes.NotesScreen
import com.example.presentation.notes.NotesViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
            ) {

                composable(route = Screen.Notes.route) {
                    NotesScreen(
                        viewModel = viewModel(
                            factory = NotesViewModel.Factory()
                        ),
                        onOpenNote = {
                            navController.navigate("${Screen.NoteDetails.route}/$it")
                        },
                    )
                }

                composable(
                    route = Screen.NoteDetails.route + Screen.NoteDetails.ARGS_NOTE,
                    arguments = listOf(
                        navArgument(
                            name = Screen.NoteDetails.NOTE
                        ) {
                            type = NavType.IntType
                        }
                    )
                ) {backStackEntry ->
                    val note = backStackEntry.arguments?.getInt(Screen.NoteDetails.NOTE)
                    note?.let {
                        NoteDetails(
                            viewModel = viewModel(
                                factory = NoteDetailsViewModel.Factory()
                            ),
                            id = note,
                            onBackIconClicked = {
                                navController.navigate("${Screen.Notes.route}")
                            }
                        )
                    }
                }

            }
        }
    }
}