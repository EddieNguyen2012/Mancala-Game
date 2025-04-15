package edu.sjsu.cs151.monymancala;

public class MancalaTester {
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel(5);
        BoardStyle style = new DefaultStyle();
        model.setBoardStyle(style);
        MancalaView view = new MancalaView(model);
    }
}