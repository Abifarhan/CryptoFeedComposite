package com.hightech.cryptoapp.crypto.feed.presentation

import android.util.Log
import android.widget.RemoteViews.RemoteResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hightech.cryptoapp.composite.CryptoFeedLoaderComposite
import com.hightech.cryptoapp.composite.CryptoFeedLoaderFactory
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedItem
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedLoader
import com.hightech.cryptoapp.crypto.feed.domain.CryptoFeedResult
import com.hightech.cryptoapp.crypto.feed.datasource.http.usecases.Connectivity
import com.hightech.cryptoapp.crypto.feed.datasource.http.usecases.InvalidData
import com.hightech.cryptoapp.crypto.feed.datasource.http.usecases.LocalCryptoFeedLoader
import com.hightech.cryptoapp.crypto.feed.datasource.http.usecases.LocalCryptoFeedLoaderFactory
import com.hightech.cryptoapp.main.factories.RemoteCryptoFeedLoaderFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CryptoFeedUiState {
    val isLoading: Boolean
    val failed: String

    data class HasCryptoFeed(
        override val isLoading: Boolean,
        val cryptoFeeds: List<CryptoFeedItem>,
        override val failed: String
    ) : CryptoFeedUiState

    data class NoCryptoFeed(
        override val isLoading: Boolean,
        override val failed: String,
    ) : CryptoFeedUiState
}

data class CryptoFeedViewModelState(
    val isLoading: Boolean = false,
    val cryptoFeeds: List<CryptoFeedItem> = emptyList(),
    val failed: String = ""
) {
    fun toCryptoFeedUiState(): CryptoFeedUiState =
        if (cryptoFeeds.isEmpty()) {
            CryptoFeedUiState.NoCryptoFeed(
                isLoading = isLoading,
                failed = failed
            )

        } else {
            CryptoFeedUiState.HasCryptoFeed(
                isLoading = isLoading,
                cryptoFeeds = cryptoFeeds,
                failed = failed
            )
        }
}

class CryptoFeedViewModel constructor(
    private val cryptoFeedLoader: CryptoFeedLoader
) : ViewModel() {
    private val viewModelState = MutableStateFlow(
        CryptoFeedViewModelState(
            isLoading = true,
            failed = ""
        )
    )

    val cryptoFeedUiState = viewModelState
        .map(CryptoFeedViewModelState::toCryptoFeedUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = viewModelState.value.toCryptoFeedUiState()
        )

    init {
        loadCryptoFeed()
    }

    fun loadCryptoFeed() {
        viewModelScope.launch {
            cryptoFeedLoader.load().collect { result ->
                Log.d("loadCryptoFeed", "$result")
                viewModelState.update {
                    when (result) {
                        is CryptoFeedResult.Success -> {
                            Log.d("loadCryptoFeed", " success get data $result")

                            it.copy(
                                cryptoFeeds = result.cryptoFeedItems,
                                isLoading = false
                            )
                        }

                        is CryptoFeedResult.Failure -> {
                            Log.d("loadCryptoFeed", " failure get data $result")
                            it.copy(
                                failed = when (result.throwable) {
                                    is Connectivity -> "Connectivity"
                                    is InvalidData -> "Invalid Data"
                                    else -> "Something Went Wrong"
                                },
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CryptoFeedViewModel(
                    CryptoFeedLoaderFactory.createCompositeFactory(
                        primary = CryptoFeedLoaderFactory.createCompositeFactory(
                            primary = CryptoFeedLoaderFactory.createCompositeFactory(
                                primary = RemoteCryptoFeedLoaderFactory.createRemoteCryptoFeedLoader(),
                                fallback = LocalCryptoFeedLoaderFactory.createLocalCryptoFeedLoader()),
                            fallback = LocalCryptoFeedLoaderFactory.createLocalCryptoFeedLoader()

                        ),
                        fallback = LocalCryptoFeedLoaderFactory.createLocalCryptoFeedLoader()
                    )
                )
            }
        }
    }
}