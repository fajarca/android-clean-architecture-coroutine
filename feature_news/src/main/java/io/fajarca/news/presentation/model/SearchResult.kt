package io.fajarca.news.presentation.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.fajarca.core.vo.Result
import io.fajarca.news.domain.entities.News

data class SearchResult(val searchState : LiveData<Result<List<News>>>, val news : LiveData<PagedList<News>> )