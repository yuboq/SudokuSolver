package com.example.sudokusolver

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the camera button*/
    fun inputCamera(view: View){
        //not implemented
        val text = "In Development!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
    fun inputManual(view: View){
        //starts a new intent for populating sudoku table
        val intent = Intent(this, ManualInputActivity::class.java)
        startActivity(intent)
    }
}
