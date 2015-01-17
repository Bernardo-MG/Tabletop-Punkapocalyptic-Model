/**
 * Copyright 2015 the original author or authors
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
package com.wandrell.tabletop.business.model.valuebox.derived.punkapocalyptic;

import com.wandrell.tabletop.business.model.valuebox.ValueBox;
import com.wandrell.tabletop.business.model.valuebox.derived.AbstractDerivedValueViewPoint;
import com.wandrell.tabletop.business.util.event.ValueChangeEvent;
import com.wandrell.tabletop.business.util.event.ValueChangeListener;

public final class GroupedUnitValorationDerivedValueViewPoint extends
        AbstractDerivedValueViewPoint {

    private final ValueChangeListener listener;
    private final ValueBox            size;
    private final Integer             valoration;

    {
        listener = new ValueChangeListener() {

            @Override
            public final void valueChanged(final ValueChangeEvent event) {
                fireValueChangedEvent(new ValueChangeEvent(this, getValue(),
                        getValue()));
            }

        };
    }

    public GroupedUnitValorationDerivedValueViewPoint(
            final GroupedUnitValorationDerivedValueViewPoint store) {
        super();

        valoration = store.valoration;
        size = store.size;
    }

    public GroupedUnitValorationDerivedValueViewPoint(final Integer valoration,
            final ValueBox size) {
        super();

        this.valoration = valoration;
        this.size = size;

        size.addValueChangeListener(listener);
    }

    @Override
    public final Integer getValue() {
        return getValoration() * getSize().getValue();
    }

    private final ValueBox getSize() {
        return size;
    }

    private final Integer getValoration() {
        return valoration;
    }

}
