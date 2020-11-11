package com.example.fitnesshub.viewModel;

import android.service.autofill.UserData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.AuthToken;
import com.example.fitnesshub.model.UserAPIService;
import com.example.fitnesshub.model.UserCredentials;
import com.example.fitnesshub.model.UserInfo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<AuthToken> token = new MutableLiveData<>();

    private UserAPIService userService = new UserAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<UserInfo> getUserData() {
        return userInfo;
    }

    public MutableLiveData<AuthToken> getToken() {
        return token;
    }

    public void tryLogin(String username, String password) {

        UserCredentials credentials = new UserCredentials(username, password);

        disposable.add(userService.login(credentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AuthToken>() {
                    @Override
                    public void onSuccess(@NonNull AuthToken authToken) {
                        token.setValue(authToken);
                        disposable.add(userService.getCurrentUser(credentials)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                                    @Override
                                    public void onSuccess(@NonNull UserInfo info) {
                                        userInfo.setValue(info);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        e.printStackTrace();
                                    }
                                })
                        );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    public void tryRegister(UserInfo data) {

        disposable.add((userService.register(data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                    @Override
                    public void onSuccess(@NonNull UserInfo info) {
                        userInfo.setValue(info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );

    }
}
