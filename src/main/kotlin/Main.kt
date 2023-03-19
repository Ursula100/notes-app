import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import utils.NoteFieldsValidation.getCategory
import utils.NoteFieldsValidation.getPriority
import utils.NoteFieldsValidation.getStatus

private val logger = KotlinLogging.logger {}

//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))


fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List notes                |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > |   5) Archive a note            |
         > |   7) Search for a note         |
         > |   8) Unarchive a note
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun subMenu(): Int{
    return readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE SUBMENU                   |
         > |   1) List all notes            |
         > |   2) List active notes         |
         > |   3) List archived notes       |
         > |   4) List notes by priority    |
         > |   5) List notes by category    |
         > |   6) List notes by status      |
         > ----------------------------------
         > |   0) Back to main menu         |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1  -> addNote()
            2  -> runSubMenu()
            3  -> updateNote()
            4  -> deleteNote()
            5  -> archiveNote()
            6  -> searchNoteByTitle()
            7  -> unarchiveNote()
            20 -> save()
            21 -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: $option \n")
        }
    } while (true)
}

fun runSubMenu(){
    do {
        when (val option = subMenu()) {
            1  -> listAllNotes()
            2  -> listActiveNotes()
            3  -> listArchivedNotes()
            4  -> listNotesByPriority()
            5  -> listNotesByCategory()
            6  -> listNotesByStatus()
            0  -> runMenu()
            else -> println("Invalid option entered: $option \n")
        }
    } while (true)
}

fun addNote(){
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = getPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = getCategory("Enter a category for the note as 'Home', 'College','Work','Hobby', or 'Family': ")
    val noteContent = readNextLine("Enter note content: ")
    val noteStatus: String = getStatus("Choose status as 'todo', 'on-going', or 'done':")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, noteContent, noteStatus, false))
    if (isAdded) println("Added Successfully\n")
    else println("Add Failed\n")
}
fun listAllNotes(){
    //logger.info { "listNotes() function invoked" }
    if(noteAPI.numberOfNotes()>0)
        print("There are : ${noteAPI.numberOfNotes()} notes stored \n")
    println(noteAPI.listAllNotes())
}

fun listActiveNotes(){
    if(noteAPI.numberOfActiveNotes()>0)
        print("There are ${noteAPI.numberOfActiveNotes()} active notes \n")
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes(){
    if(noteAPI.numberOfArchivedNotes()>0)
        print("There are ${noteAPI.numberOfArchivedNotes()} archived notes \n")
    println(noteAPI.listArchivedNotes())
}

fun listNotesByPriority(){
    val selectedPriority = getPriority("Enter priority: ")
    if(noteAPI.numberOfNotesByPriority(selectedPriority)>0)
        print("There are ${noteAPI.numberOfNotesByPriority(selectedPriority)} notes of priority $selectedPriority \n")
    println(noteAPI.listNotesBySelectedPriority(selectedPriority))
}

fun listNotesByCategory(){
    val category = getCategory("Enter category: ")
    if(noteAPI.numberOfNotesOfCategory(category)>0)
        print("There are ${noteAPI.numberOfNotesOfCategory(category)} notes of category $category \n")
    println(noteAPI.listNotesOfSelectedCategory(category))
}

fun listNotesByStatus(){
    val status = getCategory("Enter status from 'todo', 'on-going', or 'done'.")
    if(noteAPI.numberOfNotesOfStatus(status)>0)
        print("There are ${noteAPI.numberOfNotesOfStatus(status)} '$status' notes. \n")
    println(noteAPI.listNotesOfSelectedStatus(status))
}

fun updateNote(){
    //logger.info { "updateNote() function invoked" }
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes is not empty
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = getPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = getCategory("Enter a category for the note as 'Home', 'College','Work','Hobby', or 'Family': ")
            val noteContent = readNextLine("Enter note content: ")
            val noteStatus = getStatus("Enter a priority (Todo, On-going, Done): ")
            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, noteContent, noteStatus, false)))
                println("Update Successful")
            else println("Update Failed")
        }
        else println("There are no notes for this index number\n")
    }
}

fun deleteNote(){
    //logger.info { "addNote() function invoked" }
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes is not empty
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) println("Delete Successful! Deleted note: ${noteToDelete.noteTitle} \n")
        else println("Delete NOT Successful \n")
    }
}

fun archiveNote(){
    println(noteAPI.listActiveNotes())
    if(noteAPI.numberOfNotes() > 0){
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        if(noteAPI.findNote(indexToArchive)==null)
            println("Archive Unsuccessful - There is no note at index: $indexToArchive \n")
        else {
            if(!noteAPI.findNote(indexToArchive)!!.isNoteArchived) {
                if (noteAPI.archiveNote(indexToArchive))
                    println("Archive Successful! Archived note at index: $indexToArchive")
            }
            else println("Archive Unsuccessful - note is already archived \n")
        }
    }
    else println("There are no notes in store \n")
}

fun unarchiveNote(){
    println(noteAPI.listActiveNotes())
    if(noteAPI.numberOfNotes() > 0){
        val indexToArchive = readNextInt("Enter the index of the note to unarchive: ")
        if(noteAPI.findNote(indexToArchive)==null)
            println("Unarchive Unsuccessful - There is no note at index: $indexToArchive \n")
        else {
            if(noteAPI.findNote(indexToArchive)!!.isNoteArchived) {
                if (noteAPI.unarchiveNote(indexToArchive))
                    println("Unarchive Successful! unarchived note at index: $indexToArchive")
            }
            else println("Unarchive Unsuccessful - note is not archived \n")
        }
    }
    else println("There are no notes in store \n")
}

fun searchNoteByTitle(){
    val title = readNextLine("Enter title: ")
    if(noteAPI.numberOfNotesOfTitle(title)>0)
        print("There are ${noteAPI.numberOfNotesOfTitle(title)} notes with title: \'$title\' \n")
    println(noteAPI.searchNotesByTitle(title))
}


fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("\n Exiting...bye")
    exit(0)
}

