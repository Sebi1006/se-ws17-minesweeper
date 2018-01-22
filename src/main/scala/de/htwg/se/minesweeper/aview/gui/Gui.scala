package de.htwg.se.minesweeper.aview.gui

import java.awt._
import java.awt.Dimension
import javax.swing._
import java.awt.event._
import java.util._

class Gui() extends JFrame("HTWG Minesweeper") with ActionListener with ContainerListener {

  var fwidth: Int = _
  var fheight: Int = _
  var blockRowNum: Int = _
  var blockColumnNum: Int = _
  var var1: Int = _
  var var2: Int = _
  var numberOfMine: Int = _
  var detectedMine: Int = 0
  var savedLevel: Int = 1
  var savedBlockRowNum: Int = _
  var savedBlockColumnNum: Int = _
  var savedNumberOfMine: Int = 10
  var row: Array[Int] = Array(-1, -1, -1, 0, 1, 1, 1, 0)
  var column: Array[Int] = Array(-1, 0, 1, 1, 1, 0, -1, -1)
  var countMine: Array[Array[Int]] = _
  var color: Array[Array[Int]] = _
  var ic: Array[ImageIcon] = new Array[ImageIcon](14)
  var blocks: Array[Array[JButton]] = _
  var panelBlock: JPanel = new JPanel()
  var panelInfo: JPanel = new JPanel()
  var tf_mine: JTextField = _
  var tf_time: JTextField = _
  var resetButton: JButton = new JButton("")
  var randomRow: Random = new Random()
  var randomColumn: Random = new Random()
  var check: Boolean = true
  var startTimeBool: Boolean = false
  var sw: StopWatch = new StopWatch()
  var mh: MouseHandler = _
  var p: Point = _

  setMenu()
  setLocation(400, 300)
  setIc()
  setPanel(1, 0, 0, 0)

  resetButton.addActionListener(new ActionListener() {
    def actionPerformed(ae: ActionEvent): Unit = {
      try {
        sw.stop()
        setPanel(savedLevel,
          savedBlockRowNum,
          savedBlockColumnNum,
          savedNumberOfMine)
      } catch {
        case ex: Exception =>
          setPanel(savedLevel,
            savedBlockRowNum,
            savedBlockColumnNum,
            savedNumberOfMine)
      }
      reset()
    }
  })

