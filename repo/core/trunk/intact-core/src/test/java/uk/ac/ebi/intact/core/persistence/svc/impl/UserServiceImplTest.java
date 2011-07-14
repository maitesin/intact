package uk.ac.ebi.intact.core.persistence.svc.impl;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.intact.core.persistence.dao.user.UserDao;
import uk.ac.ebi.intact.core.persistence.svc.UserService;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.model.user.Preference;
import uk.ac.ebi.intact.model.user.Role;
import uk.ac.ebi.intact.model.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * UserServiceImpl Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.5
 */
public class UserServiceImplTest extends IntactBasicTestCase {

    @Autowired
    private UserService userService;

    @Test
    public void importUsers() throws Exception {

        final User sandra = getMockBuilder().createUserSandra();
        final User jyoti = getMockBuilder().createUserJyoti();

        final UserDao userDao = getDataContext().getDaoFactory().getUserDao();
        userDao.persist( sandra );
        userDao.persist( jyoti );

        final List<User> users = userDao.getAll();
        for ( User user : users ) {
            System.out.println(user);
        }

        // admin + undefined + 2
        Assert.assertEquals( 4, users.size() );

        // Test user import
        User updatedSandra = getMockBuilder().createUser( "sandra", "s", "o", "so@example.com" );
        User sam = getMockBuilder().createUser( "sam", "s", "k", "sk@example.com" );

        userService.importUsers( Arrays.asList( updatedSandra, sam, jyoti ), true );

        final User reloadedSandra = userDao.getByLogin( "sandra" );
        Assert.assertNotNull( reloadedSandra );
        Assert.assertEquals( "sandra", reloadedSandra.getLogin() );
        Assert.assertEquals( "s", reloadedSandra.getFirstName() );
        Assert.assertEquals( "o", reloadedSandra.getLastName() );
        Assert.assertEquals( "so@example.com", reloadedSandra.getEmail() );

        final User reloadedSam = userDao.getByLogin( "sam" );
        Assert.assertNotNull( reloadedSam );
        Assert.assertEquals( "sam", reloadedSam.getLogin() );
        Assert.assertEquals( "s", reloadedSam.getFirstName() );
        Assert.assertEquals( "k", reloadedSam.getLastName() );
        Assert.assertEquals( "sk@example.com", reloadedSam.getEmail() );

        final User reloadedJyoti = userDao.getByLogin( "jyoti" );
        Assert.assertNotNull( reloadedJyoti );
        Assert.assertEquals( "jyoti", reloadedJyoti.getLogin() );
        Assert.assertEquals( "jyoti", reloadedJyoti.getFirstName() );
        Assert.assertEquals( "-", reloadedJyoti.getLastName() );
        Assert.assertEquals( "jyoti@example.com", reloadedJyoti.getEmail() );
    }

    @Test
    public void importUsers_noUpdate() throws Exception {

        final User sandra = getMockBuilder().createUserSandra();
        final User jyoti = getMockBuilder().createUserJyoti();

        getCorePersister().saveOrUpdate( sandra, jyoti );

        final UserDao userDao = getDataContext().getDaoFactory().getUserDao();
        final List<User> users = userDao.getAll();
        // admin + undefined + 2
        Assert.assertEquals( 4, users.size() );

        // Test user import
        User updatedSandra = getMockBuilder().createUser( "sandra", "s", "o", "so@example.com" );
        User sam = getMockBuilder().createUser( "sam", "s", "k", "sk@example.com" );

        userService.importUsers( Arrays.asList( updatedSandra, sam, jyoti ), false );

        // this user should be identical to the original (mock) one
        final User reloadedSandra = userDao.getByLogin( "sandra" );
        Assert.assertNotNull( reloadedSandra );
        Assert.assertEquals( "sandra", reloadedSandra.getLogin() );
        Assert.assertEquals( "sandra", reloadedSandra.getFirstName() );
        Assert.assertEquals( "-", reloadedSandra.getLastName() );
        Assert.assertEquals( "sandra@example.com", reloadedSandra.getEmail() );

        final User reloadedSam = userDao.getByLogin( "sam" );
        Assert.assertNotNull( reloadedSam );
        Assert.assertEquals( "sam", reloadedSam.getLogin() );
        Assert.assertEquals( "s", reloadedSam.getFirstName() );
        Assert.assertEquals( "k", reloadedSam.getLastName() );
        Assert.assertEquals( "sk@example.com", reloadedSam.getEmail() );

        final User reloadedJyoti = userDao.getByLogin( "jyoti" );
        Assert.assertNotNull( reloadedJyoti );
        Assert.assertEquals( "jyoti", reloadedJyoti.getLogin() );
        Assert.assertEquals( "jyoti", reloadedJyoti.getFirstName() );
        Assert.assertEquals( "-", reloadedJyoti.getLastName() );
        Assert.assertEquals( "jyoti@example.com", reloadedJyoti.getEmail() );
    }

