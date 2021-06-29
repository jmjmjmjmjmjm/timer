package com.gg.exercisetimer

import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import io.realm.RealmObject
import java.util.*


open class data : RealmObject() {
    lateinit var title: String
    lateinit var play: String
    lateinit var rest: String
    lateinit var content: String
    var time:Int=0

    override fun toString(): String {
        return super.toString()
    }
}

class RealmManager(val realm: Realm) {

    fun find(title: String): data? {
        return realm.where(data::class.java).equalTo("title", title).findFirst()
    }

    fun findAll(): List<data> {
        return realm.where(data::class.java).findAll()
    }

    fun create(curdata: data) {
        realm.beginTransaction()
        val data = realm.createObject(data::class.java)
        data.title = curdata.title
        data.play = curdata.play
        data.rest = curdata.rest
        data.content = curdata.content
        data.time=curdata.time

        realm.commitTransaction()
    }

    fun update(title: String, curdata: data) {
        realm.beginTransaction()
        val data = find(title)
        data?.content = curdata.content
        realm.commitTransaction()
    }

    fun deleteByName(title: String) {
        realm.beginTransaction()
        val data = realm.where(data::class.java).equalTo("title", title).findAll()
        data.deleteAllFromRealm()
        realm.commitTransaction()
    }

}