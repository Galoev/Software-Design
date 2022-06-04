public class Roguelike {
    public static void main(String[] args) {
        Roguelike roguelike = new Roguelike();
        roguelike.run();
    }

    public void run() {
        new MainMenuView().display();
    }
}
