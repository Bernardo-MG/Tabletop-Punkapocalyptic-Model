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
package com.wandrell.tabletop.punkapocalyptic.model.faction;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.wandrell.persistence.PersistenceEntity;

@Entity(name = "Faction")
@Table(name = "factions")
public final class JPAFaction implements Faction, PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = -1;
    @Column(name = "name")
    private String  nameToken;

    public JPAFaction() {
        super();
    }

    public JPAFaction(final JPAFaction faction) {
        super();

        checkNotNull(faction, "Received a null pointer as faction");

        nameToken = faction.nameToken;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final JPAFaction other;

        other = (JPAFaction) obj;
        return Objects.equals(nameToken, other.nameToken);
    }

    @Override
    public final Integer getId() {
        return id;
    }

    @Override
    public final String getNameToken() {
        return nameToken;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(nameToken);
    }

    @Override
    public final void setId(final Integer id) {
        checkNotNull(id, "Received a null pointer as id");

        this.id = id;
    }

    public final void setNameToken(final String name) {
        checkNotNull(name, "Received a null pointer as name");

        nameToken = name;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("name", nameToken)
                .toString();
    }

}
