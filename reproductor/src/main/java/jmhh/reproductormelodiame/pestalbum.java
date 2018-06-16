package jmhh.reproductormelodiame;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jmhh.reproductormelodiame.R;


//import jmhh.reproductormelodiame.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.Session;

import static android.app.PendingIntent.getActivity;

public class pestalbum extends android.support.v4.app.Fragment implements OnItemClickListener{
	//Se declaran las variables
	public final  String baseDir =Environment.getExternalStorageDirectory().getAbsolutePath();
	public String baseDir2;
	private  File sdcardDir = new File(baseDir);
	private  File currentDir;
	public ListView lv;
	public Random rnd;
	public File[] actual;
	public int lugar;
	public ProgressBar barra;
	public ArrayAdapter<String> items;
	public ArrayAdapter<String> actualiza;
	public List<String> rutas;
	public List<String> rutas2;
	public boolean noinsert=false;
	public Button al;
	public TextView textoc;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//Se asigna el layout a la clase y se le pone el fondo negro
	  final View root = inflater.inflate(R.layout.albumxml, container, false);
	  
	     	RelativeLayout mlayout=(RelativeLayout)root.findViewById(R.id.blayout);
	    	    mlayout.setBackgroundColor(Color.BLACK);
	    	    //Se cogen los datos de los elementos del layout y la lista y se crea un random
	    	    barra=(ProgressBar)root.findViewById(R.id.progressBar1);
	    	    al=(Button)root.findViewById(R.id.album);
	    	    al.setTextColor(Color.WHITE);
	    	    lv=(ListView)root.findViewById(android.R.id.list);
	          	 lv.setOnItemClickListener(this);
	          	  textoc=(TextView)root.findViewById(R.id.textocarga);
			     	textoc.setText(getString(R.string.cargando_al));
			     	textoc.setTextColor(Color.WHITE);
	          	rnd = new Random();
	          //Se llama cuando se pulsa el boton de buscar álbumes
al.setOnClickListener(new Button.OnClickListener() 
	             {

	                 @Override
	                 public void onClick(View v) 
	                 {
	                		al.setVisibility(View.INVISIBLE);
	                		textoc.setVisibility(View.VISIBLE);
	                		barra.setVisibility(View.VISIBLE);
	                			//Si hay una tarjeta de memoria insertada
	                	       	if (Environment.getExternalStorageState().equals("mounted")) {
	                	       	//Se verifica que la ruta a buscar sea la ruta raiz y si no es asi, se va guarda dicha ruta
	                	        	int barras=0;
	                	        	int barras2=0;
	                	        	  
	                	    			for(int p=0;p<=baseDir.length();p++){
	                	    				
	                	            		 try{
	                	            			 if(baseDir.substring(p,p+1).equals("/")){
	                	    							
	                	    							barras++;
	                	    							barras2=p;
	                	    						}
	                	    						
	                	            		 }catch (Exception e) {
	                	    	         		   
	                	    	         	   }
	                	    					}
	                	    			if (barras>0){
	          		      					baseDir2=baseDir.substring(0,barras2);
	          		      				

	          		      		
	          		      					 sdcardDir = new File(baseDir2);
	          		      					 
	          		      				}else{
	          		      					baseDir2=baseDir;
	          		      				sdcardDir = new File(baseDir2);
	          		      				}
	                	    			// se crea un array adapter con el xml de una lista personalizada
	                				 items =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);
	                	//Activa el thread para cargar los álbumes
	                	       		new thread().execute();

	                	        	
	                		}else{
	                			try{
	                				//Si no hay una Tarjeta de memoria, muestra un elemento de la lista advirtiéndolo
	                	   			noinsert=true;

	                		          actualiza =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

	                		       	 	actualiza.add(getString(R.string.sin_sd2));

	                		    lv.setAdapter(actualiza);
	                				 }catch (Exception a){
	                					 
	                				 }	      
	                		}
	                 }
	             });
	          	 
	       	
	       	
	       	
	    	try{
	    		//Al mantener pulsado un elemento de la lista, si hay una SD habre el dialog de borrar
		   	lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		           public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
		               return onLongListItemClick(v,pos,id);
		           }

		           protected boolean onLongListItemClick(View v, int pos, long id) {
		        	   if (noinsert==false){
		        		   //Se asigna el nombre del elemento y su posición y crea el dialog
		        	   Reroductor.holagrupo=(String) lv.getItemAtPosition(pos);
						lugar=pos;
                           crearDialogoBorrar();
		             	/*Dialog dialogo = crearDialogoBorrar();
		    				dialogo.show();*/
		        	   }
		               return true;
		           }
		       });
		 	}catch (Exception e) {
		 		}
			return root;
	    	
	    	
	    	
	       	
	 }
	//Muestra el mensaje que se le pase
	 private void mostrarMensaje(String mensaje) {
			Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
		}
	 //Se llama al pulsar en un elemento de la lista
	 public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			// TODO Auto-generated method stub
		 //Si está insertada la SD coge el nombre del elemento de la lista y llama al dialog
		 if (noinsert==false){
	    	 Reroductor.holagrupo=(String) lv.getItemAtPosition(position);
             crearDialogoAlerta();
			 /*  Dialog dialogo = crearDialogoAlerta();
			   dialogo.show();*/
			 }else{
				 //Si antes no estaba insertada la SD, vuelve a comprobar y llama al thread de mostrar álbumes
	 			if (Environment.getExternalStorageState().equals("mounted")) {
	 				//Crea el array adapter con la lista personalizada
	 				 items =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

					 noinsert=true;
				   		new thread().execute();

				}
		 }
		}
