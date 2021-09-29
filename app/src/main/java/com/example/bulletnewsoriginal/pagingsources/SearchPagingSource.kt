package com.example.bulletnewsoriginal.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.service.api.RetrofitService
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val retrofitService: RetrofitService,
    private val query : String
) :PagingSource<Int,Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val nextPage = params.key ?: 1
            val response = retrofitService.getEverythingForSearchPaging(
                topic = query,page = nextPage,pageSize = params.loadSize
            )
            println("source working")
            println(nextPage)
            println(params.loadSize)
            if (response.isSuccessful){
                response.body()?.let {
                    val articleList = it.articles
                    return LoadResult.Page(
                        data = articleList,
                        prevKey = null,
                        nextKey = if(articleList.isNullOrEmpty()) null else nextPage+1
                    )
                } ?: return LoadResult.Error(Throwable("There is unexpected error !!!"))
            }else return LoadResult.Error(Throwable("There is unexpected error !!!"))
        }catch (e : HttpException){
            return LoadResult.Error(e)
        }catch (e : IOException){
            return LoadResult.Error(e)
        }
    }
}