package jmhh.reproductormelodiame;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class inicial extends ActionBarActivity  {
	//Se declaran los elementos necesarios para el viewpagerindicator

    @InjectView(R.id.activity_my_toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;
    private MyPagerAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {


	     	super.onCreate(savedInstanceState);

	     	setContentView(R.layout.tab);

         ButterKnife.inject(this);
        //Se crea la barra de titulo personalizada
         setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         adapter = new MyPagerAdapter(getSupportFragmentManager());
        //se le añaden los elementos de los tabs y sus titulos
         pager.setAdapter(adapter);
         tabs.setViewPager(pager);


	 }







    public class MyPagerAdapter extends FragmentPagerAdapter {
//se asignan los nombres de cada tab
        private final String[] TITLES = {"SD", getString(R.string.grupos), getString(R.string.albumes)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
//según en el tab que entre, abre un fragment u otro
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new pestbusca();

            }
            if (position == 1) {
                return new pestgrupoclass();

            } else {
                return new pestalbum();

            }

        }

    }
    //Al pulsar el boton de la barra de atrás se sale a la pantalla principal

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//Al pulsar el boton  de atrás se sale a la pantalla principal
	 public void onBackPressed() {
	    	finish();



		}

}
