package com.example.presentation.main

sealed class Screen(
    val route: String
) {

    data object Notes: Screen(NOTES_ROUTE)
    data object NoteDetails: Screen(NOTE_DETAILS_ROUTE)  {
        const val ARGS_NOTE = "/{note}"
        const val NOTE = "note"
    }

    companion object {

        private const val NOTES_ROUTE = "notes"
        private const val NOTE_DETAILS_ROUTE = "note_details"


    }


}