package ie.tudublin

class Sudoku(input: Array<IntArray>) {

    private val board = Array(9) { row -> input[row].clone() }

    private var iterations = 0
    private val maxIterations = 2_000_000

    init {
        require(input.size == 9 && input.all { it.size == 9 }) {
            "Sudoku board must be 9x9"
        }
        require(input.all { row -> row.all { it in 0..9 } }) {
            "Sudoku values must be between 0 and 9"
        }
    }

    fun getBoard(): Array<IntArray> {
        return Array(9) { row -> board[row].clone() }
    }

    private fun isValid(row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until 9) {
            if (board[row][i] == num) return false
            if (board[i][col] == num) return false
        }

        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3

        for (r in boxRow until boxRow + 3) {
            for (c in boxCol until boxCol + 3) {
                if (board[r][c] == num) return false
            }
        }

        return true
    }

    private fun validInitialBoard(): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val num = board[row][col]

                if (num != 0) {
                    board[row][col] = 0
                    val valid = isValid(row, col, num)
                    board[row][col] = num

                    if (!valid) return false
                }
            }
        }

        return true
    }

    fun solve(): Boolean {
        iterations = 0

        if (!validInitialBoard()) return false

        return backtrack()
    }

    private fun backtrack(): Boolean {
        iterations++

        if (iterations > maxIterations) return false

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board[row][col] == 0) {
                    for (num in 1..9) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num

                            if (backtrack()) return true

                            board[row][col] = 0

                            if (iterations > maxIterations) return false
                        }
                    }

                    return false
                }
            }
        }

        return true
    }
}
