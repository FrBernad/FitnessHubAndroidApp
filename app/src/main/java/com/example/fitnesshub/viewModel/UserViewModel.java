package com.example.fitnesshub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.APIService;
import com.example.fitnesshub.model.AuthToken;
import com.example.fitnesshub.model.VerificationData;
import com.example.fitnesshub.model.UserAPIService;
import com.example.fitnesshub.model.UserCredentials;
import com.example.fitnesshub.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<AuthToken> token = new MutableLiveData<>();
    private MutableLiveData<Boolean> verified = new MutableLiveData<>();

    private UserAPIService userService = new UserAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public MutableLiveData<Boolean> getVerified() {
        return verified;
    }

    public MutableLiveData<UserInfo> getUserData() {
        return userInfo;
    }

    public MutableLiveData<AuthToken> getToken() {
        return token;
    }

    public void verifyUser(String code) {
        disposable.add(userService.verifyEmail(new VerificationData(userInfo.getValue().getEmail(),code))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        verified.setValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        verified.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    public void resendVerification(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        disposable.add(userService.resendVerification(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
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
                        APIService.setAuthToken(authToken.getToken());
                        disposable.add(userService.getCurrentUser()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                                    @Override
                                    public void onSuccess(@NonNull UserInfo info) {
                                        userInfo.setValue(info);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        System.out.println("error!!!!!\n\n\n\n");
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
