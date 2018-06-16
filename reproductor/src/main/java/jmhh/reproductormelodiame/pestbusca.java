package jmhh.reproductormelodiame;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jmhh.reproductormelodiame.R;


//import jmhh.reproductormelodiame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.Session;

public class pestbusca extends Fragment implements OnItemClickListener {
	//Se declaran las variables
	public List<String> items;
	public Random rnd;
	public List<String> cancion;

	
	public final String baseDir =Environment.getExternalStorageDirectory().getAbsolutePath();
	public String baseDir2;
	public File sdcardDir = new File(baseDir);
	public File anterior=new File(baseDir);
	public File[] actual;
	private File currentDir;
	private AdaptadorTitulares adapter;
	public File fileguardar;
	public  int album=0;
	public  int grupo=0;
	public int lugar;
	public ArrayAdapter<String> actualiza;
	public boolean noinsert=false;
	public ListView lv;
	public String postactual;
	public RelativeLayout layouut;
	public TextView textoc;
	public ProgressBar progress;	
	public static final int batr = Menu.CATEGORY_SECONDARY;
	public static final int bsd = Menu.FIRST;
	public static ArrayList<String> lineaordena=new ArrayList<String>(1000);
	public static ArrayList<String> srutaordena=new ArrayList<String>(1000);
	public static ArrayList<String> Todos=new ArrayList<String>(1000);
	public static ArrayList<String> Grupos=new ArrayList<String>(1000);
	public static ArrayList<String> Tiempos=new ArrayList<String>(1000);
	 

	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		//Se asigna el layout a la clase y se le pone el fondo negro
	        final View root = inflater.inflate(R.layout.buscalayout, container, false);
	
	     	layouut=(RelativeLayout)root.findViewById(R.id.blayout);
	     	lv=(ListView)root.findViewById(android.R.id.list);
	     	textoc=(TextView)root.findViewById(R.id.textocarga);
	     	textoc.setText(getString(R.string.cargando));
	     	layouut.setBackgroundColor(Color.BLACK);
	     	textoc.setTextColor(Color.WHITE);
	        //Se cogen los datos del progressbar y la lista y se crea un random
	        rnd = new Random();
	     	progress=(ProgressBar)root.findViewById(R.id.progressBar1);
       	    
       	 lv.setOnItemClickListener(this);
      
