package com.example.akosha.sample1.animationpoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kushagarlall on 8/11/15.
 */
public class CardOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        TextView textView = (TextView) view.findViewById(R.id.fragtext1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Screenthree.ins.showfragment(1);
            }
        });
        return view;

    }
}