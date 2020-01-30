package io.fajarca.home.presentation.list

import androidx.lifecycle.*
import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.home.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel (private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) :
    ViewModel() {

    class Factory @Inject constructor(
        private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getTopHeadlinesUseCase) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    private val _topHeadlines = MutableLiveData<TopHeadlinesState<List<TopHeadline>>>()
    val topHeadlines: LiveData<TopHeadlinesState<List<TopHeadline>>>
        get() = _topHeadlines

    sealed class TopHeadlinesState<out T> {
        object Empty : TopHeadlinesState<Nothing>()
        object Loading : TopHeadlinesState<Nothing>()
        data class Success<out T>(val data: T) : TopHeadlinesState<T>()
    }

    fun getTopHeadlines() {
        _topHeadlines.postValue(TopHeadlinesState.Loading)
        viewModelScope.launch {
            val headlines = getTopHeadlinesUseCase.execute()
            if (headlines.isEmpty()) _topHeadlines.value =
                TopHeadlinesState.Empty else _topHeadlines.value =
                TopHeadlinesState.Success(
                    map(headlines)
                )
        }
    }


    private fun map(headlines: List<TopHeadline>) : List<TopHeadline> {
        val mappedHeadlines = mutableListOf<TopHeadline>()
        headlines.map {
            mappedHeadlines.add(TopHeadline(it.id, it.title, it.url, it.imageUrl, toLocalDateTime(it.publishedAt), it.sourceId, it.sourceName))
        }
        return mappedHeadlines
    }

    private fun toLocalDateTime(input: String): String {
        if (input.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            try {
                val date = dateFormat.parse(input)
                val formattedDate = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(date)

                return formattedDate
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return ""
    }
}