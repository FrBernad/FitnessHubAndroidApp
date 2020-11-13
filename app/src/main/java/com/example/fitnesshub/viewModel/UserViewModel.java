package com.example.fitnesshub.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.fitnesshub.model.APIService;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.model.AuthToken;
import com.example.fitnesshub.model.ErrorResponse;
import com.example.fitnesshub.model.VerificationData;
import com.example.fitnesshub.model.UserAPIService;
import com.example.fitnesshub.model.UserCredentials;
import com.example.fitnesshub.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<AuthToken> token = new MutableLiveData<>();
    private MutableLiveData<Boolean> verified = new MutableLiveData<>();

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MutableLiveData<ErrorResponse> loginError = new MutableLiveData<>();

    private UserAPIService userService;
    private CompositeDisposable disposable = new CompositeDisposable();

    private Application app;

    public UserViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        userService = new UserAPIService(application);
        app = application;
    }


    public void verifyUser(String code) {
        loading.setValue(true);
        disposable.add(userService.verifyEmail(new VerificationData(userInfo.getValue().getEmail(), code))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        verified.setValue(true);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        verified.setValue(false);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    public void logout() {
        disposable.add(userService.logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        new AppPreferences(app).setAuthToken(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    public void resendVerification(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        loading.setValue(true);
        disposable.add(userService.resendVerification(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        loading.setValue(false);
                    }
                })
        );
    }

    public void setUserData() {
        disposable.add(userService.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
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

    public void tryLogin(String username, String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        loading.setValue(true);
        disposable.add(userService.login(credentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AuthToken>() {

                    @Override
                    public void onSuccess(@NonNull AuthToken authToken) {
                        token.setValue(authToken);
                        APIService.setAuthToken(authToken.getToken());
                        AppPreferences preferences = new AppPreferences(app);
                        preferences.setAuthToken(authToken.getToken());
                        loginError.setValue(null);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            try {
                                Gson gson = new Gson();
                                ErrorResponse error;
                                error = gson.fromJson(httpException.response().errorBody().string(), new TypeToken<ErrorResponse>() {
                                }.getType());
                                loginError.setValue(error);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        e.printStackTrace();
                        loading.setValue(false);
                    }
                })
        );
    }

    public void tryRegister(UserInfo data) {
        loading.setValue(true);

        disposable.add((userService.register(data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                    @Override
                    public void onSuccess(@NonNull UserInfo info) {
                        userInfo.setValue(info);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        loading.setValue(false);
                    }
                })
        );

    }


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

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<ErrorResponse> getLoginError() {
        return loginError;
    }

    public void setLoginErrorCode(ErrorResponse error){
        loginError.setValue(error);
    }

}
