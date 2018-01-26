[![Build Status](https://travis-ci.org/Sebi1006/se-ws17-minesweeper.svg?branch=master)](https://travis-ci.org/Sebi1006/se-ws17-minesweeper) - [![Coverage Status](https://coveralls.io/repos/github/Sebi1006/se-ws17-minesweeper/badge.svg?branch=master)](https://coveralls.io/github/Sebi1006/se-ws17-minesweeper?branch=master) - Master


# HTWG Minesweeper Project in Scala 
=====================================================

This is a implementation of Minsweeper in Scala for the SE the
class Software Engineering at the University of Applied Science HTWG Konstanz

* Has a folder structure prepared for a MVC-style application
* Has *ScalaTest* and *ScalaMock* at their latest versions as dependencies.
* Has *sbt-scalariform*, *sbt-scapegoat*, *scalastyle-sbt-plugin* and *sbt-scoverage* sbt plugins
* Has .gitignore defaults

Commands to play:
* h to get help
* 1 for beginner grid
* 2 for advanced grid
* 3 for expert grid
* 4 for custom grid with paramters in next line (height) (width) (numMines)
* (row)(col) to set a cell
* f (row)(col) to flag a cell
* save to save the grid
* load to load the last saved grid
* exit to quit the game
* new to start a new game with last parameters
