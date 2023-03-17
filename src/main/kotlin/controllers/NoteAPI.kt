package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)){
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in list.
    fun isValidListIndex (index: Int, list: List<Any>): Boolean{
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String {
        var listOfActiveNotes = ""
        for (i in notes.indices) {
            if(!notes[i].isNoteArchived)
                listOfActiveNotes += "${i}: ${notes[i]} \n"
        }
        return if (listOfActiveNotes.isBlank()) {
            "Currently no active notes"
        } else listOfActiveNotes
    }
    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
        var numberOfActiveNotes = 0
        for (i in notes.indices) {
            if(!notes[i].isNoteArchived)
                numberOfActiveNotes += 1
        }
        return numberOfActiveNotes
    }

    fun listArchivedNotes(): String {
        var listOfArchivedNotes = ""
        for (i in notes.indices) {
            if(notes[i].isNoteArchived)
                listOfArchivedNotes += "${i}: ${notes[i]} \n"
        }
        return if (listOfArchivedNotes.isBlank()) {
            "No archived notes"
        } else listOfArchivedNotes
    }

    fun numberOfArchivedNotes(): Int {
        //helper method to determine how many archived notes there are
        var numberOfArchivedNotes = 0
        for (i in notes.indices) {
            if(notes[i].isNoteArchived)
                numberOfArchivedNotes += 1
        }
        return numberOfArchivedNotes
    }

    fun listNotesBySelectedPriority(priority: Int): String {
        var listOfNotes = ""
        for (i in notes.indices) {
            if(notes[i].notePriority==priority)
                listOfNotes += "${i}: ${notes[i]} \n"
        }
        return if (listOfNotes.isBlank()) {
            "No notes of priority $priority"
        } else listOfNotes
    }

    fun numberOfNotesByPriority(priority: Int): Int {
    //helper method to determine how many notes there are of a specific priority
        var numberOfNotes = 0
        for (i in notes.indices) {
            if(notes[i].notePriority==priority)
                numberOfNotes += 1
        }
        return numberOfNotes
    }

    fun listNotesOfSelectedCategory(category: String) : String {
        var listOfNotes = ""
        for (i in notes.indices) {
            if(notes[i].noteCategory == category)
                listOfNotes += "${i}: ${notes[i]} \n"
        }
        return if (listOfNotes.isBlank()) {
            "No notes of category $category"
        } else listOfNotes
    }

    fun numberOfNotesOfCategory(category: String) : Int{
        var numberOfNotes = 0
        for (i in notes.indices) {
            if(notes[i].noteCategory==category)
                numberOfNotes += 1
        }
        return numberOfNotes
    }

    fun listNotesOfTitle(title: String) : String{
        var listOfNotes = ""
        for (i in notes.indices) {
            if(notes[i].noteTitle.lowercase().contains(title.lowercase()))
                listOfNotes += "${i}: ${notes[i]} \n"
        }
        return if (listOfNotes.isBlank()) {
            "No notes with title $title"
        } else listOfNotes
    }

    fun numberOfNotesOfTitle(title: String): Int{
        var numberOfNotes = 0
        for (i in notes.indices) {
            if(notes[i].noteTitle.lowercase().contains(title.lowercase()))
                numberOfNotes += 1
        }
        return numberOfNotes
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
