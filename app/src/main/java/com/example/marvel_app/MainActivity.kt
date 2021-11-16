package com.example.marvel_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavBar)
        initNav()


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