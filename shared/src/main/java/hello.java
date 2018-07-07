import CustomExceptions.InvalidGameException;

public class hello {
    public static void main(String[] args) {
        System.out.println("Hello from shared");
        try {
            Game newGame = new Game("hi", new User("hi","HI"), 3);

            newGame.setGameName(null);

        }catch (InvalidGameException e){

        }

        String[] empty;

        String[] emptyNot = new String[3];
        emptyNot[0] = "hi";

        empty = emptyNot;



    }
}
