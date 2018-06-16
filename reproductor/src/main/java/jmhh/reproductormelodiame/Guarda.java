package jmhh.reproductormelodiame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jmhh.reproductormelodiame.R;


import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Guarda extends ActionBarActivity {
	private  AdView adView;
public String texto;
	public TextView text;
	public Button boton;
	public EditText buscartexto;
	public sql myDbHelper;
	public SQLiteDatabase db=null;
	public List<String> mostrar;
	public boolean sem2=false;
	public int num;
	   public void onCreate(Bundle savedInstanceState) {
	    	
		    	super.onCreate(savedInstanceState);
		        setContentView(R.layout.guardar);
           //Crea cabecera personalizada

           Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);

           setSupportActionBar(toolbar);
           this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
						R.layout.barratitulo);
		        //Se pone el fondo negro
		        RelativeLayout layout=(RelativeLayout)findViewById(R.id.layout);
		        
	       	    layout.setBackgroundColor(Color.BLACK);
	       	//Se asigna al layout anuncio la publicidad y la carga
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
//Se declaran los elementos del layout
	       	 boton = (Button) findViewById(R.id.guarda);
	       	 buscartexto= (EditText) findViewById(R.id.textguarda);
	       	 text=(TextView)findViewById(R.id.texto);
	       	 text.setText(getString(R.string.insertar));
	       	 boton.setText(getString(R.string.guardar));
	       		text.setTextColor(Color.WHITE);
	       		//Se crea la base de datos, si no se a creado, la abre.
	       		try{
			        myDbHelper = new sql (this);
			        myDbHelper.createDataBase(this);
			    	} catch (IOException ioe) {
				 		throw new Error(getString(R.string.no_bbdd));
				 	}
	       		myDbHelper.openDataBase();
	   		 
	    		db=myDbHelper.getReadableDatabase();
	    		
	    
	    		
	    		
	    		//Se acciona al pulsar el botón de guardar
	       		boton.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View view) {
	    				//Cierra el teclado
	    				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    				imm.hideSoftInputFromWindow(buscartexto.getWindowToken(),
	    						InputMethodManager.HIDE_NOT_ALWAYS);
	    				//coge el texto introducido
	    				texto=buscartexto.getText().toString();
	    				
	    			try{
	    				//Coge todos los id de la base de datos en orden descendiente
	    				  Cursor c = db.rawQuery("SELECT _id FROM lista order by _id desc",null);
	  	    	      	c.moveToFirst();
	  	    	      	//Se crea un array con los resultados
	  	    	      	num=c.getInt(0);
	  	    	      	 mostrar = new ArrayList<String>(num);
	  	    	      	 
	  	    		 String comprueba2;
	  	          	 boolean sem=false;
	  	          	 //Se recorre la base de datos guardando los nombres de las listas
	  	          		for(int i=1; i<=num; i++){
	  	          			
	  	          			Cursor d = db.rawQuery("SELECT nombre FROM lista  WHERE _id="+i+"",null);
	  	            		d.moveToFirst();
	  	            		try{
	  	   			comprueba2=d.getString(0);
	  	            		
	        			for(int e=0; e<num-1; e++){
	        				try{
	        					//Si el nombre se repite o hay algún error se pone la semilla a true
	        			    if(comprueba2.equals(mostrar.get(e))){
	        					sem=true;
	        				}
	        				}catch (Exception a){
	        	        		
	        	  		  }
	        			
	        			}
	  	            		}catch (Exception a){
	        	        		sem=true;
		        	  		  }
	        			if (sem==false){
	        				//se guarda el nombre en el array
	        				 Cursor b = db.rawQuery("SELECT nombre FROM lista  WHERE _id="+i+"",null);
	                  		b.moveToFirst();
	             			mostrar.add(b.getString(0));
	             		
	    				}else{
	    					sem=false;
	    				}
	        		}
	    				
	    			 }catch (Exception a){
	    			   		
	    			  }
	    			//Se controla si el campo está vacio
	    				if(texto.equals("")){
	    					mostrarMensaje(getString(R.string.nombre_lista));
	    				}else{
	    					int b=0;
	    					try{
	    						//Si el campo no está vacio se comprueba si existe ese nombre
		    				while(b+1<=mostrar.size()){
		    			
	    						if (texto.equals(mostrar.get(b))){
	    						
	    							sem2=true;
	    						}
	    						b++;
	    					}
	    					 }catch (Exception a){
	    					   		
	    					  }
	    					if (sem2==false){
	    		//Si el nombre no existe en la base de datos, se llama al thread que guarda la lista
	    						new threadguardar().execute();
	    				      


	    					}else{
	    						mostrarMensaje(getString(R.string.existe));
	    						sem2=false;
	    					}
	    				}
	    				
	    			}
	    	            		
	    		});
	       	
	        	
	   }
	   //Muestra el mensaje que se le pase
	   private void mostrarMensaje(String mensaje) {
			Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
			
		}
	   //Cuando se pulsa el botón de atras se cierra la base de datos y se finaliza la actividad
	   public void onBackPressed() {
	    	try{
	    		
	    	
	         	db.close();
	   
	    	 }catch (Exception e) {
	 		   
	 	   }
	    	finish();
		}
	   //Guarda una lista de reproducción
	   private class threadguardar extends AsyncTask<Void, Void, Void> {
		   ProgressDialog pDialog = new ProgressDialog(Guarda.this);
		@Override
		   protected void onPreExecute() {
			//Crea el progressdialog antes de empezar
	          pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	          pDialog.setMessage(getString(R.string.guardando));
	          pDialog.setCancelable(true);
	          pDialog.show();
		}
			protected Void doInBackground(Void... params) {
		
			// TODO Auto-generated method stub
				//Recoge las canciones y las guarda en la base de datos
				for(int i=0;i<Reroductor.linea2.size();i++){
					
					//Se le quita los elementos de url para dejar solo el nombre de la canción si es online
					
					String cancion2 = "";
					for(int p=0;p<=Reroductor.sruta.get(i).length();p++){
						
        		 try{
						if((Reroductor.sruta.get(i).substring(p,p+1).equals("'"))){
							cancion2=cancion2+"%";
						
						}else{
							cancion2=cancion2+Reroductor.sruta.get(i).substring(p,p+1);
						}
        		 }catch (Exception e) {
	         		   
	         	   }
					}
					//Se le añade los elementos de url a la ruta si es una cancion online
					String cancion3 = "";
					for(int p=0;p<=Reroductor.linea.get(i).length();p++){
						
		        		 try{
								if((Reroductor.linea.get(i).substring(p,p+1).equals("'"))){
									cancion3=cancion3+"%";
								
								}else{
									cancion3=cancion3+Reroductor.linea.get(i).substring(p,p+1);
								}
		        		 }catch (Exception e) {
			         		   
			         	   }
							}
					try{
					//Guarda los elementos en la bse de datos
				 db.execSQL("INSERT INTO lista (nombre, ruta, cancion, grupo, tiempo) VALUES ('"+texto+"', '"+cancion3+"', '"+cancion2+"', '"+Reroductor.Grupo.get(i)+"', '"+Reroductor.Tiempo.get(i)+"')");
					} catch (Exception e){
						
				 	}
				}
			return null;
		}
		protected void onProgressUpdate(Void... values) {
			
		}
		   protected void onPostExecute(Void result) {
			   //Cierra el progressdialog
			   pDialog.dismiss();
		   }
	   }
    //Al pulsar el boton de la barra de atrás se sale a la pantalla principal

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
