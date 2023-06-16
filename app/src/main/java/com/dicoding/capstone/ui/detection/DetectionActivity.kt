package com.dicoding.capstone.ui.detection

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.capstone.R
import com.dicoding.capstone.ml.Model4
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class DetectionActivity : AppCompatActivity() {

    private lateinit var btn_predict: Button
    private lateinit var btn_load_image: Button
    private lateinit var iv_cam: ImageView
    private lateinit var tv_result: TextView
    private lateinit var bitmap: Bitmap

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection)

        btn_predict = findViewById(R.id.btn_predict)
        btn_load_image = findViewById(R.id.btn_load_image)
        iv_cam = findViewById(R.id.iv_cam)
        tv_result = findViewById(R.id.tv_result)

        var imageResize = ImageProcessor.Builder()
            .add(
                ResizeOp(
                    224,
                    224,
                    ResizeOp.ResizeMethod.BILINEAR
                )
            )
            .build()

        var label = application.assets.open("labels.txt").bufferedReader().readLines()

        btn_load_image.setOnClickListener {
            var intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 500)
        }

        btn_predict.setOnClickListener {
            var tensorImg = TensorImage(DataType.FLOAT32)
            tensorImg.load(bitmap)

            tensorImg = imageResize.process(tensorImg)

            val model = Model4.newInstance(this)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImg.buffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maximalIndex = 0

            outputFeature0.forEachIndexed { index, fl ->
                if (outputFeature0[maximalIndex] < fl) {
                    maximalIndex = index
                }
            }

            // Set text view of result prediction
            tv_result.setText(label[maximalIndex])

            // Releases model resources if no longer used.
            model.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 500) {
            var uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver, uri
            )
            iv_cam.setImageBitmap(bitmap)
        }
    }
}