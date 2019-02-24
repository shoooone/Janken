package com.example.janken

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onJankenButtonTapped(view: View) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("MY_HAND", view.id)
        startActivity(intent)
    }
}
