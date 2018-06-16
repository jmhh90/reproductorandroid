package jmhh.reproductormelodiame;



import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class ecu extends ActionBarActivity {
	private  AdView adView;
		TextView tg1;
		TextView tg2;
		TextView tg3;
		TextView tg4;
		TextView tg5;
		TextView tp1;
		TextView tp2;
		TextView tp3;
		TextView tp4;
		TextView tp5;
		TextView tp6;
		TextView tp7;
		TextView tp8;
		TextView tp9;
		TextView tp10;
		SeekBar see1;
		SeekBar see2;
		SeekBar see3;
		SeekBar see4;
		SeekBar see5;
		public LinearLayout layout2;
		public Button res;
		public static int lv1;
		public static int lv2;
		public static int lv3;
		public static int lv4;
		public static int lv5;
    public RelativeLayout layout;
    public RelativeLayout layout3;


    // mService es el objeto que usa los métodos de la interface
	 EqInterface mService = null;
		

	       
		
	 public void onCreate(Bundle savedInstanceState) { 
	    	
	    	super.onCreate(savedInstanceState);

	        setContentView(R.layout.ecu);
         //Se hace la barra de título personalizada
         Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);

         setSupportActionBar(toolbar);
         this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //Se declaran los elementos del layout
	      res =   (Button)findViewById(R.id.buttonres);
	        tg1 = (TextView)findViewById(R.id.textView1);
	        tg2 = (TextView)findViewById(R.id.textView4);
	        tg3 = (TextView)findViewById(R.id.textView7);
	        tg4 = (TextView)findViewById(R.id.textView10);
	        tg5 = (TextView)findViewById(R.id.textView13);
	        tp1 = (TextView)findViewById(R.id.textView2);
	        tp2 = (TextView)findViewById(R.id.textView3);
	        tp3 = (TextView)findViewById(R.id.textView5);
	        tp4 = (TextView)findViewById(R.id.textView6);
	        tp5 = (TextView)findViewById(R.id.textView8);
	        tp6 = (TextView)findViewById(R.id.textView9);
	        tp7 = (TextView)findViewById(R.id.textView11);
	        tp8 = (TextView)findViewById(R.id.textView12);
	        tp9 = (TextView)findViewById(R.id.textView14);
	        tp10 = (TextView)findViewById(R.id.textView15);
	        see1 = (SeekBar)findViewById(R.id.Band1);
	        see2 = (SeekBar)findViewById(R.id.Band2);
	        see3 = (SeekBar)findViewById(R.id.Band3);
	        see4 = (SeekBar)findViewById(R.id.Band4);
	        see5 = (SeekBar)findViewById(R.id.Band5);

         layout=(RelativeLayout)findViewById(R.id.layout);
         layout3=(RelativeLayout)findViewById(R.id.layoutc);

         layout.setBackgroundColor(Color.TRANSPARENT);
         layout3.setBackgroundColor(Color.BLACK);

         //titulo cabecera
		         
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.barratitulo);
	        
	      //Se asigna al layout anuncio la publicidad y la carga

	       	      	 adView = new AdView(this);
	       	 		adView.setAdUnitId("ca-app-pub-8831350722190477/8350482349");
	       	 		adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
	                    layout2 = (LinearLayout)findViewById(R.id.anuncio);
 
	       		 layout2.addView(adView);
	       		 AdRequest adRequest = new AdRequest.Builder()
	       		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	       		    .addTestDevice("TEST_DEVICE_ID")
	       		    .build();

	       		   
	       adView.loadAd(adRequest);
	       
	     //llama a que cambie la pantalla según su orientación
			Configuration config = getResources().getConfiguration();

			onConfigurationChanged(config);
	       
	
	       
	    // Inicia el servicio del ecualizador
	        startService(new Intent(EqService.class.getName()));
	        
	        // Llama a mconnection para ir cambiando los valores del ecualizador
	        bindService(new Intent(EqService.class.getName()),
	                mConnection, Context.BIND_AUTO_CREATE);
	        
	 
	 }
	 //Es llamado desde Oncreate
	   private ServiceConnection mConnection = new ServiceConnection() {
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	        	// recoge el servicio del ecualizador
	            mService = EqInterface.Stub.asInterface(service);
	            
	            // Llama a setupUI para interactuar con el ecualizador
	            
	            setupUI();
	        }

	        public void onServiceDisconnected(ComponentName className) {
	            // Si se desconecta el servicio, mservice se pone a null.
	            
	            mService = null;
	        }
	    };
	    
	    private void setupUI(){
	    
			try {
//Se muestra en la banda uno su valor en HZ y sus valores mínimo y máximo de db, todo esto junto al SeekBar declarado
		            tg1.setText((mService.getCenterFreq(0) / 1000) + " Hz");
   		            
		            tp1.setText((mService.getBandLevelLow() / 100) + " dB");
       
		           tp2.setText((mService.getBandLevelHigh() / 100) + " dB");	            
		         
//Se asigna el valor máximo del SeekBar
		            see1.setMax(mService.getBandLevelHigh() - mService.getBandLevelLow());
		          //Se asigna el valor mínimo del SeekBar
		            see1.setProgress(mService.getBandLevel(0)-mService.getBandLevelLow());
		            // Se recoge la mitad del valor para poder restaurar dicho valor cuando se solicite
		            lv1 = (int)((see1.getMax()/2)+mService.getBandLevelLow());
//cuando se produce un cambio en el SeekBar, se traslada a la banda correspondiente del ecualizador
		            see1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		                public void onProgressChanged(SeekBar seekBar, int progress,
		                        boolean fromUser) {
		                    try {
								mService.setBandLevel(0, (int) (progress + mService.getBandLevelLow()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
		                }
		
		                public void onStartTrackingTouch(SeekBar seekBar) {}
		                public void onStopTrackingTouch(SeekBar seekBar) {}
		            });
		
//Se muestra en la banda dos su valor en HZ y sus valores mínimo y máximo de db, todo esto junto al SeekBar declarado

		        
		            tg2.setText((mService.getCenterFreq(1) / 1000) + " Hz");

		           		            
		            tp3.setText((mService.getBandLevelLow() / 100) + " dB");
	      
		          
		           tp4.setText((mService.getBandLevelHigh() / 100) + " dB");
		            	         
		         //Se asigna el valor máximo del SeekBar
		            see2.setMax(mService.getBandLevelHigh() - mService.getBandLevelLow());
		            //Se asigna el valor mínimo del SeekBar
		            see2.setProgress(mService.getBandLevel(1)-mService.getBandLevelLow());
		            // Se recoge la mitad del valor para poder restaurar dicho valor cuando se solicite
		            lv2 = (int)((see2.getMax()/2)+mService.getBandLevelLow());
		//cuando se produce un cambio en el SeekBar, se traslada a la banda correspondiente del ecualizador
		            see2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		                public void onProgressChanged(SeekBar seekBar, int progress,
		                        boolean fromUser) {
		                    try {
								mService.setBandLevel(1, (int) (progress + mService.getBandLevelLow()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
		                }
		
		                public void onStartTrackingTouch(SeekBar seekBar) {}
		                public void onStopTrackingTouch(SeekBar seekBar) {}
		            });
		  
		          //Se muestra en la banda tres su valor en HZ y sus valores mínimo y máximo de db, todo esto junto al SeekBar declarado

		            tg3.setText((mService.getCenterFreq(2) / 1000) + " Hz");

		           		            
		            tp5.setText((mService.getBandLevelLow() / 100) + " dB");
		      
		          
		           tp6.setText((mService.getBandLevelHigh() / 100) + " dB");
		            	         
		         //Se asigna el valor máximo del SeekBar
		            see3.setMax(mService.getBandLevelHigh() - mService.getBandLevelLow());
		          //Se asigna el valor mínimo del SeekBar
		            see3.setProgress(mService.getBandLevel(2)-mService.getBandLevelLow());
		            // Se recoge la mitad del valor para poder restaurar dicho valor cuando se solicite
		            lv3 = (int)((see3.getMax()/2)+mService.getBandLevelLow());
		        	//cuando se produce un cambio en el SeekBar, se traslada a la banda correspondiente del ecualizador
		            see3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		                public void onProgressChanged(SeekBar seekBar, int progress,
		                        boolean fromUser) {
		                    try {
								mService.setBandLevel(2, (int) (progress + mService.getBandLevelLow()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
		                }
		
		                public void onStartTrackingTouch(SeekBar seekBar) {}
		                public void onStopTrackingTouch(SeekBar seekBar) {}
		            });
		    
			          //Se muestra en la banda cuatro su valor en HZ y sus valores mínimo y máximo de db, todo esto junto al SeekBar declarado

		            tg4.setText((mService.getCenterFreq(3) / 1000) + " Hz");

		           		            
		            tp7.setText((mService.getBandLevelLow() / 100) + " dB");
				      
		          
		           tp8.setText((mService.getBandLevelHigh() / 100) + " dB");
		            	         
		         //Se asigna el valor máximo del SeekBar
		            see4.setMax(mService.getBandLevelHigh() - mService.getBandLevelLow());
		          //Se asigna el valor mínimo del SeekBar
		            see4.setProgress(mService.getBandLevel(3)-mService.getBandLevelLow());
		            // Se recoge la mitad del valor para poder restaurar dicho valor cuando se solicite
		            lv4 = (int)((see4.getMax()/2)+mService.getBandLevelLow());
		        	//cuando se produce un cambio en el SeekBar, se traslada a la banda correspondiente del ecualizador
		            see4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		                public void onProgressChanged(SeekBar seekBar, int progress,
		                        boolean fromUser) {
		                    try {
								mService.setBandLevel(3, (int) (progress + mService.getBandLevelLow()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
		                }
		
		                public void onStartTrackingTouch(SeekBar seekBar) {}
		                public void onStopTrackingTouch(SeekBar seekBar) {}
		            });
		            
			          //Se muestra en la banda cinco su valor en HZ y sus valores mínimo y máximo de db, todo esto junto al SeekBar declarado

		            tg5.setText((mService.getCenterFreq(4) / 1000) + " Hz");

		           		            
		            tp9.setText((mService.getBandLevelLow() / 100) + " dB");
		      
		          
		           tp10.setText((mService.getBandLevelHigh() / 100) + " dB");
		            	         
		         //Se asigna el valor máximo del SeekBar
		            see5.setMax(mService.getBandLevelHigh() - mService.getBandLevelLow());
		            //Se asigna el valor mínimo del SeekBar
		            see5.setProgress(mService.getBandLevel(4)-mService.getBandLevelLow());
		            // Se recoge la mitad del valor para poder restaurar dicho valor cuando se solicite

		            lv5 = (int)((see5.getMax()/2)+mService.getBandLevelLow());
		        	//cuando se produce un cambio en el SeekBar, se traslada a la banda correspondiente del ecualizador

		            see5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		                public void onProgressChanged(SeekBar seekBar, int progress,
		                        boolean fromUser) {
		                    try {
								mService.setBandLevel(4, (int) (progress + mService.getBandLevelLow()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
		                }
		
		                public void onStartTrackingTouch(SeekBar seekBar) {}
		                public void onStopTrackingTouch(SeekBar seekBar) {}
		            });
		            
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	    }
	 

		
		//Cambia la pantalla según su orientación
		public void onConfigurationChanged(Configuration newConfig) {
		    super.onConfigurationChanged(newConfig);
		 
		    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                layout2.setVisibility(LinearLayout.GONE);
                res.setVisibility(View.INVISIBLE);


		        
		    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                layout2.setVisibility(LinearLayout.VISIBLE);
                res.setVisibility(View.VISIBLE);

		    }
		}
		//Al pulsar el botón de restaurar valores, pone todas las bandas del ecualizador a su posición inicial
		public void restau(View view){
	
			see1.setProgress(see5.getMax()/2);
			see2.setProgress(see5.getMax()/2);
			see3.setProgress(see5.getMax()/2);
			see4.setProgress(see5.getMax()/2);
			see5.setProgress(see5.getMax()/2);
			
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
