package jmhh.reproductormelodiame;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import jmhh.reproductormelodiame.R;


import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentLista extends ListFragment {
	AdaptadorTitulares adapter;
	public DragSortListView lv;
	public DragSortController mController;
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Asignamos a la lista el DragSortListView
    lv = (DragSortListView) getListView(); 
//Le decimos que llame a onDrop cuando se mueve un elemento de la lista
   lv.setDropListener(onDrop);



//Se llama al mantener pulsado un elemento de la lista de reproducción
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> av, View v,
					int pos, long id) {
				return onLongListItemClick(v, pos, id);
			}

			protected boolean onLongListItemClick(View v, int pos, long id) {
				try {
					//Si hay elementos en la lista, se mete
				if (Reroductor.cancela == false) {
					if (Reroductor.online == 0) {
						//Recoge la posición de la canción
						Reroductor.lugar = pos;
						//Dependiendo de si es una canción online o no llama a un dialog u otro para borrar la canción
						if (Reroductor.Grupo.get(Reroductor.lugar).equals("Online")){
                            crearDialogoBorraDesc();

						}else{
                            crearDialogoBorrar();

						}
					}
				}
				
				} catch (Exception e) {
				}
				return true;
			}
		});


	
	}
	
    
    
    @Override
    //Al cargar el View cogemos la lista con el DragSort
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        lv = (DragSortListView) inflater.inflate(getLayout(), container, false);
//Se pone para que se puedan controlar los elementos de la lista verticalmente cuando se pulsa sobre el imagebutton de mover
        mController = buildController(lv);
        lv.setFloatViewManager(mController);
        lv.setOnTouchListener(mController);


        return lv;
    }
    //Le asignamos al layout el layout con el DragSort
    protected int getLayout() {
        return R.layout.fragmentmueve;
    }
    //Se le llama al mover un elemento de la lista
	private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {  
                	//Se mete si es distinta la posición inicial a la final del movimiento de la lista que se ha realizado
                    if (from != to) {
                    	//Llama que borre de la posición inicial e inserte en la posición final el elemento de la lista
                        adapter.remove(from);
                       adapter.insert(to);
                       try{
                    	   //Si la posición inicial movida es igual a la posición de reproduccíón
                    	   //actual, se mete
                       if (from==Reroductor.posicion&&Reroductor.reproSonido.isPlaying()){
                    	   //Le asignamos la posición final a la posición de reproducción
                    	   Reroductor.posicion=to;
                       }else{
                    	 //Si la posición inicial movida es mayor a la posición de reproduccíón y la posición final
                    	   //menor o igual a la posición de reproduccíón
                    	   //actual, se mete y se aumenta en uno la reproducción actual
                       if (from>Reroductor.posicion&&Reroductor.reproSonido.isPlaying()&&to<=Reroductor.posicion){
                    	   Reroductor.posicion++;
                    	 //Si la posición inicial movida es menor a la posición de reproduccíón y la posición final
                    	   //mayor o igual a la posición de reproduccíón
                    	   //actual, se mete y se disminuye en uno la reproducción actual
                       }else if (from<Reroductor.posicion&&Reroductor.reproSonido.isPlaying()&&to>=Reroductor.posicion){
                    	   Reroductor.posicion--;
                       }
                       }              
                       } catch (Exception e) {
                    	    
                       }
                    }
                }
            };
	
	
   
                    //Se asigna el imagebutton para que se muevan los elementos del lv al pulsar sobre el
                    public DragSortController buildController(DragSortListView dslv) {
                    	
                        DragSortController controller = new DragSortController(dslv);
                        controller.setDragHandleId(R.id.image_mover);
                        
                     
                        return controller;
                    }
	
	
