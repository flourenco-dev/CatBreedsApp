package com.fabiolourenco.catbreedsapp.ui.feature.paginatedBreeds

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.Repository

class BreedsPagingSource(
    private val repository: Repository,
    private val pageSize: Int
) : PagingSource<Int, CatBreed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreed> {
        val page = params.key ?: 0
        val limit = pageSize
        return try {
            val breeds = repository.getBreedsPage(page = page, limit = limit)

            LoadResult.Page(
                data = breeds,
                prevKey = if (page == 0) {
                    null
                } else {
                    page - 1
                },
                nextKey = if (breeds.size < limit) {
                    null
                } else {
                    page + 1
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatBreed>): Int? =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
}