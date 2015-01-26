package com.estellecomment.feelingstracker;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FormFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";

  /**
   * Displayed bounds on the seekBar. Internal values will be different, since SeekBar's min is
   * fixed at 0.
   */
  private static final int seekbarMinDisplayValue = 3;
  private static final int seekbarMaxDisplayValue = 13;

  private SeekBar seekBar1;
  private TextView seekBarValue1;

  private Button submitButton;
  private TextView submitSummary;

  /**
   * Returns a new instance of this fragment for the given section number.
   */
  public static FormFragment newInstance(int position) {
    FormFragment fragment = new FormFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, position);
    fragment.setArguments(args);
    return fragment;
  }

  public FormFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_form, container, false);

    initializeVariables(rootView);
    initializeSeekbar(seekBar1, seekBarValue1);
    initializeSubmitButton(submitButton);
    return rootView;
  }

  // A private method to help us initialize our variables.
  private void initializeVariables(View rootView) {
    seekBar1 = (SeekBar) rootView.findViewById(R.id.question_1_seekbar);
    seekBarValue1 = (TextView) rootView.findViewById(R.id.question_1_seekbar_value);
    submitButton = (Button) rootView.findViewById(R.id.submit_button);
    submitSummary = (TextView) rootView.findViewById(R.id.submit_summary);
  }

  private void initializeSeekbar(final SeekBar seekBar, final TextView seekBarValue) {
    // SeekBar has a fixed min of 0. We offset the max to get the range we want.
    seekBar.setMax(getInternalValue(seekbarMaxDisplayValue));

    // Initialize at middle of range.
    seekBar.setProgress(seekBar.getMax() / 2);
    displaySeekBarValue(seekBarValue, seekBar.getProgress());

    seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      int progress = seekBar.getProgress();

      @Override
      public void onProgressChanged(SeekBar seekBar, int progressValue,
          boolean fromUser) {
        progress = progressValue;
        displaySeekBarValue(seekBarValue, progress);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  private int getDisplayValue(int internalValue) {
    return internalValue + seekbarMinDisplayValue;
  }

  private int getInternalValue(int displayValue) {
    return displayValue - seekbarMinDisplayValue;
  }

  // Display value might be offset from internal value, since seekbar is fixed 0-MAX.
  private void displaySeekBarValue(TextView seekBarValue, int internalValue) {
    seekBarValue.setText(getDisplayValue(internalValue) + "/" + seekbarMaxDisplayValue);
  }

  private void initializeSubmitButton(Button submitButton) {
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        long timeStampMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss, EEE dd MMM yyyy");
        Date date = new Date(timeStampMillis);
        String dateTimeString = dateFormat.format(date);

        submitSummary.setText(String.format(
            getActivity().getString(R.string.feelings_recorded), dateTimeString));
        recordFeelings(timeStampMillis);
        resetForm();
      }
    });
  }

  private void recordFeelings(long timeStampMillis) {
    Log.d("AAAA", "Recording feelings at " + timeStampMillis);
    Log.d("AAAA", "Question 1 : " + getDisplayValue(seekBar1.getProgress()));
  }

  private void resetForm() {
    seekBar1.setProgress(5);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((MainActivity) activity).onSectionAttached(getArguments().getInt(
        ARG_SECTION_NUMBER));
  }
}