//Dialog que pregunta si se quiere añadir a la lista


    private void crearDialogoAlerta() {
        new MaterialDialog.Builder(getActivity())
               //.iconRes(R.drawable.dia_log)
               // .title(getString(R.string.tweetoface))
                .content(getString(R.string.anadir))
                .positiveText(getString(R.string.si))
                .neutralText(getString(R.string.cancelar))
                .negativeText(getString(R.string.anadir_todo))
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
                        //Si se quiere añadir un álbum llama al thread correspondiente y cancela el dialog
                        new threadanadir().execute();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //Si se quiere añadir todos los álbumes llama al thread correspondiente y cancela el dialog

                        new threadtodo().execute();
                    }
                })
                .show();
    }

	 //Dialog que pregunta si se quiere borrar un álbum

    private void crearDialogoBorrar() {
        new MaterialDialog.Builder(getActivity())
                //.iconRes(R.drawable.dia_log)
              //  .title(getString(R.string.tweetoface))
                .content(getString(R.string.borrar_tarjeta))
                .positiveText(getString(R.string.si))
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
                        //Si se quiere borrar un álbum llama al thread correspondiente
                        new threadborrar().execute();
                    }



                    @Override
                    public void onNegative(MaterialDialog dialog) {

                    }
                })
                .show();
    }


	    //Thread que carga los álbumes
	    private class thread extends AsyncTask<Void, Void, Void>{

			@Override
			 protected void onPreExecute(){
				//Muestra el estado de carga y se asigna el arrayadapter a la lista personalizada
				 
		    	    lv.setVisibility(View.INVISIBLE);
		    	    barra.setVisibility(View.VISIBLE);
            		textoc.setVisibility(View.VISIBLE);
					 items =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

			}
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
              	int poscar=0;
                            	
            	//Se crea un array que va a contener las rutas de las carpetas
            	String carpetass[]=new String[1000];
            	//Recoge los archivos que hay en la SD
            	actual = sdcardDir.listFiles();
            	try{
            		//asigna el arrayadapter a la lista personalizada
   			 items =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);
            	} catch (Exception e) {
						
					}
            	//Se crean los arraylists que van a contener las rutas y los nombre de las canciones
            	rutas=new ArrayList<String>(10000);
            	rutas2=new ArrayList<String>(10000);
            	//Se mete si hay archivos en la SD
             	if (actual != null){
             		currentDir = sdcardDir;
             		//Ser recorre cada archivo de la SD
             		for (File f : actual) {
             			String name = f.getName();
             			//Si un archivo es un directorio se guarda en un array
             			if (f.isDirectory()){
             				
            				
             				 if (name.substring(0, 1).equals(".")!=true)
             				{
             					carpetass[poscar]=f.getAbsolutePath();
             					poscar++;
             				}
             			
             			}//Se comprueba que el archivo sea de audio
             			else if(name.substring(name.length()-4, name.length()).equals(".mp3")
		    					|| name.substring(name.length()-4, name.length()).equals(".MP3")
								|| name.substring(name.length()-4, name.length()).equals(".ogg")
								|| name.substring(name.length()-4, name.length()).equals(".OGG")
								|| name.substring(name.length()-4, name.length()).equals(".aac")
								|| name.substring(name.length()-4, name.length()).equals(".AAC")
								|| name.substring(name.length()-4, name.length()).equals(".wav")
								|| name.substring(name.length()-4, name.length()).equals(".WAV")
								|| name.substring(name.length()-4, name.length()).equals(".wma")
								|| name.substring(name.length()-4, name.length()).equals(".WMA")
								|| name.substring(name.length()-4, name.length()).equals(".mid")
								|| name.substring(name.length()-4, name.length()).equals(".MID")
								|| name.substring(name.length()-4, name.length()).equals(".amr")
								|| name.substring(name.length()-4, name.length()).equals(".AMR"))
         				{
             				//Si es un archivo de audio, se guarda su nombre y su ruta
             				int m=0;
             				rutas.add(f.getAbsolutePath());
             				rutas2.add(f.getName());
             				//Se recogen los metadatos de cada uno de los elementos que hay en cada carpeta
             				MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            		         metaRetriever.setDataSource(f.getAbsolutePath());
            		         
             				for(int i=0;i<=carpetass.length;i++) {
             					//Si en un elemento del array de los nombres de los álbumes ya existe, m se pone a 1 para que no lo repita en la lista 
             					try{
             						if (items.getItem(i).equals(getString(R.string.desconocido)) && (metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM))==null){
             							
             							m=1;
             						}
             						 if(items.getItem(i).equals(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM))){
             							m=1;
             					}
             					} catch (Exception e) {
             						
             					}
             					
             				}
             				//Si no existe el elemento en el array, se inserta el álbum
             				if (m==0){
             					if((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)==null)){
             						items.add(getString(R.string.desconocido));
             					}
             					else {
             						items.add(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
             					}
             				}
         			}
             			
             			
             		}
             		
             		
             	}
             	
             	for (int i=0;i<=poscar-1;i++){
             		try{
             			//Recoge de cada carpeta el número de archivos
             		File nuevo=new File(carpetass[i]);
             		actual = nuevo.listFiles();
//Si el archivo no es nulo, se mete
                	if (actual != null){
                 		currentDir = nuevo;
                 		
                 	//Coge el nombre del archivo y comprueba si es un directorio
                		for (File f : actual) {
                			String name = f.getName();
                			if (f.isDirectory()){
                				 if (name.substring(0, 1).equals(".")!=true)
                				{
                					 //va guardando los directorios
               					carpetass[poscar]=f.getAbsolutePath();
                 					poscar++;
                 				}
                 			
                 			}//Comprueba si es un archivo de audio
                			else if(name.substring(name.length()-4, name.length()).equals(".mp3")
    		    					|| name.substring(name.length()-4, name.length()).equals(".MP3")
    								|| name.substring(name.length()-4, name.length()).equals(".ogg")
    								|| name.substring(name.length()-4, name.length()).equals(".OGG")
    								|| name.substring(name.length()-4, name.length()).equals(".aac")
    								|| name.substring(name.length()-4, name.length()).equals(".AAC")
    								|| name.substring(name.length()-4, name.length()).equals(".wav")
    								|| name.substring(name.length()-4, name.length()).equals(".WAV")
    								|| name.substring(name.length()-4, name.length()).equals(".wma")
    								|| name.substring(name.length()-4, name.length()).equals(".WMA")
    								|| name.substring(name.length()-4, name.length()).equals(".mid")
    								|| name.substring(name.length()-4, name.length()).equals(".MID")
    								|| name.substring(name.length()-4, name.length()).equals(".amr")
    								|| name.substring(name.length()-4, name.length()).equals(".AMR"))
             				{
                 				int m=0;
                 				//Si es un archivo de audio, se guarda su nombre y su ruta
                 				rutas.add(f.getAbsolutePath());
                 				rutas2.add(f.getName());
                 				MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                		         metaRetriever.setDataSource(f.getAbsolutePath());
                		     	//Se recogen los metadatos de cada uno de los elementos que hay en cada carpeta
                 				for(int o=0;o<=carpetass.length;o++) {
                 					//Si en un elemento del array de los nombres de los álbumes ya existe, m se pone a 1 para que no lo repita en la lista 
                 					try{
                 						if (items.getItem(o).equals(getString(R.string.desconocido)) && (metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM))==null){
                 							m=1;
                 						}
                 						if(items.getItem(o).equals(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM))){
                 							m=1;
                 					}
                 					} catch (Exception e) {
                 						
                 					}
                 					
                 				}
                 				//Si no existe el elemento en el array, se inserta el álbum

                 				if (m==0){
                 				if((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)==null)){
                 					items.add(getString(R.string.desconocido));
             					}
             					else {
             						items.add(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                 		         
                 				}
                 				}
             			}
                 			
                 			
                 		}
                 		
                 		
                 	}
             		} catch (Exception e) {
 						
 					}
             		
             		
             	
             	}
             	//Se ordenan los nombres de las rutas alfabéticamente y se guardan en otro array sus nombres
            	Collections.sort(rutas, String.CASE_INSENSITIVE_ORDER);
             	rutas2=new ArrayList<String>(10000);
             	for(int ii=0;ii<rutas.size();ii++){
             		File ff=new File(rutas.get(ii));
             		rutas2.add(ff.getName());
             	}
				return null;
			}
			protected void onProgressUpdate(Void... values) {
				
			
			}
			 protected void onPostExecute(Void result){
			     
	           
				//Al terminar el thread el array con los álbumes se ordenan alfabeticamente y se muestra
				 items.sort(String.CASE_INSENSITIVE_ORDER);

	                	lv.setAdapter(items);

	                	   barra.setVisibility(View.INVISIBLE);
	               		textoc.setVisibility(View.INVISIBLE);
		    	    lv.setVisibility(View.VISIBLE);
			 }
		
   			
   				          }
	    //Se llama cuando se quiere añadir un álbum a la lista de reproducción
	    private class threadanadir extends AsyncTask<Void, Void, Void>{
			   ProgressDialog pDialog = new ProgressDialog(getActivity());

	    	 protected void onPreExecute(){
	    		 //Muestra el progressdialog
	    		 barra.setVisibility(View.VISIBLE);
		    	    lv.setVisibility(View.INVISIBLE);
		    	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			          pDialog.setMessage(getString(R.string.anadiendo));
			          pDialog.setCancelable(true);
			          pDialog.show();
	    	 }
			protected Void doInBackground(Void... params) {
				//Se recorre el array con las rutas
				for (int i=0;i<rutas.size();i++){
					
					
						//Se extrae los metadatos del archivo y se comprueba que concide con el álbum elegido para añadirlo al array 
						try{
							MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
			   		         metaRetriever.setDataSource(rutas.get(i));
						if ((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)==null)&&Reroductor.holagrupo.equals(getString(R.string.desconocido))){
							Reroductor.linea.add(rutas.get(i));
							Reroductor.sruta.add(rutas2.get(i));
 						}
 						if ((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM).equals(Reroductor.holagrupo))){
 							Reroductor.linea.add(rutas.get(i));
							Reroductor.sruta.add(rutas2.get(i));
 						}
						} catch (Exception e) {
     						
     					}
				}
