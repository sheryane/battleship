package model;

public enum ShipType {
    BATTLESHIP(4L),
    CRUISER(3L),
    DESTROYER(2L),
    SUBMARINE(1L);

    private final Long size;

    ShipType(Long size) {
        this.size = size;
    }

    public Long size() {
        return size;
    }

}

