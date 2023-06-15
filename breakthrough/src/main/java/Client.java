import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;


class Client {
    public static void main(String[] args) {

        Socket MyClient;
        BufferedInputStream input;
        BufferedOutputStream output;
        int[][] board = new int[8][8];



        try {
            MyClient = new Socket("localhost", 8888);

            input    = new BufferedInputStream(MyClient.getInputStream());
            output   = new BufferedOutputStream(MyClient.getOutputStream());
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while(1 == 1){
                char cmd = 0;
                cmd = (char)input.read();
                System.out.println(cmd);
                // Debut de la partie en joueur blanc
                if(cmd == '1'){
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    //System.out.println("size " + size);
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer).trim();
                    System.out.println(s);
                    String[] boardValues;
                    boardValues = s.split(" ");
                    int x=0,y=0;
                    for(int i=0; i<boardValues.length;i++){
                        board[x][y] = Integer.parseInt(boardValues[i]);
                        x++;
                        if(x == 8){
                            x = 0;
                            y++;
                        }
                    }

                    System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
                    //ArrayList<String> possibleMoves = getPossibleMoves(createBoard(s), String.valueOf(player));
                    String move = null;
                    //move = possibleMoves.get(5);
                    output.write(move.getBytes(),0,move.length());
                    output.flush();
                }
                // Debut de la partie en joueur Noir
                if(cmd == '2'){
                    System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    //System.out.println("size " + size);
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer).trim();
                    System.out.println(s);
                    //getPossibleMoves(createBoard(s), String.valueOf(player));
                    //String[] boardValues;
                    /*boardValues = s.split(" ");
                    int x=0,y=0;
                    for(int i=0; i<boardValues.length;i++){
                        board[x][y] = Integer.parseInt(boardValues[i]);
                        x++;
                        if(x == 8){
                            x = 0;
                            y++;
                        }
                    }*/
                }


                // Le serveur demande le prochain coup
                // Le message contient aussi le dernier coup joue.
                if(cmd == '3'){
                    byte[] aBuffer = new byte[16];

                    int size = input.available();
                    System.out.println("size :" + size);
                    input.read(aBuffer,0,size);

                    String s = new String(aBuffer);
                    System.out.println("Dernier coup :"+ s);
                    System.out.println("Entrez votre coup : ");
                    String move = null;
                    move = console.readLine();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
                // Le dernier coup est invalide
                if(cmd == '4'){
                    System.out.println("Coup invalide, entrez un nouveau coup : ");
                    String move = null;
                    move = console.readLine();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
                // La partie est terminée
                if(cmd == '5'){
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer);
                    System.out.println("Partie Terminé. Le dernier coup joué est: "+s);
                    String move = null;
                    move = console.readLine();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }


    private static ArrayList<String> getPossibleMoves(String[][] board, String player) {
        ArrayList<String> possibleMoves = new ArrayList<>();
        if (player.equals("4"))
            for (int i = 7; i >= 0; i--) {
                for (int j = 7; j >= 0; j--) {
                    if (board[i][j].equals(player)) {
                        if ((i != 0) && (board[i - 1][j].equals("0"))) {
                            possibleMoves.add(board[i][j] + "-" + board[i-1][j]);
                        }

                        if ((i != 0 & j != 7) && !(board[i - 1][j + 1].equals(player))) {
                            possibleMoves.add(board[i][j] + "-" + board[i-1][j+1]);
                        }

                        if ((i != 0 & j != 0) && !(board[i - 1][j - 1].equals(player))) {
                            possibleMoves.add(board[i][j] + "-" + board[i-1][j+1]);
                        }
                    }
                }
            }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].equals(player)) {
                        if ((i != 7) && (board[i + 1][j].equals("0"))) {
                            possibleMoves.add(board[i][j] + "-" + board[i+1][j]);
                        }

                        if ((i != 0 & j != 7) && !(board[i + 1][j + 1].equals(player))) {
                            possibleMoves.add(board[i][j] + "-" + board[i+1][j+1]);
                        }

                        if ((i != 0 & j != 0) && !(board[i + 1][j - 1].equals(player))) {
                            possibleMoves.add(board[i][j] + "-" + board[i+1][j-1]);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }


    /*public int evaluate(String [][] board, String player){
        String opponent;
        if (player.equals("2")){
            opponent = "4";
            for (int i = 0; i < 8; i++) {
                if (board[7][i].equals(player)){
                    return 100;
                }

                if (board[0][i].equals(opponent)){
                    return -100;
                }
            }
        }else {
            opponent = "2";
            for (int i = 0; i < 8; i++) {
                if (board[7][i].equals(opponent)){
                    return -100;
                }

                if (board[0][i].equals(player)){
                    return 100;
                }
            }
        }
        return 0;
    }

    public void play(String move, String[][] board){
        String startingPosition = move.split("-")[0];
        String destinationPosition = move.split("-")[1];

        board[destinationPosition.charAt(0)][destinationPosition.charAt(1)] = board[startingPosition.charAt(0)][startingPosition.charAt(1)];
        board[startingPosition.charAt(0)][startingPosition.charAt(1)] = "0";
    }

    public void undo(String move, String[][] board){
        String startingPosition = move.split("-")[1];
        String destinationPosition = move.split("-")[0];

        board[destinationPosition.charAt(1)][destinationPosition.charAt(0)] = board[startingPosition.charAt(1)][startingPosition.charAt(0)];
        board[startingPosition.charAt(0)][startingPosition.charAt(1)] = "0";
    }*/
}
