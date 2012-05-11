package nl.waisda.tags;
 
import java.io.IOException;
import java.text.NumberFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import nl.waisda.model.Util;



 
@SuppressWarnings("serial")
public class FormatOurNumber extends TagSupport {
    
	private String number;
    
    private static final NumberFormat FORMAT = NumberFormat
			.getIntegerInstance(Util.DUTCH_LOCALE);
 
    @Override
    public int doStartTag() throws JspException {
 
        try {
            //Get the writer object for output.
            JspWriter out = pageContext.getOut();
 
            //Perform substr operation on string.
            out.println(FORMAT.format(Integer.parseInt(number)));
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}