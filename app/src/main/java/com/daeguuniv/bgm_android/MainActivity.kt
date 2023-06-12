package com.daeguuniv.bgm_android

import MainFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val bottomNavigation: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragment(MainFragment())
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    replaceFragment(MainFragment())
                }
                R.id.nav_playlist -> {
                    // playlist 메뉴를 클릭했을 때 수행할 동작을 여기에 적습니다.
                }
                R.id.nav_search -> {
                    // search 메뉴를 클릭했을 때 수행할 동작을 여기에 적습니다.
                }
                R.id.nav_chat -> {
                    // chat 메뉴를 클릭했을 때 수행할 동작을 여기에 적습니다.
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}