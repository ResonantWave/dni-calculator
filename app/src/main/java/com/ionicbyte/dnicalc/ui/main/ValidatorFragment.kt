package com.ionicbyte.dnicalc.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.ionicbyte.dnicalc.R

class ValidatorFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_validator, container, false)

        MobileAds.initialize(activity) {}
        val adView = view?.findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val numeroDni = view.findViewById<EditText>(R.id.editText2)
        val letraDni = view.findViewById<EditText>(R.id.editText3)
        val displayDni = view.findViewById<TextView>(R.id.textView1)

        numeroDni.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length < 8) {
                    displayDni?.text = getString(R.string.remainingNumbers, (8 - s.length))
                } else {
                    displayDni?.text = getString(R.string.letter)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        letraDni.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1 && numeroDni.length() == 8) {
                    if(Functions.calculateDNI(numeroDni.text.toString().toInt()) == letraDni.text[0].toUpperCase().toInt()) {
                        displayDni?.text = getString(R.string.valid)
                    } else {
                        displayDni?.text = getString(R.string.invalid)
                    }

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return view
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): ValidatorFragment {
            return ValidatorFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}