package com.example.fitnesshub.viewModel;

import android.service.autofill.UserData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.AuthToken;
import com.example.fitnesshub.model.UserAPIService;
import com.example.fitnesshub.model.UserCredentials;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserData> userData = new MutableLiveData<>();
    private MutableLiveData<AuthToken> token = new MutableLiveData<>();

    private UserAPIService userService = new UserAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<UserData> getUserData() {
        return userData;
    }

    public MutableLiveData<AuthToken> getToken() {
        return token;
    }

    public void tryLogin(String username, String password) {

        disposable.add(userService.login(new UserCredentials(username, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AuthToken>() {
                    @Override
                    public void onSuccess(@NonNull AuthToken authToken) {
                        token.setValue(authToken);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }
}
