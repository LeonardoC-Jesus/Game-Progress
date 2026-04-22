package edu.ucsal.appdeprogresso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameProgress extends View {
    private int coordenadaX, coordenadaY;
    private int raioCirculo;
    private int corCirculo;
    private int corTexto;
    private String texto;
    private float progresso = 0f;
    private Paint paintCirculo;
    private Paint paintTexto;
    private Paint paintProgresso;

    public interface OnCircleClickListener{
        void onCircleClick();
    }

    private OnCircleClickListener listener;

    public void setOnCircleClickListener(OnCircleClickListener listener) {
        this.listener = listener;
    }

    public GameProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView,0,0);
        try {
            coordenadaX = array.getInt(R.styleable.CustomView_coordX, 0);
            coordenadaY = array.getInt(R.styleable.CustomView_coordY, 0);
            raioCirculo = array.getInt(R.styleable.CustomView_raio, 0);
            corCirculo = array.getInt(R.styleable.CustomView_cor_circulo, 0);
            corTexto = array.getInt(R.styleable.CustomView_cor_texto, 0);
            texto = array.getString(R.styleable.CustomView_texto);
        }
        finally {
            array.recycle();
        }

        paintCirculo = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCirculo.setColor(corCirculo);
        paintCirculo.setStyle(Paint.Style.STROKE);
        paintCirculo.setStrokeWidth(50f);

        paintTexto = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTexto.setTextAlign(Paint.Align.CENTER);
        paintTexto.setColor(corTexto);
        paintTexto.setTextSize(80);

        paintProgresso = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintProgresso.setColor(Color.RED);
        paintProgresso.setStyle(Paint.Style.STROKE);
        paintProgresso.setStrokeWidth(50f);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(coordenadaX, coordenadaY, raioCirculo, paintCirculo);

        float angulo = progresso * 360;
        paintProgresso.setColor(Color.GREEN);
        android.graphics.RectF oval =
                new android.graphics.RectF(coordenadaX - raioCirculo,
                        coordenadaY - raioCirculo,
                        coordenadaX + raioCirculo,
                        coordenadaY + raioCirculo);

        canvas.drawArc(oval, -90, angulo, false, paintProgresso);

        canvas.drawText(texto, coordenadaX, coordenadaY, paintTexto);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float toqueX = event.getX();
        float toqueY = event.getY();

        float coordCirculoX = 370;
        float coordCirculoY = 980;
        int raio = 250;

        float pontoDeClique = ((toqueX - coordCirculoX) * (toqueX - coordCirculoX))  +  ((toqueY - coordCirculoY) * (toqueY - coordCirculoY));

        if (pontoDeClique <= (raio*raio) && event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (listener != null)
                listener.onCircleClick();
        }
        return true;
    }

    public void setProgress(float progresso) {
        this.progresso = progresso;
        invalidate();
    }
    public float getProgresso() {
        return progresso;
    }

    public void setTexto(String novoTexto) {
        this.texto = novoTexto;
        invalidate();
    }

    public String getTexto() {
        return this.texto;
    }

}
