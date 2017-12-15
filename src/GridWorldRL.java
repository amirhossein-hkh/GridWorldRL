import java.util.Random;

public class GridWorldRL {

    public static double alfa = 0.1;
    public static double gama = 0.5;
    public static double eps = 0.5;

    public static double penalty = 1;

    public static GridWorld world;

    public static int col = 5;
    public static int row = 5;

    public static void main(String[] args){

        Grid[][] scores = new Grid[row][col];
        for (int i=0 ; i<row ; i++){
            for (int j=0 ; j<col ; j++){
                scores[i][j] = new Grid();
            }
        }
        scores[4][4].score = 10;
        scores[4][4].isTerminal = true;
        scores[2][2].score = -5;
        scores[2][2].isTerminal = true;
        scores[0][3].score = -5;
        scores[0][3].isTerminal = true;
        scores[1][1].score = -5;
        scores[1][1].isTerminal = true;
        world = new GridWorld(scores,0,0);
        world.gridworld[0][0].bestDir = Dir.EAST;
        world.gridworld[0][1].bestDir = Dir.EAST;
        world.gridworld[0][2].bestDir = Dir.EAST;
        world.gridworld[0][3].bestDir = Dir.EAST;
        world.gridworld[0][4].bestDir = Dir.SOUTH;

        for(int i=0 ; i< 200000 ; i++){
            action();
            world.printValue();
        }
        world.printScore();



    }

    public static void action() {
        if(world.rewards[world.currentRow][world.currentCol].isTerminal){ //action exit

            double Qsa  = world.gridworld[world.currentRow][world.currentCol].qvalues[0];
            double reward = world.rewards[world.currentRow][world.currentCol].score;
            double value = (1-alfa)* Qsa +  alfa * reward;
            for(int i=0 ; i<4 ; i++) {
                world.gridworld[world.currentRow][world.currentCol].qvalues[i] = value;
            }
            world.currentCol = 0;
            world.currentRow = 0;
        }else { // action move
            Random random = new Random(System.nanoTime());
            int mvCol = 0;
            int mvRow = 0;
            int move = -1;
            int startRow = world.currentRow;
            int startCol = world.currentCol;
            do {
                if (move == -1 && eps <= random.nextDouble()) {
                    move = world.gridworld[world.currentRow][world.currentCol].bestDir.fId;
                } else {
                    move = (int)(random.nextDouble()*4);
                }

                switch (move) {
                    case 0:
                        mvRow = -1;
                        mvCol = 0;
                        break;
                    case 1:
                        mvRow = 0;
                        mvCol = 1;
                        break;
                    case 2:
                        mvRow = 1;
                        mvCol = 0;
                        break;
                    case 3:
                        mvRow = 0;
                        mvCol = -1;
                        break;
                }

            } while (!world.moveTo(world.currentRow + mvRow, world.currentCol + mvCol));
            System.out.println("move : "+Dir.forInt(move));
            update(startRow,startCol,move);
        }


    }

    public static void update(int startRow,int startCol,int move){
        world.gridworld[startRow][startCol].qvalues[move] = calcQValue(startRow,startCol,move);
        if(world.gridworld[startRow][startCol].qvalues[move] > world.gridworld[startRow][startCol].qvalues[world.gridworld[startRow][startCol].bestDir.fId]){
            world.gridworld[startRow][startCol].bestDir = Dir.forInt(move);
        }
    }

    public static double calcQValue(int startRow,int startCol,int move){
        int endRow = startRow;
        int endCol = startCol;

        switch (move) {
            case 0:
                endRow += -1;
                endCol += 0;
                break;
            case 1:
                endRow += 0;
                endCol += 1;
                break;
            case 2:
                endRow += 1;
                endCol += 0;
                break;
            case 3:
                endRow += 0;
                endCol += -1;
                break;
        }
        System.out.println("in calc : " +startRow + " , " + startCol + " , "+move);
        double Qsa = world.gridworld[startRow][startCol].qvalues[move];
        double reward = penalty;
        double Qnsa = world.gridworld[endRow][endCol].qvalues[world.gridworld[endRow][endCol].bestDir.fId];
        Qsa = (1-alfa) * Qsa  + alfa * (reward + gama*Qnsa);
        return Qsa;
    }




}
