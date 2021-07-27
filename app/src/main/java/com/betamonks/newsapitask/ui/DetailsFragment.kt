package com.betamonks.newsapitask.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.betamonks.newsapitask.R
import com.betamonks.newsapitask.model.Article
import com.betamonks.newsapitask.viewmodel.SelectedArticleViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedArticleViewModel: SelectedArticleViewModel = ViewModelProviders.of(requireActivity()).get(SelectedArticleViewModel::class.java)
        selectedArticleViewModel.getSelectedArticle().observe(
            viewLifecycleOwner, Observer<Article> { article->
                webViewFragment.loadUrl(article.url)
            }
        )

        webViewFragment.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingArticle.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingArticle.visibility = View.INVISIBLE
            }
        }
    }


}