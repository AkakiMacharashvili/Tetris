import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tetris {
    static final Color[] COLORS = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.YELLOW};
    private static ScheduledExecutorService service;
    static boolean isPaused = false;
    private static TetrisModel model;
    private static Controller controller;
    private static JLayeredPane layeredPane;
    private static JPanel gamePanel;
    private static JFrame frame;
    private static JButton refreshButton;
    private static View view;
    private static JButton quitButton;
    private static ScorePanel scorePanel;
    private static LevelPanel levelPanel;
    private static GameOver gameOver;

    public static void main(String[] args) {
        setUp();
        createService();
    }

    private static void createService() {
        service = Executors.newSingleThreadScheduledExecutor();
        restartScheduler(controller, model);
        frame.requestFocusInWindow();
    }

    private static void setUp() {
        createGamePanel();
        modelViewController();
//        createPanels();
        createButtons();
        createLayerPane();
        createGameOver();
        startFrame();
        addModelListeners();
        addRefreshButton();
        addQuitButton();
    }

    private static void createLayerPane() {
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 700));
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
    }

    private static void createGameOver() {
        gameOver = new GameOver(model, refreshButton, quitButton);
    }

    private static void addModelListeners() {
        model.addListener(gameOver);
//        model.addListener(scorePanel);
    }

    private static void createGamePanel() {
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                view.draw(model, (Graphics2D) g);
            }
        };

        gamePanel.setBounds(0, 0, 400, 700);
        gamePanel.setLayout(null);
    }

    private static void createPanels() {
        scorePanel = new ScorePanel(model);
        levelPanel = new LevelPanel(model);
    }

    private static void createButtons() {
        refreshButton = new JButton("Refresh");
        quitButton = new JButton("quit");
    }

    private static void modelViewController() {
        model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT, TetrisModel.DEFAULT_COLOR_NUMBER);
        view = new View(gamePanel, model);
        controller = new Controller(model, view, gamePanel);
    }

    private static void startFrame(){
        frame = new JFrame("Tetris");
        frame.setLayout(new BorderLayout());

        frame.add(layeredPane, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                caseSwitch(e);
            }
        });
    }

    private static void addPanelsToFrame() {
        frame.add(scorePanel, BorderLayout.WEST);
        frame.add(levelPanel, BorderLayout.EAST);
    }

    private static void addRefreshButton(){
        layeredPane.add(refreshButton, JLayeredPane.PALETTE_LAYER);
        // Set bounds and add refresh button to layeredPane
        refreshButton.setBounds(150, 300, 100, 30);
        refreshButton.setVisible(false);
        refreshButton.addActionListener(_ -> refresh(view));
    }

    private static void addQuitButton(){
        layeredPane.add(quitButton, JLayeredPane.PALETTE_LAYER);
        // Set bounds and add refresh button to layeredPane
        quitButton.setBounds(150, 350, 100, 30);
        quitButton.setVisible(false);
        quitButton.addActionListener(_ -> System.exit(0));
    }

    private static void refresh(View view) {
        service.shutdownNow();
        resetModel();
        controller = new Controller(model, view, gamePanel);
        restartScheduler(controller, model);
        frame.requestFocusInWindow();
        refreshButton.setVisible(false);
        quitButton.setVisible(false);
    }

    private static void restartScheduler(Controller controller, TetrisModel model) {
        if (service != null) {
            service.shutdown();
        }
        service = Executors.newSingleThreadScheduledExecutor();
        long interval = Math.max(model.level, 1);
        service.scheduleAtFixedRate(controller::infinite, 0, interval, TimeUnit.MILLISECONDS);
    }

    private static void resetModel() {
        model.reset();
    }

    private static void caseSwitch(KeyEvent e) {
        if (!isPaused) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    controller.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    controller.moveRight();
                    break;
                case KeyEvent.VK_UP:
                    controller.rotate();
                    break;
                case KeyEvent.VK_DOWN:
                    controller.drop();
                    break;
                case KeyEvent.VK_EQUALS:
                    controller.levelUp();
                    model.FirstTry = true;
                    restartScheduler(controller, model);
                    break;
                case KeyEvent.VK_MINUS:
                    controller.levelDown();
                    model.FirstTry = true;
                    restartScheduler(controller, model);
                    break;
                case KeyEvent.VK_SPACE:
                    model.slideDown();
                    break;
                case KeyEvent.VK_P:
                    model.pause();
            }
        }
    }
}
