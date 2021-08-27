package com.example.bulletnewsoriginal.adapter

import com.example.bulletnewsoriginal.model.Article

interface AdapterListener {
    fun onClickListener(article: Article)
}