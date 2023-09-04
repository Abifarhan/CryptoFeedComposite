package com.hightech.cryptoapp.crypto.feed.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hightech.cryptoapp.crypto.feed.ui.CryptoFeedRoute
import com.ourproject.domain_module.CryptoFeedItem
import com.ourproject.presenter_module.CryptoFeedViewModel

const val cryptoGraphRoute = "crypto_graph_route"
const val cryptoFeedRoute = "crypto_feed_route"

fun NavGraphBuilder.cryptoGraph(
    onCryptoClick: (CryptoFeedItem) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = cryptoGraphRoute,
        startDestination = cryptoFeedRoute
    ) {
        composable(
            route = cryptoFeedRoute
        ) {
            CryptoFeedRoute(
                onNavigateToCryptoDetails = onCryptoClick)
        }
        nestedGraphs()
    }
}