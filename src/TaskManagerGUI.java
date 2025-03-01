import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class TaskManagerGUI {
    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JButton loadButton;
    private JButton markTaskButton;
    private String fileName = "tasks.dat";
    private TaskManager taskManager;

    public TaskManagerGUI() {
        // Initialize the TaskManager
        taskManager = new TaskManager();

        frame = new JFrame("Task Manager");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        loadButton = new JButton("Load Tasks");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readTaskManager(fileName);
            }
        });

        markTaskButton = new JButton("Mark Task");
        markTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    // Load tasks from file into TaskManager before marking
                    readTaskManager(fileName);  // This loads the tasks into the TaskManager
                    String selectedTaskTitle = selectedTask.split(" - ")[0].trim();
                    boolean result = taskManager.markTask(selectedTaskTitle);
                    try {
                        taskManager.writeTaskManager(fileName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (result) {
                        JOptionPane.showMessageDialog(frame, "Task marked as completed!");
                        readTaskManager(fileName); // Refresh the task list to reflect the change
                    } else {
                        JOptionPane.showMessageDialog(frame, "Task not found!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to mark.");
                }
            }
        });



        frame.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(loadButton);
        buttonPanel.add(markTaskButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void readTaskManager(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Task> tasks = (List<Task>) ois.readObject();
            taskManager.l.clear();
            taskManager.l.addAll(tasks);

            taskListModel.clear();
            for (Task t : tasks) {
                String taskDetails = t.getTitle() + " - Priority: " + t.getPriority();
                taskListModel.addElement(taskDetails);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
        });
    }
}
