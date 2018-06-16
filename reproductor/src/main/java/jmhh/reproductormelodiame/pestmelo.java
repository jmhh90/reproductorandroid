package jmhh.reproductormelodiame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jmhh.reproductormelodiame.R;


//import jmhh.reproductormelodiame.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import com.google.ads.AdRequest;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.Session;
import com.google.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class pestmelo extends ActionBarActivity implements OnItemClickListener{
	//Se declaran crean los elementos
	 public SearchView mSearchView;
	public String name2;
	public Random rnd;
	public ImageButton buscarboton;
	public EditText buscartexto;
	public List<String> items2;
	public ArrayAdapter<String> cadena;
	public Socket miCliente;
	public Thread thread;
	public  BufferedReader entrada;
	public PrintWriter salida;
	//Comando de referencia de como buscar canciones
	public String comando="command=findMusic&criteria=sandman";
	public int n=comando.length();
	public StringBuilder sb = new StringBuilder();
	public JSONObject array;
	public String canciones[]=new String[1000];
	public ListView lv;
	private AdView adView;
	 public void onCreate(Bundle savedInstanceState) {
	    	
	    	
   	super.onCreate(savedInstanceState);
     	setContentView(R.layout.melolayout);
//Se crea la barra personalizada
         Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);

         setSupportActionBar(toolbar);
         this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   	 rnd = new Random();
   	 //Se le asigna el banner as layout y se carga
   	 adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-8831350722190477/8350482349");
		adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
		
         LinearLayout layout2 = (LinearLayout)findViewById(R.id.anuncio);

		 layout2.addView(adView);
		 AdRequest adRequest = new AdRequest.Builder()
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .addTestDevice("TEST_DEVICE_ID")
		    .build();

		   
adView.loadAd(adRequest);
//Se declaran elementos del layout
	  
     	buscarboton = (ImageButton) findViewById(R.id.boton);
     	buscartexto= (EditText) findViewById(R.id.textoo);
     	lv=(ListView)findViewById(android.R.id.list);
     	lv.setOnItemClickListener(this);
     	 LinearLayout mlayout=(LinearLayout)findViewById(R.id.mlayout);
    	    mlayout.setBackgroundColor(Color.BLACK);
    	    //Se mete cuando se pulsa el botón de buscar
     	buscarboton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Se comprueba si hay red, y si es así llama al thread de buscar
				ConnectivityManager connec = (ConnectivityManager) pestmelo.this
	     				.getSystemService(Context.CONNECTIVITY_SERVICE);
				 
	     		NetworkInfo redes = connec.getActiveNetworkInfo();
	     		boolean comprueba = false;
				if (redes != null && redes.isConnected()) {
	     			comprueba = true;
	     		}

	     		if (comprueba == false) {
	     			mostrarMensaje(getString(R.string.sin_red));
	     		} else {
				new threadbuscar().execute();

	     		}
	     	
			}
	            		
		});

}
	//Comprueba si se conecta a melodiame
	 public boolean Connect() {
			//Se coge la IP y el puerto a conectar
			String IP = "melodiame.com";
			int PORT = 80;
			
				
			
			try {//creamos sockets con los valores anteriores
				miCliente = new Socket(IP, PORT);
				//y nos conectamos
				if (miCliente.isConnected() == true) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				//Si hubo algun error mostramos error
				Log.e("Error connect()", "" + e);
				return false;
			}
		}
	 //Comprueba se un elemento está online o no y si es online llama al dialog de descargar o añadir a la lista de reproducción
	  private class comprueba extends AsyncTask<Void, Void, Void> {

		  ProgressDialog pDialog = new ProgressDialog(pestmelo.this);
		  int totalSize;
		  boolean comprueba = false;
			 protected void onPreExecute() {				 
				 //Muestra el progressdialog
				 pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          pDialog.setMessage(getString(R.string.disponibilidad));
		          pDialog.setCancelable(true);
		          pDialog.show();
				 
			 }
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				 URL url;
				 String cancion3="";
	        	    boolean espacio=false;
	        	    //Se recorre la ruta por si hay algún espacio y así insertarle %20 que significa que hay espacio
 				for(int p=0;p<=name2.length();p++){
						
		        		 try{
		        			 if(name2.substring(p,p+1).equals(" ")){
									cancion3=cancion3+"%20"+name2.substring(p,p+1);
									espacio=true;
								}
								if((name2.substring(p,p+1).equals("%"))||(name2.substring(p,p+1).equals("2"))
										||(name2.substring(p,p+1).equals("0"))||(name2.substring(p,p+1).equals(" "))){
									
								
								}else{
									cancion3=cancion3+name2.substring(p,p+1);
								}
		        		 }catch (Exception e) {
			         		   
			         	   }
							}


        				String cancion4="";
        				for(int p=0;p<=cancion3.length();p++){
								
	 		        		 try{
	 		        			
	 								if((cancion3.substring(p,p+1).equals(" "))){
	 									
	 								
	 								}else{
	 									cancion4=cancion4+cancion3.substring(p,p+1);
	 								}
	 		        		 }catch (Exception e) {
	 			         		   
	 			         	   }
	 							}
        				//Se guarda la url
		        	 if (espacio==true){
	        		 url = new URL(cancion4);
		        	 }else{
		        		 url = new URL(name2);
		        	 }
		        	 //Se comprueba si hay red
		        	 ConnectivityManager connec = (ConnectivityManager) pestmelo.this
			     				.getSystemService(Context.CONNECTIVITY_SERVICE);
						 
			     		NetworkInfo redes = connec.getActiveNetworkInfo();
			     		
						if (redes != null && redes.isConnected()) {
			     			comprueba = true;
			     		}

			     		 
			     		
						if (comprueba == true) {
						//En el caso de que haya red se abre la conexión
			     			 HttpURLConnection urlConnection = null;
			 				try {
			 					urlConnection = (HttpURLConnection) url.openConnection();
			 				} catch (IOException e2) {
			 					// TODO Auto-generated catch block
			 					e2.printStackTrace();
			 				}
			 		        	 
			 	        	    //establecemos el método get para nuestra conexión
			 	        	    //el método setdooutput es necesario para este tipo de conexiones
			 	        	    try {
			 						urlConnection.setRequestMethod("GET");
			 					} catch (ProtocolException e1) {
			 						// TODO Auto-generated catch block
			 						e1.printStackTrace();
			 					}
			 	        	    urlConnection.setDoOutput(true);
			 	        	 
			 	        	    //por último establecemos nuestra conexión
			 	        	    try {
			 						urlConnection.connect();
			 					} catch (IOException e1) {
			 						// TODO Auto-generated catch block
			 						e1.printStackTrace();
			 					}
			 	        	    //Se extrae el tamaño del archivo
			 	       	    totalSize = urlConnection.getContentLength();
			 	       
			         	
			     		}
		        
	        	 

			} catch (IOException e) {
			
			}
	    
			return null;
		}
		  protected void onPostExecute(Void result) {
			  //Se cierra el progressdialog
			  pDialog.dismiss();
			  //Si hay red y el tamaño del archivo es menor que mil bytes, es que está caido el enlace
			  if(comprueba!=false){
			    if (totalSize<=10000){
		         	mostrarMensaje(getString(R.string.caido));
		         	
		         	    }else{
		 		        	 
		 		        	 
		 		        	 //Si no está caido llama al dialog que descarga o añade a la lista de reproducción
                    crearDialogoAlertaUrl();

		         	    }
			  }else{
				  //Si no hay red, lo muestra
				  mostrarMensaje(getString(R.string.sin_red));
			  }
		  }
	  }
	  //Si se pulsa un elemento de la lista se coge su posición y comprueba su disponibilidad
	 public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			// TODO Auto-generated method stub
		 
		 name2=items2.get(position);
		new comprueba().execute();
		}
