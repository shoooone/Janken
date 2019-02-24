package com.example.janken

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
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
        val history = saveData(result)
        findViewById<TextView>(R.id.rate).text = "勝率：${history.winRate()}%"
    }

    fun saveData(result: Result): History {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        val gameCount = pref.getInt("GAME_COUNT", 0) + 1
        editor.putInt("GAME_COUNT", gameCount)

        var winCount = pref.getInt("WIN_COUNT", 0)
        if (result == Result.WIN) {
            winCount++
            editor.putInt("WIN_COUNT", winCount)
        }
        editor.apply()
        return History(gameCount, winCount)

    }

    fun onClickBackButton(view: View) {
        finish()
    }
}

data class History(val totalGameCount: Int, val totalWinCount: Int){
    fun winRate(): Double {
        return totalWinCount.toDouble() / totalGameCount.toDouble() * 100
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
        return when ((enemyHand.index - index + 3) % 3) {
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