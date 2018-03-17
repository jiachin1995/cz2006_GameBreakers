package com.example.gamebreakers.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamebreakers.R;
import com.example.gamebreakers.entities.DatabaseHelper;

import java.util.Arrays;
import java.util.List;

import static com.example.gamebreakers.login.Activity_Main.STALL_NAME;
import static com.example.gamebreakers.login.Activity_Main.USER_NAME;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Fragment_User_BrowseFood extends Fragment {

    // TODO: Customize parameter argument names
    private static final java.lang.String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    DatabaseHelper myDb;
    List<java.lang.String> food_List;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Fragment_User_BrowseFood() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //get database
        myDb= new DatabaseHelper(getContext());
        //get selected stall
        Activity_User act = (Activity_User) getActivity();
        String selectedStall= act.stallName;
        //get list of food
        java.lang.String[] foodArray = myDb.getStallMenu(selectedStall);
        food_List = Arrays.asList(foodArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_food, container, false);

        //check selected values. If have, update textview
        Activity_User act = (Activity_User) getActivity();
        String stallname= act.stallName;
        String food=act.food;

        if (stallname!=null) {
            TextView selectedStall = view.findViewById(R.id.selected_stall);
            selectedStall.setText("Stall: "+stallname);
        }
        if (food!=null) {
            TextView selectedStall = view.findViewById(R.id.selected_food);
            selectedStall.setText("Food: "+food);
        }

        View list = view.findViewById(R.id.item_list);
        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            RecyclerView recyclerView = (RecyclerView) list;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new Adapter_FoodList(food_List, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }
}
