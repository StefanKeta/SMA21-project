package com.example.licenta.util

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.lang.RuntimeException

class BarcodeAnalyzer(private val callback: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val capture = imageProxy.image
        if (capture != null) {
            val imageToAnalyze =
                InputImage.fromMediaImage(capture, imageProxy.imageInfo.rotationDegrees)
            scanner.process(imageToAnalyze)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.size != 1){
                        throw RuntimeException()
                    }
                    callback(barcodes[0].rawValue!!)
                }
                .addOnFailureListener{ e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener{
                    capture.close()
                }
        }
    }

}