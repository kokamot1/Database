package com.ebookfrenzy.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    public static final int DEFAULT_QUANTITY = 0;
    TextView idView;
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox =
                (EditText) findViewById(R.id.productQuantity);
    }

    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        int quantity = this.getQuantity();

        Product product =
                new Product(productBox.getText().toString(), quantity);

        dbHandler.addProduct(product);
        idView.setText("Product Added");
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Product product =
                dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    public void updateProduct(View view) {
        String productName = this.productBox.getText().toString();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Product product = dbHandler.findProduct(productName);

        if (product == null) {
            idView.setText("Unknown Product");
            return;
        }
        int quantity = this.getQuantity();

        boolean result = dbHandler.updateProduct(product.getID(), quantity);
        if (result)
            idView.setText("Record Updated");
        else
            idView.setText("Error updating");
    }

    public void removeAllProducts(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.deleteAllProducts();
        idView.setText("All Products deleted");
        productBox.setText("");
        quantityBox.setText("");
    }

    private int getQuantity() {
        int quantity = DEFAULT_QUANTITY;
        try {
            quantity = Integer.parseInt(quantityBox.getText().toString());
        } catch (NumberFormatException ex) {}
        return quantity;
    }
}
