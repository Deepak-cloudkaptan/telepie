package no.hild1.bank.telepay;

import no.hild1.bank.TelepayParserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Betfor21 extends Betfor {

    public Betfor21(BetforHeader header, String stringRecord) throws TelepayParserException {
        super(header, stringRecord);
        log.info(this.stringRecord);
        m = this.betforPattern.matcher(this.stringRecord);

        log.info(this.betforRegexp);
        if (m.matches()) {
            log.info("Found Betfor21");
        } else {
            throw new TelepayParserException(header.getRecordNum(), "Did not QWERGQWETHWETmatch BETFOR21");
        }
    }

    JButton showHideButton;
    JXCollapsiblePane mainCPanel;
    public JPanel getPanel() {
        JPanel panel = new JPanel();
        JXTable table = new JXTable();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Key");
        model.addColumn("Value");
        String[] keyValue = new String[2];
        for(Element e: Element.values()) {
            keyValue[0] = e.name();
            keyValue[1] = get(e);
            model.addRow(keyValue);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setModel(model);
        //table.getColumn("Value").setPreferredWidth(50);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        showHideButton = new JButton("Record #" + header.getRecordNum() + ", BETFOR" + header.getBetforTypeString());
        showHideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainCPanel.setCollapsed(!mainCPanel.isCollapsed());
            }
        });
        panel.add(showHideButton);
        mainCPanel = new JXCollapsiblePane();
        mainCPanel.setCollapsed(true);
        mainCPanel.add(table);
        mainCPanel.setCollapsed(false);
        panel.add(mainCPanel);

        return panel;
    }

    public String get(Element e) {
        return m.group(((Element)e).name());
    }

    /* makeBetforData.sh START */
	/* Generated by makeBetforData.sh */
	private static Log log = LogFactory.getLog(Betfor21.class);
	private static String betforRegexp = "^(?<"+ Element.APPLICATIONHEADER.name() + ">.{40})"
		+ "(?<"+ Element.TRANSACTIONCODE.name() + ">.{8})"
		+ "(?<"+ Element.ENTERPRISENUMBER.name() + ">.{11})"
		+ "(?<"+ Element.ACCOUNTNUMBER.name() + ">.{11})"
		+ "(?<"+ Element.SEQUENCECONTROL.name() + ">.{4})"
		+ "(?<"+ Element.REFERENCENUMBER.name() + ">.{6})"
		+ "(?<"+ Element.PAYMENTDATE.name() + ">.{6})"
		+ "(?<"+ Element.OWNREFORDER.name() + ">.{30})"
		+ "(?<"+ Element.RESERVED.name() + ">.{1})"
		+ "(?<"+ Element.PAYEESACCOUNTNUMBER.name() + ">.{11})"
		+ "(?<"+ Element.PAYEESNAME.name() + ">.{30})"
		+ "(?<"+ Element.ADDRESS1.name() + ">.{30})"
		+ "(?<"+ Element.ADDRESS2.name() + ">.{30})"
		+ "(?<"+ Element.POSTCODE.name() + ">.{4})"
		+ "(?<"+ Element.CITY.name() + ">.{26})"
		+ "(?<"+ Element.AMOUNTTOOWNACCOUNT.name() + ">.{15})"
		+ "(?<"+ Element.TEXTCODE.name() + ">.{3})"
		+ "(?<"+ Element.TRANSFERCODE.name() + ">.{1})"
		+ "(?<"+ Element.CANCELLATIONCODE.name() + ">.{1})"
		+ "(?<"+ Element.TOTALAMOUNT.name() + ">.{15})"
		+ "(?<"+ Element.CLIENTREFERENCE.name() + ">.{5})"
		+ "(?<"+ Element.VALUEDATE.name() + ">.{6})"
		+ "(?<"+ Element.VALUEDATERECEIVINGBANK.name() + ">.{6})"
		+ "(?<"+ Element.CANCELLATIONCAUSE.name() + ">.{1})"
		+ "(?<"+ Element.RESERVED2.name() + ">.{9})"
		+ "(?<"+ Element.FORMNO.name() + ">.{10})"
		+ "$";
	public static Pattern betforPattern = Pattern.compile(betforRegexp);
	Matcher m;
	public enum Element {
		APPLICATIONHEADER, TRANSACTIONCODE, ENTERPRISENUMBER, 
		ACCOUNTNUMBER, SEQUENCECONTROL, REFERENCENUMBER, 
		PAYMENTDATE, OWNREFORDER, RESERVED, 
		PAYEESACCOUNTNUMBER, PAYEESNAME, ADDRESS1, 
		ADDRESS2, POSTCODE, CITY, 
		AMOUNTTOOWNACCOUNT, TEXTCODE, TRANSFERCODE, 
		CANCELLATIONCODE, TOTALAMOUNT, CLIENTREFERENCE, 
		VALUEDATE, VALUEDATERECEIVINGBANK, CANCELLATIONCAUSE, 
		RESERVED2, FORMNO
	}
    /* makeBetforData.sh STOP */
}
