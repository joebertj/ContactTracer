package com.kenchlightyear.contacttracer.ui.establishment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EstablishmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EstablishmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}