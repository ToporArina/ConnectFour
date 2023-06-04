package connectfour

var rows = 6
var columns = 7


fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstP = readln()
    println("Second player's name:")
    val secondP = readln()
    setBoardDim()
    println("$firstP VS $secondP")
    println("$rows X $columns board")

}

fun setBoardDim() {
    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val readInp = readln()
        if (readInp == "") {
            break
        }
        val userInp = readInp.lowercase().replace(" ", "").split("\\s?".toRegex()).joinToString("")
        try {
            if (userInp.split("x")[1].toInt() !in 5..9) {
                println("Board columns should be from 5 to 9")
                continue
            } else if (userInp.split("x")[0].toInt() !in 5..9) {
                println("Board rows should be from 5 to 9")
                continue
            } else {
                rows = userInp[0].digitToInt()
                columns = userInp[2].digitToInt()
                break
            }
        } catch (e: Exception) {
            println("Invalid input")
        }
    }
}