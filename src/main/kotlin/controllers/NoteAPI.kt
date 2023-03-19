package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes)


    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes))
                   notes[index]
               else null
    }

    //utility method to determine if an index is valid in list.
    fun isValidListIndex (index: Int, list: List<Any>): Boolean{
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String =
            if  (notes.isEmpty()) "No notes stored"
            else formatListString(notes.filter { note -> !note.isNoteArchived})
                      .ifBlank {"Currently no active notes"}

    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
       return notes.stream()
                   .filter{note: Note -> !note.isNoteArchived}
                   .count()
                   .toInt()
    }

    fun listArchivedNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived})
            .ifBlank {"Currently no archived notes"}

    fun numberOfArchivedNotes(): Int {
        //helper method to determine how many archived notes there are
     return notes.stream()
                 .filter{note: Note -> note.isNoteArchived}
                 .count()
                 .toInt()
    }

    fun listNotesBySelectedPriority(priority: Int): String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes.filter { note: Note -> note.notePriority == priority })
            .ifBlank {"Currently no notes of priority $priority"}

    fun numberOfNotesByPriority(priority: Int): Int {
    //helper method to determine how many notes there are of a specific priority
          return notes.stream()
                      .filter{note: Note -> note.notePriority == priority}
                      .count()
                      .toInt()
    }

    fun listNotesOfSelectedCategory(category: String) : String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(notes.filter { note: Note -> note.noteCategory.equals(category,true) })
            .ifBlank {"Currently no notes in category: $category"}


    fun numberOfNotesOfCategory(category: String) : Int{
        return notes.stream()
            .filter{note: Note -> note.noteCategory == category}
            .count()
            .toInt()
    }

    fun searchNotesByTitle(title: String) : String =
        if  (notes.isEmpty()) "No notes stored"
        else formatListString(
            notes.filter { note: Note -> note.noteTitle.contains(title,true) })
            .ifBlank {"Currently no notes with title: \'$title\'"}

    fun numberOfNotesOfTitle(title: String): Int{
        return notes.stream()
            .filter{note: Note -> note.noteTitle.contains(title, true)}
            .count()
            .toInt()
    }

    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    private fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString() }

    fun archiveNote(indexToArchive: Int): Boolean {
        //find the note object using the index number
        val noteToArchive = findNote(indexToArchive)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((noteToArchive != null) && (!noteToArchive.isNoteArchived)) {
            noteToArchive.isNoteArchived = true
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes)
    }

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }

}
