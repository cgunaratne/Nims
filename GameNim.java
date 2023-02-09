import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {
	
	char marks[] = {'O', 'X'}; //'O' for player1, 'X' for player2
	
    
    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;    
    
    public GameNim() {
    	currentState = new StateNim();
    }
    
    public boolean isWinState(State state)
    {
        StateNim tstate = (StateNim) state;

        if (tstate.remainingCoins == 1){
            return true;
        }
        return false;
    }
    

    //In this game we will never get stuck!! i.e. there will never be a draw.
    
    public boolean isStuckState(State state) { 
    	
        // if (isWinState(state)) 
        //     return false;
        
        // StateNim tstate = (StateNim) state;
        
        // for (int i=1; i<=9; i++) 
        //     if ( tstate.board[i] == ' ' ) 
        //         return false;
        
        return false;
    }
	
	
	public Set<State> getSuccessors(State state) /////// For some reason is resulting in an infinite loop.
    {
		if(isWinState(state))
			return null;
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;

        StateNim successor_state;
        
        char mark = 'O';
        if (tstate.player == 1) //human
            mark = 'X';
        
        

        for (int i = 1; i <= 3; i++) {

            successor_state = new StateNim(tstate);
            int position = tstate.pos;
            int rem = tstate.remainingCoins;

            if (i<rem){ // only if the positions to be added is less than the number of remaining coins. 
                for (int n = 0; n<i; n++){
                    successor_state.coins[position] = mark;
                    position++;
                }
    
                successor_state.remainingCoins = rem - i;
                successor_state.pos = position;
                successor_state.player = (state.player==0 ? 1 : 0);
                successors.add(successor_state);
            }

            

            
        }
    
        return successors;
    }	
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
    	
	    	if (previous_player==0) //computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}
        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Welcome to Nims!");
        System.out.println("\nThe rules are as follows: ");
        System.out.println("\n<<>>There is a pile of 13 coins on the table, on each turn, players take either 1, 2, or 3 coins from the pile and put them aside. ");
        System.out.println("<<>>The objective of the game is to avoid being forced to take the last coin.");
        System.out.println("<<>>You are 'X' and the computer is 'O'. You have the first turn");
        System.out.println("\nGOOD LUCK!\n");


        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 12;
        
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {

            
            
            StateNim cur = (StateNim)game.currentState;
        	
        	StateNim 	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
            	  //get human's move
                  System.out.println("Number of coins left: " + cur.remainingCoins);
                  boolean retreive = false;
                  int num = 0;
                  while(retreive == false){
                    System.out.print("Enter number of coins you want to retrieve (1, 2 or 3): ");
                    num = Integer.parseInt( in.readLine() );
                    if ((num < cur.remainingCoins) && (num == 1 || num == 2 || num == 3 )){
                        retreive = true;


                    }
                    else if (cur.remainingCoins == 3){ //Only 3 coins are left
                        System.out.println("Enter 2 or less");
                    }
                    else if (cur.remainingCoins == 2){ //Only 2 coins are left
                        System.out.println("Enter 1");
                    }
                    else if(cur.remainingCoins == 1){
                        break;
                    }
                    else{ //Game is over only one coin is left
                        System.out.println("Enter 1, 2 or 3");
                        
                    }

                  }
                  
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                  nextState.remainingCoins = cur.remainingCoins - num;
                  for (int cnt = 0; cnt<num;cnt++){
                      nextState.coins[cur.pos] = 'X';
                      cur.pos++;
                  }
                  nextState.pos = cur.pos;
                  System.out.println("Human: \n" + nextState);
                  break;
                  
              case 0: //Computer
                  

            	  nextState = (StateNim)search.bestSuccessorState(depth);             	  
                  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }
            

                        
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 1) //i.e. last move was by the computer
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");
            	
            	break;
            }

            


            
            
        }
    }
}