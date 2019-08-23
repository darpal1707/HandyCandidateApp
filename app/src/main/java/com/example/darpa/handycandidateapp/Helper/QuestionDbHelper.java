package com.example.darpa.handycandidateapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.darpa.handycandidateapp.model.Questions;
import com.example.darpa.handycandidateapp.Helper.QuestionContract;

import java.util.ArrayList;
import java.util.List;

public class QuestionDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Questions.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuestionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        Questions q1 = new Questions("What would you do about cleaning cowebs and dusting?",
                "Don't clean any cowebs because that's too detailed, but I do dusting.",
                "Don't clean any cowebs or dust because it takes too long.",
                "Clean cowebs I can reach, and dust all surfaces.",
                3);
        addQuestion(q1);
        Questions q2 = new Questions("What is the effect of Oven cleaner or kitchen countertops?",
                "No effect.",
                "Enhances shine.",
                "Stains surface.",
                3);
        addQuestion(q2);
        Questions q3 = new Questions("In what order would you clean these items in the kitchen?",
                "Clean the floors, wash the dishes, wipe countertops.",
                "Wash dishes, wipe countertops, clean floors.",
                "Wash dishes, clean floors, wipe countertops.",
                2);
        addQuestion(q3);

    }

    @Override
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
