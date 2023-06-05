package connectfour

import kotlin.system.exitProcess

var rows = 6
var columns = 7
var board = mutableListOf<MutableList<Char>>()
var firstP = ""
var firstScore = 0
var secondScore = 0
var secondP = ""
var isGameFin = false
var isFirst = true
var nOfGames = ""

fun main() {
    println("Connect Four")
    println("First player's name:")
    firstP = readln()
    println("Second player's name:")
    secondP = readln()
    setBoardDim()
    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")
        try {
            nOfGames = readln()
            if (nOfGames == "" || nOfGames == "1") {
                nOfGames = "1"
                break
            }
            if (nOfGames.toInt() < 1) {
                println("Invalid input")
            } else {
                break
            }
        } catch (e: Exception) {
            println("Invalid input")
        }
    }
    println("$firstP VS $secondP")
    println("$rows X $columns board")
    if (nOfGames.toInt() > 1) {
        println("Total ${nOfGames.toInt()} games")
    }


    var gameNo = 1
    repeat(nOfGames.toInt()) {
        isGameFin = false
        if (nOfGames.toInt() == 1) {
            println("Single game")
        } else {
            println("Game #$gameNo")
        }
        board = MutableList(rows) { MutableList(columns) { ' ' } }
        printBoard()
        play()
        if (nOfGames.toInt() != 1) {
            println("Score")
            println("$firstP: $firstScore $secondP: $secondScore")
        }
        gameNo++
    }
    println("Game over!")
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


fun printBoard() {
    for (i in 0 until columns) {
        print(" ${i + 1}")
    }
    println()
    for (i in 0 until rows) {
        print("|")
        for (y in 0 until columns) {
            print("${board[i][y]}|")
        }
        println()
    }
    print("=")
    for (i in 0 until columns - 1) {
        print("==")
    }
    print("==")
    println()
}

fun play() {

    var disk = ' '
    var column = 0
    while (true && !isGameFin) {
        if (isFirst) {
            println("$firstP's turn:")
            disk = 'o'
        } else {
            println("$secondP's turn:")
            disk = '*'
        }
        try {
            val userInp = readln()
            if (userInp == "end") {
                println("Game over!")
                exitProcess(0)
            }
            column = userInp.toInt()
            if (column > columns || column < 1) {
                println("The column number is out of range (1 - $columns)")
                continue
            } else if (board[0][column - 1] != ' ') {
                println("Column $column is full")
                continue
            } else {
                for (i in rows - 1 downTo 0) {
                    if (board[i][column - 1] == ' ') {
                        board[i][column - 1] = disk
                        isFirst = !isFirst
                        printBoard()
                        checkCondition()
                        break
                    }
                }
            }
        } catch (e: Exception) {
            println("Incorrect column number")
            continue
        }
    }
}

fun checkCondition() {
    val lines = mutableListOf<String>()
    var line = ""
    if (board.any { it.contains(' ') }) {
        l00ps@ for (i in 0 until rows) {
            for (y in 0 until columns) {
                try {
                    line = "${board[i - 3][y]}${board[i - 2][y]}${board[i - 1][y]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i + 3][y]}${board[i + 2][y]}${board[i + 1][y]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i][y - 3]}${board[i][y - 2]}${board[i][y - 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i][y + 3]}${board[i][y + 2]}${board[i][y + 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i + 3][y + 3]}${board[i + 2][y + 2]}${board[i + 1][y + 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i - 3][y - 3]}${board[i - 2][y - 2]}${board[i - 1][y - 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i + 3][y - 3]}${board[i + 2][y - 2]}${board[i + 1][y - 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                try {
                    line = "${board[i - 3][y + 3]}${board[i - 2][y + 2]}${board[i - 1][y + 1]}${board[i][y]}"
                    lines.add(line)
                } catch (_: Exception) {
                }
                if (lines.contains("oooo")) {
                    println("Player $firstP won")
                    isGameFin = true
                    firstScore += 2
                    l00ps@ return
                } else if (lines.contains("****")) {
                    println("Player $secondP won")
                    isGameFin = true
                    secondScore += 2
                    l00ps@ return
                }
            }
        }
    } else {
        println("It is a draw")
        isGameFin = true
        firstScore += 1
        secondScore += 1
    }
}