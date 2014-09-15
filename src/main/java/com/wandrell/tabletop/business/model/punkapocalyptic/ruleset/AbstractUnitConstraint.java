package com.wandrell.tabletop.business.model.punkapocalyptic.ruleset;

public abstract class AbstractUnitConstraint implements
        ArmyBuilderUnitConstraint {

    private final String message;
    private final String unit;

    public AbstractUnitConstraint(final String unit, final String message) {
        super();

        this.unit = unit;
        this.message = message;
    }

    @Override
    public final String getErrorMessage() {
        return message;
    }

    protected final String getUnit() {
        return unit;
    }

}