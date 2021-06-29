package com.gg.exercisetimer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_total.*

class Total : Fragment() {
    lateinit var realmManager: RealmManager
    var num: String = "0"
    var ava: String = "0"
    var size = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        realmManager = RealmManager(Realm.getDefaultInstance())
        size = realmManager.findAll().size

        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onStart() {
        super.onStart()
        total_search.setOnClickListener {
            if (size > 0) {
                num = number()
                ava = average()
            }
            total_play.text = num
            total_average.text = ava+"분"
        }

    }

    fun number(): String {          // 운동횟수
        var date: Int = 0
        for (i in 0 until realmManager.findAll().size) {
            Log.d("기록들", "" + realmManager.findAll()[i].time)
            date++
        }
        return date.toString()
    }

    fun average(): String {         // 운동평균시간
        var min = 0
        for (i in 0 until realmManager.findAll().size) {
            min += (realmManager.findAll()[i].time) / 60
        }
        return (min / realmManager.findAll().size).toString()
    }
}