package wildnature.general.characters;

import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.STUN;
import static wildnature.general.characters.Animal.TWO_RULERS;
import wildnature.general.control.SwarmControl;
import wildnature.server.Environment;
import java.util.ArrayList;
import processing.core.PApplet;

public class TwoRulers extends Swimmer {

    Environment e;
    int clonePeriod = 1000;
    int time = 0;
    int cloneNum = 0;
    ArrayList<TwoRulers> rulers = new ArrayList<>();

    public TwoRulers(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        knockbackResistence = 0.5F;
        this.length = TWO_RULERS_LENGTH;
        this.weight = TWO_RULERS_WEIGHT;
        this.velocity = TWO_RULERS_SPEED;
        this.passiveAbilityPower = TWO_RULERS_PASSIVE_ABILITY;
        this.maxEnergy = TWO_RULERS_MAX_ENERGY;
        this.regen = TWO_RULERS_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = TWO_RULERS_ENERGY_INCREASE;
        this.maxTurn = TWO_RULERS_TURN;
        this.health = TWO_RULERS_MAX_HEALTH;
        this.maxHealth = TWO_RULERS_MAX_HEALTH;
        this.damage = TWO_RULERS_DAMAGE;
        this.abilityTime = TWO_RULERS_ABILITY_TIME;
        this.boostTime = TWO_RULERS_BOOST_TIME;
        this.armor = TWO_RULERS_ARMOR;
        this.armorPiercing = TWO_RULERS_ARMOR_PIERCING;
        this.ability1 = STUN;

        this.type = TWO_RULERS;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        for (int i = 0; i < rulers.size(); i++) {
            TwoRulers ruler = rulers.get(i);
            if (!ruler.isAlive()) {
                cloneNum--;
                rulers.remove(i);
                e.scores.remove(ruler.getName());
                e.charAbilities.remove(ruler);
                i--;
            }
        }
        if (time == 0 && cloneNum < 1) {
            TwoRulers ruler = new TwoRulers(this.getName() + " X", x, y, null, e);
            ruler.control = new SwarmControl(ruler, this, e);
            ruler.clone = true;
            ruler.team = team;
            e.characters.add(ruler);
            e.charAbilities.put(ruler, ruler.ability1);
            e.scores.put(ruler.getName(), 0);
            rulers.add(ruler);
            cloneNum++;
            ruler.cloneNum++;
        }
        if (!rulers.isEmpty()) {
            if (rulers.get(0).health > health) {
                health += 0.5;
                rulers.get(0).health -= 0.5;
            }
        }
        energyTime--;
        if (energyTime <= 0) {
            velocity = TWO_RULERS_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = TWO_RULERS_SPEED * 5;
        }
        this.move(velocity, control.moveAngle());
        if (control.mousePressed() && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        time++;
        time %= clonePeriod;
    }

    @Override
    public int getWidth() {
        return (int) TWO_RULERS_LENGTH;
    }

    @Override
    public int getHeight() {
        return 61;
    }

    @Override
    public boolean isAlive() {
        if (this.health <= 0.0F) {
            if (!clone && !rulers.isEmpty()) {
                replaceClone();
                return true;
            }
            return false;
        }
        return true;
    }

    private void replaceClone() {
        for (int i = 0; i < rulers.size(); i++) {
            TwoRulers ruler = rulers.get(i);
            health = ruler.health;
            x = ruler.x;
            y = ruler.y;
            energy = ruler.energy;
            angle = ruler.angle;

            ruler.health = Float.NEGATIVE_INFINITY;
            cloneNum--;
            rulers.remove(i);
            e.scores.remove(ruler.getName());
            e.charAbilities.remove(ruler);
            i--;
        }
    }

}
