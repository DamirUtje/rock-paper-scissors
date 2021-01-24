package de.damirutje.rockpaperscissors.model;

public enum HandSign {

    Rock {
        @Override
        public boolean isBetterThan(HandSign handSign) {
            return (handSign == Scissors);
        }
    },
    Paper {
        @Override
        public boolean isBetterThan(HandSign handSign) {
            return (handSign == Rock || handSign == Well);
        }
    },
    Scissors {
        @Override
        public boolean isBetterThan(HandSign handSign) {
            return (handSign == Paper);
        }
    },
    Well {
        @Override
        public boolean isBetterThan(HandSign handSign) {
            return (handSign == Rock || handSign == Scissors);
        }
    };

    public abstract boolean isBetterThan(HandSign move);
}
