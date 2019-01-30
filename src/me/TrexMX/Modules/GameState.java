package me.TrexMX.Modules;

public enum GameState {


    WAITING(),
    STARTING(),
    PLAYING(),
    ENDED();

    @Override
    public String toString() {
        switch (this) {
            case WAITING:
                return "Waiting";
            case STARTING:
                return "Starting";
            case PLAYING:
                return "Playing";
            case ENDED:
                return "Ended";
            default:
                return "";
        }
}
}
