import java.io.*;
import java.net.*;
import java.util.ArrayList;


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
            CPUPlayer cpu = null;
            Board dummyBoard = null;
            while(1 == 1){
                char cmd = 0;
                cmd = (char)input.read();
                System.out.println(cmd);

                // Debut de la partie en joueur rouge
                if(cmd == '1'){
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    //System.out.println("size " + size);
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer).trim();
                    System.out.println(s);
                    String[] boardValues;
                    boardValues = s.split(" ");
                    int x = 0, y = 0;
                    for(int i = 0; i < boardValues.length; i++){
                        board[x][y] = Integer.parseInt(boardValues[i]);
                        y++;
                        if(y == 8){
                            y = 0;
                            x++;
                        }
                    }

                    cpu = new CPUPlayer(4);
                    dummyBoard = new Board(s);

                    System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
                    Move moveToPlay = cpu.getNextMove(dummyBoard);
                    dummyBoard.play(moveToPlay, cpu.getPlayerNumber());
                    output.write(moveToPlay.toString().getBytes(),0,moveToPlay.toString().length());
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
                    dummyBoard = new Board(s);
                }


                // Le serveur demande le prochain coup
                // Le message contient aussi le dernier coup joue.
                if(cmd == '3'){

                    long startTime = System.currentTimeMillis();

                    byte[] aBuffer = new byte[16];
                    if (cpu == null){
                        cpu = new CPUPlayer(2);
                    }

                    int size = input.available();
                    System.out.println("size :" + size);
                    input.read(aBuffer,0,size);

                    String s = new String(aBuffer);
                    System.out.println("Dernier coup :"+ s);
                    Move opponentMove = new Move(s);
                    dummyBoard.play(opponentMove, cpu.getOpponentNumber());

                    System.out.println("Entrez votre coup : ");


                    Move moveToPlay = cpu.getNextMove(dummyBoard);
                    dummyBoard.play(moveToPlay, cpu.getPlayerNumber());
                    output.write(moveToPlay.toString().getBytes(),0,moveToPlay.toString().length());
                    long endTime = System.currentTimeMillis();
                    output.flush();
                    System.out.println("Coup trouvé en : " + ((endTime - startTime)) + " ms");
                    System.out.println();
                }
                // Le dernier coup est invalide
                if(cmd == '4'){
                    System.out.println("Coup invalide, entrez un nouveau coup : ");
                    Move moveToPlay = cpu.getNextMove(dummyBoard);
                    output.write(moveToPlay.toString().getBytes(),0,moveToPlay.toString().length());
                    output.flush();

                }
                // La partie est terminée
                if(cmd == '5'){
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer);
                    System.out.println("Partie Terminé. Le dernier coup joué est: "+s);
                    //move = console.readLine();
                    //output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}
