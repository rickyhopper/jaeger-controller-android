package com.rickyhopper.trirhomjaegerparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rickyhopper.trirhomjaegerparty.db.LocalJaegerHelper;
import com.rickyhopper.trirhomjaegerparty.model.Jaeger;


public class MainActivity extends ActionBarActivity {

    private LocalJaegerHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.add_jaeger_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.add_jaeger_button) {
                    addJaeger();
                }
            }
        });
        Button k = (Button) findViewById(R.id.kaiju_button);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.kaiju_button) {
                    fireKaiju();
                }
            }
        });

        dbHelper = new LocalJaegerHelper(this);

        refreshList();
    }

    public void deleteJaeger(String name) {
        dbHelper.deleteListItem(name);
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshList() {
        Cursor cursor = dbHelper.getAllItemsAsCursor();

        // THE DESIRED COLUMNS TO BE BOUND
        String[] columns = new String[] { LocalJaegerHelper.KEY_NAME };
        // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
        int[] to = new int[] { R.id.jaeger_name };

        // CREATE THE ADAPTER USING THE CURSOR POINTING TO THE DESIRED DATA AS WELL AS THE LAYOUT INFORMATION
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_item_layout, cursor, columns, to);

        ListView list = (ListView) findViewById(R.id.jaeger_list);
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                //get cursor, positioned to row in result set
                Log.i("list", "Listener activated.");
                Cursor itemCursor = (Cursor) listView.getItemAtPosition(position);
                try {
                    final String name = itemCursor.getString(itemCursor.getColumnIndexOrThrow(LocalJaegerHelper.KEY_NAME));

                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete Jaeger?")
                            .setMessage("Remove " + name + "?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteJaeger(name);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } catch (Exception e) {
                    Log.e("list", e.toString());
                    return;
                }
            }
        });
    }

    public void addJaeger() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Inebrial Handshake");
        alert.setMessage("Enter Jaeger Name: ");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                dbHelper.addListItem(new Jaeger(value));
                refreshList();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void fireKaiju() {
        String kaiju = KaijuGenerator.getRandomKaiju();
        ListView list = (ListView) findViewById(R.id.jaeger_list);
        Cursor itemCursor = (Cursor) list.getItemAtPosition((int) (Math.random()*(list.getCount())));
        String name = itemCursor.getString(itemCursor.getColumnIndexOrThrow(LocalJaegerHelper.KEY_NAME));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Kaiju Appears for " + name + "!")
                .setMessage(kaiju)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
