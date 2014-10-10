package com.wandrell.tabletop.business.model.valuehandler.module.store.punkapocalyptic;

import com.wandrell.tabletop.business.model.punkapocalyptic.unit.Gang;
import com.wandrell.tabletop.business.model.valuehandler.event.ValueHandlerEvent;
import com.wandrell.tabletop.business.model.valuehandler.event.ValueHandlerListener;
import com.wandrell.tabletop.business.model.valuehandler.module.store.AbstractStoreModule;
import com.wandrell.tabletop.business.service.punkapocalyptic.RulesetService;

public final class MaxUnitsStore extends AbstractStoreModule {

    private final Gang           gang;
    private final RulesetService serviceRuleset;

    public MaxUnitsStore(final Gang gang, final RulesetService service) {
        super();

        if (gang == null) {
            throw new NullPointerException("Received a null pointer as gang");
        }

        if (service == null) {
            throw new NullPointerException(
                    "Received a null pointer as ruleset service");
        }

        this.gang = gang;
        serviceRuleset = service;

        gang.getValoration().addValueEventListener(new ValueHandlerListener() {

            @Override
            public final void valueChanged(final ValueHandlerEvent evt) {
                fireValueChangedEvent(evt);
            }

        });
    }

    public MaxUnitsStore(final MaxUnitsStore store) {
        super(store);

        gang = store.gang;
        serviceRuleset = store.serviceRuleset;
    }

    @Override
    public MaxUnitsStore createNewInstance() {
        return new MaxUnitsStore(this);
    }

    @Override
    public final Integer getValue() {
        return getRulesetService().getMaxAllowedUnits(getGang());
    }

    private final Gang getGang() {
        return gang;
    }

    private final RulesetService getRulesetService() {
        return serviceRuleset;
    }

}
