package Bean;



/**
 * Created by Lawliet on 2016/6/8.
 */
public class Task {

    private String name;
    private String teamOrUser;
    private String startdate;
    private String deadline;
    private int schedule;
    private int taskId;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public Task(String name, String startdate, String teamOrUser, String deadline, int schedule) {
        this.name = name;
        this.startdate = startdate;
        this.teamOrUser = teamOrUser;
        this.deadline = deadline;
        this.schedule = schedule;
    }

    public Task() {
    }

    public String getTeamOrUser() {
        return this.teamOrUser;
    }

    public void setTeamOrUser(String teamOrUser) {
        this.teamOrUser = teamOrUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
