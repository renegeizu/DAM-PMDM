package com.rngds.pong;

	import android.app.Activity;
	import android.os.Bundle;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.widget.TextView;

public class Pong extends Activity {

    private static final int MENU_NUEVO_JUEGO=1;
    private static final int MENU_CONTINUAR=2;
    private static final int MENU_SALIR=3;
    private PongThread hiloJuego;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong);
        final PongView vistaPong = (PongView) findViewById(R.id.main);
        vistaPong.setEstado((TextView) findViewById(R.id.estado));
        vistaPong.setMarcador((TextView) findViewById(R.id.marcador));
        hiloJuego = vistaPong.getGameThread();
        if (savedInstanceState==null) {
            hiloJuego.setEstado(PongThread.ESTADO_LISTO);
        } else {
            hiloJuego.restaurarEstado(savedInstanceState);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hiloJuego.pausar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        hiloJuego.guardarEstado(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_NUEVO_JUEGO, 0, R.string.menu_juego_nuevo);
        menu.add(0, MENU_CONTINUAR, 0, R.string.menu_continuar);
        menu.add(0, MENU_SALIR, 0, R.string.menu_salir);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_NUEVO_JUEGO:
                hiloJuego.nuevoJuego();
                return true;
            case MENU_SALIR:
                finish();
                return true;
            case MENU_CONTINUAR:
                hiloJuego.continuar();
                return true;
        }
        return false;
    }

}