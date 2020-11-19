package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutineExecution;
import com.example.fitnesshub.model.RoutineHistory;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoutinesViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineData>> routineCards = new MutableLiveData<>();
    private MutableLiveData<List<RoutineData>> userRoutines = new MutableLiveData<>();
    private MutableLiveData<List<RoutineData>> userHistory = new MutableLiveData<>();
    private MutableLiveData<RoutineData> currentRoutine = new MutableLiveData<>();
    private MutableLiveData<Boolean> noMoreEntries = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> routinesFirstLoad = new MutableLiveData<>(true);

    private RoutinesAPIService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    private int currentPage = 0;
    private int totalPages = 0;
    private int itemsPerRequest = 5;
    private boolean isLastPage = false;
    private String direction = "desc";
    private String filter = null;
    private String orderBy = "dateCreated";
    private int orderById = 0;
    private int directionId = 0;
    private int filterId = -1;

    public RoutinesViewModel(@NonNull Application application) {
        super(application);
        routinesService = new RoutinesAPIService(application);
    }

    public void resetData() {
        currentPage = 0;
        isLastPage = false;
        totalPages = 0;
        routineCards.setValue(new ArrayList<>());
        updateData();
    }

    public void updateData() {
        if (!isLastPage) {
            fetchFromRemote();
        }
    }

    public void updateUserRoutines() {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("orderBy", orderBy);
        options.put("direction", direction);
        options.put("size", String.valueOf(1000));

        disposable.add(
                routinesService.getUserRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineData> routinesEntries) {
                                userRoutines.setValue(routinesEntries.getEntries());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void getRoutineById(int id) {
        disposable.add(
                routinesService.getRoutineById(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<RoutineData>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RoutineData routine) {
                                int id = routine.getCategory().getId();
                                switch (id) {
                                    case 1:

                                        routine.setImage(String.valueOf(R.drawable.p1));
                                        break;

                                    case 2:
                                        routine.setImage(String.valueOf(R.drawable.p2));

                                        break;

                                    case 3:
                                        routine.setImage(String.valueOf(R.drawable.p3));

                                        break;

                                    case 4:
                                        routine.setImage(String.valueOf(R.drawable.p4));

                                        break;

                                    case 5:
                                        routine.setImage(String.valueOf(R.drawable.p5));

                                        break;

                                    case 6:
                                        routine.setImage(String.valueOf(R.drawable.p6));

                                        break;

                                    case 7:
                                        routine.setImage(String.valueOf(R.drawable.p7));

                                        break;
                                }

                                currentRoutine.setValue(routine);
                            }
                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void updateUserHistory() {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("direction", "asc");
        options.put("size", String.valueOf(1000));

        disposable.add(
                routinesService.getUserHistory(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineHistory>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineHistory> routinesEntries) {
                                List<RoutineData> routines = new ArrayList<>();
                                for (RoutineHistory execution : routinesEntries.getEntries()) {
                                    routines.add(execution.getRoutine());
                                }
                                userHistory.setValue(routines);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }


    public void orderRoutines(int option) {
        directionId = option;
        switch (option) {
            case 0:
                direction = "desc";
                break;

            case 1:
                direction = "asc";
                break;
        }

        applyChanges();
    }

    public void filterRoutines(Integer option) {
        filterId = option;
        switch (option) {
            case -1:
                filter = null;
                break;

            case 0:
                filter = "rookie";
                break;

            case 1:
                filter = "beginner";
                break;

            case 2:
                filter = "intermediate";
                break;

            case 3:
                filter = "advanced";
                break;

        }

        applyChanges();
    }

    public void sortRoutines(int option) {
        orderById = option;
        switch (option) {
            case 0:
                orderBy = "dateCreated";
                break;

            case 1:
                orderBy = "averageRating";
                break;

            case 2:
                orderBy = "categoryId";
                break;

            case 3:
                orderBy = "difficulty";
                break;

            case 4:
                orderBy = "name";
                break;
        }

        applyChanges();
    }

    private void applyChanges() {
        routineCards.setValue(new ArrayList<>());
        currentPage = 0;
        isLastPage = false;
        totalPages = 0;
        fetchFromRemote();
    }

    private void fetchFromRemote() {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(currentPage));
        options.put("orderBy", orderBy);
        options.put("direction", direction);
        options.put("size", String.valueOf(itemsPerRequest));
        if (filter != null) {
            options.put("difficulty", filter);
        }

        loading.setValue(true);

        disposable.add(
                routinesService.getRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineData> routinesEntries) {
                                isLastPage = routinesEntries.getLastPage();
                                noMoreEntries.setValue(isLastPage);
                                currentPage++;
                                List<RoutineData> aux = new ArrayList<>();
                                if (routineCards.getValue() != null) {
                                    aux = routineCards.getValue();
                                }
                                aux.addAll(routinesEntries.getEntries());
                                routineCards.setValue(aux);
                                totalPages = (int) Math.ceil(routinesEntries.getTotalCount() / (double) itemsPerRequest);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void addRoutineExcecution(int routineId) {
        RoutineExecution routineExecution = new RoutineExecution(1, false);
        disposable.add(
                routinesService.addRoutineExecution(routineId, routineExecution)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<RoutineData>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RoutineData routinesEntries) {
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<RoutineData>> getRoutineCards() {
        return routineCards;
    }

    public MutableLiveData<List<RoutineData>> getUserHistory() {
        return userHistory;
    }

    public MutableLiveData<Boolean> getNoMoreEntries() {
        return noMoreEntries;
    }

    public MutableLiveData<Boolean> getRoutinesFirstLoad() {
        return routinesFirstLoad;
    }

    public void setRoutinesFirstLoad(Boolean firstLoad) {
        routinesFirstLoad.setValue(firstLoad);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<List<RoutineData>> getUserRoutines() {
        return userRoutines;
    }

    public MutableLiveData<RoutineData> getCurrentRoutine() {
        return currentRoutine;
    }

    public String getDirection() {
        return direction;
    }

    public String getFilter() {
        return filter;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public int getOrderById() {
        return orderById;
    }

    public int getDirectionId() {
        return directionId;
    }

    public int getFilterId() {
        return filterId;
    }
}

