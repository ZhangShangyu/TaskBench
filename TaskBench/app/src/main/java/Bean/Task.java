package Bean;


import java.sql.Date;

/**
 * Created by Lawliet on 2016/6/8.
 */
public class Task {

    private String name;
    private String team;
    private Date startdate;
    private Date deadline;
    private int schedule;

    public Task(String name, Date startdate, String team, Date deadline, int schedule) {
        this.name = name;
        this.startdate = startdate;
        this.team = team;
        this.deadline = deadline;
        this.schedule = schedule;
    }

    public Task() {
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}
