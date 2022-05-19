package ood.designpatterns.adapter;

public class PlayerAdapter implements VideoPlayer {

    private final ExistPermanentPlayer player;

    public PlayerAdapter() {
        this.player = new ExistPermanentPlayer();
    }

    @Override
    public void play(final String type, final String fileName) {
        switch (type) {
            case "avi":
                player.playAvi(fileName);
                break;
            case "wmv":
                player.playWmv(fileName);
                break;
            case "mp4":
                player.playMp4(fileName);
                break;
            default:
                throw new IllegalArgumentException("Not supported video type!");
        }
    }
}
