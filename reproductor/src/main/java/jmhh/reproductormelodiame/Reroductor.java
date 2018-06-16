package jmhh.reproductormelodiame;

import java.io.BufferedReader;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import org.json.*;

import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

//Creación de clase que extiende de SlidingFragmentActivity e implementa OnBufferingUpdateListener 
//para mostrar el buffer de las canciones online
public class Reroductor extends ActionBarActivity implements OnBufferingUpdateListener {
    private static final String PERMISSION = "publish_actions";
    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";
    public static boolean cargabuffer = true;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;
    private boolean canPresentShareDialog;
    private UiLifecycleHelper uiHelper;
    public static int j = 0;
    public static boolean empiezaale = false;
    public AdRequest adRequest2;
    public boolean finatras = false;
    public static Random rnd;
    public static int posale[];
    public static int posale2[];
    public boolean repitee;
    public static boolean aleatorioo;
    public static boolean cancela = false;
    public  RelativeLayout layout;
    public ImageView imagen;
    public static ImageView imagen2;
    public String cantante;
    public String titulo;
    public byte[] caratula;
    public String disco;
    public int duraciones;
    public static boolean norepitas = false;
    public static boolean descsem;
    public static int contdes = 1;
    public int resalta = 0;
    public int ce;
    public MediaMetadataRetriever metaRetriever;
    public static boolean onlinerror = false;
    public static boolean nofunciona = false;
    public boolean error = false;
    public static boolean crearbarra = false;
    public String result;
    public int cee = 0;
    public Cursor c;
    public Cursor b;
    public SQLiteDatabase db = null;
    public static int estado;
    public static Thread thread;
    public static int cpausa = 0;
    public static String holagrupo;
    public int grupo = 0;
    public static int estadoss;
    public static List<String> actualiza;
    public static String name2;
    public JSONObject item;
    public static int online = 0;
    public String comando = "command=findMusic&criteria=sandman";
    public int n = comando.length();
    public JSONObject array;
    public static File f;
    public static ArrayList<String> linea2;
    public static ArrayList<String> Grupo = new ArrayList<String>(1000);
    public static ArrayList<String> Tiempo = new ArrayList<String>(1000);
    final public static String parametro = "parametro1";
    public static NotificationManager notManager;
    public static NotificationManager nm;
    public static Notification notif;
    public String texto;
    public static int estados = 0;
    public static int posicion = 0;
    public static int estado2 = 0;
    public static ArrayList<String> linea = new ArrayList<String>(1000);
    public static ArrayList<String> sruta = new ArrayList<String>(1000);
    public static int seg = 0;
    public static int min = 0;
    public static int seg2 = 0;
    public static int min2 = 0;
    public static MediaPlayer reproSonido;
    public TextView text;
    public TextView text2;
    public static TextView text3;
    public static TextView text4;
    public ImageButton siguiente;
    public ImageButton repite;
    public ImageButton aleatorio;
    public Button sd;
    public Button busc;
    public Button melo;
    public ImageButton atras;
    public ImageButton para;
    public ImageButton play;
    public static ImageButton play2;
    public ListView lv;
    public TextView timee;
    public static TextView timee2;
    public TextView duracion;
    public static TextView duracion2;
    public SeekBar barra;
    public static SeekBar barra2;
    public static int lugar = 0;
    public static int progreso = 1000;
    public static MyCount counter;
    public MyCount2 counter2;
    public static int acancionessem = 0;
    public static int acarpeta = 0;
    protected static String ruta;
    private TweetHelper tweet_helper;
    private int TWITTER_AUTH = 1;
    public OnSeekBarChangeListener barChange;
    public RatingBar star;
    public int puntuacion;
    public sql myDbHelper;
    public EqInterface mService = null;
    private InterstitialAd interstitial;
    private String[] mDrawerTitles;
    private TypedArray mDrawerIcons;
    private ArrayList<Items> drawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Fragment fragment = null;

    //Método que se le llama para mostrar texto en el tablón de Facebook 
    private enum PendingAction {
        POST_STATUS_UPDATE, NONE
    }

    //Dialog que se le llama al modificar las estrellas de puntuación

    private void puntua() {
        new MaterialDialog.Builder(this)
                //.iconRes(R.drawable.star_off2)
                //.title("¿?")
                .content(getString(R.string.puntuar))
                .positiveText(R.string.si)
                .negativeText(R.string.cancelar)
                .positiveColorRes(android.R.color.white)
                .negativeColorRes(android.R.color.white)
                .titleGravity(GravityEnum.CENTER)
               // .titleColorRes(android.R.color.white)
                .contentColorRes(android.R.color.white)
                .backgroundColorRes(R.color.primary_color)
                .dividerColorRes(android.R.color.white)
                .positiveColor(Color.WHITE)
                .negativeColorAttr(Color.WHITE)
                .theme(Theme.DARK)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //Se le quita los elementos de url para dejar solo el nombre de la canción si es online

                        String cancion2 = "";
                        for (int p = 0; p <= Reroductor.sruta.get(posicion).length(); p++) {

                            try {
                                if ((Reroductor.sruta.get(posicion).substring(p, p + 1).equals("'"))) {
                                    cancion2 = cancion2 + "%";

                                } else {
                                    cancion2 = cancion2 + Reroductor.sruta.get(posicion).substring(p, p + 1);
                                }
                            } catch (Exception e) {

                            }
                        }


                        db = myDbHelper.getReadableDatabase();


                        //Se recorre la base de datos comprobado las valoraciones de las canciones


                        boolean sem2 = false;
                        ;
                        Cursor b = db.rawQuery("SELECT cancion FROM valoraciones", null);
                        b.moveToFirst();
                        try {
                            if (cancion2.equals(b.getString(0))) {
                                sem2 = true;
                            }
                        } catch (Exception e) {

                        }
                        while (b.moveToNext()) {
                            if (cancion2.equals(b.getString(0))) {
                                sem2 = true;
                            }


                        }

                        b.close();


                        if (sem2 == false) {
                            //Si el nombre no existe en la base de datos, se llama al thread que guarda la valoracion

                            new threadguardar().execute();


                        } else {
                            new threadguardar2().execute();

                            sem2 = false;
                        }
                    }



                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                })
                .show();
    }



    //Dialog que se le llama al pulsar en el botón de redes sociales


    private void twittface() {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.dia_log)
                .title(getString(R.string.tweetoface))
                .content(getString(R.string.publicar))
                .positiveText("Twitter")
                .neutralText(getString(R.string.cancelar))
                .negativeText("Facebook")
                .positiveColorRes(android.R.color.white)
                .negativeColorRes(android.R.color.white)
                //.neutralColor(android.R.color.white)
                .titleGravity(GravityEnum.CENTER)
                         .titleColorRes(android.R.color.white)
                .contentColorRes(android.R.color.white)
                .backgroundColorRes(R.color.primary_color)
                .dividerColorRes(android.R.color.white)
                .positiveColor(Color.WHITE)
                .negativeColorAttr(Color.WHITE)
                .theme(Theme.DARK)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        try {

                            // Validamos login de nuevo.
                            validar_login();

                            // Obtenemos texto a mostrar
                            String texto;
                            if (titulo != null) {

                                texto = getString(R.string.estar_escuchando) + " " + titulo + " " + getString(R.string.de) + " "
                                        + cantante;

                            } else {
                                //En el caso de que en los metadatos no exista ni título ni canción, twitea el nombre
                                //del archivo
                                String poner = sruta.get(posicion).substring(0,
                                        sruta.get(posicion).length() - 4);

                                texto = getString(R.string.estar_escuchando) + " " + poner;

                            }


                            // Enviamos tweet y obtenemos estado de envio, mostramos si se ha twiteado o no.
                            boolean twt_snd_status = tweet_helper.enviar_Tweet(texto);

                            if (twt_snd_status) {
                                mostrarMensaje(getString(R.string.tweet));
                            } else {
                                mostrarMensaje(getString(R.string.no_tweet));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //Comprueba si la sesión de Facebook está abierta y así publicar, en caso contrario
                        //se habre un view para meter los datos e iniciar sesión
                        Session session = Session.getActiveSession();

                        if (session != null && session.isOpened()) {


                            onClickPostStatusUpdate();
                        } else {

                            onCreateView(null, controlsContainer, null);

                        }
                    }
                })
                .show();
    }



    //Se ejecuta al mantener pulsado el botón de atrás y así poder rebobinar hacia el punto que se quiera

    public class MyCount2 extends CountDownTimer {

        public MyCount2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
//Le vamos restando un segundo al progreso y lo mostramos en el tiempo y tambien lo metemos en porcentaje al
            //SeekBar
            barra.setProgress((int) (((float) progreso / duraciones) * 100));
            if (finatras == false) {
                progreso = progreso - 1000;
                if (seg2 < 10) {

                    duracion.setText(min2 + ":0" + seg2);
                } else {
                    duracion.setText(min2 + ":" + seg2);
                }

                seg2--;
                if (seg2 == -1) {
                    min2--;
                    seg2 = 59;
                }
                if (min2 <= 0 && seg2 <= 0) {
                    duracion.setText("0" + ":00");
                    min2 = 0;
                    seg2 = 0;
                    finatras = true;
                }
            }
        }

    }

    //CountDownTimer que cada segundo lo va mostrando en el tiempo que va pasando y también
    //lo muestra en porcentaje en el SeekBar
    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
            //Al acabar de contar pone todas las variables a cero,deshabilita e SeekBar y muestra la imagen por defecto 
            ponercero2();
            text.setText("");
            text2.setText("");

//Cancela la notificación de la barra de notificaciones
            notManager.cancel(1);


