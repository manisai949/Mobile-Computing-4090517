package com.manisai.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.manisai.myapplication.TaskContract.TaskEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskDbHelper dbHelper;
    private ListView taskListView;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDbHelper(this);
        taskListView = findViewById(R.id.task_list_view);
        addTaskButton = findViewById(R.id.button_add_task);

        // Set an onClickListener for the "Add Task" button
        addTaskButton.setOnClickListener(v -> addTask());

        // Load tasks from the database
        loadTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Use if-else instead of switch for menu item selection
        if (item.getItemId() == R.id.action_add_task) {
            addTask();  // Add task when menu item is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TaskEntry.TABLE_NAME,
                null,  // Select all columns
                null,  // No where clause
                null,  // No where arguments
                null,  // No group by
                null,  // No having
                null   // No order by
        );

        ArrayList<String> taskNames = new ArrayList<>();

        while (cursor.moveToNext()) {
            String taskName = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_TASK_NAME));
            taskNames.add(taskName);
        }
        cursor.close();

        // Set up the adapter to display the task names in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskNames);
        taskListView.setAdapter(adapter);
    }

    private void addTask() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create new task content values
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_TASK_NAME, "New Task");
        values.put(TaskEntry.COLUMN_NAME_TASK_DETAILS, "Task details");
        values.put(TaskEntry.COLUMN_NAME_PRIORITY, "High");

        // Insert the new task into the database
        long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            loadTasks();  // Reload the tasks after adding a new one
        } else {
            Toast.makeText(this, "Error adding task", Toast.LENGTH_SHORT).show();
        }
    }
}
