package de.htwg.se.minesweeper.aview.gui

import de.htwg.se.minesweeper.controller.{CellChanged, Controller, GridSizeChanged}
import java.awt._
import java.awt.Dimension
import javax.swing._
import java.awt.event._
import scala.swing.Frame

class Gui(controller: Controller) extends JFrame("HTWG Minesweeper") with ActionListener with ContainerListener {

  var fwidth: Int = _
  var fheight: Int = _
  var savedHeight: Int = 10
  var savedWidth: Int = 10
  var savedNumMines: Int = 10
  var numberOfMine: Int = 10
  var detectedMine: Int = 0
  var ic: Array[ImageIcon] = new Array[ImageIcon](14)
  var blocks: Array[Array[JButton]] = _
  var panelBlock: JPanel = new JPanel()
  var panelInfo: JPanel = new JPanel()
  var tf_mine: JTextField = _
  var tf_time: JTextField = _
  var resetButton: JButton = new JButton("")
  var startTimeBool: Boolean = false
  var sw: StopWatch = new StopWatch()
  var mh: MouseHandler = _
  var p: Point = _
  var lastgame: Int = 1
  var var1: Int = _
  var var2: Int = _

  setMenu()
  setLocation(600, 300)
  setIc()
  controller.createGrid(10, 10, 10)
  setPanel(10, 10)

