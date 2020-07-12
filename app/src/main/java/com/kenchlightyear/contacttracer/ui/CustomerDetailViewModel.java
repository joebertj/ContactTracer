package com.kenchlightyear.contacttracer.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerDetailViewModel extends ViewModel {
        private MutableLiveData<String> mText;

        public CustomerDetailViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is dashboard fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }
}
