package jmhh.reproductormelodiame;

import android.content.Context;
import android.content.SharedPreferences;
//Clase necesitada por la clase TweetHelper
public class Preferencias_Helper {
	// Objetos.
	private SharedPreferences settings;
	private ConstantesConfiguracion constants;

	public Preferencias_Helper(String Shared_pref_name, Context context) {
		// Creamos referencia al shared preferences.
		settings = context.getSharedPreferences(Shared_pref_name, Context.MODE_PRIVATE);
	}

	/**
	 * Metodo publico que retorna T o F si existe el valor.
	 * @param val
	 * @return
	 */
	public boolean isExist(String val) {
		// Si no existe retorna null.
		String value = settings.getString(val, null);

		if (value == null) {
			return false;
		}else{
			 return true;
		}
	}

	/**
	 * Metodo publico que retorna un String con el contenido del parametro val sacado del Shared Preferences.
	 * @param val
	 * @return
	 */
	public String Get_stringfrom_shprf(String val) {
		String valor = settings.getString(val, null);
		return valor;

	}

	/**
	 * Metodo publico que escribe un valor en l String.
	 * @param clave
	 * @param valor
	 */
	public void Write_String(String clave, String valor) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(clave, valor);
		editor.commit();
	}

	/**
	 * Metodo publico que borra un valor.
	 * @param clave
	 */
	public void Remove_Value(String clave) {
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(clave);
		editor.commit();
	}
}