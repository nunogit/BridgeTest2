/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.uri;

import java.util.List;
import org.bridgedb.uri.lens.Lens;
import org.bridgedb.uri.lens.LensTools;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Christian
 */
public abstract class UriMapperOtherTest extends UriListenerTest{


    /**
     * Test of mapFull method, of class UriMapper.
     */
    @Test
    public void testGetLenss() throws Exception {
        report("getLenss");
        List<Lens> results = LensTools.getLens(LensTools.PUBLIC_GROUP_NAME);
        assertThat(results.size(), greaterThanOrEqualTo(2));
     }

    /**
     * Test of mapFull method, of class UriMapper.
     */
    @Test
    public void testGetDefaultLens() throws Exception {
        report("GetDefaultLens");
        Lens result = LensTools.byId(Lens.DEFAULT_LENS_NAME);
        assertEquals(Lens.DEFAULT_LENS_NAME, result.getId());
        assertNotNull(result.getCreatedBy());
        assertNotNull(result.getCreatedOn());
        assertNotNull(result.getName());
        assertThat(result.getJustifications().size(), greaterThanOrEqualTo(1));
     }

    /**
     * Test of mapFull method, of class UriMapper.
     */
    @Test
    public void testGetAllLens() throws Exception {
        report("GetAllLens");
        Lens result = LensTools.byId(Lens.ALL_LENS_NAME);
        assertEquals(Lens.ALL_LENS_NAME, result.getId());
        //assertNotNull(result.getCreatedBy());
        assertNotNull(result.getDescription());
        assertNotNull(result.getName());
        assertThat(result.getJustifications().size(), greaterThanOrEqualTo(1));
     }
}
