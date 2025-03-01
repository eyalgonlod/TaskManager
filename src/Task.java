

import java.io.*;
import java.time.LocalDate;

public class Task implements Serializable {
    String Title;
    String Description;
    int Priority;
    LocalDate Date;

    Task(String title, String description, int priority, LocalDate date) {
        if (priority <= 3 && priority >= 0) {
            this.Priority = priority;
        } else {
            this.Priority = -1;
        }
        this.Title = title;
        this.Description = description;
        this.Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getPriority() {
        return Priority;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setPriority(int priority) {
        if (priority >= 0 && priority <= 3) {
            this.Priority = priority;
        } else {
            System.out.println("Invalid priority!");
        }
    }




}
