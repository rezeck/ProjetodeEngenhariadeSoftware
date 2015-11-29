package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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
                + COLUMN_ID + " integer primary key, "
            + COLUMN_QUESTION + " text not null,"
                + COLUMN_CORRECT + " integer not null"
            +");";

    private static final String TABLE_RANK = "create table "
            + NAME_RANK
            + "("
            + COLUMN_NAME + " text, "
            + COLUMN_SCORE + " integer"
            +");";

    private Context context;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;


    }

    public void InitiateQuestions() throws XmlPullParserException, IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        XmlResourceParser parser = this.context.getResources().getXml(R.xml.questions);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("question")) {
                String id = null, enunciate = null,
                        alternativeA = null, alternativeB = null,
                        alternativeC = null, alternativeD = null,
                        solution = null;

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();
                    if (name.equals("id")) {
                        id = readText(parser);
                    } else if (name.equals("enunciate")) {
                        enunciate = readText(parser);
                    } else if (name.equals("alternativeA")) {
                        alternativeA = readText(parser);
                    } else if (name.equals("alternativeB")) {
                        alternativeB = readText(parser);
                    } else if (name.equals("alternativeC")) {
                        alternativeC = readText(parser);
                    } else if (name.equals("alternativeD")) {
                        alternativeD = readText(parser);
                    } else if (name.equals("solution")) {
                        solution = readText(parser);
                    }
                }
                String q = enunciate + "\n" + "A - " + alternativeA + "\n" +
                        "B - " + alternativeB + "\n" + "C - " + alternativeC + "\n" +
                        "D - " + alternativeD;

                InsertQuestion(db, q, Integer.parseInt(solution), Integer.parseInt(id));
            }
        }
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_QUESTIONS);
        database.execSQL(TABLE_RANK);
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

    public boolean InsertQuestion(SQLiteDatabase db, String question,  int correct, int id){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_CORRECT, correct);

        db.insert(NAME_QUESTIONS, null, contentValues);
        return true;
    }

    public boolean InsertRank( String nome, int rank){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, nome);
        contentValues.put(COLUMN_SCORE, rank);

        db.insert(NAME_RANK,null,contentValues);
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

    public List<Rank> GetRanks(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Rank> list = new ArrayList<Rank>();

        Cursor res = db.rawQuery("select * from Rank ORDER BY pontuacao desc",null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            Rank rank = cursorToRank(res);
            list.add(rank);
            res.moveToNext();
        }
        res.close();
        db.close();
        return list;
    }

    public boolean IsQuestionsEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<QuestionModel> list = new ArrayList<QuestionModel>();

        Cursor res =  db.rawQuery( "select COUNT(_id) from Perguntas ", null );
        res.moveToFirst();

        if(res.getInt(0) == 0)
        {

            res.close();

            db.close();
            return true;
        }

        res.close();

        db.close();
        return true;
    }

    private QuestionModel cursorToQuestion(Cursor cursor) {
        QuestionModel question = new QuestionModel();
        question.setId(cursor.getLong(0));
        question.setPergunta(cursor.getString(1));
        question.setCorreta(cursor.getInt(2));
        return question;
    }

    private Rank cursorToRank(Cursor cursor){
        Rank rank = new Rank();
        rank.setNome(cursor.getString(0));
        rank.setRank(cursor.getInt(1));
        return rank;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
