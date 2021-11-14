package com.prj.coroutineex.alram

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.prj.coroutineex.databinding.ActivityAlarmBinding
import kotlin.concurrent.thread

class AlarmActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAlarmBinding.inflate(layoutInflater) }
    var total = 0
    var started = false

    val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            var minute = String.format("%02d",total/60)
            var second = String.format("%02d",total%60)
            binding.textTimer.text = "$minute:$second"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("TestApp","---------")
        binding.buttonStart.setOnClickListener {
            Log.d("TestApp","시작")
            started = true
            Log.d("TestApp","호출")
            thread(start=true){
                while(started){
                    Log.d("TestApp","루프")
                    Thread.sleep(1000)
                    if(started){
                        total += 1
                        handler?.sendEmptyMessage(0)
                    }
                }
            }
        }

        binding.buttonStop.setOnClickListener {
            Log.d("TestApp","종료")
            if(started){
                started = false
                total = 0
                binding.textTimer.text = "00:00"
            }
        }

    }
}