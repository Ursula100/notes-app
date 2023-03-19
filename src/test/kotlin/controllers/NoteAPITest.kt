package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class NoteAPITest {
    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var swimP2: Note? = null
    private var petTraining: Note? = null
    private var testArchived: Note? = null
    private var testArchived2: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var populatedNoPriority2Notes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var emptyNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var populatedNoArchivedNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var populatedNoActiveNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))


    @BeforeEach
    fun setup() {
        learnKotlin = Note("Learning Kotlin", 5, "college", "I have to learn kotlin", "todo", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", "Spend a month in Paris", "todo",false)
        codeApp = Note("Code App", 4, "work", "Code an app in Java", "done", false)
        testApp = Note("Test App", 4, "work", "Work on an app using Kotlin", "on-going", false)
        swim = Note("Swim - Pool", 3, "hobby", "Go for a pool party on Saturday to swim", "todo", false)
        testArchived = Note("Test Archived", 3, "college", "Validate modules", "on-going", true)
        testArchived2 = Note("Test Archived 2", 5, "work","Work on database project", "done", true)
        swimP2 = Note("Swim 2", 2, "hobby", "Swimming lessons in two days", "todo", false)
        petTraining = Note( "pet puppy", 2, "home", "Complete 10 lessons with Fifi", "done", false)

        //adding all Notes to the populatedNotes
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)
        populatedNotes!!.add(testArchived!!)
        populatedNotes!!.add(testArchived2!!)
        populatedNotes!!.add(swimP2!!)
        populatedNotes!!.add(petTraining!!)

        //adding all Notes with isArchived set as false to the populatedNoArchivedNotes
        populatedNoArchivedNotes!!.add(learnKotlin!!)
        populatedNoArchivedNotes!!.add(summerHoliday!!)
        populatedNoArchivedNotes!!.add(codeApp!!)
        populatedNoArchivedNotes!!.add(testApp!!)
        populatedNoArchivedNotes!!.add(swim!!)
        populatedNoArchivedNotes!!.add(swimP2!!)
        populatedNoArchivedNotes!!.add(petTraining!!)

        //adding all Notes with isArchived set as true to the populatedNoActiveNotes
        populatedNoActiveNotes!!.add(testArchived!!)
        populatedNoActiveNotes!!.add(testArchived2!!)

        //
        populatedNoPriority2Notes!!.add(learnKotlin!!)
        populatedNoPriority2Notes!!.add(summerHoliday!!)
        populatedNoPriority2Notes!!.add(codeApp!!)
        populatedNoPriority2Notes!!.add(testApp!!)
        populatedNoPriority2Notes!!.add(swim!!)
        populatedNoPriority2Notes!!.add(testArchived!!)
        populatedNoPriority2Notes!!.add(testArchived2!!)

    }

    @AfterEach
    fun tearDown() {
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        petTraining = null
        swimP2 = null
        testArchived = null
        testArchived2 = null
        populatedNotes = null
        emptyNotes = null
        populatedNoActiveNotes = null
        populatedNoArchivedNotes = null
        populatedNoPriority2Notes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "college", "Get a book and study lambdas", "Todo",false)
            assertEquals(9, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(10, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", "Get a book and study lambdas", "Todo",false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {
        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(9, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
            assertTrue(notesString.contains("test archived"))
            assertTrue(notesString.contains("test archived 2"))
            assertTrue(notesString.contains("swim 2"))
            assertTrue(notesString.contains("pet puppy"))
        }
    }

    @Nested
    inner class ListArchivedNotes {
        @Test
        fun `listArchivedNotes returns No Notes Stored when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfArchivedNotes())
            assertTrue(emptyNotes!!.listArchivedNotes().lowercase().contains("no notes stored"))
        }

        @Test
        fun `listArchivedNotes returns No Archived Notes when ArrayList has notes stored but none has isArchived set to true`() {
            assertEquals(0, populatedNoArchivedNotes!!.numberOfArchivedNotes())
            assertTrue(populatedNoArchivedNotes!!.listArchivedNotes().lowercase().contains("no archived notes"))
        }

        @Test
        fun `listArchivedNotes returns Notes that have isArchived set to true when ArrayList has notes with mixed isArchived values stored`() {
            assertEquals(2, populatedNotes!!.numberOfArchivedNotes())
            val archivedNoteString = populatedNotes!!.listArchivedNotes().lowercase()
            assertTrue(archivedNoteString.lowercase().contains("test archived"))
            assertTrue(archivedNoteString.lowercase().contains("test archived 2"))
        }

    }

    @Nested
    inner class ListActiveNotes {
        @Test
        fun `listActiveNotes No notes stored Notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfActiveNotes())
            assertTrue(emptyNotes!!.listActiveNotes().lowercase().contains("no notes stored"))
        }

        @Test
        fun `listActiveNotes returns Currently No Active when ArrayList has Notes stored but none has isArchived set to false`() {
            assertEquals(0, populatedNoActiveNotes!!.numberOfActiveNotes())
            assertTrue(populatedNoActiveNotes!!.listActiveNotes().lowercase().contains("currently no active notes"))
        }

        @Test
        fun `listActiveNotes returns Notes that have isArchived set to false when ArrayList has notes with mixed isArchived values stored`() {
            assertEquals(7, populatedNotes!!.numberOfActiveNotes())
            val activeNoteString = populatedNotes!!.listActiveNotes().lowercase()
            assertTrue(activeNoteString.contains("learning kotlin"))
            assertTrue(activeNoteString.contains("code app"))
            assertTrue(activeNoteString.contains("test app"))
            assertTrue(activeNoteString.contains("swim"))
            assertTrue(activeNoteString.contains("summer holiday"))
            assertTrue(activeNoteString.contains("swim 2"))
            assertTrue(activeNoteString.contains("pet puppy"))
        }
    }

    @Nested
    inner class ListNotesByPriority{
        @Test
        fun `ListNotesBySelectedPriority return No notes of priority $priority when ArrayList has no notes stored`(){
            assertEquals(0, emptyNotes!!.numberOfNotesByPriority(1))
            assertEquals(0, emptyNotes!!.numberOfNotesByPriority(5))
            assertTrue(emptyNotes!!.listNotesBySelectedPriority(1).lowercase().contains("no notes stored"))
            assertTrue(emptyNotes!!.listNotesBySelectedPriority(5).lowercase().contains("no notes stored"))
        }
        @Test
        fun `ListNotesBySelectedPriority return Currently No Notes Of Priority $priority when ArrayList has no notes of the specified priority stored`(){
            assertEquals(0, populatedNoPriority2Notes!!.numberOfNotesByPriority(2))
            assertTrue(populatedNoPriority2Notes!!.listNotesBySelectedPriority(2).lowercase() == "currently no notes of priority 2")
        }

        @Test
        fun `listNotesBySelectedPriority returns notes of selected priority when populated ArrayList contains notes of the specified priority`(){
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(2))
            assertTrue(populatedNotes!!.listNotesBySelectedPriority(2).lowercase().contains("swim 2"))
            assertTrue(populatedNotes!!.listNotesBySelectedPriority(2).lowercase().contains("pet puppy"))
        }

    }

    @Nested
    inner class ListNotesOfCategory {
       @Test
       fun `listNotesOfSelectedCategory returns No Notes Stored $category when ArrayList has no notes stored` (){
           assertEquals(0, emptyNotes!!.numberOfNotesOfCategory("Lessons"))
           assertTrue(emptyNotes!!.listNotesOfSelectedCategory("Lessons").lowercase() == "no notes stored")
       }

        @Test
        fun `listNotesBySelectedCategory returns Currently No Notes Of Category $category when ArrayList has no notes of the specified category stored`(){
            assertEquals(0, populatedNotes!!.numberOfNotesOfCategory("Lab"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("Lab").lowercase() == "currently no notes in category: lab")
        }

        @Test
        fun `listNotesBySelectedCategory returns notes of selected category when populated ArrayList contains notes of the specified category`(){
            assertEquals(2, populatedNotes!!.numberOfNotesOfCategory("hobby"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("hobby").lowercase().contains("swim 2"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("hobby").lowercase().contains("pool"))
        }
    }

    @Nested
    inner class SearchMethods {
        @Test
        fun `searchNoteByTitle returns No Notes Stored when ArrayList has no notes stored` () {
            assertEquals(0, emptyNotes!!.numberOfNotesOfTitle("Pet Puppy"))
            assertTrue(emptyNotes!!.searchNotesByTitle("Pet Puppy").lowercase() == "no notes stored")
        }

        @Test
        fun `searchNoteByTitle returns Currently No Notes With Title $title when ArrayList has no notes of with specified title stored`(){
            assertEquals(0, populatedNotes!!.numberOfNotesOfTitle("Titled Note"))
            assertTrue(populatedNotes!!.searchNotesByTitle("Titled Note").lowercase() == "currently no notes with title: \'titled note\'")
        }

        @Test
        fun `searchNoteByTitle returns notes with specified title when populated ArrayList contains notes with specified title (case match)`(){
            assertEquals(2, populatedNotes!!.numberOfNotesOfTitle("Test Archived"))
            assertTrue(populatedNotes!!.searchNotesByTitle("Test Archived").lowercase().contains("test archived"))
            assertTrue(populatedNotes!!.searchNotesByTitle("Test Archived").lowercase().contains("test archived 2"))
        }

        @Test
        fun `searchNoteByTitle returns notes with specified title when populated ArrayList contains notes with specified title (case does not match)`(){
            assertEquals(2, populatedNotes!!.numberOfNotesOfTitle("Test archIveD"))
            assertTrue(populatedNotes!!.searchNotesByTitle("Test Archived").lowercase().contains("test archived"))
            assertTrue(populatedNotes!!.searchNotesByTitle("Test Archived").lowercase().contains("test archived 2"))
        }
    }

    @Nested
    inner class DeleteNotes {

        @Test
        fun `deleting a Note that does not exist, returns null`() {
            assertNull(emptyNotes!!.deleteNote(0))
            assertNull(populatedNotes!!.deleteNote(-1))
            assertNull(populatedNotes!!.deleteNote(9))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            assertEquals(9, populatedNotes!!.numberOfNotes())
            assertEquals(swim, populatedNotes!!.deleteNote(4))
            assertEquals(8, populatedNotes!!.numberOfNotes())
            assertEquals(learnKotlin, populatedNotes!!.deleteNote(0))
            assertEquals(7, populatedNotes!!.numberOfNotes())
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a note that does not exist returns false`(){
            assertFalse(populatedNotes!!.updateNote(9, Note("Updating Note", 2, "Work", "Note does not exist", "On-going", false)))
            assertFalse(populatedNotes!!.updateNote(-1, Note("Updating Note", 2, "Work", "Note does not exist", "On-going",false)))
            assertFalse(emptyNotes!!.updateNote(0, Note("Updating Note", 2, "Work", "Note does not exist", "On-going",false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(swim, populatedNotes!!.findNote(4))
            assertEquals("Swim - Pool", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(3, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("hobby", populatedNotes!!.findNote(4)!!.noteCategory)
            assertEquals("Go for a pool party on Saturday to swim", populatedNotes!!.findNote(4)!!.noteContent)
            assertEquals("todo", populatedNotes!!.findNote(4)!!.noteStatus)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedNotes!!.updateNote(4, Note("Updating this Note", 2, "college", "Note exists", "on-going",false)))
            assertEquals("Updating this Note", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(2, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("college", populatedNotes!!.findNote(4)!!.noteCategory)
            assertEquals("Note exists", populatedNotes!!.findNote(4)!!.noteContent)
            assertEquals("on-going", populatedNotes!!.findNote(4)!!.noteStatus)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Nested
        inner class XMLSerialization {

            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                // Saving an empty notes.XML file.
                val storingNotes = NoteAPI(XMLSerializer(File("notes.xml")))
                storingNotes.store()

                //Loading the empty notes.xml file into a new object
                val loadedNotes = NoteAPI(XMLSerializer(File("notes.xml")))
                loadedNotes.load()

                //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
                assertEquals(0, storingNotes.numberOfNotes())
                assertEquals(0, loadedNotes.numberOfNotes())
                assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                // Storing 3 notes to the notes.XML file.
                val storingNotes = NoteAPI(XMLSerializer(File("notes.xml")))
                storingNotes.add(testApp!!)
                storingNotes.add(swim!!)
                storingNotes.add(summerHoliday!!)
                storingNotes.store()

                //Loading notes.xml into a different collection
                val loadedNotes = NoteAPI(XMLSerializer(File("notes.xml")))
                loadedNotes.load()

                //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
                assertEquals(3, storingNotes.numberOfNotes())
                assertEquals(3, loadedNotes.numberOfNotes())
                assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
                assertEquals(storingNotes.findNote(0), loadedNotes.findNote(0))
                assertEquals(storingNotes.findNote(1), loadedNotes.findNote(1))
                assertEquals(storingNotes.findNote(2), loadedNotes.findNote(2))
            }
        }

        @Nested
        inner class JSONSerialization{
            @Test
            fun `saving and loading an empty collection in JSON doesn't crash app`() {
                // Saving an empty notes.json file.
                val storingNotes = NoteAPI(JSONSerializer(File("notes.json")))
                storingNotes.store()

                //Loading the empty notes.json file into a new object
                val loadedNotes = NoteAPI(JSONSerializer(File("notes.json")))
                loadedNotes.load()

                //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
                assertEquals(0, storingNotes.numberOfNotes())
                assertEquals(0, loadedNotes.numberOfNotes())
                assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
            }

            @Test
            fun `saving and loading an loaded collection in JSON doesn't loose data`() {
                // Storing 3 notes to the notes.json file.
                val storingNotes = NoteAPI(JSONSerializer(File("notes.json")))
                storingNotes.add(testApp!!)
                storingNotes.add(swim!!)
                storingNotes.add(summerHoliday!!)
                storingNotes.store()

                //Loading notes.json into a different collection
                val loadedNotes = NoteAPI(JSONSerializer(File("notes.json")))
                loadedNotes.load()

                //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
                assertEquals(3, storingNotes.numberOfNotes())
                assertEquals(3, loadedNotes.numberOfNotes())
                assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
                assertEquals(storingNotes.findNote(0), loadedNotes.findNote(0))
                assertEquals(storingNotes.findNote(1), loadedNotes.findNote(1))
                assertEquals(storingNotes.findNote(2), loadedNotes.findNote(2))
            }

        }
    }

    @Nested
    inner class ArchiveNotes {
        @Test
        fun `archiveNote returns false when note does not exist`(){
            assertFalse(populatedNotes!!.archiveNote(9))
            assertFalse(populatedNotes!!.archiveNote(-1))
            assertFalse(emptyNotes!!.archiveNote(0))
        }

        @Test
        fun `archiveNote returns false when note is already archived`(){
            assertTrue(populatedNotes!!.findNote(6)!!.isNoteArchived)
            assertFalse(populatedNotes!!.archiveNote(6))
        }

        @Test
        fun `archiveNote returns true and archives active note`() {
            assertFalse(populatedNotes!!.findNote(1)!!.isNoteArchived)
            assertTrue(populatedNotes!!.archiveNote(1))
            assertTrue(populatedNotes!!.findNote(1)!!.isNoteArchived)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfNotesCalculatedCorrectly() {
            assertEquals(9, populatedNotes!!.numberOfNotes())
            assertEquals(0, emptyNotes!!.numberOfNotes())
        }

        @Test
        fun numberOfArchivedNotesCalculatedCorrectly() {
            assertEquals(2, populatedNotes!!.numberOfArchivedNotes())
            assertEquals(0, emptyNotes!!.numberOfArchivedNotes())
        }

        @Test
        fun numberOfActiveNotesCalculatedCorrectly() {
            assertEquals(7, populatedNotes!!.numberOfActiveNotes())
            assertEquals(0, emptyNotes!!.numberOfActiveNotes())
        }

        @Test
        fun numberOfNotesByPriorityCalculatedCorrectly() {
            assertEquals(1, populatedNotes!!.numberOfNotesByPriority(1))
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(2))
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(3))
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(4))
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(5))
            assertEquals(0, emptyNotes!!.numberOfNotesByPriority(1))
        }
    }


}
