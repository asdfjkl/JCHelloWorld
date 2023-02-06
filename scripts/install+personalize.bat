#!/bin/bash
# install cap file to card
java -jar tools/gp.jar -reinstall cap/hw.cap 
  
# additional personalization/install scripts
# use pcsc_scan to detect precise reader name
#scriptor -r "SCL010 Contactless Reader [SCL010 Contactless Reader] (00000741000005) 00 00" personalisation_script_smartcafe.txt
#scriptor -r "SCM Microsystems Inc. SCL010 Contactless Reader [SCL010 Contactless Reader] (00000741000005) 00 00" scripts/personalisation_script_smartcafe.txt
#scriptor -r "OMNIKEY CardMan 5321 00 00" personalisation_script_smartcafe.txt
#scriptor -r "SCM Microsystems Inc. SCR 335 [CCID Interface] (21120706318594) 00 00" scripts/personalisation_script_smartcafe.txt
#scriptor -r "SCM Microsystems Inc. SCR 335 [CCID Interface] (21120706318594) 00 00" scripts/personalisation_script_smartcafe.txt
