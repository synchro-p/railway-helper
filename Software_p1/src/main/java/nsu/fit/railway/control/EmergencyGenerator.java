package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Emergency;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class EmergencyGenerator {

    private final Double emergencyChance;
    private final Random random;

    private final List<Class<? extends Emergency>> pickList;

    public EmergencyGenerator(Double chance, List<Class<? extends Emergency>> pickList) {
        emergencyChance = chance;
        random = new Random();
        this.pickList = pickList;
    }

    public Emergency roll() {
        double roll = random.nextDouble();
        if(roll < emergencyChance) {
            return generate();
        }
        else return null;
    }

    private Emergency generate() {
        int decider = abs(random.nextInt() % pickList.size());
        try {
            return pickList.get(decider).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