  def setMenu(): Unit = {
    val bar: JMenuBar = new JMenuBar()
    val game: JMenu = new JMenu("Game")
    val menuitem: JMenuItem = new JMenuItem("New Game")
    val beginner: JCheckBoxMenuItem = new JCheckBoxMenuItem("Beginner")
    val intermediate: JCheckBoxMenuItem = new JCheckBoxMenuItem("Intermediate")
    val expert: JCheckBoxMenuItem = new JCheckBoxMenuItem("Expert")
    val custom: JCheckBoxMenuItem = new JCheckBoxMenuItem("Custom...")
    val exit: JMenuItem = new JMenuItem("Exit")
    val status: ButtonGroup = new ButtonGroup()
    menuitem.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        controller.createGrid(10, 10, 10)
        numberOfMine = 10
        setPanel(10, 10)
      }
    })
    beginner.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(10, 10, 10)
        numberOfMine = 10
        setPanel(10, 10)
        lastgame = 1
        savedHeight = 10
        savedWidth = 10
        savedNumMines = 10
        panelBlock.revalidate()
        panelBlock.repaint()
        beginner.setSelected(true)
      }
    })
    intermediate.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(16, 16, 70)
        numberOfMine = 70
        setPanel(16, 16)
        lastgame = 2
        savedHeight = 16
        savedWidth = 16
        savedNumMines = 70
        panelBlock.revalidate()
        panelBlock.repaint()
        intermediate.setSelected(true)
      }
    })
    expert.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(20, 20, 150)
        numberOfMine = 150
        setPanel(20, 20)
        lastgame = 3
        savedHeight = 20
        savedWidth = 20
        savedNumMines = 150
        panelBlock.revalidate()
        panelBlock.repaint()
        expert.setSelected(true)
      }
    })
    custom.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        var c: Custom = new Custom()
        panelBlock.revalidate()
        panelBlock.repaint()
        custom.setSelected(true)
      }
    })
    exit.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        System.exit(0)
      }
    })
    setJMenuBar(bar)
    status.add(beginner)
    status.add(intermediate)
    status.add(expert)
    status.add(custom)
    game.add(menuitem)
    game.addSeparator()
    game.add(beginner)
    game.add(intermediate)
    game.add(expert)
    game.add(custom)
    game.addSeparator()
    game.add(exit)
    bar.add(game)
  }

  def setPanel(height: Int, width: Int): Unit = {
    fwidth = (20 * width)
    fheight = (30 * height)
    setSize(fwidth, fheight)
    setResizable(false)
    detectedMine = numberOfMine
    p = this.getLocation
    blocks = Array.ofDim[JButton](controller.height(), controller.width())
    mh = new MouseHandler()
    getContentPane.removeAll()
    panelBlock.removeAll()
    tf_mine = new JTextField("" + numberOfMine, 3)
    tf_mine.setEditable(false)
    tf_mine.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25))
    tf_mine.setBackground(Color.BLACK)
    tf_mine.setForeground(Color.RED)
    tf_mine.setBorder(BorderFactory.createLoweredBevelBorder())
    tf_time = new JTextField("000", 3)
    tf_time.setEditable(false)
    tf_time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25))
    tf_time.setBackground(Color.BLACK)
    tf_time.setForeground(Color.RED)
    tf_time.setBorder(BorderFactory.createLoweredBevelBorder())
    resetButton.setIcon(ic(11))
    resetButton.setBorder(BorderFactory.createLoweredBevelBorder())
    panelInfo.removeAll()
    panelInfo.setLayout(new BorderLayout())
    panelInfo.add(tf_mine, BorderLayout.WEST)
    panelInfo.add(resetButton, BorderLayout.CENTER)
    panelInfo.add(tf_time, BorderLayout.EAST)
    panelInfo.setBorder(BorderFactory.createLoweredBevelBorder())
    panelBlock.setBorder(
      BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createLoweredBevelBorder()))
    panelBlock.setPreferredSize(new Dimension(fwidth, fheight))
    panelBlock.setLayout(new GridLayout(0, controller.width))
    panelBlock.addContainerListener(this)
    for (i <- 0 until controller.height; j <- 0 until controller.width) {
      blocks(i)(j) = new JButton("")
      blocks(i)(j).addMouseListener(mh)
      panelBlock.add(blocks(i)(j))
    }
    reset()
    panelBlock.revalidate()
    panelBlock.repaint()
    getContentPane.setLayout(new BorderLayout())
    getContentPane.addContainerListener(this)
    getContentPane.repaint()
    getContentPane.add(panelBlock, BorderLayout.CENTER)
    getContentPane.add(panelInfo, BorderLayout.NORTH)
    setVisible(true)
  }

  resetButton.addActionListener(new ActionListener() {
    def actionPerformed(ae: ActionEvent): Unit = {
      try {
        sw.stop()
        controller.createGrid(savedHeight, savedWidth, savedNumMines)
        setPanel(savedHeight, savedWidth)
      } catch {
        case ex: Exception =>
          controller.createGrid(savedHeight, savedWidth, savedNumMines)
          setPanel(savedHeight, savedWidth)
      }
      reset()
    }
  })

  def reset(): Unit = {
    startTimeBool = false
    for (i <- 0 until controller.height; j <- 0 until controller.width) {
      controller.setColor(i, j, 'w')
    }
  }

  def componentAdded(ce: ContainerEvent): Unit = {}
  def componentRemoved(ce: ContainerEvent): Unit = {}
  def actionPerformed(ae: ActionEvent): Unit = {}

  class MouseHandler extends MouseAdapter {

    override def mouseClicked(me: MouseEvent): Unit = {
      for (i <- 0 until controller.height; j <- 0 until controller.width
           if me.getSource == blocks(i)(j)) {
        var1 = i
        var2 = j
      }
      if(me.getButton == MouseEvent.BUTTON1) {
        var win = controller.setChecked(var1, var2)
        if(win._2 != 0) {
          winner(win._2)
        }
      } else {
        controller.setFlag(var1, var2)
      }
      showValue(me)
      if (startTimeBool == false) {
        sw.start()
        startTimeBool = true
      }
    }

  }

  def winner(win: Int): Unit = {
    if(win == 1) {
      for (k <- 0 until controller.height; l <- 0 until controller.width) {
        blocks(k)(l).removeMouseListener(mh)
      }
      sw.stop()
      JOptionPane.showMessageDialog(this, "Hurray! You win!")
    } else {
      for (k <- 0 until controller.height; l <- 0 until controller.width) {
        if (controller.getMine(k, l)) {
          blocks(k)(l).setIcon(ic(9))
          blocks(k)(l).removeMouseListener(mh)
        }
      }
      sw.stop()
      resetButton.setIcon(ic(12))
      JOptionPane.showMessageDialog(this, "Game Over!")
    }
  }

  def showValue(e: MouseEvent): Unit = {
    for (i <- 0 until controller.height; j <- 0 until controller.width
         if e.getSource == blocks(i)(j)) {
      if (e.isMetaDown == false) {
        println("1")
        if (blocks(i)(j).getIcon == ic(10)) {
          if (detectedMine < numberOfMine) {
            { detectedMine += 1; detectedMine - 1 }
          }
          tf_mine.setText("" + detectedMine)
        }
        if (controller.getMine(i, j)) {
        } else if (!controller.getMine(i, j)) {
          controller.depthFirstSearch(i, j)
          paint()
        } else {
          blocks(i)(j).setIcon(ic(controller.getValue(i, j)))
          controller.setColor(i, j, 'b')
        }
      } else {
        if (detectedMine != 0) {
          if (blocks(i)(j).getIcon == null) {
            { detectedMine -= 1; detectedMine + 1 }
            blocks(i)(j).setIcon(ic(10))
          }
          tf_mine.setText("" + detectedMine)
        }
      }
    }
  }

  def setIc(): Unit = {
    var name: String = null
    var i: Int = 0
    while (i <= 8) {
      name = "Z:\\se-ws17-minesweeper\\src\\main\\resources\\" + i + ".png"
      ic(i) = new ImageIcon(name) { i += 1; i - 1 }
    }
    ic(9) = new ImageIcon("Z:\\se-ws17-minesweeper\\src\\main\\resources\\mine.png")
    ic(10) = new ImageIcon("Z:\\se-ws17-minesweeper\\src\\main\\resources\\flag.png")
    ic(11) = new ImageIcon("Z:\\se-ws17-minesweeper\\src\\main\\resources\\new game.png")
    ic(12) = new ImageIcon("Z:\\se-ws17-minesweeper\\src\\main\\resources\\lose.png")
  }

  def paint(): Unit = {
    for (i <- 0 until controller.height(); j <- 0 until controller.width()) {
      if (controller.getColorBack(i, j) == (Color.LIGHT_GRAY)) {
        blocks(i)(j).setBackground(Color.LIGHT_GRAY)
      }
      if (controller.getChecked(i, j)) {
        if (controller.getValue(i, j) == -1) {
          blocks(i)(j).setIcon(ic(10))
        } else {
          blocks(i)(j).setIcon(ic(controller.getValue(i, j)))
        }
      }
    }
  }

  class StopWatch extends JFrame with Runnable {

    var startTime: Long = _
    var updater: Thread = _
    var isRunning: Boolean = false
    var i: Long = 0
    var displayUpdater: Runnable = new Runnable() {
      def run(): Unit = {
        displayElapsedTime(i);
        { i += 1; i - 1 }
      }
    }

    def stop(): Unit = {
      val elapsed: Long = i
      isRunning = false
      try updater.join()
      catch {
        case ie: InterruptedException => {}
      }
      displayElapsedTime(elapsed)
      i = 0
    }

    private def displayElapsedTime(elapsedTime: Long): Unit = {
      if (elapsedTime >= 0 && elapsedTime < 9) {
        tf_time.setText("00" + elapsedTime)
      } else if (elapsedTime > 9 && elapsedTime < 99) {
        tf_time.setText("0" + elapsedTime)
      } else if (elapsedTime > 99 && elapsedTime < 999) {
        tf_time.setText("" + elapsedTime)
      }
    }

    def run(): Unit = {
      try while (isRunning) {
        SwingUtilities.invokeAndWait(displayUpdater)
        Thread.sleep(1000)
      } catch {
        case ite: java.lang.reflect.InvocationTargetException =>
          ite.printStackTrace(System.err)
        case ie: InterruptedException => {}
      }
    }

    def start(): Unit = {
      startTime = System.currentTimeMillis()
      isRunning = true
      updater = new Thread(this)
      updater.start()
    }

  }

  class Custom() extends JFrame("Custom Field") with ActionListener {

    var tf1: JTextField = new JTextField()
    var tf2: JTextField = new JTextField()
    var tf3: JTextField = new JTextField()
    var l1: JLabel = new JLabel("Height")
    var l2: JLabel = new JLabel("Width")
    var l3: JLabel = new JLabel("Mines")
    var b1: JButton = new JButton("OK")
    var b2: JButton = new JButton("Cancel")
    var i1: Int = _
    var i2: Int = _
    var i3: Int = _

    setSize(180, 200)
    setResizable(false)
    setLocation(900, 300)
    p = this.getLocation
    b1.addActionListener(this)
    b2.addActionListener(this)
    getContentPane.setLayout(new GridLayout(0, 2))
    getContentPane.add(l1)
    getContentPane.add(tf1)
    getContentPane.add(l2)
    getContentPane.add(tf2)
    getContentPane.add(l3)
    getContentPane.add(tf3)
    getContentPane.add(b1)
    getContentPane.add(b2)
    setVisible(true)

    def actionPerformed(e: ActionEvent): Unit = {
      if (e.getSource == b1) {
        try {
          i1 = java.lang.Integer.parseInt(tf1.getText)
          i2 = java.lang.Integer.parseInt(tf2.getText)
          i3 = java.lang.Integer.parseInt(tf3.getText)
          controller.createGrid(i1, i2, i3)
          savedHeight = i1
          savedWidth = i2
          savedNumMines = i3
          numberOfMine = i3
          lastgame = 4
          setPanel(i1, i2)
          dispose()
        } catch {
          case ex: Exception => {
            JOptionPane.showMessageDialog(this, "Integer!")
            tf1.setText("")
            tf2.setText("")
            tf3.setText("")
          }
        }
      }
      if (e.getSource == b2) {
        dispose()
      }
    }

  }

  class GuiPanel(controller: Controller) extends Frame {

    listenTo(controller)

    reactions += {
      case event: GridSizeChanged => resize(event.height, event.width, event.mineNumber)
      case event: CellChanged =>
    }

    def resize(height: Int, width: Int, mineNumber: Int): Unit = {
      controller.createGrid(height, width, mineNumber)
      setPanel(height, width)
    }

  }

}
