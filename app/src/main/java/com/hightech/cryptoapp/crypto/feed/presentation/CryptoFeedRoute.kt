package com.hightech.cryptoapp.crypto.feed.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hightech.cryptoapp.crypto.feed.ui.CryptoFeedScreen
import com.ourproject.domain_module.CryptoFeedItem
import com.ourproject.presenter_module.CryptoFeedUiState
import com.ourproject.presenter_module.CryptoFeedViewModel

@Composable
fun CryptoFeedRoute(
    viewModel: CryptoFeedViewModel = viewModel(factory = CryptoViewModelFactory.FACTORY),
    onNavigateToCryptoDetails: (CryptoFeedItem) -> Unit
) {
    val cryptoFeedUiState by viewModel.cryptoFeedUiState.collectAsStateWithLifecycle()

    Log.d("loadCryptoFeed", "$cryptoFeedUiState")

    when (cryptoFeedUiState) {
        is CryptoFeedUiState.HasCryptoFeed -> {
            val hasCryptoFeedState = cryptoFeedUiState as CryptoFeedUiState.HasCryptoFeed
            Log.d("TAG", "CryptoFeedRoute: you get the data $hasCryptoFeedState")
        }

        is CryptoFeedUiState.NoCryptoFeed -> {
            Log.d("TAG", "CryptoFeedRoute: you get the data error")
        }
    }
    CryptoFeedScreen(
        cryptoFeedUiState = cryptoFeedUiState,
        onRefreshCryptoFeed = viewModel::loadCryptoFeed,
        onNavigateToCryptoDetails = onNavigateToCryptoDetails
    )
}