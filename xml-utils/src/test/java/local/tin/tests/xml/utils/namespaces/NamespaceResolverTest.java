package local.tin.tests.xml.utils.namespaces;

import java.util.HashMap;
import java.util.Map;
import local.tin.tests.xml.utils.Common;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyString;

/**
 *
 * @author benitodarder
 */
public class NamespaceResolverTest {

    private static final String XMLNS_01 = "crap";
    private static final String XMLNS_URI_01 = "this is the default";
    private static final String XMLNS_URI_02 = "whatever";
    private static final String XMLNS_02 = "meh";
    private NamespaceResolver namespaceResolver;
    private Map<String, String> map;

    @Before
    public void setUp() {
        map = new HashMap<>();
        map.put(Common.ATTRIBUTE_XMLNS, XMLNS_URI_01);
        map.put(Common.ATTRIBUTE_XMLNS + NamespaceResolver.NAMESPACE_SEPARATOR + XMLNS_02, XMLNS_URI_02);
        namespaceResolver = new NamespaceResolver(XMLNS_01, map);
    }

    @Test
    public void getFakeDefaultPrefix_returns_stated_string() {

        String result = namespaceResolver.getFakeDefaultPrefix();

        assertThat(result, equalTo(XMLNS_01));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPrefix_throws_exception() {

        namespaceResolver.getPrefix(anyString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPrefixes_throws_exception() {

        namespaceResolver.getPrefixes(anyString());
    }

    @Test
    public void getFakeDefaultPrefix_returns_a_default_value() {
        namespaceResolver = new NamespaceResolver(map);

        String result = namespaceResolver.getFakeDefaultPrefix();

        assertThat(result, notNullValue());
    }

    @Test
    public void getNamespaceURI_returns_expected_string_for_fake_namespace() {

        String result = namespaceResolver.getNamespaceURI(XMLNS_01);

        assertThat(result, equalTo(XMLNS_URI_01));
    }

    @Test
    public void getNamespaceURI_returns_expected_string_for_namespace() {

        String result = namespaceResolver.getNamespaceURI(XMLNS_02);

        assertThat(result, equalTo(XMLNS_URI_02));
    }

    @Test
    public void getNamespaceURI_returns_null_for_unkown_namespace() {

        String result = namespaceResolver.getNamespaceURI(XMLNS_02 + XMLNS_02);

        assertThat(result, nullValue());
    }

}
