package edu.ucsal.appdeprogresso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private String progresso = "0";
    private float progressoAtual = 0;
    private float progressoAtualEmPorcentagem = 0;
    private boolean visualizacaoEmPorcentagem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_progress);
        Button botaoAdicionar = (Button) findViewById(R.id.button_adicionar);
        botaoAdicionar.setOnClickListener(this);
        Button botaoSair = (Button) findViewById(R.id.button_sair);
        botaoSair.setOnClickListener(this);
        Button botaoAtualizar = (Button)  findViewById(R.id.button_atualizar);
        botaoAtualizar.setOnClickListener(this);

        GameProgress gameProgress = findViewById(R.id.gameProgress);
        gameProgress.setOnCircleClickListener(new GameProgress.OnCircleClickListener() {
            @Override
            public void onCircleClick() {
                trocarVisualizacaoDeProgresso();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String nomeJogoSalvo = sharedPreferences.getString("SAVED_NOME_JOGO", null);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

        TextView nomeJogo = findViewById(R.id.nome_jogo);
        nomeJogo.setText(nomeJogoSalvo);
        TextView totalFases = findViewById(R.id.total_fases);
        totalFases.setText(totalFasesSalvas);

        GameProgress gameProgress = findViewById(R.id.gameProgress);
        gameProgress.setTexto(progressoAtual + "/" + totalFasesSalvas);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_adicionar) {
            Intent i = new Intent(this, GameAddActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.button_sair) {
            finishAffinity();
        }
        if (v.getId() == R.id.button_atualizar) {
            progressoAtual++;
            if (!visualizacaoEmPorcentagem) {
                atualizarProgressoEmBarra(progressoAtual);
            } else {
                atualizarProgressoEmPorcentagem(progressoAtual);
            }
            SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
            String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

            int total = Integer.parseInt(totalFasesSalvas);
            progressoAtualEmPorcentagem = (100f / total) * progressoAtual;

            GameProgress gameProgress = findViewById(R.id.gameProgress);
            float progresso = progressoAtualEmPorcentagem / 100f;
            gameProgress.setProgresso(progresso);
        }
    }

    public void trocarVisualizacaoDeProgresso() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

        GameProgress gameProgress = findViewById(R.id.gameProgress);
        if (gameProgress.getTexto().contains("/")) {
            gameProgress.setTexto(progressoAtualEmPorcentagem+"%");
            visualizacaoEmPorcentagem = true;
        } else {
            gameProgress.setTexto(progressoAtual + "/" + totalFasesSalvas);
            visualizacaoEmPorcentagem = false;
        }
    }

    public void atualizarProgressoEmBarra(float progressoAtual) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");
        progresso = String.valueOf(progressoAtual);

        GameProgress gameProgress = findViewById(R.id.gameProgress);

        if (progressoAtual <= Integer.parseInt(totalFasesSalvas)) {
            gameProgress.setTexto(progresso + "/" + totalFasesSalvas);
        } else {
            Toast.makeText(this, "Progresso já foi completado totalmente!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void atualizarProgressoEmPorcentagem(float progressoAtual) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");
        progresso = String.valueOf(progressoAtual);

        int fasesTotal = Integer.parseInt(totalFasesSalvas);

        progressoAtualEmPorcentagem = (100/fasesTotal) * progressoAtual;

        GameProgress gameProgress = findViewById(R.id.gameProgress);

        if (progressoAtualEmPorcentagem <= 100) {
            gameProgress.setTexto(progressoAtualEmPorcentagem+"%");
        } else {
            Toast.makeText(this, "Progresso já foi completado totalmente!!", Toast.LENGTH_SHORT).show();
        }
    }
}