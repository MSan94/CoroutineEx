package com.prj.coroutineex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prj.coroutineex.alram.AlarmActivity
import com.prj.coroutineex.alram.ImageActivity
import com.prj.coroutineex.databinding.ActivityMainBinding
import com.prj.coroutineex.mvvm.view.DbActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        binding.btnImage.setOnClickListener {
            var intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }

        binding.btnDb.setOnClickListener {
            var intent = Intent(this, DbActivity::class.java)
            startActivity(intent)
        }

    }
}