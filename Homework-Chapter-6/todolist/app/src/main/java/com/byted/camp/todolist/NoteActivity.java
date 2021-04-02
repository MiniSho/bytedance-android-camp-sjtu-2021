package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract.TodoNote;
import com.byted.camp.todolist.db.TodoDbHelper;


public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";
    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    private AppCompatRadioButton lowRadio;

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        // setTitle(R.string.take_a_note);
        final long id = getIntent().getLongExtra("id",-1);
        Log.d(TAG, "id: "+ id);
        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        lowRadio = findViewById(R.id.btn_low);
        lowRadio.setChecked(true);

        addBtn = findViewById(R.id.btn_add);

        if(id == -1){
            // save
            setTitle(R.string.take_a_note);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence content = editText.getText();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(NoteActivity.this,
                                "No content to add", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean succeed = saveNote2Database(content.toString().trim(),
                            getSelectedPriority());
                    if (succeed) {
                        Toast.makeText(NoteActivity.this,
                                "Note added", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                    } else {
                        Toast.makeText(NoteActivity.this,
                                "Error", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }else{
            // edit
            setTitle("Edit");
            String content = getIntent().getStringExtra("content");
            int priority = getIntent().getIntExtra("priority",0);

            editText.setText(content);
            radioGroup.check(getButtonPriority(priority));

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence content = editText.getText();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(NoteActivity.this,
                                "No content to edit", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean succeed = editNote2Database(id, content.toString().trim(),
                            getSelectedPriority());
                    if (succeed) {
                        Toast.makeText(NoteActivity.this,
                                "Note edited", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                    } else {
                        Toast.makeText(NoteActivity.this,
                                "Error", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        database = null;
        dbHelper.close();
        dbHelper = null;
    }

    private boolean saveNote2Database(String content, Priority priority) {
        if (database == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT, content);
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);
        long rowId = database.insert(TodoNote.TABLE_NAME, null, values);
        return rowId != -1;
    }

    private boolean editNote2Database(long id,String content, Priority priority){
        if (database == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT, content);
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);
        String whereClause= TodoNote._ID + "=?";
        String[] whereArgs={String.valueOf(id)};
        long rowId = database.update(TodoNote.TABLE_NAME, values,whereClause,whereArgs);
        return rowId != -1;
    }

    private Priority getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Priority.High;
            case R.id.btn_medium:
                return Priority.Medium;
            default:
                return Priority.Low;
        }
    }

    private int getButtonPriority(int id){
        switch (id) {
            case 2:
                return R.id.btn_high;
            case 1 :
                return R.id.btn_medium;
            default:
                return R.id.btn_low;
        }
    }
}
