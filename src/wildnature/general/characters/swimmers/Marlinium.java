package wildnature.general.characters.swimmers;

import wildnature.general.characters.*;
import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.SUPERBITE;
import static wildnature.general.characters.Animal.MARLINIUM;
import wildnature.general.control.BackTrackControl;
import wildnature.general.control.BaseBackTrackControl;
import wildnature.server.Environment;
import java.util.ArrayList;
import processing.core.PApplet;

public class Marlinium extends Swimmer {

    Environment e;
    int birthPeriod = 200;
    int time = 0;
    static int miniMarlinNo = 0;
    int miniMarlinNum = 0;
    ArrayList<MiniMarlin> marlins = new ArrayList<>();

    public Marlinium(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        control = new BackTrackControl(this, e);
        this.x = x;
        this.y = y;    
        this.length = MARLINIUM_LENGTH;
        this.weight = MARLINIUM_WEIGHT;
        this.velocity = MARLINIUM_SPEED;
        this.passiveAbilityPower = MARLINIUM_PASSIVE_ABILITY;
        this.maxEnergy = MARLINIUM_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = MARLINIUM_HEALTH_REGEN;
        this.energyIncrease = MARLINIUM_ENERGY_INCREASE;
        this.maxTurn = MARLINIUM_TURN;
        this.health = MARLINIUM_MAX_HEALTH;
        this.maxHealth = MARLINIUM_MAX_HEALTH;
        this.damage = MARLINIUM_DAMAGE;
        this.abilityTime = MARLINIUM_ABILITY_TIME;
        this.boostTime = MARLINIUM_BOOST_TIME;
        this.armor = MARLINIUM_ARMOR;
        this.armorPiercing = MARLINIUM_ARMOR_PIERCING;       
        this.ability1 = SUPERBITE;
        
        this.type = MARLINIUM;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 3000, mouseX, mouseY, mousePressed);
        for (int i = 0; i < marlins.size(); i++) {
            MiniMarlin mm = marlins.get(i);
            if (!mm.isAlive()) {
                miniMarlinNum--;
                marlins.remove(i);
                e.scores.remove(mm.getName());
                e.charAbilities.remove(mm);
                i--;
            }
        }
        if (time == 0 && miniMarlinNum < 2) {
            MiniMarlin mm = new MiniMarlin(this.getName() + " " + miniMarlinNo, x, y, null, e);
            mm.control = new BaseBackTrackControl(mm, this, e);
            mm.clone = true;
            mm.team = team;
            e.characters.add(mm);
            e.charAbilities.put(mm, mm.ability1);
            e.scores.put(mm.getName(), 0);
            marlins.add(mm);
            miniMarlinNum++;
            miniMarlinNo++;
            miniMarlinNo %= 999;
        }
        energyTime--;
        if (energyTime <= 0) {
            velocity = MARLINIUM_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = MARLINIUM_SPEED * 5;
        }
        this.move(velocity, control.moveAngle());
        if (control.mousePressed() && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        time++;
        time %= birthPeriod;
    }

    @Override
    public int getWidth() {
        return (int) MARLINIUM_LENGTH;
    }

    @Override
    public int getHeight() {
        return 42;
    }

}
