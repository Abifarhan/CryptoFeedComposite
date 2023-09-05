package com.hightech.cryptoapp.crypto.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.cache_module.factories.CryptoFeedLocalInsertFactory
import com.ourproject.cache_module.datasource.db.usecase.LocalCryptoFeedLoaderFactory
import com.hightech.cryptoapp.composite.CryptoFeedLoaderCacheDecorator
import com.hightech.cryptoapp.composite.CryptoFeedLoaderFactory
import com.ourproject.http_module.factories.RemoteCryptoFeedLoaderFactory
import com.ourproject.presenter_module.CryptoFeedViewModel


class CryptoViewModelFactory  : ViewModel() {
    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CryptoFeedViewModel(
                    CryptoFeedLoaderFactory.createCompositeFactory(
                    primary = CryptoFeedLoaderCacheDecorator(
                        decorate = RemoteCryptoFeedLoaderFactory.createRemoteCryptoFeedLoader(),
                        cache = CryptoFeedLocalInsertFactory.createLocalInsertCryptoFeedLoader(),
                    ),
                        fallback = LocalCryptoFeedLoaderFactory.createLocalCryptoFeedLoader()
                    )
                )
            }
        }
    }
}