package edu.ucsal.appdeprogresso;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private float progressoAtual;
    private float progressoAtualEmPorcentagem;
    private float progressoAnterior;
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
        boolean reiniciaProgresso = sharedPreferences.getBoolean("SAVED_REINICIA_PROGRESSO", false);

        TextView nomeJogo = findViewById(R.id.nome_jogo);
        nomeJogo.setText(nomeJogoSalvo);
        TextView totalFases = findViewById(R.id.total_fases);
        totalFases.setText(totalFasesSalvas);

        GameProgress gameProgress = findViewById(R.id.gameProgress);
        if (reiniciaProgresso) {
            progressoAtual = 0;
            progressoAtualEmPorcentagem = 0;
            gameProgress.setProgresso(0f);
        }

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
            if (!visualizacaoEmPorcentagem) {
                atualizarProgressoEmBarra();
            } else {
                atualizarProgressoEmPorcentagem();
            }
            SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
            String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

            int total = Integer.parseInt(totalFasesSalvas);
            progressoAtualEmPorcentagem = (100 * progressoAtual) / total;

            GameProgress gameProgress = findViewById(R.id.gameProgress);
            float valorIncial = gameProgress.getProgresso();
            float valorFinal = progressoAtualEmPorcentagem / 100f;

            ValueAnimator animator = ValueAnimator.ofFloat(valorIncial, valorFinal);
            animator.setDuration(500);
            animator.addUpdateListener(animation -> {
                float progressoAnimado = (float) animation.getAnimatedValue();
            gameProgress.setProgresso(progressoAnimado);
            });
            animator.start();
        }
    }

    @SuppressLint("DefaultLocale")
    public void trocarVisualizacaoDeProgresso() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

        GameProgress gameProgress = findViewById(R.id.gameProgress);
        if (gameProgress.getTexto().contains("/")) {
            gameProgress.setTexto(String.format("%.0f", progressoAtualEmPorcentagem)+"%");
            visualizacaoEmPorcentagem = true;
        } else {
            gameProgress.setTexto(progressoAtual + "/" + totalFasesSalvas);
            visualizacaoEmPorcentagem = false;
        }
    }

    @SuppressLint("DefaultLocale")
    public void atualizarProgressoEmBarra() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

        GameProgress gameProgress = findViewById(R.id.gameProgress);

        if (progressoAtual < Integer.parseInt(totalFasesSalvas)) {
            progressoAnterior = progressoAtual;
            progressoAtual++;
            gameProgress.setTexto(String.format("%.0f", progressoAtual) + "/" + totalFasesSalvas);
        } else {
            Toast.makeText(this, "Progresso já foi completado totalmente!!", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("DefaultLocale")
    public void atualizarProgressoEmPorcentagem() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String totalFasesSalvas = sharedPreferences.getString("SAVED_TOTAL_FASES", "0");

        int fasesTotal = Integer.parseInt(totalFasesSalvas);

        GameProgress gameProgress = findViewById(R.id.gameProgress);

        if (progressoAtualEmPorcentagem < 100) {
            progressoAnterior = progressoAtual;
            progressoAtual++;
            progressoAtualEmPorcentagem = (100 * progressoAtual) / fasesTotal;
            gameProgress.setTexto(String.format("%.0f", progressoAtualEmPorcentagem) + "%");
        } else {
            Toast.makeText(this, "Progresso já foi completado totalmente!!", Toast.LENGTH_SHORT).show();
        }
    }
}