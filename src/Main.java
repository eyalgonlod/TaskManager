import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create the TaskManager instance
        TaskManager taskManager = new TaskManager();

        // Add some sample tasks to the TaskManager
        taskManager.addTask("Task 1", "Description of Task 1", 2, LocalDate.now());
        taskManager.addTask("Task 2", "Description of Task 2", 1, LocalDate.now().plusDays(1));
        taskManager.addTask("Task 3", "Description of Task 3", 3, LocalDate.now().plusDays(2));

        // Save the tasks to a file (you can test the file creation)
        try {
            taskManager.writeTaskManager("tasks.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now run the GUI to load and display the tasks
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
        });
    }
}