//Dialog que añade a la lista de reproducción o descarga la canción


    private void crearDialogoAlertaUrl() {
        new MaterialDialog.Builder(this)
               // .iconRes(R.drawable.dia_log)
               // .title(getString(R.string.tweetoface))
                .content(getString(R.string.anadir))
                .positiveText(getString(R.string.si))
                .neutralText(getString(R.string.cancelar))
                .negativeText(getString(R.string.descargar))
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
                        int cont=0;
                        int posi=0;
                        //Recorre la longitud de la ruta para coger la posición de la ultima barra
                        for(int p=0;p<=name2.length();p++){
                            cont++;
                            try{
                                if((name2.substring(p,p+1).equals("/"))){
                                    posi=cont;
                                }
                            }catch (Exception e) {

                            }
                        }
                        //A partir de la última barra se extrae su nombre
                        String cancion=name2.substring(posi, name2.length());
                        cont=0;
                        posi=0;
                        String cancion2 = "";
                        //Se recorre el nombre de la canción en busca de los %20 para ponerle en su lugar espacios
                        for(int p=0;p<=cancion.length();p++){
                            cont++;
                            try{
                                if((cancion.substring(p,p+1).equals("%"))||(cancion.substring(p,p+1).equals("2"))
                                        ||(cancion.substring(p,p+1).equals("0"))||(cancion.substring(p,p+1).equals("'"))){
                                    posi=cont;

                                }else{
                                    cancion2=cancion2+cancion.substring(p,p+1);
                                }
                            }catch (Exception e) {

                            }
                        }

                        String cancion3="";
                        boolean espacio=false;
                        //Recorremos la ruta en busca de espacios para poner le en su lugar %20
                        for(int p=0;p<=name2.length();p++){

                            try{
                                if(name2.substring(p,p+1).equals(" ")){
                                    cancion3=cancion3+"%20"+name2.substring(p,p+1);
                                    espacio=true;
                                }
                                if((name2.substring(p,p+1).equals("%"))||(name2.substring(p,p+1).equals("2"))
                                        ||(name2.substring(p,p+1).equals("0"))||(name2.substring(p,p+1).equals(" "))){


                                }else{
                                    cancion3=cancion3+name2.substring(p,p+1);
                                }
                            }catch (Exception e) {

                            }
                        }


                        String cancion4="";
                        for(int p=0;p<=cancion3.length();p++){

                            try{

                                if((cancion3.substring(p,p+1).equals(" "))){


                                }else{
                                    cancion4=cancion4+cancion3.substring(p,p+1);
                                }
                            }catch (Exception e) {

                            }
                        }
                        //Guardamos la ruta de la canción en la lista de reproducción
                        if (espacio==true){
                            Reroductor.linea.add(cancion4);
                        }else{
                            Reroductor.linea.add(name2);
                        }

                        //Guardamos el nombre de la canción en la lista de reproducción
                        Reroductor.sruta.add(cancion2);

                        //Se vuelve a añadir la lista de reproducción con los nuevos datos
                        try{

                            int limite=Reroductor.linea.size();
                            String strings[] = new String[Reroductor.linea.size()];
                            String strings2[] = new String[Reroductor.linea.size()];
                            Reroductor.linea2=new ArrayList<String>(Reroductor.linea.size());;
                            Reroductor.Grupo=new ArrayList<String>(Reroductor.linea.size());;
                            Reroductor.Tiempo=new ArrayList<String>(Reroductor.linea.size());
                            //Se van añadiendo las rutas y nombres al nuevo array
                            for(int contador = 0; contador < limite; contador++){
                                strings[contador]=Reroductor.sruta.get(contador);
                                strings2[contador]=Reroductor.linea.get(contador);



                                int cont2=0;
                                int seg=0;
                                int min=0;
                                try{//Si es un archivo online el nombre del grupo es Online
                                    if(strings2[contador].substring(strings2[contador].indexOf("h"), 4).equals("http")){

                                        Reroductor.linea2.add(strings[contador].substring(0,strings[contador].length()-4));
                                        Reroductor.Grupo.add("Online");
                                        Reroductor.Tiempo.add("");
                                    }


                                }catch (Exception a){

                                    try{
                                        //Se van extrayendo los metadatos para ir añadiendo el nombre del grupo, la canción y el tiempo

                                        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                                        metaRetriever.setDataSource(strings2[contador]);
                                        cont2=Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                                        while(cont2>=0){
                                            cont2=cont2-1000;
                                            seg=seg+1;
                                            if (seg==60){
                                                min=min+1;
                                                seg=0;
                                            }
                                        }
                                        seg--;
                                        if (seg<10){
                                            if(seg==-1){
                                                min--;
                                                seg=59;
                                                Reroductor.Tiempo.add(""+min+":"+seg);
                                            }else{
                                                Reroductor.Tiempo.add(""+min+":"+0+seg);
                                            }
                                        }else{
                                            Reroductor.Tiempo.add(""+min+":"+seg);
                                        }
                                        if(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)!=null){
                                            strings[contador]=strings[contador].substring(0,strings[contador].length()-4);
                                            Reroductor.linea2.add(strings[contador]);
                                            Reroductor.Grupo.add(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

                                        }else{//Si no hay metadatos el nombre del grupo se pone como Desconocido
                                            strings[contador]=strings[contador].substring(0,strings[contador].length()-4);
                                            Reroductor.linea2.add(strings[contador]);
                                            Reroductor.Grupo.add(getString(R.string.desconocido));

                                        }


                                    }catch (Exception b){

                                    }

                                }
                            };



                        }catch (Exception a){

                        }//Se mete en el caso de que esté activado el aleatorio
                        if (Reroductor.aleatorioo==true){
                            try {
                                Reroductor.estadoss=Reroductor.linea.size();
                                //Se controla que no conincida lo lo que hay en el array de aleatorio con la posición actual

                                int k=0;
                                for(int i=0; i<Reroductor.posale.length; i++){
                                    if(Reroductor.posale[i]==Reroductor.posicion){
                                        k=i;
                                    }
                                }//Se realiza una copia del contenido del array de aleatorio
                                Reroductor.posale2=new int [k];
                                for(int i=0; i<=k; i++){
                                    Reroductor.posale2[i]=Reroductor.posale[i];
                                }
                                Reroductor.posale=new int [Reroductor.linea.size()];
                                //Se vuelve a insertar las posiciones en aleatorio sin la posición actual
                                Reroductor.j=k;

                                for(int i=0; i<=k; i++){
                                    Reroductor.posale[i]=Reroductor.posale2[i];
                                }//Se meten las posiciones nuevas a partir de la posción actual
                                for(int i=k+1; i<Reroductor.posale.length; i++){
                                    int localizado= (int)(rnd.nextDouble() * Reroductor.estadoss);


                                    Reroductor.posale[i]=localizado;
                                    for (int t=0; t!=i; t++){

                                        while(Reroductor.posale[i]==Reroductor.posale[t]){
                                            localizado= (int)(rnd.nextDouble() * Reroductor.estadoss);
                                            if (t!=0){
                                                for (int p=0; p!=t; p++){
                                                    while(localizado==Reroductor.posale[p]||Reroductor.posale[0]==localizado){
                                                        localizado= (int)(rnd.nextDouble() * Reroductor.estadoss);
                                                    }
                                                }
                                            }
                                            Reroductor.posale[i]=localizado;
                                            Reroductor.posicion=Reroductor.posale[Reroductor.j];


                                        }

                                    }
                                }

                            }catch (Exception a){

                            }

                        }
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //Si se elige la opción de descargar se comprueba se está insertada la SD
                        if (Environment.getExternalStorageState().equals("mounted")) {





                            //Se activa el contador de descargas
                            Reroductor.contdes++;


                            thread = new Thread(){

                                public void run() {
                                    int contdecar=Reroductor.contdes;
                                    try {


                                        //primero especificaremos el origen de nuestro archivo a descargar utilizando
                                        //la ruta completa poniendo %20 donde haya espacios
                                        String cancion3="";
                                        boolean espacio=false;
                                        for(int p=0;p<=name2.length();p++){

                                            try{
                                                if(name2.substring(p,p+1).equals(" ")){
                                                    cancion3=cancion3+"%20"+name2.substring(p,p+1);
                                                    espacio=true;
                                                }
                                                if((name2.substring(p,p+1).equals("%"))||(name2.substring(p,p+1).equals("2"))
                                                        ||(name2.substring(p,p+1).equals("0"))||(name2.substring(p,p+1).equals(" "))){


                                                }else{
                                                    cancion3=cancion3+name2.substring(p,p+1);
                                                }
                                            }catch (Exception e) {

                                            }
                                        }


                                        String cancion4="";
                                        for(int p=0;p<=cancion3.length();p++){

                                            try{

                                                if((cancion3.substring(p,p+1).equals(" "))){


                                                }else{
                                                    cancion4=cancion4+cancion3.substring(p,p+1);
                                                }
                                            }catch (Exception e) {

                                            }
                                        }
                                        URL url;
                                        //Guadamos la url
                                        if (espacio==true){
                                            url = new URL(cancion4);
                                        }else{
                                            url = new URL(name2);
                                        }
                                        //establecemos la conexión con el destino
                                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                                        //establecemos el método jet para nuestra conexión
                                        //el método setdooutput es necesario para este tipo de conexiones
                                        urlConnection.setRequestMethod("GET");
                                        urlConnection.setDoOutput(true);

                                        //por último establecemos nuestra conexión y cruzamos los dedos <img src="http://www.insdout.com/wp-includes/images/smilies/icon_razz.gif" alt=":P" class="wp-smiley">
                                        urlConnection.connect();

                                        //vamos a establecer la ruta de destino para nuestra descarga
                                        //que será en la carpeta melodiame
                                        //en la raíz de la tarjeta SD
                                        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                                        File SDCardRoot1 = new File(baseDir);
                                        File directory = new File(SDCardRoot1
                                                + "/melodiame");

                                        directory.mkdirs();

                                        File SDCardRoot = new File(baseDir+"/melodiame");
                                        //vamos a crear un objeto del tipo de fichero
                                        //donde descargaremos nuestro fichero,
                                        //le damos el nombre de la canción

                                        int cont=0;
                                        int posi=0;
                                        for(int p=0;p<=name2.length();p++){
                                            cont++;
                                            try{
                                                if((name2.substring(p,p+1).equals("/"))){
                                                    posi=cont;
                                                }
                                            }catch (Exception e) {

                                            }
                                        }

                                        String cancion=name2.substring(posi, name2.length());
                                        cont=0;
                                        posi=0;
                                        String cancion2 = "";
                                        for(int p=0;p<=cancion.length();p++){
                                            cont++;
                                            try{
                                                if((cancion.substring(p,p+1).equals("%"))||(cancion.substring(p,p+1).equals("2"))
                                                        ||(cancion.substring(p,p+1).equals("0"))||(cancion.substring(p,p+1).equals("'"))){
                                                    posi=cont;

                                                }else{
                                                    cancion2=cancion2+cancion.substring(p,p+1);
                                                }
                                            }catch (Exception e) {

                                            }
                                        }

                                        File file = new File(SDCardRoot,cancion2);

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
                                        Reroductor.nm = (NotificationManager) pestmelo.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                        CharSequence tickerText = getString(R.string.descargando);
                                        long when = System.currentTimeMillis();
                                        Reroductor.notif = new Notification(R.drawable.ic_menu_download, tickerText, when);
                                        Context context = pestmelo.this.getApplicationContext();
                                        Intent notiIntent = new Intent(context, Reroductor.class);
                                        Bundle guardar = new Bundle();
                                        guardar.putInt(Reroductor.parametro, 2);

                                        notiIntent.putExtras(guardar);
                                        startActivityForResult(notiIntent, 0);
                                        PendingIntent pi = PendingIntent.getActivity(context, 0, notiIntent, 0);
                                        Reroductor.notif.flags |= Notification.FLAG_NO_CLEAR;

                                        CharSequence title = cancion2;
                                        RemoteViews contentView = new RemoteViews(pestmelo.this.getPackageName(), R.layout.noti);
                                        contentView.setImageViewResource(R.id.status_icon, R.drawable.ic_menu_download);
                                        contentView.setTextViewText(R.id.status_text, title);
                                        //al progressbar de la notificación se le pone el tamaño de la canción
                                        contentView.setProgressBar(R.id.status_progress, totalSize, 0, false);
                                        Reroductor.notif.contentView = contentView;
                                        Reroductor.notif.contentIntent = pi;
                                        long tiempo=System.currentTimeMillis();
                                        tiempo=tiempo+180000;

                                        Reroductor.nm.notify(contdecar, Reroductor.notif);
                                        //Se realiza un control para que se actualize la barra de progreso solo diez veces
                                        int control=totalSize/10;
                                        int control2=control;
                                        //ahora iremos recorriendo el buffer para escribir el archivo de destino
                                        //siempre teniendo constancia de la cantidad descargada y el total del tamaño
                                        //y asignandoselo a la barra de progreso
                                        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

                                            fileOutput.write(buffer, 0, bufferLength);
                                            downloadedSize += bufferLength;
                                            if (tiempo<=System.currentTimeMillis()){
                                                break;
                                            }
                                            if (downloadedSize>=control2){
                                                Reroductor.notif.contentView.setTextViewText(R.id.status_text, cancion2);
                                                Reroductor.notif.contentView.setProgressBar(R.id.status_progress, totalSize, downloadedSize, false);
                                                Reroductor.nm.notify(contdecar, Reroductor.notif);
                                                control2=control2+control;
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
                                    Reroductor.descsem=false;
                                    try{
                                        Reroductor.nm.cancel(contdecar);
                                    }catch (Exception e) {

                                    }
                                }

                            };

                            thread.start();



                        }else{
                            //Avisa de que no hay tarjeta SD y cierra el dialog
                            mostrarMensaje(getString(R.string.sin_sd));


                        }

                    }
                })
                .show();
    }


	 //Muestra el mensaje que se le pase
	 private void mostrarMensaje(String mensaje) {
			Toast.makeText(pestmelo.this, mensaje, Toast.LENGTH_LONG).show();
		}
	 //Thread que busca la canción elegida
	 private class threadbuscar extends AsyncTask<Void, Void, Void>{

		 protected void onPreExecute(){
			 //Crea un array adapter con la lista personalizada
			 cadena =  new ArrayAdapter<String>(pestmelo.this, R.layout.list_item);
			 buscarboton.setImageResource(R.drawable.reloj_arena);
			 //Cierra el teclado
				InputMethodManager imm = (InputMethodManager) pestmelo.this.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(buscartexto.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		 }
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try{
    			
				boolean conectstatus=Connect();
				//Comprueba si hay conexión con melodiame
				if(conectstatus){
					
					
			            		 int items;
			            		 //Extraemos el texto del edittext
			 					String searchFor = buscartexto.getText().toString();
					
						
						  try {
							  //Se crea un buffer de entrada y otro de salida
							entrada = new BufferedReader(
							            new InputStreamReader( miCliente.getInputStream() ) );
							 salida = new PrintWriter(
						                new OutputStreamWriter( miCliente.getOutputStream() ),true );
							//Se crea el comando con la canción a buscar y se guarda su longitud
						              comando="command=findMusic2&criteria="+searchFor;
						              n=comando.length();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					             
					              
					              
//Si el socket está conectado se inserta en el buffer de salida el método post con el comando creado, su longitud y el resto de comandos necesarios	
						if (miCliente.isConnected())
						{
							
							 salida.println("POST /r/PHP/API_songStorage.php  HTTP/1.0\r\nHost: melodiame.com\r\nContent-Type:application/x-www-form-urlencoded\r\nContent-Length: "+n+"\r\nConnection:close\r\n\r\n"+comando);
							
							

						} 
					
				            		
						  
							
				            	
				            		 String lineas = null;
									  sb.setLength(0);
			              try {
			            	  //Vamos metiendo en un stringBuilder lo que nos devuelve melodiame, que está en formato JSON
							while( (lineas = entrada.readLine()) != null ) {
							       
								
							        sb.append(lineas);
							        
							  }
						} catch (IOException e) {
							
							e.printStackTrace();
						}
			             
				        
				         
			              String result;
			              //Metemos la devolución en un string
			              result=sb.toString();
			       
			             
			              
						//Cogemos sólo lo correspondiente a la canción y su ruta
						result=result.substring (result.indexOf("json")+28, result.length()); 
			              try {
			         		
			         		//Metemos en un JSONObjet el resultado
			            	  array = new JSONObject(result);
			            	  //Metemos en el jarray los resultados de exactos de la búsqueda 
			            	  //y en jarray2 metemos los resultados que contienen algúna palabra de la buscada
			            	  JSONArray jarray = new JSONArray(array.getString("pref"));
			            	  JSONArray jarray2 = new JSONArray(array.getString("norm"));
			        	//Cogemos la longitud de jarry
			            	  items = jarray.length();
			            	  //Guardamos tanto en un array como en un arraylist las longitudes de los dos resultados juntos
					        	 canciones=new String[jarray.length()+jarray2.length()];
					        	 items2 = new ArrayList<String>(jarray.length()+jarray2.length());
					        
			        	 String cadenass;
			        	 int acaba = 0;
//Recorremos el JSONArray de los resultados exactos
			        	 for (int i=0; i<=jarray.length()-1; i++) {
			        		acaba=i;
//Extraemos elementos y vamos guardando la canción y su ruta en sus respectivos arrays
			        		 JSONObject ob = jarray.getJSONObject(i);				        		 
			        		 canciones[i]=ob.getString("songLink");
			        		 items2.add(ob.getString("songLink"));
			        		 cadenass=ob.getString("songLink");
			        		
			        			
			        		 //Guardamos el nombre de la canción a partir de su ruta sustituyendo los %20 por espacios
			        		 int cont=0;
				        	 int posi=0;
								for(int p=0;p<=cadenass.length();p++){
									cont++;
			        		 try{
									if((cadenass.substring(p,p+1).equals("/"))){
										posi=cont;
									}
			        		 }catch (Exception e) {
				         		   
				         	   }
								}
							
								String cancion=cadenass.substring(posi, cadenass.length());
								cont=0;
								posi=0;
								String cancion2 = "";
								for(int p=0;p<=cancion.length();p++){
									cont++;
			        		 try{
									if((cancion.substring(p,p+1).equals("%"))||(cancion.substring(p,p+1).equals("2"))
											||(cancion.substring(p,p+1).equals("0"))||(cancion.substring(p,p+1).equals("'"))){
										posi=cont;
									
									}else{
										cancion2=cancion2+cancion.substring(p,p+1);
									}
			        		 }catch (Exception e) {
				         		   
				         	   }
								}
								
								
						   	 
								cadena.add(cancion2);


			        
								 
			        		 
	    			    		
			        	 }
			        	 
			        	//Recorremos el JSONArray de los resultados aproximados
			          	 for (int i=0; i<=jarray2.length()-1; i++) {
				        		acaba=acaba+1;
				        		//Extraemos elementos y vamos guardando la canción y su ruta en sus respectivos arrays
				        		 JSONObject ob = jarray2.getJSONObject(i);

				        		 canciones[acaba]=ob.getString("songLink");
				        		 items2.add(ob.getString("songLink"));
				        		 cadenass=ob.getString("songLink");
				        		
				        			
				        		//Guardamos el nombre de la canción a partir de su ruta sustituyendo los %20 por espacios
				        		 int cont=0;
					        	 int posi=0;
									for(int p=0;p<=cadenass.length();p++){
										cont++;
				        		 try{
										if((cadenass.substring(p,p+1).equals("/"))){
											posi=cont;
										}
				        		 }catch (Exception e) {
					         		   
					         	   }
									}
								
									String cancion=cadenass.substring(posi, cadenass.length());
									cont=0;
									posi=0;
									String cancion2 = "";
									for(int p=0;p<=cancion.length();p++){
										cont++;
				        		 try{
										if((cancion.substring(p,p+1).equals("%"))||(cancion.substring(p,p+1).equals("2"))
												||(cancion.substring(p,p+1).equals("0"))||(cancion.substring(p,p+1).equals("'"))){
											posi=cont;
										
										}else{
											cancion2=cancion2+cancion.substring(p,p+1);
										}
				        		 }catch (Exception e) {
					         		   
					         	   }
									}
									cadena.add(cancion2);

									

				        		 
				        
				        		 
									
				        	 }
			        	
			    			
			    		
			    			
			    			
			    	
			    			
			    			
			        	
						} catch (JSONException e) {
							

							// TODO Auto-generated catch block
						e.printStackTrace();
						}
			              
			              

					 
					
					try {
						//Cierra el socket
						miCliente.close();
					} catch (IOException e) {
						

						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
				}
				
			 }catch (Exception e) {
				 
       	   }
			return null;
		}
		  protected void onProgressUpdate(Void... values) {

			  
		  }
		  protected void onPostExecute(Void result){
			  try{
//Añade los elementos a la lista
				  lv.setAdapter(cadena);
			  }catch (Exception e) {
				  //Muestra si a habido algún error
  						 mostrarMensaje(getString(R.string.error));
  		       	   }
			  //Vuelve a poner la imagen de la lupa
			  buscarboton.setImageResource(R.drawable.ic_menu_search);
			  
		  }
	 }
//Se le añade el botón de atras en la barra de la aplicación
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
	
	
}
