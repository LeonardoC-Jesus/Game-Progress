package edu.ucsal.appdeprogresso;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editNomeJogo;
    private EditText editTotalFases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_add);

        Button botaoSalvar = (Button) findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(this);
        Button botaoVoltar = (Button) findViewById(R.id.button_voltar);
        botaoVoltar.setOnClickListener(this);

        editNomeJogo = (EditText) findViewById(R.id.input_nome);
        editTotalFases = (EditText) findViewById(R.id.input_fase);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_salvar) {

            String nomeJogoInput = editNomeJogo.getText().toString();
            String totalFasesInput = editTotalFases.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("SAVED_NOME_JOGO", nomeJogoInput);
            editor.putString("SAVED_TOTAL_FASES", totalFasesInput);
            editor.apply();

            Toast.makeText(this, "Jogo salvo!!", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (v.getId() == R.id.button_voltar) {
            finish();
        }
    }
}