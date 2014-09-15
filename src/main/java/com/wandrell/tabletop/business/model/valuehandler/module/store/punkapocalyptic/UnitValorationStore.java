package com.wandrell.tabletop.business.model.valuehandler.module.store.punkapocalyptic;

import com.wandrell.tabletop.business.model.punkapocalyptic.unit.Unit;
import com.wandrell.tabletop.business.model.valuehandler.module.store.StoreModule;
import com.wandrell.tabletop.business.service.punkapocalyptic.RulesetService;

public class UnitValorationStore extends StoreModule {

    private final RulesetService serviceRuleset;
    private Unit                 unit;

    public UnitValorationStore(final RulesetService service) {
        super();

        if (service == null) {
            throw new NullPointerException(
                    "Received a null pointer as ruleset service");
        }

        serviceRuleset = service;
    }

    public UnitValorationStore(final Unit unit, final RulesetService service) {
        super();

        if (unit == null) {
            throw new NullPointerException("Received a null pointer as unit");
        }

        if (service == null) {
            throw new NullPointerException(
                    "Received a null pointer as ruleset service");
        }

        this.unit = unit;
        serviceRuleset = service;
    }

    public UnitValorationStore(final UnitValorationStore store) {
        super(store);

        if (store == null) {
            throw new NullPointerException("Received a null pointer as store");
        }

        unit = store.unit;
        serviceRuleset = store.serviceRuleset;
    }

    @Override
    public final UnitValorationStore createNewInstance() {
        return new UnitValorationStore(this);
    }

    @Override
    public final Integer getValue() {
        return getRulesetService().getUnitValoration(getUnit());
    }

    public final void setUnit(final Unit unit) {
        this.unit = unit;
    }

    protected final RulesetService getRulesetService() {
        return serviceRuleset;
    }

    protected final Unit getUnit() {
        return unit;
    }

}
