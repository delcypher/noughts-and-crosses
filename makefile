#This is a simple MakeFile for building the Noughts and Crosses demo program

JAVAC=javac
JAVA=java
COMPONENTS = BoardInterface.class ConsoleUI.class Game.class GameFactory.class LocalPlayer.class Player.class Position.class RandomLocalPlayer.class SimpleBoard.class UserInterface.class
VPATH=uk/co/suroot/nandx

#Default target
GameTestHarness.class : GameTestHarness.java $(COMPONENTS)


#Implicit rule to make .class files
%.class : %.java
	$(JAVAC) $< 

.PHONY : clean
clean : 
	rm *.class
	rm uk/co/suroot/nandx/*.class

.PHONY : run
run : GameTestHarness.class
	java GameTestHarness
