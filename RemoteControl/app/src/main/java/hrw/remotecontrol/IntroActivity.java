package hrw.remotecontrol;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import hrw.remotecontrol.fragments.IntroFragment;

public class IntroActivity extends AppCompatActivity implements hrw.remotecontrol.fragments.DialogFragment.EditListener, GestureDetector.OnGestureListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FragmentTransaction transaction;
    Fragment fragment;
    hrw.remotecontrol.fragments.DialogFragment dialogFragment;
    ProgressDialog progressDialog;
    Socket socket;
    Toolbar toolbar;
    FrameLayout frameLayout;
    static String IP;
    GestureDetector gestureDetector;
    ObjectOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBar));
        }
        gestureDetector = new GestureDetector(IntroActivity.this, this);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        frameLayout.setClickable(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.intro_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        transaction = getFragmentManager().beginTransaction();
    }

    @Override
    public void onEditFinished(String edit) {
        IP = edit;
        progressDialog = ProgressDialog.show(IntroActivity.this, "Connecting", "Plz wait......", false);
        new Thread(socketConnect).start();
    }

    private void onConnected() {
//        fragment = new IntroFragment();
//        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    Runnable socketConnect = new Runnable() {
        @Override
        public void run() {
            socket = new Socket();
            InetSocketAddress isa = new InetSocketAddress(IP, Utilities.SERVER_PORT);
            try {
                socket.connect(isa);
                progressDialog.dismiss();
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setTitle("Mouse Control");
                        onConnected();
                        frameLayout.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                boolean result = gestureDetector.onTouchEvent(motionEvent);
                                return result;
                            }
                        });
                    }
                });
            } catch (IOException e) {
                Log.w("IOException", e.toString());
                progressDialog.cancel();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
        try {
            outputStream.writeObject(new int[]{(int) (distanceX + 0.5),
                    (int) (distanceY + 0.5)});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.w("onScroll", e.toString());
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            outputStream.writeObject(new int[]{Utilities.END});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (IP != null && !IP.equals("")) {
            progressDialog = ProgressDialog.show(IntroActivity.this, "Connecting", "Plz wait......", false);
            new Thread(socketConnect).start();
        } else {
            dialogFragment = hrw.remotecontrol.fragments.DialogFragment.newInstance();
            dialogFragment.show(getFragmentManager(), "");
        }
    }
}
