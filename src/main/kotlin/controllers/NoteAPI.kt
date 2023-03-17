package controllers

import models.Note

class NoteAPI {
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

    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
        var numberOfActiveNotes = 0
        for (i in notes.indices) {
            if(!notes[i].isNoteArchived)
                numberOfActiveNotes += 1
        }
        return numberOfActiveNotes
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
}
