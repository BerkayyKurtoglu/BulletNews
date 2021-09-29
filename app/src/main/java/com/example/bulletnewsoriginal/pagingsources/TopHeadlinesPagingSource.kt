package com.example.bulletnewsoriginal.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.service.api.RetrofitService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopHeadlinesPagingSource(
    var retrofitService: RetrofitService
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
         try {
            val pageNumber = params.key ?: 1
            val response = retrofitService.getResponseForTopHeadlines(pageNumber,params.loadSize)
            val resultNumber = response.body()?.totalResults
            val maxNumber = resultNumber?.div(20)
            return LoadResult.Page(
                data = response.body()?.articles ?: listOf(),
                prevKey = null,
                nextKey = if (response.body()?.articles == null) null else pageNumber+1
                //if (pageNumber+1 > maxNumber!!) null else pageNumber+1
            )

        }catch (e : HttpException){
            return LoadResult.Error(e)
        }catch (e : IOException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}