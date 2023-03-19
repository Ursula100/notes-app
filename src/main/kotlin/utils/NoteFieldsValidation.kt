package utils


import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

object NoteFieldsValidation {

    @JvmStatic
    fun getPriority(prompt: String?): Int {
        do {
            var priority = readNextInt(prompt)
            if(priority in 1..5) return priority
            else  System.err.println("\tEnter a number between 1 and 5.")
        } while (true)
    }

    @JvmStatic
    fun getStatus(prompt: String?): String{
        do{
             var status = readNextLine(prompt).lowercase()
             when(status){
                  "todo", "on-going", "done" -> return status
                   else -> System.err.println("\t Choose status as 'todo', 'on-going', or 'done'.")
             }
        } while (true)
    }

    @JvmStatic
    fun getCategory(prompt: String?): String{
        do {

            var cat = readNextLine(prompt).lowercase()
            when(cat){
                "home", "college", "Work", "hobby", "family" -> return cat
                else -> System.err.println("\t Choose  from:  \'Home\', \'College\',\'Work\',\'Hobby\',\'Family\'.")
            }

        } while (true)
    }
}