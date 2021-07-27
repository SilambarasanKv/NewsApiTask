package com.betamonks.newsapitask.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betamonks.newsapitask.viewmodel.ArticleViewModel

import com.betamonks.newsapitask.R
import com.betamonks.newsapitask.adapter.ArticleAdapter
import com.betamonks.newsapitask.model.Article
import com.betamonks.newsapitask.model.ArticleResult
import com.betamonks.newsapitask.viewmodel.SelectedArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*

class ArticleListFragment : Fragment(), ArticleAdapter.ClickListener {
    private lateinit var articleAdapter : ArticleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        articleAdapter = ArticleAdapter()
        recycler_article.adapter = articleAdapter
        recycler_article.layoutManager = viewManager

        articleAdapter.setOnClickListener(this)

        observeViewModel()
    }

    fun observeViewModel(){
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        articleViewModel.getResults().observe(viewLifecycleOwner, Observer<ArticleResult> { result->
            recycler_article.visibility = View.VISIBLE
            articleAdapter.updateList(result.articles)
        })

        articleViewModel.getError().observe(viewLifecycleOwner, Observer<Boolean> {isError ->
            if(isError) {
                error_msg.visibility = View.VISIBLE
                recycler_article.visibility = View.GONE

                //swipe refresh when internet from disconnected to connect
                swipeLayout.setOnRefreshListener {
                    swipeLayout.isRefreshing = false
                    articleViewModel.loadResults()
                }
            }else{
                error_msg.visibility = View.GONE
            }
        })

        articleViewModel.getLoading().observe(viewLifecycleOwner, Observer<Boolean> {isLoading ->
            loadingView.visibility = (if(isLoading) View.VISIBLE else View.INVISIBLE)
            if(isLoading){
                error_msg.visibility = View.GONE
                recycler_article.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        articleViewModel.loadResults()
    }

    override fun onClick(article: Article) {
        if (!TextUtils.isEmpty(article.url)){
            val selectedArticleViewModel: SelectedArticleViewModel = ViewModelProviders.of(requireActivity()).get(SelectedArticleViewModel::class.java)
            selectedArticleViewModel.selectedArticle(article)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.screen_article, DetailsFragment())
                .addToBackStack(null)           //replace fragment you want to go back
                .commit()
        }
    }


}