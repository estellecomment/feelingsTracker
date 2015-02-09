package com.estellecomment.feelingstracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabeledSeekBar extends LinearLayout {
  private final int minValue;
  private final int maxValue;
  private final TextView minLabel;
  private final TextView maxLabel;

  public LabeledSeekBar(Context context, AttributeSet attrs) {
    // TODO(estellecomment): do we have two nested LinearLayouts for nothing?
    super(context, attrs);
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
        R.styleable.LabeledSeekBar, 0, 0);

    String minDescription, maxDescription;
    try {
      minValue = a.getInt(R.styleable.LabeledSeekBar_minValue, 0);
      maxValue = a.getInt(R.styleable.LabeledSeekBar_maxValue, 10);
      String tryMinDescription = a.getString(R.styleable.LabeledSeekBar_minDescription);
      minDescription = tryMinDescription == null ? "" : tryMinDescription;
      String tryMaxDescription = a.getString(R.styleable.LabeledSeekBar_maxDescription);
      maxDescription = tryMaxDescription == null ? "" : tryMaxDescription;
    } finally {
      a.recycle();
    }

    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rootView = inflater.inflate(R.layout.labeled_seek_bar, this, true);

    minLabel = (TextView) rootView.findViewById(R.id.min_label);
    minLabel.setText(minDescription);

    maxLabel = (TextView) rootView.findViewById(R.id.max_label);
    maxLabel.setText(maxDescription);

    // TODO(estellecomment): current value for seekbar should be above it, otherwise you hide it
    // with your finger.
  }

  public int getMinValue() {
    return minValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public String getMinDescription() {
    return minLabel.getText().toString();
  }

  public void setMinDescription(String description) {
    minLabel.setText(description);
  }

  public String getMaxDescription() {
    return maxLabel.getText().toString();
  }

  public void setMaxDescription(String description) {
    maxLabel.setText(description);
  }

}
