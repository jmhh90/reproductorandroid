package jmhh.reproductormelodiame;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.util.Log;

public class TweetHelper {
	// Clase para acceder a twitter de la lib twitter4j.
	private Twitter twitter;
		
	private RequestToken mRequestToken = null;
	ConstantesConfiguracion constantes = new ConstantesConfiguracion();

	// Datos de Twitter.
	public String OAUTH_CONSUMER_KEY = constantes.OAUTH_CONSUMER_KEY;
	public String OAUTH_CONSUMER_SECRET = constantes.OAUTH_CONSUMER_SECRET;
	public String CALLBACKURL = constantes.TWITTER_CALLBACK;

	// Registros a guardar en Shared preferences.
	public String TW_ACCTOKEN = constantes.TW_ACCTOKEN;
	public String TW_ACCTOKEN_SECRET = constantes.TW_ACCTOKEN_SECRET;
		
	// Instancia a clase para manejar el acceso al Shared preferences.
	Preferencias_Helper shared_preference;
 
	/**
	 * Constructor recibe como parametro el contexto .
	 * @param context
	 */
	public TweetHelper(Context context) {
		// Creo referencia para manejar shared preferences.
		shared_preference = new Preferencias_Helper(constantes.SHARED_PREF_NAME, context);

		// Empezamos a crear objeto para interactuar con twitter.
		twitter = new TwitterFactory().getInstance();
		mRequestToken = null;
			
		// Seteamos claves para la autenticacion usando OAuth.
		twitter.setOAuthConsumer(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET);

		String callbackURL = CALLBACKURL;
		
		try{
			// Tomamos request token en base a callback URL
			// es utilizado para en el web view para obtener las claves.
			mRequestToken = twitter.getOAuthRequestToken(callbackURL);
		}catch(TwitterException e){
		     e.printStackTrace();
		}

	}

	/**
	 * Metodo publico que graba un nuevo OAuth en el shared preferences.
	 * @param OAuth
	 */
	public void store_OAuth_verifier(String OAuth) {
		AccessToken at = null;

		try{
			at = twitter.getOAuthAccessToken(mRequestToken, OAuth);
			shared_preference.Write_String(TW_ACCTOKEN, at.getToken());
			shared_preference.Write_String(TW_ACCTOKEN_SECRET, at.getTokenSecret());
		}catch (TwitterException e){
		     e.printStackTrace();
		}
	}

	/**
	 * Metodo publico que devuelve la autentication URL.
	 * @return
	 */
	public String get_authenticationURL() {
		return mRequestToken.getAuthenticationURL();
	}

	/**
	 * Metodo publico que borra del shared preferences valores TW_ACCTOKEN y TW_ACCTOKEN_SECRET.
	 */
	public void logOff() {
		shared_preference.Remove_Value(TW_ACCTOKEN);
		shared_preference.Remove_Value(TW_ACCTOKEN_SECRET);
	}

	/**
	 * Metodo publico que verifica si en el shared preferences estan guardados:
	 * - TW_ACCTOKEN
	 * - TW_ACCTOKEN_SECRET
	 * @return
	 */
	public boolean verificar_logindata(){			
		if (shared_preference.isExist(TW_ACCTOKEN)){
			Log.e("shrpref.isExist(TW_ACCTOKEN)->", "true");
			if (shared_preference.isExist(TW_ACCTOKEN_SECRET)){							
				// Ok estan estos datos guardados.
				Log.e("shrpref.isExist(TW_ACCTOKEN_SECRET)->","true");
				return true;
			}else{
			     Log.e("shrpref.isExist(TW_ACCTOKEN_SECRET)->","false");
				 return false;
			}
		}else{
		     Log.e("shrpref.isExist(TW_ACCTOKEN)->","false");
			 // Tiene que estar si o si los dos. 
			 return false;
		}	
	}
		
	/**
	 * Metodo publicoque envia un Tweet.
	 * @param tweet_text
	 * @return
	 */
	public boolean enviar_Tweet(String tweet_text) {
		Log.e("Tweet send_tweet", "started");
			
		// Cargamos keys del shared preferences .
		String accessToken = shared_preference.Get_stringfrom_shprf(TW_ACCTOKEN);
		String accessTokenSecret = shared_preference.Get_stringfrom_shprf(TW_ACCTOKEN_SECRET);
			
		Log.e("accessToken= "+accessToken,"accessTokenSecret "+accessTokenSecret);
			
		// Validamos clave cargadas del shared preferences.
		if ((accessToken != null) && (accessTokenSecret != null)) {
			/* Luego creamos el objeto configuracion de la lib twitter4j.
			 * Pasamos como parametros las claves consumer key and consumer Secret 
			 * y los accessToken y accessTokensecret  para la autenticacion OAuth
			 */
			Configuration conf = new ConfigurationBuilder()
				.setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
				.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
				// Datos obtenidos del shared pref
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret).build();
				
	        // Usamos lo seteado anteriormente para obtener una instancia para autenticacion OAuth.
			// Creamos objeto para acceder a twitter.
			Twitter t = new TwitterFactory(conf).getInstance();

			try{
				// Actualizamos estado, envamos el twwet.
				t.updateStatus(tweet_text);

			}catch(TwitterException e){
			     // Error
				 e.printStackTrace();
				 Log.e("Error Tweet NO Enviado!!", "FAIL");
				 return false;
			}
			Log.e("Tweet Enviado!!", "ok");
			return true;
		}else{
		     Log.e("Error Tweet NO Enviado!!", "FAIL");
			 return false;
		}
	}
}