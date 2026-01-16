import java.util.Scanner;

public class BingBong {
    // bot properties
    private static final String botName = "BingBong";
    private static final String byeCommand = "bye";

    // message templates
    private static final String startMessage = "Yo, my name is "
            + botName
            + ". Hit me up if you need any help.";
    private static final String endMessage = "Hasta la vista, baby!";

    public static void main(String[] args) {
        System.out.println(new Message(startMessage));

        // echo user input if it is not bye
        Scanner sc = new Scanner(System.in);
        String inputCommand = sc.nextLine();
        while (!inputCommand.equals(byeCommand)) {
            System.out.println(new Message(inputCommand));
            inputCommand = sc.nextLine();
        }

        System.out.println(new Message(endMessage));
    }
}