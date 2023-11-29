package com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces;

import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

public interface CanReceiveAnEvent {
    void onEventRetrieved(String retrievingFunctionName,Event event);

    void onEventDatabaseFailure(String retrievingFunctionName);
}
