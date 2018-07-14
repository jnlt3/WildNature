package wildnature.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import wildnature.general.characters.Animal;
import wildnature.general.characters.Swimmer;
import wildnature.general.characters.swimmers.Alien;
import wildnature.general.characters.swimmers.BlackMarlin;
import wildnature.general.characters.swimmers.ColossalSquid;
import wildnature.general.characters.swimmers.Crocodile;
import wildnature.general.characters.swimmers.Dolphin;
import wildnature.general.characters.swimmers.DoodFish;
import wildnature.general.characters.swimmers.ElectricMarlin;
import wildnature.general.characters.swimmers.Ghost;
import wildnature.general.characters.swimmers.GreenlandShark;
import wildnature.general.characters.swimmers.Guardian;
import wildnature.general.characters.swimmers.Hippo;
import wildnature.general.characters.swimmers.MakoShark;
import wildnature.general.characters.swimmers.Marlin;
import wildnature.general.characters.swimmers.Marlinium;
import wildnature.general.characters.swimmers.MegaMouth;
import wildnature.general.characters.swimmers.MiniMarlin;
import wildnature.general.characters.swimmers.Orca;
import wildnature.general.characters.swimmers.Shark;
import wildnature.general.characters.swimmers.Sharkodile;
import wildnature.general.characters.swimmers.TigerShark;
import wildnature.general.characters.swimmers.TwoRulers;

public class Utilities {

    public static Swimmer getSwimmer(String type, String name, Environment env) throws NoSuchAnimalException {
        if (Animal.MARLIN.name().equals(type)) {
            return new Marlin(name, 0.0F, 0.0F, null, env);
        } else if (Animal.BLACK_MARLIN.name().equals(type)) {
            return new BlackMarlin(name, 0.0F, 0.0F, null, env);
        } else if (Animal.CROCODILE.name().equals(type)) {
            return new Crocodile(name, 0.0F, 0.0F, null, env);
        } else if (Animal.SHARK.name().equals(type)) {
            return new Shark(name, 0.0F, 0.0F, null, env);
        } else if (Animal.MEGA_MOUTH.name().equals(type)) {
            return new MegaMouth(name, 0.0F, 0.0F, null, env);
        } else if (Animal.ORCA.name().equals(type)) {
            return new Orca(name, 0.0F, 0.0F, null, env);
        } else if (Animal.HIPPO.name().equals(type)) {
            return new Hippo(name, 0.0F, 0.0F, null, env);
        } else if (Animal.COLOSSAL_SQUID.name().equals(type)) {
            return new ColossalSquid(name, 0.0F, 0.0F, null, env);
        } else if (Animal.DOOD_FISH.name().equals(type)) {
            return new DoodFish(name, 0.0F, 0.0F, null, env);
        } else if (Animal.ELECTRIC_MARLIN.name().equals(type)) {
            return new ElectricMarlin(name, 0.0F, 0.0F, null, env);
        } else if (Animal.GUARDIAN.name().equals(type)) {
            return new Guardian(name, 0.0F, 0.0F, null, env);
        } else if (Animal.TIGER_SHARK.name().equals(type)) {
            return new TigerShark(name, 0.0F, 0.0F, null, env);
        } else if (Animal.DOLPHIN.name().equals(type)) {
            return new Dolphin(name, 0.0F, 0.0F, null, env);
        } else if (Animal.MAKO_SHARK.name().equals(type)) {
            return new MakoShark(name, 0.0F, 0.0F, null, env);
        } else if (Animal.TWO_RULERS.name().equals(type)) {
            return new TwoRulers(name, 0.0F, 0.0F, null, env);
        } else if (Animal.GHOST.name().equals(type)) {
            return new Ghost(name, 0.0F, 0.0F, null, env);
        } else if (Animal.ALIEN.name().equals(type)) {
            return new Alien(name, 0.0F, 0.0F, null, env);
        } else if (Animal.GREENLAND_SHARK.name().equals(type)) {
            return new GreenlandShark(name, 0.0F, 0.0F, null, env);
        } else if (Animal.SHARKODILE.name().equals(type)) {
            return new Sharkodile(name, 0.0F, 0.0F, null, env);
        } else if (Animal.MARLINIUM.name().equals(type)) {
            return new Marlinium(name, 0.0F, 0.0F, null, env);
        } else if (Animal.MINI_MARLIN.name().equals(type)) {
            return new MiniMarlin(name, 0.0F, 0.0F, null, env);
        }
        throw new NoSuchAnimalException("No Animal Named " + type);
    }

    public static class NoSuchAnimalException extends Exception {

        public NoSuchAnimalException() {
        }

        public NoSuchAnimalException(String message) {
            super(message);
        }
    }
}
