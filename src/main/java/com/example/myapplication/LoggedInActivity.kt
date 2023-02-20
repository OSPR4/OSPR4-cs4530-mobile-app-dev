package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayOutputStream

class LoggedInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceStateLogin: Bundle?) {
        super.onCreate(savedInstanceStateLogin)
        setContentView(R.layout.activity_logged_in)

        val textView = findViewById<TextView>(R.id.textView)


        val text = intent.getStringExtra("user")
        val content = "$text is logged in!"
        textView.text = content


    }
}