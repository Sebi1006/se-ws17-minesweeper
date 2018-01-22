package de.htwg.se.minesweeper.aview.gui

import java.awt._
import java.awt.Dimension
import javax.swing._
import java.awt.event._
import java.util._

import de.htwg.se.minesweeper.controller.Controller

class Gui(controller: Controller) extends JFrame("HTWG Minesweeper") with ActionListener with ContainerListener {

  var fwidth: Int = _
  var fheight: Int = _
  var numberOfMine: Int = 10
  var detectedMine: Int = 0
  var savedBlockRowNum: Int = 10
  var savedBlockColumnNum: Int = 10
  var savedNumberOfMine: Int = 10
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
  var row: Array[Int] = Array(-1, -1, -1, 0, 1, 1, 1, 0)
  var col: Array[Int] = Array(-1, 0, 1, 1, 1, 0, -1, -1)

  setMenu()
  setLocation(400, 300)
  setIc()
  controller.createGrid(10,10,10)
  setPanel()

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
        controller.createGrid(10,10,10)
        setPanel()
        fwidth = 200
        fheight = 300
      }
    })
    beginner.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(10,10,10)
        setPanel()
        fwidth = 200
        fheight = 300
        lastgame = 1
        panelBlock.revalidate()
        panelBlock.repaint()
        beginner.setSelected(true)
      }
    })
    intermediate.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(16,16,70)
        setPanel()
        fwidth = 320
        fheight = 416
        lastgame = 2
        panelBlock.revalidate()
        panelBlock.repaint()
        intermediate.setSelected(true)
      }
    })
    expert.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(20,20,150)
        setPanel()
        fwidth = 400
        fheight = 520
        lastgame = 3
        panelBlock.revalidate()
        panelBlock.repaint()
        expert.setSelected(true)
      }
    })
    custom.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        controller.createGrid(15,15,60)
        setPanel()
        fwidth = (20 * 15)
        fheight = (24 * 15)
        lastgame = 1
        panelBlock.revalidate()
        panelBlock.repaint()
        expert.setSelected(true)
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

  def setPanel(): Unit = {
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
        controller.createGrid(10,10,10)
        setPanel()
      } catch {
        case ex: Exception =>
          controller.createGrid(10,10,10)
          setPanel()
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
      controller.setChecked(var1, var2)
      showValue(me)
      winner()
      if (startTimeBool == false) {
        sw.start()
        startTimeBool = true
      }
    }

  }

  def winner(): Unit = {
    var q: Int = 0
    for (k <- 0 until controller.height; l <- 0 until controller.width
         if controller.getColor(k, l) == 'w') {
      q = 1
    }
    if (q == 0) {
      for (k <- 0 until controller.height; l <- 0 until controller.width) {
        blocks(k)(l).removeMouseListener(mh)
      }
      sw.stop()
      JOptionPane.showMessageDialog(this, "Hurray! You win!")
    }
  }

  def showValue(e: MouseEvent): Unit = {
    for (i <- 0 until controller.height; j <- 0 until controller.width
         if e.getSource == blocks(i)(j)) {
      if (e.isMetaDown == false) {
        if (blocks(i)(j).getIcon == ic(10)) {
          if (detectedMine < numberOfMine) {
            { detectedMine += 1; detectedMine - 1 }
          }
          tf_mine.setText("" + detectedMine)
        }
        if (controller.getMine(i, j)) {
          for (k <- 0 until controller.height; l <- 0 until controller.width) {
            if (controller.getMine(k, l)) {
              blocks(k)(l).setIcon(ic(9))
              blocks(k)(l).removeMouseListener(mh)
            }
            blocks(k)(l).removeMouseListener(mh)
          }
          sw.stop()
          resetButton.setIcon(ic(12))
          JOptionPane.showMessageDialog(null, "Game Over!")
        } else if (!controller.getMine(i, j)) {
          depthFirstSearch(i, j)
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

  def depthFirstSearch(rowD: Int, colD: Int): Unit = {
    var R: Int = 0
    var C: Int = 0
    controller.setColor(rowD, colD, 'b')
    blocks(rowD)(colD).setBackground(Color.LIGHT_GRAY)
    blocks(rowD)(colD).setIcon(ic(controller.getValue(rowD, colD)))
    for (i <- 0.until(8)) {
      R = rowD + row(i)
      C = colD + col(i)
      if (R >= 0 && R < controller.height && C >= 0 && C < controller.width &&
        controller.getColor(R, C) == 'w') {
        if (controller.getValue(R, C) == 0) {
          depthFirstSearch(R, C)
        } else {
          blocks(R)(C).setIcon(ic(controller.getValue(R, C)))
          controller.setColor(R, C, 'b')
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

}
