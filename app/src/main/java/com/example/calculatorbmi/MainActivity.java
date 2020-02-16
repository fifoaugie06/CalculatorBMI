package com.example.calculatorbmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnMale, btnFemale, btnCalculate;
    EditText edtAge, edtHeight, edtWeight;
    TextView tvResult, tvBMIClassification;
    private static final String STATE_RESULT = "state_result";
    private static final String STATE_BMI = "state_bmi";
    private static final String STATE_COLOR = "state_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        tvBMIClassification = findViewById(R.id.tv_BMIClassification);
        edtAge = findViewById(R.id.edt_age);
        edtHeight = findViewById(R.id.edt_height);
        edtWeight = findViewById(R.id.edt_weight);
        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);
        btnCalculate = findViewById(R.id.btn_calculate);

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource(R.drawable.ic_button_selected);
                btnFemale.setBackgroundResource(R.drawable.ic_button_notselected);

                btnCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (Integer.valueOf(edtAge.getText().toString()) <= 10) {
                                tvResult.setText(BBIAnak(Integer.valueOf(edtAge.getText().toString())) + " Kg");
                                tvBMIClassification.setText("");
                            } else {
                                if (edtHeight.getText().toString().length() == 0) {
                                    edtHeight.setError("Not Null");
                                } else if (edtWeight.getText().toString().length() == 0) {
                                    edtWeight.setError("Not Null");
                                } else {
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    float result = (BMIDewasa(Float.valueOf(edtHeight.getText().toString()),
                                            Float.valueOf(edtWeight.getText().toString())));
                                    tvResult.setText(df.format(result));
                                    tvBMIClassification.setText(BMIClassification(result));
                                }
                            }
                        } catch (NumberFormatException e) {
                            Log.i("Cendol", String.valueOf(e));
                        }
                    }
                });
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFemale.setBackgroundResource(R.drawable.ic_button_selected);
                btnMale.setBackgroundResource(R.drawable.ic_button_notselected);

                btnCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (Integer.valueOf(edtAge.getText().toString()) <= 10) {
                                tvResult.setText(BBIAnak(Integer.valueOf(edtAge.getText().toString())) + " Kg");
                                tvBMIClassification.setText("");
                            } else {
                                if (edtHeight.getText().toString().length() == 0) {
                                    edtHeight.setError("Not Null");
                                } else if (edtWeight.getText().toString().length() == 0) {
                                    edtWeight.setError("Not Null");
                                } else {
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    float result = (BMIDewasa(Float.valueOf(edtHeight.getText().toString()),
                                            Float.valueOf(edtWeight.getText().toString())));
                                    tvResult.setText(df.format(result));
                                    tvBMIClassification.setText(BMIClassification(result));
                                }
                            }
                        } catch (NumberFormatException e) {
                            Log.i("Cendol", String.valueOf(e));
                        }
                    }
                });
            }
        });

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(STATE_RESULT);
            tvResult.setText(result);

            String classification = savedInstanceState.getString(STATE_BMI);
            tvBMIClassification.setText(classification);

            int classificationColor = savedInstanceState.getInt(STATE_COLOR);
            String currentColor = String.format("#%06X", (0xFFFFFF & classificationColor));
            tvBMIClassification.setTextColor(Color.parseColor(currentColor));
        }
    }

    private String BMIClassification(float result) {
        if (result < 18.5) {
            tvBMIClassification.setTextColor(Color.parseColor("#c0392b"));
            return "Underweight";
        } else if (result >= 18.5 && result < 25) {
            tvBMIClassification.setTextColor(Color.parseColor("#2ecc71"));
            return "Normal Weight";
        } else if (result >= 25 && result < 30) {
            tvBMIClassification.setTextColor(Color.parseColor("#f1c40f"));
            return "Over Weight";
        } else if (result >= 30 && result < 35) {
            tvBMIClassification.setTextColor(Color.parseColor("#f39c12"));
            return "Obesity 1";
        } else if (result >= 35 && result < 40) {
            tvBMIClassification.setTextColor(Color.parseColor("#d35400"));
            return "Obesity 2";
        } else {
            tvBMIClassification.setTextColor(Color.parseColor("#c0392b"));
            return "Obesity 3";
        }
    }

    private float BMIDewasa(float tinggi, float bobot) {
        return (bobot) / ((tinggi / 100) * (tinggi / 100));
    }

    private int BBIAnak(int umur) {
        return ((umur * 2) + 8);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_RESULT, tvResult.getText().toString());
        outState.putString(STATE_BMI, tvBMIClassification.getText().toString());
        outState.putInt(STATE_COLOR, tvBMIClassification.getCurrentTextColor());
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnMale.performClick();
    }
}
