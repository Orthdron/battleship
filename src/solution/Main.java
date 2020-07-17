package solution;

import battleship.BattleShip;

public class Main
{
   static final int NUMBEROFGAMES = 10000;
   public static void startingSolution()
  {
    int totalShots = 0;
    System.out.println(BattleShip.version());
    for (int game = 0; game < NUMBEROFGAMES; game++) {

      BattleShip battleShip = new BattleShip();
      CheckerBoard checkerBot = new CheckerBoard(battleShip);

      while (!battleShip.allSunk()) {
        checkerBot.fireShot();

      }
      int gameShots = battleShip.totalShotsTaken();
      totalShots += gameShots;
    }
    System.out.printf("CheckerBoardBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double)totalShots / NUMBEROFGAMES);

  }
  public static void main(String[] args)
  {
    startingSolution();
  }
}
