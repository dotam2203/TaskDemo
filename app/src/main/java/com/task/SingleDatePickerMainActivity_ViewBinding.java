package com.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;

import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

public class SingleDatePickerMainActivity_ViewBinding implements Unbinder {
    private SingleDatePickerMainActivity target;
    private final View viewClick;
    @UiThread
    public SingleDatePickerMainActivity_ViewBinding(
            SingleDatePickerMainActivity target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public SingleDatePickerMainActivity_ViewBinding(
            final SingleDatePickerMainActivity target, View source) {
        this.target = target;
        View view;
        target.singleText = Utils.findRequiredViewAsType(source, R.id.singleText, "field 'singleText'", TextView.class);
        view = Utils.findRequiredView(source, R.id.singleLayout, "method 'simpleClicked'");
        viewClick = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View p0) {
                target.simpleClicked();
            }
        });
    }

    @Override
    @CallSuper
    public void unbind() {
        SingleDatePickerMainActivity target = this.target;
        if (target == null) throw new IllegalStateException("Bindings already cleared.");
        this.target = null;

        target.singleText = null;
        viewClick.setOnClickListener(null);
    }
}
