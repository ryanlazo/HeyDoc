package edu.cnm.deepdive.heydoc;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static UniDatabase database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    HomeFragment homeFragment = new HomeFragment();
    transaction.replace(R.id.container1, homeFragment).addToBackStack("home").commit();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    switch (item.getItemId()) {
      case R.id.nav_home:
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.container1, homeFragment).addToBackStack("home").commit();
        break;
      case R.id.nav_specialty:
        SpecialtyFragment specialtyFragment = new SpecialtyFragment();
        transaction.replace(R.id.container1, specialtyFragment).addToBackStack("home").commit();
        break;
      case R.id.nav_favorites:
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        transaction.replace(R.id.container1, favoritesFragment).addToBackStack("home").commit();
        break;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);

    return true;
  }

  public UniDatabase getDatabase(Context context) {
    if (database == null) {
      database = UniDatabase.getInstance(context);
    }
    return database;

  }

  public void loadFragment(Fragment fragment, Bundle bundle) {
    if (bundle != null) {
      fragment.setArguments(bundle);
    }
    getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment)
        .addToBackStack("home").commit();
  }
}




