package com.ionicbyte.dnicalc.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ionicbyte.dnicalc.R

class MainFragment : Fragment() {

    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var adView : AdView

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireActivity().baseContext, getString(R.string.interstitialId), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
    private fun calculateDni() {
        val editText1 = view?.findViewById<EditText>(R.id.editText1)
        val displayDni = view?.findViewById<TextView>(R.id.textView1)

        val dni = editText1?.text.toString().toInt()
        val letter = Functions.calculateDNI(dni).toChar().toString()
        displayDni?.text = getString(R.string.calculatedDni, dni, letter)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val buttonCalculate = view.findViewById<Button>(R.id.buttonCalculate)
        val displayDni = view.findViewById<TextView>(R.id.textView1)
        val editText1 = view.findViewById<EditText>(R.id.editText1)

        buttonCalculate.visibility = View.GONE

        MobileAds.initialize(requireActivity().baseContext) {}
        loadInterstitialAd()
        val adRequest = AdRequest.Builder().build()
        adView = view?.findViewById<View>(R.id.adView) as AdView
        adView.loadAd(adRequest)

        editText1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length < 8) {
                    displayDni?.text = getString(R.string.remainingNumbers, (8 - s.length))
                    buttonCalculate.visibility = View.GONE
                } else {
                    displayDni?.text = ""
                    buttonCalculate.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        buttonCalculate.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            }

            loadInterstitialAd()
            calculateDni()
        }

        return view
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}