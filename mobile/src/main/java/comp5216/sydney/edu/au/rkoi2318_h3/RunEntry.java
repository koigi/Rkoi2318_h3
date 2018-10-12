package comp5216.sydney.edu.au.rkoi2318_h3;

import android.text.format.DateFormat;


import java.util.Date;


public class RunEntry {

    protected int distance;
    //private float pace, speed;
    protected Date startTime, endTime;

    public RunEntry(){
        this.startTime = new Date();
    }

    protected int getDistance() {
        return distance;
    }

    protected Date getStartTime() {
        return startTime;
    }

    protected Date getEndTime() {
        return endTime;
    }

    protected void setDistance(int distance) {
        this.distance = distance;
    }

    protected void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    protected void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    //TODO
    protected int getDuration(){
        int duration;
        Date tempTime = null;
        //If run is still in progress then we'll need to use now as the end date
        if(null == this.endTime){tempTime = new Date();
        }else {tempTime =  this.endTime;}

        if(tempTime.getTime() < this.startTime.getTime()){
            duration = 0;
        }else{
            duration = (int)(tempTime.getTime() - this.startTime.getTime())/1000;
        }
        return duration;
    }

    //TODO
    protected float getSpeed(){
        return 2/3;
    }

    //TODO
    protected float getPace(){
        return 2/3;
    }



}
