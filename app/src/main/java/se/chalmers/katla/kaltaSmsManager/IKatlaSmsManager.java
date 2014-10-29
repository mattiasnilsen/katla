package se.chalmers.katla.kaltaSmsManager;

import android.app.PendingIntent;

import java.util.ArrayList;

/**
 * Created by Joel on 10/10/2014.
 */
public interface IKatlaSmsManager {

    /**
     * Send a text based sms. If the message is longer than 160 signs multiple messages might get sent.
     * @param destinationAddress the address to send the message to.
     * @param scAddress is the service center address or null to use the current default SMSC.
     * @param text the body of the message to send.
     * @param sentIntent if not NULL this PendingIntent is broadcast when the message is
     *                   successfully sent, or failed.
     * @param deliveryIntent if not NULL this PendingIntent is broadcast when the message is
     *                       delivered to the recipient. The raw pdu of the status report is in the
     *                       extended data ("pdu").
     */
    public void sendTextMessage(String destinationAddress, String scAddress, String text,
                                PendingIntent sentIntent, PendingIntent deliveryIntent);

    /**
     * Send a text based sms. If the message is longer than 160 signs multiple messages might get sent.
     * @param destinationAddress the address to send the message to.
     * @param scAddress is the service center address or null to use the current default SMSC.
     * @param text the body of the message to send.
     */
    public void sendTextMessage(String destinationAddress, String scAddress, String text);

    /**
     * Send a multi-part text based sms.
     * @param destinationAddress the address to send the message to
     * @param scAddress is is the service center address or null to use the current default SMSC.
     * @param parts the body of the message to send.
     * @param sentIntent if not NULL this PendingIntent is broadcast when the message is
     *                      successfully sent, or failed.
     * @param deliveryIntent if not NULL this PendingIntent is broadcast when the message is
     *                       delivered to the recipient. The raw pdu of the status report is in the
     *                       extended data ("pdu").
     */
    public void sendMultipartTextMessage(String destinationAddress, String scAddress,
                                         ArrayList<String> parts, ArrayList<PendingIntent> sentIntent,
                                         ArrayList<PendingIntent> deliveryIntent);

    /**
     * Send a multi-part text based sms.
     * @param destinationAddress the address to send the message to
     * @param scAddress is is the service center address or null to use the current default SMSC.
     * @param parts the body of the message to send.

     */
    public void sendMultipartTextMessage(String destinationAddress, String scAddress,
                                         ArrayList<String> parts);

}
