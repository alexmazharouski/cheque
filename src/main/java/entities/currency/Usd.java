package entities.currency;

public final class Usd {
    private int cents;

    public Usd(int cents) {
        this.cents = cents;
    }

    public Usd(Usd usd) {
        this(usd.cents);
    }

    public Usd add(Usd usd) {
        this.cents += usd.cents;
        return this;
    }

    public Usd multiply(int value) {
        this.cents *= value;
        return this;
    }

    public Usd sub(Usd usd) {
        this.cents -= usd.cents;
        return this;
    }

    public Usd multiplyRound(double value) {
        double percentCost = 1 - value / 100;
        cents = (int)Math.round((cents * percentCost) / 100) * 100;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usd usd = (Usd) o;
        return cents == usd.cents;
    }

    @Override
    public String toString() {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}
