package com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces;

import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;

import java.util.ArrayList;

public interface CanReceiveEventTypes {

    void onEventTypesRetrieved(ArrayList<EventType> events);


    void onEventTypesDatabaseFailure();
}
