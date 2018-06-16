package jmhh.reproductormelodiame;

import android.app.Service;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


public class EqService extends Service {
	
	  private Equalizer myEq;
	  
	  private int maxLevel, minLevel;

    public static boolean isRunning;

	  @Override
	  public void onCreate() {

		  super.onCreate();
		  
		  // Se coge el ecualizador con prioridad 10 y la sesión 0
	      myEq = new Equalizer(10,0);
	      myEq.setEnabled(true);
	      

	      
	      // Se coge el valor máximo y mínimo del ecualizador
		  maxLevel=(int)myEq.getBandLevelRange()[1];
	      minLevel=(int)myEq.getBandLevelRange()[0];
	      
	       
	  }

	  @Override
	  public IBinder onBind(Intent intent) {

	      return mBinder;
	  }
	  //Al finalizar EqService se libera el eculizador
	  @Override
	  public void onDestroy() {

		  myEq.release();

		  myEq=null;

		  isRunning=false;
	  }
	  
	  
//Se recoge el mínimo y el máximo nivel de equalizador, el número de bandas, la frecuencia intermedia, 
	  //el nivel actual de la banda y se inserta el cambio que se la haya hecho a una banda.
	  //También devuelve si está activado el servicio.
	  //Todo esto se recoge de EqInterface.aidl
	  private final EqInterface.Stub mBinder = new EqInterface.Stub() {
			public int getBandLevelLow() throws RemoteException {			
				return minLevel;
			}
			public int getBandLevelHigh() throws RemoteException {			
				return maxLevel;
			}
			public int getNumberOfBands() throws RemoteException {	
				return (int)myEq.getNumberOfBands();
			}
			public int getCenterFreq(int band) throws RemoteException {			
				return myEq.getCenterFreq((short)band);
			}
			public int getBandLevel(int band) throws RemoteException {		
				return (int)myEq.getBandLevel((short)band);
			}
			public void setBandLevel(int band, int level) throws RemoteException {		
				myEq.setBandLevel((short)band,(short)level);
			}
			public boolean isRunning() throws RemoteException {
				return isRunning;
			}
			
			

	  };
}

