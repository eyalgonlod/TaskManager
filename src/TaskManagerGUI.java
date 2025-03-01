import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;


public class TaskManagerGUI {
    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JButton loadButton;
    private JButton markTaskButton;
    private JButton addTaskButton; // <-- Declare the addTaskButton here
    private String fileName = "tasks.dat";
    private TaskManager taskManager;


    public TaskManagerGUI() {
        // Initialize the TaskManager
        taskManager = new TaskManager();

        frame = new JFrame("Task Manager");
        frame.setSize(450, 500);
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
                    readTaskManager(fileName);
                    String selectedTaskTitle = selectedTask.split(" - ")[0].trim();
                    boolean result = taskManager.markTask(selectedTaskTitle);
                    taskManager.l.sort(Comparator.comparing(task -> task.Priority));
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

        // Add Task Button
        addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show dialog for task details
                String title = JOptionPane.showInputDialog(frame, "Enter task title:");
                String description = JOptionPane.showInputDialog(frame, "Enter task description:");
                String priorityString = JOptionPane.showInputDialog(frame, "Enter task priority (0-3):");
                String dateString = JOptionPane.showInputDialog(frame, "Enter task date (Example: 2025-03-01):");

                if (title != null && description != null && priorityString != null && dateString != null) {
                    try {
                        int priority = Integer.parseInt(priorityString);
                        LocalDate date = LocalDate.parse(dateString);

                        // Add the task to the TaskManager
                        taskManager.addTask(title, description, priority, date);

                        // Save tasks to file
                        taskManager.writeTaskManager(fileName);

                        // Refresh the task list
                        readTaskManager(fileName);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.");
                    }
                }
            }
        });

        // Delete Task Button (new addition)
        JButton deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    // Load tasks from file into TaskManager before deleting
                    readTaskManager(fileName);
                    String selectedTaskTitle = selectedTask.split(" - ")[0].trim();
                    boolean result = taskManager.deleteTask(selectedTaskTitle);  // Use deleteTask here
                    try {
                        taskManager.writeTaskManager(fileName);  // Save tasks after deleting
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (result) {
                        JOptionPane.showMessageDialog(frame, "Task deleted successfully!");
                        readTaskManager(fileName); // Refresh the task list after deletion
                    } else {
                        JOptionPane.showMessageDialog(frame, "Task not found!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to delete.");
                }
            }
        });

        // Add buttons to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Explicitly set layout
        buttonPanel.add(loadButton);
        buttonPanel.add(markTaskButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(deleteTaskButton);  // Add Delete Task button here
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        // Revalidate and repaint to make sure layout is updated
        frame.revalidate();
        frame.repaint();
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
