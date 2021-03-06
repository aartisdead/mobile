package com.example.myphone;


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


        //filling company phone types
        String[] applePhones = new String[]{"IPhone5", "IPhone6", "IPhone7", "IPhone8"};

        String[] androidPhone = {"Android9", "Android10"};

        String[] xiaomiPhone = {"Xiaomi Redmi"};

        String[] samsungButtonPhone = {"Samsung GT-793", "Samsung ST-745"};
        String[] samsungSmartPhone = {"Samsung S9", "Samsung S10"};

        String[] nokiaPhone = {"Nokia 700", "Nokia 583", "Nokia 759", "Nokia 623"};

        applePhoneTypes.put("Смарфон", applePhones);

        androidPhoneTypes.put("Смартфон", androidPhone);

        xiaomiPhoneTypes.put("Смартфон", xiaomiPhone);

        samsungPhoneTypes.put("Кнопочный", samsungButtonPhone);
        samsungPhoneTypes.put("Смартфон", samsungSmartPhone);

        nokiaPhoneTypes.put("Кнопочный", nokiaPhone);


        //filling companies
        companyMap.put("Apple", applePhoneTypes);
        companyMap.put("Android", androidPhoneTypes);
        companyMap.put("Xiaomi", xiaomiPhoneTypes);
        companyMap.put("Samsung", samsungPhoneTypes);
        companyMap.put("Nokia", nokiaPhoneTypes);

        Spinner spinner = view.findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, phones);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);


        Button button = (Button) view.findViewById(R.id.okButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAndSaveDetail();
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

    public void updateAndSaveDetail() {
        Spinner spinner = getView().findViewById(R.id.spinner);
        RadioGroup rg = getView().findViewById(R.id.rg);
        RadioButton selectedButton = getView().findViewById(rg.getCheckedRadioButtonId());

        if(selectedButton != null){
            //getting selected values
            String selectedPhoneType = spinner.getSelectedItem().toString();
            String selectedCompany = "";
            selectedCompany = selectedButton.getText().toString();
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
                    buffer = "No phones found\n";
                }
            } else {
                buffer = "No company found\n";
            }

            // Посылаем данные Activity
            mListener.onFragmentInteraction(buffer);
        }
        else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Select company", 300);
            toast.show();
        }
    }
}

