
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class MainGUI extends JFrame {

    private ArrayList<Task> tasks = new ArrayList<>();

    private JPanel mainPanel;
    private JPanel sidebar;
    private JPanel contentPanel;
    private JPanel taskListPanel;

    private JLabel totalLabel;
    private JLabel completedLabel;
    private JLabel pendingLabel;
    private JLabel themeButton;

    private String currentPage = "Dashboard";
    private String currentTheme = "Dark";

    private Color background;
    private Color sidebarColor;
    private Color cardColor;
    private Color foreground;
    private Color secondary;
    private Color borderColor;
    private Color accent;

    private JTextField searchField;

    public MainGUI() {

        setTitle("TaskFlow");
        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        applyTheme("Dark");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        createSidebar();

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(background);
        contentPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        showDashboard();
    }

    // ================= THEME SYSTEM =================

    private void applyTheme(String theme) {

        currentTheme = theme;

        if (theme.equals("Dark")) {

            background = new Color(15, 18, 30);
            sidebarColor = new Color(20, 24, 39);
            cardColor = new Color(27, 32, 51);
            foreground = new Color(244, 245, 255);
            secondary = new Color(153, 161, 185);
            borderColor = new Color(47, 53, 76);
            accent = new Color(124, 92, 255);

        } else if (theme.equals("Light")) {

            background = new Color(245, 247, 252);
            sidebarColor = Color.WHITE;
            cardColor = Color.WHITE;
            foreground = new Color(31, 35, 53);
            secondary = new Color(112, 119, 143);
            borderColor = new Color(226, 230, 240);
            accent = new Color(104, 78, 230);

        } else {

            background = Color.BLACK;
            sidebarColor = new Color(8, 8, 8);
            cardColor = new Color(18, 18, 18);
            foreground = Color.WHITE;
            secondary = Color.YELLOW;
            borderColor = Color.YELLOW;
            accent = Color.YELLOW;
        }
    }

    private void changeTheme(String theme) {

        applyTheme(theme);

        mainPanel.setBackground(background);
        createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        if (currentPage.equals("Dashboard")) {
            showDashboard();
        } else if (currentPage.equals("My Tasks")) {
            showMyTasks();
        } else {
            showAddTask();
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // ================= SIDEBAR =================

    private void createSidebar() {

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 18, 25, 18));

        JLabel logo = new JLabel("✦  TaskFlow");
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setForeground(accent);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(50));

        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        menuLabel.setForeground(secondary);
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(15));

        addSidebarButton("▦   Dashboard", "Dashboard");
        addSidebarButton("☷   My Tasks", "My Tasks");
        addSidebarButton("＋   Add New Task", "Add Task");

        sidebar.add(Box.createVerticalGlue());

        JLabel version = new JLabel("TASKFLOW  •  DESKTOP");
        version.setFont(new Font("SansSerif", Font.PLAIN, 10));
        version.setForeground(secondary);
        version.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(version);
    }

    private void addSidebarButton(String text, String page) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(
                currentPage.equals(page) ? accent : secondary
        );

        button.setBackground(
                currentPage.equals(page) ? cardColor : sidebarColor
        );

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(205, 48));
        button.setPreferredSize(new Dimension(205, 48));
        button.setBorder(new EmptyBorder(12, 15, 12, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addActionListener(e -> {

            if (page.equals("Dashboard")) {
                showDashboard();
            } else if (page.equals("My Tasks")) {
                showMyTasks();
            } else {
                showAddTask();
            }
        });

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    // ================= PAGE HEADER =================

    private JPanel createHeader(String title, String subtitle) {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(background);
        header.setBorder(new EmptyBorder(0, 0, 25, 0));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(background);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 29));
        heading.setForeground(foreground);

        JLabel description = new JLabel(subtitle);
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(secondary);

        textPanel.add(heading);
        textPanel.add(Box.createVerticalStrut(7));
        textPanel.add(description);

        themeButton = new JLabel("◉  " + currentTheme + " Mode  ▼");
        themeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        themeButton.setForeground(foreground);
        themeButton.setOpaque(true);
        themeButton.setBackground(cardColor);
        themeButton.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(12, 15, 12, 15)
        ));
        themeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        themeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showThemeMenu(themeButton);
            }
        });

        header.add(textPanel, BorderLayout.WEST);
        header.add(themeButton, BorderLayout.EAST);

        return header;
    }

    private void showThemeMenu(Component component) {

        JPopupMenu menu = new JPopupMenu();

        String[] themes = {"Dark Mode", "Light Mode", "High Contrast"};

        for (String item : themes) {

            JMenuItem option = new JMenuItem(item);

            option.addActionListener(e -> {

                if (item.equals("Dark Mode")) {
                    changeTheme("Dark");
                } else if (item.equals("Light Mode")) {
                    changeTheme("Light");
                } else {
                    changeTheme("Contrast");
                }
            });

            menu.add(option);
        }

        menu.show(component, 0, component.getHeight());
    }

    // ================= DASHBOARD =================

    private void showDashboard() {

        currentPage = "Dashboard";
        contentPanel.removeAll();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(background);

        page.add(
                createHeader("Dashboard", "Here's what's happening with your tasks."),
                BorderLayout.NORTH
        );

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(background);

        JPanel stats = new JPanel(new GridLayout(1, 3, 18, 0));
        stats.setBackground(background);

        totalLabel = new JLabel();
        completedLabel = new JLabel();
        pendingLabel = new JLabel();

        stats.add(createStatCard("TOTAL TASKS", totalLabel, "▦"));
        stats.add(createStatCard("COMPLETED", completedLabel, "✓"));
        stats.add(createStatCard("PENDING", pendingLabel, "◷"));

        updateStats();

        body.add(stats);
        body.add(Box.createVerticalStrut(30));

        JPanel recentPanel = createPanel();

        recentPanel.setLayout(new BorderLayout(0, 18));
        recentPanel.setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel recentTitle = new JLabel("Recent Tasks");
        recentTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        recentTitle.setForeground(foreground);

        recentPanel.add(recentTitle, BorderLayout.NORTH);

        JPanel recentList = new JPanel();
        recentList.setLayout(new BoxLayout(recentList, BoxLayout.Y_AXIS));
        recentList.setBackground(cardColor);

        if (tasks.isEmpty()) {

            JLabel empty = new JLabel("No tasks yet. Add your first task!");
            empty.setForeground(secondary);
            empty.setFont(new Font("SansSerif", Font.PLAIN, 14));

            recentList.add(empty);

        } else {

            int start = Math.max(0, tasks.size() - 5);

            for (int i = tasks.size() - 1; i >= start; i--) {

                Task task = tasks.get(i);

                JLabel taskLabel = new JLabel(
                        (task.isCompleted ? "✓   " : "○   ") + task.taskName
                );

                taskLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                taskLabel.setForeground(
                        task.isCompleted ? secondary : foreground
                );

                recentList.add(taskLabel);
                recentList.add(Box.createVerticalStrut(15));
            }
        }

        recentPanel.add(recentList, BorderLayout.CENTER);

        body.add(recentPanel);

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(background);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        page.add(scroll, BorderLayout.CENTER);

        contentPanel.add(page);
        refresh();
    }

    private JPanel createStatCard(String title, JLabel number, String icon) {

        JPanel card = createPanel();
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        titleLabel.setForeground(secondary);

        number.setText("0");
        number.setFont(new Font("SansSerif", Font.BOLD, 32));
        number.setForeground(foreground);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        iconLabel.setForeground(accent);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);
        card.add(iconLabel, BorderLayout.EAST);

        return card;
    }

    private void updateStats() {

        int total = tasks.size();
        int completed = 0;

        for (Task task : tasks) {
            if (task.isCompleted) {
                completed++;
            }
        }

        if (totalLabel != null) {
            totalLabel.setText(String.valueOf(total));
            completedLabel.setText(String.valueOf(completed));
            pendingLabel.setText(String.valueOf(total - completed));
        }
    }

    // ================= MY TASKS =================

    private void showMyTasks() {

        currentPage = "My Tasks";
        contentPanel.removeAll();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(background);

        page.add(
                createHeader("My Tasks", "Manage all your tasks in one place."),
                BorderLayout.NORTH
        );

        JPanel body = new JPanel(new BorderLayout(0, 20));
        body.setBackground(background);

        JPanel top = new JPanel(new BorderLayout(15, 0));
        top.setBackground(background);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setForeground(foreground);
        searchField.setBackground(cardColor);
        searchField.setCaretColor(foreground);
        searchField.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(13, 15, 13, 15)
        ));

        searchField.setToolTipText("Search your tasks...");
        top.add(searchField, BorderLayout.CENTER);

        JButton addButton = createButton("+  Add Task");
        addButton.addActionListener(e -> showAddTask());

        top.add(addButton, BorderLayout.EAST);

        body.add(top, BorderLayout.NORTH);

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(background);

        JScrollPane scroll = new JScrollPane(taskListPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(background);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        body.add(scroll, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                refreshTaskList();
            }

            public void removeUpdate(DocumentEvent e) {
                refreshTaskList();
            }

            public void changedUpdate(DocumentEvent e) {
                refreshTaskList();
            }
        });

        page.add(body, BorderLayout.CENTER);

        contentPanel.add(page);
        refreshTaskList();
        refresh();
    }

    private void refreshTaskList() {

        if (taskListPanel == null) {
            return;
        }

        taskListPanel.removeAll();

        String search = searchField == null
                ? ""
                : searchField.getText().trim().toLowerCase();

        boolean found = false;

        for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            if (!task.taskName.toLowerCase().contains(search)) {
                continue;
            }

            found = true;

            final int taskIndex = i;

            JPanel row = createPanel();
            row.setLayout(new BorderLayout(15, 0));
            row.setBorder(new EmptyBorder(17, 18, 17, 18));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBackground(cardColor);

            JLabel taskName = new JLabel(task.taskName);
            taskName.setFont(new Font("SansSerif", Font.BOLD, 14));
            taskName.setForeground(
                    task.isCompleted ? secondary : foreground
            );

            JLabel status = new JLabel(
                    task.isCompleted ? "COMPLETED" : "PENDING"
            );

            status.setFont(new Font("SansSerif", Font.BOLD, 10));
            status.setForeground(
                    task.isCompleted ? new Color(50, 190, 130) : accent
            );

            info.add(taskName);
            info.add(Box.createVerticalStrut(7));
            info.add(status);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            actions.setBackground(cardColor);

            JButton completeButton = createSmallButton(
                    task.isCompleted ? "Completed" : "Complete"
            );

            completeButton.setEnabled(!task.isCompleted);

            completeButton.addActionListener(e -> {

                tasks.get(taskIndex).markCompleted();

                showMyTasks();
            });

            JButton deleteButton = createSmallButton("Delete");
            deleteButton.setForeground(new Color(235, 95, 110));

            deleteButton.addActionListener(e -> {

                tasks.remove(taskIndex);

                showMyTasks();
                showToast("Task deleted successfully!");
            });

            actions.add(completeButton);
            actions.add(deleteButton);

            row.add(info, BorderLayout.CENTER);
            row.add(actions, BorderLayout.EAST);

            taskListPanel.add(row);
            taskListPanel.add(Box.createVerticalStrut(12));
        }

        if (!found) {

            JLabel empty = new JLabel(
                    tasks.isEmpty()
                            ? "No tasks yet. Add your first task!"
                            : "No matching tasks found."
            );

            empty.setForeground(secondary);
            empty.setFont(new Font("SansSerif", Font.PLAIN, 14));

            taskListPanel.add(Box.createVerticalStrut(25));
            taskListPanel.add(empty);
        }

        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    // ================= ADD TASK =================

    private void showAddTask() {

        currentPage = "Add Task";
        contentPanel.removeAll();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(background);

        page.add(
                createHeader("Add New Task", "Create a task and keep moving forward."),
                BorderLayout.NORTH
        );

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(background);

        JPanel form = createPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Increased form height for the new fields
        form.setPreferredSize(new Dimension(600, 490));

        JLabel title = new JLabel("Task Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(foreground);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(title);
        form.add(Box.createVerticalStrut(25));

        // TASK NAME

        JLabel taskLabel = new JLabel("TASK NAME");
        taskLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        taskLabel.setForeground(secondary);
        taskLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(taskLabel);
        form.add(Box.createVerticalStrut(10));

        JTextField taskInput = new JTextField();

        taskInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taskInput.setForeground(foreground);
        taskInput.setBackground(background);
        taskInput.setCaretColor(foreground);
        taskInput.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(14, 14, 14, 14)
        ));

        taskInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        taskInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(taskInput);

        // DEADLINE

        form.add(Box.createVerticalStrut(20));

        JLabel deadlineLabel = new JLabel("DEADLINE (DD-MM-YYYY)");
        deadlineLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        deadlineLabel.setForeground(secondary);
        deadlineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(deadlineLabel);
        form.add(Box.createVerticalStrut(10));

        JTextField deadlineInput = new JTextField();

        deadlineInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        deadlineInput.setForeground(foreground);
        deadlineInput.setBackground(background);
        deadlineInput.setCaretColor(foreground);
        deadlineInput.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(14, 14, 14, 14)
        ));

        deadlineInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        deadlineInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(deadlineInput);

        // PRIORITY

        form.add(Box.createVerticalStrut(20));

        JLabel priorityLabel = new JLabel("PRIORITY");
        priorityLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        priorityLabel.setForeground(secondary);
        priorityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(priorityLabel);
        form.add(Box.createVerticalStrut(10));

        String[] priorities = {"Low", "Medium", "High"};

        JComboBox<String> priorityInput = new JComboBox<>(priorities);

        priorityInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priorityInput.setForeground(foreground);
        priorityInput.setBackground(cardColor);
        priorityInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        priorityInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Default priority
        priorityInput.setSelectedItem("Medium");

        form.add(priorityInput);
        form.add(Box.createVerticalStrut(25));

        // CREATE TASK BUTTON

        JButton saveButton = createButton("＋  Create Task");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(190, 45));

        saveButton.addActionListener(e -> {

            String taskName = taskInput.getText().trim();
            String deadlineText = deadlineInput.getText().trim();

            String priority = (String) priorityInput.getSelectedItem();

            // Validate task name

            if (taskName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a task name.",
                        "Empty Task",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Validate deadline

            LocalDate deadline = null;

            if (!deadlineText.isEmpty()) {

                try {

                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("dd-MM-uuuu")
                                    .withResolverStyle(ResolverStyle.STRICT);

                    deadline = LocalDate.parse(deadlineText, formatter);

                } catch (DateTimeParseException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid date in DD-MM-YYYY format.",
                            "Invalid Deadline",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            // Create task with deadline and priority

            tasks.add(new Task(taskName, deadline, priority));

            showMyTasks();
            showToast("Task added successfully!");
        });

        form.add(saveButton);

        body.add(form);

        page.add(body, BorderLayout.CENTER);

        contentPanel.add(page);
        refresh();
    }

    // ================= CUSTOM UI COMPONENTS =================

    private JPanel createPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(cardColor);
        panel.setBorder(new LineBorder(borderColor, 1, true));

        return panel;
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(accent);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        return button;
    }

    private JButton createSmallButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        return button;
    }

    private void refresh() {

        contentPanel.revalidate();
        contentPanel.repaint();

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // ================= ANIMATED TOAST NOTIFICATION =================

    private void showToast(String message) {

        JWindow toast = new JWindow(this);

        JPanel panel = new JPanel(new BorderLayout(12, 0)) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(30, 190, 130, 190));

                g2.fillRoundRect(
                        0, 0,
                        getWidth(),
                        getHeight(),
                        20, 20
                );

                g2.dispose();
            }
        };

        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(18, 20, 18, 25));

        JLabel icon = new JLabel("✓");
        icon.setFont(new Font("SansSerif", Font.BOLD, 25));
        icon.setForeground(Color.WHITE);

        JLabel text = new JLabel(message);
        text.setFont(new Font("SansSerif", Font.BOLD, 14));
        text.setForeground(Color.WHITE);

        panel.add(icon, BorderLayout.WEST);
        panel.add(text, BorderLayout.CENTER);

        toast.setContentPane(panel);
        toast.setSize(300, 65);
        toast.setBackground(new Color(0, 0, 0, 0));

        Rectangle bounds = getBounds();

        int targetX = bounds.x + bounds.width - 325;
        int startX = bounds.x + bounds.width + 10;

        int y = bounds.y + (bounds.height / 2) - 32;

        toast.setLocation(startX, y);
        toast.setVisible(true);

        final int[] x = {startX};

        Timer slideIn = new Timer(10, null);

        slideIn.addActionListener(e -> {

            x[0] -= 15;

            if (x[0] <= targetX) {

                x[0] = targetX;
                toast.setLocation(x[0], y);

                slideIn.stop();

                Timer delay = new Timer(1500, event -> {

                    Timer slideOut = new Timer(10, null);

                    slideOut.addActionListener(outEvent -> {

                        x[0] += 15;

                        toast.setLocation(x[0], y);

                        if (x[0] >= startX) {

                            slideOut.stop();
                            toast.dispose();
                        }
                    });

                    slideOut.start();
                });

                delay.setRepeats(false);
                delay.start();
            }

            toast.setLocation(x[0], y);
        });

        slideIn.start();
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainGUI app = new MainGUI();
            app.setVisible(true);

        });
    }
}