//Comprueba si está activado la reproducción aleatoria y si es así verifica si el número de la cancion actual
            // es menor que las totales y si la longitud del array con las posiciones de las canciones aleatorias
            //no está vacio
            if (aleatorioo == true) {
                try {
                    if (j < estadoss - 1 && posale.length != 0) {

                        j++;
                        posicion = posale[j];

                        try {
//Comprueba si es una cancion online y si la posición de reproducción actual es menor que las posiciones totales
                            //de las canciones
                            if (posicion < estadoss
                                    && linea.get(posicion)
                                    .substring(
                                            linea.get(posicion)
                                                    .indexOf("h"), 4)
                                    .equals("http")) {

                                cargabuffer = true;
                                //Se comprueba si hay acceso a internet
                                ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo redes = connec
                                        .getActiveNetworkInfo();
                                boolean comprueba = false;
                                if (redes != null && redes.isConnected()) {
                                    comprueba = true;
                                }
                                barra.setSecondaryProgress(0);
//Si no hay acceso a internet se ponen todas las variables a cero, deshabilita SeekBar y la notificación,
                                //se para la reproducció y se llama al thread de reproducir
                                if (comprueba == false) {

                                    mostrarMensaje(getString(R.string.sin_red));
                                    try {
                                        text.setText("");
                                        text2.setText("");
                                        reproSonido.stop();
                                        counter.cancel();
                                        posicion = 0;
                                        estados = 0;
                                        play.setImageResource(R.drawable.play_1);
                                        notManager.cancel(1);
                                        ponercero2();
                                        new empieza2().execute();
                                    } catch (Exception e) {

                                    }
                                } else {
//Si hay internet se ponen en false y en cero las variables de comprobacción de reproducción 
                                    // y se llama al thread de reproducir								
                                    estados = 0;
                                    ponercero();
                                    new empieza2().execute();


                                }

                            } else {
                                //Si no es una canción online y la posición de reproducción actual es mayor o igual
                                //que las posiciones totales de las canciones se ponen en false 
                                //y en cero las variables de comprobacción de reproducción 
                                if (posicion >= estadoss) {
                                    play.setImageResource(R.drawable.play_1);
                                    ponercero();
                                }
                            }

                        } catch (Exception e) {
                            //Si se produce algún error se ponen a cero las variables de comprobacción de reproducción 
                            //y se llama al thread de reproducir
                            cargabuffer = false;
                            ponercero();
                            new empieza2().execute();

                        }

                    }
//Se comprueba si está activada la reproducción en bucle y se verifica si el número de la cancion actual
                    // es igual que las totales y si es así se vuelve a poner la primera canción para reproducir
                    else if (j < estadoss && repitee == true) {
                        estadoss = linea.size();
                        ponercero();
                        j = 0;
                        posicion = posale[j];

                        new empieza2().execute();
//En el caso de que la posición de la canción que se está reproduciendo sea el penúltimo de las canciones totales,
                        //se vuelve a crear un array con las posiciones aleatorias de las canciones y vuelve a
                        //reproduccir desde el principio
                    } else if (posicion < linea.size() - 1) {
                        try {

                            Reroductor.estadoss = Reroductor.linea.size();
                            Reroductor.posale = new int[Reroductor.linea.size()];
                            int k = posicion;

                            Reroductor.j = k + 1;
                            for (int i = 0; i <= k; i++) {
                                Reroductor.posale[i] = i;
                            }

                            for (int i = k + 1; i < Reroductor.posale.length; i++) {
                                int localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);

                                Reroductor.posale[i] = localizado;
                                for (int t = 0; t != i; t++) {

                                    while (Reroductor.posale[i] == Reroductor.posale[t]) {
                                        localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);
                                        if (t != 0) {
                                            for (int p = 0; p != t; p++) {
                                                while (localizado == Reroductor.posale[p]) {
                                                    localizado = (int) (rnd
                                                            .nextDouble() * Reroductor.estadoss);
                                                }
                                            }
                                        }
                                        Reroductor.posale[i] = localizado;

                                        Reroductor.posicion = Reroductor.posale[Reroductor.j];

                                    }

                                }
                            }

                        } catch (Exception a) {

                        }
                        try {

                            if (posicion < estadoss
                                    && linea.get(posicion)
                                    .substring(
                                            linea.get(posicion)
                                                    .indexOf("h"), 4)
                                    .equals("http")) {

                                ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                                cargabuffer = true;
                                NetworkInfo redes = connec
                                        .getActiveNetworkInfo();
                                boolean comprueba = false;
                                if (redes != null && redes.isConnected()) {
                                    comprueba = true;
                                }

                                if (comprueba == false) {
                                    mostrarMensaje(getString(R.string.sin_red));
                                    try {
                                        text.setText("");
                                        text2.setText("");
                                        reproSonido.stop();
                                        counter.cancel();
                                        posicion = 0;
                                        estados = 0;
                                        play.setImageResource(R.drawable.play_1);
                                        ponercero2();
                                        notManager.cancel(1);
                                        new empieza2().execute();
                                    } catch (Exception c) {

                                    }
                                } else {
                                    ponercero();
                                    new empieza2().execute();
                                }

                            } else {
                                if (posicion >= estadoss) {
                                    play.setImageResource(R.drawable.play_1);
                                    ponercero();
                                    posicion = 0;
                                    Reroductor.estadoss = 0;
                                    Reroductor.posale = new int[0];
                                    j = 0;
                                }
                            }

                        } catch (Exception b) {
                            //Si se produce algún error se ponen a cero las variables de comprobacción de reproducción 
                            //y se llama al thread de reproducir
                            cargabuffer = false;
                            ponercero();
                            new empieza2().execute();

                        }
                        //En caso contrario se pone todo a cero y false
                    } else {
                        play.setImageResource(R.drawable.play_1);
                        ponercero();
                        j = 0;
                        empiezaale = false;
                        posicion = 0;
                        Reroductor.estadoss = 0;
                        Reroductor.posale = new int[0];

                    }

                } catch (Exception e) {
//Si se produce algún error en los pasos anteriores se vuelve a crear un array con las posiciones de reproducción
                    //aleatorias y vuelve a hacer el proceso de reproducción
                    if (posicion < linea.size() - 1) {
                        try {

                            Reroductor.estadoss = Reroductor.linea.size();
                            Reroductor.posale = new int[Reroductor.linea.size()];
                            int k = posicion;

                            Reroductor.j = k + 1;
                            for (int i = 0; i <= k; i++) {
                                Reroductor.posale[i] = i;
                            }

                            for (int i = k + 1; i < Reroductor.posale.length; i++) {
                                int localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);

                                Reroductor.posale[i] = localizado;
                                for (int t = 0; t != i; t++) {

                                    while (Reroductor.posale[i] == Reroductor.posale[t]) {
                                        localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);
                                        if (t != 0) {
                                            for (int p = 0; p != t; p++) {
                                                while (localizado == Reroductor.posale[p]) {
                                                    localizado = (int) (rnd
                                                            .nextDouble() * Reroductor.estadoss);
                                                }
                                            }
                                        }
                                        Reroductor.posale[i] = localizado;

                                        Reroductor.posicion = Reroductor.posale[Reroductor.j];
                                        j++;
                                    }

                                }
                            }

                        } catch (Exception a) {

                        }
                        try {

                            if (posicion < estadoss
                                    && linea.get(posicion)
                                    .substring(
                                            linea.get(posicion)
                                                    .indexOf("h"), 4)
                                    .equals("http")) {

                                cargabuffer = true;
                                ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo redes = connec
                                        .getActiveNetworkInfo();
                                boolean comprueba = false;
                                if (redes != null && redes.isConnected()) {
                                    comprueba = true;
                                }

                                if (comprueba == false) {
                                    mostrarMensaje(getString(R.string.sin_red));
                                    try {
                                        text.setText("");
                                        text2.setText("");
                                        reproSonido.stop();
                                        counter.cancel();
                                        posicion = 0;
                                        estados = 0;
                                        play.setImageResource(R.drawable.play_1);
                                        ponercero2();
                                        notManager.cancel(1);
                                        new empieza2().execute();
                                    } catch (Exception c) {

                                    }
                                } else {
                                    ponercero();
                                    new empieza2().execute();
                                }

                            } else {
                                if (posicion >= estadoss) {
                                    play.setImageResource(R.drawable.play_1);
                                    ponercero();
                                    posicion = 0;
                                    Reroductor.estadoss = 0;
                                    Reroductor.posale = new int[0];
                                    j = 0;
                                }
                            }

                        } catch (Exception b) {
                            //Si se produce algún error se ponen a cero las variables de comprobacción de reproducción 
                            //y se llama al thread de reproducir
                            cargabuffer = false;
                            ponercero();
                            new empieza2().execute();

                        }
                        //Si la reproducción en bucle está desactivada se para la reproducción
                    } else if (repitee == false) {
                        play.setImageResource(R.drawable.play_1);
                        ponercero();
                        posicion = 0;
                        Reroductor.estadoss = 0;
                        Reroductor.posale = new int[0];
                        j = 0;
                        //Si la reproducción en bucle está activada vuelve a realizar la reproducción
                    } else if (repitee == true) {
                        estadoss = linea.size();
                        ponercero();
                        empiezaale = true;
                        estadoss = linea.size();
                        posale = new int[estadoss];
                        j = 0;

                        for (int u = 0; u < estadoss; u++) {
                            int localizado = (int) (rnd.nextDouble() * estadoss);
                            posale[u] = localizado;

                            for (int t = 0; t != u; t++) {

                                while (posale[u] == posale[t]) {
                                    localizado = (int) (rnd.nextDouble() * estadoss);
                                    if (t != 0) {
                                        for (int p = 0; p != t; p++) {
                                            while (localizado == posale[p]) {
                                                localizado = (int) (rnd
                                                        .nextDouble() * estadoss);
                                            }
                                        }
                                    }

                                    posale[u] = localizado;

                                }

                            }
                        }

                        posicion = posale[j];

                        new empieza2().execute();

                    }
                }
//Si la reproduccíón aleatoria está desactivada pasaría a la reproducción de la siguiente canción
            } else {
                estadoss = linea.size();
                posicion++;
                try {

                    if (posicion < estadoss
                            && linea.get(posicion)
                            .substring(
                                    linea.get(posicion).indexOf("h"), 4)
                            .equals("http")) {

                        cargabuffer = true;
                        ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                .getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo redes = connec.getActiveNetworkInfo();
                        boolean comprueba = false;
                        if (redes != null && redes.isConnected()) {
                            comprueba = true;
                        }

                        if (comprueba == false) {
                            mostrarMensaje(getString(R.string.sin_red));
                            try {
                                text.setText("");
                                text2.setText("");
                                reproSonido.stop();
                                counter.cancel();
                                posicion = 0;
                                estados = 0;
                                play.setImageResource(R.drawable.play_1);
                                ponercero2();
                                notManager.cancel(1);
                                new empieza2().execute();
                            } catch (Exception e) {

                            }
                        } else {

                            ponercero();
                            new empieza2().execute();
                        }

                    } else {
                        if (posicion >= estadoss) {
                            play.setImageResource(R.drawable.play_1);
                            ponercero();
                        }
                        if (repitee == true) {

                            barra.setProgress(0);
                            barra.setSecondaryProgress(0);
                            cargabuffer = false;
                            progreso = 0;

                            posicion = 0;
                            estado2 = 0;

                            new empieza2().execute();
                        }
                    }

                } catch (Exception e) {

                    cargabuffer = false;
                    ponercero();
                    new empieza2().execute();

                }
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
//Va actualizando cada segundo incrementándolo en el tiempo y en el SeekBar 
            barra.setProgress((int) (((float) progreso / duraciones) * 100));
            progreso = progreso + 1000;
            if (seg2 < 10) {
                duracion.setText(min2 + ":0" + seg2);
            } else {
                duracion.setText(min2 + ":" + seg2);
            }

            seg2++;
            if (seg2 == 60) {
                min2++;
                seg2 = 0;
            }
            if (min2 >= min && seg2 >= seg) {

                if (seg < 10) {
                    duracion.setText("" + min + ":" + "0" + seg);
                } else {
                    duracion.setText("" + min + ":" + seg);
                }

            }
        }

    }

    //Cuando se pulsa el botón de regreso, se sale de la aplicación y elimina las notificaiones existentes
    //y para la reproducción
    public void onBackPressed() {


        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{

        if (layout.getVisibility() == View.VISIBLE) {
            try {

                counter.cancel();
                reproSonido.stop();
                notManager.cancel(1);

            } catch (Exception e) {

            }
            aleatorioo = false;
            repitee = false;
            // llama a mconnection para reestablecer el ecualizador a su posición normal
            bindService(new Intent(EqService.class.getName()),
                    mConnection, Context.BIND_AUTO_CREATE);
            //finaliza la aplicación
            finish();
        } else {
            layout.setVisibility(View.VISIBLE);
            fragment = null;
        }

    }
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // First, we start the service (if it wasn't started on boot)
        startService(new Intent(EqService.class.getName()));

        //Se asigna y carga la publicidad intersticial


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-8831350722190477/8350482349");


        adRequest2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("TEST_DEVICE_ID")
                .build();


//uiHelper  activa y guarda la sesion en Facebook
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        //savedInstanceState es un bundle que recoge los datos de session de Facebook
        if (savedInstanceState != null) {
            String name = savedInstanceState
                    .getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
//se asigna el layout
        setContentView(R.layout.activity_reroductor);
        //se asigna el toolbar y se aplica
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        //  toolbar.setTitle("Reproductor Melodiame");

        setSupportActionBar(toolbar);

//se asignan los elementos del menu lateral junto con sus iconos
        mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
        mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        drawerItems = new ArrayList<Items>();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        for (int i = 0; i < mDrawerTitles.length; i++) {
            drawerItems.add(new Items(mDrawerTitles[i], mDrawerIcons.getResourceId(i, -(i + 1))));
        }

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {


        };
        //Se le añade un pie al menú lateral
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer,
                mDrawerList, false);
        mDrawerList.addFooterView(footer, null, true); // true = clickable


        //Se le asigna al menú lateral u ancho de 430n
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        lp.width = 430;
        mDrawerList.setLayoutParams(lp);

        // se le asigna el adapter al menú
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), drawerItems));
        // llama al setonclick.... cuando se pulse un elemento de la lista
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//Se le asigna el botón para abrir el menú
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //La barra de título se le añade uno personalizado
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.barratitulo);
        //Ayuda a controlar el flujo de música (Se escucha con mejor calidad)
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //hace que en las versiones de SDK mayores que la 9 se puedan usar en red los thread
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build();


