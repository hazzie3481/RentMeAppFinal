package com.example.hireme;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hireme.SQL.GetAllUsers;
import com.example.hireme.helper.RecyclerViewAdapter;
import com.example.hireme.model.UserListData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final AppCompatActivity activity = UserManagementActivity.this;
    private GetAllUsers getAllUsers = new GetAllUsers();
    FloatingActionButton floatingActionButton;
    Button btnAdd;
    EditText etEmail;
    EditText etDisplayName;
    ArrayList<UserListData> movieListData = new ArrayList<UserListData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // sharedPref = getSharedPreferences("SharePref",0);
        setContentView(R.layout.activity_user_management);


        movieListData.addAll(getAllUsers.GetListOfUsers());


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        try {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog(view.getContext(), "add User");
                }
            });
        }
        catch (Exception ex)
        {
            openDialog(null,"Exceptoion");
        }
        //btnSignOut = (Button) findViewById(R.id.sign_out_button);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    //    Intent intent = getIntent();
  //      Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<UserListData> listOfUsers = (ArrayList<UserListData>) args.getSerializable("ARRAYLIST");
        // specify an adapter
        mAdapter = new RecyclerViewAdapter(movieListData);
        recyclerView.setAdapter(mAdapter);
//        userlistObj = new GetAllUsers(recyclerView,mAdapter,activity);
//        movielistObj.execute((Void) null);
    }

    public void openDialog(Context context, String title) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.setContentView(R.layout.dialog_layout);
        alertDialog.setTitle("Action");
        btnAdd = (Button)alertDialog.findViewById(R.id.btnAdd);
        etEmail = (EditText)alertDialog.findViewById(R.id.etEmail);
        etDisplayName = (EditText)alertDialog.findViewById(R.id.etName);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UserListData user =  new UserListData();
                user.setEmail(etEmail.getText().toString());
                user.setTitle(etDisplayName.getText().toString());
                movieListData.add(user);
                mAdapter.notifyItemInserted(movieListData.size()-1);
                mAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
