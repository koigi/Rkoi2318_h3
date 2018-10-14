package comp5216.sydney.edu.au.rkoi2318_h3;

import android.text.format.DateFormat;



import java.util.Date;
import java.util.UUID;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "RunEntry")
public class RunEntry {

    @PrimaryKey
    @NonNull
    private String runId;

    protected int distance, duration;
    private float pace, speed;
    protected Date startTime, endTime;

    public RunEntry(){
        this.runId = UUID.randomUUID().toString();
        this.startTime = new Date();
    }
    public RunEntry(Date start, Date end, int distance){
        this.runId = UUID.randomUUID().toString();
        this.startTime = start;
        this.endTime = end;
        this.distance = distance;
        calculateDuration();
        calculatePace();
        calculateSpeed();

    }

    //Why you would have a null run ID I don't know but needed a setter so here you are.
    protected void setRunId(String runId){
        if (this.runId == null) this.runId = UUID.randomUUID().toString();
    }
    protected int getDistance() {
        return this.distance;
    }

    protected String getRunId(){return this.runId;}

    protected float getPace(){
        if(0 == this.distance || 0 == this.duration){
            return 0;
        }else{
            this.calculatePace();
            return this.pace;
        }
    }

    protected float getSpeed(){
        if(0 == this.distance || 0 == this.duration){
            return 0;
        }else{
            this.calculateSpeed();
            return this.speed;
        }
    }

    protected Date getStartTime() {
        return this.startTime;
    }

    protected Date getEndTime() {
        return this.endTime;
    }

    protected void setDistance(int distance) {
        this.distance = distance;
    }

    protected void setSpeed(float speed){this.calculateSpeed();}

    protected void setPace(float pace){this.calculatePace();}

    protected void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    protected void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    //Returns the duration of a run in seconds when th start time is set.
    private boolean calculateDuration(){

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
    private boolean calculateSpeed(){
        boolean successfulCalc = false;
        if ((this.duration <= 0) || (this.distance <= 0)){
            this.speed = 0;
        }else {
            this.speed = (float)this.distance / (float)this.duration;
            successfulCalc = true;
        }
        return successfulCalc;
    }

    //TODO
    // Pace is defined here as the distance covered in a certain amount of time.
    // This function will return pace as Minutes per kilometer.
    private boolean calculatePace(){
        boolean successfulCalc = false;
        if ((this.duration <= 0) || (this.distance <= 0)){
            this.pace = 0;
        }else {
            float durationMinutes = this.duration/60;
            float distanceKilometers = this.distance / 1000;
            this.pace = durationMinutes / distanceKilometers;
            successfulCalc = true;
        }
        return successfulCalc;
    }



}
