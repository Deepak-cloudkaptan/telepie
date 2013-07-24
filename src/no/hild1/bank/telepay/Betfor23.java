package no.hild1.bank.telepay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import no.hild1.bank.TelepayParserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXCollapsiblePane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Betfor23 extends Betfor {
    private boolean isKIDPayment = false;
    public Betfor23(BetforHeader header, String stringRecord) throws TelepayParserException {
        super(header, stringRecord);
        log.info(this.stringRecord);
        m = this.betforPattern.matcher(this.stringRecord);

        log.info(this.betforRegexp);
        if (m.matches()) {
            log.info("Record #" + header.getRecordNum()
                    + " (staring at line " + (header.getRecordNum()*4) + ") is a BETFOR23");
            isKIDPayment = get(Element.KID).matches("^[ ]{27}$");
        } else {
            String error = "Failed to parse record #" + header.getRecordNum()
                    + " (staring at line " + (header.getRecordNum()*4) + ") as a BETFOR23";
            log.error(error);
            throw new TelepayParserException(header.getRecordNum(),
                    error);
        }
    }

    @Override
    public Color getColor(ElementInterface e) {
        switch (((Element)e)) {
            case ACCOUNTNUMBER:
                return Color.YELLOW;
            case ENTERPRISENUMBER:
                return Color.MAGENTA;
            case INVOICEAMOUNT:
                return Color.GREEN;
            case INVOICEDATE:
                return Color.BLUE;
            case KID:
                return Color.CYAN;
            default:
                return null;
        }
    }

    public boolean isKIDPayment() {
        return isKIDPayment;
    }

    /* makeBetforData.sh START */
	/* Generated by makeBetforData.sh */
	private static Log log = LogFactory.getLog(Betfor23.class);
	private static String betforRegexp = "^(?<"+ Element.APPLICATIONHEADER.name() + ">.{40})"
		+ "(?<"+ Element.TRANSACTIONCODE.name() + ">.{8})"
		+ "(?<"+ Element.ENTERPRISENUMBER.name() + ">.{11})"
		+ "(?<"+ Element.ACCOUNTNUMBER.name() + ">.{11})"
		+ "(?<"+ Element.SEQUENCECONTROL.name() + ">.{4})"
		+ "(?<"+ Element.REFERENCENUMBER.name() + ">.{6})"
		+ "(?<"+ Element.PAYEEREFINVOICE.name() + ">.{120})"
		+ "(?<"+ Element.KID.name() + ">.{27})"
		+ "(?<"+ Element.OWNREFERENCEINVOICE.name() + ">.{30})"
		+ "(?<"+ Element.INVOICEAMOUNT.name() + ">.{15})"
		+ "(?<"+ Element.DEBITCREDITCODECANCELLATIONCODE.name() + ">.{1})"
		+ "(?<"+ Element.INVOICENUMBER.name() + ">.{20})"
		+ "(?<"+ Element.SERIALNUMBER.name() + ">.{3})"
		+ "(?<"+ Element.CANCELLATIONCAUSE.name() + ">.{1})"
		+ "(?<"+ Element.CUSTOMERNUMBER.name() + ">.{15})"
		+ "(?<"+ Element.INVOICEDATE.name() + ">.{8})"
		+ "$";
	public static Pattern betforPattern = Pattern.compile(betforRegexp);
	public enum Element implements ElementInterface {
		APPLICATIONHEADER, TRANSACTIONCODE, ENTERPRISENUMBER, 
		ACCOUNTNUMBER, SEQUENCECONTROL, REFERENCENUMBER, 
		PAYEEREFINVOICE, KID, OWNREFERENCEINVOICE, 
		INVOICEAMOUNT, DEBITCREDITCODECANCELLATIONCODE, INVOICENUMBER, 
		SERIALNUMBER, CANCELLATIONCAUSE, CUSTOMERNUMBER, 
		INVOICEDATE
	}
	public ElementInterface[] getElements() { return Element.values(); }
    /* makeBetforData.sh STOP */
}
