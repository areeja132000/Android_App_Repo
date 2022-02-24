package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.User;
import com.example.teambriancanweswitchourname.R;

import java.util.HashMap;
import java.util.Map;

public class ViewInactiveAccounts extends AppCompatActivity {

    Admin currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inactive_accounts);

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(ViewInactiveAccounts.this);
        Intent intent = getIntent();
        currentAdmin = (Admin) intent.getSerializableExtra("adminUser");

        int sizeOfUsers = mydb.getUserTableSize();
        int userId = 1;
        HashMap<Integer, Integer> AccountIdToUserId = new HashMap<Integer, Integer>();
        for(int i=1; i<=sizeOfUsers; i++) {
            Cursor allUserAccounts = mydb.getUserInactiveAccounts(userId);
            allUserAccounts.moveToFirst();
            while (!allUserAccounts.isAfterLast()) {
                AccountIdToUserId.put(allUserAccounts.getInt(0), allUserAccounts.getInt(1));
                allUserAccounts.moveToNext();
            }
            allUserAccounts.close();
            userId +=1;
        }

        String addOn = "Current Inactive Accounts:\n";
        for (Map.Entry<Integer, Integer> currentAccount : AccountIdToUserId.entrySet()) {
            int acctId = currentAccount.getKey();
            int currentUserId = currentAccount.getValue();

            Cursor toUserDetails = mydb.getUserDetails(currentUserId);
            toUserDetails.moveToFirst();
            User currentUser = new Customer(toUserDetails.getInt(0), toUserDetails.getString(1), toUserDetails.getInt(2), toUserDetails.getString(3));
            toUserDetails.close();

            addOn = addOn + currentUser.getName() + " has ID: " + currentUserId + " and has account with ID: " + acctId + "\n";
        }

        TextView activePrinting = (TextView) findViewById(R.id.inactiveOutput);
        activePrinting.setText(addOn);

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewInactiveAccounts.this, AdminPanel.class);
                intent.putExtra("admin", currentAdmin.getId());
                startActivity(intent);
                finish();
            }
        });
    }
}
