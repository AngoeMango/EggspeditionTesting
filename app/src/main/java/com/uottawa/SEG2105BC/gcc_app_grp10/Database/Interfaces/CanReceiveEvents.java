package com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces;

import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;

import java.util.ArrayList;

public interface CanReceiveEvents {
    void onEventsRetrieved(ArrayList<Event> events);


    void onEventsDatabaseFailure();
}
