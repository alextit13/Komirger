package com.mukmenev.android.findjob.social_networks.buy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.WriteReadText;
import com.cloudipsp.android.Card;
import com.cloudipsp.android.CardInputLayout;
import com.cloudipsp.android.Cloudipsp;
import com.cloudipsp.android.CloudipspWebView;
import com.cloudipsp.android.Currency;
import com.cloudipsp.android.Order;
import com.cloudipsp.android.Receipt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MarkerAd extends Activity implements View.OnClickListener {
    private static final int MERCHANT_ID = 1396424;

    //private EditText editAmount;
    //private Spinner spinnerCcy;
    private TextView offer;
    private EditText editEmail;
    private EditText editDescription;
    private CardInputLayout cardLayout;
    private CloudipspWebView webView;
    private Cloudipsp cloudipsp;
    private Ad ad;
    private int MmMamount = 1000; // 30 рублей

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_example);
        offer = (TextView) findViewById(R.id.offer);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editDescription = (EditText) findViewById(R.id.edit_description);
        cardLayout = (CardInputLayout) findViewById(R.id.card_layout);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        webView = (CloudipspWebView) findViewById(R.id.web_view);
        cloudipsp = new Cloudipsp(MERCHANT_ID, webView);
        ad = (Ad) getIntent().getSerializableExtra("ad");
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MarkerAd.this);
                builder.setTitle("ОФЕРТА");
                builder.setMessage("Тут текст оферты");
                builder.setPositiveButton("ПРИНЯТЬ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_amount:
                fillTest();
                break;*/
            case R.id.btn_pay:
                offerCheck();
                break;
        }
    }

    private void offerCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MarkerAd.this);
        builder.setTitle("Пользовательское соглашение");
        builder.setMessage(WriteReadText.readFromFile(this));
        builder.setPositiveButton("ПРИНЯТЬ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                processPay();
            }
        });
        builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    private void processPay() {
        //editAmount.setError(null);
        editEmail.setError(null);
        editDescription.setError(null);

        final int amount;
        try {
            amount = MmMamount;
            //amount = Integer.valueOf(editAmount.getText().toString());

        } catch (Exception e) {
            //editAmount.setError(getString(R.string.e_invalid_amount));
            return;
        }

        final String email = editEmail.getText().toString();
        final String description = editDescription.getText().toString();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.e_invalid_email));
        } else if (TextUtils.isEmpty(description)) {
            editDescription.setError(getString(R.string.e_invalid_description));
        } else {
            final Card card = cardLayout.confirm(new CardInputLayout.ConfirmationErrorHandler() {
                @Override
                public void onCardInputErrorClear(CardInputLayout view, EditText editText) {
                }

                @Override
                public void onCardInputErrorCatched(CardInputLayout view, EditText editText, String error) {
                    editText.setError(error);
                }
            });

            if (card != null) {
                //final Currency currency = (Currency) spinnerCcy.getSelectedItem();
                final Currency currency = Currency.RUB;
                final Order order = new Order(amount, currency, "vb_" + System.currentTimeMillis(), description, email);
                order.setLang(Order.Lang.ru);

                cloudipsp.pay(card, order, new Cloudipsp.PayCallback() {
                    @Override
                    public void onPaidProcessed(Receipt receipt) {
                        Toast.makeText(MarkerAd.this, "Paid " + receipt.status.name() + "\nPaymentId:" + receipt.paymentId, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPaidFailure(Cloudipsp.Exception e) {
                        if (e instanceof Cloudipsp.Exception.Failure) {
                            Cloudipsp.Exception.Failure f = (Cloudipsp.Exception.Failure) e;

                            Toast.makeText(MarkerAd.this, "Failure\nErrorCode: " +
                                    f.errorCode + "\nMessage: " + f.getMessage() + "\nRequestId: " + f.requestId, Toast.LENGTH_LONG).show();
                        } else if (e instanceof Cloudipsp.Exception.NetworkSecurity) {
                            Toast.makeText(MarkerAd.this, "Network security error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        } else if (e instanceof Cloudipsp.Exception.ServerInternalError) {
                            Toast.makeText(MarkerAd.this, "Internal server error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        } else if (e instanceof Cloudipsp.Exception.NetworkAccess) {
                            Toast.makeText(MarkerAd.this, "Network error", Toast.LENGTH_LONG).show();
                        } else {
                            refreshUser();
                            Toast.makeText(MarkerAd.this, "Платеж успешно произведен", Toast.LENGTH_LONG).show();
                            //Toast.makeText(FlexibleExampleActivity.this, "Payment Failed", Toast.LENGTH_LONG).show();

                        }
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private void refreshUser() {
        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").removeValue().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ad.setPremium(true);
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").setValue(ad);
                        Toast.makeText(MarkerAd.this, "Объявление успешно выделено!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        if (webView.waitingForConfirm()) {
            webView.skipConfirm();
        } else {
            super.onBackPressed();
        }
    }
}
