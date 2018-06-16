package jmhh.reproductormelodiame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class buscagv extends ActionBarActivity {
	private AdView adView;
	public sql myDbHelper;
	public SQLiteDatabase db=null;
	public List<String> mostrar;
	public List<String> mostrar2;
	public List<String> mostrar3;
	public int posicion;
	public RelativeLayout layout;
	public TextView nlista;
	public Random rnd;
	public ListView lv;


	   public void onCreate(Bundle savedInstanceState) { 
	    	
	    	super.onCreate(savedInstanceState);

	        setContentView(R.layout.buscavaloracion);
           //Se asigna barra de titlo personalizada

           Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);

           setSupportActionBar(toolbar);
           this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.barratitulo);
	        

	        //Se asigna el banner de carga de publicidad
	        adView = new AdView(this);
			adView.setAdUnitId("ca-app-pub-8831350722190477/8350482349");
			adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
	        rnd = new Random();
            LinearLayout layout2 = (LinearLayout)findViewById(R.id.anuncio);
//Se muestra el banner de publicidad
            layout2.addView(adView);
   		 AdRequest adRequest = new AdRequest.Builder()
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .addTestDevice("TEST_DEVICE_ID")
		    .build();

		   
adView.loadAd(adRequest);

	        //Se declaran los elementos del layout
	       layout=(RelativeLayout)findViewById(R.id.layout);
      	    layout.setBackgroundColor(Color.BLACK);
      	  nlista=(TextView)findViewById(R.id.nolista);
      	lv=(ListView)findViewById(android.R.id.list);
      	nlista.setTextColor(Color.WHITE);
     nlista.setText(getString(R.string.no_valor));
      	//Se crea la base de datos si no estuviera hecha
      		try{
		        myDbHelper = new sql (this);
		        myDbHelper.createDataBase(this);
		    	} catch (IOException ioe) {
			 		throw new Error(getString(R.string.no_bbdd));
			 	}
      		//Muestra las canciones guardadas
      		new threadbd().execute();
           //Al hacer click en un elemento de la lista, se coge la posición del elemento y se habre el dialog crearDialogo

           lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   posicion=position;
                   crearDialogo();

               }
           });

      		//Se llama al mantener pulsado un elemento de las canciones valoradas
    		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    			public boolean onItemLongClick(AdapterView<?> av, View v,
    					int pos, long id) {
    				return onLongListItemClick(v, pos, id);
    			}

    			protected boolean onLongListItemClick(View v, int pos, long id) {
    				
    					//Llama al dialog de borrar todas las canciones
                    crearDialogoBorrartodo();

   
    				return true;
    			}
    		});
	   }

	   //Al pulsar el botón de atras se cierra la base de datos y esta activity
	   public void onBackPressed() {
	    	try{
	    		
	    	
	         	db.close();
	   
	    	 }catch (Exception e) {
	 		   
	 	   }
	    	finish();
		}

	   //Dialg que muestra añadir o borrar un elemento de la lista

    private void crearDialogo() {
        new MaterialDialog.Builder(this)
                //.iconRes(R.drawable.dia_log)
                //.title(getString(R.string.tweetoface))
                .content(getString(R.string.anadir_borrar))
                .positiveText(getString(R.string.anadir_boton))
                .neutralText(getString(R.string.cancelar))
                .negativeText(getString(R.string.borrar_boton))
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
                        //Llama al thread que añade elementos a la lista de reproducción
                        new threadlista().execute();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //Llama al thread que borra la lista de la base de datos
                        new threadborrar().execute();
                    }
                })
                .show();
    }


	   //Añade elementos a la lista de reproducción
	   private class threadlista extends AsyncTask<Void, Void, Void> {
		   ProgressDialog pDialog = new ProgressDialog(buscagv.this);

		   protected void onPreExecute() {
			   //crea el progresdialog
			   pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          pDialog.setMessage(getString(R.string.anadiendo));
		          pDialog.setCancelable(true);
		          pDialog.show();
		          
		   }
			protected Void doInBackground(Void... params) {
				//Realiza la consulta a la base de datos con el nombre de la lista
			    		String name=mostrar.get(posicion);
			    		Cursor p = db.rawQuery("SELECT ruta, cancion, grupo, tiempo FROM valoraciones  WHERE cancion='"+name+"'",null);
			    		//Añade los elementos a la lista
			    		if (p.moveToFirst()) {
			    		    
			    		     do {
			    		    	 String cancion2 = "";
			    		    		String cancion3 = "";
			    		    		//añade elementos a la lista si es una canción online
			    		    	 try{
			    		    	 if(p.getString(0).substring(p.getString(0).indexOf("h"), 4).equals("http")){
			    		    			
										for(int u=0;u<=p.getString(0).length();u++){
											
					        		 try{
											if((p.getString(0).substring(u,u+1).equals("%"))){
												cancion2=cancion2+"'";
											
											}else{
												cancion2=cancion2+p.getString(0).substring(u,u+1);
											}
					        		 }catch (Exception e) {
						         		   
						         	   }
										}
									
										for(int u=0;u<=p.getString(1).length();u++){
											
							        		 try{
													if((p.getString(1).substring(u,u+1).equals("%"))){
														cancion3=cancion3+"'";
													
													}else{
														cancion3=cancion3+p.getString(1).substring(u,u+1);
													}
							        		 }catch (Exception e) {
								         		   
								         	   }
												}
										
			    		    		 Reroductor.linea.add(cancion2);
				    		    	 Reroductor.sruta.add(cancion3);
				    		    	 Reroductor.Grupo.add(p.getString(2));
				    		    	 Reroductor.Tiempo.add(p.getString(3));
			    		    	 }
			    		    	 }catch (Exception a){
			    		    		 //añade elementos a la lista si no es una canción online
			    						for(int u=0;u<=p.getString(0).length();u++){
											
							        		 try{
													if((p.getString(0).substring(u,u+1).equals("%"))){
														cancion2=cancion2+"'";
													
													}else{
														cancion2=cancion2+p.getString(0).substring(u,u+1);
													}
							        		 }catch (Exception e) {
								         		   
								         	   }
												}
											
												for(int u=0;u<=p.getString(1).length();u++){
													
									        		 try{
															if((p.getString(1).substring(u,u+1).equals("%"))){
																cancion3=cancion3+"'";
															
															}else{
																cancion3=cancion3+p.getString(1).substring(u,u+1);
															}
									        		 }catch (Exception e) {
										         		   
										         	   }
														}
			    		    		 File file = new File(cancion2);
			    		    	
				    		    	   if (file.exists()) {
				    		    		  
				    		    	 Reroductor.linea.add(cancion2);
				    		    	 Reroductor.sruta.add(cancion3);
				    		    	 Reroductor.Grupo.add(p.getString(2));
				    		    	 Reroductor.Tiempo.add(p.getString(3));
			    		    	 }
			    		    	 
			    		    	   }
			    		    	   
			    		     } while(p.moveToNext());
			    		}
			    		try{
			    	    	 //añade los nombres a la lista que contiene las rutas
			    	    	 int limite=Reroductor.linea.size();
			    	    	 String strings[] = new String[Reroductor.linea.size()];
			    	    	 String strings2[] = new String[Reroductor.linea.size()];
			    	    	 Reroductor.linea2=new ArrayList<String>(Reroductor.linea.size());;
			    	    	 for(int contador = 0; contador < limite; contador++){
			    	    		 strings[contador]=Reroductor.sruta.get(contador);
			    	    		 strings2[contador]=Reroductor.linea.get(contador);
			    	    		 try{
			    	    		 if(strings2[contador].substring(strings2[contador].indexOf("h"), 4).equals("http")){
			    	    		
		    	   	    	  
		    	   	    		        	 
		 		    	   	    			Reroductor.linea2.add(strings[contador]);
		    	   	    		         
			    	    		 }
			    	    		
			    	    		 
			    	    		 }catch (Exception a){
			    	    			 //Si no es una canción online añade los nombres de los metadatos en caso contrario,
			    	    			 //añade los nombres de archivo
			    	    			 try{
			    	    					MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
			    	   	    	         metaRetriever.setDataSource(strings2[contador]);
			    	   	    	         if(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)!=null){
			    	   	    	        	 strings[contador]=strings[contador].substring(0,strings[contador].length()-4);
			 		    	   	    			Reroductor.linea2.add(strings[contador]);
			    	   	    		         }else{
			    	   	    		        	 strings[contador]=strings[contador].substring(0,strings[contador].length()-4);
			 		    	   	    			Reroductor.linea2.add(strings[contador]);
			    	   	    		         }
			    	   	    			
			    	    			
			    	    			 }catch (Exception b){
			    	    				 
				   	    			 }
			    	       	    		
			    	       	    		
			    	   	    			 
			    	    		 }
			    	    	 }
			    	    	
			    	          
			    	    	}catch (Exception a){
			    			    
			    			  }
			    		//En el caso de estar activado el aleatorio, añade los elementos a la lista de forma aleatoria
				    	if (Reroductor.aleatorioo==true){
				    		try {
							Reroductor.estadoss=Reroductor.linea.size();
							//Coge la posición actual de reproducción para meter canciones sin incluirlo
		    				int k=0;
		    				for(int i=0; i<Reroductor.posale.length; i++){
		    					if(Reroductor.posale[i]==Reroductor.posicion){
		    						k=i;
		    					}
		    				}
		    				//Realiza una copia del array actual para empezar a meter en este sus posiciones
		    				Reroductor.posale2=new int [k];
		    				for(int i=0; i<k; i++){
		    					Reroductor.posale2[i]=Reroductor.posale[i];
		    				}
		    				// se vacia el array y se mete las posiciones anteriores y seguidamente se le meten las posiciones
		    				//nuevas sin la posición actual
		    				Reroductor.posale=new int [Reroductor.linea.size()];
		    	
		    				Reroductor.j=k;
		    				for(int i=0; i<k; i++){
		    					Reroductor.posale[i]=Reroductor.posale2[i];
		    				}
		    				//Se meten las posiciones nuevas a partir de la posción actual
		    				for(int i=k+1; i<Reroductor.posale.length; i++){
		        				int localizado= (int)(rnd.nextDouble() * Reroductor.estadoss);

								
		    		 	         	Reroductor.posale[i]=localizado;
		    		 	         	for (int t=0; t!=i; t++){
		    			         		
		    		         			while(Reroductor.posale[i]==Reroductor.posale[t]){
		    		         			localizado= (int)(rnd.nextDouble() * Reroductor.estadoss);
		    		         			if (t!=0){
				         					for (int y=0; y!=t; y++){
				         						while(localizado==Reroductor.posale[y]||Reroductor.posale[0]==localizado){
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
				return null;
			}
			   protected void onPostExecute(Void result) {
				   //Al terminar el thread cierra el progressdialog
				   pDialog.dismiss();
			   }
	   }
	   //Borra una valoración
	   private class threadborrar extends AsyncTask<Void, Void, Void> {
		   ProgressDialog pDialog = new ProgressDialog(buscagv.this);

		   protected void onPreExecute() {
			  //Antes de empezar activa el progressdialog
			   pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          pDialog.setMessage(getString(R.string.borrando));
		          pDialog.setCancelable(true);
		          pDialog.show();
		   }
			protected Void doInBackground(Void... params) {
				//Coge el nombre de la lista a borrar y realiza el borrado
	        	String name=mostrar.get(posicion);
	        	db.execSQL("DELETE FROM valoraciones WHERE cancion='"+name+"'");
				return null;

			}

			   protected void onPostExecute(Void result) {
				 //cierra el progressdialog y recarga la lista
				   pDialog.dismiss();
				   new threadbd().execute();
			   }
	   }
	   
	   //Borra todas las valoraciones
	   private class threadborrartodo extends AsyncTask<Void, Void, Void> {
		   ProgressDialog pDialog = new ProgressDialog(buscagv.this);

		   protected void onPreExecute() {
			  //Antes de empezar activa el progressdialog
			   pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          pDialog.setMessage(getString(R.string.borrando));
		          pDialog.setCancelable(true);
		          pDialog.show();
		   }
			protected Void doInBackground(Void... params) {
		//Borra todas las valoraciones
	        	db.execSQL("DELETE FROM valoraciones");
				return null;

			}

			   protected void onPostExecute(Void result) {
				 //cierra el progressdialog y recarga la lista
				   pDialog.dismiss();
				   new threadbd().execute();
			   }
	   }
	   
	   
	   //thread que muestra las canciones guardadas
	   private class threadbd extends AsyncTask<Void, Void, Void> {
		   ProgressDialog pDialog = new ProgressDialog(buscagv.this);
		
			  
		   int mostr=0;
    		 protected void onPreExecute() {
    			 //Antes de empezar muestra el progressdialog
    			 pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          pDialog.setMessage(getString(R.string.cargando_lista));
		          pDialog.setCancelable(true);
		          pDialog.show();
    		 }
			protected Void doInBackground(Void... params) {
				try{
					//Abre la base de datos realiza una consulta por id en orden descendiente
				myDbHelper.openDataBase();
		   		 
	    		db=myDbHelper.getReadableDatabase();
	    		
	      	  Cursor c = db.rawQuery("SELECT * FROM valoraciones",null);
	      	c.moveToFirst();
	      	
	      	int num=c.getInt(0);
	      	//Se crea tres arrays con el número de resultados obtenidos
	      	 mostrar = new ArrayList<String>(num);
	      	mostrar2 = new ArrayList<String>(num);
	      	mostrar3 = new ArrayList<String>(num);
	      	
	      	 //Recogemos el nombre de cada resultado
	      		
	      		
	      			
	      				 Cursor b = db.rawQuery("SELECT cancion, grupo, valoracion FROM valoraciones  order by valoracion desc",null);
	      				b.moveToFirst();
	      				try{
	      				mostrar.add(b.getString(0));
	           			mostrar2.add(b.getString(1));
	           			mostrar3.add(b.getString(2));
	                		while(b.moveToNext()){
	                		try{
	           			mostrar.add(b.getString(0));
	           			mostrar2.add(b.getString(1));
	           			mostrar3.add(b.getString(2));
	                		}catch (Exception a){
	          	        		
	            	  		  }
	      			}
				}catch (Exception a){
  	        		
  	  		  }
	      			try{
	      				//al terminar llama a que muestre la lista
	      				mostr=1;
	      				publishProgress();	
	      				
	          	
	          		}catch (Exception a){
	          			
	          		}
	      		
		  
				}catch (Exception a){
					//Si hubo algún error no muestra la lista
					publishProgress();	   		  
					}
				
				return null;

			}
			protected void onProgressUpdate(Void... values) {
				//Muestra la lista
				if (mostr==1){
					   try{
						   ArrayList<Titular> mList = new ArrayList<Titular>();
								//Se añaden a la lista todos los archivos y carpetas de esa ruta
								 Titular titular;
								
								 for (int i=0; i<mostrar.size();i++){
									 try{
									 titular= new Titular(mostrar.get(i),mostrar2.get(i),mostrar3.get(i)); 
									 mList.add(titular);
									 } catch (Exception e) {
											
										}
								 }
								
								 AdaptadorTitulares actualitatAdapter = new AdaptadorTitulares(buscagv.this, mList);
								   lv.setAdapter(actualitatAdapter);

					   }catch (Exception a){
				      		  
				  		  }
				}else{
					//Muestra que no hay listas
				 nlista.setVisibility(View.VISIBLE);
	        	    lv.setVisibility(View.INVISIBLE);
				}
	        	    
			}
			   protected void onPostExecute(Void result) {
				   //Cierra el progressdialog
				   
				   pDialog.dismiss();
			   }
		   }
	   
	   
		 //Adapta cada elemento a la lista personalizada
		 public class AdaptadorTitulares extends BaseAdapter {

			    private Activity mActivityAct;
			    private LayoutInflater mInflater;
			    private ArrayList<Titular> mLItems;
	//Se asigna su activity y arraylist
			    public AdaptadorTitulares(Activity a, ArrayList<Titular> it) {
			    	try{
			        mActivityAct = a;
			        mLItems = it;
			        mInflater = (LayoutInflater) mActivityAct
			                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    	} catch (Exception e) {
							
						}
			    }

			  

				public  class VistaH {
					//Se crean las variables que van a contener, el nombre de la canción, grupo y su valoracion
			        public TextView nombree;
			        public TextView grupoo;
			        public TextView valoor;
			    }

			    @Override
			    public int getCount() {
			    	//Se coge el tamaño del arraylist
			        return mLItems.size();
			    }

			    @Override
			    public Object getItem(int position) {
			        return position;
			    }

			    @Override
			    public long getItemId(int position) {
			        return position;
			    }

			    @Override
			    public View getView(int position, View convertView, ViewGroup parent) {
			        View vi = convertView;
			        VistaH vh = null;
	//Si lo que recoge  es nulo, se asigna el layout personalizado y sus elementos
			        if (vi == null) {
			            vi = mInflater.inflate(R.layout.listitem_valor, null);
			            vh = new VistaH();
			            vh.nombree = (TextView) vi.findViewById(R.id.LblTitulo);
			            vh.grupoo = (TextView) vi.findViewById(R.id.LblSubTitulo);
			            vh.valoor = (TextView) vi
			                    .findViewById(R.id.tiempo);
			            vi.setTag(vh);
			        }
	//Se van añadiendo los contenidos de los arrays de cancion, grupo etc, a sus respectivos textviews
			        vh = (VistaH) vi.getTag();

			        Titular notice = mLItems.get(position);
			        vh.nombree.setText(notice.getDate());
			        vh.grupoo.setText(notice.getTitle());
			        vh.valoor.setText(notice.getDescription());

			        return vi;
			    }

			    @Override
			    public void unregisterDataSetObserver(DataSetObserver observer) {
			        if (observer != null) {
			            super.unregisterDataSetObserver(observer);
			        }
			    }
			}
	   
		   //Dialog que muestra borrar todas las canciones valoradas


    private void crearDialogoBorrartodo() {
        new MaterialDialog.Builder(this)
                //.iconRes(R.drawable.dia_log)
                //.title(getString(R.string.tweetoface))
                .content(getString(R.string.borrar_todov))
                .positiveText(getString(R.string.borrar_todo))
                .negativeText(getString(R.string.cancelar))
                .positiveColorRes(android.R.color.white)
                .negativeColorRes(android.R.color.white)
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
//Llama al thread que borra todas las canciones valoradas
                        new threadborrartodo().execute();
                    }



                    @Override
                    public void onNegative(MaterialDialog dialog) {

                    }
                })
                .show();
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

