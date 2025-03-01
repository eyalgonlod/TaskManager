import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class TaskManagerGUI {
    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JButton loadButton;
    private String fileName = "tasks.dat";

    public TaskManagerGUI() {
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

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(loadButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // This function should read the task list from the file and update the GUI
    private void readTaskManager(String fileName) {
        // You will implement this function yourself
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagerGUI());
    }
}
