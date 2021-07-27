package com.betamonks.newsapitask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betamonks.newsapitask.R
import com.betamonks.newsapitask.model.Article
import com.betamonks.newsapitask.model.Source
import com.betamonks.newsapitask.toSimpleString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article_list.view.*

class ArticleAdapter(var articleList: List<Article> = ArrayList()): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    var mClickListener: ClickListener? = null

    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articleList.get(position))
    }

    fun updateList(article: List<Article>){
        this.articleList = article
        notifyDataSetChanged()
    }


    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view: View = itemView
        private lateinit var article : Article
      //  private lateinit var source: Source

        init {
            itemView.setOnClickListener(this)
        }

        fun bindArticle(article: Article){
            this.article = article
      //      this.source = source
            Picasso.get().load(article.urlToImage)
                .placeholder(R.drawable.loading)
                .into(view.ivArticleImage)

            view.tvTitle.text = article.title
            view.tvDescription.text = article.description
            view.article_date.text = toSimpleString(article.publishedAt)
     //       view.tvSource.text = source.name
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(article)
        }
    }

    interface ClickListener{
        fun onClick(article: Article)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }
}