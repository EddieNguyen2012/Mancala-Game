package edu.sjsu.cs151.monymancala;

public class MancalaTest {
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaController controller = new MancalaController(model);
        MancalaView view = new MancalaView(model, controller);
        model.addChangeListener(view);
        controller.setView(view);
    }
}