//Se asigna el dialogo a cargar para cuando se tenga que dar los permisos a la aplicación
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        //Se asigna la clase TweetHelper que contiene las instrucciones necesarias para poder twitear
        tweet_helper = new TweetHelper(this);




//Comprueba si hay una lista cargada anterior
        try {

            if (linea2.size() > 0) {
                online = 1;
                acancionessem = 1;

            }
        } catch (Exception a) {

        }
        //Desactiva el "Modo Estricto" que hace que la aplicación no arranque en versiones
        //superiores a la 2.3 si se ha desarrollado con un
        //target de nivel 8 o inferior usando threads
        StrictMode.setThreadPolicy(policy);
        //Se le pone fondo negro a la app
        layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setBackgroundColor(Color.BLACK);
//Se asignan todos los textos y se les da color el blanco, tambien se asignan botones y seekBar
        text = (TextView) findViewById(R.id.texto);
        text2 = (TextView) findViewById(R.id.texto2);
        text3 = (TextView) findViewById(R.id.texto);
        text4 = (TextView) findViewById(R.id.texto2);
        timee = (TextView) findViewById(R.id.tiempo);
        timee2 = (TextView) findViewById(R.id.tiempo);
        text.setTextColor(Color.WHITE);
        text2.setTextColor(Color.WHITE);
        timee.setTextColor(Color.WHITE);
        timee2.setTextColor(Color.WHITE);
        barra = (SeekBar) findViewById(R.id.barra);
        barra2 = (SeekBar) findViewById(R.id.barra);
        duracion = (TextView) findViewById(R.id.duracion);
        duracion2 = (TextView) findViewById(R.id.duracion);
        duracion.setTextColor(Color.WHITE);
        duracion2.setTextColor(Color.WHITE);
        lv = (ListView) findViewById(android.R.id.list);
        barra.setEnabled(false);
        barra.setMax(99);
        para = (ImageButton) findViewById(R.id.para);
        play = (ImageButton) findViewById(R.id.empieza);
        play2 = (ImageButton) findViewById(R.id.empieza);
        siguiente = (ImageButton) findViewById(R.id.button2);
        atras = (ImageButton) findViewById(R.id.atras);
        imagen = (ImageView) findViewById(R.id.imagenn);
        imagen2 = (ImageView) findViewById(R.id.imagenn);
        repite = (ImageButton) findViewById(R.id.repitee);
        aleatorio = (ImageButton) findViewById(R.id.aleatorioo);
        star = (RatingBar) findViewById(R.id.estrella);

        //Se crea la base de datos, si no se a creado, la abre.

        try {
            myDbHelper = new sql(this);
            myDbHelper.createDataBase(this);
        } catch (IOException ioe) {
            throw new Error(getString(R.string.no_bbdd));
        }
        myDbHelper.openDataBase();


        //Si la activity se habre desde una notificación se finaliza el actual
        try {
            Bundle extras2 = getIntent().getExtras();

            int numero = extras2.getInt(Reroductor.parametro);
            if (numero == 1 || numero == 2) {
                finish();
            }
        } catch (Exception e) {

        }
        //Se ejecuta al mantener pulsado el botón de atras
        atras.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                // TODO Auto-generated method stub
                atras.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        //Cancela la cuenta hacia atras y empieza a contar normal hacia delante
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            finatras = false;
                            try {

                                counter2.cancel();
                                reproSonido.seekTo((duraciones / 100) * barra.getProgress());
                                counter = new MyCount(reproSonido.getDuration()
                                        - reproSonido.getCurrentPosition(),
                                        1000);
                                progreso = reproSonido.getCurrentPosition();
                                if (estados != 0) {
                                    counter.start();
                                }
                                if (min2 == 0 && seg2 <= 0) {
                                    min2 = 0;
                                    seg2 = 0;
                                }
                            } catch (Exception e) {
                            }
                            //Cancela la cuenta hacia delante y empieza hacia atras rebobinando
                        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            try {


                                counter.cancel();
                                counter2 = new MyCount2(reproSonido.getDuration()
                                        - barra.getProgress(), 100);

                                if (min2 == 0 && seg2 <= 0) {
                                    counter2.cancel();
                                } else {
                                    counter2.start();
                                }

                            } catch (Exception e) {

                            }
                        }
                        return false;

                    }
                });

                return true;

            }

        });
        //Se ejecuta al mantener pulsado el botón de siguiente
        siguiente.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                // TODO Auto-generated method stub
                siguiente.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        //Cancela la cuenta rapida hacia delante y empieza a contar normal
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            try {
                                counter.cancel();
                                reproSonido.seekTo((duraciones / 100) * barra.getProgress());
                                counter = new MyCount(reproSonido.getDuration()
                                        - reproSonido.getCurrentPosition(),
                                        1000);
                                progreso = reproSonido.getCurrentPosition();
                                if (estados != 0) {
                                    counter.start();
                                }
                                if (min2 >= min && seg2 >= seg) {
                                    min2 = min;
                                    seg2 = seg;
                                }

                            } catch (Exception e) {
                            }
                            //Cancela la cuenta normal hacia delante y empieza a contar rápido
                        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            try {
                                counter.cancel();
                                counter = new MyCount(reproSonido.getDuration()
                                        - barra.getProgress(), 100);

                                if (min2 >= min && seg2 >= seg) {
                                    if (min2 >= min && seg2 >= seg) {

                                        if (seg < 10) {
                                            duracion.setText("" + min + ":"
                                                    + "0" + seg);
                                        } else {
                                            duracion.setText("" + min + ":"
                                                    + seg);
                                        }

                                    }
                                    counter.cancel();
                                } else {
                                    counter.start();
                                }
                            } catch (Exception e) {

                            }
                        }
                        return false;

                    }
                });

                return true;

            }

        });
//Se ejecuta al modificarse le barra de progreso
        barChange = new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                counter.cancel();
            }

            @Override
            //Se ejecuta al paralizarse la barra de progreso
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                switch (seekBar.getId()) {

                    //Al pararse la barra de progreso se paraliza la reproducción y vuelve a iniciarse la canción por la 
                    //posición en la que se haya dejado la barra(Si no se ha pulsado el boton de stop o pausa)
                    case R.id.barra:

                        counter.cancel();

                        reproSonido.seekTo((duraciones / 100) * barra.getProgress());
                        counter = new MyCount(reproSonido.getDuration()
                                - reproSonido.getCurrentPosition(), 1000);
                        progreso = reproSonido.getCurrentPosition();
                        if (estados != 0) {
                            counter.start();
                        }
                        int cont2 = reproSonido.getCurrentPosition();
                        seg2 = 0;
                        min2 = 0;
                        while (cont2 >= 0) {
                            cont2 = cont2 - 1000;
                            seg2 = seg2 + 1;
                            if (seg2 == 60) {
                                min2 = min2 + 1;
                                seg2 = 0;
                            }
                        }
                        seg2--;
                        duracion.setText("" + min2 + ":" + seg2);

                }
            }

        };
        barra.setOnSeekBarChangeListener(barChange);
        //Se llama cuando se modifica las estrellas
        star.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                //llama al dialog de puntuar si se está reproduciendo una canción
                try {
                    if (reproSonido.isPlaying() != true) {
                      //  mostrarMensaje(getString(R.string.no_escucha));
                    } else {
                        puntuacion = (int) rating;
                        puntua();

                    }
                } catch (Exception a) {
                   // mostrarMensaje(getString(R.string.no_escucha));
                }


            }
        });

