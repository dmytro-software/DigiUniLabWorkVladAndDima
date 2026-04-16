package Project.service;

import Project.Models.LoadState;

public class LoadStateManager {
    private LoadState currentState = LoadState.EMPTY;

    public LoadState getState() {
        return currentState;
    }

    public void setState(LoadState state) {
        this.currentState = state;
    }

    public boolean canLoadStudents() {
        return currentState.ordinal() >= LoadState.DEPARTMENTS_LOADED.ordinal();
    }

    public boolean canLoadDepartments() {
        return currentState.ordinal() >= LoadState.FACULTIES_LOADED.ordinal();
    }

    public boolean canLoadFaculties() {
        return currentState.ordinal() >= LoadState.UNIVERSITY_LOADED.ordinal();
    }
}
