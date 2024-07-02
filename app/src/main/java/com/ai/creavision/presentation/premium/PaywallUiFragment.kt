package com.ai.creavision.presentation.premium

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adapty.Adapty
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyProfile
import com.adapty.models.AdaptyPurchasedInfo
import com.adapty.ui.AdaptyPaywallInsets
import com.adapty.ui.AdaptyPaywallView
import com.adapty.ui.AdaptyUI
import com.adapty.ui.listeners.AdaptyUiDefaultEventListener
import com.adapty.ui.listeners.AdaptyUiPersonalizedOfferResolver
import com.adapty.ui.listeners.AdaptyUiTagResolver
import com.adapty.utils.AdaptyResult
import com.ai.creavision.R
import com.ai.creavision.utils.DataHolder

class PaywallUiFragment : Fragment(R.layout.fragment_paywall_ui) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPaywall()
    }

    private fun initPaywall() {

        val paywallView = view as? AdaptyPaywallView ?: return

        paywallView.setEventListener(
            object : AdaptyUiDefaultEventListener() {

                /**
                 * You can override more methods if needed
                 */

                override fun onRestoreSuccess(
                    profile: AdaptyProfile,
                    view: AdaptyPaywallView,
                ) {
                    if (profile.accessLevels["premium"]?.isActive == true) {
                        DataHolder.isPremium.value = true
                        parentFragmentManager.popBackStack()
                    }
                }

                override fun onPurchaseSuccess(
                    purchasedInfo: AdaptyPurchasedInfo?,
                    product: AdaptyPaywallProduct,
                    view: AdaptyPaywallView
                ) {
                    super.onPurchaseSuccess(purchasedInfo, product, view)
                    DataHolder.isPremium.value = true

                }
            }
        )

        /**
         * You need the `onReceiveSystemBarsInsets` callback only in case the status bar or
         * navigation bar overlap the view otherwise it may not be called, so simply
         * call `paywallView.showPaywall(paywall, products, viewConfig, AdaptyPaywallInsets.NONE)`
         */

        AdaptyUI.getViewConfiguration(DataHolder.paywall.value!!) { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val viewConfiguration = result.value

                    paywallView.showPaywall(
                        viewConfiguration,
                        null,
                        AdaptyPaywallInsets.of(5, 200),
                        AdaptyUiPersonalizedOfferResolver.DEFAULT,
                        AdaptyUiTagResolver.DEFAULT
                    )
                }

                is AdaptyResult.Error -> {
                    val error = result.error
                    // handle the error
                }
            }
        }
    }
}