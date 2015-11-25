package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

/**
 * Created by mateuspalhares on 31/10/15.
 */
public class Question extends Activity implements View.OnClickListener, NomeFragment.nameDialogListener{


    private MySQLiteHelper dbHelper;
    private List<QuestionModel> questionsList;
    private QuestionModel question;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private TextView questionTxt;
    private EditText nomeRank;
    int rank = 0;
    Random rand = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perguntas);

        dbHelper = new MySQLiteHelper(this);

        //collecting all questions in the database
        questionsList = dbHelper.GetQuestions();
        Log.d("Debug", "There are " + questionsList.size() + " questions.");
        if (questionsList.isEmpty()){
            Log.e("Error", "Question List is empty");
        }

        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        questionTxt = (TextView) findViewById(R.id.txtQuestion);
        //creating listeners on the choose buttons
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        //choosing a question
        question = questionsList.get(rand.nextInt(questionsList.size()-1));
        //setting text for a question on the screen
        questionTxt.setText(question.getPergunta());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnA:
                if(question.getCorreta() == 1)
                    NextQuestion();
                else
                    GetRank();
                break;
            case R.id.btnB:
                if(question.getCorreta() == 2)
                    NextQuestion();
                else
                    GetRank();
                break;
            case R.id.btnC:
                if(question.getCorreta() == 3)
                    NextQuestion();
                else
                    GetRank();
                break;
            case R.id.btnD:
                if(question.getCorreta() == 4)
                    NextQuestion();
                else
                    GetRank();
                break;
        }
    }

    private void NextQuestion(){
            questionsList.remove(question);
            if (!questionsList.isEmpty()) {
                rank++;
                int sizelist = questionsList.size() - 1;
                int choice = 0;
                if (sizelist != 0) choice = rand.nextInt(sizelist);
                question = questionsList.get(choice);
                questionTxt.setText(question.getPergunta());
            }else{
                Log.d("Debug", "Winner!");
                GetRank();
            }
    }

    private void GetRank(){
        NomeFragment nome = new NomeFragment();
        nome.show(getFragmentManager(), "nome");
    }


    @Override
    public void onEnviarPressed(DialogFragment dialog) {
        nomeRank = (EditText) dialog.getDialog().findViewById(R.id.username);

        Intent intent = new Intent(Question.this, RankActivity.class);

        //Toast.makeText(this, nomeRank.getText().toString(),Toast.LENGTH_SHORT);

        if(nomeRank.getText().toString().isEmpty()){
            onCancelarPressed();
        }
        else{
            intent.putExtra("nome", nomeRank.getText().toString());
            intent.putExtra("rank",rank);
            Log.d("Debug", "sending");
            startActivity(intent);
        }
    }

    @Override
    public void onCancelarPressed() {
        Intent intent = new Intent(Question.this, MainActivity.class);
        intent.putExtra("flag",1);
        startActivity(intent);
    }
}
