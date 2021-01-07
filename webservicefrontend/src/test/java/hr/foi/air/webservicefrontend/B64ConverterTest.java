package hr.foi.air.webservicefrontend;
import android.net.Uri;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
public class B64ConverterTest {

    @Test
    public void B64ConverterBad() {
        Uri badUri=Uri.parse("badURI");
        String result = B64Converter.Convert(badUri);
        assertEquals("",result);
    }

    @Test
    public void B64ConverterEmpty() {

        Uri emptyUri= Uri.parse("");
        String result = B64Converter.Convert(emptyUri);
        assertEquals("",result);

    }
}
