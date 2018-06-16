package jmhh.reproductormelodiame;



//import jmhh.reproductormelodiame.R;
import jmhh.reproductormelodiame.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//Clase que carga el webView de twitter
public class TwitterWebActivity extends Activity{
	// Objetos.
	private Intent intent;
	private ConstantesConfiguracion constantes;
	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_webview);
		
		// Recibe el Intent de viene del MainActivity.
		intent = getIntent();
		
		constantes = new ConstantesConfiguracion();
		String url = (String) intent.getExtras().get("URL");
		
		// Hace referencia al xml.
		webView = (WebView) findViewById(R.id.webView);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.contains(constantes.TWITTER_CALLBACK)) {
					Uri uri = Uri.parse(url);
					String oauthVerifier = uri.getQueryParameter("oauth_verifier");
					intent.putExtra("oauth_verifier", oauthVerifier);
					setResult(RESULT_OK, intent);
					finish();
					return true;
				}
				return false;
			}
		});
		//Carga la url para meter los datos de twitter
		webView.loadUrl(url);
	}
}