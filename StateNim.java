
public class StateNim extends State {
	
    public char coins[] = new char [13+1];
    public int remainingCoins = 13;
    public int pos = 0; //Currently all 13 coins remain
    
    public StateNim() {
    	
        for (int i=0; i<14; i++) 
            coins[i] = ' ';
        
        player = 1;
    }
    
    public StateNim(StateNim state) {
    	
        for(int i=0; i<14; i++)
            this.coins[i] = state.coins[i]; 
        
        this.remainingCoins = state.remainingCoins;
        player = state.player;
    }
    
    public String toString() {
    	
    	String ret = "";
    	
        for(int j=0; j<12; j++) {
            ret += coins[j] + ", ";
        }
        ret += coins[12] + "\n";
    	
    	return ret;
    }

    // public void setPos(int val){
    //     this.pos = val;
    // }

    // public int getPos(){
    //     return this.pos;
    // }

    // public void setRemainingCoins(n){
    //     this.RemainingCoins = n;
    // }

    // public int getRemainingCoins(){
    //     return this.remainingCoins;
    // }


}
