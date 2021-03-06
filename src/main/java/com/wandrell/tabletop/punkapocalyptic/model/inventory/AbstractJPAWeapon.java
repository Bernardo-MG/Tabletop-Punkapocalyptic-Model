package com.wandrell.tabletop.punkapocalyptic.model.inventory;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.wandrell.persistence.PersistenceEntity;
import com.wandrell.tabletop.punkapocalyptic.model.ruleset.JPASpecialRule;

@Entity(name = "Weapon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "weapon_type")
@Table(name = "weapons")
public abstract class AbstractJPAWeapon implements Weapon, PersistenceEntity {

    private Integer                          cost;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer                          id    = -1;
    private String                           name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "weapon_rules", joinColumns = { @JoinColumn(
            name = "weapon_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "rule_id",
                    referencedColumnName = "id") })
    private final Collection<JPASpecialRule> rules = new LinkedHashSet<>();
    @Column(name = "two_handed")
    private Boolean                          twoHanded;

    public AbstractJPAWeapon() {
        super();
    }

    public AbstractJPAWeapon(final AbstractJPAWeapon weapon) {
        super();

        this.name = weapon.name;
        this.cost = weapon.cost;
        this.twoHanded = weapon.twoHanded;
        this.rules.addAll(weapon.rules);
    }

    public final void addSpecialRule(final JPASpecialRule rule) {
        getSpecialRulesEditable().add(rule);
    }

    @Override
    public final Integer getCost() {
        return cost;
    }

    @Override
    public final Integer getId() {
        return id;
    }

    @Override
    public final String getNameToken() {
        return name;
    }

    @Override
    public final Collection<JPASpecialRule> getSpecialRules() {
        return Collections.unmodifiableCollection(getSpecialRulesEditable());
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public final Boolean isTwoHanded() {
        return twoHanded;
    }

    public final void removeSpecialRule(final JPASpecialRule rule) {
        getSpecialRulesEditable().remove(rule);
    }

    public final void setCost(final Integer cost) {
        checkNotNull(cost, "Received a null pointer as cost");

        this.cost = cost;
    }

    @Override
    public final void setId(final Integer id) {
        checkNotNull(id, "Received a null pointer as id");

        this.id = id;
    }

    public final void setName(final String name) {
        checkNotNull(name, "Received a null pointer as name");

        this.name = name;
    }

    public final void setSpecialRules(final Collection<JPASpecialRule> rules) {
        checkNotNull(rules, "Received a null pointer as rules");

        getSpecialRulesEditable().clear();
        getSpecialRulesEditable().addAll(rules);
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("name", name)
                .add("cost", cost).add("rules", rules).toString();
    }

    private final Collection<JPASpecialRule> getSpecialRulesEditable() {
        return rules;
    }

}
