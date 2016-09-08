package com.boulio.burnedcaloriescalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {



    DecimalFormat precision = new DecimalFormat("##.###");
    private EditText weightTextField;
    private TextView milesRanNumericValueLabel;
    private SeekBar  milesRanSeekBar;
    private TextView caloriesBurnedNumericalLabel;
    private Spinner feetSpinner;
    private Spinner inchesSpinner;
    private TextView bmiNumericalLabel;
    private EditText nameEditText;

    private double wieght;
    private int milesRanInt;
    private double milesRanDouble;
    private double caloriesBurned;

    private String formatedBmi;

    private String tempA;
    private String tempB;
    private String tempC;
    private String name;
    private double bmi;

    private int inches;
    private int feet;

    private SharedPreferences SavedValues;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeVariables();
        setArrayAdapters();
        setListeners();

    }


    private void initializeVariables(){

        weightTextField = (EditText) findViewById(R.id.wieghtTextField);
        milesRanNumericValueLabel = (TextView) findViewById(R.id.milesRanNumericValueLabel);
        milesRanSeekBar         = (SeekBar)  findViewById(R.id.milesRanSeekBar);
        caloriesBurnedNumericalLabel  = (TextView) findViewById(R.id.caloriesBurnedNumericalLabel);
        feetSpinner      = (Spinner) findViewById(R.id.feetSpinner);
        inchesSpinner    = (Spinner) findViewById(R.id.inchesSpinner);
        bmiNumericalLabel = (TextView) findViewById(R.id.bmiNumericalLabel);
        nameEditText = (EditText) findViewById(R.id.nameEditText);

    }

    private void setArrayAdapters(){
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.feetSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        feetSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(this, R.array.inchesSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        inchesSpinner.setAdapter(adapter);
    }


    private void setListeners(){

        weightTextField.setOnEditorActionListener(this);
        weightTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    calculateAndDisplay();
            }
            //not using
            @Override
            public void afterTextChanged(Editable editable){}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            //done not using
        });

        milesRanSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                milesRanInt = i;
                milesRanNumericValueLabel.setText(String.valueOf(i)+"mi");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    calculateAndDisplay();

            }
            //not using
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //done not using
        });

        inchesSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inches = i+1;
                calculateAndDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        feetSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                feet = i+1;
                calculateAndDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nameEditText.setOnEditorActionListener(this);



    }


    private void calculateAndDisplay() {


            tempA = weightTextField.getText().toString();
            tempB = ""+milesRanInt;


            wieght = Double.parseDouble(tempA);
            milesRanDouble = Double.parseDouble(tempB);

            caloriesBurned = (.75 * wieght) * milesRanDouble;

            tempC =""+caloriesBurned;
            bmi = (wieght * 703) / ((12*feet+inches)*(12*feet+inches));
            formatedBmi = precision.format(bmi);





            caloriesBurnedNumericalLabel.setText(tempC);
            bmiNumericalLabel.setText(formatedBmi);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        if(actionID == EditorInfo.IME_ACTION_DONE || actionID   == EditorInfo.IME_ACTION_UNSPECIFIED){
            name=nameEditText.getText().toString();
        }
        return false;
    }

/*was unable to get these to work, here is my best attempt at doing so hoping that even an attempt will net me some points.
    @Override
    protected void onResume() {

        wieght = Double.parseDouble(SavedValues.getString("wieght",""));
        milesRanDouble = Double.parseDouble(SavedValues.getString("milesRanDouble",""));
        inches = SavedValues.getInt("inches", Integer.parseInt(""));
        feet = SavedValues.getInt("feet", Integer.parseInt(""));
             name = SavedValues.getString("name","");
        calculateAndDisplay();

        super.onResume();
    }

    @Override
    protected void onPause(){
        //save the instance variables
        SharedPreferences.Editor editor = SavedValues.edit();
        editor.putString("wieght", String.valueOf(wieght));
        editor.putString("milesRanDouble", String.valueOf(milesRanDouble));
        editor.putInt("inches",inches);
         editor.putString("name", name);
        editor.putInt("feet",feet);
        editor.apply();

        super.onPause();
    }
*/


}
