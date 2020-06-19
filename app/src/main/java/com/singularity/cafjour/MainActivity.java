package com.singularity.cafjour;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    boolean hasWippedCream = false;
    boolean hasChocolateCream = false;


    public void increment(View view) {
        displayQuantity(++quantity);
    }

    public void decrement(View view) {
        if (quantity != 0)
            --quantity;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void submitOrder(View view) {

        EditText editText = findViewById(R.id.editText);
        String name = editText.getText().toString();

        CheckBox wippedCream = findViewById(R.id.whipped_cream);
        hasWippedCream = wippedCream.isChecked();


        CheckBox chocolateCream = findViewById(R.id.chocolate_cream);
        hasChocolateCream = chocolateCream.isChecked();


        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nAdd whipped cream? " + hasWippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolateCream;
        priceMessage += "\nTotal: $" + calculateTotalPrice();
        priceMessage += "\n" + getString(R.string.thank_you);
        //  displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "gallantdecoy@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public int calculateTotalPrice() {
        int basePrice = 5;
        if (hasWippedCream) {
            basePrice += 1;
        }

        if (hasChocolateCream) {
            basePrice += 2;
        }

        return (basePrice * quantity);
    }


//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    public void payment(View view) {
        Context context = getApplicationContext();
        int time = Toast.LENGTH_LONG;
        Toast message = Toast.makeText(context, "Are you sure?", time);
        message.show();
        message.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
