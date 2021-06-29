package com.gg.exercisetimer


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.Calendar
import java.util.Calendar.DAY_OF_WEEK

class Calendar : Fragment() {
    lateinit var realmManager: RealmManager
    val saturdayDecorator = SaturdayDecorator()
    val sundayDecorator = SundayDecorator()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Realm.init(this.context)
        realmManager = RealmManager(Realm.getDefaultInstance())
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onStart() {
        super.onStart()
        calendarview.addDecorator(sundayDecorator)
        calendarview.addDecorator(saturdayDecorator)

        var rec = contentrecord.findViewById<EditText>(R.id.contentrecord)
        calendarview.setOnDateChangedListener { widget, date, selected ->

            var title = date.toString()
            Log.d("타이틀확인", "" + title)

            if (realmManager.find(title) != null) {
                Log.d("타이틀널확인", "널이아닙니다")
                var play = realmManager.find(title)?.play
                var rest = realmManager.find(title)?.rest
                var content = realmManager.find(title)!!.content
                playrecord.text = play
                restrecord.text = rest
                Log.d("리스트확인", "" + realmManager.findAll()[0].title)
                rec.setText(content)
                calendarview.addDecorator(Decor(date))
            } else {
                playrecord.text = "00:00"
                restrecord.text = "00:00"
                rec.setText("")
            }
            del_Btn.setOnClickListener {
                realmManager.deleteByName(title)
                playrecord.text = "00:00"
                restrecord.text = "00:00"
                rec.setText("")
            }
            store.setOnClickListener {
                var data1 = data()
                data1.content = contentrecord.text.toString()
                realmManager.update(title, data1)
            }
            Log.d("날짜", "" + realmManager.find(title)?.content)  // 선택날짜 보여짐(타이틀).
        }
    }
}

class Decor(
    var currentDay: CalendarDay
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == currentDay
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5F, Color.RED))
    }
}


class SaturdayDecorator : DayViewDecorator {
    private val calendar = java.util.Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(Color.BLUE) {})
    }
}

class SundayDecorator : DayViewDecorator {
    private val calendar = java.util.Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(Color.RED) {})
    }
}