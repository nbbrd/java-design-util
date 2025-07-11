package internal.nbbrd.design;

import nbbrd.design.Trait;

public class TraitValid {

    @Trait
    public interface Movable {

        void move(int x, int y);
    }

    @Trait
    public interface HasFeature {

        void doSomething();
    }
}