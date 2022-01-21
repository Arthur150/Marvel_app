package com.example.marvel_app.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import com.example.marvel_app.R
import com.example.marvel_app.model.QRCode.QRCodeData
import com.example.marvel_app.model.QRCode.ScanQRCodeViewModel
import com.example.marvel_app.view.character.CharacterDetailActivity
import com.example.marvel_app.view.comic.ComicDetailActivity
import com.example.marvel_app.view.serie.SerieDetailActivity
import com.google.gson.Gson

class QRCodeScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    private var model: ScanQRCodeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        model = ViewModelProvider(this)[ScanQRCodeViewModel::class.java]

        model?.getCharacter()?.observe(this, { character ->
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra("MarvelCharacter", character)
            startActivity(intent)
        })

        model?.getComic()?.observe(this, { comic ->
            val intent = Intent(this, ComicDetailActivity::class.java)
            intent.putExtra("MarvelComic", comic)
            startActivity(intent)
        })

        model?.getSerie()?.observe(this, { serie ->
            val intent = Intent(this, SerieDetailActivity::class.java)
            intent.putExtra("MarvelSerie", serie)
            startActivity(intent)
        })

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback { result ->
            val data = Gson().fromJson(result.text, QRCodeData::class.java)

            when (data.type) {
                "character" -> {
                    model?.loadCharacter(data.id)
                }
                "comic" -> {
                    model?.loadComic(data.id)
                }
                "serie" -> {
                    model?.loadSerie(data.id)
                }
            }

        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.cameraError) + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.cameraGranted), Toast.LENGTH_LONG).show()
                startScanning()
            } else {
                Toast.makeText(this, getString(R.string.cameraNotGranted), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }
}