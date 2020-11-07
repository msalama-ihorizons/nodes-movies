package com.nodes.movies.ui.moviedetails.rate

import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nodes.movies.R
import kotlinx.android.synthetic.main.rate_bottom_sheet_fragment.*

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

class RateBottomSheetFragment: BottomSheetDialogFragment() {

    var rateClickCallback: RateClickCallback? = null

    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (root != null) {
            val parent = root?.parent as ViewGroup
            parent.removeView(root)
        }
        try {
            root = inflater.inflate(R.layout.rate_bottom_sheet_fragment, container, false)
        } catch (e: InflateException) {
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRate.setOnClickListener {
            rateClickCallback?.onRateBtnClick(ratingBar.rating)
            dismiss()
        }
    }


    interface RateClickCallback {
        fun onRateBtnClick(rateValue: Float)
    }
}