//llama a que cambie la pantalla según su orientación
        Configuration config = getResources().getConfiguration();

        onConfigurationChanged(config);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincroniza el estado despues de llamar a onRestoreInstanceState
        mDrawerToggle.syncState();
    }


    public void displayInterstitial() {
// If Ads esta cargado, muestra la publicidad  Interstitial.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    //Se ejecuta al pulsar el botón de play
    public void empieza(View view) throws IOException {
        //Comprueba si está activado la reproducción aleatoria 


        if (aleatorioo == true) {
            try {
                //Comprueba si ya estaba activada la variable de aleatoria y que haya una lista de reproduccion hecha
                //y si es así crea un array con las posiciones aleatorias
                if (empiezaale == false && linea.size() != 0) {
                    empiezaale = true;
                    estadoss = linea.size();
                    posale = new int[estadoss];
                    j = 0;

                    for (int u = 0; u < estadoss; u++) {
                        int localizado = (int) (rnd.nextDouble() * estadoss);
                        posale[u] = localizado;

                        for (int t = 0; t != u; t++) {

                            while (posale[u] == posale[t]) {
                                localizado = (int) (rnd.nextDouble() * estadoss);
                                if (t != 0) {
                                    for (int p = 0; p != t; p++) {
                                        while (localizado == posale[p]) {
                                            localizado = (int) (rnd
                                                    .nextDouble() * estadoss);
                                        }
                                    }
                                }

                                posale[u] = localizado;

                            }

                        }
                    }
//asigna a la posición de reproducción actual la posición cero del array
                    posicion = posale[j];
                }
//Si hay alguna lista de reproducción asignada se mete
                if (linea.size() != 0) {
                    int canciones = linea.size();
                    //Si la posicion actual de reproduccion es igual o mayor que el número de canciones,
                    //se pone a cero
                    if (posicion >= canciones) {
                        posicion = 0;

                    }

                    try {
                        try {
                            //Comprueba si es una cancIón online para poder activar el buffer y si es así se comprueba
                            //que haya conexión a internet para empezar a reproducir
                            if (linea
                                    .get(posicion)
                                    .substring(
                                            linea.get(posicion).indexOf("h"), 4)
                                    .equals("http")) {

                                cargabuffer = true;
                                ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo redes = connec
                                        .getActiveNetworkInfo();
                                boolean comprueba = false;
                                if (redes != null && redes.isConnected()) {
                                    comprueba = true;
                                }

                                if (comprueba == false) {
                                    mostrarMensaje(getString(R.string.sin_red));
                                    try {
                                        //se de deshabilita la barra, se para reproducción y el contador,
                                        //se pone todo a cero y se borra la notificación									
                                        text.setText("");
                                        text2.setText("");
                                        reproSonido.stop();
                                        counter.cancel();
                                        progreso = 0;
                                        posicion = 0;
                                        estados = 0;
                                        play.setImageResource(R.drawable.play_1);
                                        ponercero2();
                                        notManager.cancel(1);

                                    } catch (Exception e) {

                                    }
                                } else {
//Llama a la reproducción
                                    new empieza2().execute();

                                }

                            }
                        } catch (Exception e) {
                            //Si hubo algun error se desabilita el buffer y llama a la reproducción
                            cargabuffer = false;
                            new empieza2().execute();

                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        } else {
            //Si no está activado el aleatorio se comprueba que haya una lista de reproducción
            if (linea.size() != 0) {
                int canciones = linea.size();
                //Si la posicion actual de reproduccion es igual o mayor que el número de canciones,
                //se pone a cero
                if (posicion >= canciones) {
                    posicion = 0;

                }

                try {
                    try {
                        //Comprueba si es una cancIón online para poder activar el buffer y si es así se comprueba
                        //que haya conexión a internet para empezar a reproducir
                        if (linea.get(posicion)
                                .substring(linea.get(posicion).indexOf("h"), 4)
                                .equals("http")) {

                            cargabuffer = true;
                            ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo redes = connec.getActiveNetworkInfo();
                            boolean comprueba = false;
                            if (redes != null && redes.isConnected()) {
                                comprueba = true;
                            }

                            if (comprueba == false) {
                                mostrarMensaje(getString(R.string.sin_red));
                                try {
                                    //se de deshabilita la barra, se para reproducción y el contador,
                                    //se pone todo a cero y se borra la notificación
                                    text.setText("");
                                    text2.setText("");
                                    reproSonido.stop();
                                    counter.cancel();
                                    posicion = 0;
                                    estados = 0;
                                    play.setImageResource(R.drawable.play_1);
                                    ponercero2();
                                    notManager.cancel(1);

                                } catch (Exception e) {

                                }
                            } else {
//Llama a la reproducción
                                new empieza2().execute();


                            }

                        }
                    } catch (Exception e) {
                        //Si hubo algun error se desabilita el buffer y llama a la reproducción

                        cargabuffer = false;
                        new empieza2().execute();

                    }
                } catch (Exception e) {
                }
            }
        }
    }

    //Se activa al pulsar el botón de stop
    public void para(View view) {
        try {
            //Se ponen todas las variable a cero, se inhabilita la barra y se cancela la reproducción
            j = 0;
            empiezaale = false;
            cpausa = 0;
            text.setText("");
            text2.setText("");
            reproSonido.stop();
            norepitas = false;
            counter.cancel();
            posicion = 0;
            estados = 0;
            play.setImageResource(R.drawable.play_1);
            ponercero2();
            notManager.cancel(1);
            empiezaale = false;
            estadoss = 0;
            posale = new int[0];
        } catch (Exception e) {

        }
    }

    public void atras(View view) {
        //Se comprueba si está activado el aleatorio
        if (aleatorioo == true) {
//Si la posición del array es mayor que cero, se le resta 1 y se le asigna a la posición de reproducción
            if (j > 0) {
                j--;
                posicion = posale[j];
//Si la posición de reproducción es mayor o igual que cero que cero o tambien que sea menor o igual que 
                //penúltimo del total
                //de canciones, se para la reproducción y se ponen todas las variables a cero
                if (posicion >= 0 && posicion <= linea.size() - 1) {
                    try {

                        counter.cancel();
                        reproSonido.stop();
                    } catch (Exception e) {

                    }
                    ponercero2();
                    ponercero();

//Llama al método de reproducción
                    new clickatras().execute();

                }
            } else {
                //Si la posición del array es igual a cero y la posición de reproducción es mayor que cero 
                //o tambien que sea menor o igual que el penúltimo del total
                //de canciones, se para la reproducción y se ponen todas las variables a cero
                if (posicion > 0 && posicion <= linea.size() - 1) {
                    try {

                        counter.cancel();
                        reproSonido.stop();
                    } catch (Exception e) {

                    }
                    ponercero2();
                    ponercero();
                    posicion--;
                    //Llama al método de reproducción
                    new clickatras().execute();

                }
            }
        } else {
            //Si el aletorio no está activado y  la posición de reproducción es mayor que cero 
            //o tambien que sea menor o igual que el penúltimo del total
            //de canciones, se para la reproducción y se ponen todas las variables a cero
            if (posicion > 0 && posicion <= linea.size() - 1) {
                try {

                    counter.cancel();
                    reproSonido.stop();
                } catch (Exception e) {

                }
                ponercero2();
                ponercero();
                posicion--;
                //Llama al método de reproducción
                new clickatras().execute();

            }
        }
    }

    public void siguiente(View view) {
        if (aleatorioo == true) {
            try {
                //Al ser aleatorio se comprueba que la posición del array sea menor que el penúltimo de la lista total
                //y así incrementarlo y asignarselo a la próxima posición a reproducir
                if (j < estadoss - 1) {
                    j++;
                    posicion = posale[j];

                    try {
//En el caso de que la posición que acabamos de asignar no sea igual al total de posiciones y que haya alguna canción
                        //reproduciendose, se para la reprodución, se ponen las variables a cero y llama al método
                        //para reproducir la siguiente canción
                        if (posicion != estadoss && reproSonido.isPlaying()) {
                            try {

                                counter.cancel();
                                reproSonido.stop();
                            } catch (Exception e) {

                            }
                            ponercero2();
                            ponercero();

                            new empieza2().execute();

                        } else {
                            //En el caso de que la canción esté pausada, la cancela y pasa a reproducirse la
                            //siguiente canción
                            if (posicion != estadoss && estado2 == 1) {
                                try {

                                    counter.cancel();
                                    reproSonido.stop();
                                } catch (Exception b) {

                                }
                                ponercero2();
                                ponercero();

                                new empieza2().execute();

                            }
                        }
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {
//en el caso de que la posición actual sea menor que el penúltimo del total y se haya producido algún error se vuelve
                //a cerear un array con las posiciones aleatorias
                if (posicion < linea.size() - 1) {

                    try {

                        Reroductor.estadoss = Reroductor.linea.size();
                        Reroductor.posale = new int[Reroductor.linea.size()];
                        int k = posicion;

                        Reroductor.j = k + 1;
                        for (int i = 0; i <= k; i++) {
                            Reroductor.posale[i] = i;
                        }

                        for (int i = k + 1; i < Reroductor.posale.length; i++) {
                            int localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);

                            Reroductor.posale[i] = localizado;
                            for (int t = 0; t != i; t++) {

                                while (Reroductor.posale[i] == Reroductor.posale[t]) {
                                    localizado = (int) (rnd.nextDouble() * Reroductor.estadoss);
                                    if (t != 0) {
                                        for (int p = 0; p != t; p++) {
                                            while (localizado == Reroductor.posale[p]) {
                                                localizado = (int) (rnd
                                                        .nextDouble() * Reroductor.estadoss);
                                            }
                                        }
                                    }
                                    Reroductor.posale[i] = localizado;

                                    Reroductor.posicion = Reroductor.posale[Reroductor.j];

                                }

                            }
                        }

                    } catch (Exception a) {

                    }
                    try {
//comprueba si es una canción online para poner a true el buffer y comprobar si hay red
                        if (posicion < estadoss
                                && linea.get(posicion)
                                .substring(
                                        linea.get(posicion)
                                                .indexOf("h"), 4)
                                .equals("http")) {

                            cargabuffer = true;
                            ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo redes = connec.getActiveNetworkInfo();
                            boolean comprueba = false;
                            if (redes != null && redes.isConnected()) {
                                comprueba = true;
                            }
// en el caso de que no haya conexión se ponen todas las variables a cero, deshabilita la barra y las notificaciones
                            //y se llama al método de reproducir
                            if (comprueba == false) {
                                mostrarMensaje(getString(R.string.sin_red));
                                try {
                                    text.setText("");
                                    text2.setText("");
                                    reproSonido.stop();
                                    counter.cancel();
                                    posicion = 0;
                                    estados = 0;
                                    play.setImageResource(R.drawable.play_1);
                                    ponercero2();
                                    notManager.cancel(1);

                                    new empieza2().execute();
                                } catch (Exception c) {

                                }
                            } else {
                                // si hay conexión se ponen todas las variables a cero, deshabilita la barra y las notificaciones
                                //y se llama al método de reproducir
                                ponercero();

                                ponercero2();
                                text.setText("");
                                text2.setText("");
                                counter.cancel();
                                reproSonido.stop();
                                notManager.cancel(1);
                                new empieza2().execute();


                            }

                        }

                    } catch (Exception b) {
                        // si a habido un error se ponen todas las variables a cero, deshabilita la barra y las notificaciones
                        //y se llama al método de reproducir
                        ponercero2();
                        text.setText("");
                        text2.setText("");
                        counter.cancel();
                        reproSonido.stop();
                        notManager.cancel(1);
                        ponercero();
                        new empieza2().execute();


                    }
                }
            }

        } else {
            //Si no está activado el aleatorio se comprueba que la posición de reproducción actual sea menor
            //que el penúltimo del total y que la canción está reproduciéndose, para cancelarla, poner variables
            //a cero, deshabilitar la barra y comenzar la reprodución con la siguiente canción
            try {

                if (posicion < linea.size() - 1 && reproSonido.isPlaying()) {
                    try {

                        counter.cancel();
                        reproSonido.stop();
                    } catch (Exception e) {

                    }
                    ponercero2();
                    ponercero();
                    posicion++;
                    new empieza2().execute();

                } else {
                    //Si no está pausada la reproducción se comprueba que la posición de reproducción actual sea menor
                    //que el penúltimo del total y que la canción está reproduciéndose, para cancelarla, poner variables
                    //a cero, deshabilitar la barra y comenzar la reprodución con la siguiente canción
                    if (posicion < linea.size() - 1 && estado2 == 1) {
                        try {

                            counter.cancel();
                            reproSonido.stop();
                        } catch (Exception b) {

                        }
                        ponercero2();
                        ponercero();
                        posicion++;
                        new empieza2().execute();

                    }
                }
            } catch (Exception e) {

            }
        }
    }


    //Se activa al pulsar la reproducción en bucle ya acciona o desactiva este tipo de reproducción
    public void repite(View view) {
        if (repitee == false) {
            repitee = true;
            repite.setImageResource(R.drawable.repite_on);
        } else {
            repite.setImageResource(R.drawable.repite_off);
            repitee = false;
        }
    }

    //Se activa al pulsar el botón de redes sociales
    public void redes(View view) {
        ConnectivityManager connec = (ConnectivityManager) Reroductor.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo red = connec.getActiveNetworkInfo();
        try {
            //Comprueba que se está escuchando algo y si hay red para lanzar el dialog de de publicar en redes sociales
            if (reproSonido.isPlaying()) {
                if (red != null && red.isConnected()) {
                    twittface();
                  //  Dialog dialogo = twittface();
                    //dialogo.show();
                } else {
                    mostrarMensaje(getString(R.string.sin_red));
                }
            } else {
                mostrarMensaje(getString(R.string.no_escucha));
            }
        } catch (Exception a) {
            mostrarMensaje(getString(R.string.no_escucha));
        }

    }

    //Se activa al pulsar la reproducción en aleatoria ya acciona o desactiva este tipo de reproducción
    public void aleatorio(View view) {
        if (aleatorioo == false) {
            aleatorioo = true;
            aleatorio.setImageResource(R.drawable.aleatorio_on);
            try {
                if (reproSonido.isPlaying()) {
                    estadoss = linea.size();
                }
            } catch (Exception a) {

            }
        } else {
            aleatorio.setImageResource(R.drawable.aleatorio_off);
            aleatorioo = false;
        }
    }

    //Se llama al pulsar el botón de buscar (lupa) y llama a la clase que tiene el viewpagerindicator
    public void acanciones(View view) {
//Muestra la publicidad intersticial
        interstitial.loadAd(adRequest2);


        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });
        online = 1;
        resalta = 0;
        acancionessem = 1;
        Bundle guardar = new Bundle();
        guardar.putInt(parametro, 1);
        Intent myIntento = new Intent(view.getContext(), inicial.class);
        myIntento.putExtras(guardar);
        startActivityForResult(myIntento, 0);

    }


    //Crea las opciones de menú cogiendo de referencia lo que se ha creado en el menú de acivity_reproductor
    //situado en la carpeta menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_reroductor, menu);

        return true;
    }

    //Acciona el elemento que se a pulsado en el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.buscarid:

                //Cancela todo y se sale del reproductor
                try {
                    counter.cancel();
                    reproSonido.stop();
                    notManager.cancel(1);
                } catch (Exception e) {

                }
                finish();
                break;

        }
        return true;
    }

    //Se le llama para mostrar el mensaje en pantalla que se le haya pasado
    void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

    }


    //Hebra que es llamada para reproduccir una canción anterior de la lista
    public class clickatras extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            int canciones = linea.size();
            //Se pone la posicion de reproducción a 0 si es negativo o si es el mayor o igual que el número total
            //de canciones de a lista
            if (posicion < 0 || posicion >= canciones) {
                posicion = 0;
            }
            //Deshabilitan todos los botones para que no puedan interrumpir el proceso de la hebra
            cancela = true;
            play.setImageResource(R.drawable.reloj_arena);
            play.setEnabled(false);

            siguiente.setEnabled(false);
            atras.setEnabled(false);
            para.setEnabled(false);

            cee = 0;
            //Comprueba se es una canción online para accionar la carga del buffer
            try {
                if (linea
                        .get(posicion)
                        .substring(
                                linea.get(posicion).indexOf("h"), 4)
                        .equals("http")) {
                    cargabuffer = true;
                } else {
                    cargabuffer = false;
                }
            } catch (Exception p) {

            }
        }

        protected Void doInBackground(Void... arg0) {
            //Al comenzar la ejecución en segundo plano se rehaliza un bucle para que salte a la anterior canción
            //mientras la canción no exista o está dañado
            // TODO Auto-generated method stub
            while (cee == 0) {
                int canciones = linea.size();
                //Se comprueba que la posición actual sea menor que la total y que sea mayor o igual a cero
                if (posicion < canciones && posicion >= 0) {
                    //Se comprueba que exista el archivo o que sea online
                    File files = new File(linea.get(posicion));
                    if (files.exists() || cargabuffer == true) {
                        try {
//Se comprueba que no se esté reproduciendo nada
                            if (estado2 == 0) {

                                reproSonido = new MediaPlayer();
                                //Se adigna el buffer de la cancion a esta clase
                                reproSonido.setOnBufferingUpdateListener(Reroductor.this);
                                reproSonido.setDataSource(linea.get(posicion));
                                reproSonido.prepare();
//Se extraen los metadatos de la canción y se realiza el tiempo
                                metaRetriever = new MediaMetadataRetriever();
                                metaRetriever.setDataSource(linea.get(posicion));
                                titulo = metaRetriever
                                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                                disco = metaRetriever
                                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                                cantante = metaRetriever
                                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                                caratula = metaRetriever.getEmbeddedPicture();
                                seg = 0;
                                min = 0;
                                int cont = reproSonido.getDuration();
                                duraciones = reproSonido.getDuration();

                                while (cont >= 0) {
                                    cont = cont - 1000;
                                    seg = seg + 1;
                                    if (seg == 60) {
                                        min = min + 1;
                                        seg = 0;
                                    }
                                }
                                seg--;
                                //llama a la ejecución que no se realiza en segundo plano
                                publishProgress();

                            }
                            //Se comprueba que no se esté reproduciendo nada
                            if (estados == 0) {
                                crearbarra = true;
                                //llama a la ejecución que no se realiza en segundo plano
                                publishProgress();
                                //Activa que se está reproduciendo algo y continúa en el bucle
                                estados = 1;
                                cee = 1;
                            } else {
//Si se está reproducciendo algo avisa de ello y continúa en el bucle
                                cpausa = 1;

                                estados = 0;
                                onlinerror = true;
                                //llama a la ejecución que no se realiza en segundo plano
                                publishProgress();
                                cee = 1;
                            }

                        } catch (Exception e) {
//Si a habido algún error, se abisa de ello y si la canción acual es menor o igual que las totales,
                            //continúa en el bucle
                            if (canciones <= posicion) {
                                cee = 1;
                            }
                            nofunciona = true;
                            //llama a la ejecución que no se realiza en segundo plano
                            publishProgress();

                        }
                    }
                    //En el caso que no exista el archivo o que esté offline
                    //Se le resta una posición
                    else {
                        posicion--;
                    }

                }//Si la posición actual es mayor o igual que la total y que sea menor que cero 
                //llama a la ejecución que no se realiza en segundo plano
                else {
                    publishProgress();

                }

            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            //Si la posición actual es negativa, se continúa en el bucle y se pone todo a cero y se deshabilita la barra 
            // y las notificaciones activas se borran
            if (posicion < 0) {
                cee = 1;
                try {
                    text.setText("");
                    text2.setText("");
                    reproSonido.stop();
                    counter.cancel();
                    ponercero2();
                    estados = 0;
                    play.setImageResource(R.drawable.play_1);
                    atras.setImageResource(R.drawable.atras_1);
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);
                    notManager.cancel(1);
                } catch (Exception p) {
                    play.setImageResource(R.drawable.play_1);
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);
                    cee = 1;

                }
            }//En el caso de que la posicion actual sea positiva
            else {
                //Se mete si no ha habido ningún error
                if (crearbarra == false && onlinerror == false
                        && nofunciona == false) {
//Se asigna el tiempo
                    if (seg < 10) {
                        if (seg == -1) {
                            min--;
                            seg = 59;
                            timee.setText("" + min + ":" + seg);
                        } else {
                            timee.setText("" + min + ":" + 0 + seg);
                        }
                    } else {
                        timee.setText("" + min + ":" + seg);
                    }
//Si tiene metadatos el archivo se asigna, la carátula, grupo y nombre de la canción
                    if (titulo != null) {
                        text.setText(titulo + "-" + cantante);
                        text2.setText(disco);
                        try {
                            Bitmap bMap = BitmapFactory.decodeByteArray(caratula,
                                    0, caratula.length);
                            imagen.setImageBitmap(bMap);
                        } catch (Exception e) {
                            imagen.setImageResource(R.drawable.cor_chea);
                        }
                    } else {
                        //Si no tiene metadatos se pone el nombre del archivo
                        String poner = sruta.get(posicion).substring(0,
                                sruta.get(posicion).length() - 4);

                        text2.setText("");
                        text.setText(poner);
                        imagen.setImageResource(R.drawable.cor_chea);
                    }
                    barra.setEnabled(true);
                    estado2 = 1;
//Se crea la notificación y se le añade icono,texto y hora y que al  pulsar sobre el
                    //abra esta activity
                    String ns = Context.NOTIFICATION_SERVICE;
                    notManager = (NotificationManager) getSystemService(ns);

                    int icono = android.R.drawable.ic_media_play;
                    CharSequence textoEstado = text.getText();
                    long hora = System.currentTimeMillis();

                    Notification notif = new Notification(icono, textoEstado, hora);
                    Context contexto = getApplicationContext();
                    CharSequence titulo = "Reproductor Melodiame";
                    CharSequence descripcion = text.getText();

                    Intent notIntent = new Intent(contexto, Reroductor.class);

                    Bundle guardar = new Bundle();

                    guardar.putInt(parametro, 1);

                    notIntent.putExtras(guardar);
                    startActivityForResult(notIntent, 0);
                    PendingIntent contIntent = PendingIntent.getActivity(contexto,
                            0, notIntent, 0);

                    notif.setLatestEventInfo(contexto, titulo, descripcion,
                            contIntent);
                    notif.flags |= Notification.FLAG_NO_CLEAR;

                    notManager.notify(1, notif);


                }
                //Se mete si no ha habido ningún error inicia la cuenta de la reproducción
                if (crearbarra = true && onlinerror == false && nofunciona == false) {

                    crearbarra = false;
                    play.setImageResource(R.drawable.pausa_1);
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);
                    //Si se está reproduciendo algo (está en pausa), realiza una nueva cuenta a partir de la posición
                    //pausada
                    if (cpausa == 1) {

                        counter = new MyCount(reproSonido.getDuration()
                                - reproSonido.getCurrentPosition(), 1000);
                        cpausa = 0;
                        cee = 1;
                        counter.start();
                    }

                    try {
//Si no hay nada reproducciendose realiza una nueva cuenta y empieza la reproducción
                        if (norepitas == false) {

                            counter = new MyCount(duraciones, 1000);

                            counter.start();

                            norepitas = true;
                        }
                    } catch (Exception e) {

                    }

                    reproSonido.start();

                }//Si a habido algún error se para el contador y se pausa la reproducción
                if (onlinerror == true) {
                    onlinerror = false;
                    try {
                        counter.cancel();
                        play.setImageResource(R.drawable.play_1);
                        play.setEnabled(true);
                        siguiente.setEnabled(true);
                        atras.setEnabled(true);
                        para.setEnabled(true);
                        reproSonido.pause();
                    } catch (Exception e) {

                    }

                }
                //Si a habido algun error se ponen variables a cero, se resta una posición para que reproduzca otra canción 
                //anterior y se deshabilita la barra así como su progreso y el buffer
                if (nofunciona == true && onlinerror == false) {
                    nofunciona = false;
                    norepitas = false;
                    posicion--;
                    cpausa = 0;
                    text.setText("");
                    text2.setText("");
                    reproSonido.stop();
                    ponercero2();
                    estados = 0;
                    play.setEnabled(false);
                    siguiente.setEnabled(false);
                    atras.setEnabled(false);
                    para.setEnabled(false);
                }
            }
        }

        //Se ejecuta al finalizar el thread y el booleano cancela se pone a false
        protected void onPostExecute(Void result) {
            cancela = false;
        }
    }

    //Hebra que se ejecuta para reproducir una canción
    public class empieza2 extends AsyncTask<Void, Void, Void> {
        //Se ejecuta antes de comenzar la hebra
        protected void onPreExecute() {

            int canciones = linea.size();
            //Si la posición actual es negativa o es mayor o igual que la total, se pone a cero
            if (posicion < 0 || posicion >= canciones) {
                posicion = 0;
            }
//Se deshabilitan los botones para que no intrerrumpa la ejecución de la hebra
            cancela = true;
            play.setImageResource(R.drawable.reloj_arena);
            play.setEnabled(false);

            siguiente.setEnabled(false);
            atras.setEnabled(false);
            para.setEnabled(false);
//cee se inicializa a cero para el bucle de ejecución, por si da un error pasa a la siguiente canción
            cee = 0;
            try {
                //Se comprueba si es una canción online para activar la carga del buffer
                if (linea
                        .get(posicion)
                        .substring(
                                linea.get(posicion).indexOf("h"), 4)
                        .equals("http")) {
                    cargabuffer = true;
                } else {
                    cargabuffer = false;
                }
            } catch (Exception p) {

            }
        }

        protected Void doInBackground(Void... arg0) {

            // TODO Auto-generated method stub
//Ya dentro de la hebra se mete dentro del bucle por si da un error pasar a la siguiente canción
            while (cee == 0) {
                int canciones = linea.size();
                //Se mete si la posición actual es menor que el total de canciones
                if (posicion < canciones) {
                    File files = new File(linea.get(posicion));
                    //Se mete si la cancion existe o es una cancion online
                    if (files.exists() || cargabuffer == true) {
                        try {
//Se comprueba que no se esté reproducciendo nada
                            if (estado2 == 0) {

                                reproSonido = new MediaPlayer();
                                //Activa el buffer de la canción y prepara la cancion para su 
                                //reproducción
                                reproSonido.setOnBufferingUpdateListener(Reroductor.this);
                                try {
                                    reproSonido.setDataSource(linea.get(posicion));
                                    reproSonido.prepare();
                                } catch (IllegalArgumentException e1) {
                                    // TODO Auto-generated catch block

                                    e1.printStackTrace();
                                } catch (SecurityException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                } catch (IllegalStateException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }


                                //Se extraen los metadatos de la canción y se asigna el tiempo

                                try {
                                    metaRetriever = new MediaMetadataRetriever();
                                    metaRetriever.setDataSource(linea.get(posicion));
                                    titulo = metaRetriever
                                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                                    disco = metaRetriever
                                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                                    cantante = metaRetriever
                                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                                    caratula = metaRetriever.getEmbeddedPicture();
                                } catch (IllegalArgumentException e1) {
                                    // TODO Auto-generated catch block

                                    e1.printStackTrace();
                                }
                                seg = 0;
                                min = 0;
                                int cont = reproSonido.getDuration();
                                duraciones = reproSonido.getDuration();


                                while (cont >= 0) {
                                    cont = cont - 1000;
                                    seg = seg + 1;
                                    if (seg == 60) {
                                        min = min + 1;
                                        seg = 0;
                                    }
                                }
                                seg--;
                                publishProgress();

                            }


                            //Se comprueba que no se esté reproduciendo nada
                            if (estados == 0) {
                                crearbarra = true;
                                //llama a la ejecución que no se realiza en segundo plano
                                publishProgress();
                                //Activa que se está reproduciendo algo y continúa en el bucle
                                estados = 1;
                                cee = 1;


                            } else {
                                //Si se está reproducciendo algo avisa de ello y continúa en el bucle
                                cpausa = 1;

                                estados = 0;
                                onlinerror = true;
                                //llama a la ejecución que no se realiza en segundo plano
                                publishProgress();
                                cee = 1;
                            }

                        } catch (Exception e) {
                            //Si a habido algún error, se abisa de ello y si la canción acual es menor o igual que las totales,
                            //continúa en el bucle
                            if (canciones <= posicion) {
                                cee = 1;
                            }
                            nofunciona = true;
                            //llama a la ejecución que no se realiza en segundo plano
                            publishProgress();

                        }
                    }
                    //En el caso que no exista el archivo o que esté offline
                    //Se le suma una posición
                    else {
                        posicion++;
                    }

                } else {
                    //llama a la ejecución que no se realiza en segundo plano
                    publishProgress();

                }

            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            int canciones = linea.size();
            //Si la posición actual es menor o igual 
            //que la total se continúa en el bucle y se pone todo a cero y se deshabilita la barra 
            // y las notificaciones activas se borran
            if (canciones <= posicion) {
                cee = 1;
                try {
                    text.setText("");
                    text2.setText("");
                    reproSonido.stop();
                    counter.cancel();
                    cee = 1;
                    estados = 0;
                    play.setImageResource(R.drawable.play_1);
                    siguiente.setImageResource(R.drawable.sigui);
                    ponercero2();
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);
                    notManager.cancel(1);
                } catch (Exception p) {
                    play.setImageResource(R.drawable.play_1);
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);
                    cee = 1;

                }
            }

            //En el caso de que la posicion actual sea mayor que la total
            else {
                //Se mete si no ha habido ningún error
                if (crearbarra == false && onlinerror == false
                        && nofunciona == false) {

                    //Se asigna el tiempo
                    if (seg < 10) {

                        if (seg == -1) {
                            min--;
                            seg = 59;
                            timee.setText("" + min + ":" + seg);
                        } else {
                            timee.setText("" + min + ":" + 0 + seg);
                        }
                    } else {
                        timee.setText("" + min + ":" + seg);
                    }
                    //Si tiene metadatos el archivo se asigna, la carátula, grupo y nombre de la canción
                    if (titulo != null) {
                        text.setText(titulo + "-" + cantante);
                        text2.setText(disco);
                        try {
                            Bitmap bMap = BitmapFactory.decodeByteArray(caratula,
                                    0, caratula.length);
                            imagen.setImageBitmap(bMap);
                        } catch (Exception e) {
                            imagen.setImageResource(R.drawable.cor_chea);
                        }
                    } else {
//Si no tiene metadatos se pone el nombre del archivo
                        String poner = sruta.get(posicion).substring(0,
                                sruta.get(posicion).length() - 4);

                        text2.setText("");
                        text.setText(poner);
                        imagen.setImageResource(R.drawable.cor_chea);

                    }
                    barra.setEnabled(true);
                    estado2 = 1;
                    //Se crea la notificación y se le añade icono,texto y hora y que al  pulsar sobre el
                    //abra esta activity
                    String ns = Context.NOTIFICATION_SERVICE;
                    notManager = (NotificationManager) getSystemService(ns);

                    int icono = android.R.drawable.ic_media_play;
                    CharSequence textoEstado = text.getText();
                    long hora = System.currentTimeMillis();

                    Notification notif = new Notification(icono, textoEstado, hora);
                    Context contexto = getApplicationContext();
                    CharSequence titulo = "Reproductor Melodiame";
                    CharSequence descripcion = text.getText();

                    Intent notIntent = new Intent(contexto, Reroductor.class);

                    Bundle guardar = new Bundle();

                    guardar.putInt(parametro, 1);

                    notIntent.putExtras(guardar);
                    startActivityForResult(notIntent, 0);
                    PendingIntent contIntent = PendingIntent.getActivity(contexto,
                            0, notIntent, 0);

                    notif.setLatestEventInfo(contexto, titulo, descripcion,
                            contIntent);
                    notif.flags |= Notification.FLAG_NO_CLEAR;

                    notManager.notify(1, notif);

                }
                //Se mete si no ha habido ningún error inicia la cuenta de la reproducción

                if (crearbarra = true && onlinerror == false && nofunciona == false) {

                    crearbarra = false;
                    play.setImageResource(R.drawable.pausa_1);
                    play.setEnabled(true);
                    siguiente.setEnabled(true);
                    atras.setEnabled(true);
                    para.setEnabled(true);

                    //Si se está reproduciendo algo (está en pausa), realiza una nueva cuenta a partir de la posición
                    //pausada
                    if (cpausa == 1) {

                        counter = new MyCount(reproSonido.getDuration()
                                - reproSonido.getCurrentPosition(), 1000);
                        cpausa = 0;
                        cee = 1;
                        counter.start();
                    }

                    try {
                        //Si no hay nada reproducciendose realiza una nueva cuenta y empieza la reproducción
                        if (norepitas == false) {

                            counter = new MyCount(duraciones, 1000);

                            counter.start();

                            norepitas = true;
                        }
                    } catch (Exception e) {

                    }

                    reproSonido.start();

                }
                //Si a habido algún error se para el contador y se pausa la reproducción
                if (onlinerror == true) {
                    onlinerror = false;
                    try {
                        counter.cancel();
                        play.setImageResource(R.drawable.play_1);
                        play.setEnabled(true);
                        siguiente.setEnabled(true);
                        atras.setEnabled(true);
                        para.setEnabled(true);
                        reproSonido.pause();
                    } catch (Exception e) {

                    }

                }
                //Si a habido algun error se ponen variables a cero, se suma una posición para que reproduzca otra canción 
                //anterior y se deshabilita la barra así como su progreso y el buffer
                if (nofunciona == true && onlinerror == false) {
                    nofunciona = false;
                    norepitas = false;
                    canciones = linea.size();
                    posicion++;
                    barra.setEnabled(false);
                    cpausa = 0;
                    text.setText("");
                    text2.setText("");
                    reproSonido.stop();
                    estados = 0;
                    ponercero2();
                    play.setEnabled(false);
                    siguiente.setEnabled(false);
                    atras.setEnabled(false);
                    para.setEnabled(false);

                }
            }

        }

        //Al finalizar la hebra se pone cancela a false
        protected void onPostExecute(Void result) {
            cancela = false;
        }
    }


    public void validar_login() {
        // Verificar si existen claves alamacenadas en el telefono si no llama
        // al webview.
        if (!tweet_helper.verificar_logindata()) {
            mostrarMensaje(getString(R.string.claves));

            // Creamos intent y pasamos get_AuthenticationURL.
            Intent i = new Intent(Reroductor.this, TwitterWebActivity.class);
            i.putExtra("URL", tweet_helper.get_authenticationURL());
            startActivityForResult(i, TWITTER_AUTH);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            //Recoge los datos de facebook
            uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
            //Si se ha recibido algo, se obtienen los datos de twitter
            if (resultCode == Activity.RESULT_OK) {
                // Obtenemos oauth_verifier pasado por el webview.
                String oauthVerifier = (String) data.getExtras().get(
                        "oauth_verifier");
                Log.e("oauthVerifier ->", oauthVerifier);

                // Grabamos el valor de oauthVerifier en el shared preferences.
                tweet_helper.store_OAuth_verifier(oauthVerifier);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Facebook a partir de aqui

    //Método que se ejecuta al cambiar la orientaciónd la pantalla
    protected void onSaveInstanceState(Bundle outState) {
        //Lo que hace es que no se resetee el layout al cambiar de orientación la pantalla
        super.onSaveInstanceState(outState);
        //Se mantienen los datos de facebook
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    //Comprueba si hay una sesión activa de facebook y con los permisos dados antes de postear
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        if (pendingAction != PendingAction.NONE
                && (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
            new AlertDialog.Builder(Reroductor.this).setTitle(getString(R.string.cancelado))
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null).show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }

    }

    //Lo que hace es llamar al método para publicar en el tablón de facebook lo que se está escuchando
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;

        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {

            case POST_STATUS_UPDATE:

                postStatusUpdate();
                break;
        }
    }

    //Devuelve si el resultado de la publicación en facebook
    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

    //Comprueba si se ha publicado exitosamente en el muro de facebook
    private void showPublishResult(String message, GraphObject result,
                                   FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = getString(R.string.publicado);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this).setTitle(title).setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null).show();
    }

    //Se llama después de publicar en facebook
    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }


    //Publica en el muro de facebook lo que se está escuchando
    private void postStatusUpdate() {


        if (user != null && hasPublishPermission()) {

            String texto;
            if (titulo != null) {

                texto = getString(R.string.estar_escuchando) + " " + titulo + " " + getString(R.string.de) + " " + cantante;

            } else {

                String poner = sruta.get(posicion).substring(0,
                        sruta.get(posicion).length() - 4);

                texto = getString(R.string.estar_escuchando) + " " + poner;

            }

            final String message = texto;
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), message, place, tags,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message,
                                    response.getGraphObject(),
                                    response.getError());

                        }
                    });
            request.executeAsync();

        } else {
            //Si no hay ninguna sesión activa ni se tienen los permisos para publicar llama a que te pida que inicies sesion
            pendingAction = PendingAction.POST_STATUS_UPDATE;


        }
    }

    //Recoge los permisos de la sesión de facebook
    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null
                && session.getPermissions().contains("publish_actions");
    }

    //Comprueba que se han dado los permisos para publicar en facebook y si no es así, los pide
    private void performPublish(PendingAction action, boolean allowNoSession) {
        Session session = Session.getActiveSession();

        if (session != null) {

            pendingAction = action;
            if (hasPublishPermission()) {


                handlePendingAction();
                return;
            } else if (session.isOpened()) {


                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                        this, PERMISSION));
                return;
            }
        }

        if (allowNoSession) {

            pendingAction = action;
            handlePendingAction();
        }
    }

    //Proporciona información de los cambios de sesión
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    //Define una interfaz de devolución de llamada que se llamará 
    //cuando el usuario completa la interacción con un cuadro de diálogo de Facebook, o si se produce un error.
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall,
                            Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall,
                               Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

    //Muestra la pantalla de iniciar sesión (en el caso de que no esté inicada) y después publica en facebook
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @SuppressWarnings("deprecation")
            @Override
            public void call(Session session, SessionState state,
                             Exception exception) {

                if (session.isOpened()) {

                    Request.executeMeRequestAsync(session,
                            new Request.GraphUserCallback() {


                                @Override
                                public void onCompleted(GraphUser userr,
                                                        Response response) {

                                    if (userr != null) {

                                        user = userr;
                                        onClickPostStatusUpdate();

                                    }

                                }

                            });
                } else {

                    onClickPostStatusUpdate();
                }

            }
        });

        return null;
    }

    //Muestra la barra de buffer si se está reproducciendo una canción, sino lo pone a cero
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/


        if (cargabuffer == true) {
            barra.setSecondaryProgress(percent);
        } else {
            barra.setSecondaryProgress(0);
        }


    }

    //Pone todas las variables a cero de estados de reproducción a cero
    public void ponercero() {
        try {

            estados = 0;
            norepitas = false;
            cpausa = 0;
            onlinerror = false;
            crearbarra = false;
            nofunciona = false;


        } catch (Exception e) {

        }
    }

    //se le llama desde FragmentLista para comenzar a descargar una canción
    public void descarga() {
        //comprueba si está insertada la tarjeta de memoria para comenzar a descargar
        if (Environment.getExternalStorageState().equals("mounted")) {


//aumenta en uno el contador de descargas, por si hay varias simultáneas


            Reroductor.contdes++;

//Se crea un thread para la descarga
            thread = new Thread() {

                public void run() {
                    int contdecar = Reroductor.contdes;
                    try {


                        //primero especificaremos el origen de nuestro archivo a descargar utilizando
                        //la ruta completa quitando espacios en la url, si los hubiera
                        String cancion3 = "";
                        boolean espacio = false;
                        for (int p = 0; p <= ruta.length(); p++) {

                            try {
                                if (ruta.substring(p, p + 1).equals(" ")) {
                                    cancion3 = cancion3 + "%20" + ruta.substring(p, p + 1);
                                    espacio = true;
                                }
                                if ((ruta.substring(p, p + 1).equals("%")) || (ruta.substring(p, p + 1).equals("2"))
                                        || (ruta.substring(p, p + 1).equals("0")) || (ruta.substring(p, p + 1).equals(" "))) {


                                } else {
                                    cancion3 = cancion3 + ruta.substring(p, p + 1);
                                }
                            } catch (Exception e) {

                            }
                        }


                        String cancion4 = "";
                        for (int p = 0; p <= cancion3.length(); p++) {

                            try {

                                if ((cancion3.substring(p, p + 1).equals(" "))) {


                                } else {
                                    cancion4 = cancion4 + cancion3.substring(p, p + 1);
                                }
                            } catch (Exception e) {

                            }
                        }
                        URL url;
                        //Se guarda la url
                        if (espacio == true) {
                            url = new URL(cancion4);
                        } else {
                            url = new URL(ruta);
                        }
                        //establecemos la conexión con el destino
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                        //establecemos el método Get para nuestra conexión
                        //el método setdooutput es necesario para este tipo de conexiones
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoOutput(true);

                        //por último establecemos nuestra conexión 
                        urlConnection.connect();

                        //vamos a establecer la ruta de destino para nuestra descarga
                        //que se realiza en 
                        //la raíz de la tarjeta SD, en la carpeta melodiame
                        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File SDCardRoot1 = new File(baseDir);
                        File directory = new File(SDCardRoot1
                                + "/melodiame");

                        directory.mkdirs();

                        File SDCardRoot = new File(baseDir + "/melodiame");
                        //vamos a crear un objeto del tipo de fichero
                        //donde descargaremos nuestro fichero, en el que le damos el nombre de la canción
                        //extraida de la url

                        int cont = 0;
                        int posi = 0;
                        for (int p = 0; p <= ruta.length(); p++) {
                            cont++;
                            try {
                                if ((ruta.substring(p, p + 1).equals("/"))) {
                                    posi = cont;
                                }
                            } catch (Exception e) {

                            }
                        }

                        String cancion = ruta.substring(posi, ruta.length());
                        cont = 0;
                        posi = 0;
                        int spa = 0;
                        String cancion2 = "";
                        for (int p = 0; p <= cancion.length(); p++) {
                            cont++;
                            try {
                                if ((cancion.substring(p, p + 1).equals("%")) || (cancion.substring(p, p + 1).equals("2"))
                                        || (cancion.substring(p, p + 1).equals("0")) || (cancion.substring(p, p + 1).equals("'"))) {
                                    posi = cont;
                                    spa++;

                                } else {
                                    if (spa > 0) {
                                        spa = 0;
                                        cancion2 = cancion2 + " " + cancion.substring(p, p + 1);
                                    } else {
                                        cancion2 = cancion2 + cancion.substring(p, p + 1);
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                        File file = new File(SDCardRoot, cancion2);

                        //utilizaremos un objeto del tipo fileoutputstream
                        //para escribir el archivo que descargamos en el nuevo
                        FileOutputStream fileOutput = new FileOutputStream(file);

                        //leemos los datos desde la url
                        InputStream inputStream = urlConnection.getInputStream();

                        //obtendremos el tamaño del archivo y lo asociaremos a una
                        //variable de tipo entero
                        int totalSize = urlConnection.getContentLength();
                        int downloadedSize = 0;

                        //creamos un buffer y una variable para ir almacenando el 
                        //tamaño temporal de este
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;

                        //Se crea la notificacón de descarga, asignándole el nombre,el icono de descarga,
                        //y que cuando se le haga click reabra esta acivity si no está abierta
                        Reroductor.nm = (NotificationManager) Reroductor.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        CharSequence tickerText = getString(R.string.descargando);
                        long when = System.currentTimeMillis();
                        Reroductor.notif = new Notification(R.drawable.ic_menu_download, tickerText, when);
                        Context context = Reroductor.this.getApplicationContext();
                        Intent notiIntent = new Intent(context, Reroductor.class);
                        Bundle guardar = new Bundle();
                        guardar.putInt(Reroductor.parametro, 2);

                        notiIntent.putExtras(guardar);
                        startActivityForResult(notiIntent, 0);
                        PendingIntent pi = PendingIntent.getActivity(context, 0, notiIntent, 0);
                        Reroductor.notif.flags |= Notification.FLAG_NO_CLEAR;

                        CharSequence title = cancion2;
                        RemoteViews contentView = new RemoteViews(Reroductor.this.getPackageName(), R.layout.noti);
                        contentView.setImageViewResource(R.id.status_icon, R.drawable.ic_menu_download);
                        contentView.setTextViewText(R.id.status_text, title);
                        //al progressbar de la notificación se le pone el tamaño de la canción
                        contentView.setProgressBar(R.id.status_progress, totalSize, 0, false);
                        Reroductor.notif.contentView = contentView;
                        Reroductor.notif.contentIntent = pi;
                        long tiempo = System.currentTimeMillis();
                        tiempo = tiempo + 180000;

                        Reroductor.nm.notify(contdecar, Reroductor.notif);
                        //Se realiza un control para que se actualize la barra de progreso solo diez veces
                        int control = totalSize / 10;
                        int control2 = control;

                        //ahora iremos recorriendo el buffer para escribir el archivo de destino
                        //siempre teniendo constancia de la cantidad descargada y el total del tamaño
                        //y asignandoselo a la barra de progreso
                        while ((bufferLength = inputStream.read(buffer)) > 0) {

                            fileOutput.write(buffer, 0, bufferLength);
                            downloadedSize += bufferLength;
                            if (tiempo <= System.currentTimeMillis()) {
                                break;
                            }
                            if (downloadedSize >= control2) {
                                Reroductor.notif.contentView.setTextViewText(R.id.status_text, cancion2);
                                Reroductor.notif.contentView.setProgressBar(R.id.status_progress, totalSize, downloadedSize, false);
                                Reroductor.nm.notify(contdecar, Reroductor.notif);
                                control2 = control2 + control;
                            }
                        }
                        //cerramos
                        fileOutput.close();

                        //y gestionamos errores
                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    //paraliza todo lo relacionado con la notificación de descarga
                    Reroductor.descsem = false;
                    try {
                        Reroductor.nm.cancel(contdecar);
                    } catch (Exception e) {

                    }
                }

            };

            thread.start();


        } else {
            mostrarMensaje(getString(R.string.sin_sd));

        }
    }

    //Deshabilita notificaciones, para la reproduccíón y quita lo que contiene los tv de grupo y canción
//Se le llama desde FragmentLista
    public void ponercerosample() {
        j = 0;
        empiezaale = false;
        text.setText("");
        text2.setText("");
        try {
            reproSonido.stop();
            counter.cancel();
            notManager.cancel(1);
        } catch (Exception e) {

        }
        posicion = 0;
        play.setImageResource(R.drawable.play_1);

    }

    //Pone todas las variables a cero, deshabilita la barra de progreso
    public void ponercero2() {

        try {
            barra.setProgress(0);
            barra.setSecondaryProgress(0);
            cargabuffer = false;
            progreso = 0;
            min = 0;
            min2 = 0;
            seg = 0;
            seg2 = 0;
            estado2 = 0;
            barra.setEnabled(false);
            duracion.setText(min2 + ":0" + seg2);
            timee.setText("" + min + ":0" + seg);
            imagen.setImageResource(R.drawable.cor_chea);

        } catch (Exception e) {

        }

    }


    //Guarda una valoracion
    private class threadguardar extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        protected Void doInBackground(Void... params) {

            // TODO Auto-generated method stub
            //Abre base de datos
            db = myDbHelper.getReadableDatabase();
            //Recoge la cancion y las guarda en la base de datos


            //Se le quita los elementos de url para dejar solo el nombre de la canción si es online

            String cancion2 = "";
            for (int p = 0; p <= Reroductor.sruta.get(posicion).length(); p++) {

                try {
                    if ((Reroductor.sruta.get(posicion).substring(p, p + 1).equals("'"))) {
                        cancion2 = cancion2 + "%";

                    } else {
                        cancion2 = cancion2 + Reroductor.sruta.get(posicion).substring(p, p + 1);
                    }
                } catch (Exception e) {

                }
            }
            //Se le añade los elementos de url a la ruta si es una cancion online
            String cancion3 = "";
            for (int p = 0; p <= Reroductor.linea.get(posicion).length(); p++) {

                try {
                    if ((Reroductor.linea.get(posicion).substring(p, p + 1).equals("'"))) {
                        cancion3 = cancion3 + "%";

                    } else {
                        cancion3 = cancion3 + Reroductor.linea.get(posicion).substring(p, p + 1);
                    }
                } catch (Exception e) {

                }
            }

            try {
                //Guarda el elemento en la base de datos
                db.execSQL("INSERT INTO valoraciones (valoracion, ruta, cancion, grupo, tiempo) VALUES (" + puntuacion + ", '" + cancion3 + "', '" + cancion2 + "', '" + Reroductor.Grupo.get(posicion) + "', '" + Reroductor.Tiempo.get(posicion) + "')");
            } catch (Exception e) {

            }
            //cierra base de datos
            db.close();
            return null;
        }

        protected void onProgressUpdate(Void... values) {

        }

        protected void onPostExecute(Void result) {
            //Muestra que se ha guardado
            mostrarMensaje(getString(R.string.guar_dar));
        }
    }

    //Modifica una valoracion
    private class threadguardar2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        protected Void doInBackground(Void... params) {

            // TODO Auto-generated method stub
            //Abre base de datos
            db = myDbHelper.getReadableDatabase();
            //Recoge la cancion y las guarda en la base de datos


            //Se le quita los elementos de url para dejar solo el nombre de la canción si es online

            String cancion2 = "";
            for (int p = 0; p <= Reroductor.sruta.get(posicion).length(); p++) {

                try {
                    if ((Reroductor.sruta.get(posicion).substring(p, p + 1).equals("'"))) {
                        cancion2 = cancion2 + "%";

                    } else {
                        cancion2 = cancion2 + Reroductor.sruta.get(posicion).substring(p, p + 1);
                    }
                } catch (Exception e) {

                }
            }


            try {
                //Guarda el elemento en la base de datos
                db.execSQL("UPDATE valoraciones SET valoracion=" + puntuacion + " WHERE cancion='" + cancion2 + "'");
            } catch (Exception e) {

            }
            //cierra base de datos
            db.close();
            return null;
        }

        protected void onProgressUpdate(Void... values) {

        }

        protected void onPostExecute(Void result) {
            //Muestra que se ha guardado
            mostrarMensaje(getString(R.string.mo_difica));
        }
    }


    //Cambia la pantalla según su orientación
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//Si está en horizontal redimensiona la imagen del disco, quita los botones de valoracion,bucle, y repetición.
        //Recarga la lista de reproducciñon si está abierta
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            repite.setVisibility(View.INVISIBLE);
            aleatorio.setVisibility(View.INVISIBLE);
            star.setVisibility(View.INVISIBLE);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
            imagen.getLayoutParams().height = px;
            imagen.getLayoutParams().width = px;
            if (fragment != null) {
                fragment=null;
                fragment = new FragmentLista();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }


        }//Si está en vertical redimensiona la imagen del disco, pone los botones de valoracion,bucle, y repetición.
        //Recarga la lista de reproducciñon si está abierta
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            repite.setVisibility(View.VISIBLE);
            aleatorio.setVisibility(View.VISIBLE);
            star.setVisibility(View.VISIBLE);

            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            imagen.getLayoutParams().height = px;
            imagen.getLayoutParams().width = px;
            if (fragment != null) {
                fragment=null;
                fragment = new FragmentLista();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }

        }
    }

    //se le llama desde el onback e intera con el servicio de la aplicación
    //para volver a poner el ecualizador a su posición normal
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            // recoge el servicio del ecualizador
            mService = EqInterface.Stub.asInterface(service);

            // Añade a cada banda del ecualizador su posición inicial
            try {


                mService.setBandLevel(0, ecu.lv1);


                mService.setBandLevel(1, ecu.lv2);


                mService.setBandLevel(2, ecu.lv3);


                mService.setBandLevel(3, ecu.lv4);


                mService.setBandLevel(4, ecu.lv5);

            } catch (Exception e) {

            }
        }