//Se mete al cargar la activity
	@Override
	public void onResume() {
	    super.onResume();
	    try {
		if (Reroductor.linea2.size() > 0) {
		//Si hay elementos en la lista de reproducción, llama a que lo cargue
		
				Recargar();
				Reroductor.online = 0;
				Reroductor.acancionessem = 0;
		}else{
			try {
				//Si no hay elementos lo advierte en la lista
				Reroductor.actualiza = new ArrayList<String>(1);
				Reroductor.actualiza.add(getString(R.string.no_canciones));
				
				setListAdapter(new ArrayAdapter(getActivity(), R.layout.list_item,
						Reroductor.actualiza));
				
			} catch (Exception b) {

			}
		}
		} catch (Exception a) {
			//Si hubo algún error en la carga, muestra que no hay canciones
			try {
				Reroductor.actualiza = new ArrayList<String>(1);
				Reroductor.actualiza.add(getString(R.string.no_canciones));

				setListAdapter(new ArrayAdapter(getActivity(), R.layout.list_item,
						Reroductor.actualiza));
				
			} catch (Exception b) {

			}
		}
	
	   
	
	    
	}
	//Se mete al pulsar en un elemento de la lista de reproducción
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {



//Habilita el botón de play
		Reroductor.estado = 0;
		Reroductor.play2.setEnabled(true);
		try {

//Se mete si hay elementos en la lista
			if (Reroductor.cancela == false) {

				if (Reroductor.online == 0) {
					//Comprueba si está activado la reproducción aleatoria 
					if (Reroductor.aleatorioo == true) {
						//Comprueba si ya estaba activada la variable de aleatoria y que haya una lista de reproduccion hecha
						//y si es así crea un array con las posiciones aleatorias
						if (Reroductor.empiezaale == false && Reroductor.linea.size() != 0) {
//Activa el booleano de aleatorio y crea una lista aleatoria
							Reroductor.posicion = position;
							creaaleatorio();
							//Si hay alguna lista de reproducción asignada se mete
							if (Reroductor.linea.size() != 0) {
								int canciones = Reroductor.linea.size();
								//Si la posicion actual de reproduccion es igual o mayor que el número de canciones,
								//se pone a cero
								if (Reroductor.posicion >= canciones) {
									Reroductor.posicion = 0;

								}

								try {
									try {
										//Comprueba si es una canción online  y si es así se comprueba
										//que haya conexión a internet para empezar a reproducir
										if (Reroductor.linea
												.get(Reroductor.posicion)
												.substring(
														Reroductor.linea.get(Reroductor.posicion)
																.indexOf("h"),
														4).equals("http")) {
											ConnectivityManager connec = (ConnectivityManager) getActivity()
													.getSystemService(Context.CONNECTIVITY_SERVICE);

											NetworkInfo redes = connec
													.getActiveNetworkInfo();
											boolean comprueba = false;
											if (redes != null
													&& redes.isConnected()) {
												comprueba = true;
											}

											if (comprueba == false) {
												//Si no hay conexión llama a que ponga todas las variables a su estado inicial
												((Reroductor)getActivity()).mostrarMensaje(getString(R.string.sin_red));
												((Reroductor)getActivity()).ponercero2();
												((Reroductor)getActivity()).ponercero();

											} else {
												//Si hay conexión de guarda la URL
												 URL url;
												 String cancion3="";
									        	    boolean espacio=false;
									        	    //Recorremos la ruta por si hay algún espacio para así ponerle %20
								  				for(int p=0;p<=Reroductor.name2.length();p++){
														
										        		 try{
										        			 if(Reroductor.name2.substring(p,p+1).equals(" ")){
																	cancion3=cancion3+"%20"+Reroductor.name2.substring(p,p+1);
																	espacio=true;
																}
																if((Reroductor.name2.substring(p,p+1).equals("%"))||(Reroductor.name2.substring(p,p+1).equals("2"))
																		||(Reroductor.name2.substring(p,p+1).equals("0"))||(Reroductor.name2.substring(p,p+1).equals(" "))){
																	
																
																}else{
																	cancion3=cancion3+Reroductor.name2.substring(p,p+1);
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
								         				//Asignamos la url
										        	 if (espacio==true){
									        		 url = new URL(cancion4);
										        	 }else{
										        		 url = new URL(Reroductor.name2);
										        	 }
												HttpURLConnection urlConnection = null;
								 				try {
								 					//Abrimos la conexión
								 					urlConnection = (HttpURLConnection) url.openConnection();
								 				} catch (IOException e2) {
								 					// TODO Auto-generated catch block
								 					e2.printStackTrace();
								 				}
								 		        	 
								 	        	    //establecemos el método Get para nuestra conexión
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
								 					//Recogemos el tamaño para comprobar si está online
								 	       	    int totalSize = urlConnection.getContentLength();
								         	
								         	    if (totalSize<=1000){
								     
								         	((Reroductor)getActivity()).mostrarMensaje(getString(R.string.caido));
								         	
								         	    }else{
								 		        	 
								 		        	 //Si está online ponemos variables a su estado inicial y llamamos a empieza para que comienze la reproducción
								         	    	((Reroductor)getActivity()).ponercero2();
								         	    	((Reroductor)getActivity()).ponercero();
													((Reroductor)getActivity()).empieza(v);
								         	    }
								 	        	   } catch (IOException e2) {
									 					// TODO Auto-generated catch block
									 					e2.printStackTrace();
									 				}

												

											}

										}
									} catch (Exception e) {
										//Si hubo algún error llamamos a empieza para reproducir
										((Reroductor)getActivity()).ponercero2();
										((Reroductor)getActivity()).ponercero();
										//Comprobamos si es online
										try{
											if (Reroductor.linea.get(position)
															.substring(
																	Reroductor.linea.get(position)
																			.indexOf("h"), 4)
															.equals("http")) {
											}
											} catch (Exception j) {
												Reroductor.cargabuffer=false;
											}
										
										((Reroductor)getActivity()).empieza(v);

									}
								} catch (Exception e) {

								}
							}
						} else//Si la variable aleatoria no está activada
							//y hay alguna canción reproduciendose, se activa y se crea un array con las posiciones aleatorias
						{


							//Recoge la posición reproducir y crea la lista aleatoria
							Reroductor.posicion = position;
							creaaleatorio();

							try {
//Para el contador del tiempo y la canción
								Reroductor.counter.cancel();
								Reroductor.reproSonido.stop();
							} catch (Exception b) {

							}
							
						//llama a poner todas las variables a su estado inicial y a comenzar la reproducción 
							Reroductor.imagen2.setImageDrawable(null);
							((Reroductor)getActivity()).ponercero2();
							((Reroductor)getActivity()).ponercero();
							((Reroductor)getActivity()).empieza(v);


						}

					}
//Se mete en el caso de que no esté activado el aleatorio
					else {
						//Recoge la posición a reproducir
						Reroductor.posicion = position;
						//Comprueba si es una cancIón online para poder activar el buffer y si es así se comprueba
						//que haya conexión a internet para empezar a reproducir
						try {
							if (Reroductor.linea
									.get(Reroductor.posicion)
									.substring(
											Reroductor.linea.get(Reroductor.posicion).indexOf("h"), 4)
									.equals("http")) {

								ConnectivityManager connec = (ConnectivityManager) getActivity()
										.getSystemService(Context.CONNECTIVITY_SERVICE);

								NetworkInfo redes = connec
										.getActiveNetworkInfo();
								boolean comprueba = false;
								if (redes != null && redes.isConnected()) {
									comprueba = true;
								}

								if (comprueba == false) {

									//se de deshabilita la barra, se para reproducción y el contador,
									//se pone todo a cero y se borra la notificación
									ponercero();
								} else {

									try {
//Para el contador del tiempo y lo que se esté reproduciendo
										Reroductor.counter.cancel();
										Reroductor.reproSonido.stop();
									} catch (Exception e) {

									}
									//Pone las variables a su estado inicial y llama a la reproducción
								
									Reroductor.imagen2.setImageDrawable(null);
									((Reroductor)getActivity()).ponercero();
									((Reroductor)getActivity()).ponercero2();
						
									((Reroductor)getActivity()).empieza(v);


								}

							}
						} catch (Exception e) {
							try {
//En el caso de que haya algún error, cancela el contador del tiempo y la reprodución 
								Reroductor.counter.cancel();
								Reroductor.reproSonido.stop();
							} catch (Exception b) {

							}
						//	Pone las variables a su estado inicial y llama a la reproducción


                            Reroductor.imagen2.setImageDrawable(null);
							((Reroductor)getActivity()).ponercero();
							((Reroductor)getActivity()).ponercero2();
							
							((Reroductor)getActivity()).empieza(v);


						}

					}
				}
			}
			//Si hay abierta otra activity y hay una lista de reprodución, la vuelve a cargar
			if (Reroductor.online == 1) {


				try {
					if (Reroductor.linea2.size() > 0) {
						Recargar();
						Reroductor.online = 0;
						Reroductor.acancionessem = 0;

					} 
				} catch (Exception a) {
			

				}

			}

		} catch (Exception a) {
			try {
				//Pone las variables a su posición inicial
				ponercero();
			} catch (Exception e) {

			}
		}

	}//Vuelve a recargar la activity
	private void Recargar() {

		try {
			adapter = new AdaptadorTitulares(getActivity());
	        setListAdapter(adapter);

		} catch (Exception a) {

		}

	}	 //Adapta cada elemento a la lista personalizada
	class AdaptadorTitulares extends BaseAdapter {
public String sruta2;
public String linea2;
public int index;
public int top;
		private LayoutInflater mInflater;

		public AdaptadorTitulares(Context context) {

			mInflater = LayoutInflater.from(context);

		}

		public void insert(int to) {
			// Se añaden la ruta y nombre (en array de reproducción) 
			//a la posición final donde se haya movido el elemento del lv
			Reroductor.sruta.add(to, sruta2);
			Reroductor.linea.add(to, linea2);
			//Se vuelven a insertar los datos en la lista de reproducción
			reinserta();
			//Pone la posicion anterior del scroll
			lv.setSelectionFromTop(index, top);
		}

		public void remove(int from) {
			//Recoge la posición del scroll
			index = lv.getFirstVisiblePosition();
			View v = lv.getChildAt(0);
			top = (v == null) ? 0 : v.getTop();		
			//Se coge la posición anterior a la posicion final del lv
			sruta2=Reroductor.sruta.get(from);
			linea2=Reroductor.linea.get(from);
			//Se borran el elemento del array de reproducción(ruta y nombre) 
			//de la posición anterior a la posicion final del lv
			Reroductor.sruta.remove(from);
			Reroductor.linea.remove(from);
	
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			//Si lo que recoge no es nulo, se asigna el layout personalizado y sus elementos
			if (convertView == null) {

				convertView = mInflater
						.inflate(R.layout.listitem_titular2, null);

			}
			
			
			 
			TextView lblTitulo = (TextView) convertView
					.findViewById(R.id.LblTitulo);
			lblTitulo.setText(Reroductor.linea2.get(position));

			TextView lblSubtitulo = (TextView) convertView
					.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText(Reroductor.Grupo.get(position));

			TextView lblTiempo = (TextView) convertView
					.findViewById(R.id.tiempo);
			lblTiempo.setText(Reroductor.Tiempo.get(position));

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//Se coge el tamaño del arraylist
			return Reroductor.linea2.size();

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}
	
//Dialog que borra un elemento de la lista

    private void crearDialogoBorrar() {
        new MaterialDialog.Builder(getActivity())
               // .iconRes(R.drawable.dia_log)
               // .title(getString(R.string.tweetoface))
                .content(getString(R.string.eliminar))
                .positiveText(getString(R.string.si))
                .neutralText(getString(R.string.cancelar))
                .negativeText(getString(R.string.borrar_todo))
                .positiveColorRes(android.R.color.white)
                .negativeColorRes(android.R.color.white)
                        //.neutralColor(android.R.color.white)
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
                        //Borra el nombre y la ruta de la canción elegida
                        borrar();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {

//Se mete si se quiere borrar toda la lista de reproducción
                            borrartodo();


                    }
                })
                .show();
    }




	//Se llama si se quiere borrar una canción online



    private void crearDialogoBorraDesc() {
        new MaterialDialog.Builder(getActivity())
                // .iconRes(R.drawable.dia_log)
                // .title(getString(R.string.tweetoface))
                .content(getString(R.string.elimina_descarga))
                .positiveText(getString(R.string.borrar_boton))
                .neutralText(getString(R.string.descargar))
                .negativeText(getString(R.string.borrar_todo))
                .positiveColorRes(android.R.color.white)
                .negativeColorRes(android.R.color.white)
                        //.neutralColor(android.R.color.white)
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
                        //Borra el nombre y la ruta de la canción elegida
                        borrar();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {
//Si se elige descargar, coge la url y llama a la descarga
                        Reroductor.ruta=Reroductor.linea.get(Reroductor.lugar);
                        ((Reroductor)getActivity()).descarga();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
//Se mete en el caso de que se quiera borrar todos los elementos de la lista de reproducción
                        borrartodo();


                    }
                })
                .show();
    }

	//Crea una lista aleatoria
	public void creaaleatorio(){
		//Activa el booleano de aleatorio 
		Reroductor.empiezaale = true;
		Reroductor.estadoss = Reroductor.linea.size();
		Reroductor.posale = new int[Reroductor.estadoss];
		Reroductor.j = 0;

		Reroductor.posale[0] = Reroductor.posicion;
		for (int u = 1; u < Reroductor.estadoss; u++) {
			int localizado = (int) (Reroductor.rnd.nextDouble() * Reroductor.estadoss);
			Reroductor.posale[u] = localizado;

			for (int t = 1; t != u; t++) {

				while (Reroductor.posale[u] == Reroductor.posale[t]
						|| Reroductor.posale[u] == Reroductor.posicion) {
					localizado = (int) (Reroductor.rnd.nextDouble() * Reroductor.estadoss);
					Reroductor.posale[u] = localizado;

				}

			}
		}
		//asigna a la posición de reproducción actual la posición cero del array
		Reroductor.posicion = Reroductor.posale[Reroductor.j];
	}
	public void ponercero(){
		//se de deshabilita la barra, se para reproducción y el contador,
		//se pone todo a cero y se borra la notificación
		try {
			Reroductor.barra2.setEnabled(false);
			Reroductor.text3.setText("");
			Reroductor.text4.setText("");
			Reroductor.reproSonido.stop();
			Reroductor.counter.cancel();
			Reroductor.	barra2.setProgress(0);
			Reroductor.	barra2.setSecondaryProgress(0);
			Reroductor.progreso = 0;
			Reroductor.min = 0;
			Reroductor.	min2 = 0;
			Reroductor.	seg = 0;
			Reroductor.posicion = 0;
			Reroductor.estado2 = 0;
			Reroductor.seg2 = 0;
			Reroductor.	estados = 0;
			Reroductor.	duracion2.setText(Reroductor.min2 + ":0" + Reroductor.seg2);
			Reroductor.timee2.setText("" + Reroductor.min + ":0" + Reroductor.seg);
			Reroductor.play2.setImageResource(R.drawable.play_1);
			Reroductor.imagen2.setImageDrawable(null);
			Reroductor.notManager.cancel(1);

		} catch (Exception e) {
			Reroductor.play2.setImageResource(R.drawable.play_1);
		}
	}
	public void borrar(){
		//Borra el nombre y la ruta de la canción elegida
		Reroductor.sruta.remove(Reroductor.lugar);
		Reroductor.linea.remove(Reroductor.lugar);
//Si coincide lo que se borra con la posición de reproducción actual, se llama a que se paralice todo y se pongan
		//las variables a su posición inicial
		if (Reroductor.lugar == Reroductor.posicion) {
			((Reroductor)getActivity()).ponercerosample();
			((Reroductor)getActivity()).ponercero();
			((Reroductor)getActivity()).ponercero2();
		}
		try{
			//Si la posición actual de reproducción es menor que la posición a borrar y no está activado el aleatorio, se mete
		if (Reroductor.lugar <Reroductor. posicion 
				//&& Reroductor.aleatorioo == false
				//&& Reroductor.posale.length > 0
				) {
			//Se le resta una posición a la elegida para que no haya problemas con la reprodución
			Reroductor.posicion--;
	
		}
		} catch (Exception e) {
			
		}
		//Se mete en el caso de que esté activado el aleatorio
		if (Reroductor.aleatorioo == true) {

			Reroductor.estadoss = Reroductor.linea.size();
			//Se crea un nuevo array de aleatorio con una posición menos
			Reroductor.posale2 = new int[Reroductor.posale.length - 1];
			//Se realiza una copia del contenido del array de aleatorio sin que coincida la posición a borrar
			int k = 0;
			for (int i = 0; i < Reroductor.posale.length; i++) {
				if (Reroductor.posale[i] != Reroductor.lugar) {
					try {
						Reroductor.posale2[k] = Reroductor.posale[i];
						k++;
					} catch (Exception a) {

					}
				}
			}
			//Se meten las posiciones en el array de aleatorio original
			Reroductor.posale = new int[Reroductor.posale2.length];
			for (int i = 0; i < Reroductor.posale2.length; i++) {
				if (Reroductor.posale2[i] > Reroductor.lugar && Reroductor.posale2[i] > 0) {
					Reroductor.posale2[i]--;
				}

				Reroductor.posale[i] = Reroductor.posale2[i];

			}
			try {
				//Si hubo un error y
				//si la posición actual de reproducción es menor que la posición a borrar y si la posición de reprodución
				//es distinto a cero, se le resta uno a la posición de reproducción
				if (Reroductor.lugar < Reroductor.posicion && Reroductor.j != 0 && Reroductor.posale[Reroductor.j] != 0) {
					Reroductor.j--;
				}
			} catch (Exception a) {

			}

		}
		
//Se vuelven a insertar los datos en la lista de reproducción
	reinserta();
		
	}
	public void borrartodo(){
		//Se mete en el caso de que se quiera borrar todos los elementos de la lista de reproducción
		try {

			int limite = Reroductor.linea.size();
			String strings[] = new String[Reroductor.linea.size()];
			Reroductor.linea2 = new ArrayList<String>(Reroductor.linea.size());
			Reroductor.Grupo = new ArrayList<String>(Reroductor.linea.size());
			Reroductor.Tiempo = new ArrayList<String>(Reroductor.linea.size());
			;//Recorre el array de rutas y se van borrando las rutas y nombres de las canciones
			for (int contador = 0; contador < limite; contador++) {
				strings[contador] = Reroductor.sruta.get(contador);
				try {

					Reroductor.sruta.removeAll(Reroductor.sruta);

					Reroductor.linea.removeAll(Reroductor.linea);

				} catch (Exception a) {

				}
			}

		} catch (Exception a) {

		}
//Pone en el estado inicial todas las variables y recarga la lista
		((Reroductor)getActivity()).ponercero();
		((Reroductor)getActivity()).ponercerosample();
		((Reroductor)getActivity()).ponercero2();
	Recargar();
	}
	public void reinserta(){
		try {
//Se vuelven a insertar los datos en la lista de reproducción
			int limite = Reroductor.linea.size();
			String strings[] = new String[Reroductor.linea.size()];
			Reroductor.linea2 = new ArrayList<String>(Reroductor.linea.size());
			Reroductor.Grupo = new ArrayList<String>(Reroductor.linea.size());
			Reroductor.Tiempo = new ArrayList<String>(Reroductor.linea.size());
			MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
			//Vamos recorryendo el array de rutas
			for (int contador = 0; contador < limite; contador++) {
				strings[contador] = Reroductor.sruta.get(contador);
//Se comprueba si es una canción online para añadirlo
				try {

					if (Reroductor.linea
							.get(contador)
							.substring(
									Reroductor.linea.get(contador).indexOf("h"), 4)
							.equals("http")) {
						Reroductor.linea2.add(strings[contador].substring(0,
								strings[contador].length() - 4));
						Reroductor.Grupo.add("Online");
						Reroductor.Tiempo.add("");

					}

				} catch (Exception a) {
					//Si no es online
					//Se van extrayendo los metadatos para ir añadiendo el nombre del grupo, la canción y el tiempo
					metaRetriever.setDataSource(Reroductor.linea.get(contador));
					int cont = Integer.parseInt(metaRetriever
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
					int seg = 0;
					int min = 0;
					while (cont >= 0) {
						cont = cont - 1000;
						seg = seg + 1;
						if (seg == 60) {
							min = min + 1;
							seg = 0;
						}
					}
					seg--;
					if (seg < 10) {
					
						if(seg==-1){
	    	   				min--;
	    	   				seg=59;
	    	   				Reroductor.Tiempo.add("" + min + ":" + seg);
	    	   			}else{
	    	   				Reroductor.Tiempo.add("" + min + ":" + 0
									+ seg);
	    	   			}
					} else {
						Reroductor.Tiempo.add("" + min + ":" + seg);
					}
					strings[contador] = strings[contador].substring(0,
							strings[contador].length() - 4);

					if (metaRetriever
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) != null) {
						Reroductor.linea2.add(strings[contador]);
						Reroductor.Grupo.add(metaRetriever
								.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
					
					} else {
						Reroductor.linea2.add(strings[contador]);
						Reroductor.Grupo.add(getString(R.string.desconocido));				
					}

				}
			}

			

		} catch (Exception a) {

		}
		//llama a recargar la lista
		Recargar();
	}

}

