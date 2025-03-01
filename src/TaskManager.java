import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    List<Task> l;

    TaskManager() {
        l = new ArrayList<>();
    }


    public void addTask(String title, String description, int priority, LocalDate date) {
        for (int i = 0; i < this.l.size(); i++) {
            if (this.l.get(i).getTitle().equals(title))  // title is the "id", there cannot be 2 same tasks.
            {
                return;
            }
        }
        Task t = new Task(title, description, priority, date);
        this.l.add(t);  // adding the task to the list of tasks
    }

    public boolean markTask(String title) {
        for (int i = 0; i < this.l.size(); i++) {
            if (this.l.get(i).getTitle().equals(title)) {
                this.l.get(i).setPriority(0);// if found, makes the priority status 0
                return true;  //completed and returning true cause it worked
            }
        }
        return false; // no task found so returning false
    }

    public boolean deleteTask(String title) {
        for (int i = 0; i < this.l.size(); i++) {
            if (this.l.get(i).getTitle().equals(title)) {
                this.l.remove(i);   // removing the task
                return true; // removed and worked
            }
        }
        return false;  // not found task
    }

    public boolean writeTaskManager(String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(this.l);
        oos.flush();
        oos.close();
        return true;
    }


}