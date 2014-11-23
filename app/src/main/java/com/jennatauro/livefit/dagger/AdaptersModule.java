package com.jennatauro.livefit.dagger;

import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jennatauro on 2014-11-22.
 */
@Module(
        library = true
)
public class AdaptersModule {

    @Provides
    WorkoutAdapter provideWorkoutAdapter(){
        return new WorkoutAdapter();
    }

    @Provides
    ExerciseAdapter provideExerciseAdapter(){
        return new ExerciseAdapter();
    }
}