        ListView l = (ListView) root.findViewById(android.R.id.list);
        //Se dice que el fragment pueda tener opciones de menú
        setHasOptionsMenu(true);
        
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
				 anterior=new File(baseDir2);
			}else{
				baseDir2=baseDir;
				sdcardDir = new File(baseDir2);
				 anterior=new File(baseDir2);
			}
        
        
        
        //Se llama cuando se mantiene pulsado un elemento de la lista
      	try{
      	l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
              public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                  return onLongListItemClick(v,pos,id);
              }

              protected boolean onLongListItemClick(View v, int pos, long id) {
            	  //Si está insertada la tarjeta de memoria, se comprueba si el elemento pulsado no es un directorio
            	  //en ese caso comprueba que sea un archivo de audio
              	if (noinsert==false){
              
              		try{
              			ListView	l = (ListView) root.findViewById(android.R.id.list);
              		String name = cancion.get(pos);
             		//asigna el archivo elegido
 		    		Reroductor.f = new File(currentDir, name);
 		    		if (Reroductor.f.isDirectory())
 		    		{
 		    			
 		    		}else{
 		    			try{
 		    	 			String sonido=Reroductor.f.getName();
 		    	 			if(sonido.substring(sonido.length()-4, sonido.length()).equals(".mp3")
 			    					|| sonido.substring(sonido.length()-4, sonido.length()).equals(".MP3")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".ogg")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".OGG")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".aac")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".AAC")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".wav")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".WAV")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".wma")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".WMA")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".mid")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".MID")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".amr")
 									|| sonido.substring(sonido.length()-4, sonido.length()).equals(".AMR"))
 		    	 			{//Si es un archivo de audio, se guarda y se llama al dialog que borra la canción
 		    	 				Reroductor.holagrupo=cancion.get(pos);
 		    	 				postactual=cancion.get(pos);
 		    	 				fileguardar=currentDir;
                                crearDialogoBorrar();
 		    	    /*          	Dialog dialogo = crearDialogoBorrar();
 		    	     				dialogo.show();*/
 		    	 				
 		    	 			} else {
 		    	 				
 		    	 			}
 		    	 			
 		    	 				}catch (Exception e) {
 		    	 					
 		    	 				}
 		    			if (album==1||grupo==1){
		    	 				Reroductor.holagrupo=cancion.get(pos);
		    	 				postactual=cancion.get(pos);
 		    			 		fileguardar=currentDir;
		    	 				lugar=pos;
                            crearDialogoBorrar();
		    	              /*	Dialog dialogo = crearDialogoBorrar();
		    	     				dialogo.show();*/
 		    			}
 		    			
 		    			
 		    		}
              	}catch (Exception e) {
	 					
	 				}
              	}
                  return true;
              }
          });
    	}catch (Exception e) {
    		}
      	
      	
    
      	 
      //Si hay una tarjeta de memoria insertada, se llama al thread que carga la lista
       	 if (Environment.getExternalStorageState().equals("mounted")) {
       		 try{
       			actual = sdcardDir.listFiles();
				 
		    	if (actual != null){
		    		currentDir = sdcardDir;
		    		
		new thread().execute();
			    	}
       		
       		 }catch (Exception a){
				 
			 }

       	 }else{//Si no hay una Tarjeta de memoria, muestra un elemento de la lista advirtiéndolo
       		try{
       			noinsert=true;
     	       actualiza =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

   	       	 	actualiza.add(getString(R.string.sin_sd2));

   	    lv.setAdapter(actualiza);
   	 lv.setVisibility(View.VISIBLE);
	 progress.setVisibility(View.INVISIBLE);
	 textoc.setVisibility(View.INVISIBLE);
   			 }catch (Exception a){
   				 
   			 }
       	 }
	    	
	        return root;
			  
	  }
//Dialog que pregunta si se quiere borrar la canción

    private void crearDialogoBorrar() {
        new MaterialDialog.Builder(getActivity())
                //.iconRes(R.drawable.dia_log)
               // .title(getString(R.string.tweetoface))
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
                        if (album==0&&grupo==0){
                            //Borra la canción elegida
                            String ruta=Reroductor.f.getAbsolutePath();
                            File file = new File(ruta);
                            file.delete();


                            //Si hay un atarjeta SD, se carga de nuevo la lista
                            if (Environment.getExternalStorageState().equals("mounted")) {
                                //Coge la ruta a cargar
                                String guardado=fileguardar.toString();
                                Reroductor.f = new File(guardado);
                                //Si un archivo es un directorio, recorre sus archivos.
                                if (Reroductor.f.isDirectory())
                                {//Si la ruta contiene elementos, se mete
                                    actual = Reroductor.f.listFiles();

                                    if (actual != null){
                                        currentDir = Reroductor.f;
                                        new thread().execute();
                                    }

                                }
                            }

                        }
                    }



                    @Override
                    public void onNegative(MaterialDialog dialog) {

                    }
                })
                .show();
    }



  	 //Se llama para añadir elementos a la lista de reproducción


    private void crearDialogoAlerta2() {
        new MaterialDialog.Builder(getActivity())
               // .iconRes(R.drawable.dia_log)
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
                        //En el caso de que se quiera añadir a la lista de reproducción se recoge su ruta y nombre
                        Reroductor.linea.add(Reroductor.f.getAbsolutePath());
                        Reroductor.sruta.add(Reroductor.f.getName());
                        //Se vuelve a añadir la lista de reproducción con los nuevos datos
                        addcanciones();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //En el caso de que se quiera añadir todos los elementos, se crean dos nuevos arraylists con la
                        //longitud de todos los elementos
                        lineaordena=new ArrayList<String>(actual.length);
                        srutaordena=new ArrayList<String>(actual.length);
                        //Nos recorremos todos los elementos comprobando que sean archivos de audio
                        for (File p : actual) {
                            String archivo=p.getName();
                            try{


                                if(archivo.substring(archivo.length()-4, archivo.length()).equals(".mp3")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".MP3")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".ogg")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".OGG")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".aac")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".AAC")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".wav")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".WAV")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".wma")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".WMA")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".mid")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".MID")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".amr")
                                        || archivo.substring(archivo.length()-4, archivo.length()).equals(".AMR"))
                                {
//En el caso de que sean de audio, se guarda su ruta y su nombre y se van ordenando alfabéticamente
                                    lineaordena.add(p.getAbsolutePath());
                                    srutaordena.add(p.getName());
                                    Collections.sort(lineaordena, String.CASE_INSENSITIVE_ORDER);
                                    Collections.sort(srutaordena, String.CASE_INSENSITIVE_ORDER);
                                }
                            }catch (Exception e) {

                            }

                        }
                        //Metemos en el array de la lista de reproducción todos los nombres y rutas recogidos anteriormente
                        for (int coloca=0;coloca<lineaordena.size();coloca++){
                            try{
                                Reroductor.linea.add(lineaordena.get(coloca));
                                Reroductor.sruta.add(srutaordena.get(coloca));
                            }catch (Exception e) {

                            }
                        }
                        //Se vuelve a añadir la lista de reproducción con los nuevos datos
                        addcanciones();


                    }
                })
                .show();
    }

