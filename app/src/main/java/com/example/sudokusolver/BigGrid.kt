package com.example.sudokusolver

import android.app.Application

class BigGrid2D: Application() {
    var gridVals = Array<IntArray> (9) { IntArray(9) }
    var totalSum = 405
    fun isSolved ():Boolean {
        //checks if gridVals is complete, and if all values are correct
        if (totalSum !=0){
            return false
        }
        print ("current totla sum is " + totalSum)
        return true;
    }
}

class BigGrid3D: Application() {
    var gridVals = Array<Array<MutableList<Int>>> (9) {
        Array<MutableList<Int>>(9){ mutableListOf<Int>(1,2,3,4,5,6,7,8,9) } }
    fun removeVal(pos : Pair <Int, Int>, value : Int ){
        removeValueBox(pos, value)
        removeValueCol(pos, value)
        removeValueRow(pos, value)
    }
    fun findBoxCorner(pos: Pair<Int, Int>): Pair<Int, Int> {
        //finds the box that encompasses the coordinates
        when {
            ((pos.first < 3) && (pos.second < 3)) -> return Pair (0,0)
            ((pos.first < 6) && (pos.second < 3)) -> return Pair (3,0)
            ((pos.first < 9) && (pos.second < 3)) -> return Pair (6,0)
            ((pos.first < 3) && (pos.second < 6)) -> return Pair (0,3)
            ((pos.first < 6) && (pos.second < 6)) -> return Pair (3,3)
            ((pos.first < 9) && (pos.second < 6)) -> return Pair (6,3)
            ((pos.first < 3) && (pos.second < 9)) -> return Pair (0,6)
            ((pos.first < 6) && (pos.second < 9)) -> return Pair (3,6)
            ((pos.first < 9 ) && (pos.second< 9)) -> return Pair (6,6)
            else -> return Pair (0,0)
        }
    }
    fun removeValueBox(pos: Pair <Int, Int>, value: Int){
        //find box top left corner
        var boxCorner :Pair <Int, Int> = findBoxCorner (pos)
        //remove value from box
        for (i in 0..2){
            for (j in 0.. 2){
                gridVals[i+boxCorner.first][j+boxCorner.second].remove(value)
            }
        }
    }
    fun removeValueRow(pos: Pair<Int, Int>, value: Int){
        for (i in 0.. 8){
            gridVals[i][pos.second].remove(value)
        }
    }
    fun removeValueCol(pos:Pair<Int, Int>, value:Int){
        for (j in 0 ..8){
            gridVals[pos.first][j].remove(value)
        }
    }
    fun printGrid(){
        //simply prints out the size of the array in a 2D map
        for (i in 0.. 8){
            for (j in 0..8){
                print(gridVals[i][j].size.toString() + " ")
                print(gridVals[i][j])
                println()
            }
            println()
        }
    }
}