package uk.ac.ebi.intact.sanity.rules.annotatedobject;

import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.sanity.commons.SanityRuleException;
import uk.ac.ebi.intact.sanity.commons.annotation.SanityRule;
import uk.ac.ebi.intact.sanity.commons.rules.GeneralMessage;
import uk.ac.ebi.intact.sanity.commons.rules.MessageLevel;
import uk.ac.ebi.intact.sanity.commons.rules.Rule;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@SanityRule (target = AnnotatedObject.class)
public class XrefWithNonValidPrimaryId implements Rule<AnnotatedObject<?,?>>
{

    private static final String DESCRIPTION = "Xref primary ID is invalid. It does not match the regex expected.";
    private static final String SUGGESTION = "Fix the primary ID";

    public Collection<GeneralMessage> check(AnnotatedObject<?,?> intactObject) throws SanityRuleException {
        Collection<GeneralMessage> messages = new ArrayList<GeneralMessage>();

        for (Xref xref : intactObject.getXrefs()) {
            String primaryId = xref.getPrimaryId();

            String idValidationRegexp = getIdValidationRegexp(intactObject);

            if (idValidationRegexp != null && !primaryId.matches(idValidationRegexp)) {
                messages.add(new GeneralMessage(DESCRIPTION, MessageLevel.NORMAL, SUGGESTION, xref));
            }
        }

        return messages;
    }

    protected String getIdValidationRegexp(AnnotatedObject<?,?> annotatedObject) {
        String regexp = null;

        for (Annotation annotation : annotatedObject.getAnnotations()) {
            if (annotation.getCvTopic() != null) {
                CvObjectXref idXref = CvObjectUtils.getPsiMiIdentityXref(annotation.getCvTopic());
                if (idXref != null &&
                    idXref.getPrimaryId().equals(CvTopic.XREF_VALIDATION_REGEXP_MI_REF)) {
                     regexp = annotation.getAnnotationText();
                }
            }
        }

        return regexp;
    }
}
