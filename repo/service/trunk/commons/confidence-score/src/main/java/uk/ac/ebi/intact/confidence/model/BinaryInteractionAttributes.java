/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package uk.ac.ebi.intact.confidence.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * BinaryInteraction with Attributes.
 *
 * @author Irina Armean (iarmean@ebi.ac.uk)
 * @version $Id$
 * @since 0.1
 *        <pre>
 *        07-Dec-2007
 *        </pre>
 */
public class BinaryInteractionAttributes extends BinaryInteraction{
    private List<Attribute> attributes;

    public BinaryInteractionAttributes(Identifier firstIdentifier, Identifier secondIdentifier, Confidence confidence){
        super(firstIdentifier, secondIdentifier, confidence);
        attributes = new ArrayList<Attribute>();
    }

    public BinaryInteractionAttributes(Identifier firstIdentifier, Identifier secondIdentifier, List<Attribute> attribute, Confidence confidence){
        this( firstIdentifier, secondIdentifier, confidence );
        if ( attribute != null ) {
            this.attributes = attribute;
        }
    }

    public Identifier getFirstId() {
        return super.getFirstId();
    }

    public Identifier getSecondId() {
        return super.getSecondId();
    }

    public Confidence getConfidence(){
        return super.getConfidence();
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes( List<Attribute> attributes ) {
        this.attributes = attributes;
    }

    public void addAttributes(List<Attribute> attributes){
        attributes.addAll( attributes);
    }

    public void addAttribute(Attribute attribute){
        attributes.add( attribute);
    }

    public void removeAttribute(Attribute attribute){
        attributes.remove( attribute);
    }

    public String convertToString(){
       String result = super.convertToString();

       for ( Iterator<Attribute> attributeIterator = attributes.iterator(); attributeIterator.hasNext(); ) {
           Attribute attribute =  attributeIterator.next();
           result += "," + attribute.convertToString();
       }

       return result;
   }
    
}
