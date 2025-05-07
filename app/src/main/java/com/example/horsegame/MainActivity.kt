package com.example.horsegame


import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.TypedValue
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var cellSelected_x = 0
    private var cellSelected_y = 0

    private var movesRequired = 4
    private var moves = 64
    private var options = 0

    private var nameColorBlack = "black_cell"
    private var nameColorWhite = "white_cell"

    private lateinit var board: Array<IntArray>


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
        resetBoard()
        setFirstPosition()

    }

     fun checkCellClicked(view: View){
        var name = view.tag.toString()
        var x = name.subSequence(1, 2).toString().toInt()
        var y = name.subSequence(2, 3).toString().toInt()

        checkCell(x, y)
    }
    private fun checkCell(x: Int, y: Int){

        var dif_x = x - cellSelected_x
        var dif_y = y - cellSelected_y

        var checkTrue = false
        if (dif_x == 1 && dif_y == 2)   checkTrue = true // right - top long
        if (dif_x == 1 && dif_y == -2)  checkTrue = true // right - bottom long
        if (dif_x == 2 && dif_y == 1)   checkTrue = true // right long - top
        if (dif_x == 2 && dif_y == -1)  checkTrue = true // right long - bottom
        if (dif_x == -1 && dif_y == 2)  checkTrue = true // left - top long
        if (dif_x == -1&& dif_y == -2)  checkTrue = true // left - bottom long
        if (dif_x == -2 && dif_y == 1)  checkTrue = true // left long - top
        if (dif_x == -2 && dif_y == -1) checkTrue = true // left long - bottom

        if (board[x][y] == 1) checkTrue = false

        if (checkTrue) selectCell(x, y)

    }

    private fun resetBoard(){

        //0 esta libre
        //1 casilla marcada
        // s es un bonus
        // 9 es una opcion del moviento actual

        board = arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        )
    }

    private fun setFirstPosition(){
        var x = 0
        var y = 0
        x = (0..7).random()
        y = (0..7).random()

        cellSelected_x = x
        cellSelected_y = y
        selectCell(x, y)

    }

    private fun selectCell(x: Int, y: Int){

        moves--
        var tvMovesData = findViewById<TextView>(R.id.tvMovesData)
        tvMovesData.text = moves.toString()

        board[x][y] = 1
        paintHorseCell(cellSelected_x,cellSelected_y, "previus_cell")

        cellSelected_x = x
        cellSelected_y = y

        clearOptions()

        paintHorseCell(x, y, "selected_cell")
        checkOption(x, y )

        if (moves > 0){
            checkNewBonus()
            //checkGameOver(x, y)
        }
        //else checkSuccessFulEnd()
    }

    private fun checkNewBonus(){
        if (moves % movesRequired ==0){
            var bonusCell_x = 0
            var bonusCell_y = 0

            var bonusCell = false
            while (bonusCell == false){
                bonusCell_x = (0..7).random()
                bonusCell_y = (0..7).random()

                if (board[bonusCell_x][bonusCell_y] == 0) bonusCell = true
            }
            board[bonusCell_x][bonusCell_y] = 2
            paintBonusCell(bonusCell_x, bonusCell_y)
        }
    }
    private fun paintBonusCell(x: Int, y: Int){
        var iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        iv.setImageResource(R.drawable.bonus)
    }

    private fun clearOption(x:Int, y:Int){

        var iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        if (checKColorCell(x, y) == "black")
            iv.setBackgroundColor(ContextCompat.getColor(this,
                resources.getIdentifier(nameColorBlack, "color", packageName)))
        else
            iv.setBackgroundColor(ContextCompat.getColor(this,
                resources.getIdentifier(nameColorWhite, "color", packageName)))
        if (board[x][y] ==1) iv.setBackgroundColor(ContextCompat.getColor(this,
                resources.getIdentifier("previus_cell", "color", packageName)))
    }
    private fun clearOptions(){
        for (i in 0..7){
            for (j in 0..7){
                if (board[i][j] == 9 || board[i][j]== 2){
                    if (board[i][j] == 9) board[i][j] = 0
                    clearOption(i, j)
                }
            }
        }
    }

    private fun checkOption(x: Int, y:Int){
         options = 0

        checkMove(x, y, 1, 2)   //check move right -top long
        checkMove(x, y, 2, 1)   //check move right long - top
        checkMove(x, y, 1, -2)  //check move right - bottom long
        checkMove(x, y, 2, -1)  //check move right long - bottom
        checkMove(x, y, -1, 2)  //check move left - top long
        checkMove(x, y, -2, 1)  //check move left long - top
        checkMove(x, y, -1, -2) //check move left - bottom long
        checkMove(x, y, -2, -1) //check move left long - bottom

        var tvOptionsData = findViewById<TextView>(R.id.tvOptionsData)
        tvOptionsData.text = options.toString()
    }
    private fun checkMove(x: Int, y:Int, mov_x: Int, mov_y: Int){
        var option_x = x + mov_x
        var option_y = y + mov_y

        if (option_x < 8 && option_y < 8 && option_x >= 0 && option_y >= 0){
            if (board[option_x][option_y] == 0
                || board[option_x][option_y] == 2 ){
                options++
                paintOptions( option_x, option_y)

                board[option_x][option_y ] = 9


            }
        }

    }
    private fun checKColorCell(x: Int, y: Int): String{
        var color= ""
        var blackColumn_x = arrayOf(0,2,4,6)
        var blackRow_x = arrayOf(1,3,5,7)
        if ((blackColumn_x.contains(x) && blackColumn_x.contains(y))
            || (blackRow_x.contains(x) && blackRow_x.contains(y)))
            color = "black"
        else color = "white"

        return color
    }

    private  fun paintOptions(x: Int, y:Int){
        var iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        if (checKColorCell(x, y) == "black") iv.setBackgroundResource(R.drawable.option_black)
    else iv.setBackgroundResource(R.drawable.option_white)}

    private  fun paintHorseCell(x: Int,y: Int, color: String){
        var iv: ImageView= findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        iv.setBackgroundColor(ContextCompat.getColor(this, resources.getIdentifier(color, "color", packageName)))
        iv.setImageResource(R.drawable.horseb)
    }

    //esta seccion es para que la cuadricula del juego se acomode a las pantallas de diferentes dispositivos
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

                var height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_cell, getResources().getDisplayMetrics()).toInt()
                var width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height_cell, getResources().getDisplayMetrics()).toInt()

                iv.setLayoutParams(TableRow.LayoutParams(width, height))
            }
        }
    }
    // fin seccion de ajuste pantalla para cualquier dispositivo

    //funcion para poner invisible el mensaje donde val nivel y vidas y boton de siguiente nivel
    private  fun hide_message(){
        var lyMessage = findViewById<LinearLayout>(R.id.lyMessage)
        lyMessage.visibility = View.INVISIBLE
    }
}