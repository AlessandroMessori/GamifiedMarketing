package fakeEntities;

public class User {

    private final String uname;
    private final String email;
    private boolean deletedQuestionnaire;

    public User(String uname, String email) {
        this.uname = uname;
        this.email = email;
        this.deletedQuestionnaire = false;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public boolean getDeletedQuestionnaire() {
        return deletedQuestionnaire;
    }

    public void setDeletedQuestionnaire(boolean deletedQuestionnaire) {
        this.deletedQuestionnaire = deletedQuestionnaire;
    }
}
