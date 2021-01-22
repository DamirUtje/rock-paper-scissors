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

}
