package ie.tudublin

import java.io.File

fun printBoard(board: Array<IntArray>) {
    for (row in board) {
        println(row.joinToString(" "))
    }
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: sudoku <puzzle-file>")
        return
    }

    try {
        val lines = File(args[0]).readLines()
            .filter { it.isNotBlank() }

        require(lines.size == 9) {
            "Puzzle must contain exactly 9 rows"
        }

        val board = Array(9) { row ->
            val values = lines[row].trim().split(Regex("\\s+"))
                .map { it.toInt() }

            require(values.size == 9) {
                "Each row must contain exactly 9 numbers"
            }

            values.toIntArray()
        }

        println("Input Sudoku board:")
        printBoard(board)

        val sudoku = Sudoku(board)

        if (sudoku.solve()) {
            println("\nSolved Sudoku board:")
            printBoard(sudoku.getBoard())
        } else {
            println("\nCould not find a solution.")
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
