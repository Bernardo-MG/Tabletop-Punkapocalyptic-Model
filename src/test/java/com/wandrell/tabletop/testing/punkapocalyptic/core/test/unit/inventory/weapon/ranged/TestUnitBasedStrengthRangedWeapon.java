package com.wandrell.tabletop.testing.punkapocalyptic.core.test.unit.inventory.weapon.ranged;

import java.util.LinkedList;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wandrell.tabletop.punkapocalyptic.model.inventory.UnitBasedStrengthRangedWeapon;
import com.wandrell.tabletop.punkapocalyptic.model.ruleset.SpecialRule;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Unit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.stats.AttributesHolder;
import com.wandrell.tabletop.punkapocalyptic.model.util.RangedValue;

public final class TestUnitBasedStrengthRangedWeapon {

    private UnitBasedStrengthRangedWeapon weapon;

    public TestUnitBasedStrengthRangedWeapon() {
        super();
    }

    @BeforeTest
    public final void initializeWeapon() {
        final RangedValue penetration;
        final RangedValue strength;
        final RangedValue distanceCM;
        final RangedValue distanceInches;
        final Unit unit;
        final AttributesHolder attributes;

        penetration = Mockito.mock(RangedValue.class);
        strength = Mockito.mock(RangedValue.class);
        distanceCM = Mockito.mock(RangedValue.class);
        distanceInches = Mockito.mock(RangedValue.class);

        unit = Mockito.mock(Unit.class);

        attributes = Mockito.mock(AttributesHolder.class);

        Mockito.when(strength.getShortValue()).thenReturn(1);
        Mockito.when(strength.getMediumValue()).thenReturn(2);
        Mockito.when(strength.getLongValue()).thenReturn(3);

        Mockito.when(attributes.getStrength()).thenReturn(4);

        Mockito.when(unit.getAttributes()).thenReturn(attributes);

        weapon = new UnitBasedStrengthRangedWeapon(unit, "weapon", 0, false,
                new LinkedList<SpecialRule>(), penetration, strength,
                distanceCM, distanceInches, false);
    }

    @Test
    public final void testStrength_Long() {
        Assert.assertEquals(weapon.getLongStrength(), (Integer) 7);
    }

    @Test
    public final void testStrength_Medium() {
        Assert.assertEquals(weapon.getMediumStrength(), (Integer) 6);
    }

    @Test
    public final void testStrength_Short() {
        Assert.assertEquals(weapon.getShortStrength(), (Integer) 5);
    }

}
