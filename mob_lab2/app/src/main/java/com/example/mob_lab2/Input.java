package com.example.mob_lab2;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.HashMap;

public class Input extends Fragment {
    private OnFragmentInteractionListener mListener;
    HashMap<String, HashMap<String, String[]>> companyMap = new HashMap<>();

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String buffer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_input, container, false);

        //declaring
        String[] phones = {"Кнопочный", "Смартфон"};
        String[] companies = {"Apple", "Android", "Xiaomi", "Samsung", "Nokia"};
        HashMap<String, String[]> applePhoneTypes = new HashMap<>();
        HashMap<String, String[]> androidPhoneTypes = new HashMap<>();
        HashMap<String, String[]> xiaomiPhoneTypes = new HashMap<>();
        HashMap<String, String[]> samsungPhoneTypes = new HashMap<>();
        HashMap<String, String[]> nokiaPhoneTypes = new HashMap<>();


        String[] applePhones = new String[]{"IPhone5", "IPhone6", "IPhone7", "IPhone8"};

        String[] androidPhone = {"Android9", "Android10"};

        String[] xiaomiPhone = {"Xiaomi Redmi"};

        String[] samsungButtonPhone = {"Samsung GT-983", "Samsung ST-732"};
        String[] samsungSmartPhone = {"Samsung S10", "Samsung S9"};

        String[] nokiaPhone = {"Nokia 700", "Nokia 456", "Nokia 342", "Nokia 678"};

        applePhoneTypes.put("Смартфон", applePhones);

        androidPhoneTypes.put("Смартфон", androidPhone);

        xiaomiPhoneTypes.put("Смартфон", xiaomiPhone);

        samsungPhoneTypes.put("Кнопочный", samsungButtonPhone);
        samsungPhoneTypes.put("Смартфон", samsungSmartPhone);

        nokiaPhoneTypes.put("Кнопочный", nokiaPhone);


        companyMap.put("Apple", applePhoneTypes);
        companyMap.put("Android", androidPhoneTypes);
        companyMap.put("Xiaomi", xiaomiPhoneTypes);
        companyMap.put("Samsung", samsungPhoneTypes);
        companyMap.put("Nokia", nokiaPhoneTypes);

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, phones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Button button = (Button) view.findViewById(R.id.okButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail(view);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    public void updateDetail(View view) {
        Spinner spinner = view.findViewById(R.id.spinner);
        RadioGroup rg = view.findViewById(R.id.rg);
        RadioButton selectedButton = view.findViewById(rg.getCheckedRadioButtonId());



        //getting selected values
        String selectedPhoneType = spinner.getSelectedItem().toString();
        String selectedCompany = "";

        if(selectedButton != null){
            selectedCompany = selectedButton.getText().toString();
        }
        else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Select company", 300);
            toast.show();
        }


        String buffer = "";

        HashMap<String, String[]> companyCheck = companyMap.get(selectedCompany);

        //checking for nulls
        if (companyCheck != null) {
            String[] phoneArray = companyCheck.get(selectedPhoneType);

            if (phoneArray != null) {
                for (int i = 0; i < phoneArray.length; i++) {
                    buffer += phoneArray[i] + "\n";
                }
            } else {
                buffer = "No phones found";
            }
        } else {
            buffer = "No company found";
        }


        // Посылаем данные Activity
        mListener.onFragmentInteraction(buffer);
    }
}


