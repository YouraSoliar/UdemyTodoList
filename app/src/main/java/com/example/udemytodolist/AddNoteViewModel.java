package com.example.udemytodolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> isFinish = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NotesDatabase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getIsFinish() {
        return isFinish;
    }

    public void add (Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.add(note);
                isFinish.postValue(true);
            }
        });
        thread.start();
    }
}
