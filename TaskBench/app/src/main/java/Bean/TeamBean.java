package Bean;

/**
 * Created by Lawliet on 2016/6/11.
 */
public class TeamBean {
    private int id;
    private String name;
    private String leaderName;
    private int numberOfMember;
    private String description;

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int leaderId;

    public String getLeaderName() {
        return leaderName;
    }

    public String getDescription(){
        return description;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
