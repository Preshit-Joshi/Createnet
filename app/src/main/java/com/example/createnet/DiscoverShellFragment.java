package com.example.createnet;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverShellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class DiscoverShellFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mFindMapBtn;
    String mGetCategory;

    private EditText mGetRadius;




    public DiscoverShellFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverShellFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverShellFragment newInstance(String param1, String param2) {
        DiscoverShellFragment fragment = new DiscoverShellFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);










        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_discover_shell, container, false);
        mFindMapBtn=(Button)root.findViewById(R.id.find_map_btn);

        //mGetRadius=(EditText)root.findViewById(R.id.get_radius);

        final Spinner dropdown = root.findViewById(R.id.get_spinner_category);

        //create a list of items for the spinner.
        String[] items = new String[]{"ALL","Acting","Comedy","Design","Literature","Music","Painting","Photography","Other"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(DiscoverShellFragment.this);



        mFindMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(),mGetCategory,Toast.LENGTH_SHORT).show();
                Intent mapintent = new Intent(getActivity(),DiscoverMapsActivity.class);
                mapintent.putExtra("Category",mGetCategory);
                startActivity(mapintent);







            }
        });





        return root;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mGetCategory=adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}





