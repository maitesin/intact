/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.dataexchange.psimi.xml.converter;

import uk.ac.ebi.intact.dataexchange.psimi.xml.converter.location.LocationTree;

/**
 * Psi Conversion Exception.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsiConversionException extends RuntimeException {

    private LocationTree location;

    public PsiConversionException() {
        resetState();
    }

    public PsiConversionException(Throwable cause) {
        super(cause);
        resetState();
    }

    public PsiConversionException(String message) {
        super(message);
        resetState();
    }

    public PsiConversionException(String message, Throwable cause) {
        super(message, cause);
        resetState();
    }

    public LocationTree getLocation() {
        return location;
    }

    public void resetState() {
        this.location = ConverterContext.getInstance().getLocation();
        ConverterContext.getInstance().resetLocation();
    }
}