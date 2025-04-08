package com.example.horsegame


import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rlScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        initScreenGame()

    }
    private fun initScreenGame(){
        setSizeBoard()
        hide_message()
    }

//funcion para dimensionar el tablero a diferentes dispositivos
    private fun setSizeBoard(){
        var iv: ImageView

        // aca capturamos el tama;o de la pantalla
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x

        //aca pasamos la medida a dp
        var width_dp = ( width / getResources().getDisplayMetrics().density)

        var lateralMarginsDP = 0
        val width_cell = (width_dp - lateralMarginsDP)/8
        val height_cell = width_cell

        for (i in 0..7){
            for (j in 0..7){
                iv = findViewById(resources.getIdentifier("c$i$j","id", packageName))

            }
        }
    }

    //funcion para poner invisible el mensaje donde val nivel y vidas y boton de siguiente nivel
    private  fun hide_message(){
        var lyMessage = findViewById<LinearLayout>(R.id.lyMessage)
        lyMessage.visibility = View.INVISIBLE
    }
}