//Se crean los elementos del menú
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		  menu.add(0,  bsd, 0, "SD").setIcon(R.drawable.ic_menu_save);
	        menu.add(1,  batr, 1, getString(R.string.anterior)).setIcon(R.drawable.back_1);
	    }

//Se definen las acciones de los elementos del menu
	 public boolean onOptionsItemSelected(MenuItem item) {

	        switch (item.getItemId()) {
	        //Vuelve a la ruta raiz
	        case bsd:
	        	//Se hace un tipo archivo con la ruta raiz y llama al thread para volver a cargar la lista
	        	if(lv.getVisibility()==View.VISIBLE){
	        	album=0;
	        	grupo=0;
	        	anterior= new File(baseDir2);
	        	try{
	        		actual = sdcardDir.listFiles();
					 
			    	if (actual != null){
			    		currentDir = sdcardDir;
			new thread().execute();
				    	}
	       	 }catch (Exception a){
				 
			 }
	        	}
	        	break;
	        	//Vuelve a la ruta anterior
	        case batr:
	        	//Se extrae la ruta anterior y llama al thread que carga la lista anterior
	        	if(lv.getVisibility()==View.VISIBLE){
	        	int barras=0;
	        	int barras2=0;
	        	  String cancion3="";
	        	  String cancion4=anterior.toString();
	        	  
   				for(int p=0;p<=cancion4.length();p++){
						
		        		 try{
		        			 if(cancion4.substring(p,p+1).equals("/")){
									
									barras++;
									barras2=p;
								}
								
		        		 }catch (Exception e) {
			         		   
			         	   }
							}
   				if (barras>0){
   					cancion3=cancion4.substring(0,barras2);
   				}else{
   					cancion3=cancion4;	
   				}
   				anterior= new File(cancion3);
   				album=0;
	        	grupo=0;
			   	 actual = anterior.listFiles();

			   	if (actual != null){
		    		currentDir = anterior;
			   	new thread().execute();
			   	}
	        	}
	        	break;
	        }
			return true;
	
	 }
	  
	 //Se llama para recargar la lista
	 private class thread extends AsyncTask<Void, Void, Void>{

		 protected void onPreExecute(){
			//Se muestra el estado de carga
			 lv.setVisibility(View.INVISIBLE);
			 progress.setVisibility(View.VISIBLE);
			 textoc.setVisibility(View.VISIBLE);

			 
		 }
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		   
	 	    		//Crea un array por cada canción,grupo etc a guardar

		    		items = new ArrayList<String>(actual.length);
		    		cancion = new ArrayList<String>(actual.length);
		    		Todos = new ArrayList<String>(actual.length);
		    		Grupos = new ArrayList<String>(actual.length);
		    		Tiempos = new ArrayList<String>(actual.length);
		    		//Se recorre cada archivo de la ruta
		    		for (File f : actual) {
		    			String name = f.getName();
		    			String ruta = f.getAbsolutePath();
		    			//Si un archivo es un directorio se guarda en un array
		    			if (f.isDirectory()){
		    				if (name.substring(0, 1).equals(".")!=true)
		    				{
		    					name += '/';
		 	    				cancion.add(name);
		    					items.add(name+"-"+ruta);
		    				}
		    				//Se comprueba que el archivo sea de audio
		    			}else if(name.substring(name.length()-4, name.length()).equals(".mp3")
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
		    			
						{//Si es un archivo de audio, se guarda su nombre y su ruta
		    				cancion.add(name);
		    					items.add(name+"-"+ruta);

		    		
		    			
		    		}
		    		
		    		
		    		}//Si no hay ningún archivo de audio ni carpetas, se avisa
		    		if (items.size()==0){
		    			Todos.add(getString(R.string.no_valido));
		    		Grupos.add("");
		    		Tiempos.add("");
		    		
		    		
		    		} 		
		    		if (items.size()!=0){
		    			//En el caso de que haya archivos válidos, se ordenan alfabéticamente
	    			Collections.sort(items, String.CASE_INSENSITIVE_ORDER);
	    			Collections.sort(cancion, String.CASE_INSENSITIVE_ORDER);
	    			//Una vez ordenados , se vuelve a recorrer los arrays
	    			for(int i=0; i<items.size();i++){
	    				//Se extrae su ruta y su nombre
	    				String path=items.get(i).substring(items.get(i).indexOf("-"+sdcardDir)+1,items.get(i).length());
	    				String canc=items.get(i).substring(0,items.get(i).indexOf("-"+sdcardDir));
	    				try{
	    				if(canc.substring(canc.length()-4, canc.length()).equals(".mp3")
		    					|| canc.substring(canc.length()-4, canc.length()).equals(".MP3")
								|| canc.substring(canc.length()-4, canc.length()).equals(".ogg")
								|| canc.substring(canc.length()-4, canc.length()).equals(".OGG")
								|| canc.substring(canc.length()-4, canc.length()).equals(".aac")
								|| canc.substring(canc.length()-4, canc.length()).equals(".AAC")
								|| canc.substring(canc.length()-4, canc.length()).equals(".wav")
								|| canc.substring(canc.length()-4, canc.length()).equals(".WAV")
								|| canc.substring(canc.length()-4, canc.length()).equals(".wma")
								|| canc.substring(canc.length()-4, canc.length()).equals(".WMA")
								|| canc.substring(canc.length()-4, canc.length()).equals(".mid")
								|| canc.substring(canc.length()-4, canc.length()).equals(".MID")
								|| canc.substring(canc.length()-4, canc.length()).equals(".amr")
								|| canc.substring(canc.length()-4, canc.length()).equals(".AMR")){
	    					//Se guarda el nombre sin la extensión
	    					Todos.add(canc.substring(0,canc.length()-4));
	    				}else{//Si no hay extensión o es distinta se guarda solo su nombre
	    					Todos.add(items.get(i).substring(0,items.get(i).indexOf("-"+sdcardDir)));
	    				}
	    			 }catch (Exception a){
	    					//Si hubo algún error al leer los datos se guarda solo su nombre
	    					Todos.add(items.get(i).substring(0,items.get(i).indexOf("-"+sdcardDir)));
	    				 }
	    				//En el caso de que sea una carpeta se mete en el array de grupos que es una carpeta
	    				if (Todos.get(i).substring(Todos.get(i).length()-1,Todos.get(i).length()).equals("/")){
	    					Grupos.add(getString(R.string.carpeta));
	    					 Tiempos.add("");
	    					
	    				}else{
try{
	//Si son canciones, se extraen sus metadatos y se añaden al sus respectivos arrays
			    				MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
			   	    	         metaRetriever.setDataSource(path);
			   	    	     
			   	    	      if(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)!=null){
			   	    	        	Grupos.add(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));	
			   	    	         }else{//Si no existe el grupo, se añade como desconocido
			    				Grupos.add(getString(R.string.desconocido));
			   	    	         }
			   	    	   int cont=Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
			    	         int seg=0;
			    	         int min=0;
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
			    	   				Tiempos.add(""+min+":"+seg);
			    	   			}else{
			    	   			Tiempos.add(""+min+":"+0+seg);
			    	   			}
					    	}else{
					    		Tiempos.add(""+min+":"+seg);
					    	}				    		         
			    				 }catch (Exception a){
			    					//Si hubo algún error al leer los datos, se pone como defectuoso
			    					 Grupos.add(getString(R.string.defectuoso));
			    					 Tiempos.add("");
			    				 }
	    				}
	    				
	    			}
					
				
		    		
					
		    
		    	}
			return null;
		}
		protected void onPostExecute(Void result){
			//Al terminar, se quita el estado de carga
			 lv.setVisibility(View.VISIBLE);
			 progress.setVisibility(View.INVISIBLE);
			 textoc.setVisibility(View.INVISIBLE);
	
			 ArrayList<Titular> mList = new ArrayList<Titular>();
			//Se añaden a la lista todos los archivos y carpetas de esa ruta
			 Titular titular;
			
			 for (int i=0; i<Todos.size();i++){
				 try{
				 titular= new Titular(Todos.get(i),Grupos.get(i),Tiempos.get(i)); 
				 mList.add(titular);
				 } catch (Exception e) {
						
					}
			 }
			
			 AdaptadorTitulares actualitatAdapter;
            actualitatAdapter = new AdaptadorTitulares(getActivity(), mList);

            lv.setAdapter(actualitatAdapter);
					
					 
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
				//Se crean las variables que van a contener, el nombre de la canción, grupo y su tiempo
		        public TextView nombree;
		        public TextView grupoo;
		        public TextView tiempoo;
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
//Si lo que recoge es nulo, se asigna el layout personalizado y sus elementos
		        if (vi == null) {
		            vi = mInflater.inflate(R.layout.listitem_titular, null);
		            vh = new VistaH();
		            vh.nombree = (TextView) vi.findViewById(R.id.LblTitulo);
		            vh.grupoo = (TextView) vi.findViewById(R.id.LblSubTitulo);
		            vh.tiempoo = (TextView) vi
		                    .findViewById(R.id.tiempo);
		            vi.setTag(vh);
		        }
//Se van añadiendo los contenidos de los arrays de cancion, grupo etc, a sus respectivos textviews
		        vh = (VistaH) vi.getTag();

		        Titular notice = mLItems.get(position);
		        vh.nombree.setText(notice.getDate());
		        vh.grupoo.setText(notice.getTitle());
		        vh.tiempoo.setText(notice.getDescription());

		        return vi;
		    }

		    @Override
		    public void unregisterDataSetObserver(DataSetObserver observer) {
		        if (observer != null) {
		            super.unregisterDataSetObserver(observer);
		        }
		    }
		}

	//Se mete al pulsar en un elemento de la lista
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
	// TODO Auto-generated method stub
	 try{
		 //Se mete si hay una tarjeta SD insertada
	 if (noinsert==false){
		 //Comprueba si es una canción de audio
			if(cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".mp3")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".MP3")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".ogg")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".OGG")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".aac")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".AAC")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".wav")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".WAV")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".wma")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".WMA")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".mid")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".MID")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".amr")
					|| cancion.get(position).substring(cancion.get(position).length()-4, cancion.get(position).length()).equals(".AMR")){
				//Si es de audio, coge su posición y su archivo y llama a al dialog de añadir o no a la lista
				String name = cancion.get(position);
				Reroductor.f = new File(currentDir, name);
				 Reroductor.holagrupo=cancion.get(position);
                crearDialogoAlerta2();

			}
	 
	 else{
	 try{
		
//Si no es de audio, es que es una carpeta, por lo que coge su posición y archivo y llama al thread de entrar dentro
		 //de esa carpeta
			String name = cancion.get(position);
	 		postactual=cancion.get(position);
	fileguardar=currentDir;
	Reroductor.f = new File(currentDir, name);
	 String hola=Reroductor.f.toString();
	 
	 anterior=new File(hola);
	try{
		 if (grupo!=1||album!=1){
			 if (Reroductor.f.isDirectory())
			 		{
			 			actual = Reroductor.f.listFiles();
			 			//Se mete si hay archivos en la ruta
			 	    	if (actual != null){
			 	    		currentDir = Reroductor.f;
			 new thread().execute();
			 	    	}
			 		}
		 }
	 }catch (Exception a){
		 
	 }
	//Comprueba si es un archivo de audio para entrar en el dialog de añadirlo o no 
		try{
		String sonido=Reroductor.f.getName();
		if(sonido.substring(sonido.length()-4, sonido.length()).equals(".mp3")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".MP3")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".ogg")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".OGG")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".aac")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".AAC")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".wav")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".WAV")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".wma")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".WMA")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".mid")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".MID")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".amr")
				|| sonido.substring(sonido.length()-4, sonido.length()).equals(".AMR"))
		{
			Reroductor.name2=cancion.get(position);
            crearDialogoAlerta2();

			
		} else{
			
		}
		
			}catch (Exception e) {
			
			}
		

		
}catch (Exception e) {
		
	}
	 }
}else{
	//Se no está insertada la SD, lo vueve a comprobar
  	 if (Environment.getExternalStorageState().equals("mounted")) {
  		 //Si está montada llama al thread de cargar la lista
  		 try{
  			actual = sdcardDir.listFiles();
			 
	    	if (actual != null){
	    		currentDir = sdcardDir;
	new thread().execute();
		    	}
  		 }catch (Exception a){
			 
		 }

	    		
				noinsert=false;
				

	    	
      	 }else{
      		 //Si no está insertada vuelve a advertirlo
      		try{
      			noinsert=true;
      		 actualiza =  new ArrayAdapter<String>(getActivity(), R.layout.list_item);

  	       	 	actualiza.add(getString(R.string.sin_sd2));
  	       	 lv.setVisibility(View.VISIBLE);
  			 progress.setVisibility(View.INVISIBLE);
  			 textoc.setVisibility(View.INVISIBLE);
  	    lv.setAdapter(actualiza);

  			 }catch (Exception a){
  				 
  			 }
      	 }
}
}catch (Exception a){
	 
}
}
    //Se recarga la lista de reproducción