    ///////////////////////////
    // Import/Export XML

    public File getParent() {
        final URL resource = UserServiceImplTest.class.getResource( "/" );
        return new File( resource.getFile() ).getParentFile();
    }

    private void assertUserEquals( User sam, User copy ) {
        Assert.assertEquals( copy.getLogin(), sam.getLogin() );
        Assert.assertEquals( copy.getPassword(), sam.getPassword() );
        Assert.assertEquals( copy.getEmail(), sam.getEmail() );
        Assert.assertEquals( copy.getOpenIdUrl(), sam.getOpenIdUrl() );
        Assert.assertEquals( copy.getFirstName(), sam.getFirstName() );
        Assert.assertEquals( copy.getLastName(), sam.getLastName() );

        Assert.assertTrue( CollectionUtils.isEqualCollection( copy.getRoles(), sam.getRoles() ) );
        Assert.assertTrue( CollectionUtils.isEqualCollection( copy.getPreferences(), sam.getPreferences() ) );
    }

    private User createSam() {
        User sam = new User( "skerrien", "Samuel", "Kerrien", "skerrien@example.com" );
        sam.setPassword( "############" );
        sam.setOpenIdUrl( "http://www.google.com" );
        sam.setDisabled( true );

        Role role = new Role( "ADMIN" );
        sam.addRole( role );

        Preference p = new Preference( sam, "note" );
        p.setValue( "some\nimportant information..." );
        sam.getPreferences().add( p );
        return sam;
    }

    private User createBruno() {
        User bruno = new User( "baranda", "Bruno", "Aranda", "baranda@example.com" );
        bruno.setPassword( "***************" );
        bruno.setOpenIdUrl( "http://www.yahoo.com" );
        bruno.setDisabled( false );

        Role role = new Role( "CURATOR" );
        bruno.addRole( role );

        Preference p = new Preference( bruno, "note" );
        p.setValue( "lalalalalalalala" );
        bruno.getPreferences().add( p );
        return bruno;
    }

    private User createMarine() {
        User marine = new User( "marine", "Marine", "Dumousseau", "marine@example.com" );
        marine.setPassword( "xxxxxxxxxxxxxxxx" );
        marine.setOpenIdUrl( "http://www.wanadoo.fr" );
        marine.setDisabled( false );
        return marine;
    }

    @Test
    public void complexSingleUser() throws Exception {

        User sam = createSam();

        final File output = new File( getParent(), "user.xml" );
        userService.marshallUsers( Arrays.asList( sam ), new FileOutputStream( output ) );

        InputStream is = new FileInputStream( output );
        final Collection<User> users = userService.parseUsers( is );
        User copy = users.iterator().next();

        assertUserEquals( sam, copy );
    }

    @Test
    public void threeUsers() throws Exception {

        User sam = createSam();
        User bruno = createBruno();
        User marine = createMarine();

        final File output = new File( getParent(), "users.xml" );
        userService.marshallUsers( Arrays.asList( sam, bruno, marine ), new FileOutputStream( output ) );

        InputStream is = new FileInputStream( output );
        final Collection<User> users = userService.parseUsers( is );
        Assert.assertNotNull( users );
        Assert.assertEquals( 3, users.size() );
        final Iterator<User> iterator = users.iterator();
        User copySam = iterator.next();
        User copyBruno = iterator.next();
        User copyMarine = iterator.next();

        assertUserEquals( sam, copySam );
        assertUserEquals( bruno, copyBruno );
        assertUserEquals( marine, copyMarine);
    }
}
