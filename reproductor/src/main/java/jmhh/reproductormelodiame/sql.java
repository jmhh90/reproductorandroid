package jmhh.reproductormelodiame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {
	//Ruta por defecto de las bases de datos en el sistema Android
	private final static String DB_PATH = "/data/data/jmhh.reproductormelodiame/databases/";
	 private final static String DB_NAME = "listamm";
	 private SQLiteDatabase myDataBase; 
	 private final Context myContext;
	 
	 public sql(Context context) {
	    	super(context, DB_NAME, null, 1);
	        this.myContext = context;
	    }	
	 
	 public void createDataBase(Context contexto) throws IOException{
		 
	    	//Comprueba si ya existe la base de datos
	    	boolean dbExist = checkDataBase();
	 
	 
	 
	    	if(dbExist){
	    		//Si existe la base de datos no hace nada
	    	}else{
	    		//Si no existe se llama a este método que crea una nueva base de datos en la ruta por defecto
	        	this.getReadableDatabase();
	 
	        	try {
	        		//Copia nuestra database.sqlite en la nueva base de datos creada
	        		
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error("Error copying database");
	 
	        	}
	    	}
	 
	    }
	 
	    
	     // Comprueba si ya existe nuestra base de datos
	    
	    private boolean checkDataBase(){
	 
	    	SQLiteDatabase checkDB = null;
	 
	    	try{
	    		String myPath = DB_PATH + DB_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    	}catch(SQLiteException e){
	 
	    		
	 
	    	}
	 
	    	if(checkDB != null){
	 
	    		checkDB.close();
	 
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	 
	 
	   
	     // Copia nuestra base de datos sqlite de la carpeta assets a nuestra nueva base de datos
	    
	    private void copyDataBase() throws IOException{
	 
	    	//Abre nuestra base de datos del fichero
	    	
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    	//La dirección de nuestra nueva base de datos

	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	//Abre la nueva base de datos
	  	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//Transfiere bytes desde nuestro archivo a la nueva base de datos
	    	
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Cierra losOutPutStream
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	 
	    
	      //Abre la base de datos
	  
	    public void openDataBase() throws SQLException{
	 
	        String myPath = DB_PATH + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    }
	 //Cierra la base de datos
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
	 
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
		}

		

		
	}
	
