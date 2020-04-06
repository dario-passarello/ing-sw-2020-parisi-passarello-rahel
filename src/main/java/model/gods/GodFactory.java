package model.gods;

public class GodFactory {
    public GodFactory() {

    }

    public God getGod(String name) {
        switch (name){
            case "Apollo":
                return new Apollo();
            case "Artemis":
                return new Artemis();
            case "Athena":
                return new Athena();
            case "Atlas":
                return new Atlas();
            case "Chronus":
                return new Chronus();
            case "Demeter":
                return new Demeter();
            case "Hephaestus":
                return new Hephaestus();
            case "Hera":
                return new Hera();
            case "Hestia":
                return new Hestia();
            case "Minotaur":
                return new Minotaur();
            case "Pan":
                return new Pan();
            case "Prometheus":
                return new Prometheus();
            case "Triton":
                return new Triton();
            case "Zeus":
                return new Zeus();
            default:
                return null;
        }
    }
}
