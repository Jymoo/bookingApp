package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FQA extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;



    private View parent_view;
    private NestedScrollView nested_scroll_view;
    private ImageButton bt_toggle_text, bt_toggle_input;
    private Button bt_hide_text, bt_save_input, bt_hide_input;
    private View lyt_expand_text, lyt_expand_input;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fqa);

//
//        listView = (ExpandableListView) findViewById(R.id.question0);
//        initData();
//        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
//        listView.setAdapter(listAdapter);
//    }
//
//
//
//    private void initData() {
//        listDataHeader = new ArrayList<>();
//        listHash = new HashMap<>();
//
//        listDataHeader.add("What is this app for?");
//        listDataHeader.add("How do I use this app?");
//        listDataHeader.add("How do I contact support?");
//
//        List<String> question1 = new ArrayList<>();
//        question1.add("This app is for managing your personal tasks and to-do lists.");
//
//        List<String> question2 = new ArrayList<>();
//        question2.add("To use this app, you can add tasks by clicking the '+' button in the bottom right corner of the screen. You can also mark tasks as complete by clicking on them. To delete a task, swipe it to the right or left. You can also create and manage different task lists by clicking on the menu icon in the top left corner of the screen.");
//
//        List<String> question3 = new ArrayList<>();
//        question3.add("To contact support, click on the menu icon in the top left corner of the screen and select the 'Contact Support' option.");
//
//        listHash.put(listDataHeader.get(0), question1);
//        listHash.put(listDataHeader.get(1), question2);
//        listHash.put(listDataHeader.get(2), question3);
//   }
//}






//        parent_view = findViewById(android.R.id.content);

//     //   initToolbar();
//
//        initComponent();
//    }

//    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Basic");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Tools.setSystemBarColor(this);
//    }
//
//    private void initComponent() {
//
//        // text section
//        bt_toggle_text = (ImageButton) findViewById(R.id.bt_toggle_text);
//        bt_hide_text = (Button) findViewById(R.id.bt_hide_text);
//        lyt_expand_text = (View) findViewById(R.id.lyt_expand_text);
//        lyt_expand_text.setVisibility(View.GONE);
//
//        bt_toggle_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSectionText(bt_toggle_text);
//            }
//        });
//
//        bt_hide_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSectionText(bt_toggle_text);
//            }
//        });

        // input section
//        bt_toggle_input = (ImageButton) findViewById(R.id.bt_toggle_input);
//        bt_hide_input = (Button) findViewById(R.id.bt_hide_input);
//        bt_save_input = (Button) findViewById(R.id.bt_save_input);
//        lyt_expand_input = (View) findViewById(R.id.lyt_expand_input);
//        lyt_expand_input.setVisibility(View.GONE);

//        bt_toggle_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSectionInput(bt_toggle_input);
//            }
//        });
//
//        bt_hide_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSectionInput(bt_toggle_input);
//            }
//        });
//
//        bt_save_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(parent_view, "Data saved", Snackbar.LENGTH_SHORT).show();
//                toggleSectionInput(bt_toggle_input);
//            }
//        });

        // nested scrollview
   //     nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
   // }
//
//    private void toggleSectionText(View view) {
//        boolean show = toggleArrow(view);
//        if (show) {
//            ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
//                @Override
//                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
//                }
//            });
//        } else {
//            ViewAnimation.collapse(lyt_expand_text);
//        }
//    }
//
//    private void toggleSectionInput(View view) {
//        boolean show = toggleArrow(view);
//        if (show) {
//            ViewAnimation.expand(lyt_expand_input, new ViewAnimation.AnimListener() {
//                @Override
//                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt_expand_input);
//                }
//            });
//        } else {
//            ViewAnimation.collapse(lyt_expand_input);
//        }
//    }
//
//    public boolean toggleArrow(View view) {
//        if (view.getRotation() == 0) {
//            view.animate().setDuration(200).rotation(180);
//            return true;
//        } else {
//            view.animate().setDuration(200).rotation(0);
//            return false;
//        }
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.menu_setting, menu);
////        return true;
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
    }
}