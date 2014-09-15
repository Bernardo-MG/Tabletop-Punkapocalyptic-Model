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
import java.util.LinkedHashSet;

import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.Armor;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.Equipment;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.Weapon;
import com.wandrell.tabletop.business.model.punkapocalyptic.ruleset.SpecialRule;
import com.wandrell.tabletop.business.model.valuehandler.AbstractValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.DefaultDerivedValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.EditableValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.ValueHandler;
import com.wandrell.tabletop.business.model.valuehandler.event.ValueHandlerEvent;
import com.wandrell.tabletop.business.model.valuehandler.module.store.punkapocalyptic.UnitValorationStore;

public final class DefaultUnit implements Unit {

    private static final String           VALORATION_NAME = "valoration";
    private final ValueHandler            actions;
    private final ValueHandler            agility;
    private Armor                         armor;
    private final ValueHandler            combat;
    private final Integer                 cost;
    private final Collection<Equipment>   equipment       = new LinkedHashSet<>();
    private final Integer                 maxWeaponSlots  = 2;
    private final String                  name;
    private final ValueHandler            precision;
    private final Collection<SpecialRule> rules           = new LinkedHashSet<>();
    private final ValueHandler            strength;
    private final ValueHandler            tech;
    private final ValueHandler            toughness;
    private final ValueHandler            valoration;
    private final Collection<Weapon>      weapons         = new LinkedHashSet<>();
    private Integer                       weaponSlots;

    public DefaultUnit(final DefaultUnit unit) {
        super();

        if (unit == null) {
            throw new NullPointerException("Received a null pointer as unit");
        }

        name = unit.name;

        actions = unit.actions.createNewInstance();
        agility = unit.agility.createNewInstance();
        combat = unit.combat.createNewInstance();
        precision = unit.precision.createNewInstance();
        strength = unit.strength.createNewInstance();
        tech = unit.tech.createNewInstance();
        toughness = unit.toughness.createNewInstance();

        weaponSlots = unit.weaponSlots;

        cost = unit.cost;

        armor = unit.armor;

        for (final Equipment e : unit.equipment) {
            equipment.add(e);
        }

        for (final Weapon w : unit.weapons) {
            weapons.add(w);
        }

        for (final SpecialRule r : unit.rules) {
            rules.add(r);
        }

        valoration = buildValoration();
    }

    public DefaultUnit(final String name, final EditableValueHandler actions,
            final EditableValueHandler agility,
            final EditableValueHandler combat,
            final EditableValueHandler precision,
            final EditableValueHandler strength,
            final EditableValueHandler tech,
            final EditableValueHandler toughness, final Integer cost,
            final Collection<SpecialRule> rules) {
        super();

        if (name == null) {
            throw new NullPointerException("Received a null pointer as name");
        }

        if (actions == null) {
            throw new NullPointerException("Received a null pointer as actions");
        }

        if (agility == null) {
            throw new NullPointerException("Received a null pointer as agility");
        }

        if (combat == null) {
            throw new NullPointerException("Received a null pointer as agility");
        }

        if (precision == null) {
            throw new NullPointerException(
                    "Received a null pointer as precision");
        }

        if (strength == null) {
            throw new NullPointerException(
                    "Received a null pointer as strength");
        }

        if (tech == null) {
            throw new NullPointerException("Received a null pointer as tech");
        }

        if (toughness == null) {
            throw new NullPointerException(
                    "Received a null pointer as toughness");
        }

        if (cost == null) {
            throw new NullPointerException("Received a null pointer as cost");
        }

        this.name = name;

        this.actions = actions;
        this.agility = agility;
        this.combat = combat;
        this.precision = precision;
        this.strength = strength;
        this.tech = tech;
        this.toughness = toughness;

        weaponSlots = maxWeaponSlots;

        this.cost = cost;

        valoration = buildValoration();

        for (final SpecialRule rule : rules) {
            if (rule == null) {
                throw new NullPointerException(
                        "Received a null pointer as rule");
            }

            _getSpecialRules().add(rule);
        }
    }

    @Override
    public final void addEquipment(final Equipment equipment) {
        if (equipment == null) {
            throw new NullPointerException(
                    "Received a null pointer as equipment");
        }

        _getEquipment().add(equipment);

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final void addWeapon(final Weapon weapon) {
        if (weapon == null) {
            throw new NullPointerException("Received a null pointer as weapon");
        }

        if (getFreeWeaponSlots() >= weapon.getHands()) {
            _getWeapons().add(weapon);
            weaponSlots -= weapon.getHands();

            ((AbstractValueHandler) getValoration())
                    .fireValueChangedEvent(new ValueHandlerEvent(
                            getValoration()));
        }
    }

    @Override
    public final void clearEquipment() {
        _getEquipment().clear();

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final void clearWeapons() {
        _getWeapons().clear();

        weaponSlots = maxWeaponSlots;

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final DefaultUnit createNewInstance() {
        return new DefaultUnit(this);
    }

    @Override
    public final Integer getActions() {
        return actions.getStoredValue();
    }

    @Override
    public final Integer getAgility() {
        return agility.getStoredValue();
    }

    @Override
    public final Armor getArmor() {
        return armor;
    }

    @Override
    public final Integer getBaseCost() {
        return cost;
    }

    @Override
    public final Integer getCombat() {
        return combat.getStoredValue();
    }

    @Override
    public final Collection<Equipment> getEquipment() {
        return Collections.unmodifiableCollection(_getEquipment());
    }

    @Override
    public final Integer getFreeWeaponSlots() {
        return weaponSlots;
    }

    @Override
    public final Integer getMaxWeaponSlots() {
        return maxWeaponSlots;
    }

    @Override
    public final Integer getPrecision() {
        return precision.getStoredValue();
    }

    @Override
    public final Collection<SpecialRule> getSpecialRules() {
        return Collections.unmodifiableCollection(_getSpecialRules());
    }

    @Override
    public final Integer getStrength() {
        return strength.getStoredValue();
    }

    @Override
    public final Integer getTech() {
        return tech.getStoredValue();
    }

    @Override
    public final Integer getToughness() {
        return toughness.getStoredValue();
    }

    @Override
    public final String getUnitName() {
        return name;
    }

    @Override
    public final ValueHandler getValoration() {
        return valoration;
    }

    @Override
    public final Collection<Weapon> getWeapons() {
        return Collections.unmodifiableCollection(_getWeapons());
    }

    @Override
    public final void setArmor(final Armor armor) {
        this.armor = armor;

        ((AbstractValueHandler) getValoration())
                .fireValueChangedEvent(new ValueHandlerEvent(getValoration()));
    }

    @Override
    public final String toString() {
        return getUnitName();
    }

    private final ValueHandler buildValoration() {
        return new DefaultDerivedValueHandler(VALORATION_NAME,
                new UnitValorationStore(this));
    }

    protected final Collection<Equipment> _getEquipment() {
        return equipment;
    }

    protected final Collection<SpecialRule> _getSpecialRules() {
        return rules;
    }

    protected final Collection<Weapon> _getWeapons() {
        return weapons;
    }

}