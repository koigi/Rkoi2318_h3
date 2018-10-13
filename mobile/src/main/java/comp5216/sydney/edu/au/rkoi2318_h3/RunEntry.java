package comp5216.sydney.edu.au.rkoi2318_h3;

import android.text.format.DateFormat;



import java.util.Date;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RunEntry {

    @PrimaryKey
    @NonNull
    private int runId;

    protected int distance, duration;
    private float pace, speed;
    protected Date startTime, endTime;

    public RunEntry(){
        this.startTime = new Date();
    }

    protected int getDistance() {
        return this.distance;
    }

    protected float getPace(){return this.pace;}

    protected float getSpeed(){return this.speed;}

    protected Date getStartTime() {
        return this.startTime;
    }

    protected Date getEndTime() {
        return this.endTime;
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

    //Returns the duration of a run in seconds
    protected boolean calculateDuration(){

        Date tempTime = null;
        boolean successfulCalc = false;
        //If run is still in progress then we'll need to use now as the end date
        if(null == this.endTime){tempTime = new Date();
        }else {tempTime =  this.endTime;}

        if(tempTime.getTime() < this.startTime.getTime()){
            this.duration = 0;
        }else{
            this.duration = (int)(tempTime.getTime() - this.startTime.getTime())/1000;
            successfulCalc = true;
        }
        return successfulCalc;
    }

    //Speed is defined as the amount of time it takes to cover a certain distance
    // This function will set the speed to
    protected boolean calculateSpeed(){
        boolean successfulCalc = false;
        if ((this.duration <= 0) || (this.distance <= 0)){
            this.speed = 0;
        }else {
            this.pace = this.distance / this.duration;
            successfulCalc = true;
        }
        return successfulCalc;
    }

    //TODO
    // Pace is defined here as the distance covered in a certain amount of time.
    // This function will return pace as seconds per meter.
    protected boolean calculatePace(){
        boolean successfulCalc = false;
        if ((this.duration <= 0) || (this.distance <= 0)){
            this.pace = 0;
        }else {
            this.pace = this.duration / this.distance;
            successfulCalc = true;
        }
        return successfulCalc;
    }



}
