package com.prj.coroutineex.alram

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prj.coroutineex.databinding.ActivityImageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun loadImage(imageUrl : String) : Bitmap{
    val url = URL(imageUrl)
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}

class ImageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityImageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // run 스코프로 감싸서 binding. 제거가능
        binding.run {
            binding.buttonDownload.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    // 프로그래스바 설정
                    progress.visibility = View.VISIBLE
                    val url = binding.editUrl.text.toString()
                    // 백그라운드 처리를 담당하는 IO 컨텍스트에서 진행해야 하므로 withContext()를 사용하여 컨텍스트를 IO로 전환, 결과값은 bitmap 변수에 저장
                    val bitmap = withContext(Dispatchers.IO){
                        loadImage(url)
                    }
                    // 이미지뷰에 bitmap을 입력하고 프로그래스바 다시 설정
                    imagePreview.setImageBitmap(bitmap)
                    progress.visibility = View.GONE
                }
            }
        }

    }
}