public class GridWorld {

    GridValue[][] gridworld;

    Grid[][] rewards;
    int currentRow;
    int currentCol;
    int rows;
    int cols;
    public GridWorld(Grid[][] rewards,int startRow,int startCol){
        this.rewards = rewards;
        this.rows = rewards.length;
        this.cols = rewards[0].length;
        this.currentCol = startCol;
        this.currentRow = startRow;
        gridworld = new GridValue[this.rows][this.cols];
        for(int i=0 ; i<this.rows; i++){
            for(int j=0 ; j<this.cols ; j++){
                gridworld[i][j] = new GridValue();
            }
        }
    }

    public boolean moveTo(int row,int col){
        if(row <rows && row >=0 && col < cols && col>=0){
            if(!rewards[row][col].isBlocked){
                currentRow = row;
                currentCol = col;
                System.out.println("current : " +currentRow + " : " + currentCol);
            }

            return true;
        }
        return false;
    }

    public void printValue(){
        for(int i=0 ; i<rows ; i++){
            for (int j=0 ; j<cols ; j++){
                System.out.print(gridworld[i][j].bestDir+" ");
            }
            System.out.println();;
        }
        System.out.println();
    }

    public void printScore(){
        for(int i=0 ; i<rows ; i++){
            for (int j=0 ; j<cols ; j++){
                System.out.print(rewards[i][j].score+" ");
            }
            System.out.println();;
        }
        System.out.println();
    }

}

enum Dir{
    NORTH(0),EAST(1),SOUTH(2),WEST(3);
    public final int fId;

    private Dir(int id) {
        this.fId = id;
    }

    public static Dir forInt(int id) {
        for (Dir dir : values()) {
            if (dir.fId == id) {
                return dir;
            }
        }
        return NORTH;
    }
}

class GridValue

{
    double qvalues[];
    Dir bestDir;

    public GridValue(){
        qvalues = new double[4];
        bestDir = Dir.NORTH;
    }
}

class Grid{
    boolean isTerminal;
    boolean isBlocked;
    double score;

    public Grid(){
        isTerminal = false;
        this.isBlocked = false;
        this.score = 0.0;
    }

    public Grid(double score){
        this.isBlocked = false;
        this.score = score;
        this.isTerminal = false;
    }

    public Grid(boolean isBlocked,double score,boolean isTerminal){
        this.isBlocked = isBlocked;
        this.score = score;
        this.isTerminal =  isTerminal;
    }
}
