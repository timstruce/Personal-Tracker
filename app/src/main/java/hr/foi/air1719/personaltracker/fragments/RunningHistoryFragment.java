package hr.foi.air1719.personaltracker.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import hr.foi.air1719.core.facade.DatabaseFacade;
import hr.foi.air1719.database.entities.Activity;
import hr.foi.air1719.database.entities.ActivityMode;
import hr.foi.air1719.personaltracker.R;


/**
 * Created by Nikolina on 14.01.2018..
 */

public class RunningHistoryFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TableLayout tableRunningHistory = null;

    private OnFragmentInteractionListener mListener;

    public RunningHistoryFragment() {
        // Required empty public constructor
    }


    public static RunningHistoryFragment newInstance(String param1, String param2) {
        RunningHistoryFragment fragment = new RunningHistoryFragment();
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

    static RunningHistoryFragment myFrame;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myFrame = this;
        tableRunningHistory = (TableLayout) getView().findViewById(R.id.tableRunningHistory);
        fillTable();

    }

    private void fillTable() {

        new Thread(new Runnable() {
            public void run() {
                DatabaseFacade dbfacade = new DatabaseFacade(getView().getContext());
                Message message = mHandler.obtainMessage(1, dbfacade.getActivityByModeOrderByStartDESC(ActivityMode.RUNNING));
                message.sendToTarget();
            }
        }).start();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_running_history, container, false);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            System.out.println(message.toString());
            int i = 0;


            List<Activity> ac = (List<Activity>) message.obj;

            TableRow tbrow0 = new TableRow(getActivity());

            TextView tv1 = new TextView(getActivity());
            tv1.setText(" Start ");
            tv1.setBackgroundColor(Color.LTGRAY);
            tv1.setHeight(100);
            tv1.setTypeface(null, Typeface.BOLD);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(Color.BLACK);
            tbrow0.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setText(" Distance");
            tv2.setTextColor(Color.BLACK);
            tv2.setBackgroundColor(Color.LTGRAY);
            tv2.setHeight(100);
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setGravity(Gravity.CENTER);
            tbrow0.addView(tv2);

            TextView tv3 = new TextView(getActivity());
            tv3.setText(" Show activity ");
            tv3.setTextColor(Color.BLACK);
            tv3.setBackgroundColor(Color.LTGRAY);
            tv3.setHeight(100);
            tv3.setTypeface(null, Typeface.BOLD);
            tv3.setGravity(Gravity.CENTER);
            tbrow0.addView(tv3);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick_ShowActivity(v, new Activity(ActivityMode.RUNNING));

                }

            });


            tableRunningHistory.addView(tbrow0);


            for (Activity a : ac) {

                TableRow tbrow = new TableRow(getActivity());
                TextView t1v = new TextView(getActivity());
                t1v.setText(a.getStart().toString());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                if (i % 2 == 0) t1v.setBackgroundColor(Color.rgb(236, 236, 236));
                tbrow.addView(t1v);


                TextView t2v = new TextView(getActivity());
                t2v.setText(String.valueOf(a.getDistance()));
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                if (i % 2 == 0) t2v.setBackgroundColor(Color.rgb(236, 236, 236));
                tbrow.addView(t2v);

                TextView t3v = new TextView(getActivity());
                t3v.setText("Show activity");
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                t3v.setTextColor(Color.BLUE);
                if (i % 2 == 0) t3v.setBackgroundColor(Color.rgb(236, 236, 236));

                t3v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_ShowActivity(v, new Activity(ActivityMode.RUNNING));
                    }
                });
                tbrow.addView(t3v);

                tableRunningHistory.addView(tbrow);
                i++;

            }
        }
    };

    private void onClick_ShowActivity(View v, Activity activity) {

        //todo
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}