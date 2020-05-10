package com.example.codetour;

<<<<<<< Updated upstream
=======
import android.content.Context;

import java.util.List;

>>>>>>> Stashed changes
public interface InputContract {
    interface View{

    }
<<<<<<< Updated upstream
    interface Presenter{

=======
    interface Presenter extends BaseContract.Presenter<View>{
        void makeTripSchedule(String name, String startDate, String endDate, int pNum,
                                     int tourBudget, int accBudget, int[] startTime, int[] endTime, List<String> food_selection,List<String> theme_selection);
        void clear();
        TripSchedule getTripSchedule();
>>>>>>> Stashed changes
    }
}
