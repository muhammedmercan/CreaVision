package com.ai.creavision.domain.model

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ai.creavision.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AspectRatioBottomSheetModel: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.aspect_ratio_modal_bottom_sheet_content, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

}

