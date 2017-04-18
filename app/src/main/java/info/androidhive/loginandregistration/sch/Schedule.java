package info.androidhive.loginandregistration.sch;

/**
 * Created by shara on 4/18/2017.
 */

public class Schedule {

    private String session_name;
    private String session_time;

    public Schedule(String sName, String sTime)
    {
        session_name = sName;
        session_time = sTime;
    }

    public String getSessionName() {
        return session_name;
    }

    public String getSessionTime() {
        return session_time;
    }

}