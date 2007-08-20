/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.sanity.commons.rules;

import uk.ac.ebi.intact.model.IntactObject;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08-Mar-2007</pre>
 */
public class GeneralMessage {

    @Deprecated
    public static final MessageLevel LOW_LEVEL = MessageLevel.MINOR;

    @Deprecated
    public static final MessageLevel AVERAGE_LEVEL = MessageLevel.NORMAL;

    @Deprecated
    public static final MessageLevel HIGH_LEVEL = MessageLevel.MAJOR;

    public static final String NO_IDEA = "";
    /*
    The description of the rule.
    ex : An experiment should always be linked to an interaction.
     */
    private String description;
    /*
    The level of gravity of the rule.
     */
    private MessageLevel level;
    /*
    The proposed solution if the rule is transgressed
     */
    private String proposedSolution;
    /*
    The out-low object
     */
    private IntactObject outLow;

    @Deprecated
    public GeneralMessage(String description, int level, String proposedSolution, IntactObject outLow) {
        this.description = description;
        this.proposedSolution = proposedSolution;
        this.outLow = outLow;

        switch (level) {
            case 2:
                this.level = MessageLevel.MAJOR;
                break;
            case 1:
                this.level = MessageLevel.NORMAL;
                break;
            case 0:
                this.level = MessageLevel.MINOR;
                break;
        }
    }

    public GeneralMessage(String description, MessageLevel level, String proposedSolution, IntactObject outLow) {
        this.description = description;
        this.level = level;
        this.proposedSolution = proposedSolution;
        this.outLow = outLow;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MessageLevel getLevel() {
        return level;
    }

    public void setLevel(MessageLevel level) {
        this.level = level;
    }

    public String getProposedSolution() {
        return proposedSolution;
    }

    public void setProposedSolution(String proposedSolution) {
        this.proposedSolution = proposedSolution;
    }


    public IntactObject getOutLow() {
        return outLow;
    }

    public void setOutLow(IntactObject outLow) {
        this.outLow = outLow;
    }

    @Override
    public String toString() {
        return "["+level+"] "+description+" (Tip: "+proposedSolution+")";
    }
}