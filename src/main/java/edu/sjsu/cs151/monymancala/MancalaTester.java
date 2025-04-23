package edu.sjsu.cs151.monymancala;

public class MancalaTester {
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaView view = new MancalaView(model);
        model.addChangeListener(view);
    }
}
