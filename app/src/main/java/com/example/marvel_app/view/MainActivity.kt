package com.example.marvel_app.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvel_app.R
import com.example.marvel_app.view.character.CharacterListFragment
import com.example.marvel_app.view.comic.ComicsFragment
import com.example.marvel_app.view.serie.SeriesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavBar)
        initNav()

        val scanQRCodeButton = findViewById<ImageButton>(R.id.mainQRCodeScanner)

        scanQRCodeButton.setOnClickListener {
            startActivity(Intent(this, QRCodeScannerActivity::class.java))
        }


    }

    private fun initNav() {
        bottomNavigationView?.itemIconTintList = null
        bottomNavigationView?.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView?.selectedItemId = R.id.characters
        bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.series -> {
                    showFragment(SeriesFragment())
                    true
                }
                R.id.characters -> {
                    showFragment(CharacterListFragment())
                    true
                }
                R.id.comics -> {
                    showFragment(ComicsFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainerView, fragment)
        fragmentManager.commit()
    }

}