package uk.ac.ebi.intact.jami.merger;

import uk.ac.ebi.intact.jami.model.user.Preference;
import uk.ac.ebi.intact.jami.model.user.Role;
import uk.ac.ebi.intact.jami.model.user.User;

import java.util.Collection;
import java.util.Iterator;

/**
 * User merger that will only enrich properties of an existing user.
 * It will only add missing info, it does not override anything
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class UserMergerEnrichOnly extends IntactDbMergerEnrichOnly<User, User> {

    public UserMergerEnrichOnly(){
        super(User.class);
    }

    @Override
    public User merge(User obj1, User obj2) {
        // obj2 is mergedUser
        User mergedUser = super.merge(obj1, obj2);

        // merge last login
        if (mergedUser.getLastLogin() == null){
            mergedUser.setLastLogin(obj1.getLastLogin());
        }
        //merge roles
        if (obj1.areRolesInitialized()){
            mergeRoles(mergedUser, mergedUser.getRoles(), obj1.getRoles());
        }
        //merge preferences
        if (obj1.arePreferencesInitialized()){
            mergePreferences(mergedUser.getPreferences(), obj1.getPreferences());
        }
        return mergedUser;
    }

    private void mergeRoles(User userToEnrich, Collection<Role> toEnrichRoles, Collection<Role> sourceRoles) {
        Iterator<Role> roleIterator = toEnrichRoles.iterator();
        while(roleIterator.hasNext()){
            Role role = roleIterator.next();
            boolean containsRole = false;
            for (Role role2 : sourceRoles){
                if (role.equals(role2)){
                    containsRole = true;
                    break;
                }
            }
            // remove roles not in second list
            if (!containsRole){
                roleIterator.remove();
            }
        }

        roleIterator = sourceRoles.iterator();
        while(roleIterator.hasNext()){
            Role role = roleIterator.next();
            boolean containsRole = false;
            for (Role role2 : toEnrichRoles){
                // identical roles
                if (role.equals(role2)){
                    containsRole = true;
                    break;
                }
            }
            // add missing role not in second list
            if (!containsRole){
                userToEnrich.getRoles().add(role);
            }
        }
    }


    private void mergePreferences(Collection<Preference> toEnrichPreferences, Collection<Preference> sourcePreferences){

        Iterator<Preference> prefIterator = toEnrichPreferences.iterator();
        while(prefIterator.hasNext()){
            Preference pref = prefIterator.next();
            boolean containsPref = false;
            for (Preference pref2 : sourcePreferences){
                if (pref.equals(pref2)){
                    containsPref = true;
                    break;
                }
            }
            // remove preferences not in second list
            if (!containsPref){
                prefIterator.remove();
            }
        }

        prefIterator = sourcePreferences.iterator();
        while(prefIterator.hasNext()){
            Preference pref = prefIterator.next();
            boolean containsPref = false;
            for (Preference pref2 : toEnrichPreferences){
                // identical pref
                if (pref.equals(pref2)){
                    // if value updated, update value of pref in the preferences to enrich
                    if ((pref.getValue() == null && pref2.getValue() == null)
                            || (pref.getValue() != null && pref.getValue().equals(pref2.getValue()))){
                        pref2.setValue(pref.getValue());
                        break;
                    }
                    else{
                        containsPref = true;
                        break;
                    }
                }
            }
            // add missing pref not in second list
            if (!containsPref){
                toEnrichPreferences.add(pref);
            }
        }
    }
}
