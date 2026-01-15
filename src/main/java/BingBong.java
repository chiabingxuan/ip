public class BingBong {
    private final static String botName = "BingBong";

    public static void main(String[] args) {
        String startMessage = "Yo, my name is "
                + botName
                + ". Hit me up if you need any help.";
        String endMessage = "Hasta la vista, baby!";

        System.out.println(new Message(startMessage));
        System.out.println(new GoodbyeMessage(endMessage));
    }
}