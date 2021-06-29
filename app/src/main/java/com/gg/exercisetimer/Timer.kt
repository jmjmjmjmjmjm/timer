package com.gg.exercisetimer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_timer.*
import java.util.*
import java.util.Calendar
import kotlin.concurrent.timer

class timer : Fragment() {

    private var time = 0
    private var timerTask: Timer? = null
    private var resttime = 0
    private var resttimerTask: Timer? = null
    lateinit var realmManager: RealmManager
    val instance = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Realm.init(this.context)
        realmManager = RealmManager(Realm.getDefaultInstance())
        return inflater.inflate(R.layout.fragment_timer, container, false)

    }

    override fun onStart() {
        super.onStart()

        play_btn.setOnClickListener {
            reststop()        //휴식멈춰
            startTimer()      //운동시간 시작
            play_btn.visibility = View.INVISIBLE //버튼 없애기
            stop_btn.visibility = View.VISIBLE  // 정지버튼 생성
        }

        stop_btn.setOnClickListener {
            startstop() // 운동멈춰
            restTimer()     //휴식시작
            play_btn.visibility = View.VISIBLE
            stop_btn.visibility = View.INVISIBLE
        }

        save_btn.setOnClickListener {
            reststop()
            startstop()
            if (realmManager.find(CalendarDay.today().toString()) == null) {
                var play = exercise.text
                var rest = rest.text
                var data1 = data()
                var month = instance.get(Calendar.MONTH) + 1
                var playtime = time / 100
                data1.title = CalendarDay.today().toString()  // 머터리얼캘린더랑 맞추기위해
                data1.play = play.toString()
                data1.rest = rest.toString()
                data1.time = playtime
                data1.content = "이날 하신운동 기록하실래요?"
                Log.d("기록확인", ": " + month + "," + playtime)

                realmManager.create(data1)
                Toast.makeText(activity, "캘린더에 저장되었습니다 ->.", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(activity, "오늘 이미 기록하셨어요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startTimer() {
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            val min = sec / 60
            exercise.text = "${min}:${sec % 60}:${milli}"
        }
    }

    fun restTimer() {
        resttimerTask = timer(period = 10) {
            resttime++
            val sec = (resttime / 100)
            val milli = resttime % 100
            val min = sec / 60
            rest.text = "${min}:${sec % 60}:${milli}"
        }
    }

    fun startstop() {
        timerTask?.cancel()
    }

    fun reststop() {
        resttimerTask?.cancel()
    }

}

