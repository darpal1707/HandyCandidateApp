package com.example.darpa.handycandidateapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.darpa.handycandidateapp.Handyman.HandymanQuestionActivity;
import com.example.darpa.handycandidateapp.model.Questions;

import java.util.ArrayList;

public class HandymanQuestionDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HandyQuestions.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    /*public HandymanQuestionDbHelper(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }*/

    public HandymanQuestionDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionContract.QuestionsTable.TABLE_NAME + " ( " +
                QuestionContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("You have a job at 9:00am and realize you won't arrive until 9:20am. You would:",
                "Notify the client and Handy as soon as possible and plan to stay overtime.",
                "Notify the client and Handy after 9:00am in case you end up arriving by 9:10am.",
                "Don't notify anyone since you're only going to be 20 minutes late.",
                1);
        addQuestion(q1);
        Questions q2 = new Questions("What would you do if you arrive at a job and do not have necessary tools to complete the required task?",
                "Do your best to complete the job with the tools you have.",
                "Tell the customer you cannot do the job and leave.",
                "Explain to the customer that you do not have the required tools and ask them to contact Handy to reschedule the booking and add a note regarding what tools are required.",
                3);
        addQuestion(q2);


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestion(Questions question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionContract.QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionContract.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
