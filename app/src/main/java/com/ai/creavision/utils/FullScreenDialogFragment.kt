import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adapty.Adapty
import com.adapty.internal.utils.InternalAdaptyApi
import com.adapty.models.AdaptyPaywall
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyProfile
import com.adapty.ui.AdaptyPaywallInsets
import com.adapty.ui.AdaptyPaywallView
import com.adapty.ui.AdaptyUI
import com.adapty.ui.listeners.AdaptyUiDefaultEventListener
import com.adapty.ui.listeners.AdaptyUiEventListener
import com.adapty.ui.listeners.AdaptyUiPersonalizedOfferResolver
import com.adapty.ui.listeners.AdaptyUiTagResolver
import com.adapty.utils.AdaptyResult
import com.adapty.utils.seconds
import com.ai.creavision.databinding.FragmentFullScreenDialogBinding
import com.ai.creavision.utils.DataHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FullScreenDialogFragment : DialogFragment() {

    private var _binding: FragmentFullScreenDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullScreenDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    @OptIn(InternalAdaptyApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        )



        AdaptyUI.getViewConfiguration(DataHolder.paywall) { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val viewConfiguration = result.value

                    paywallView.showPaywall(
                        viewConfiguration,
                        null,
                        AdaptyPaywallInsets.NONE,
                        AdaptyUiPersonalizedOfferResolver.DEFAULT,
                        tagResolver = AdaptyUiTagResolver.DEFAULT
                    )

                    println("olumluu2")
                    
                }

                is AdaptyResult.Error -> {
                    val error = result.error
                    // handle the error
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    private fun onClick() {


        /*
        binding.btnPayContinue.setOnClickListener() {

        }

        binding.btnFullScreenDialogCancel.setOnClickListener() {

            findNavController().popBackStack()

        }

         */

    }
}
