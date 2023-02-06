package de.asdfjkl.helloworld.javacard;


import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.OwnerPIN;
import javacard.framework.Util;
import javacard.security.CryptoException;
import javacardx.apdu.ExtendedLength;


public class MainApplet extends Applet implements ExtendedLength{
	
	private static final byte[] HELLO= {0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x72, 0x6f, 0x62, 0x65, 0x72, 0x74} ;
	private static final byte INS_CMD1 = (byte) 0x01;
	private static final byte INS_CMD2 = (byte) 0x02;
	private static final byte INS_CMD3 = (byte) 0x03;
	
	// Assume CMD1 has the following form:
	//	CLA 00
	//	INS 01
	//	P1  00
	//	P2  00
	//	LC  10 (16 Byte Data)
	//	Data: data to be transferred to card
	// as an example, just return the data via return apdu


	public static void install(byte[] buffer, short offset, byte length) 
	
	{
		// GP-compliant JavaCard applet registration
		new MainApplet().register();
	}

	public void process(APDU apdu) {
		
		// return 9000 on SELECT
		if (selectingApplet()) {
			return;
		}

		byte[] buf = apdu.getBuffer();
		switch (buf[ISO7816.OFFSET_INS]) {
		case INS_CMD1:   // apdu has form as described above
			processCDM1(apdu);
			break;
		case INS_CMD2:
			break;
		case INS_CMD3:
			break;
		default:
			// if unknown instruction, return not supported
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

	}
	
	private void processCDM1(APDU apdu) throws ISOException {
		
		byte[] buffer = apdu.getBuffer();
		short len = apdu.setIncomingAndReceive();
		short dataOffset = apdu.getOffsetCdata();
		byte p1 = buffer[ISO7816.OFFSET_P1]; // 00, could encode further parameters for CMD1 here 
		byte p2 = buffer[ISO7816.OFFSET_P2]; // 00, could encode further parameters for CMD2 here

		if (p1 == 0x00 && p2 == 0x00) {
			if (len != 16) {
				ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
			} else {
				apdu.setOutgoingAndSend(dataOffset, (short)16);
			}
		} else {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}	
		return;
	}

	
	
}
