package com.example.login_app.Db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RESULTS + "(" + KEY_ID
                + " integer primary key," + KEY_QUNUM + " integer," + KEY_RANSNUM + " integer" +
                KEY_MARK + " integer," + KEY_STUDENTID + " text" +
                KEY_TOPICNAME + " text," + KEY_SUBJECTNAME + " text" +")")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists " + TABLE_RESULTS)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "contactDb"
        const val TABLE_RESULTS = "results"
        const val KEY_ID = "_id"
        const val KEY_QUNUM = "quNum"
        const val KEY_RANSNUM = "rightAnsNum"
        const val KEY_MARK = "mark"
        const val KEY_STUDENTID = "studentId"
        const val KEY_TOPICNAME = "topicName"
        const val KEY_SUBJECTNAME = "subjectName"
    }
}