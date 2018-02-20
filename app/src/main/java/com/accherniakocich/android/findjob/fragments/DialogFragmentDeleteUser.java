package com.accherniakocich.android.findjob.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.otto.Subscribe;

public class DialogFragmentDeleteUser extends DialogFragment{
    private User user;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_delete_user,container,false);
        ((Button)view.findViewById(R.id.delete_acc_button_ok))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteUserFromServer();
                            }
                        }
                );
        ((Button)view.findViewById(R.id.delete_acc_button_cancel))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // close dialog
                                
                            }
                        }
                );
        return view;
    }

    public void setUser(Context c, User U){
        user = U;
        context = c;
    }

    private void deleteUserFromServer() {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getName())
                .removeValue()
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Пользователь удален", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
    }
}