public void addcanciones(){
		try{
	    	 
	    	 int limite=Reroductor.linea.size();
	    	 String strings[] = new String[Reroductor.linea.size()];
	    	 String strings2[] = new String[Reroductor.linea.size()];
	    	 Reroductor.linea2=new ArrayList<String>(Reroductor.linea.size());
	    	 Reroductor.Grupo=new ArrayList<String>(Reroductor.linea.size());
	    	 Reroductor.Tiempo=new ArrayList<String>(Reroductor.linea.size());
	    	//Se van añadiendo las rutas y nombres al nuevo array
	    	 for(int contador = 0; contador < limite; contador++){
	    		 strings[contador]=Reroductor.sruta.get(contador);
	    		 strings2[contador]=Reroductor.linea.get(contador);
	    		 
	    			
	    	         int cont=0;
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
	   	    	
	    	   
			    	
	   	    		         }else{//Si no hay metadatos el nombre del grupo se pone como Desconocido
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
		}//Se realiza una copia del contenido del array de aleatorio
		Reroductor.posale2=new int [k+1];
		for(int i=0; i<=k; i++){
			Reroductor.posale2[i]=Reroductor.posale[i];
		}
		Reroductor.posale=new int [Reroductor.linea.size()];
		 //Se vuelve a insertar las posiciones en aleatorio sin la posición actual
		Reroductor.j=k;
		for(int i=0; i<=k; i++){
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
    						while(localizado==Reroductor.posale[p]){
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
