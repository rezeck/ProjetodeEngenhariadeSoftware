package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateuspalhares on 19/02/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String NAME_QUESTIONS = "Perguntas";
    public static final String NAME_RANK = "Rank";
    public static final String COLUMN_NAME = "nome";
    public static final String COLUMN_SCORE = "pontuacao";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION = "pergunta";
    public static final String COLUMN_CORRECT = "correta";

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String TABLE_QUESTIONS = "create table "
            + NAME_QUESTIONS
            + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_QUESTION + " text not null,"
                + COLUMN_CORRECT + " integer not null"
            +");";
    private static final String TABLE_RANK = "create table "
            + NAME_RANK
            + "("
            + COLUMN_NAME + " text primary key, "
            + COLUMN_QUESTION + " text not null,"
            + COLUMN_SCORE + " text not null"
            +");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_QUESTIONS);
        database.execSQL(TABLE_RANK);
        InsertQuestion(" Tendo em vista que o desenvolvimento em Cascata é o mais usado no desenvolvimento" +
                " tradicional, quais das características a seguir NÃO representam uma desvantagem desse modelo " +
                "segundo os princípios ágeis?\nA - O término de cada etapa do processo está associado à uma documetação padrão\n" +
                "B - Torna o processo de desenvolvimento estruturado\nC - O cliente não participa do desenvovimento\nD - Ao longo do" +
                " desenvolvimento, não é permitida a atualização ou redefinição de fases já finalizadas",2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANK);
        onCreate(db);
    }

    public boolean InsertQuestion(String question,  int correct){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION,question);
        contentValues.put(COLUMN_CORRECT,correct);
        db.insert(TABLE_QUESTIONS,null,contentValues);
        return true;
    }

    public List<QuestionModel> GetQuestions(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<QuestionModel> list = new ArrayList<QuestionModel>();

        Cursor res =  db.rawQuery( "select * from Perguntas ", null );
        res.moveToFirst();

        while (!res.isAfterLast()){
            QuestionModel question = cursorToQuestion(res);
            list.add(question);
            res.moveToNext();
        }

        res.close();

        db.close();
        return list;
    }

    private QuestionModel cursorToQuestion(Cursor cursor) {
        QuestionModel question = new QuestionModel();
        question.setId(cursor.getLong(0));
        question.setPergunta(cursor.getString(1));
        question.setCorreta(cursor.getInt(2));
        return question;
    }

}
