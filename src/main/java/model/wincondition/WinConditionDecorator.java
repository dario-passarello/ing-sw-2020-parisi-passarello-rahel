package model.wincondition;

//TODO
public abstract class WinConditionDecorator implements WinCondition {
    protected WinCondition wrappedWinCondition;

    public WinCondition getWrappedWinCondition() {
        return wrappedWinCondition;
    }

}