//Se vuelve a añadir la lista de reproducción con los nuevos datos
             	
    recarga();
				return null;
			}
			  protected void onPostExecute(Void result){
				  //Cierra el progressDialog de carga
				  barra.setVisibility(View.INVISIBLE);
		    	    lv.setVisibility(View.VISIBLE);
		    	    pDialog.dismiss();
			  }
	    }
	    //Se llama cuando se quiere meter todos los álbumes
	    private class threadtodo extends AsyncTask<Void, Void, Void>{
			   ProgressDialog pDialog = new ProgressDialog(getActivity());

	    	 protected void onPreExecute(){
	    		 //Muestra el progressdialog de carga
	    		 barra.setVisibility(View.VISIBLE);
		    	    lv.setVisibility(View.INVISIBLE);
		    	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			          pDialog.setMessage(getString(R.string.anadiendo));
			          pDialog.setCancelable(true);
			          pDialog.show();
		    	    
	    	 }
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				//Añade todas las rutas y nombres de los archivos a la lista de reproducción
				for (int i=0;i<rutas.size();i++){
					
					
					
					
						Reroductor.linea.add(rutas.get(i));
					Reroductor.sruta.add(rutas2.get(i));
				
		}

				//Se vuelve a añadir la lista de reproducción con los nuevos datos
       recarga();
				return null;
			}
			  protected void onPostExecute(Void result){
				  //Se cierra el progerssdialog de carga
				  barra.setVisibility(View.INVISIBLE);
		    	    lv.setVisibility(View.VISIBLE);
		    	    pDialog.dismiss();
			  }
	    }
	    //Se llama cuando se quiere borrar un álbum entero
	    private class threadborrar extends AsyncTask<Void, Void, Void>{
			   ProgressDialog pDialog = new ProgressDialog(getActivity());

	    	 protected void onPreExecute(){
	    		 //Se muestra el progressdialog de carga
	    		 barra.setVisibility(View.VISIBLE);
		    	    lv.setVisibility(View.INVISIBLE);
		    	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			          pDialog.setMessage(getString(R.string.borrando));
			          pDialog.setCancelable(true);
			          pDialog.show();
	    	 }
			protected Void doInBackground(Void... params) {
				//se crean dos nuevos arraylist con el tamaño de los arrays de la lista
				List<String> 	 nuevos = new ArrayList<String>(rutas.size());
				List<String> 	 nuevos2 = new ArrayList<String>(rutas2.size());
				//Nos recorremos el array de canciones
				for (int i=0;i<rutas.size();i++){
					//Si coincide el albúm de la canción con el elegido se borra
					MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
   		         metaRetriever.setDataSource(rutas.get(i));
					
						
						try{
						if ((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)==null)&&Reroductor.holagrupo.equals(getString(R.string.desconocido))){
							
							String rutaa=rutas.get(i);
							
 		 		    		File filea = new File(rutaa);
 		 		    		filea.delete();
 						}
						else if ((metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM).equals(Reroductor.holagrupo))){
 							String rutaa=rutas.get(i);
							
 		 		    		File filea = new File(rutaa);
 		 		    		filea.delete();
 						}else{
 							//Si no coincide el nombre del álbum, se mete en el array
 							nuevos.add(rutas.get(i));
 							nuevos2.add(rutas2.get(i));
 						}
						} catch (Exception e) {
							nuevos.add(rutas.get(i));
 							nuevos2.add(rutas2.get(i));
     					}
				}//Se realiza una copia de los contenidos del los arrays de canciones
				rutas = new ArrayList<String>(nuevos.size());
				rutas2 = new ArrayList<String>(nuevos2.size());
				for (int i=0;i<nuevos.size();i++){
					rutas.add(nuevos.get(i));
					rutas2.add(nuevos2.get(i));
				}
				//Se actualiza el array de la lista de álbumes copiando los que no coincidan con el álbum borrado
				List<String> 	 nuevo = new ArrayList<String>(items.getCount());
	              for(int i=0;i<items.getCount();i++){
	            	  if(items.getItem(i).equals(Reroductor.holagrupo)){
	            	
	            	  }else{
	            		  nuevo.add(items.getItem(i));
	            	  }
	              }
	 			 items =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

	              for(int i=0;i<nuevo.size();i++){
	            	  items.add(nuevo.get(i));
	              }

				return null;
			}
			 protected void onPostExecute(Void result){
				 //Se comprueba que haya una tarjeta SD
					if (Environment.getExternalStorageState().equals("mounted")) {
					    try{
					    	//Muestra los álbumes por orden alfabético
					    	items.sort(String.CASE_INSENSITIVE_ORDER);

		                	lv.setAdapter(items);
		                	 }catch (Exception e) {
	
		                 	   }
					    
		              barra.setVisibility(View.INVISIBLE);
 			    	    lv.setVisibility(View.VISIBLE);
					}else{
						 barra.setVisibility(View.INVISIBLE);
	 			    	    lv.setVisibility(View.VISIBLE);
	            		mostrarMensaje(getString(R.string.sin_sd));
	            	}
					//Cancela el progressdialog de carga
					pDialog.dismiss();
			 }
	    	
	    }
    //Recarga la lista de reproducción
	public void recarga(){
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
	    
	    	         int cont=0;
	    	         int seg=0;
	    	         int min=0;
	    		 try{
	    			//Si es un archivo online el nombre del grupo es Online
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
		    	         cont=Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
		  		      	while(cont>=0){
				    		cont=cont-1000;
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
	   	    	         }//Si no hay metadatos el nombre del grupo se pone como Desconocido
	   	    	         else{
	   	    		        	 strings[contador]=strings[contador].substring(0,strings[contador].length()-4);
	   	    		        	Reroductor.linea2.add(strings[contador]);
		    	   	    		Reroductor.Grupo.add(getString(R.string.desconocido));	     		  
   	   	    		     
	   	    		         }
	    			
	    			 }catch (Exception b){
	    				 
  	    			 }	    	       	    			    	       	    		
	   	    			 
	    		 }
	    	 }
	    	
	          
	    	}catch (Exception a){
			    
			  }
     //Se mete en el caso de que esté activado el aleatorio
   	if (Reroductor.aleatorioo==true){
   		try {
			Reroductor.estadoss=Reroductor.linea.size();
			//Se controla que no conincida lo lo que hay en el array de aleatorio con la posición actual
			int k=0;
			for(int i=0; i<Reroductor.posale.length; i++){
				if(Reroductor.posale[i]==Reroductor.posicion){
					k=i;
				}
			}
			//Se realiza una copia del contenido del array de aleatorio
			Reroductor.posale2=new int [k];
			for(int i=0; i<k; i++){
				Reroductor.posale2[i]=Reroductor.posale[i];
			}
			  //Se vuelve a insertar las posiciones en aleatorio sin la posición actual
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

	
}