  def reset(): Unit = {
    check = true
    startTimeBool = false
    for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum) {
      color(i)(j) = 'w'
    }
  }

  def setPanel(level: Int, setr: Int, setc: Int, setm: Int): Unit = {
    if (level == 1) {
      fwidth = 200
      fheight = 300
      blockRowNum = 10
      blockColumnNum = 10
      numberOfMine = 10
    } else if (level == 2) {
      fwidth = 320
      fheight = 416
      blockRowNum = 16
      blockColumnNum = 16
      numberOfMine = 70
    } else if (level == 3) {
      fwidth = 400
      fheight = 520
      blockRowNum = 20
      blockColumnNum = 20
      numberOfMine = 150
    } else if (level == 4) {
      fwidth = (20 * setc)
      fheight = (24 * setr)
      blockRowNum = setr
      blockColumnNum = setc
      numberOfMine = setm
    }
    savedBlockRowNum = blockRowNum
    savedBlockColumnNum = blockColumnNum
    savedNumberOfMine = numberOfMine
    setSize(fwidth, fheight)
    setResizable(false)
    detectedMine = numberOfMine
    p = this.getLocation
    blocks = Array.ofDim[JButton](blockRowNum, blockColumnNum)
    countMine = Array.ofDim[Int](blockRowNum, blockColumnNum)
    color = Array.ofDim[Int](blockRowNum, blockColumnNum)
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
    panelBlock.setLayout(new GridLayout(0, blockColumnNum))
    panelBlock.addContainerListener(this)
    for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum) {
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

  def setMenu(): Unit = {
    val bar: JMenuBar = new JMenuBar()
    val game: JMenu = new JMenu("Game")
    val menuitem: JMenuItem = new JMenuItem("New Game")
    val beginner: JCheckBoxMenuItem = new JCheckBoxMenuItem("Beginner")
    val intermediate: JCheckBoxMenuItem = new JCheckBoxMenuItem("Intermediate")
    val expert: JCheckBoxMenuItem = new JCheckBoxMenuItem("Expert")
    val exit: JMenuItem = new JMenuItem("Exit")
    val status: ButtonGroup = new ButtonGroup()
    menuitem.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        setPanel(1, 0, 0, 0)
      }
    })
    beginner.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        setPanel(1, 0, 0, 0)
        panelBlock.revalidate()
        panelBlock.repaint()
        beginner.setSelected(true)
        savedLevel = 1
      }
    })
    intermediate.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        setPanel(2, 0, 0, 0)
        panelBlock.revalidate()
        panelBlock.repaint()
        intermediate.setSelected(true)
        savedLevel = 2
      }
    })
    expert.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = {
        panelBlock.removeAll()
        reset()
        setPanel(3, 0, 0, 0)
        panelBlock.revalidate()
        panelBlock.repaint()
        expert.setSelected(true)
        savedLevel = 3
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
    game.add(menuitem)
    game.addSeparator()
    game.add(beginner)
    game.add(intermediate)
    game.add(expert)
    game.addSeparator()
    game.add(exit)
    bar.add(game)
  }

  def componentAdded(ce: ContainerEvent): Unit = {}
  def componentRemoved(ce: ContainerEvent): Unit = {}
  def actionPerformed(ae: ActionEvent): Unit = {}

  class MouseHandler extends MouseAdapter {

    override def mouseClicked(me: MouseEvent): Unit = {
      if (check == true) {
        for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum
             if me.getSource == blocks(i)(j)) {
          var1 = i
          var2 = j
        }
        setMine()
        calculation()
        check = false
      }
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
    for (k <- 0 until blockRowNum; l <- 0 until blockColumnNum
         if color(k)(l) == 'w') {
      q = 1
    }
    if (q == 0) {
      for (k <- 0 until blockRowNum; l <- 0 until blockColumnNum) {
        blocks(k)(l).removeMouseListener(mh)
      }
      sw.stop()
      JOptionPane.showMessageDialog(this, "You win!")
    }
  }

  def showValue(e: MouseEvent): Unit = {
    for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum
         if e.getSource == blocks(i)(j)) {
      if (e.isMetaDown == false) {
        if (blocks(i)(j).getIcon == ic(10)) {
          if (detectedMine < numberOfMine) {
            { detectedMine += 1; detectedMine - 1 }
          }
          tf_mine.setText("" + detectedMine)
        }
        if (countMine(i)(j) == -1) {
          for (k <- 0 until blockRowNum; l <- 0 until blockColumnNum) {
            if (countMine(k)(l) == -1) {
              blocks(k)(l).setIcon(ic(9))
              blocks(k)(l).removeMouseListener(mh)
            }
            blocks(k)(l).removeMouseListener(mh)
          }
          sw.stop()
          resetButton.setIcon(ic(12))
          JOptionPane.showMessageDialog(null, "You lose!")
        } else if (countMine(i)(j) == 0) {
          depthFirstSearch(i, j)
        } else {
          blocks(i)(j).setIcon(ic(countMine(i)(j)))
          color(i)(j) = 'b'
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

  def calculation(): Unit = {
    var rowC: Int = 0
    var columnC: Int = 0
    for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum) {
      var value: Int = 0
      var R: Int = 0
      var C: Int = 0
      rowC = i
      columnC = j
      if (countMine(rowC)(columnC) != -1) {
        for (k <- 0.until(8)) {
          R = rowC + row(k)
          C = columnC + column(k)
          if (R >= 0 && C >= 0 && R < blockRowNum && C < blockColumnNum) {
            if (countMine(R)(C) == -1) {
              { value += 1; value - 1 }
            }
          }
        }
        countMine(rowC)(columnC) = value
      }
    }
  }

  def depthFirstSearch(rowD: Int, colD: Int): Unit = {
    var R: Int = 0
    var C: Int = 0
    color(rowD)(colD) = 'b'
    blocks(rowD)(colD).setBackground(Color.LIGHT_GRAY)
    blocks(rowD)(colD).setIcon(ic(countMine(rowD)(colD)))
    for (i <- 0.until(8)) {
      R = rowD + row(i)
      C = colD + column(i)
      if (R >= 0 && R < blockRowNum && C >= 0 && C < blockColumnNum &&
        color(R)(C) == 'w') {
        if (countMine(R)(C) == 0) {
          depthFirstSearch(R, C)
        } else {
          blocks(R)(C).setIcon(ic(countMine(R)(C)))
          color(R)(C) = 'b'
        }
      }
    }
  }

  def setMine(): Unit = {
    var row: Int = 0
    var col: Int = 0
    val flag: Array[Array[Boolean]] =
      Array.ofDim[Boolean](blockRowNum, blockColumnNum)
    for (i <- 0 until blockRowNum; j <- 0 until blockColumnNum) {
      flag(i)(j) = true
      countMine(i)(j) = 0
    }
    flag(var1)(var2) = false
    color(var1)(var2) = 'b'
    var i: Int = 0
    while (i < numberOfMine) {
      row = randomRow.nextInt(blockRowNum)
      col = randomColumn.nextInt(blockColumnNum)
      if (flag(row)(col) == true) {
        countMine(row)(col) = -1
        color(row)(col) = 'b'
        flag(row)(col) = false
      } else {
        { i -= 1; i + 1 }
      }
      i += 1
    }
  }

  def setIc(): Unit = {
    var name: String = null
    var i: Int = 0
    while (i <= 8) {
      name = "C:\\Users\\Sebi\\IdeaProjects\\se-ws17-minesweeper\\src\\main\\resources\\" + i + ".png"
      ic(i) = new ImageIcon(name) { i += 1; i - 1 }
    }
    ic(9) = new ImageIcon("C:\\Users\\Sebi\\IdeaProjects\\se-ws17-minesweeper\\src\\main\\resources\\mine.png")
    ic(10) = new ImageIcon("C:\\Users\\Sebi\\IdeaProjects\\se-ws17-minesweeper\\src\\main\\resources\\flag.png")
    ic(11) = new ImageIcon("C:\\Users\\Sebi\\IdeaProjects\\se-ws17-minesweeper\\src\\main\\resources\\new game.png")
    ic(12) = new ImageIcon("C:\\Users\\Sebi\\IdeaProjects\\se-ws17-minesweeper\\src\\main\\resources\\lose.png")
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
