package com.hipark.randomer.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hipark.randomer.Injection
import com.hipark.randomer.R
import com.hipark.randomer.util.replaceFragmentInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var itemPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
            as MainFragment? ?: MainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        MainPresenter(Injection.provideItemRepository(applicationContext), mainFragment);

    }
}
