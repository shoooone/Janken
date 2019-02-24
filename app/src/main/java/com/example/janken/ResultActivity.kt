package com.example.janken

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val id = intent.getIntExtra("MY_HAND", 0)
        val myHand = fromViewId(id)
        findViewById<ImageView>(R.id.my_hand_image).setImageResource(myHand.myHandImage)

        val comHand = randomHand()
        findViewById<ImageView>(R.id.com_hand_image).setImageResource(comHand.comHandImage)

        val result = myHand.judge(comHand)
        findViewById<TextView>(R.id.result_label).setText(result.text)
    }

    fun onClickBackButton(view: View) {
        finish()
    }
}

enum class Hand(
    val index: Int,
    val viewId: Int,
    val myHandImage: Int,
    val comHandImage: Int
) {

    GU(0, R.id.gu, R.drawable.gu, R.drawable.com_gu),
    CHOKI(1, R.id.choki, R.drawable.choki, R.drawable.com_choki),
    PA(2, R.id.pa, R.drawable.pa, R.drawable.com_pa);

    fun judge(enemyHand: Hand): Result {
        return when((enemyHand.index - index + 3) % 3){
            0 -> Result.DRAW
            1 -> Result.WIN
            2 -> Result.LOSE
            else -> Result.DRAW
        }
    }
}

enum class Result(
    val text: Int
) {
    WIN(R.string.result_win),
    LOSE(R.string.result_lose),
    DRAW(R.string.result_draw);
}

fun fromViewId(id: Int): Hand {
    return Hand.values().find { it.viewId == id } ?: Hand.GU
}

fun randomHand() = Hand.values()[Random.nextInt(0..2)]