//se llama cuando la conexión con el servicio a sido desconectada
        public void onServiceDisconnected(ComponentName className) {

            mService = null;
        }
    };
//se mete al pulsar un elemento del menu lateral
    private void selectItem(int position) {

        fragment = null;
        switch (position) {
            case 0:
                //Muestra el layout principal
                layout.setVisibility(View.VISIBLE);
                fragment=null;
                break;
            case 1:
              //Muestra lista de reproducción

                online = 1;
                resalta = 0;
                acancionessem = 1;

                fragment = new FragmentLista();
                break;


            case 2:
                //Muestra la publicidad intersticial
                interstitial.loadAd(adRequest2);


                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                //Si hay asignadas canciones para reproduir, abre la clase para guardar esta lista de canciones
                try {
                    if (linea2.size() > 0) {

                        Bundle guardar = new Bundle();
                        guardar.putInt(parametro, 1);
                        Intent myIntento = new Intent(getApplicationContext(),
                                Guarda.class);
                        myIntento.putExtras(guardar);
                        startActivityForResult(myIntento, 0);
                    } else {
                        mostrarMensaje(getString(R.string.anadir_lista));
                    }

                } catch (Exception e) {
                    mostrarMensaje(getString(R.string.anadir_lista));
                }

                break;

            case 3:
                //Muestra la publicidad intersticial
                interstitial.loadAd(adRequest2);


                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                //Abre la clase para ver las listas guardadas
                online = 1;
                resalta = 0;
                acancionessem = 1;

                Bundle guardar2 = new Bundle();
                guardar2.putInt(parametro, 1);
                Intent myIntento2 = new Intent(getApplicationContext(),
                        Buscag.class);
                myIntento2.putExtras(guardar2);
                startActivityForResult(myIntento2, 0);
                break;

            case 4:
                //Muestra la publicidad intersticial
                interstitial.loadAd(adRequest2);


                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                //Abre la clase para ver las valoraciones

                online = 1;
                resalta = 0;
                acancionessem = 1;

                Bundle guardar3 = new Bundle();
                guardar3.putInt(parametro, 1);
                Intent myIntento3 = new Intent(getApplicationContext(),
                        buscagv.class);
                myIntento3.putExtras(guardar3);
                startActivityForResult(myIntento3, 0);
                break;

            case 5:
                //Muestra la publicidad intersticial
                interstitial.loadAd(adRequest2);


                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                //Abre la clase pestmelo para buscar canciones en red
                online = 1;
                resalta = 0;
                acancionessem = 1;

                Bundle guardarr = new Bundle();
                guardarr.putInt(parametro, 1);
                Intent myIntentoo = new Intent(getApplicationContext(),
                        pestmelo.class);
                myIntentoo.putExtras(guardarr);
                startActivityForResult(myIntentoo, 0);
                break;

            case 6:
                //llama al método de redes sociales
                redes(controlsContainer);
                break;

            case 7:
                //Muestra la publicidad intersticial
                interstitial.loadAd(adRequest2);


                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });
                //Abre la clase del ecualizador
                online = 1;
                resalta = 0;
                acancionessem = 1;

                Bundle guardar4 = new Bundle();
                guardar4.putInt(parametro, 1);
                Intent myIntento4 = new Intent(getApplicationContext(),
                        ecu.class);
                myIntento4.putExtras(guardar4);
                startActivityForResult(myIntento4, 0);
                break;


            case 8:

                //Cancela todo y se sale del reproductor
                try {
                    counter.cancel();
                    reproSonido.stop();
                    notManager.cancel(1);
                } catch (Exception e) {

                }
                finish();
                break;



        }
        if (fragment != null) {
            // oculta el layout principal y muestra la lista de reproducción
layout.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }
mDrawerLayout.closeDrawer(mDrawerList);
    }
//llama al elemento que se a pulsado en el menú lateral
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

    }




}
