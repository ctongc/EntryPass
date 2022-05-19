package ood.designpatterns.adapter;

public class ExistPermanentPlayer {

    public void playAvi(final String fileName) {
        System.out.println("ExistPermanentPlayer::playAvi played " + fileName);
    }

    public void playWmv(final String fileName) {
        System.out.println("ExistPermanentPlayer::playWmv played " + fileName);
    }

    public void playMp4(final String fileName) {
        System.out.println("ExistPermanentPlayer::playMp4 played " + fileName);
    }
}
