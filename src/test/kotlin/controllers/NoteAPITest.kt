package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested

class NoteAPITest {
    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var testArchived: Note? = null
    private var testArchived2: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI()
    private var emptyNotes: NoteAPI? = NoteAPI()
    private var populatedNoArchivedNotes: NoteAPI? = NoteAPI()
    private var populatedNoActiveNotes: NoteAPI? = NoteAPI()


    @BeforeEach
    fun setup() {
        learnKotlin = Note("Learning Kotlin", 5, "college", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)
        testArchived = Note("Test Archived", 3, "College", true)
        testArchived2 = Note("Test Archived 2", 5, "Work", true)

        //adding all Notes to the populatedNotes
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)
        populatedNotes!!.add(testArchived!!)
        populatedNotes!!.add(testArchived2!!)

        //adding all Notes with isArchived set as false to the populatedNoArchivedNotes
        populatedNoArchivedNotes!!.add(learnKotlin!!)
        populatedNoArchivedNotes!!.add(summerHoliday!!)
        populatedNoArchivedNotes!!.add(codeApp!!)
        populatedNoArchivedNotes!!.add(testApp!!)
        populatedNoArchivedNotes!!.add(swim!!)

        //adding all Notes with isArchived set as true to the populatedNoActiveNotes
        populatedNoActiveNotes!!.add(testArchived!!)
        populatedNoActiveNotes!!.add(testArchived2!!)
    }

    @AfterEach
    fun tearDown() {
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        testArchived = null
        testArchived2 = null
        populatedNotes = null
        emptyNotes = null
        populatedNoActiveNotes = null
        populatedNoArchivedNotes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "college", false)
            assertEquals(7, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(8, populatedNotes!!.numberOfNotes())
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
            assertEquals(7, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
            assertTrue(notesString.contains("test archived"))
            assertTrue(notesString.contains("test archived 2"))
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
            assertEquals(5, populatedNotes!!.numberOfActiveNotes())
            val activeNoteString = populatedNotes!!.listActiveNotes().lowercase()
            assertTrue(activeNoteString.contains("learning kotlin"))
            assertTrue(activeNoteString.contains("code app"))
            assertTrue(activeNoteString.contains("test app"))
            assertTrue(activeNoteString.contains("swim"))
            assertTrue(activeNoteString.contains("summer holiday"))
        }
    }

}
