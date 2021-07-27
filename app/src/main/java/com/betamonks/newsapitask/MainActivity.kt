package com.betamonks.newsapitask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.betamonks.newsapitask.ui.ArticleListFragment
import com.betamonks.newsapitask.viewmodel.ArticleViewModel

class MainActivity : AppCompatActivity() {

    lateinit var articleViewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val articleFragment = ArticleListFragment()
            supportFragmentManager.beginTransaction().add(R.id.screen_container, articleFragment).commit()
        }
    }
}