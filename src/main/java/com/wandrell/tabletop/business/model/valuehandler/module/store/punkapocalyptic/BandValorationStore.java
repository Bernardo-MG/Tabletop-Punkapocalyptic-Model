package com.wandrell.tabletop.business.model.valuehandler.module.store.punkapocalyptic;

import com.wandrell.tabletop.business.model.punkapocalyptic.unit.Gang;
import com.wandrell.tabletop.business.model.punkapocalyptic.unit.Unit;
import com.wandrell.tabletop.business.model.valuehandler.module.StoreModule;

public class BandValorationStore extends StoreModule {

    private Gang          band;
    private final Integer bulletCost;

    public BandValorationStore(final BandValorationStore store) {
        super(store);

        if (store == null) {
            throw new NullPointerException("Received a null pointer as store");
        }

        band = store.band;
        bulletCost = store.bulletCost;
    }

    public BandValorationStore(final Gang band, final Integer bulletCost) {
        super();

        if (band == null) {
            throw new NullPointerException("Received a null pointer as band");
        }

        if (bulletCost == null) {
            throw new NullPointerException(
                    "Received a null pointer as bullet cost");
        }

        this.band = band;
        this.bulletCost = bulletCost;
    }

    @Override
    public final void addValue(final Integer value) {}

    @Override
    public final BandValorationStore createNewInstance() {
        return new BandValorationStore(this);
    }

    @Override
    public final Integer getValue() {
        Integer cost;

        cost = 0;
        for (final Unit unit : getBand().getUnits()) {
            cost += unit.getValoration().getStoredValue();
        }

        cost += (getBand().getBullets().getStoredValue() * getBulletCost());

        return cost;
    }

    public final void setBand(final Gang band) {
        this.band = band;
    }

    @Override
    public final void setValue(final Integer value) {}

    protected final Gang getBand() {
        return band;
    }

    protected final Integer getBulletCost() {
        return bulletCost;
    }

}
