package com.example.data.mappers

import com.example.data.entities.NoteEntity
import com.example.domain.models.Note

fun Note.toEntity() = NoteEntity(id, title, text, date)

fun NoteEntity.toNote() = Note(id, title, text, date)