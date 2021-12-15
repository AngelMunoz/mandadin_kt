package me.tunaxor.apps.mandadin.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import me.tunaxor.apps.mandadin.Note
import me.tunaxor.apps.mandadin.Notesdb
import org.kodein.memory.util.UUID
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.ui.Alignment

/**
 * Not the cleanest impl of a view model but at least it does the job for now
 * I need to brush up a little bit on how to propperly use these
 */
class NotesPageVm : ViewModel() {
    val notes = mutableStateListOf<Note>()

    init {
        fetchNotes()
    }

    fun saveNewNote(content: String) {
        Notesdb.save(Note(UUID.randomUUID(), content))
    }

    fun fetchNotes() {
        notes.clear()
        notes.addAll(Notesdb.find())
    }

    fun deleteNote(uid: UUID) {
        Notesdb.delete(uid)
    }

    fun clearNotes() {
        Notesdb.deleteAll()
    }
}

@Composable
private fun NoteForm(onSubmit: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column {
        Text("Add a new Note")
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                text,
                onValueChange = { text = it },
                placeholder = { Text("Type your note") })
            Button(onClick = {
                onSubmit(text)
                text = ""
            }) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun NoteRow(note: Note, onDelete: (UUID) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = note.content, fontSize = 14.sp, modifier = Modifier.padding(2.dp))
        Button(onClick = { onDelete(note.uid) }) {
            Text(text = "Borrar", fontSize = 12.sp)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                Icons.Rounded.Delete,
                contentDescription = "Delete"
            )
        }
    }
}

/**
 * Not the cleanest execution, but kind of works
 */
@Preview(showBackground = true)
@Composable
fun NotesPage(vm: NotesPageVm = NotesPageVm()) {
    Column(modifier = Modifier.padding(5.dp)) {
        Row {
            Button(onClick = {
                vm.clearNotes()
                vm.fetchNotes()
            }) {
                Text("Delete All Notes")
            }
        }
        NoteForm(onSubmit = {
            vm.saveNewNote(it)
            vm.fetchNotes()
        })
        LazyColumn {
            items(vm.notes) {
                NoteRow(it, onDelete = {
                    vm.deleteNote(it)
                    vm.fetchNotes()
                })
            }
        }
    }
}