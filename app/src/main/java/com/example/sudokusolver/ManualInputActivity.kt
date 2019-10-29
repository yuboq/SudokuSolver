package com.example.sudokusolver

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

fun isSolved (bigGrid2d: BigGrid2D): Boolean {
    for (i in 0..8){
        for (j in 0..8){
            if (bigGrid2d.gridVals[i][j] ==0){
                return false
            }
        }
    }
    return true
}

class ManualInputActivity : AppCompatActivity() {
    var selectedTVCell : TextView ?= null
    var bigGrid = BigGrid2D()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)
        resetBigGrid()
        val btnClearAll = findViewById(R.id.button_numpadC) as Button
        btnClearAll.setOnLongClickListener{
            resetBigGrid()
            return@setOnLongClickListener true
        }

    }


    fun demoSudoku (view: View) {
        //populates the puzzle with a demo the sudoku
        val row1:IntArray = intArrayOf(0,7,0,0,0,2,0,0,0)
        val row2:IntArray = intArrayOf(3,0,0,0,0,7,0,6,0)
        val row3:IntArray = intArrayOf(0,6,0,3,5,0,9,8,0)
        val row4:IntArray = intArrayOf(8,0,0,7,0,6,5,0,1)
        val row5:IntArray = intArrayOf(7,5,0,0,4,0,0,3,8)
        val row6:IntArray = intArrayOf(9,0,2,5,0,3,0,0,6)
        val row7:IntArray = intArrayOf(0,2,9,0,3,5,0,7,0)
        val row8:IntArray = intArrayOf(0,8,0,4,0,0,0,0,9)
        val row9:IntArray = intArrayOf(0,0,0,2,0,0,0,1,0)

        bigGrid.gridVals = arrayOf (row1,row2,row3,row4,row5,row6,row7,row8,row9)
        updateGridVals()

    }

    fun demoSudoku2 (view: View) {
        //populates the puzzle with a demo the sudoku
        val row1:IntArray = intArrayOf(0,0,7,8,2,5,0,0,0)
        val row2:IntArray = intArrayOf(8,0,0,0,0,0,0,0,0)
        val row3:IntArray = intArrayOf(0,1,4,3,0,0,0,0,8)
        val row4:IntArray = intArrayOf(4,0,1,0,7,0,0,0,3)
        val row5:IntArray = intArrayOf(0,0,0,0,5,0,0,0,0)
        val row6:IntArray = intArrayOf(5,0,0,0,7,0,1,0,6)
        val row7:IntArray = intArrayOf(3,0,0,0,0,2,9,7,0)
        val row8:IntArray = intArrayOf(0,0,0,0,0,0,0,0,5)
        val row9:IntArray = intArrayOf(0,0,0,9,6,4,3,0,0)

        bigGrid.gridVals = arrayOf (row1,row2,row3,row4,row5,row6,row7,row8,row9)
        updateGridVals()

    }

    fun updateGridVals (){

        //updates the onscreen textviews
        for (i in 0..8) {
            for (j in 0..8) {
                //create a string for accessing the textView ID
                val currTvId = "TV" + i.toString() + j.toString()
                val tvId = resources.getIdentifier(currTvId, "id", packageName)
                val tvObj = findViewById(tvId) as TextView
                val valToDisplay :String
                if (bigGrid.gridVals[i][j] == 0){
                    valToDisplay = " "
                }else{
                    valToDisplay = bigGrid.gridVals[i][j].toString()
                }

                tvObj.text = valToDisplay
            }
        }
    }
    fun resetCellFormat (){

        //updates the onscreen textviews
        for (i in 0..8) {
            for (j in 0..8) {
                //create a string for accessing the textView ID
                val currTvId = "TV" + i.toString() + j.toString()
                val tvId = resources.getIdentifier(currTvId, "id", packageName)
                val tvObj = findViewById(tvId) as TextView
                selectedTVCell = null
                /*determine which cell row border layout to use, we'll use bits to keep track
                position 0= regular cell
                position 1 = left thick
                position 2 = right border
                position 3 = top thick
                position 4 = bot border

                Defaults to regular cell. Top thick and bot border are mutually exclusive,
                similarily, left thick and right border are mutually exclusive
                 */

                var borderToUse = 0
                when (j) {
                    3,6 -> borderToUse +=0b00001 //add left
                    8 -> borderToUse +=0b00010 //add right
                }
                when (i) {
                    3,6 -> borderToUse +=0b00100//add top
                    8 -> borderToUse +=0b01000 //add bot
                }

                //apply the drawable border
                when (borderToUse) {
                    //regular border
                    CellBorderMux.REGULAR.muxVal -> tvObj.background =
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_regular)
                    CellBorderMux.TOP.muxVal -> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_top)
                    CellBorderMux.BOT.muxVal -> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_bot)
                    CellBorderMux.LEFT.muxVal -> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_left)
                    CellBorderMux.LEFTBOT.muxVal ->tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_leftbot)
                    CellBorderMux.LEFTTOP.muxVal -> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_lefttop)
                    CellBorderMux.RIGHT.muxVal -> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_right)
                    CellBorderMux.RIGHTBOT.muxVal-> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_rightbot)
                    CellBorderMux.RIGHTTOP.muxVal-> tvObj.background=
                        ActivityCompat.getDrawable(this, R.drawable.table_cell_righttop)
                }
            }
        }
    }
    fun resetBigGrid(){
        bigGrid = BigGrid2D()
        resetCellFormat()
        updateGridVals()
    }
    fun solve(view: View){
        /** There's a few steps to solving the puzzle:
         * 1. Create a new 9x9 grid for mapping all possible values from row search
         * 2. Create a new 9x9 grid for mapping all possible values from column search
         * 3. Create a new 9x9 grid for mapping all possible values from box search
         * 4. update original gridVals with "solved" values where all 3 grids generate a
         *      single mutually inclusive value
         * 5. Remove that value from all 3 grids
         * 6. Repeat step 4,5 until no updates are made. This means sudoku has unique (solved)
         *      solution, or the puzzle has multiple solutions
         *
         * Alternative method (I'm probably do this one)
         * 1. Create a 9x9x9 grid of arrays in 0..9
         * 2. Each value present in original grid will have an empty array, and the values
         *      that aren't possible are removed from each array
         * 3. Do this for row, column and box search
         * 4. Looped step:
         *    a) Find first single value
         *    b) update row, column and box numbers by removing that single value (if possible)
         *    c) repeat until no updates occur
         */

        var bigGrid3D = BigGrid3D()
        initialRemove (bigGrid3D)

        println("helloworld asdfijaosdfji")
        var isFinished = false//false means finished solving
        while (!isFinished){
            isFinished= true
            //Search for single elements to update bigGrid.gridvals
            for (i in 0.. 8){
                for (j in 0..8){
                    val possibleVals : MutableList<Int> = bigGrid3D.gridVals[i][j]
                    if (possibleVals.size ==1){
                        //there's still more numbers to solve
                        bigGrid.gridVals[i][j] = possibleVals.first()
                        bigGrid.totalSum -= possibleVals.first()
                        bigGrid3D.removeVal (Pair(i, j), possibleVals.first())
                        updateGridVals()
                        isFinished = false
                        break
                    }
                }
                if (!isFinished){
                    break
                }
            }
        }
        if (bigGrid.isSolved()){
            return
        }else{
            bruteForceSearch (bigGrid3D)
        }
    }


    fun bruteForceSearch(bigGrid3D: BigGrid3D){
        //guarantees a solution by using depth first traversal along the tree
        //TODO complete this method
    }

    fun initialRemove( bigGrid3D: BigGrid3D){
        //Remove the entire potential list in bigGrid3D if number is already given
        for (i in 0..8){
            for (j in 0..8){
                val hintVal= bigGrid.gridVals[i][j]
                bigGrid.totalSum -= hintVal
                if (hintVal != 0){
                    //remove the list
                    bigGrid3D.gridVals[i][j].clear()
                    bigGrid3D.removeVal(Pair(i,j), hintVal)
                }
            }
        }
        //bigGrid3D.printGrid()
    }
    fun buttonCellSelect(view: View){
        //highlights the current button for text change
        resetCellFormat()
        selectedTVCell = findViewById<TextView>(view.id)
        selectedTVCell?.setBackgroundColor(Color.YELLOW)
    }

    fun updateValue (view: View){
        //updates a highlighted value in gridVal when numpad button is pressed
        //find value to update

        //handle the case when "C" clear is pressed
        val pressedVal : Int
        if (view.id == R.id.button_numpadC){
            pressedVal = 0
        }else{
            pressedVal = findViewById<Button>(view.id).text.toString().toInt()
        }

        //parse the selectedTVCell id to a position
        val tvCellId = view.context.resources.getResourceEntryName(selectedTVCell?.id!!)

        //because we know there's only 2 numbers, we can just hard code this
        val tvCellIdStringArray = tvCellId.split("/*".toRegex())
        val row :Int = tvCellIdStringArray[3].toInt()
        val col: Int = tvCellIdStringArray[4].toInt()

        bigGrid.gridVals[row][col] = pressedVal

        updateGridVals()
    }

}
