/**
 * Copyright 2014 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.wandrell.tabletop.business.model.punkapocalyptic.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.event.EventListenerList;

import com.wandrell.tabletop.business.model.punkapocalyptic.faction.Faction;
import com.wandrell.tabletop.business.model.punkapocalyptic.unit.event.BandListener;
import com.wandrell.tabletop.business.model.punkapocalyptic.unit.event.UnitEvent;
import com.wandrell.tabletop.business.model.valuehandler.AbstractValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.DefaultValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.DelegateValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.ValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.event.ValueHandlerEvent;
import com.wandrell.tabletop.business.model.valuehandler.event.ValueHandlerListener;
import com.wandrell.tabletop.business.model.valuehandler.module.generator.DefaultGenerator;
import com.wandrell.tabletop.business.model.valuehandler.module.interval.DefaultIntervalModule;
import com.wandrell.tabletop.business.model.valuehandler.module.store.DefaultStore;
import com.wandrell.tabletop.business.model.valuehandler.module.store.punkapocalyptic.BandValorationStore;
import com.wandrell.tabletop.business.model.valuehandler.module.validator.IntervalValidator;

public final class DefaultBand implements Band {

    private final ValueHandler      bullets;
    private final Faction           faction;
    private final EventListenerList listeners = new EventListenerList();
    private final Collection<Unit>  units     = new LinkedList<>();
    private final ValueHandler      valoration;

    public DefaultBand(final DefaultBand band) {
        super();

        if (band == null) {
            throw new NullPointerException("Received a null pointer as band");
        }

        faction = band.faction;
        bullets = band.bullets.createNewInstance();

        for (final Unit unit : band.units) {
            units.add(unit.createNewInstance());
        }

        valoration = band.valoration.createNewInstance();
        ((BandValorationStore) ((DelegateValueHandler) valoration).getStore())
                .setBand(this);

        ((AbstractValueHandler) bullets)
                .addValueEventListener(new ValueHandlerListener() {

                    @Override
                    public final void valueChanged(final ValueHandlerEvent evt) {
                        ((AbstractValueHandler) valoration)
                                .fireValueChangedEvent(new ValueHandlerEvent(
                                        valoration));
                    }

                });
    }

    public DefaultBand(final Faction faction, final Integer bulletCost) {
        super();

        if (faction == null) {
            throw new NullPointerException("Received a null pointer as faction");
        }

        this.faction = faction;

        bullets = new DefaultValueHandler("bullets", new DefaultGenerator(),
                new DefaultIntervalModule(0, Integer.MAX_VALUE),
                new DefaultStore(), new IntervalValidator());

        valoration = new DefaultValueHandler("band_valoration",
                new DefaultGenerator(), new DefaultIntervalModule(),
                new BandValorationStore(this, bulletCost),
                new IntervalValidator());

        ((AbstractValueHandler) bullets)
                .addValueEventListener(new ValueHandlerListener() {

                    @Override
                    public final void valueChanged(final ValueHandlerEvent evt) {
                        ((AbstractValueHandler) valoration)
                                .fireValueChangedEvent(new ValueHandlerEvent(
                                        valoration));
                    }

                });
    }

    @Override
    public final void addBandListener(final BandListener listener) {
        if (listener == null) {
            throw new NullPointerException(
                    "Received a null pointer as listener");
        }

        getListeners().add(BandListener.class, listener);
    }

    @Override
    public final void addUnit(final Unit unit) {
        _getUnits().add(unit);

        fireUnitAddedEvent(new UnitEvent(this, unit));

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final void clearUnits() {
        _getUnits().clear();

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final DefaultBand createNewInstance() {
        return new DefaultBand(this);
    }

    @Override
    public final ValueHandler getBullets() {
        return bullets;
    }

    @Override
    public final Faction getFaction() {
        return faction;
    }

    @Override
    public final Collection<Unit> getUnits() {
        return Collections.unmodifiableCollection(_getUnits());
    }

    @Override
    public final ValueHandler getValoration() {
        return valoration;
    }

    @Override
    public final void removeBandListener(final BandListener listener) {
        if (listener == null) {
            throw new NullPointerException(
                    "Received a null pointer as listener");
        }

        getListeners().remove(BandListener.class, listener);
    }

    @Override
    public final void removeUnit(final Unit unit) {
        final Iterator<Unit> itr;
        Boolean found;

        itr = _getUnits().iterator();
        found = false;
        while ((itr.hasNext()) && (!found)) {
            found = (itr.next() == unit);

            if (found) {
                itr.remove();
            }
        }

        fireUnitRemovedEvent(new UnitEvent(this, unit));

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    protected final Collection<Unit> _getUnits() {
        return units;
    }

    protected final void fireUnitAddedEvent(final UnitEvent evt) {
        final BandListener[] ls;

        if (evt == null) {
            throw new NullPointerException("Received a null pointer as event");
        }

        ls = getListeners().getListeners(BandListener.class);
        for (final BandListener l : ls) {
            l.unitAdded(evt);
        }
    }

    protected final void fireUnitRemovedEvent(final UnitEvent evt) {
        final BandListener[] ls;

        if (evt == null) {
            throw new NullPointerException("Received a null pointer as event");
        }

        ls = getListeners().getListeners(BandListener.class);
        for (final BandListener l : ls) {
            l.unitRemoved(evt);
        }
    }

    protected final EventListenerList getListeners() {
        return listeners;
    }

}
