import java.time.LocalDate;

public class Task {
    String taskName;
    boolean isCompleted;
    LocalDate dueDate;
    String priority;

    Task(String taskName){
        this.taskName = taskName;
        this.isCompleted = false;
        this.dueDate = null;
        this.priority = "Medium";
    }
    
    public Task(String taskName, LocalDate dueDate, String priority){
        this.taskName = taskName;
        this.isCompleted = false;
        this.dueDate = dueDate;
        this.priority = priority;
    }
    void markCompleted() {
        isCompleted = true;
    }
}