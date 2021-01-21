package de.damirutje.rockpaperscissors.model;

public enum HandShape {

    Rock {
        @Override
        public boolean isBetterThan(HandShape handShape) {
            return (handShape == Scissors);
        }
    },
    Paper {
        @Override
        public boolean isBetterThan(HandShape handShape) {
            return (handShape == Rock || handShape == Well);
        }
    },
    Scissors {
        @Override
        public boolean isBetterThan(HandShape handShape) {
            return (handShape == Paper);
        }
    },
    Well {
        @Override
        public boolean isBetterThan(HandShape handShape) {
            return (handShape == Rock || handShape == Scissors);
        }
    };

    public abstract boolean isBetterThan(HandShape move);

    public static HandShape fromInt(int value) {
        switch (value) {
            case 0:
                return Rock;
            case 1:
                return Paper;
            case 2:
                return Scissors;
            case 3:
                return Well;
            default:
                return null;
        }
    }
}
