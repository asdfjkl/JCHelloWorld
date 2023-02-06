from smartcard.System import readers
from smartcard.util import toHexString
import logging

reader_index = 0

# Assume CMD1 has the following form:
# CLA 00
# INS 01
# P1  00
# P2  00
# LC  10 (16 Byte Data)
# Data: data to be transferred to card
# as an example, just return the data via return apdu

SELECT = [ 0x00, 0xA4, 0x04, 0x00, 0x08 ]
AID = [ 0xA0, 0x00, 0x00, 0x06, 0x48, 0x2F, 0x00, 0x02 ]

CMD1_CMD = [ 0x00, 0x01, 0x00, 0x00, 0x10 ]
CMD1_DATA = [ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, \
              0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F ]

def init_reader(reader_index):
    r=readers()
    connection = r[reader_index].createConnection()
    connection.connect()
    logging.info("ATR: "+toHexString(connection.getATR()))
    return connection

def main():
    logging.basicConfig(format='%(asctime)s %(message)s', level=logging.DEBUG)
    connection = init_reader(reader_index)
    data, sw1, sw2 = connection.transmit(SELECT + AID)
    print("select app, return data:  " + toHexString(data))
    print("select app, return sw's:  " + toHexString([sw1,sw2]))
    data, sw1, sw2 = connection.transmit(CMD1_CMD + CMD1_DATA)
    print("cmd1, return data      :  " + toHexString(data))
    print("cmd2, return data      :  " + toHexString([sw1,sw2]))

if __name__ == "__main__":
    main()
