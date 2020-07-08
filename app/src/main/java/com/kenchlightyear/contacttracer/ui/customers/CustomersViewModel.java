package com.kenchlightyear.contacttracer.ui.customers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}