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
                    int random_int = (int)Math.floor(Math.random() * (22 - 1 + 1) + 22);
                    ArrayList<String> possibleMoves = getPossibleMoves(createBoard(s), "2");
                    String move = null;
                    move = possibleMoves.get(5);
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
                    getPossibleMoves(createBoard(s), "4");
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

    private static String[][] createBoard(String s) {
        s = s.trim();
        String[][] board = new String[8][8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = String.valueOf(s.charAt(index));
                index+=2;
            }
        }
        return board;
    }

    private static String translate(int i, int j){
        StringBuilder s = new StringBuilder();
        if (j == 0) {
            s.append("A");
        } else if (j == 1) {
            s.append("B");
        } else if (j == 2) {
            s.append("C");
        } else if (j == 3) {
            s.append("D");
        } else if (j == 4) {
            s.append("E");
        } else if (j == 5) {
            s.append("F");
        } else if (j == 6) {
            s.append("G");
        } else if (j == 7) {
            s.append("H");
        }

        return String.valueOf(s.append(i+1));
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
                            possibleMoves.add(translate(i, j) + "-" + translate(i + 1, j));
                        }

                        if ((i != 0 & j != 7) && !(board[i + 1][j + 1].equals(player))) {
                            possibleMoves.add(translate(i, j) + "-" + translate(i + 1, j + 1));
                        }

                        if ((i != 0 & j != 0) && !(board[i + 1][j - 1].equals(player))) {
                            possibleMoves.add(translate(i, j) + "-" + translate(i + 1, j - 1));
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    public void play(String move, String board){
        String startingPosition = move.split("-")[0];
        String destinationPosition = move.split("-")[1];


    }
}
