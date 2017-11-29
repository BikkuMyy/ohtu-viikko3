package ohtu;

public class TennisGame {
    
    Player player1;
    Player player2;
    String[] scores = {"Love", "Fifteen", "Thirty", "Forty"};

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }
    
    public void wonPoint(String playerName){
        if(playerName.equals(player1.getName())){
            player1.increaseScore();
        } else if (playerName.equals(player2.getName())){
            player2.increaseScore();
        }
    }

    public String getScore() {
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();
        
        if (player1Score == player2Score){
            return scoreWhenTied(player1Score);
        }
        if ((player1Score >= 4 || player2Score >= 4) && Math.abs(player1Score - player2Score) >= 2){
                return declare("Win for ");
        }
        if (player1Score >= 3 && player2Score >= 3 && Math.abs(player1Score - player2Score) == 1){
            return declare("Advantage ");
        }
        return scores[player1Score]+"-"+scores[player2Score];
    }
    
        public String scoreWhenTied(int score){
        if (score == 0){
            return "Love-All";
        }
        if (score > 3){
            return "Deuce";
        }
        return scores[score]+"-All";
    }

    private String declare(String declaration) {
        if (player1.getScore() > player2.getScore()){
            return declaration + player1.getName();
        } else {
            return declaration + player2.getName();
        }
    }
}