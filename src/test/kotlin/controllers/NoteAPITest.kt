package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
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
        learnKotlin = Note("Learning Kotlin", 5, "college", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)
        testArchived = Note("Test Archived", 3, "College", true)
        testArchived2 = Note("Test Archived 2", 5, "Work", true)
        swimP2 = Note("Swim 2", 2, "Lessons", false)
        petTraining = Note( "pet puppy", 2, "Lessons", false)

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
            val newNote = Note("Study Lambdas", 1, "college", false)
            assertEquals(9, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(10, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
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
        fun `listArchivedNotes returns No Archived Notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfArchivedNotes())
            assertTrue(emptyNotes!!.listArchivedNotes().lowercase().contains("no archived notes"))
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
        fun `listActiveNotes returns Currently No Active Notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfActiveNotes())
            assertTrue(emptyNotes!!.listActiveNotes().lowercase().contains("currently no active notes"))
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
            assertTrue(emptyNotes!!.listNotesBySelectedPriority(1).lowercase().contains("no notes of priority"))
            assertTrue(emptyNotes!!.listNotesBySelectedPriority(5).lowercase().contains("no notes of priority"))
        }
        @Test
        fun `ListNotesBySelectedPriority return No Notes Of Priority $priority when ArrayList has no notes of the specified priority stored`(){
            assertEquals(0, populatedNoPriority2Notes!!.numberOfNotesByPriority(2))
            assertTrue(populatedNoPriority2Notes!!.listNotesBySelectedPriority(2).lowercase().contains("no notes of priority"))
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
       fun `listNotesOfSelectedCategory returns No Note of Category $category when ArrayList has no notes stored` (){
           assertEquals(0, emptyNotes!!.numberOfNotesOfCategory("Lessons"))
           assertTrue(emptyNotes!!.listNotesOfSelectedCategory("Lessons").lowercase().contains("no notes of category"))
       }

        @Test
        fun `listNotesBySelectedCategory return No Notes Of Category $category when ArrayList has no notes of the specified category stored`(){
            assertEquals(0, populatedNotes!!.numberOfNotesOfCategory("Lab"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("Lab").lowercase().contains("no notes of category"))
        }

        @Test
        fun `listNotesBySelectedCategory returns notes of selected category when populated ArrayList contains notes of the specified category`(){
            assertEquals(2, populatedNotes!!.numberOfNotesOfCategory("Lessons"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("Lessons").lowercase().contains("swim 2"))
            assertTrue(populatedNotes!!.listNotesOfSelectedCategory("Lessons").lowercase().contains("pet puppy"))
        }
    }

    @Nested
    inner class ListNotesOfTitle {
        @Test
        fun `listNotesOfTitle returns No Notes With Title $title when ArrayList has no notes stored` () {
            assertEquals(0, emptyNotes!!.numberOfNotesOfTitle("Pet Puppy"))
            assertTrue(emptyNotes!!.listNotesOfTitle("Pet Puppy").lowercase().contains("no notes with title"))
        }

        @Test
        fun `listNotesOfTitle returns No Notes With Title $title when ArrayList has no notes of with specified title stored`(){
            assertEquals(0, populatedNotes!!.numberOfNotesOfTitle("Titled Note"))
            assertTrue(populatedNotes!!.listNotesOfTitle("Titled Note").lowercase().contains("no notes with title"))
        }

        @Test
        fun `listNotesOfTitle returns notes with specified title when populated ArrayList contains notes with specified title`(){
            assertEquals(2, populatedNotes!!.numberOfNotesOfTitle("Test Archived"))
            assertTrue(populatedNotes!!.listNotesOfTitle("Test Archived").lowercase().contains("test archived"))
            assertTrue(populatedNotes!!.listNotesOfTitle("Test Archived").lowercase().contains("test archived 2"))
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
            assertFalse(populatedNotes!!.updateNote(9, Note("Updating Note", 2, "Work", false)))
            assertFalse(populatedNotes!!.updateNote(-1, Note("Updating Note", 2, "Work", false)))
            assertFalse(emptyNotes!!.updateNote(0, Note("Updating Note", 2, "Work", false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(swim, populatedNotes!!.findNote(4))
            assertEquals("Swim - Pool", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(3, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("Hobby", populatedNotes!!.findNote(4)!!.noteCategory)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedNotes!!.updateNote(4, Note("Updating Note", 2, "College", false)))
            assertEquals("Updating Note", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(2, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("College", populatedNotes!!.findNote(4)!!.noteCategory)
        }
    }

    @Nested
    inner class PersistenceTests {

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


}
